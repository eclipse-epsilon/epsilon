/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package org.eclipse.epsilon.pinset.dt.launching.tabs;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.epsilon.common.dt.EpsilonPlugin;
import org.eclipse.epsilon.common.dt.launching.tabs.AbstractAdvancedConfigurationTab;
import org.eclipse.epsilon.pinset.dt.PinsetPlugin;

/**
 * PinsetAdvancedConfigurationTab.
 *
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class PinsetAdvancedConfigurationTab extends AbstractAdvancedConfigurationTab {

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
	}

	@Override
	public EpsilonPlugin getPlugin() {
		return PinsetPlugin.getDefault();
	}

	@Override
	public String getLanguage(ILaunchConfiguration configuration) {
		return getLanguage();
	}

	public String getLanguage() {
		return "Pinset";
	}
}
