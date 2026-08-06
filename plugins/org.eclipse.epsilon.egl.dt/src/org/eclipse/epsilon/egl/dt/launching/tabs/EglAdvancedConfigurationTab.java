/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* Contributors:
*     Horacio Hoyos - initial API and implementation
**********************************************************************/
package org.eclipse.epsilon.egl.dt.launching.tabs;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.epsilon.common.dt.EpsilonPlugin;
import org.eclipse.epsilon.common.dt.launching.tabs.AbstractAdvancedConfigurationTab;
import org.eclipse.epsilon.egl.dt.EglPlugin;
import org.eclipse.epsilon.egl.dt.launching.EglLaunchConfigurationDelegate;

/**
 * The Class EglAdvancedConfigurationTab.
 * @since 1.6
 * @author Horacio Hoyos Rodriguez
 */
public class EglAdvancedConfigurationTab extends AbstractAdvancedConfigurationTab {
	
	@Override
	public EpsilonPlugin getPlugin() {
		return EglPlugin.getDefault();
	}
	
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		super.initializeFrom(configuration);
	}
	
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		updateAvailableImpls(configuration);
		super.performApply(configuration);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		
	}

	String language;
	
	@Override
	public String getLanguage(ILaunchConfiguration configuration) {
		return language = EglLaunchConfigurationDelegate.getLanguageFromSource(configuration);
	}
	
	@Override
	protected boolean shouldImplementationBeIncludedInDropDown(String implName, ILaunchConfiguration configuration) {
		return implName.contains(language);
	}
	
	@Override
	protected boolean shouldImplementationBeRemoved(String language, IConfigurationElement configurationElement) {
		return false;//!language.equals(configurationElement.getAttribute("language"));
	}
	
	@Override
	protected boolean shouldConfigurationElementBeIncludedAsAnImplementation(String language, IConfigurationElement configurationElement) {
		return super.shouldConfigurationElementBeIncludedAsAnImplementation(language, configurationElement);
	}
}
