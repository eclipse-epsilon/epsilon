/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.launching.dialogs;

import org.eclipse.epsilon.common.dt.util.DialogUtil;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public abstract class AbstractModelConfigurationDialog extends TitleAreaDialog{
	
	protected StringProperties properties;
	
	protected Label nameLabel;
	protected Text nameText;
	protected Label aliasesLabel;
	protected Text aliasesText;
	
	protected Label readOnLoadLabel;
	protected Button readOnLoadCheckbox;
	protected Label storeOnDisposalLabel;
	protected Button storeOnDisposalCheckbox;
	
	public AbstractModelConfigurationDialog() {
		//super(LaunchConfigurationDialog.getCurrentlyVisibleLaunchConfigurationDialog().getActiveTab().getControl().getShell());
		super(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
	}

	public StringProperties getProperties(){
		return properties;
	}
	
	public void setProperties(StringProperties properties){
		this.properties = properties;
	}
	
	public static Composite createGroupContainer(Composite parent, String text, int columns) {
		return DialogUtil.createGroupContainer(parent, text, columns);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		
		Composite superControl = (Composite) super.createDialogArea(parent);
		
		
		this.setTitle("Configure " + getModelName());
		this.setMessage("Configure the details of the " + getModelName());
		this.getShell().setText("Configure " + getModelName());
		
		Composite control = new Composite(superControl, SWT.FILL);
		control.setLayout(new GridLayout(1,true));
		control.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		createGroups(control);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(control, "org.eclipse.epsilon.help.emc_dialogs");
		
		loadProperties();
		
		control.layout();
		control.pack();
		
		return control;
	}
	
	protected void createLoadStoreOptionsGroup(Composite parent) {
		final Composite groupContent = DialogUtil.createGroupContainer(parent, "Load/Store Options", 2);
		
		readOnLoadLabel = new Label(groupContent, SWT.NONE);
		readOnLoadLabel.setText("Read on load: ");
		
		readOnLoadCheckbox = new Button(groupContent, SWT.CHECK);
		readOnLoadCheckbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		readOnLoadCheckbox.setSelection(true);
		
		storeOnDisposalLabel = new Label(groupContent, SWT.NONE);
		storeOnDisposalLabel.setText("Store on disposal: ");
		
		storeOnDisposalCheckbox = new Button(groupContent, SWT.CHECK);
		storeOnDisposalCheckbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		storeOnDisposalCheckbox.setSelection(true);
		
		groupContent.layout();
		groupContent.pack();		
	}
	
	protected void createNameAliasGroup(Composite parent) {
		final Composite groupContent = DialogUtil.createGroupContainer(parent, "Identification", 2);
		
		nameLabel = new Label(groupContent, SWT.NONE);
		nameLabel.setText("Name: ");
		
		nameText = new Text(groupContent, SWT.BORDER);
		nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		aliasesLabel = new Label(groupContent, SWT.NONE);
		aliasesLabel.setText("Aliases: ");
		
		aliasesText = new Text(groupContent, SWT.BORDER);
		aliasesText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		groupContent.layout();
		groupContent.pack();
	}
	
	protected void loadProperties() {
		if (properties == null) return;
		nameText.setText(properties.getProperty("name"));
		aliasesText.setText(properties.getProperty("aliases"));
		if (readOnLoadCheckbox!=null) readOnLoadCheckbox.setSelection(Boolean.parseBoolean(properties.getProperty("readOnLoad", "true")));
		if (storeOnDisposalCheckbox!=null) storeOnDisposalCheckbox.setSelection(Boolean.parseBoolean(properties.getProperty("storeOnDisposal", "false")));
	}
	
	protected void storeProperties() {
		properties = new StringProperties();
		properties.put("type", getModelType());
		properties.put("name", nameText.getText());
		properties.put("aliases", aliasesText.getText());
		if (readOnLoadCheckbox!=null) properties.put("readOnLoad", readOnLoadCheckbox.getSelection() + "");
		if (storeOnDisposalCheckbox!=null) properties.put("storeOnDisposal", storeOnDisposalCheckbox.getSelection() + "");
	}
	
	@Override
	protected void okPressed() {
		storeProperties();
		super.okPressed();
	}
	
	@Override
	protected void setShellStyle(int newShellStyle) {
		   super.setShellStyle(newShellStyle | SWT.RESIZE);
	}
	
	protected void createGroups(Composite control) {
		createNameAliasGroup(control);
	}
	
	abstract protected String getModelName();
	abstract protected String getModelType();
	
	protected class BrowseWorkspaceForModelsListener implements Listener{
		private Text text = null;
		private String title = "";
		private String prompt = "";
		private String extension = null;
		
		public BrowseWorkspaceForModelsListener(Text text, String title, String prompt) {
			this(text, title, prompt, null);
		}

		public BrowseWorkspaceForModelsListener(Text text, String title, String prompt, String extension) {
			this.text = text;
			this.title = title;
			this.prompt = prompt;
			this.extension = extension;
		}

		@Override
		public void handleEvent(Event event) {
			String file;
			if (extension == null) {
				file = BrowseWorkspaceUtil.browseFilePath(getShell(), title, prompt, null);
			} else {
				file = BrowseWorkspaceUtil.browseFilePath(getShell(), title, prompt, extension, null);
			}

			if (file != null){
				text.setText(file);
			}
		}
	}
	
}
