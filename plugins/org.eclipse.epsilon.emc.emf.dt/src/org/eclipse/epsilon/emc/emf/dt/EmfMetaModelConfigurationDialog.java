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
import org.eclipse.epsilon.common.dt.util.DialogUtil;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emf.dt.BrowseEPackagesListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class EmfMetaModelConfigurationDialog extends AbstractCachedModelConfigurationDialog {

	
	public EmfMetaModelConfigurationDialog(){
		super();
	}
	
	@Override
	protected String getModelName() {
		return "Registered EPackage";
	}
	
	@Override
	protected String getModelType() {
		return "EMF_M2";
	}
	
	protected Label metaModelUriLabel;
	protected Text metaModelUriText;
	protected Button browseMetamodelUri;
	
	
	@Override
	protected void createGroups(Composite control) {
		super.createGroups(control);
		createFilesGroup(control);
	}
	
	protected Composite createFilesGroup(Composite parent) {
		final Composite groupContent = DialogUtil.createGroupContainer(parent, "Files/URIs", 3);
		
		metaModelUriLabel = new Label(groupContent, SWT.NONE);
		metaModelUriLabel.setText("Meta-model URI: ");
		
		metaModelUriText = new Text(groupContent, SWT.BORDER);
		metaModelUriText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		browseMetamodelUri = new Button(groupContent, SWT.NONE);
		browseMetamodelUri.setText("Browse EPackages...");
		browseMetamodelUri.addListener(SWT.Selection, new BrowseEPackagesListener() {

			@Override
			public void selectionChanged(String ePackageUri) {
				metaModelUriText.setText(ePackageUri);
			}
			
		});
		
		Label emptyLabel = new Label(groupContent, SWT.NONE);
		emptyLabel.setText("");
		
		groupContent.layout();
		groupContent.pack();
		return groupContent;
	}

	
	@Override
	protected void loadProperties(){
		super.loadProperties();
		if (properties == null) return;
		metaModelUriText.setText(properties.getProperty(EmfModel.PROPERTY_METAMODEL_URI));
	}
	
	@Override
	protected void storeProperties(){
		super.storeProperties();
		properties.put(EmfModel.PROPERTY_METAMODEL_URI, metaModelUriText.getText());
	}

}
