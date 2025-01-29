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
import org.eclipse.epsilon.eol.dt.userinput.JFaceUserInput;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
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
	
	private static void loadOperationContributors(IEolContext context) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = registry.getExtensionPoint("org.eclipse.epsilon.common.dt.operationContributor");
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
