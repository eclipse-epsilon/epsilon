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

import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.epsilon.common.dt.launching.tabs.EpsilonLaunchConfigurationTabGroup;
import org.eclipse.epsilon.pinset.dt.launching.tabs.PinsetAdvancedConfigurationTab;
import org.eclipse.epsilon.pinset.dt.launching.tabs.PinsetSourceConfigurationTab;

/**
 * PinsetLaunchConfigurationTabGroup.
 *
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class PinsetLaunchConfigurationTabGroup extends EpsilonLaunchConfigurationTabGroup {

	@Override
	public ILaunchConfigurationTab getSourceConfigurationTab() {
		return new PinsetSourceConfigurationTab();
	}

	@Override
	public ILaunchConfigurationTab[] getOtherConfigurationTabs() {
		return new ILaunchConfigurationTab[] {};
	}

	@Override
	public ILaunchConfigurationTab getAdvancedConfigurationTab() {
		return new PinsetAdvancedConfigurationTab();
	}

}
