/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.simulink.dt;

import org.eclipse.epsilon.emc.simulink.common.dt.AbstractSimulinkModelConfigurationDialog;
import org.eclipse.epsilon.emc.simulink.model.SimulinkModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class SimulinkModelConfigurationDialog extends AbstractSimulinkModelConfigurationDialog {

	@Override
	protected String getModelName() {
		return "Simulink Model";
	}

	@Override
	protected String getModelType() {
		return "Simulink";
	}

	protected Button currentSimulinkCheckbox;
	protected Button showInMatlabEditorCheckbox;
	protected Button followLinksCheckbox;
	protected Button isCommentedCheckbox;
	protected Button enableFindOptimisationsCheckbox;
	
	@Override
	protected Composite createFilesGroup(Composite parent) {
		Composite createFilesGroup = super.createFilesGroup(parent);
		modelFileTextLabel.setText("Model file: ");
		
		createFilesGroup.layout();
		createFilesGroup.pack();
		return createFilesGroup;
	}
	
	@Override
	protected Composite createEngineGroup(Composite parent) {
		Composite engineGroup = super.createEngineGroup(parent);
		
		GridData buttonData = new GridData(GridData.FILL_HORIZONTAL);
		buttonData.horizontalSpan = 1;

		Label currentSimulinkLabel = new Label(engineGroup, SWT.NONE);
		currentSimulinkLabel.setText("Use current model: ");
				
		currentSimulinkCheckbox = new Button(engineGroup, SWT.CHECK);
		currentSimulinkCheckbox.setSelection(false);
		currentSimulinkCheckbox.setToolTipText("If selected, disregards any specified simulink model file and instead works with the current open model in a shared MATLAB session.");
		currentSimulinkCheckbox.setLayoutData(buttonData);
		
		currentSimulinkCheckbox.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button source = (Button) e.getSource();
				disableOnSelect(source, modelFileText);
				disableOnSelect(source, browseModelFile);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				Button source = (Button) e.getSource();
				disableOnSelect(source, modelFileText);
				disableOnSelect(source, browseModelFile);
			}
		});
		
		Label followLinksLabel = new Label(engineGroup, SWT.NONE);
		followLinksLabel.setText("Follow Block Links: ");
		followLinksLabel.setToolTipText("Set the 'FollowLinks' flag to 'on' of the 'find_system' method "
				+ "used when calling all elements of a type in Epsilon e.g. ModelType.all;");
		
		followLinksCheckbox = new Button(engineGroup, SWT.CHECK);
		followLinksCheckbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		followLinksCheckbox.setSelection(false);
		
		Label isCommentedLabel = new Label(engineGroup, SWT.NONE);
		isCommentedLabel.setText("Include Commented Blocks: ");
		isCommentedLabel.setToolTipText(""); //FIXME
		
		isCommentedCheckbox = new Button(engineGroup, SWT.CHECK);
		isCommentedCheckbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		isCommentedCheckbox.setSelection(false);
		
		Label enableFindOptimisationsLabel = new Label(engineGroup, SWT.NONE);
		enableFindOptimisationsLabel.setText("Use MATLAB bulk find: ");
		enableFindOptimisationsLabel.setToolTipText(""); //FIXME
		
		enableFindOptimisationsCheckbox = new Button(engineGroup, SWT.CHECK);
		enableFindOptimisationsCheckbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		enableFindOptimisationsCheckbox.setSelection(true);
				
		engineGroup.layout();
		engineGroup.pack();
		return engineGroup;
	}
	
	@Override
	protected void restoreStateOfNonSharedEngineFields() {
		super.restoreStateOfNonSharedEngineFields();
		currentSimulinkCheckbox.setEnabled(false);
		currentSimulinkCheckbox.setSelection(false);
		modelFileText.setEnabled(true);
		modelFileText.setEditable(true);
		browseModelFile.setEnabled(true);
		showInMatlabEditorCheckbox.setEnabled(true);
	}
	
	@Override
	protected void enableSharedEngineRelatedFields() {
		super.enableSharedEngineRelatedFields();
		//enableOnSelect(isSharedButton, currentSimulinkButton);
	}

	@Override
	protected void loadProperties() {
		super.loadProperties();
		if (properties == null) return;
		if (currentSimulinkCheckbox != null) {
			currentSimulinkCheckbox.setSelection(Boolean.valueOf(properties.getProperty(SimulinkModel.PROPERTY_CURRENT_SIMULINK_MODEL,"false")));
			if (currentSimulinkCheckbox.getSelection()) {
				disableOnSelect(currentSimulinkCheckbox, modelFileText);
				disableOnSelect(currentSimulinkCheckbox, browseModelFile);
			}
		}
		if (followLinksCheckbox != null) {
			followLinksCheckbox.setSelection(Boolean.valueOf(properties.getProperty(SimulinkModel.PROPERTY_FOLLOW_LINKS,"true")));
		}
		if (isCommentedCheckbox != null) {
			isCommentedCheckbox.setSelection(Boolean.valueOf(properties.getProperty(SimulinkModel.PROPERTY_INCLUDE_COMMENTED,"true")));
		}
		if (enableFindOptimisationsCheckbox != null) {
			enableFindOptimisationsCheckbox.setSelection(Boolean.valueOf(properties.getProperty(SimulinkModel.PROPERTY_LOOK_UNDER_MASKS,"true")));
		}
	}

	@Override
	protected void storeProperties() {
		super.storeProperties();
		if (followLinksCheckbox != null) {
			properties.put(SimulinkModel.PROPERTY_FOLLOW_LINKS, followLinksCheckbox.getSelection() + "");
		}
		if (isCommentedCheckbox != null) {
			properties.put(SimulinkModel.PROPERTY_INCLUDE_COMMENTED, isCommentedCheckbox.getSelection() + "");
		}
		if (enableFindOptimisationsCheckbox != null) {
			properties.put(SimulinkModel.PROPERTY_FIND_OPTIMISATION, enableFindOptimisationsCheckbox.getSelection() + "");
		}
		if (currentSimulinkCheckbox != null) {
			properties.put(SimulinkModel.PROPERTY_CURRENT_SIMULINK_MODEL, currentSimulinkCheckbox.getSelection() + "");
		}
	}
	
}
