/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecl.dt.launching;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.epsilon.common.dt.launching.extensions.ModuleImplementationExtension;
import org.eclipse.epsilon.ecl.IEclModule;
import org.eclipse.epsilon.ecl.debug.EclDebugger;
import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.eol.dt.launching.EpsilonLaunchConfigurationDelegate;

public class EclLaunchConfigurationDelegate extends EpsilonLaunchConfigurationDelegate {
	
	/**
	 * The language provided by the plugin. It allows other plugins to contribute
	 * alternate IModule implementation of the language.
	 * @since 1.6
	 */
	public String getLanguage() {
		return "ECL";
	}
	
	@Override
	protected EolDebugger createDebugger() {
		return new EclDebugger();
	}


	@Override
	public IEclModule getDefaultModule(ILaunchConfiguration configuration) {
		try {
			return ModuleImplementationExtension.defaultImplementation(getLanguage()).createModule();
		} catch (CoreException e) {
		}
		return null;
	}
	
}
