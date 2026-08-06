/*******************************************************************************
 * Copyright (c) 2016 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Saheed Popoola - initial API and implementation
 *     Horacio Hoyos - aditional functionality
 ******************************************************************************/
package org.eclipse.epsilon.emg.dt.launching;

import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.epsilon.emg.dt.launching.tabs.EmgSourceConfigurationTab;
import org.eclipse.epsilon.epl.dt.launching.EplLaunchConfigurationTabGroup;

public class EmgLaunchConfigurationTabGroup extends
		EplLaunchConfigurationTabGroup {
	
	@Override
	public ILaunchConfigurationTab getSourceConfigurationTab() {
		return new EmgSourceConfigurationTab();
	}
	
//	@Override
//	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
//		
//		ArrayList<ILaunchConfigurationTab> tabList = new ArrayList<ILaunchConfigurationTab>();
//		for (ILaunchConfigurationTab tab : getSourceConfigurationTabs()) {
//			tabList.add(tab);
//		}
//		
//		tabList.add(new ParametersConfigurationTab());
//		
//		for (ILaunchConfigurationTab tab : getOtherConfigurationTabs()) {
//			tabList.add(tab);
//		}
//		
//		IExtensionRegistry registry = Platform.getExtensionRegistry();
//		IExtensionPoint extensionPoint = registry.getExtensionPoint("org.eclipse.epsilon.eol.dt.launchConfigurationExtension");
//
//		for (IConfigurationElement configurationElement : extensionPoint.getConfigurationElements()) {
//			try {
//				EpsilonLaunchConfigurationTabContributor contributor = (EpsilonLaunchConfigurationTabContributor) configurationElement.createExecutableExtension("tabContributor");
//				for(ILaunchConfigurationTab tab : contributor.getTabs(this, dialog, mode)) {
//					tabList.add(tab);
//				}
//			}
//			catch (CoreException e) {
//				LogUtil.log(e);
//			}
//		}
//		tabList.add(new CommonTab());
//		
//		setTabs(tabList);
//	}
}
