/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eml.dt.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.epsilon.common.dt.launching.extensions.ModuleImplementationExtension;
import org.eclipse.epsilon.ecl.EclModule;
import org.eclipse.epsilon.ecl.debug.EclDebugger;
import org.eclipse.epsilon.ecl.dt.launching.EclLaunchConfigurationDelegate;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.eml.EmlModule;
import org.eclipse.epsilon.eml.debug.EmlDebugger;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dt.launching.EolLaunchConfigurationAttributes;
import org.eclipse.epsilon.eol.dt.launching.EpsilonLaunchConfigurationDelegate;
import org.eclipse.epsilon.eol.models.ModelRepository;

public class EmlLaunchConfigurationDelegate extends EpsilonLaunchConfigurationDelegate {

	MatchTrace matchTrace = null;
	ModelRepository modelRepository = null;
	
	/**
	 * The language provided by the plugin. It allows other plugins to contribute
	 * alternate IModule implementation of the language.
	 * @since 1.6
	 */
	public String getLanguage() {
		return "EML";
	}
	
	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor progressMonitor)
			throws CoreException {
		
		EclLaunchConfigurationDelegate eclLaunchConfigurationDelegate = new EclLaunchConfigurationDelegate() {
			@Override
			protected void postExecute(IEolModule module) {
				if (module instanceof EclModule) {
					matchTrace = ((EclModule) module).getContext().getMatchTrace().getReduced();
					modelRepository = ((EclModule) module).getContext().getModelRepository();
				}
			}
		};
		
		if (!eclLaunchConfigurationDelegate.launch(configuration, mode, launch, 
				progressMonitor, new EclModule(), new EclDebugger(),  EmlLaunchConfigurationAttributes.ECL_SOURCE, true, false))
			return;
	
		super.launch(configuration, mode, launch, 
				progressMonitor, new EmlModule(), new EmlDebugger(), EolLaunchConfigurationAttributes.SOURCE, false, true);
		
	}
	
	@Override
	protected void preExecute(IEolModule module) {
		if (module instanceof EmlModule) {
			((EmlModule) module).getContext().setMatchTrace(matchTrace);
			((EmlModule) module).getContext().setModelRepository(modelRepository);
		}
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
