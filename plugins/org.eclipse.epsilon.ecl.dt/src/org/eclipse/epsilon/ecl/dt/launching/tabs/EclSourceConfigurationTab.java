/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecl.dt.launching.tabs;

import org.eclipse.epsilon.common.dt.EpsilonPlugin;
import org.eclipse.epsilon.common.dt.launching.tabs.AbstractSourceConfigurationTab;
import org.eclipse.epsilon.ecl.dt.EclPlugin;

public class EclSourceConfigurationTab extends AbstractSourceConfigurationTab{

	@Override
	public EpsilonPlugin getPlugin() {
		return EclPlugin.getDefault();
	}

	@Override
	public String getImagePath() {
		return "icons/ecl.gif";
	}

	@Override
	public String getFileExtension() {
		return "ecl";
	}

	@Override
	public String getSelectionTitle() {
		return "Select an ECL Comparison";
	}

	@Override
	public String getSelectionSubtitle() {
		return "ECL Comparisons in Workspace";
	}

	public String getLaunchConfigurationKey() {
		return "SOURCE.ECL";
	}

	//public String getTitle() {
	//	return "ECL Source";
	//}

}
