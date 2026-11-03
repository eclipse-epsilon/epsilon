/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.Region;
import org.eclipse.epsilon.coverage.model.CoverageElement;
import org.eclipse.epsilon.coverage.model.CoverageFactory;
import org.eclipse.epsilon.coverage.model.CoverageSession;
import org.eclipse.epsilon.coverage.model.SourceFile;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dom.Import;
import org.eclipse.epsilon.eol.dom.StatementBlock;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.control.IInheritableExecutionListener;

/**
 * Records which AST nodes of Epsilon programs are executed. Usage:
 *
 * <pre>
 * CoverageRecorder recorder = CoverageRecorder.attach(module);
 * module.execute();
 * CoverageSession session = recorder.createCoverageSession("My run");
 * CoverageResources.save(session, new File("coverage.ecv"));
 * </pre>
 *
 * The same recorder can be attached to several modules (e.g. in a workflow),
 * in which case the produced session contains the coverage of all of them.
 * Modules that are only discovered during execution (e.g. EGL templates invoked
 * from EGX, or modules imported dynamically) are included automatically the first
 * time one of their elements is executed. This class is thread-safe.
 *
 * @since 2.10
 */
public class CoverageRecorder implements IInheritableExecutionListener {

	protected final CoverageProbes probes;

	/** Hit counters for all known module elements (keyed by identity) */
	protected final Map<Object, AtomicLong> counters = new ConcurrentHashMap<>();

	/** Modules (in discovery order) and their root elements */
	protected final Map<IModule, List<ModuleElement>> modules = new IdentityHashMap<>();
	protected final List<IModule> moduleOrder = new ArrayList<>();

	/** All elements that have been registered so far (guarded by this) */
	protected final Set<ModuleElement> registered = Collections.newSetFromMap(new IdentityHashMap<>());

	public CoverageRecorder() {
		this(CoverageProbes.INSTANCE);
	}

	public CoverageRecorder(CoverageProbes probes) {
		this.probes = probes;
	}

	/**
	 * Creates a new recorder and attaches it to the module.
	 */
	public static CoverageRecorder attach(IEolModule module) {
		CoverageRecorder recorder = new CoverageRecorder();
		recorder.attachTo(module);
		return recorder;
	}

	/**
	 * Registers this recorder as an execution listener of the module and
	 * eagerly discovers the elements of the module and its imports, so that
	 * elements that are never executed are reported as uncovered.
	 */
	public void attachTo(IEolModule module) {
		if (!module.getContext().getExecutorFactory().getExecutionListeners().contains(this)) {
			module.getContext().getExecutorFactory().addExecutionListener(this);
		}
		discover(module);
	}

	public void detachFrom(IEolModule module) {
		module.getContext().getExecutorFactory().removeExecutionListener(this);
	}

	/**
	 * Discovers the elements of the module (and the modules it imports).
	 */
	public synchronized void discover(IModule module) {
		if (module == null || modules.containsKey(module)) return;

		List<ModuleElement> roots = new ArrayList<>();
		modules.put(module, roots);
		moduleOrder.add(module);

		List<ModuleElement> children = new ArrayList<>(module.getChildren());
		if (module instanceof IEolModule) {
			StatementBlock main = ((IEolModule) module).getMain();
			if (main != null && !containsIdentical(children, main)) children.add(main);
		}

		for (ModuleElement child : children) {
			if (register(child)) roots.add(child);
		}

		if (module instanceof IEolModule) {
			for (Import imp : ((IEolModule) module).getImports()) {
				discover(imp.getImportedModule());
			}
		}
	}

	protected boolean register(ModuleElement element) {
		if (element == null || element instanceof IModule || probes.isIgnored(element) || !registered.add(element)) return false;
		counters.putIfAbsent(new IdentityKey(element), new AtomicLong());
		for (ModuleElement child : element.getChildren()) {
			register(child);
		}
		return true;
	}

	@Override
	public void aboutToExecute(ModuleElement ast, IEolContext context) {
		if (ast instanceof IModule) {
			// e.g. when the recorder has been registered without attachTo()
			discover((IModule) ast);
			return;
		}
		IdentityKey key = new IdentityKey(ast);
		AtomicLong counter = counters.get(key);
		if (counter == null) {
			discover(ast.getModule());
			counter = counters.computeIfAbsent(key, k -> new AtomicLong());
		}
		counter.incrementAndGet();
	}

	@Override
	public void finishedExecuting(ModuleElement ast, Object result, IEolContext context) {}

	@Override
	public void finishedExecutingWithException(ModuleElement ast, EolRuntimeException exception, IEolContext context) {}

	/**
	 * @return The number of times the element has been executed so far
	 */
	public long getHits(ModuleElement element) {
		AtomicLong counter = counters.get(new IdentityKey(element));
		return counter == null ? 0 : counter.get();
	}

	/**
	 * Clears all hit counters (discovered modules are retained)
	 */
	public void reset() {
		for (AtomicLong counter : counters.values()) counter.set(0);
	}

	public synchronized Collection<IModule> getModules() {
		return new ArrayList<>(moduleOrder);
	}

	public CoverageSession createCoverageSession() {
		return createCoverageSession(null);
	}

