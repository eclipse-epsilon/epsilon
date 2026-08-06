/*******************************************************************************
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Martins Francis - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.spreadsheets.excel.dt;

import org.eclipse.epsilon.common.dt.launching.dialogs.AbstractModelConfigurationDialog;
import org.eclipse.epsilon.common.dt.util.DialogUtil;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.emc.spreadsheets.excel.ExcelModel;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ExcelModelConfigurationDialog extends AbstractModelConfigurationDialog {
	
	@Override
	protected String getModelName() {
		return "Microsoft Excel Spreadsheet";
	}

	@Override
	protected String getModelType() {
		return "ExcelModel";
	}

	protected Label spreadsheetFileTextLabel;
	protected Text spreadsheetFileText;
	protected Button browseSpreadsheetFile;
	protected Label configFileTextLabel;
	protected Text configurationFileText;
	protected Button browseConfigFile;
	protected Label passwordLabel;
	protected Text passwordText;

	@Override
	protected void createGroups(final Composite control) {
		createNameAliasGroup(control);
		createSpreadsheetGroup(control);
		createLoadStoreOptionsGroup(control);
		createConfigurationFileGroup(control);
		createPasswordGroup(control);
	}

	protected void createSpreadsheetGroup(final Composite parent) {
		final Composite groupContent = DialogUtil.createGroupContainer(parent, "Spreadsheet Details", 3);

		spreadsheetFileTextLabel = new Label(groupContent, SWT.NONE);
		spreadsheetFileTextLabel.setText("File: ");

		spreadsheetFileText = new Text(groupContent, SWT.BORDER);
		spreadsheetFileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		browseSpreadsheetFile = new Button(groupContent, SWT.NONE);
		browseSpreadsheetFile.setText("Browse Workspace...");
		browseSpreadsheetFile.addListener(SWT.Selection, new BrowseWorkspaceForModelsListener(spreadsheetFileText,
			"Microsoft Excel File in the workspace", "Select Microsoft Excel File"));

		groupContent.layout();
		groupContent.pack();
	}

	protected void createConfigurationFileGroup(final Composite parent) {
		final Composite groupContent = DialogUtil.createGroupContainer(parent, "ORM Meta-data", 3);

		configFileTextLabel = new Label(groupContent, SWT.NONE);
		configFileTextLabel.setText("File: ");

		configurationFileText = new Text(groupContent, SWT.BORDER);
		configurationFileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		browseConfigFile = new Button(groupContent, SWT.NONE);
		browseConfigFile.setText("Browse Workspace...");
		browseConfigFile.addListener(SWT.Selection, new BrowseWorkspaceForModelsListener(configurationFileText,
			"XML meta-data file in the workspace", "Select an XML Meta-data File"));

		groupContent.layout();
		groupContent.pack();
	}

	protected void createPasswordGroup(final Composite parent) {
		final Composite groupContent = DialogUtil.createGroupContainer(parent, "Secure Spreadsheet (XLSX only)", 2);

		passwordLabel = new Label(groupContent, SWT.NONE);
		passwordLabel.setText("Password: ");

		passwordText = new Text(groupContent, SWT.PASSWORD | SWT.BORDER);
		passwordText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		groupContent.layout();
		groupContent.pack();
	}

	@SuppressWarnings("resource")
	@Override
	protected void loadProperties() {
		super.loadProperties();

		if (properties != null) {
			this.spreadsheetFileText.setText(properties.getProperty(ExcelModel.SPREADSHEET_FILE));
			this.configurationFileText.setText(properties.getProperty(ExcelModel.CONFIGURATION_FILE));
			
			if (properties.hasProperty(ExcelModel.SPREADSHEET_PASSWORD)) {
				try {
					this.passwordText.setText(new SecureExcelModel().loadPassword(properties));
				}
				catch (StorageException e) {
					LogUtil.log(e);
				}
			}
		}
	}

	@Override
	protected void storeProperties() {
		super.storeProperties();

		super.properties.put(ExcelModel.SPREADSHEET_FILE, this.spreadsheetFileText.getText());
		super.properties.put(ExcelModel.CONFIGURATION_FILE, this.configurationFileText.getText());
		
		if (!this.passwordText.getText().isEmpty()) {
			final ISecurePreferences preferences = SecurePreferencesFactory.getDefault();
			final ISecurePreferences node = preferences.node(this.spreadsheetFileText.getText());
			try {
				node.put(ExcelModel.SPREADSHEET_PASSWORD, this.passwordText.getText(), true);
			}
			catch (StorageException e) {
				final String message = "Equinox Security was unable to store the Microsoft Excel File password";
				throw new RuntimeException(message);
			}
		}
		else {
			super.properties.remove(ExcelModel.SPREADSHEET_PASSWORD);
		}
	}

}
