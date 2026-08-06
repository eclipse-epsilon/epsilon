/*******************************************************************************
 * Copyright (c) 2016-2022 University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Antonio García-Domínguez - initial API and implementation
 *******************************************************************************/
package org.eclipse.epsilon.emc.cdo.dt;


import org.eclipse.epsilon.common.dt.launching.dialogs.AbstractModelConfigurationDialog;
import org.eclipse.epsilon.emc.cdo.CDOModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Configuration dialog for CDO models in the Epsilon run configuration dialogs.
 */
public class CDOModelConfigurationDialog extends AbstractModelConfigurationDialog {

	private Text txtURL;
	private Text txtName;
	private Text txtPath;
	private Text txtBranch;
	private Text txtCDOInitial;
	private Text txtCDORChunk;
	private Text txtCDORPrefetch;
	private Button btnCDOFeatAnalyzer;
	private Button btnCreateIfMissing;

	@Override
	protected String getModelName() {
		return "CDO Model";
	}

	@Override
	protected String getModelType() {
		return "cdo";
	}

	@Override
	protected void createGroups(Composite parent) {
		super.createGroups(parent);
		createAccessGroup(parent);
		createPrefetchGroup(parent);
		createLoadStoreOptionsGroup(parent);
	}

	protected void createPrefetchGroup(Composite parent) {
		final Composite groupContent = createGroupContainer(parent, "Prefetching", 2);

		final Label lblCDOInitial = new Label(groupContent, SWT.NONE);
		lblCDOInitial.setText("Initial collection prefetch size:");
		txtCDOInitial = new Text(groupContent, SWT.BORDER);
		txtCDOInitial.setText("0");
		txtCDOInitial.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		final Label lblCDORChunk = new Label(groupContent, SWT.NONE);
		lblCDORChunk.setText("Collection resolving chunk size:");
		txtCDORChunk = new Text(groupContent, SWT.BORDER);
		txtCDORChunk.setText("300");
		txtCDORChunk.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		final Label lblCDORPrefetch = new Label(groupContent, SWT.NONE);
		lblCDORPrefetch.setText("Revision prefetch size:");
		txtCDORPrefetch = new Text(groupContent, SWT.BORDER);
		txtCDORPrefetch.setText("100");
		txtCDORPrefetch.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		btnCDOFeatAnalyzer = new Button(groupContent, SWT.CHECK);
		btnCDOFeatAnalyzer.setText("Use CDO model-based feature analyzer");
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		btnCDOFeatAnalyzer.setLayoutData(gd);
	}

	protected void createAccessGroup(Composite parent) {
		final Composite groupContent = createGroupContainer(parent, "Repository access", 2);
		
		final Label lblURL = new Label(groupContent, SWT.NONE);
		lblURL.setText("URL:");
		txtURL = new Text(groupContent, SWT.BORDER);
		txtURL.setText("tcp://localhost:2036");
		txtURL.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		final Label lblName = new Label(groupContent, SWT.NONE);
		lblName.setText("Repository:");
		txtName = new Text(groupContent, SWT.BORDER);
		txtName.setText("repo1");
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		final Label lblBranch = new Label(groupContent, SWT.NONE);
		lblBranch.setText("Branch:");
		txtBranch = new Text(groupContent, SWT.BORDER);
		txtBranch.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		txtBranch.setToolTipText("Path to the branch (e.g. MAIN/branch1). Leave empty to use the main branch.");

		final Label lblPath = new Label(groupContent, SWT.NONE);
		lblPath.setText("Path:");
		txtPath = new Text(groupContent, SWT.BORDER);
		txtPath.setText("/input.xmi");
		txtPath.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		final Label lblCreateIfMissing = new Label(groupContent, SWT.NONE);
		lblCreateIfMissing.setText("Create if missing:");
		btnCreateIfMissing = new Button(groupContent, SWT.CHECK);
	}

	@Override
	protected void loadProperties() {
		super.loadProperties();
		if (properties != null) {
			txtURL.setText((String) properties.get(CDOModel.PROPERTY_CDO_URL));
			txtName.setText((String) properties.get(CDOModel.PROPERTY_CDO_NAME));
			txtPath.setText((String) properties.get(CDOModel.PROPERTY_CDO_PATH));
			btnCreateIfMissing.setSelection(properties.hasProperty(CDOModel.PROPERTY_CDO_CREATE_MISSING));
			if (properties.hasProperty(CDOModel.PROPERTY_CDO_BRANCH)) {
				txtBranch.setText((String) properties.get(CDOModel.PROPERTY_CDO_BRANCH));
			}

			if (properties.hasProperty(CDOModel.PROPERTY_CDO_COLLECTION_INITIAL)) {
				txtCDOInitial.setText((String) properties.get(CDOModel.PROPERTY_CDO_COLLECTION_INITIAL));
			}
			if (properties.hasProperty(CDOModel.PROPERTY_CDO_COLLECTION_RCHUNK)) {
				txtCDORChunk.setText((String) properties.get(CDOModel.PROPERTY_CDO_COLLECTION_RCHUNK));
			}
			if (properties.hasProperty(CDOModel.PROPERTY_CDO_REVPREFETCH)) {
				txtCDORPrefetch.setText((String) properties.get(CDOModel.PROPERTY_CDO_REVPREFETCH));
			}
			btnCDOFeatAnalyzer.setSelection(properties.hasProperty(CDOModel.PROPERTY_CDO_FEATANALYZER));
		}
	}

	@Override
	protected void storeProperties() {
		super.storeProperties();

		// Access
		properties.put(CDOModel.PROPERTY_CDO_URL, txtURL.getText());
		properties.put(CDOModel.PROPERTY_CDO_NAME, txtName.getText());
		properties.put(CDOModel.PROPERTY_CDO_PATH, txtPath.getText());
		if (btnCreateIfMissing.getSelection()) {
			properties.put(CDOModel.PROPERTY_CDO_CREATE_MISSING, "1");
		}
		properties.put(CDOModel.PROPERTY_CDO_BRANCH, txtBranch.getText());

		// Prefetch
		properties.put(CDOModel.PROPERTY_CDO_COLLECTION_INITIAL, txtCDOInitial.getText());
		properties.put(CDOModel.PROPERTY_CDO_COLLECTION_RCHUNK, txtCDORChunk.getText());
		properties.put(CDOModel.PROPERTY_CDO_REVPREFETCH, txtCDORPrefetch.getText());
		if (btnCDOFeatAnalyzer.getSelection()) {
			properties.put(CDOModel.PROPERTY_CDO_FEATANALYZER, "1");
		}
	}

}
