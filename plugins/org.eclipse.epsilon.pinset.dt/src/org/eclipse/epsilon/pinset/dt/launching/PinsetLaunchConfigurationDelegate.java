/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package org.eclipse.epsilon.pinset.dt.launching;

import static org.eclipse.epsilon.pinset.dt.launching.tabs.PinsetSourceConfigurationTab.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.epsilon.common.dt.launching.extensions.ModuleImplementationExtension;
import org.eclipse.epsilon.common.dt.util.EclipseUtil;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.eol.dt.launching.EpsilonLaunchConfigurationDelegate;
import org.eclipse.epsilon.pinset.PinsetModule;


/**
 * PinsetLaunchConfigurationDelegate.
 *
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class PinsetLaunchConfigurationDelegate extends EpsilonLaunchConfigurationDelegate {

	@Override
	public IEolModule createModule() {
		PinsetModule module = new PinsetModule();
		if (configuration != null) {
			try {
				if (configuration.getAttribute(GENERATE_TO, GENERATE_TO_DEFAULT_FOLDER) == GENERATE_TO_CUSTOM_FOLDER) {
					module.setOutputFolder(EclipseUtil.getWorkspaceContainerAbsolutePath(
							configuration.getAttribute(OUTPUT_FOLDER, "")));
				}
			}
			catch (CoreException e) {
				e.printStackTrace();
			}
		}
		return module;
	}

	@Override
	protected EolDebugger createDebugger() {
		return new PinsetDebugger();
	}

	@Override
	public String getLanguage() {
		return "Pinset";
	}

	@Override
	public PinsetModule getDefaultModule(ILaunchConfiguration configuration) {
		try {
			return ModuleImplementationExtension.defaultImplementation(getLanguage()).createModule();
		}
		catch (CoreException e) {
		}
		return null;
	}

}
