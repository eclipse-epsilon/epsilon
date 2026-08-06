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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.epsilon.common.concurrent.ConcurrencyUtils;
import org.eclipse.epsilon.eol.execute.context.concurrent.IEolContextParallel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

/**
 * 
 * @author Horacio Hoyos
 * @author Sina Madani - parallel configuraiton utils
 * @since 1.6
 */
public class AbstractModuleConfiguration implements ModuleConfiguration {

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		// Nothing to do
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		// Nothing to store
	}

	@Override
	public void createModuleConfigurationWidgets(Composite group, AbstractAdvancedConfigurationTab tab) {
		// Nothing to create
	}
	
	protected static class ConfigurationTabSelectionListener implements SelectionListener {
		final AbstractAdvancedConfigurationTab advancedTab;
		
		public ConfigurationTabSelectionListener(AbstractAdvancedConfigurationTab tab) {
			this.advancedTab = tab;
		}
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			advancedTab.enableApply();
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			
		}
	}
	
	// Parallel module utils
	
	protected static final int THREAD_INITIAL = Math.max(1, ConcurrencyUtils.DEFAULT_PARALLELISM - 1);
	protected static final int THREAD_INCREMENTS = calculateThreadIncrementFromInitial(THREAD_INITIAL);
	protected static final int THREAD_MAXIMUM = THREAD_INITIAL*THREAD_INCREMENTS;
	
	/**
	 * Example mapping of initial -> increment:</br>
	 * 	64 -> 8,</br>
	 * 	56 -> 7,</br>
	 * 	48 -> 6,</br>
	 * 	32 -> 4,</br>
	 * 	24 -> 4,</br>
	 * 	16 -> 4,</br>
	 * 	12 -> 3,</br>
	 * 	8 -> 2,</br>
	 * 	6 -> 2,</br>
	 * 	4 -> 2,</br>
	 * 	2 -> 1
	 */
	protected static int calculateThreadIncrementFromInitial(int initial) {
		for (int i = (int) Math.round(Math.sqrt(initial)); i > 0; i--) {
			if (initial % i == 0)
				return i;
		}
		return (int) Math.sqrt(initial);
	}
	
	protected static Composite createParallelContainer(Composite group) {
		final Composite container = new Composite(group, SWT.FILL);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		return container;
	}
	
	protected static Label createThreadsLabel(Composite container) {
		Label numThreadsLabel = new Label(container, SWT.WRAP);
		numThreadsLabel.setText("Number of threads: ");
		return numThreadsLabel;
	}

	protected static Spinner createThreadsSelector(Composite container, AbstractAdvancedConfigurationTab tab) {
		Spinner numThreadsSelector = new Spinner(container, SWT.BORDER);
		numThreadsSelector.setMinimum(1);
		numThreadsSelector.setSelection(THREAD_INITIAL);
		numThreadsSelector.setIncrement(THREAD_INCREMENTS);
		numThreadsSelector.setMaximum(THREAD_MAXIMUM);
		numThreadsSelector.setToolTipText("Parallelism");
		numThreadsSelector.addSelectionListener(new ConfigurationTabSelectionListener(tab));
		return numThreadsSelector;
	}
	
	protected static void initializeThreadsFromConfiguration(ILaunchConfiguration configuration, Spinner numThreadsSelector) {
		try {
			int numThreads = configuration.getAttribute(IEolContextParallel.NUM_THREADS_CONFIG, THREAD_INITIAL);
			numThreadsSelector.setSelection(numThreads);
			numThreadsSelector.setIncrement(calculateThreadIncrementFromInitial(numThreads));
			numThreadsSelector.setMaximum(THREAD_MAXIMUM);
		}
		catch (CoreException cx) {
			// TODO Auto-generated catch block
		}
	}
	
	protected static void performApplyThreadsForConfiguration(ILaunchConfigurationWorkingCopy configuration, Spinner numThreadsSelector) {
		int numThreads = numThreadsSelector != null ? numThreadsSelector.getSelection() : THREAD_INITIAL;
		configuration.setAttribute(IEolContextParallel.NUM_THREADS_CONFIG, numThreads);
		if (numThreadsSelector != null) {
			numThreadsSelector.setIncrement(calculateThreadIncrementFromInitial(numThreads));
		}
	}
}
