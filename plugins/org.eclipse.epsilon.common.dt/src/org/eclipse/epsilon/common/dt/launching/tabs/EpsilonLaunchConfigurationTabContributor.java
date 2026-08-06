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

import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

public interface EpsilonLaunchConfigurationTabContributor {
	
	public ILaunchConfigurationTab[] getTabs(EpsilonLaunchConfigurationTabGroup tabGroup, ILaunchConfigurationDialog dialog, String mode);
	
}
