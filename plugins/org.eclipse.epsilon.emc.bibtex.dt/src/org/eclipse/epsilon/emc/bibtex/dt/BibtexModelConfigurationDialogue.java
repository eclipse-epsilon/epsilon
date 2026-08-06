/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.bibtex.dt;

import org.eclipse.epsilon.common.dt.launching.dialogs.AbstractModelConfigurationDialog;
import org.eclipse.epsilon.common.dt.launching.dialogs.BrowseWorkspaceUtil;
import org.eclipse.epsilon.common.dt.util.DialogUtil;
import org.eclipse.epsilon.emc.bibtex.BibtexModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class BibtexModelConfigurationDialogue extends AbstractModelConfigurationDialog {

	private Text bibtexFileText;
	
	@Override
	protected String getModelName() {
		return "BibTeX Model";
	}

	@Override
	protected String getModelType() {
		return "BIBTEX";
	}
	
	@Override
	protected void createGroups(Composite control) {
		createNameAliasGroup(control);
		createBibtexFileGroup(control);
		createLoadStoreOptionsGroup(control);
	}
	
	private void createBibtexFileGroup(Composite parent) {
		final Composite groupContent = DialogUtil.createGroupContainer(parent, "Bibtex Source", 3);
		
		final Label bibtexFileLabel = new Label(groupContent, SWT.NONE);
		bibtexFileLabel.setText("Bibtex file: ");
		
		bibtexFileText = new Text(groupContent, SWT.BORDER);
		bibtexFileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		final Button browseHutnFile = new Button(groupContent, SWT.NONE);
		browseHutnFile.setText("Browse Workspace...");
		browseHutnFile.addListener(SWT.Selection, new BrowseWorkspaceForBibtexSourceFilesListener(bibtexFileText));
		
		groupContent.layout();
		groupContent.pack();
	}
	
	@Override
	protected void loadProperties(){
		super.loadProperties();
		
		if (properties != null) {;
			bibtexFileText.setText(properties.getProperty(BibtexModel.PROPERTY_SOURCE_FILE));
		}
	}
	
	@Override
	protected void storeProperties(){
		super.storeProperties();
		
		properties.put(BibtexModel.PROPERTY_SOURCE_FILE, bibtexFileText.getText());
	}
	
	
	protected class BrowseWorkspaceForBibtexSourceFilesListener implements Listener {
		
		private final Text text;
		
		public BrowseWorkspaceForBibtexSourceFilesListener(Text text) {
			this.text = text;
		}
		
		@Override
		public void handleEvent(Event event) {
			final String file = BrowseWorkspaceUtil.browseFilePath(getShell(),
			                                                       "BibTeX source files in the workspace",
			                                                       "Select a BibTeX source file",
			                                                       "bib",
			                                                       null);
			
			if (file != null){
				text.setText(file);
			}
		}
	}


}
