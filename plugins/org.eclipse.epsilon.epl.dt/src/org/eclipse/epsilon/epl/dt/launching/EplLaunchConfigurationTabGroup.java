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

import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.epsilon.common.dt.launching.tabs.EpsilonLaunchConfigurationTabGroup;
import org.eclipse.epsilon.epl.dt.launching.tabs.EplAdvancedConfigurationTab;
import org.eclipse.epsilon.epl.dt.launching.tabs.EplSourceConfigurationTab;

public class EplLaunchConfigurationTabGroup extends EpsilonLaunchConfigurationTabGroup{

	@Override
	public ILaunchConfigurationTab getSourceConfigurationTab() {
		return new EplSourceConfigurationTab();
	}
	
	@Override
	public ILaunchConfigurationTab getAdvancedConfigurationTab() {
		return new EplAdvancedConfigurationTab();
	}

}
