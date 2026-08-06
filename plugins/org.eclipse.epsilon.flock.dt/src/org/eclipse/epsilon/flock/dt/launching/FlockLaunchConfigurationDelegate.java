/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.dt.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.epsilon.common.dt.console.EpsilonConsole;
import org.eclipse.epsilon.common.dt.launching.extensions.ModuleImplementationExtension;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.eol.dt.launching.EpsilonLaunchConfigurationDelegate;
import org.eclipse.epsilon.eol.exceptions.EolInternalException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.flock.debug.FlockDebugger;
import org.eclipse.epsilon.flock.execute.FlockResult;
import org.eclipse.epsilon.flock.execute.context.IFlockContext;
import org.eclipse.epsilon.flock.execute.exceptions.FlockUnsupportedModelException;

public class FlockLaunchConfigurationDelegate extends EpsilonLaunchConfigurationDelegate {
	
	/**
	 * The language provided by the plugin. It allows other plugins to contribute
	 * alternate IModule implementation of the language.
	 * @since 1.6
	 */
	@Override
	public String getLanguage() {
		return "FLOCK";
	}
	
	@Override
	protected EolDebugger createDebugger() {
		return new FlockDebugger();
	}
	
	@Override
	protected void preExecute(IEolModule module) throws CoreException, EolRuntimeException {
		super.preExecute(module);
		IFlockContext context = (IFlockContext) module.getContext();
		try {
			context.setOriginalModel(configuration.getAttribute(FlockLaunchConfigurationAttributes.ORIGINAL_MODEL, -1));
			context.setMigratedModel(configuration.getAttribute(FlockLaunchConfigurationAttributes.MIGRATED_MODEL, -1));
		}
		catch (FlockUnsupportedModelException ex) {
			throw new EolInternalException(ex);
		}
	}
	
	@Override
	protected void postExecute(IEolModule module) throws CoreException, EolRuntimeException {
		((FlockResult)result).printWarnings(EpsilonConsole.getInstance().getWarningStream());
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

