/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.evl.dt.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.epsilon.common.dt.launching.extensions.ModuleImplementationExtension;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dt.launching.EpsilonLaunchConfigurationDelegate;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.debug.EvlDebugger;
import org.eclipse.epsilon.evl.dt.views.ValidationViewFixer;

public class EvlLaunchConfigurationDelegate extends EpsilonLaunchConfigurationDelegate {
	
	
	/**
	 * The language provided by the plugin. It allows other plugins to contribute
	 * alternate IModule implementation of the language.
	 * @since 1.6
	 */
	@Override
	public String getLanguage() {
		return "EVL";
	}

	@Override
	protected EvlDebugger createDebugger() {
		return new EvlDebugger();
	}
	
	@Override
	protected void preExecute(IEolModule module) throws CoreException, EolRuntimeException {
		super.preExecute(module);
		EvlModule evlModule = (EvlModule) module;
		
		evlModule.setUnsatisfiedConstraintFixer(new ValidationViewFixer(configuration));
		evlModule.setOptimizeConstraints(configuration.getAttribute(EvlModule.OPTIMIZE_CONSTRAINTS, false));
	}
	
	@Override
	public IEvlModule getDefaultModule(ILaunchConfiguration configuration) {
		try {
			return ModuleImplementationExtension.defaultImplementation(getLanguage()).createModule();
		} catch (CoreException e) {
		}
		return null;
	}
}