	/**
	 * Creates an EMF model capturing a snapshot of the coverage information recorded so far.
	 */
	public synchronized CoverageSession createCoverageSession(String name) {
		CoverageSession session = CoverageFactory.eINSTANCE.createCoverageSession();
		session.setName(name);
		session.setTimestamp(new Date());

		Map<String, SourceFile> files = new LinkedHashMap<>();
		Set<ModuleElement> visited = Collections.newSetFromMap(new IdentityHashMap<>());
		int inline = 0;

		for (IModule module : moduleOrder) {
			List<ModuleElement> roots = modules.get(module);
			if (roots.isEmpty()) continue;

			URI uri = getUri(module, roots);
			String key = uri != null ? uri.toString() : "inline:" + (++inline);

			SourceFile file = files.get(key);
			if (file == null) {
				file = CoverageFactory.eINSTANCE.createSourceFile();
				file.setUri(uri != null ? uri.toString() : null);
				file.setLanguage(getLanguage(module));
				files.put(key, file);
			}
			SourceText text = SourceText.read(uri);
			if (text != null) file.setContentHash(text.getHash());
			List<CoverageElement> elements = new ArrayList<>();
			for (ModuleElement root : roots) {
				elements.addAll(createElements(root, text, visited));
			}
			// The same file may have been parsed by more than one module (e.g. in workflows)
			CoverageResources.mergeElements(file.getElements(), elements);
		}

		for (SourceFile file : files.values()) {
			if (!file.getElements().isEmpty()) session.getFiles().add(file);
		}
		return session;
	}

	/**
	 * Converts an AST node into coverage elements. Nodes without a region are
	 * skipped (but their children are retained), and so are non-probe nodes
	 * that contain no probes. Nodes that are reachable from more than one parent
	 * (e.g. EVL fixes) are only included once.
	 */
	protected List<CoverageElement> createElements(ModuleElement node, SourceText text, Set<ModuleElement> visited) {
		if (!visited.add(node)) return Collections.emptyList();
		List<CoverageElement> children = new ArrayList<>();
		for (ModuleElement child : node.getChildren()) {
			if (child instanceof IModule || probes.isIgnored(child)) continue;
			children.addAll(createElements(child, text, visited));
		}

		boolean probe = probes.isProbe(node);
		if (!CoverageProbes.hasRegion(node) || (!probe && !hasProbes(children))) {
			return children;
		}

		CoverageElement element = CoverageFactory.eINSTANCE.createCoverageElement();
		Region region = node.getRegion();
		element.setKind(node.getClass().getSimpleName());
		element.setStartLine(region.getStart().getLine());
		element.setStartColumn(region.getStart().getColumn());
		element.setEndLine(region.getEnd().getLine());
		element.setEndColumn(region.getEnd().getColumn());
		element.setProbe(probe);
		element.setHits(getHits(node));
		element.setLabel(getLabel(node, probe, text));
		element.getChildren().addAll(children);
		return Collections.singletonList(element);
	}

	protected boolean hasProbes(List<CoverageElement> elements) {
		for (CoverageElement element : elements) {
			if (element.isProbe() || hasProbes(element.getChildren())) return true;
		}
		return false;
	}

	protected String getLabel(ModuleElement node, boolean probe, SourceText text) {
		if (!probe) {
			String name = getName(node);
			if (name != null) return node.getClass().getSimpleName() + " " + name;
		}
		if (text != null) {
			String snippet = text.getSnippet(node.getRegion());
			if (snippet != null && !snippet.isEmpty()) return snippet;
		}
		return node.getClass().getSimpleName();
	}

	/**
	 * Uses reflection to retrieve the name of named elements
	 * (e.g. operations, rules, constraints).
	 */
	protected String getName(ModuleElement node) {
		try {
			Method method = node.getClass().getMethod("getName");
			if (method.getParameterCount() == 0 && method.getReturnType() == String.class) {
				return (String) method.invoke(node);
			}
		}
		catch (Exception ex) {
			// No name
		}
		return null;
	}

	protected URI getUri(IModule module, List<ModuleElement> roots) {
		URI uri = module.getSourceUri();
		if (uri != null) return uri;
		for (ModuleElement root : roots) {
			if (root.getUri() != null) return root.getUri();
		}
		return null;
	}

	protected String getLanguage(IModule module) {
		String name = module.getClass().getSimpleName();
		return name.endsWith("Module") ? name.substring(0, name.length() - "Module".length()) : name;
	}

	protected static boolean containsIdentical(Collection<?> collection, Object object) {
		for (Object o : collection) if (o == object) return true;
		return false;
	}

	/**
	 * Map key that uses object identity (AST nodes override equals/hashCode
	 * so that distinct nodes with the same region compare as equal).
	 */
	protected static final class IdentityKey {
		protected final Object object;
		protected final int hash;

		IdentityKey(Object object) {
			this.object = object;
			this.hash = System.identityHashCode(object);
		}

		@Override
		public int hashCode() {
			return hash;
		}

		@Override
		public boolean equals(Object other) {
			return other instanceof IdentityKey && ((IdentityKey) other).object == object;
		}
	}
}
