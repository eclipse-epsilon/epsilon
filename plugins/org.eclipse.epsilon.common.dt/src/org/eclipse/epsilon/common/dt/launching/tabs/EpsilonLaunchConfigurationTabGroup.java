/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.launching.tabs;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.epsilon.common.dt.util.LogUtil;

public abstract class EpsilonLaunchConfigurationTabGroup extends AbstractLaunchConfigurationTabGroup {
		
		@Override
		public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
			
			ArrayList<ILaunchConfigurationTab> tabList = new ArrayList<>();
			for (ILaunchConfigurationTab tab : getSourceConfigurationTabs()) {
				tabList.add(tab);
			}
			
			tabList.add(new ModelsConfigurationTab());
			tabList.add(new ParametersConfigurationTab());
			tabList.add(getAdvancedConfigurationTab());
			
			for (ILaunchConfigurationTab tab : getOtherConfigurationTabs()) {
				tabList.add(tab);
			}
			
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IExtensionPoint extensionPoint = registry.getExtensionPoint("org.eclipse.epsilon.eol.dt.launchConfigurationExtension");

			for (IConfigurationElement configurationElement : extensionPoint.getConfigurationElements()) {
				try {
					EpsilonLaunchConfigurationTabContributor contributor = (EpsilonLaunchConfigurationTabContributor) configurationElement.createExecutableExtension("tabContributor");
					for(ILaunchConfigurationTab tab : contributor.getTabs(this, dialog, mode)) {
						tabList.add(tab);
					}
				}
				catch (CoreException e) {
					LogUtil.log(e);
				}
			}
			tabList.add(new CommonTab());
			
			setTabs(tabList);
		}
		
		public void setTabs(Collection<? extends ILaunchConfigurationTab> tabs) {
			setTabs(tabs.toArray(new ILaunchConfigurationTab[]{}));
		}
		
		public abstract ILaunchConfigurationTab getSourceConfigurationTab();
		
		public ILaunchConfigurationTab[] getSourceConfigurationTabs() {
			return new ILaunchConfigurationTab[]{getSourceConfigurationTab()};
		}
		
		public abstract ILaunchConfigurationTab getAdvancedConfigurationTab();
		
		public ILaunchConfigurationTab[] getAdvancedConfigurationTabs() {
			return new ILaunchConfigurationTab[]{getAdvancedConfigurationTab()};
		}
		
		public ILaunchConfigurationTab[] getOtherConfigurationTabs() {
			return new ILaunchConfigurationTab[]{};
		}
}
