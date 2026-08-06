/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.dt.wizards;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.common.dt.wizards.NewFileWizardPage;
import org.eclipse.epsilon.emf.dt.BrowseEPackagesListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewHutnFileWizardPage extends NewFileWizardPage {

	private Text metamodelUri;
	
	@Override
	protected void createExtraControls(Composite parent) {
		final Label metamodelUriLabel = new Label(parent, SWT.NULL);
		metamodelUriLabel.setText("Metamodel Ns&Uri:");
		
		metamodelUri = new Text(parent, SWT.BORDER | SWT.SINGLE);
		 
		metamodelUri.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		metamodelUri.addModifyListener(e -> dialogChanged());
		
		final Button browseMetamodelUri = new Button(parent, SWT.NONE);
		browseMetamodelUri.setText("Browse EPackages...");
		browseMetamodelUri.addListener(SWT.Selection, new BrowseEPackagesListener() {

			@Override
			public void selectionChanged(String ePackageUri) {
				metamodelUri.setText(ePackageUri);
			}
			
		});
	}
	
	public EPackage getSelectedEPackage() {
		return metamodelUri == null ? null : EPackage.Registry.INSTANCE.getEPackage(metamodelUri.getText());
	}	
}
