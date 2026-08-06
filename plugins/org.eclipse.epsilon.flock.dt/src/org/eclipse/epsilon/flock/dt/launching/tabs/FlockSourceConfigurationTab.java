/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.dt.launching.tabs;

import org.eclipse.epsilon.common.dt.EpsilonPlugin;
import org.eclipse.epsilon.common.dt.launching.tabs.AbstractSourceConfigurationTab;
import org.eclipse.epsilon.flock.dt.FlockDevelopmentToolsPlugin;

public class FlockSourceConfigurationTab extends AbstractSourceConfigurationTab{

	@Override
	public EpsilonPlugin getPlugin() {
		return FlockDevelopmentToolsPlugin.getDefault();
	}

	@Override
	public String getImagePath() {
		return "icons/flock16.png";
	}

	@Override
	public String getFileExtension() {
		return "mig";
	}

	@Override
	public String getSelectionTitle() {
		return "Select an Epsilon Flock Migration Strategy";
	}

	@Override
	public String getSelectionSubtitle() {
		return "Epsilon Flock Migration Strategies in Workspace";
	}

	public String getLaunchConfigurationKey() {
		return "SOURCE.MIG";
	}

}
