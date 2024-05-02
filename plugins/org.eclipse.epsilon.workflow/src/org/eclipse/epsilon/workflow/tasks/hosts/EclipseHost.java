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
import org.eclipse.debug.core.ILaunch;
import org.eclipse.epsilon.common.dt.console.EolRuntimeExceptionHyperlinkListener;
import org.eclipse.epsilon.common.dt.extensions.ClassBasedExtension;
import org.eclipse.epsilon.common.dt.launching.EclipseExecutionController;
import org.eclipse.epsilon.common.dt.launching.extensions.ModelTypeExtension;
import org.eclipse.epsilon.ecl.IEclModule;
import org.eclipse.epsilon.ecl.debug.EclDebugger;
import org.eclipse.epsilon.egl.IEgxModule;
import org.eclipse.epsilon.egl.debug.EgxDebugger;
import org.eclipse.epsilon.eml.IEmlModule;
import org.eclipse.epsilon.eml.debug.EmlDebugger;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.eol.dt.ExtensionPointToolNativeTypeDelegate;
import org.eclipse.epsilon.eol.dt.debug.EolDebugTarget;
import org.eclipse.epsilon.eol.dt.userinput.JFaceUserInput;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.epl.IEplModule;
import org.eclipse.epsilon.epl.debug.EplDebugger;
import org.eclipse.epsilon.etl.IEtlModule;
import org.eclipse.epsilon.etl.debug.EtlDebugger;
import org.eclipse.epsilon.eunit.EUnitTestListener;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.debug.EvlDebugger;
import org.eclipse.epsilon.pinset.PinsetModule;
import org.eclipse.epsilon.pinset.debug.PinsetDebugger;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IOConsole;

public class EclipseHost implements Host {

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
		final EolDebugger debugger = (EolDebugger) createDebugger(module);

		// HACK: we assume the only running launch is the Ant launch. There's no clear way to
		// tell apart an Ant launch from a regular Run launch, apart from using internal classes
		// in the Eclipse Ant internal API.
		final ILaunch currentLaunch = DebugPlugin.getDefault().getLaunchManager().getLaunches()[0];
		// HACK: we need to remove the Ant source locator so Eclipse can find the source file
		currentLaunch.setSourceLocator(null);

		final EolDebugTarget target = new EolDebugTarget(
			currentLaunch, module, debugger, file.getAbsolutePath());
		debugger.setTarget(target);
		currentLaunch.addDebugTarget(target);
		return target.debug();
	}
	
	private Object createDebugger(IEolModule module) {
		if (module instanceof IEclModule) {
			return new EclDebugger();
		}
		else if (module instanceof IEplModule) {
			return new EplDebugger();
		}
		else if (module instanceof IEmlModule) {
			return new EmlDebugger();
		}
		else if (module instanceof IEtlModule) {
			return new EtlDebugger();
		}
		else if (module instanceof IEvlModule) {
			return new EvlDebugger();
		}
		else if (module instanceof IEgxModule) {
			return new EgxDebugger();
		}
		else if (module instanceof PinsetModule) {
			return new PinsetDebugger();
		}
		else if (module != null) {
			return new EolDebugger();
		}
		else {
			return null;
		}
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

}
