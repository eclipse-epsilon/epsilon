/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eml.dt.launching.tabs;

import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.epsilon.common.dt.EpsilonPlugin;
import org.eclipse.epsilon.common.dt.launching.tabs.AbstractSourceConfigurationTab;
import org.eclipse.epsilon.eml.dt.EmlPlugin;
import org.eclipse.ui.PlatformUI;

public class EmlSourceConfigurationTab extends AbstractSourceConfigurationTab {

	public EmlSourceConfigurationTab() {
		super();
	}

	@Override
	public EpsilonPlugin getPlugin() {
		return EmlPlugin.getDefault();
	}

	@Override
	public String getImagePath() {
		return "icons/eml.gif";
	}

	@Override
	public String getFileExtension() {
		return "eml";
	}

	@Override
	public String getSelectionTitle() {
		return "Select EML module source";
	}

	@Override
	public String getSelectionSubtitle() {
		return "EML modules in Workspace";
	}
	
	@Override
	public String getTitle() {
		return "EML source";
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		String activeEditorName = getActiveEditorName();
		if (activeEditorName.length() > 0){
			configuration.rename(newLaunchConfigurationName(activeEditorName));
			configuration.setAttribute("source", getActiveEditorPath());
			configuration.setAttribute("source.ecl", getActiveEditorPath()
					.replace("eml", "ecl"));
		}
		
		PlatformUI.getWorkbench();
		
	}
}
