/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.etl.dt.launching;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.epsilon.common.dt.launching.tabs.AbstractAdvancedConfigurationTab;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EtlModuleParallelConfiguration extends EtlModuleConfiguration {

	protected Spinner numThreadsSelector;
	protected Label numThreadsLabel;
	
	@Override
	public void createModuleConfigurationWidgets(Composite group, AbstractAdvancedConfigurationTab tab) {
		super.createModuleConfigurationWidgets(group, tab);
		Composite container = createParallelContainer(group);
		numThreadsLabel = createThreadsLabel(container);
		numThreadsSelector = createThreadsSelector(container, tab);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		super.initializeFrom(configuration);
		initializeThreadsFromConfiguration(configuration, numThreadsSelector);
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		performApplyThreadsForConfiguration(configuration, numThreadsSelector);
	}
	
}
