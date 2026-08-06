/*******************************************************************************
 * Copyright (c) 2010 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.csv.dt;

import org.eclipse.epsilon.common.dt.launching.dialogs.AbstractCachedModelConfigurationDialog;
import org.eclipse.epsilon.common.dt.util.DialogUtil;
import org.eclipse.epsilon.emc.csv.CsvModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class CsvModelConfigurationDialogue extends AbstractCachedModelConfigurationDialog {

	private Text fileText;
	private Text fieldSeparatorText;
	private Text quoteCharacterText;
	private Text idFieldText;
	private Button knownHeadersBtn;
	private Button varargsHeadersBtn;
	
	@Override
	protected String getModelName() {
		return "CSV model";
	}

	@Override
	protected String getModelType() {
		return "CSV";
	}

	@Override
	protected void createGroups(Composite control) {
		super.createGroups(control);
		createFileGroup(control);
		createLoadStoreOptionsGroup(control);
		createCsvGroup(control);
	}

	private void createFileGroup(Composite parent) {
		final Composite groupContent = DialogUtil.createGroupContainer(parent, "Files", 3);
		
		final Label modelFileLabel = new Label(groupContent, SWT.NONE);
		modelFileLabel.setText("Model file: ");
		
		fileText = new Text(groupContent, SWT.BORDER);
		fileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final Button browseFile = new Button(groupContent, SWT.NONE);
		browseFile.setText("Browse Workspace...");
		browseFile.addListener(SWT.Selection, new BrowseWorkspaceForModelsListener(fileText, "CSV files in the workspace", "Select a CSV file"));
		
	}
	
	protected void createCsvGroup(Composite parent) {
		final Composite groupContent = DialogUtil.createGroupContainer(parent, "CSV", 4);
		
		final Label modelFieldSeparatorLabel = new Label(groupContent, SWT.NONE);
		modelFieldSeparatorLabel.setText("Field Separator: ");
		fieldSeparatorText = new Text(groupContent, SWT.BORDER);
		fieldSeparatorText.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		fieldSeparatorText.setTextLimit(2);		// Accept \t for tabs? test this!!!
		
		final Label modelQuoteCharacterLabel = new Label(groupContent, SWT.NONE);
		modelQuoteCharacterLabel.setText("Quote Character: ");
		quoteCharacterText = new Text(groupContent, SWT.BORDER);
		quoteCharacterText.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		quoteCharacterText.setTextLimit(2);
		
		knownHeadersBtn = new Button(groupContent, SWT.CHECK);
		knownHeadersBtn.setText("Known Headers");
		knownHeadersBtn.addListener(SWT.Selection, event -> {
			System.out.println("knownHeadersBtn Selected");
			varargsHeadersBtn.setEnabled(knownHeadersBtn.getSelection());
		});
		varargsHeadersBtn = new Button(groupContent, SWT.CHECK);
		varargsHeadersBtn.setText("Varargs Headers");
		
		final Label modelIdFieldLabel = new Label(groupContent, SWT.NONE);
		modelIdFieldLabel.setText("Id Field: ");
		idFieldText = new Text(groupContent, SWT.BORDER);
		idFieldText.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		idFieldText.setToolTipText("This can be used to indicate that a given field/colum can be used as id. If using " +
								   "headers this should be the field name, if not, it should be the index of the column");
	}
	
	

	@Override
	protected void loadProperties() {
		super.loadProperties();
		if (properties == null) {
			fieldSeparatorText.setText(",");
			quoteCharacterText.setText("\"");
			knownHeadersBtn.setSelection(true);
		} else {
			fileText.setText(properties.getProperty(CsvModel.PROPERTY_FILE));
			fieldSeparatorText.setText(properties.getProperty(CsvModel.PROPERTY_FIELD_SEPARATOR));
			quoteCharacterText.setText(properties.getProperty(CsvModel.PROPERTY_QUOTE_CHARACTER));
			idFieldText.setText(properties.getProperty(CsvModel.PROPERTY_ID_FIELD));
			knownHeadersBtn.setSelection(properties.getBooleanProperty(CsvModel.PROPERTY_HAS_KNOWN_HEADERS, true));
			varargsHeadersBtn.setSelection(properties.getBooleanProperty(CsvModel.PROPERTY_HAS_VARARGS_HEADERS, false));
			varargsHeadersBtn.setEnabled(knownHeadersBtn.getSelection());
		}
	}

	@Override
	protected void storeProperties() {
		super.storeProperties();
		properties.setProperty(CsvModel.PROPERTY_FILE, fileText.getText());
		properties.setProperty(CsvModel.PROPERTY_FIELD_SEPARATOR, fieldSeparatorText.getText());
		properties.setProperty(CsvModel.PROPERTY_QUOTE_CHARACTER, quoteCharacterText.getText());
		
		String idFieldValue = idFieldText.getText();
		if(idFieldValue.isEmpty()) {
			properties.remove(CsvModel.PROPERTY_ID_FIELD);
		}
		else {
			properties.setProperty(CsvModel.PROPERTY_ID_FIELD, idFieldValue);
		}
		properties.setProperty(CsvModel.PROPERTY_HAS_KNOWN_HEADERS, String.valueOf(knownHeadersBtn.getSelection()));
		properties.setProperty(CsvModel.PROPERTY_HAS_VARARGS_HEADERS, String.valueOf(varargsHeadersBtn.getSelection()));
	}
}
