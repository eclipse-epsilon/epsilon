/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.tools.ant.BuildException;
import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EglModule;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.EgxModule;
import org.eclipse.epsilon.egl.IEglModule;
import org.eclipse.epsilon.egl.IEgxModule;
import org.eclipse.epsilon.egl.concurrent.EgxModuleParallel;
import org.eclipse.epsilon.egl.engine.traceability.fine.EglFineGrainedTraceContextAdaptor;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.ModelLocation;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Region;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.TextLocation;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Trace;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.TraceLink;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.execute.context.EgxContext;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.execute.context.concurrent.EgxContextParallel;
import org.eclipse.epsilon.egl.formatter.Formatter;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.workflow.tasks.nestedelements.EglDefaultFormatterNestedElement;

public class EglTask extends ExportableModuleTask {
	
	protected String target;
	protected Class<? extends EglTemplateFactory> templateFactoryType = EglFileGeneratingTemplateFactory.class;
	protected List<EglDefaultFormatterNestedElement> defaultFormatterNestedElements = new LinkedList<>();
	protected Trace trace;
	protected File outputRoot;
	
	@Override
	protected IEolModule createDefaultModule() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		final IEolModule module; 
		final EglTemplateFactory templateFactory = createDefaultTemplateFactory();
		
		if (templateFactory instanceof EglFileGeneratingTemplateFactory && outputRoot != null) {
			try {
				((EglFileGeneratingTemplateFactory) templateFactory).setOutputRoot(outputRoot.getAbsolutePath());
			} catch (EglRuntimeException e) {
				throw new RuntimeException(e);
			}
		}
		
		templateFactory.setDefaultFormatters(instantiateDefaultFormatters());
		
		if (src != null && src.getName().endsWith("egx")) {
			module = new EgxModule(new EgxContext(templateFactory));
		}
		else {		
			module = new EglModule(templateFactory);
		}
		
		// Turn on fine-grained traceability, and
		// obtain a reference to the trace so that we can export it later
		if (shouldExportAsModel()) {
			trace = new EglFineGrainedTraceContextAdaptor().adapt((IEglContext) module.getContext());
		}
		
		return module;
	}
	
	protected EglTemplateFactory createDefaultTemplateFactory() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return templateFactoryType.getDeclaredConstructor().newInstance();
	}
	
	@Override
	protected IEolModule createAlternativeModule() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		@SuppressWarnings("unchecked")
		Class<IEolModule> clazz = (Class<IEolModule>) Class.forName(moduleImplementationClass);
		IEolModule module = clazz.getDeclaredConstructor().newInstance();
		final EglTemplateFactory templateFactory = templateFactoryType.getDeclaredConstructor().newInstance();
		if (templateFactory instanceof EglFileGeneratingTemplateFactory && outputRoot != null) {
			try {
				((EglFileGeneratingTemplateFactory) templateFactory).setOutputRoot(outputRoot.getAbsolutePath());
			}
			catch (EglRuntimeException e) {
				throw new RuntimeException(e);
			}
		}
		
		templateFactory.setDefaultFormatters(instantiateDefaultFormatters());
		
		if (module instanceof IEgxModule) {
			if (module instanceof EgxModuleParallel) {
				module.setContext(new EgxContextParallel(templateFactory));
			}
			else {
				module.setContext(new EgxContext(templateFactory));
			}
		}
		else {		
			((IEglModule) module).setTemplateFactory(templateFactory);
		}
		
		// Turn on fine-grained traceability, and
		// obtain a reference to the trace so that we can export it later
		if (shouldExportAsModel()) {
			trace = new EglFineGrainedTraceContextAdaptor().adapt((IEglContext) module.getContext());
		}
		Set<String> requiredProperties = module.getConfigurationProperties();
		Map<String, Object> props = new HashMap<>(requiredProperties.size());
		for (String rp : requiredProperties) {
			for (ModuleProperty mp : properties) {
				if (rp.equals(mp.name)) {
					props.put(mp.name, mp.value);
				}
			}
		}
		module.configure(props);
		return module;
	}
	

	private List<Formatter> instantiateDefaultFormatters() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		final List<Formatter> defaultFormatters = new LinkedList<>();
		
		for (EglDefaultFormatterNestedElement defaultFormatterNestedElement : defaultFormatterNestedElements) {
			defaultFormatters.add(defaultFormatterNestedElement.getImplementation().getDeclaredConstructor().newInstance());
		}
		
		return defaultFormatters;
	}

	@Override
	protected void examine() throws Exception {
		super.examine();
		
		if (target == null) return;
		
		File baseDir = getProject().getBaseDir();
		
		if (outputRoot != null) {
			baseDir = outputRoot;
		}

		try (FileWriter fw = new FileWriter(new File(baseDir, target))) {
			fw.write(String.valueOf(result));
		}
	}

	@Override
	protected void initialize() throws Exception {}

	public String getTarget() {
		return target;
	}

	public void setTarget(String output) {
		this.target = output;
	}
	
	public File getOutputRoot() {
		return outputRoot;
	}
	
	public void setOutputRoot(File outputRoot) {
		this.outputRoot = outputRoot;
	}
	
	public Class<? extends EglTemplateFactory> getTemplateFactoryType() {
		return templateFactoryType;
	}
	
	public void setTemplateFactoryType(Class<? extends EglTemplateFactory> templateFactoryType) {
		if (EglTemplateFactory.class.isAssignableFrom(templateFactoryType)) {
			this.templateFactoryType = templateFactoryType;
		}
		else {
			throw new BuildException("The templateFactoryType parameter must be class that subtypes "+EglTemplateFactory.class.getName());
		}
	}
	
	public EglDefaultFormatterNestedElement createDefaultFormatter() {
		final EglDefaultFormatterNestedElement nestedElement = new EglDefaultFormatterNestedElement();
		defaultFormatterNestedElements.add(nestedElement);
		return nestedElement;
	}
	
	@Override
	protected Collection<? extends Class<?>> getClassesForExportedModel() {
		return Arrays.asList(Trace.class, TraceLink.class, TextLocation.class, ModelLocation.class, Region.class);
	}
	
	@Override
	protected Collection<? extends Object> getObjectsForExportedModel() {
		return trace.getAllContents();
	}
}
