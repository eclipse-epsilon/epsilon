/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf.dt;

import org.eclipse.epsilon.common.dt.launching.dialogs.BrowseWorkspaceUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class EmfM0ModelConfigurationDialog extends EmfModelConfigurationDialog {
	
	private Label m0SpecificationFileLabel;
	private Text m0SpecificationFileText;
	private Button browseM0SpecificationFile;
	
	public EmfM0ModelConfigurationDialog(){
		super();
	}
	
	@Override
	protected String getModelName() {
		return "EMF M0 model";
	}
	
	@Override
	protected void createGroups(Composite control) {
		createNameAliasGroup(control);
		createEmfGroup(control);
		createFilesGroup(control);
		createLoadStoreOptionsGroup(control);
	}
	
	@Override
	protected Composite createFilesGroup(Composite parent) {
		final Composite filesGroupContent = super.createFilesGroup(parent);
		
		m0SpecificationFileLabel = new Label(filesGroupContent, SWT.NONE);
		m0SpecificationFileLabel.setText("M0 specification: ");
		
		m0SpecificationFileText = new Text(filesGroupContent, SWT.BORDER);
		m0SpecificationFileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		browseM0SpecificationFile = new Button(filesGroupContent, SWT.NONE);
		browseM0SpecificationFile.setText("Browse Workspace...");
		browseM0SpecificationFile.addListener(SWT.Selection, new BrowseWorkspaceForM0SpecificationsListener(m0SpecificationFileText));
		
		return filesGroupContent;
	}
		
	class BrowseWorkspaceForM0SpecificationsListener implements Listener{
		
		private Text text = null;
		
		public BrowseWorkspaceForM0SpecificationsListener(Text text){
			this.text = text;
		}
		
		public void handleEvent(Event event) {
			String file = BrowseWorkspaceUtil.browseFilePath(getShell(), 
					"M0 specifications in the workspace", "Select an EOL file", "eol", null);
			if (file != null){
				text.setText(file);
			}
		}
	}

	@Override
	protected void loadProperties(){
		super.loadProperties();
		if (properties == null) return;
		m0SpecificationFileText.setText(properties.getProperty("m0SpecificationFile"));
	}
	
	@Override
	protected void storeProperties(){
		super.storeProperties();
		properties.put("m0SpecificationFile", m0SpecificationFileText.getText());
	}
	
	@Override
	protected String getModelType() {
		return "EMFM0";
	}
}
