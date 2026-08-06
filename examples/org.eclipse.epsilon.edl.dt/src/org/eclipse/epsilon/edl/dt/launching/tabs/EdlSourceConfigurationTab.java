/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.edl.dt.launching.tabs;

import org.eclipse.epsilon.common.dt.EpsilonPlugin;
import org.eclipse.epsilon.common.dt.launching.tabs.AbstractSourceConfigurationTab;
import org.eclipse.epsilon.edl.dt.EdlPlugin;

public class EdlSourceConfigurationTab extends AbstractSourceConfigurationTab {

	@Override
	public EpsilonPlugin getPlugin() {
		return EdlPlugin.getDefault();
	}

	@Override
	public String getImagePath() {
		return "icons/edl.png";
	}

	@Override
	public String getFileExtension() {
		return "edl";
	}

	@Override
	public String getSelectionTitle() {
		return "Select an EDL Demo";
	}

	@Override
	public String getSelectionSubtitle() {
		return "EDL Demos in Workspace";
	}

	public String getLaunchConfigurationKey() {
		return "SOURCE";
	}

}
