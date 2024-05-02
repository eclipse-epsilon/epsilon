/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.epl.dt.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.epsilon.common.dt.launching.extensions.ModuleImplementationExtension;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.eol.dt.launching.EpsilonLaunchConfigurationDelegate;
import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.epsilon.epl.IEplModule;

public class EplLaunchConfigurationDelegate extends EpsilonLaunchConfigurationDelegate {
	
	/**
	 * The language provided by the plugin. It allows other plugins to contribute
	 * alternate IModule implementation of the language.
	 * @since 1.6
	 */
	public String getLanguage() {
		return "EPL";
	}
	
	@Override
	protected EolDebugger createDebugger() {
		return new EplDebugger();
	}
	
	@Override
	public void aboutToExecute(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor progressMonitor,
			IEolModule module) throws Exception {
		super.aboutToExecute(configuration, mode, launch, progressMonitor, module);
		IEplModule eplModule = (IEplModule) module;
		
		eplModule.setMaxLoops(configuration.getAttribute(EplLaunchConfigurationAttributes.MAX_LOOPS, EplModule.INFINITE));
		eplModule.setRepeatWhileMatches(configuration.getAttribute(EplLaunchConfigurationAttributes.REPEAT_WHILE_MATCHES_FOUND, false));
		
	}
	
	@Override
	public void executed(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor progressMonitor,
			IEolModule module, Object result) throws Exception {
		// TODO Auto-generated method stub
		super.executed(configuration, mode, launch, progressMonitor, module, result);
		this.result = null;
	}
	
	@Override
	public IEolModule getDefaultModule(ILaunchConfiguration configuration) {
		try {
			return ModuleImplementationExtension.defaultImplementation(getLanguage()).createModule();
		} catch (CoreException e) {
		}
		return null;
	}
}

