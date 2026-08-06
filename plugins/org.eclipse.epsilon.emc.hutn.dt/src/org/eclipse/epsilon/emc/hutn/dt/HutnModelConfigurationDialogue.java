/*******************************************************************************
 * Copyright (c) 2010 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.hutn.dt;

import org.eclipse.epsilon.common.dt.launching.dialogs.AbstractModelConfigurationDialog;
import org.eclipse.epsilon.common.dt.launching.dialogs.BrowseWorkspaceUtil;
import org.eclipse.epsilon.common.dt.util.DialogUtil;
import org.eclipse.epsilon.emc.hutn.HutnModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class HutnModelConfigurationDialogue extends AbstractModelConfigurationDialog {

	private Text hutnFileText;

	@Override
	protected String getModelName() {
		return "EMF model expressed with HUTN.";
	}

	@Override
	protected String getModelType() {
		return "EMF_HUTN";
	}

	@Override
	protected void createGroups(Composite parent) {
		createNameAliasGroup(parent);
		createHutnFileGroup(parent);
		createLoadStoreOptionsGroup(parent);
	}
	
	private void createHutnFileGroup(Composite parent) {
		final Composite groupContent = DialogUtil.createGroupContainer(parent, "HUTN Source", 3);
		
		final Label hutnFileLabel = new Label(groupContent, SWT.NONE);
		hutnFileLabel.setText("HUTN file: ");
		
		hutnFileText = new Text(groupContent, SWT.BORDER);
		hutnFileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final Button browseHutnFile = new Button(groupContent, SWT.NONE);
		browseHutnFile.setText("Browse Workspace...");
		browseHutnFile.addListener(SWT.Selection, new BrowseWorkspaceForHutnSourceFilesListener(hutnFileText));
		
		groupContent.layout();
		groupContent.pack();
	}
	
	@Override
	protected void loadProperties(){
		super.loadProperties();
		
		if (properties != null) {;
			hutnFileText.setText(properties.getProperty(HutnModel.PROPERTY_SOURCE_FILE));
		}
	}
	
	@Override
	protected void storeProperties(){
		super.storeProperties();
		
		properties.put(HutnModel.PROPERTY_SOURCE_FILE, hutnFileText.getText());
	}
	
	
	protected class BrowseWorkspaceForHutnSourceFilesListener implements Listener {
		
		private final Text text;
		
		public BrowseWorkspaceForHutnSourceFilesListener(Text text) {
			this.text = text;
		}
		
		public void handleEvent(Event event) {
			final String file = BrowseWorkspaceUtil.browseFilePath(getShell(),
			                                                       "HUTN source files in the workspace",
			                                                       "Select a HUTN source file",
			                                                       "hutn",
			                                                       null);
			
			if (file != null){
				text.setText(file);
			}
		}
	}
}
