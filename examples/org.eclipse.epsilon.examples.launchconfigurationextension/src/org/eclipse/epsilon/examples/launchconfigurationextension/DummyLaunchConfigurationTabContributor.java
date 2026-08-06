/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.examples.launchconfigurationextension;

import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.epsilon.common.dt.launching.tabs.EpsilonLaunchConfigurationTabContributor;
import org.eclipse.epsilon.common.dt.launching.tabs.EpsilonLaunchConfigurationTabGroup;

public class DummyLaunchConfigurationTabContributor implements EpsilonLaunchConfigurationTabContributor {

	@Override
	public ILaunchConfigurationTab[] getTabs(
			EpsilonLaunchConfigurationTabGroup tabGroup,
			ILaunchConfigurationDialog dialog, String mode) {
		return new ILaunchConfigurationTab[]{new DummyLaunchConfigurationTab()};
	}

}
