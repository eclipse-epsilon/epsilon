/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.evl.dt.launching.tabs;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.epsilon.common.dt.launching.tabs.AbstractAdvancedConfigurationTab;
import org.eclipse.epsilon.common.dt.launching.tabs.AbstractModuleConfiguration;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.execute.context.IEvlContext;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * 
 * @author Sina Madani
 * @author Horacio Hoyos
 * @since 1.6
 */
public class EvlModuleConfiguration extends AbstractModuleConfiguration {

	private Button optimizeConstraintsBtn, optimizeConstraintTraceBtn, shortCircuitBtn;
	
	@Override
	public void createModuleConfigurationWidgets(Composite group, AbstractAdvancedConfigurationTab tab) {
		SelectionListener selectionListener = new ConfigurationTabSelectionListener(tab);
		
		optimizeConstraintsBtn = new Button(group, SWT.CHECK);
		optimizeConstraintsBtn.setText("Optimize Constraints to Select Operations");
		optimizeConstraintsBtn.addSelectionListener(selectionListener);
		
		optimizeConstraintTraceBtn = new Button(group, SWT.CHECK);
		optimizeConstraintTraceBtn.setSelection(true);
		optimizeConstraintTraceBtn.setText("Optimize constraint trace");
		optimizeConstraintTraceBtn.setToolTipText(
			"Only add results to the constraint trace if they are invoked by a satisfies operation."
		);
		optimizeConstraintTraceBtn.addSelectionListener(selectionListener);
		
		shortCircuitBtn = new Button(group, SWT.CHECK);
		shortCircuitBtn.setText("Short-circuited validation");
		shortCircuitBtn.setToolTipText("Stop validation when any constraints are unsatisfied.");
		shortCircuitBtn.addSelectionListener(selectionListener);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			optimizeConstraintsBtn.setSelection(
				configuration.getAttribute(EvlModule.OPTIMIZE_CONSTRAINTS, optimizeConstraintsBtn.getSelection())
			);
			optimizeConstraintTraceBtn.setSelection(configuration.getAttribute(
				IEvlContext.OPTIMIZE_CONSTRAINT_TRACE, optimizeConstraintTraceBtn.getSelection())
			);
			shortCircuitBtn.setSelection(
				configuration.getAttribute(IEvlContext.SHORT_CIRCUIT, shortCircuitBtn.getSelection())
			);
		}
		catch (CoreException e) {
			// skip 
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		boolean optimizeConstraints = optimizeConstraintsBtn != null ? optimizeConstraintsBtn.getSelection() : false;
		boolean optimizeConstraintTrace = optimizeConstraintTraceBtn != null ? optimizeConstraintTraceBtn.getSelection() : false;
		boolean shortCircuit = shortCircuitBtn != null ? shortCircuitBtn.getSelection() : false;
		configuration.setAttribute(EvlModule.OPTIMIZE_CONSTRAINTS, optimizeConstraints);
		configuration.setAttribute(IEvlContext.OPTIMIZE_CONSTRAINT_TRACE, optimizeConstraintTrace);
		configuration.setAttribute(IEvlContext.SHORT_CIRCUIT, shortCircuit);
	}
}
