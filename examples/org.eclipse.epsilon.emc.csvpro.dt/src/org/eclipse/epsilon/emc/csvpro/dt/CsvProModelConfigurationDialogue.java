/*******************************************************************************
 * Copyright (c) 2010 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.csvpro.dt;

import java.io.BufferedReader;
import java.io.FileReader;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.epsilon.common.dt.launching.dialogs.AbstractCachedModelConfigurationDialog;
import org.eclipse.epsilon.common.dt.util.EclipseUtil;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.emc.csvpro.CsvProModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class CsvProModelConfigurationDialogue extends AbstractCachedModelConfigurationDialog {

	private Text fileText;
	private Text fieldSeparatorText;
	private Button knownHeadersBtn;
	private Button varargsHeadersBtn;
	private Combo columnForTypeCombo;
	private Button columnForTypeBtn;
	private Button columnForIndexBtn;
	private Combo columnForIndexCombo;
	
	@Override
	protected String getModelName() {
		return "CSV Pro model";
	}

	@Override
	protected String getModelType() {
		return "CSV Pro";
	}

	@Override
	protected void createGroups(Composite control) {
		super.createGroups(control);
		createFileGroup(control);
		createLoadStoreOptionsGroup(control);
		createCsvGroup(control);
	}

	private void createFileGroup(Composite parent) {
		final Composite groupContent = createGroupContainer(parent, "Files", 3);
		
		final Label modelFileLabel = new Label(groupContent, SWT.NONE);
		modelFileLabel.setText("Model file: ");
		
		fileText = new Text(groupContent, SWT.BORDER);
		fileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fileText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				populateColumnCombos(fileText.getText());
			}
		});
		fileText.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				populateColumnCombos(fileText.getText());
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		final Button browseFile = new Button(groupContent, SWT.NONE);
		browseFile.setText("Browse Workspace...");
		browseFile.addListener(SWT.Selection, new BrowseWorkspaceForModelsListener(fileText, "CSV files in the workspace", "Select a CSV file"));
		
	}
	
	protected void populateColumnCombos(String relativePath) {
		String fullPath = null;
		try {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(relativePath));
			if (file != null) { 
				fullPath = file.getLocation().toOSString(); 
			}
		}
		catch (Exception ex) { LogUtil.log("Error while resolving absolute path for " + relativePath, ex); }
		if (fullPath == null) {
			fullPath = EclipseUtil.getWorkspacePath() + relativePath;
		}
		String text = "";
		try {
			BufferedReader brTest = new BufferedReader(new FileReader(fullPath));
			text = brTest.readLine();
			brTest.close();
		} catch (Exception ex) {
			LogUtil.log("Error while resolving absolute path for " + relativePath, ex);
			return;
		}
		String[] items = text.split(this.fieldSeparatorText.getText());
		columnForTypeCombo.setItems(items);
		columnForTypeCombo.select(0);
		columnForIndexCombo.setItems(items);
		columnForIndexCombo.select(0);
		
	}

	protected void createCsvGroup(Composite parent) {
		final Composite groupContent = createGroupContainer(parent, "CVS", 4);
		
		final Label modelFieldSeparatorLabel = new Label(groupContent, SWT.NONE);
		modelFieldSeparatorLabel.setText("Field Separator: ");
		fieldSeparatorText = new Text(groupContent, SWT.BORDER);
		fieldSeparatorText.setLayoutData(new GridData(GridData.BEGINNING));
		
		knownHeadersBtn = new Button(groupContent, SWT.CHECK);
		knownHeadersBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		knownHeadersBtn.setText("Known Headers");
		knownHeadersBtn.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				//varargsHeadersBtn.setEnabled(knownHeadersBtn.getSelection());
				columnForTypeBtn.setEnabled(knownHeadersBtn.getSelection());
				columnForTypeCombo.setEnabled(knownHeadersBtn.getSelection() && columnForTypeBtn.getSelection());
				columnForIndexBtn.setEnabled(knownHeadersBtn.getSelection());
				columnForIndexCombo.setEnabled(knownHeadersBtn.getSelection() && columnForIndexBtn.getSelection());
			}
		});
		varargsHeadersBtn = new Button(groupContent, SWT.CHECK);
		varargsHeadersBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		varargsHeadersBtn.setText("Varargs Headers");
		varargsHeadersBtn.setEnabled(false);
		
		columnForTypeBtn = new Button(groupContent, SWT.CHECK);
		columnForTypeBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		columnForTypeBtn.setText("Use Colum for types");
		columnForTypeBtn.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				columnForTypeCombo.setEnabled(columnForTypeBtn.getSelection());
			}
		});
		
		final Label columnForType = new Label(groupContent, SWT.NONE);
		columnForType.setText("Column for types: ");
		columnForTypeCombo = new Combo(groupContent, SWT.READ_ONLY);
		GridData columnForTypeComboGridData = new GridData(GridData.FILL_HORIZONTAL);
		columnForTypeComboGridData.horizontalSpan = 2;
		columnForTypeCombo.setLayoutData(columnForTypeComboGridData);
		columnForTypeCombo.setEnabled(false);
		
		columnForIndexBtn = new Button(groupContent, SWT.CHECK);
		columnForIndexBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		columnForIndexBtn.setText("Use Colum for index");
		columnForIndexBtn.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				columnForIndexCombo.setEnabled(columnForIndexBtn.getSelection());
			}
		});
		
		final Label columnForType1 = new Label(groupContent, SWT.NONE);
		columnForType1.setText("Column for index: ");
		columnForIndexCombo = new Combo(groupContent, SWT.READ_ONLY);
		columnForIndexCombo.setLayoutData(columnForTypeComboGridData);
		columnForIndexCombo.setEnabled(false);
	}
	
	@Override
	protected void loadProperties() {
		super.loadProperties();
		if (properties == null) {
			fieldSeparatorText.setText(",");
			knownHeadersBtn.setSelection(false);
		} else {
			// Separator first so columnForTypeCombo will be filled in correctly
			fieldSeparatorText.setText(properties.getProperty(CsvProModel.PROPERTY_FIELD_SEPARATOR));
			fileText.setText(properties.getProperty(CsvProModel.PROPERTY_FILE));
			knownHeadersBtn.setSelection(properties.getBooleanProperty(CsvProModel.PROPERTY_HAS_KNOWN_HEADERS, true));
			varargsHeadersBtn.setSelection(properties.getBooleanProperty(CsvProModel.PROPERTY_HAS_VARARGS_HEADERS, false));
			//varargsHeadersBtn.setEnabled(knownHeadersBtn.getSelection());
			columnForTypeBtn.setEnabled(knownHeadersBtn.getSelection());
			columnForTypeBtn.setSelection(properties.getBooleanProperty(CsvProModel.PROPERTY_USE_TYPE_COLUMN, false));
			columnForTypeCombo.setEnabled(columnForTypeBtn.getSelection());
			int selection =  properties.getIntegerProperty(CsvProModel.PROPERTY_TYPE_COLUMN, 0);
			columnForTypeCombo.select(selection);
			
			columnForIndexBtn.setEnabled(knownHeadersBtn.getSelection());
			columnForIndexBtn.setSelection(properties.getBooleanProperty(CsvProModel.PROPERTY_USE_INDEX_COLUMN, false));
			columnForIndexCombo.setEnabled(columnForTypeBtn.getSelection());
			selection =  properties.getIntegerProperty(CsvProModel.PROPERTY_INDEX_COLUMN, 0);
			columnForIndexCombo.select(selection);
		}
	}

	@Override
	protected void storeProperties() {
		super.storeProperties();
		properties.setProperty(CsvProModel.PROPERTY_FILE, fileText.getText());
		properties.setProperty(CsvProModel.PROPERTY_FIELD_SEPARATOR, fieldSeparatorText.getText());
		properties.setProperty(CsvProModel.PROPERTY_HAS_KNOWN_HEADERS, String.valueOf(knownHeadersBtn.getSelection()));
		properties.setProperty(CsvProModel.PROPERTY_HAS_VARARGS_HEADERS, String.valueOf(varargsHeadersBtn.getSelection()));
		properties.setProperty(CsvProModel.PROPERTY_USE_TYPE_COLUMN, String.valueOf(columnForTypeBtn.getEnabled() && columnForTypeBtn.getSelection()));
		properties.setProperty(CsvProModel.PROPERTY_TYPE_COLUMN, String.valueOf(columnForTypeCombo.getSelectionIndex()));
		properties.setProperty(CsvProModel.PROPERTY_USE_INDEX_COLUMN, String.valueOf(columnForIndexBtn.getEnabled() && columnForIndexBtn.getSelection()));
		properties.setProperty(CsvProModel.PROPERTY_INDEX_COLUMN, String.valueOf(columnForIndexCombo.getSelectionIndex()));
	}
}
