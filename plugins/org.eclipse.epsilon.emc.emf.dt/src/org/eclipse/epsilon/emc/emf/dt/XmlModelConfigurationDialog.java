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

import org.eclipse.epsilon.common.dt.launching.dialogs.AbstractCachedModelConfigurationDialog;
import org.eclipse.epsilon.common.dt.launching.dialogs.BrowseWorkspaceUtil;
import org.eclipse.epsilon.common.dt.util.DialogUtil;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emc.emf.xml.XmlModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class XmlModelConfigurationDialog extends AbstractCachedModelConfigurationDialog {
	 
	protected Button browseModelFile;
	protected Button browseXSDUri;
	protected Text modelUriText;
	protected Label metaModelUriLabel;
	protected Text metaModelUriText;
	protected Label modelUriLabel;
	
	public XmlModelConfigurationDialog() {
		super();
	}
	
	@Override
	protected String getModelName() {
		return "XML document backed by XSD (EMF)";
	}
	
	@Override
	protected void createGroups(Composite control) {
		super.createGroups(control);
		createFilesGroup(control);
		createLoadStoreOptionsGroup(control);
	}
		
	protected Composite createFilesGroup(Composite parent) {
		final Composite groupContent = DialogUtil.createGroupContainer(parent, "Files/URIs", 3);
		
		modelUriLabel = new Label(groupContent, SWT.NONE);
		modelUriLabel.setText("XML file: ");
		
		modelUriText = new Text(groupContent, SWT.BORDER);
		modelUriText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		browseModelFile = new Button(groupContent, SWT.NONE);
		browseModelFile.setText("Browse Workspace...");
		browseModelFile.addListener(SWT.Selection, new BrowseWorkspaceForModelsListener(modelUriText, "XML Documents in the workspace", "Select an XML document"));
		
		metaModelUriLabel = new Label(groupContent, SWT.NONE);
		metaModelUriLabel.setText("XSD Schema file: ");
		
		metaModelUriText = new Text(groupContent, SWT.BORDER);
		metaModelUriText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		browseXSDUri = new Button(groupContent, SWT.NONE);
		browseXSDUri.setText("Browse Workspace...");
		browseXSDUri.addListener(SWT.Selection, new BrowseWorkspaceForXSDListener(metaModelUriText));
		
		groupContent.layout();
		groupContent.pack();
		return groupContent;
	}

	protected class BrowseWorkspaceForXSDListener implements Listener{
		
		private Text text = null;
		
		public BrowseWorkspaceForXSDListener(Text text){
			this.text = text;
		}
		
		@Override
		public void handleEvent(Event event) {
			String file = BrowseWorkspaceUtil.browseFilePath(getShell(), 
					"XSD schemas in the workspace", "Select an XSD schema", "xsd", null);
			if (file != null){
				text.setText(file);
			}
		}
	}
	
	@Override
	protected String getModelType() {
		return "XML";
	}
	
	@Override
	protected void loadProperties() {
		super.loadProperties();
		if (properties == null) return;
		modelUriText.setText(properties.getProperty(EmfModel.PROPERTY_MODEL_URI));
		metaModelUriText.setText(properties.getProperty(XmlModel.PROPERTY_XSD_URI));
	}
	
	@Override
	protected void storeProperties() {
		super.storeProperties();
		// Create and persist URI values that are needed to construct an instance of EmfModel
		properties.put(EmfModel.PROPERTY_MODEL_URI, EmfUtil.createFullyQualifiedUri(modelUriText.getText()));
		properties.put(XmlModel.PROPERTY_XSD_URI, EmfUtil.createFullyQualifiedUri(metaModelUriText.getText()));
	}
}
