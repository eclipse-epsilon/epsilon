/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.flock.dt.launching.tabs;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.epsilon.common.dt.launching.tabs.ModelsConfigurationTab;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.flock.dt.launching.FlockLaunchConfigurationAttributes;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class FlockModelsConfigurationTab extends ModelsConfigurationTab implements SelectionListener {

	private Combo originalCombo;
	private Combo migratedCombo;

	@Override
	protected void createBottomControl(Composite parent) {
		final Composite container = new Composite(parent, SWT.FILL);
		container.setLayout(new GridLayout(2, false));

		final GridData comboData = new GridData();
		comboData.widthHint = 200;
		
		createLabel(container, "Original model:");
		originalCombo = createCombo(container, comboData, this);
		
		createLabel(container, "Migrated model:");
		migratedCombo = createCombo(container, comboData, this);
		
		addListenerToButtonControls(this);
		
		canSave();
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		updateCombos();
		canSave();
		updateLaunchConfigurationDialog();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		widgetSelected(e);
	}
	
	@Override
	public boolean canSave() {		
		if (originalCombo.getSelectionIndex() == -1) {
			this.setErrorMessage("The original (source) model has not been chosen.");
			return false;
		}
		
		if (migratedCombo.getSelectionIndex() == -1) {
			this.setErrorMessage("The migrated (target) model has not been chosen.");
			return false;
		}
		
		return super.canSave();
	}
	
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		configuration.setAttribute(FlockLaunchConfigurationAttributes.ORIGINAL_MODEL, originalCombo.getSelectionIndex());
		configuration.setAttribute(FlockLaunchConfigurationAttributes.MIGRATED_MODEL, migratedCombo.getSelectionIndex());
	}
	
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		super.initializeFrom(configuration);
		
		updateCombos();
		
		try {
			originalCombo.select(configuration.getAttribute(FlockLaunchConfigurationAttributes.ORIGINAL_MODEL, -1));
			migratedCombo.select(configuration.getAttribute(FlockLaunchConfigurationAttributes.MIGRATED_MODEL, -1));
		} catch (CoreException e) {
			return;
		}
		
		canSave();
	}
	
	private void updateCombos() {
		final List<String> modelNames = new LinkedList<>();
		
		for (Object model : models) {
			StringProperties sp = new StringProperties();
			sp.load(model.toString());
			modelNames.add(sp.getProperty("name"));
		}
		
		updateCombo(originalCombo, modelNames, "Original", "Source");
		updateCombo(migratedCombo, modelNames, "Migrated", "Target");
	}
	
	private static void updateCombo(Combo combo, List<String> items, String... valuesToAutomaticallySelect) {
		final int selection = combo.getSelectionIndex();

		combo.setItems(items.toArray(new String[] {}));
		
		if (selection == -1) {
			for (String value : valuesToAutomaticallySelect) {
				if (items.contains(value)) {
					combo.setText(value);
				}
			}
		} else {
			combo.select(selection);
		}
	}
	
	private static Label createLabel(Composite container, String text) {
		final Label label = new Label(container, SWT.NONE);
		label.setText(text);
		return label;
	}
	
	private static Combo createCombo(Composite container, Object layoutData, SelectionListener selectionListener) {
		final Combo combo = new Combo(container, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.setLayoutData(layoutData);
		combo.addSelectionListener(selectionListener);
		return combo;
	}
}
