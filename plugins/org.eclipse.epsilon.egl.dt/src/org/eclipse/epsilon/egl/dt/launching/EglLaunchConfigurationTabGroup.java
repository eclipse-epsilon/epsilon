/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.launching;

import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.epsilon.common.dt.launching.tabs.EpsilonLaunchConfigurationTabGroup;
import org.eclipse.epsilon.egl.dt.launching.tabs.EglAdvancedConfigurationTab;
import org.eclipse.epsilon.egl.dt.launching.tabs.EglGeneratedTextConfigurationTab;
import org.eclipse.epsilon.egl.dt.launching.tabs.EglSourceConfigurationTab;

public class EglLaunchConfigurationTabGroup extends EpsilonLaunchConfigurationTabGroup {

	@Override
	public ILaunchConfigurationTab getSourceConfigurationTab() {
		return new EglSourceConfigurationTab();
	}
	
	@Override
	public ILaunchConfigurationTab[] getOtherConfigurationTabs() {
		return new ILaunchConfigurationTab[]{new EglGeneratedTextConfigurationTab()};
	}
	
	@Override
	public ILaunchConfigurationTab getAdvancedConfigurationTab() {
		return new EglAdvancedConfigurationTab();
	}
	
}
