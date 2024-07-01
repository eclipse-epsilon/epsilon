/*******************************************************************************
 * Copyright (c) 2008-2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dt.launching;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.LaunchConfigurationDelegate;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.epsilon.common.dt.console.EpsilonConsole;
import org.eclipse.epsilon.common.dt.launching.extensions.ModuleImplementationExtension;
import org.eclipse.epsilon.common.dt.launching.tabs.AbstractAdvancedConfigurationTab;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dap.EpsilonDebugServer;
import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.lsp4e.debug.debugmodel.DSPDebugTarget;
import org.eclipse.lsp4e.debug.debugmodel.TransportStreams;
import org.eclipse.lsp4e.debug.launcher.DSPLaunchDelegate;
import org.eclipse.lsp4e.debug.launcher.DSPLaunchDelegate.DSPLaunchDelegateLaunchBuilder;
import org.eclipse.lsp4j.debug.OutputEventArguments;
import org.eclipse.lsp4j.debug.OutputEventArgumentsCategory;

@SuppressWarnings("restriction")
public abstract class EpsilonLaunchConfigurationDelegate extends LaunchConfigurationDelegate implements EpsilonLaunchConfigurationDelegateListener {
	
	protected static class EpsilonDSPLaunchDelegate extends DSPLaunchDelegate {
		@Override
		protected IDebugTarget createDebugTarget(SubMonitor subMonitor,
				Supplier<TransportStreams> streamsSupplier, ILaunch launch,
				Map<String, Object> dspParameters)
				throws CoreException {
			final EpsilonConsoleDebugTarget target = new EpsilonConsoleDebugTarget(launch, streamsSupplier, dspParameters);
			target.initialize(subMonitor.split(80));
			return target;
		}
	}

	protected static class EpsilonConsoleDebugTarget extends DSPDebugTarget {
		protected EpsilonConsoleDebugTarget(ILaunch launch, Supplier<TransportStreams> streamsSupplier, Map<String, Object> dspParameters) {
			super(launch, streamsSupplier, dspParameters);
		}

		@Override
		public void output(OutputEventArguments args) {
			String output = args.getOutput();
			if (args.getCategory() == null || OutputEventArgumentsCategory.CONSOLE.equals(args.getCategory())
					|| OutputEventArgumentsCategory.STDOUT.equals(args.getCategory())) {
				EpsilonConsole.getInstance().getInfoStream().append(output);
			} else if (OutputEventArgumentsCategory.STDERR.equals(args.getCategory())) {
				EpsilonConsole.getInstance().getErrorStream().append(output);
			}
		}
	}

	protected Object result = null;
	protected ILaunchConfiguration configuration = null;
	protected ArrayList<EpsilonLaunchConfigurationDelegateListener> listeners = null;
	
	
	/**
	 * Get the module default module implementation.
	 * @since 1.6
	 */
	public abstract IEolModule getDefaultModule(ILaunchConfiguration configuration);
	
	@Override
	public boolean preLaunchCheck(ILaunchConfiguration configuration, String mode, IProgressMonitor monitor) throws CoreException {
		if (!super.saveBeforeLaunch(configuration, mode, monitor)) {
		    return false;
		}
		return super.preLaunchCheck(configuration, mode, monitor);
	}
	
	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor progressMonitor) throws CoreException {
		this.configuration = configuration;
		launch(configuration, mode, launch, progressMonitor, createModule(), createDebugger(), EolLaunchConfigurationAttributes.SOURCE, true, true);
	}
	
	public boolean launch(
		ILaunchConfiguration configuration,
		String mode,
		ILaunch launch,
		IProgressMonitor progressMonitor,
		IEolModule module,
		EolDebugger debugger,
		String lauchConfigurationSourceAttribute,
		boolean setup,
		boolean disposeModelRepository) throws CoreException {
		
		collectListeners();
		
		if (setup) {
			EpsilonConsole console = EpsilonConsole.getInstance();
			console.clear();
			String outputFile = configuration.getAttribute(IDebugUIConstants.ATTR_CAPTURE_IN_FILE, "");
			if (outputFile.length() > 0) {
				console.enableMirroring(outputFile, configuration.getAttribute(IDebugUIConstants.ATTR_APPEND_TO_FILE, false));
			}
			else {
				console.disableMirroring();
			}
		}
		
		if (!parseModule(configuration, mode, launch, progressMonitor, module, lauchConfigurationSourceAttribute)) {
			return false;
		}
		
		try { 
			setupModule(configuration, launch, progressMonitor, module, setup);

			aboutToExecute(configuration, mode, launch, progressMonitor, module);
			final String subtask = "Executing";
			progressMonitor.subTask(subtask);
			progressMonitor.beginTask(subtask, 100);

			if ("run".equalsIgnoreCase(mode)) {
				runModule(module);
			}
			else if ("debug".equalsIgnoreCase(mode)) {
				debugModule(configuration, mode, launch, progressMonitor, module);
			}

			executed(configuration, mode, launch, progressMonitor, module, result);
		} catch (Exception e) {
			return false;
		}
		finally {
			progressMonitor.done();
			teardown(module.getContext(), disposeModelRepository);
		}
		
		return true;
	}

	protected void setupModule(ILaunchConfiguration configuration, ILaunch launch, IProgressMonitor progressMonitor,
			IEolModule module, boolean loadModels) throws Exception {
		EclipseContextManager.setup(module.getContext(), configuration, progressMonitor, launch, loadModels);
	}

	protected boolean parseModule(ILaunchConfiguration configuration, String mode, ILaunch launch,
			IProgressMonitor progressMonitor, IEolModule module, String lauchConfigurationSourceAttribute)
			throws CoreException {
		aboutToParse(configuration, mode, launch, progressMonitor, module);
		if (!parse(module, lauchConfigurationSourceAttribute, configuration, mode, launch, progressMonitor)) {
			return false;
		}

		return true;
	}

	protected void debugModule(ILaunchConfiguration configuration, String mode, ILaunch launch,
			IProgressMonitor progressMonitor, IEolModule module)
			throws CoreException, InterruptedException, ExecutionException {
		// Copy launch configuration attributes to launch
		for (Map.Entry<?, ?> entry : configuration.getAttributes().entrySet()) {
			launch.setAttribute(entry.getKey() + "", entry.getValue() + "");
		}

		// Start a debug server for the module
		EpsilonDebugServer debugServer = new EpsilonDebugServer(module, 0);
		debugServer.setOnStart(() -> {
			// When the server starts, attach LSP4E to it
			final DSPLaunchDelegateLaunchBuilder builder =
					new DSPLaunchDelegateLaunchBuilder(configuration, mode, launch, progressMonitor);
			builder.setAttachDebugAdapter("127.0.0.1", debugServer.getPort());
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("request", "attach");
			builder.setDspParameters(parameters);
			DSPLaunchDelegate delegate = new EpsilonDSPLaunchDelegate();
			try {
				delegate.launch(builder);
			} catch (CoreException e) {
				LogUtil.log(e);
			}
		});
		debugServer.run();

		result = debugServer.getResult().get();
	}

	protected void runModule(IEolModule module) throws Exception {
		try {
			result = module.execute();
		} catch (Exception e) {
			/*
			 * Debug server already prints out the exception by itself, so we only need to
			 * print it from the "run" mode. The rest of the logic around crashed modules
			 * can stay the same.
			 */
			e = EolRuntimeException.wrap(e);
			e.printStackTrace();
			module.getContext().getErrorStream().println(e.toString());

			throw e;
		}
	}
	
	/**
	 * Create the new module based on the configuration.
	 * @sinv 1.6
	 * @return an ExL module for the specific language being executed
	 * @throws CoreException
	 */
	public IEolModule createModule() throws CoreException {
		String implName = configuration.getAttribute(AbstractAdvancedConfigurationTab.IMPL_NAME, "");
		IEolModule module ;
		if (implName.length() > 0) {
			ModuleImplementationExtension moduleImplementationExtension = ModuleImplementationExtension.forImplementation(getLanguage(), implName);
			if (moduleImplementationExtension != null) {
				module = moduleImplementationExtension.createModule();
				Set<String> requiredProperties = module.getConfigurationProperties();
				Map<String, Object> attr = configuration.getAttributes();
				requiredProperties.stream()
			    	.filter(k -> !attr.containsKey(k))
			    	.forEach(attr::remove);
				module.configure(attr);
				return module;
			}
		}
		
		// Backwards compatibility. For existing configurations or when the implementation name is not found, we will use the default module.
		System.out.println("Configuration does not have specific module implementation information. "
				+ "Falling back to default module.");
		//IEolModule module = ModuleImplementationExtension.defaultImplementation().createModule();
		module = getDefaultModule(configuration);
		if (module == null) {
			IStatus result = new Status(IStatus.ERROR, "org.eclipse.epsilon.eol.dt",
					"There was no default module found for the target language. Since this is defined "
					+ "in the Epsilon plugins it is either a bug or your installation may have been "
					+ "corrupted. Please raise a bug: https://bugs.eclipse.org/bugs/enter_bug.cgi?product=epsilon");
			throw new CoreException(result);
		}
		
		return module;		
	}
	
	protected abstract String getLanguage();

	protected void collectListeners() {
		
		listeners = new ArrayList<>();
		
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = registry.getExtensionPoint("org.eclipse.epsilon.eol.dt.launchConfigurationExtension");
		IConfigurationElement[] configurationElements =  extensionPoint.getConfigurationElements();
		for (int i=0;i<configurationElements.length; i++){
			IConfigurationElement configurationElement = configurationElements[i];
			
			try {
				EpsilonLaunchConfigurationDelegateListener listener = (EpsilonLaunchConfigurationDelegateListener) configurationElement.createExecutableExtension("listener");
				listeners.add(listener);
			}
			catch (CoreException e) {
				LogUtil.log(e);
			}
		}
	}
	
	@Override
	public void aboutToParse(ILaunchConfiguration configuration, String mode, 
			ILaunch launch, IProgressMonitor progressMonitor,
			IEolModule module) throws CoreException {
		
		preParse(module);
		for (EpsilonLaunchConfigurationDelegateListener listener : listeners) {
			listener.aboutToParse(configuration, mode, launch, progressMonitor, module);
		}
	}
	
	@Override
	public void aboutToExecute(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor progressMonitor,
			IEolModule module) throws Exception {
		
		preExecute(module);
		for (EpsilonLaunchConfigurationDelegateListener listener : listeners) {
			listener.aboutToExecute(configuration, mode, launch, progressMonitor, module);
		}
	}
	
	@Override
	public void executed(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor progressMonitor,
			IEolModule module, Object result) throws Exception {
		
		postExecute(module);
		for (EpsilonLaunchConfigurationDelegateListener listener : listeners) {
			listener.executed(configuration, mode, launch, progressMonitor, module, result);
		}
	}
	
	protected void preParse(IEolModule module) {}
	
	protected void preExecute(IEolModule module) throws CoreException, EolRuntimeException {}
	
	protected void postExecute(IEolModule module) throws CoreException, EolRuntimeException {}
	
	protected EolDebugger createDebugger() {
		return new EolDebugger();
	}
	
	//TODO: Report parse errors better
	protected boolean parse(IModule module, String sourceAttribute, ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor progressMonitor) throws CoreException {
		String subTask = "";
		
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(configuration.getAttribute(sourceAttribute, "")));
		String fileName = file.getRawLocation().toOSString();
		
		subTask = "Parsing " + fileName;
		progressMonitor.subTask(subTask);
		progressMonitor.beginTask(subTask, 100);
		
		boolean parsed = false;
		
		try {
			parsed = module.parse(new File(fileName));
		} catch (Exception e) {
			e.printStackTrace(EpsilonConsole.getInstance().getErrorStream());
			return false;
		}
		
		progressMonitor.done();
		
		if (!parsed){
			for (ParseProblem problem : module.getParseProblems()) {
				EpsilonConsole.getInstance().getErrorStream().println(problem.toString());
			}
		}

		return parsed;
		
	}
	
	public void teardown(IEolContext context, boolean disposeModelRepository) {
		if (disposeModelRepository) context.getModelRepository().dispose();
		context.getExecutorFactory().getExecutionController().dispose();
		try {
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			LogUtil.log(e);
		}
	}
	
}
