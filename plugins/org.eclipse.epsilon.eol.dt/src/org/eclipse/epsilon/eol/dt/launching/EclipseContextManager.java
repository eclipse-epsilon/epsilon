/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.dt.launching;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.epsilon.common.dt.console.EpsilonConsole;
import org.eclipse.epsilon.common.dt.launching.EclipseExecutionController;
import org.eclipse.epsilon.common.dt.launching.extensions.ModelTypeExtension;
import org.eclipse.epsilon.common.dt.launching.tabs.ParameterConfiguration;
import org.eclipse.epsilon.common.dt.util.EclipseUtil;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.eol.dt.ExtensionPointToolNativeTypeDelegate;
import org.eclipse.epsilon.eol.dt.uris.PlatformResourceUpURIResolver;
import org.eclipse.epsilon.eol.dt.userinput.JFaceUserInput;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.execute.control.ExecutionController;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;
import org.eclipse.epsilon.eol.execute.prettyprinting.PrettyPrinter;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.ui.PlatformUI;

public class EclipseContextManager {
	
	
	public static void teardown(IEolContext context, IProgressMonitor progressMonitor) {
		context.getModelRepository().dispose();
		context.getExecutorFactory().getExecutionController().dispose();
		try {
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, progressMonitor);
		} catch (CoreException e) {
			LogUtil.log(e);
		}
	}
	
	public static void teardown(IEolContext context) {
		context.getModelRepository().dispose();
		context.getExecutorFactory().getExecutionController().dispose();
		try {
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			LogUtil.log(e);
		}
	}
	
	public static void setup(IEolContext context) {
		loadPrettyPrinters(context);
		loadOperationContributors(context);
		loadIo(context);
		context.getNativeTypeDelegates().add(new ExtensionPointToolNativeTypeDelegate());
		context.getURIResolvers().add(new PlatformResourceUpURIResolver());
	}
	
	public static void setup(IEolContext context, IProgressMonitor progressMonitor) {
		ExecutionController executionController = new EclipseExecutionController(progressMonitor);
		context.getExecutorFactory().setExecutionController(executionController);
		setup(context);
	}
	
	public static void setup(IEolContext context, ILaunchConfiguration configuration, IProgressMonitor progressMonitor, ILaunch launch) throws Exception {
		setup(context, configuration, progressMonitor, launch, true);
	}
	
	public static void setup(IEolContext context, ILaunchConfiguration configuration, IProgressMonitor progressMonitor, ILaunch launch, boolean loadModels) throws Exception {
		loadParameters(context, configuration);
		setup(context, progressMonitor);
		if (loadModels) {
			loadModels(context,configuration,progressMonitor);
		}
		
	}
	
	private static void loadIo(IEolContext context) {
		if (PlatformUI.isWorkbenchRunning()) {
			context.setOutputStream(EpsilonConsole.getInstance().getDebugStream());
			context.setErrorStream(EpsilonConsole.getInstance().getErrorStream());
			context.setWarningStream(EpsilonConsole.getInstance().getWarningStream());
		}
		
		//context.setUserInput(new EpsilonConsoleUserInput());		
		context.setUserInput(new JFaceUserInput(context.getPrettyPrinterManager()));
	}
	
	private static void loadPrettyPrinters(IEolContext context) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = registry.getExtensionPoint("org.eclipse.epsilon.common.dt.prettyPrinter");
		IConfigurationElement[] configurationElements =  extensionPoint.getConfigurationElements();
		for (int i=0;i<configurationElements.length; i++){
			IConfigurationElement configurationElement = configurationElements[i];
			PrettyPrinter prettyPrinter;
			try {
				prettyPrinter = (PrettyPrinter) configurationElement.createExecutableExtension("class");
				context.getPrettyPrinterManager().addPrettyPrinter(prettyPrinter);
			} catch (CoreException e) {
				//PDE.log(e);
			}
		}
	}
	
	/**
	 * Registers all {@link OperationContributor}s contributed through the
	 * <code>org.eclipse.epsilon.common.dt.operationContributor</code> extension
	 * point with the operation contributor registry of the given context.
	 * 
	 * <p>Made public in 2.7 so that headless execution paths (e.g. the
	 * <code>org.eclipse.epsilon.workflow</code> Ant tasks) can reuse the same
	 * discovery logic instead of duplicating it, keeping the two in sync.</p>
	 *
	 * <p>This method is a no-op when no Eclipse extension registry is available.
	 * {@link Platform#getExtensionRegistry()} returns <code>null</code> whenever
	 * there is no running Equinox extension registry service in the current
	 * context &mdash; i.e. when the code is executing outside a live Eclipse
	 * Platform. This is exactly the situation on parts of the headless workflow
	 * path: the EUnit workflow tasks spawn a nested Ant build through the
	 * <code>antRunner</code>, whose E*L tasks run in a plain classloader with no
	 * OSGi/extension-registry service bound, so <code>getExtensionRegistry()</code>
	 * yields <code>null</code>. Before the guard below, dereferencing that
	 * <code>null</code> registry threw
	 * <code>NullPointerException: Cannot invoke
	 * "IExtensionRegistry.getExtensionPoint(String)" because "registry" is null</code>.
	 * This never surfaced previously because the method was only ever reached
	 * from {@link #setup}, which always runs with a live registry.</p>
	 *
	 * <p>Returning early in that case is also the correct semantics: extension-point
	 * contributors can only be declared and discovered inside a live Eclipse
	 * Platform, so when no registry is available there is simply nothing to
	 * contribute, and the headless path must not fail.</p>
	 */
	public static void loadOperationContributors(IEolContext context) {
		// Platform.getExtensionRegistry() is null when there is no live Eclipse
		// extension registry (e.g. E*L tasks run through the nested antRunner in
		// the EUnit workflow tests). Nothing can be contributed in that case, so
		// return early rather than NPE on the null registry.
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		if (registry == null) return;
		IExtensionPoint extensionPoint = registry.getExtensionPoint("org.eclipse.epsilon.common.dt.operationContributor");
		// Defensive: a registry with the extension point undeclared returns null here.
		if (extensionPoint == null) return;
		IConfigurationElement[] configurationElements =  extensionPoint.getConfigurationElements();
		for (int i=0;i<configurationElements.length; i++){
			IConfigurationElement configurationElement = configurationElements[i];
			OperationContributor operationContributor;
			try {
				operationContributor = (OperationContributor) configurationElement.createExecutableExtension("class");
				context.getOperationContributorRegistry().add(operationContributor);
			} catch (CoreException e) {
				//PDE.log(e);
			}
		}
	}
	
	private static void loadParameters(IEolContext context, ILaunchConfiguration configuration) {
		
		List<String> parameters = null;
		
		try {
			parameters = configuration.getAttribute("parameters", new ArrayList<String>());
		} catch (CoreException e) {
			LogUtil.log(e); return;
		}
		
		for (String parameter : parameters) {
			ParameterConfiguration p = new ParameterConfiguration(new StringProperties(parameter));
			context.getFrameStack().putGlobal(new Variable(p.getName(), p.getCastedValue(), p.getEolType()));
		}
		
	}
	
	private static void loadModels(IEolContext context, ILaunchConfiguration configuration, IProgressMonitor progressMonitor) throws Exception {
		String subtask = "Loading models";
		progressMonitor.subTask(subtask);
		progressMonitor.beginTask(subtask, 100);
		
		List<?> models = null;
		
		try {
			models = configuration.getAttribute("models", new ArrayList<String>());
		} catch (CoreException e) {
			LogUtil.log(e); return;
		}

		for (Object mdObj : models) {
			String modelDescriptor = mdObj.toString();
			StringProperties properties = new StringProperties();
			properties.load(modelDescriptor);
			
			IModel model = null;
			
			model = ModelTypeExtension.forType(properties.getProperty("type")).createModel();
			context.getModelRepository().addModel(model);
			
			model.load(properties, relativePath -> {
				try {
					IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(relativePath));
					if (file != null) { 
						return file.getLocation().toOSString(); 
					}
				}
				catch (Exception ex) { LogUtil.log("Error while resolving absolute path for " + relativePath, ex); }
				
				return EclipseUtil.getWorkspacePath() + relativePath;
			});
		}
		
		progressMonitor.done();		
	}
	
}
