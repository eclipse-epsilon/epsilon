/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.hosts;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.eclipse.ant.core.AntCorePlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.epsilon.common.dt.console.EolRuntimeExceptionHyperlinkListener;
import org.eclipse.epsilon.common.dt.extensions.ClassBasedExtension;
import org.eclipse.epsilon.common.dt.launching.EclipseExecutionController;
import org.eclipse.epsilon.common.dt.launching.extensions.ModelTypeExtension;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dap.EpsilonDebugServer;
import org.eclipse.epsilon.eol.dt.ExtensionPointToolNativeTypeDelegate;
import org.eclipse.epsilon.eol.dt.userinput.JFaceUserInput;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eunit.EUnitTestListener;
import org.eclipse.lsp4e.debug.DSPPlugin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IOConsole;

@SuppressWarnings("restriction")
public class EclipseHost implements Host {

	private static final String ANT_DEBUG_LSP4E = "org.eclipse.epsilon.workflow.debug.ant";
	private static final String LSP4E_LAUNCH_TYPE = "org.eclipse.epsilon.eol.dt.debug.RemoteEpsilonLaunchConfigurationDelegate";

	protected static class RemoteDebugOnStart implements Runnable {
		private ILaunchManager manager;
		private ILaunchConfigurationType type;
		private EpsilonDebugServer server;
		private ILaunchConfiguration config;

		public RemoteDebugOnStart(ILaunchManager manager, ILaunchConfigurationType type, EpsilonDebugServer server) {
			this.manager = manager;
			this.type = type;
			this.server = server;
		}

		@Override
		public void run() {
			try {
				ILaunchConfiguration[] configs = manager.getLaunchConfigurations(type);
				for (ILaunchConfiguration config : configs) {
					if (ANT_DEBUG_LSP4E.equals(config.getName())) {
						config.delete();
						break;
					}
				}
				ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(null, ANT_DEBUG_LSP4E);

				workingCopy.setAttribute(DSPPlugin.ATTR_DSP_SERVER_HOST, "localhost");
				workingCopy.setAttribute(DSPPlugin.ATTR_DSP_SERVER_PORT, server.getPort());
				workingCopy.setAttribute(DSPPlugin.ATTR_DSP_MODE, DSPPlugin.DSP_MODE_CONNECT);
				workingCopy.setAttribute(DSPPlugin.ATTR_DSP_PARAM, "{\"request\": \"attach\"}");

				config = workingCopy.doSave();
				Display.getDefault().asyncExec(() -> {
					// DebugUITools.launch must be called in the UI thread
					DebugUITools.launch(config, ILaunchManager.DEBUG_MODE);
				});
			} catch (CoreException e) {
				e.printStackTrace();
				LogUtil.log(e);
			}
		}

		public void deleteLaunchConfiguration() throws CoreException {
			if (config != null) {
				config.delete();
				config = null;
			}
		}
	}

	private Integer debugPort;

	@Override
	public boolean isRunning() {
		try {
			Class.forName("org.eclipse.epsilon.eol.dt.ExtensionPointToolNativeTypeDelegate");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	@Override
	public void addNativeTypeDelegates(IEolModule module) {
		module.getContext().getNativeTypeDelegates().add(new ExtensionPointToolNativeTypeDelegate());
	}

	@Override
	public void addStopCapabilities(Project project, IEolModule module) {
		// Allow the user to stop any E*L task through the stop button in the console
		IProgressMonitor monitor =
			(IProgressMonitor) project.getReferences().get(AntCorePlugin.ECLIPSE_PROGRESS_MONITOR);
		if (monitor != null) {
			module.getContext().getExecutorFactory().setExecutionController(new EclipseExecutionController(monitor));
		}
	}

	@Override
	public void initialise() {
		if (ConsolePlugin.getDefault() != null) {
			for (IConsole c : ConsolePlugin.getDefault().getConsoleManager().getConsoles()) {
				if (c instanceof IOConsole) {
					IOConsole ioConsole = ((org.eclipse.ui.console.IOConsole) c);
					ioConsole.addPatternMatchListener(new EolRuntimeExceptionHyperlinkListener(ioConsole));
				}
			}
		}
	}
	
	@Override
	public Object debug(IEolModule module, File file) throws Exception {
		// Set up and launch an LSP4E launch configuration to connect to the server
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(LSP4E_LAUNCH_TYPE);
		if (type == null) {
			throw new RuntimeException("Could not find the LSP4E launch configuration type: " + LSP4E_LAUNCH_TYPE);
		}

		final int port = debugPort == null ? 0 : debugPort;
		final EpsilonDebugServer server = new EpsilonDebugServer(module, port);
		final RemoteDebugOnStart onStart = new RemoteDebugOnStart(manager, type, server);
		server.setOnStart(onStart);

		Thread serverThread = new Thread(server);
		Object result = null;
		try {
			serverThread.start();

			// Wait for the program to execute
			result = server.getResult().get();
		} finally {
			// Delete the launch configuration as soon as execution is completed
			onStart.deleteLaunchConfiguration();
		}

		// Wait for the server to shutdown
		serverThread.join();
		return result;
	}

	@Override
	public boolean supportsDebugging() {
		return true;
	}

	@Override
	public void configureUserInput(IEolModule module, boolean isGui) {
		if (isGui) {
			module.getContext().setUserInput(new JFaceUserInput(module.getContext().getPrettyPrinterManager()));
		}
	}
	
	@Override
	public IModel createModel(String type) throws BuildException {
		try {
			IModel model = ModelTypeExtension.forType(type).createModel();
			return model;
		} catch (CoreException e) {
			throw new BuildException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> getExtensionsOfType(Class<T> klazz) throws Exception {
		String pointID = null;
		if (klazz == EUnitTestListener.class) {
			pointID = EUnitTestListener.EXTENSION_POINT_ID;
		}

		if (pointID != null) {
			return ClassBasedExtension.getImplementations(pointID, klazz);
		} else {
			return Collections.EMPTY_LIST;
		}
	}

	@Override
	public void setDebugPort(int port) {
		this.debugPort = port;
	}

}
