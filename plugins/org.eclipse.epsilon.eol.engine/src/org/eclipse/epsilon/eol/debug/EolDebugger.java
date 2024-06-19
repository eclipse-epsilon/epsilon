/*********************************************************************
 * Copyright (c) 2012-2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.debug;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.Region;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.Import;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.dom.Statement;
import org.eclipse.epsilon.eol.dom.StatementBlock;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class EolDebugger implements IEolDebugger {

	protected class EolModuleThread implements IEolThread {
		private final IEolModule module;
		private final int id;

		protected EolModuleThread(int id, IEolModule module) {
			this.id = id;
			this.module = module;
		}

		@Override
		public int getId() {
			return id;
		}

		@Override
		public String getName() {
			return String.format("%s - %s", module.getClass(), module.getSourceUri().toString());
		}

		@Override
		public FrameStack getFrameStack() {
			return module.getContext().getFrameStack();
		}
	}

	private static final Logger LOGGER = Logger.getLogger(EolDebugger.class.getName());

	/**
	 * Lazily-initialized map from module URI to the actual IModule.
	 *
	 * Do not access this field directly: instead, use {@link #getURIToModuleMap()}.
	 */
	private Map<String, IModule> uriToModule;

	private IEpsilonDebugTarget target = null;

	protected boolean stepping = false;
	protected ModuleElement currentModuleElement;
	protected ModuleElement stopAfterModuleElement;
	protected Integer stopAfterFrameStackSizeDropsBelow;

	protected int nextThreadId = 1;
	protected Map<IEolModule, IEolThread> threads = new LinkedHashMap<>();

	@Override
	public boolean isTerminated() {
		return target.isTerminated();
	}

	@Override
	public void dispose() {
		target = null;
	}

	@Override
	public void setTarget(IEpsilonDebugTarget target) {
		this.target = target;
	}
	
	@Override
	public IEpsilonDebugTarget getTarget() {
		return target;
	}

	protected IEolModule getModule() {
		return target.getModule();
	}

	@Override
	public void control(ModuleElement ast, IEolContext context) {
		if (ast.getParent() == null && ast instanceof IEolModule) {
			/*
			 * Keep track of all threads - we may have one module invoking another module
			 * (e.g. EGX invoking EGL). Each module has its own frame stack, therefore is
			 * its own thread.
			 */
			IEolModule module = (IEolModule) ast;
			IEolThread moduleThread = new EolModuleThread(nextThreadId++, module);
			threads.put(module, moduleThread);
		}

		if (!controls(ast, context)) return;
		currentModuleElement = ast;
	
		try {
			if (stepping) {
				stepping = false;
				target.suspend(ast, SuspendReason.STEP);
			} else if (hasBreakpoint(ast)) {
				target.suspend(ast, SuspendReason.BREAKPOINT);
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	
		if (isTerminated()) return;
	}

	@Override
	public void done(ModuleElement ast, IEolContext context) {
		if (ast.getParent() == null && ast instanceof IEolModule) {
			IEolModule module = (IEolModule) ast;
			threads.remove(module);
		}

		if (stopAfterModuleElement != null && ast == stopAfterModuleElement) {
			stepping = true;
			stopAfterModuleElement = null;
		}
		if (stopAfterFrameStackSizeDropsBelow != null && frameStackSize() < stopAfterFrameStackSizeDropsBelow) {
			stepping = true;
			stopAfterFrameStackSizeDropsBelow = null;
		}
	}

	@Override
	public void report(IEolContext context) {
		// nothing to do
	}

	@Override
	public void step() {
		stepping = true;
	}

	@Override
	public void stepOver() {
		stopAfterModuleElement = currentModuleElement;
	}

	@Override
	public void stepReturn() {
		stopAfterFrameStackSizeDropsBelow = frameStackSize();
	}

	private boolean controls(ModuleElement ast, IEolContext context) {
		// Top level element or block
		if (ast.getParent() == null || ast instanceof StatementBlock) return false;
		return isStatement(ast) || isContainedExpression(ast);
	}

	private int frameStackSize() {
		return getModule().getContext().getFrameStack().getFrames().size();
	}

	private ModuleElement getGrandparent(ModuleElement ast) {
		return getParent(getParent(ast));
	}

	private ModuleElement getParent(ModuleElement ast) {
		return ast != null ? ast.getParent() : null;
	}

	private boolean hasBreakpoint(ModuleElement ast) {
		if (target.hasBreakpointItself(ast)) {
			return true;
		}
		
		if (isFirstStatement(ast)) {
			return hasBreakpoint(getGrandparent(ast));
		}
		else if (isContainedExpression(ast)) {
			return hasBreakpoint(getParent(ast));
		}
		
		if (isStructuralBlock(getParent(ast))) {
			if (isExpressionOrStatementBlockContainer(ast)) {
				return hasBreakpoint(getParent(ast));
			}
			else if (isStructuralBlock(ast)) {
				return hasBreakpoint(getParent(ast));
			}
		}
		
		return false;
	}

	protected boolean isExpressionOrStatementBlockContainer(ModuleElement ast) {
		if (ast == null) return false;
		return ast instanceof Operation || ast instanceof ExecutableBlock<?>;
	}

	protected boolean isStructuralBlock(ModuleElement ast) {
		return false;
	}

	private boolean isContainedExpression(ModuleElement ast) {
		ModuleElement parent = getParent(ast);
		if (parent == null) return false;
		return isExpressionOrStatementBlockContainer(parent) && parent.getChildren().size() == 1;
	}

	private boolean isFirstStatement(ModuleElement ast) {
		ModuleElement parent = getParent(ast);
		if (parent == null) return false;
		if (!(parent instanceof StatementBlock)) return false;
		ModuleElement grandparent = getParent(parent);
		if (!isExpressionOrStatementBlockContainer(grandparent)) return false;
		return parent.getChildren().get(0) == ast;
	}

	private boolean isStatement(ModuleElement ast) {
		return ast instanceof Statement;
	}

	@Override
	public boolean isStepping() {
		return stepping;
	}

	@Override
	public ModuleElement getStopAfterModuleElement() {
		return stopAfterModuleElement;
	}

	@Override
	public Integer getStopAfterFrameStackSizeDropsBelow() {
		return stopAfterFrameStackSizeDropsBelow;
	}

	@Override
	public BreakpointResult verifyBreakpoint(BreakpointRequest request) {
		try {
			// Step 1. Map from request path (likely a file opened in the IDE) to an IEolModule
			IModule resolvedModule = resolveModule(request.getUriToPathMappings(), request.getPath());

			if (resolvedModule != null) {
				// Step 2. Find the first line with actual code from the requested line
				final int actualLine = findFirstLineGreaterThanOrEqualTo(resolvedModule, request.getLine());
				if (actualLine >= 1) {
					return BreakpointResult.verified(request, resolvedModule, actualLine);
				}
			}
		} catch (IOException e) {
			LOGGER.log(Level.WARNING, "Failed to verify breakpoint due to error: " + e.getMessage(), e);
		}

		return BreakpointResult.failed(request);
	}

	/**
	 * Tries to resolve the specific module referenced by a path reported via DAP.
	 * This will be a file in the developer's IDE, whose URI may not match 1:1 with
	 * the URI of the module being executed (e.g. if the module is being loaded from
	 * the classpath and not from the filesystem).
	 */
	protected IModule resolveModule(final Map<URI, Path> uriToPathMappings, final String argsSourcePath) throws IOException {
		// Stop if we don't actually have a path
		if (argsSourcePath == null) {
			return null;
		}

		// Stop if the DAP path does not resolve to a file in our filesystem
		File argsSourceFile = new File(argsSourcePath);
		if (!argsSourceFile.exists()) {
			return null;
		}

		// Collect all the imports
		Map<String, IModule> uriToModule = getURIToModuleMap();

		// Try to use a direct file: URI first
		final String argsSourceFileURI = argsSourceFile.toURI().toString();
		IModule resolvedModule = uriToModule.get(argsSourceFileURI);
		if (resolvedModule != null) {
			return resolvedModule;
		}

		// Try to use the URI-to-path mappings in reverse
		for (Entry<URI, Path> e : uriToPathMappings.entrySet()) {
			final String baseUri = e.getKey().toString();
			final File canonicalFile = e.getValue().toFile().getCanonicalFile();
			final String basePath = canonicalFile.getPath() + (canonicalFile.isDirectory() ? File.separator : "");

			if (argsSourcePath.startsWith(basePath)) {
				String mappedUri = baseUri + argsSourcePath.substring(basePath.length());
				resolvedModule = uriToModule.get(mappedUri);
				if (resolvedModule != null) {
					return resolvedModule;
				}
			}
		}

		return null;
	}

	protected synchronized Map<String, IModule> getURIToModuleMap() throws IOException {
		if (this.uriToModule == null) {
			this.uriToModule = new HashMap<>();
			addAllModules(uriToModule, getModule());
		}
		return uriToModule;
	}

	protected void addAllModules(Map<String, IModule> pathToModule, IModule module) throws IOException {
		String moduleURI = module.getFile() != null
			? module.getFile().getCanonicalFile().toURI().toString() : module.getUri().toString();

		if (!pathToModule.containsKey(moduleURI)) {
			pathToModule.put(moduleURI, module);
			if (module instanceof IEolModule) {
				for (Import imp : ((IEolModule) module).getImports()) {
					addAllModules(pathToModule, imp.getModule());
				}
			}
		}
	}

	/**
	 * Finds the first line greater than or equal to the given line that actually
	 * has a module element in it. This handles cases where we set a breakpoint
	 * on an empty line: it will instead break on the first non-empty line after it.
	 */
	protected int findFirstLineGreaterThanOrEqualTo(ModuleElement root, int localLine) {
		// This is a leaf node: either we find the line or we don't
		if (root.getChildren().isEmpty()) {
			final Region region = root.getRegion();
			final int regionStartLine = region.getStart().getLine();
			final int regionEndLine = region.getEnd().getLine();
			if (regionEndLine >= localLine) {
				// Region may start later than the line we set the breakpoint on
				return Math.max(localLine, regionStartLine);
			} else {
				// Region does not include the line
				return -1;
			}
		}

		/*
		 * Not a leaf node: find earliest child whose region spans the line or something
		 * after the line. We scan over all elements, because the children of a module
		 * element do not have to be in the same order as they appear on the file.
		 * 
		 * This is the case, for instance, for the operations that appear below a bit of
		 * top-level code, which will appear before that top-level code in the list of
		 * children.
		 */
		ModuleElement earliest = null;
		int earliestLine = Integer.MAX_VALUE;
		for (ModuleElement child : root.getChildren()) {
			final Region childRegion = child.getRegion();
			final int startLine = childRegion.getStart().getLine();
			final int endLine = childRegion.getEnd().getLine();
			if (startLine < earliestLine && endLine >= localLine) {
				earliest = child;
				earliestLine = startLine;
			}
		}

		if (earliest != null) {
			int earliestStart = earliest.getRegion().getStart().getLine();
			if (earliestStart >= localLine) {
				// We've already hit a line that is greater than or equal to localLine
				return earliestStart;
			} else {
				/*
				 * We haven't crossed the target line yet, but we know the module element spans
				 * over this line, so we go over its children.
				 */
				return findFirstLineGreaterThanOrEqualTo(earliest, localLine);
			}
		}

		return -1;
	}

	@Override
	public Collection<IEolThread> getThreads() {
		return Collections.unmodifiableCollection(threads.values());
	}
	
}
