/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.dt.launching.tabs;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.swt.widgets.Composite;

public interface ModuleConfiguration {
	
	/**
	 * Create the composite that contains the module configuration options
	 * @param group		a SWT Composite that can be used to place swt widgets required
	 * 					for configuring the module. 
	 * @param tab 
	 */
	void createModuleConfigurationWidgets(Composite group, AbstractAdvancedConfigurationTab tab);
	
	/**
	 * Initialise the composite for this module with the configuration values
	 * @param configuration
	 */
	void initializeFrom(ILaunchConfiguration configuration);

	/**
	 * Store the configured values in the configuration
	 * @param configuration
	 */
	void performApply(ILaunchConfigurationWorkingCopy configuration);
	
}