/*******************************************************************************
 * Copyright (c) 2008-2012 The University of York, Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio García-Domínguez - allow loading more than one metamodel
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf.dt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.common.dt.launching.dialogs.AbstractCachedModelConfigurationDialog;
import org.eclipse.epsilon.common.dt.launching.dialogs.BrowseWorkspaceUtil;
import org.eclipse.epsilon.common.dt.util.DialogUtil;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emf.dt.BrowseEPackagesListener;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class EmfModelConfigurationDialog extends AbstractCachedModelConfigurationDialog {

	private static final class MetamodelListLabelProvider implements ILabelProvider {
		private Image imgFile, imgURI;

		public MetamodelListLabelProvider() {
			imgFile = Activator.getDefault().getImageDescriptor("icons/ecorefile.gif").createImage();
			imgURI = Activator.getDefault().getImageDescriptor("icons/epackage.png").createImage();
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
			// we don't need any listeners (cells are only added or removed, not edited)
		}

		@Override
		public void dispose() {
			imgFile.dispose();
			imgURI.dispose();
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			// we don't need any listeners (cells are only added or removed, not edited)
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof String) {
				return imgFile;
			}
			else if (element instanceof URI) {
				return imgURI;
			}
			return null;
		}

		@Override
		public String getText(Object element) {
			return element + "";
		}
	}

	@SuppressWarnings("deprecation")
	private static final String PROPERTY_IS_METAMODEL_FILE_BASED = EmfModel.PROPERTY_IS_METAMODEL_FILE_BASED;

	/**
	 * Key used to store the raw (i.e. unqualified URI) model file value. 
	 */
	// The deprecated property is used for backwards-compatibility with legacy launch configurations. See Bug 341481.
	private final static String PROPERTY_MODEL_FILE     = EmfModel.PROPERTY_MODEL_FILE;

	/**
	 * Key used to store the raw (i.e. unqualified URI) metamodel file value. 
	 */
	// The deprecated property is used for backwards-compatibility with legacy launch configurations. See Bug 341481.
	private final static String PROPERTY_METAMODEL_FILE = EmfModel.PROPERTY_METAMODEL_FILE;

	protected Button expandButton;
	protected Button validateButton;
	private Text modelFileText;
	private TableViewer metamodelList;

	// May contain metamodel URIs (as URI objects) or String (file-based metamodel paths) objects
	private List<Object> metamodels = new ArrayList<>();

	private Button reuseUnmodifiedFileBasedMetamodelsButton;
	
	@Override
	protected String getModelName() {
		return "EMF model";
	}
	
	@Override
	protected void createGroups(Composite control) {
		super.createGroups(control);
		createEmfGroup(control);
		createFilesGroup(control);
		createLoadStoreOptionsGroup(control);
	}
	
	@Override
	protected String getModelType() {
		return "EMF";
	}
	
	@Override
	protected void loadProperties() {
		super.loadProperties();
		if (properties == null) return;

		metamodels.clear();

		// Restore values from legacy launch configuration
		modelFileText.setText(properties.getProperty(PROPERTY_MODEL_FILE));
		final String sLegacyFileMetamodels = properties.getProperty(PROPERTY_METAMODEL_FILE);
		for (String sPath : sLegacyFileMetamodels.trim().split("\\s*,\\s*")) {
			if (sPath.length() > 0) {
				metamodels.add(sPath);
			}
		}

		// Restore values that are used directly to construct an instance of EmfModel
		final String sURIMetamodels = properties.getProperty(EmfModel.PROPERTY_METAMODEL_URI);
		for (String sURI : sURIMetamodels.trim().split("\\s*,\\s*")) {
			if (sURI.length() > 0) {
				metamodels.add(URI.createURI(sURI));
			}
		}
		expandButton.setSelection(new Boolean(properties.getProperty(EmfModel.PROPERTY_EXPAND)).booleanValue());
		validateButton.setSelection(new Boolean(properties.getProperty(EmfModel.PROPERTY_VALIDATE, "false")).booleanValue());

		metamodelList.refresh();
	}
	
	@Override
	protected void storeProperties() {
		super.storeProperties();

		/*
		 * Compute comma-separated lists with the file paths and URIs. If we
		 * only have one metamodel (either file- or URI-based), it should be
		 * compatible with previous versions of Epsilon.
		 */
		final StringBuilder sbFileMetamodels = new StringBuilder();
		final StringBuilder sbFileMetamodelURIs = new StringBuilder();
		final StringBuilder sbURIMetamodels = new StringBuilder();
		boolean bFirstFileMetamodel = true, bFirstURIMetamodel = true;
		for (Object o : metamodels) {
			if (o instanceof String) {
				if (!bFirstFileMetamodel) {
					sbFileMetamodelURIs.append(',');
					sbFileMetamodels.append(',');
				}
				else {
					bFirstFileMetamodel = false;
				}
				sbFileMetamodels.append((String)o);
				sbFileMetamodelURIs.append(createFullyQualifiedUri((String)o));
			}
			else if (o instanceof URI) {
				if (!bFirstURIMetamodel) {
					sbURIMetamodels.append(',');
				}
				else {
					bFirstURIMetamodel = false;
				}
				sbURIMetamodels.append(o.toString());
			}
		}
		properties.put(PROPERTY_MODEL_FILE,     modelFileText.getText());
		properties.put(PROPERTY_METAMODEL_FILE, sbFileMetamodels.toString());

		// Persist values that are used directly to construct an instance of EmfModel (legacy - only one metamodel was supported)
		properties.put(EmfModel.PROPERTY_METAMODEL_URI, sbURIMetamodels.toString());
		properties.put(EmfModel.PROPERTY_EXPAND, expandButton.getSelection() + "");
		properties.put(EmfModel.PROPERTY_VALIDATE, validateButton.getSelection() + "");
		properties.put(PROPERTY_IS_METAMODEL_FILE_BASED, "".equals(sbURIMetamodels.toString()));

		// Create and persist URI values that are needed to construct an instance of EmfModel
		properties.put(EmfModel.PROPERTY_MODEL_URI, createFullyQualifiedUri(modelFileText.getText()));
		properties.put(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, sbFileMetamodelURIs.toString());
		properties.put(EmfModel.PROPERTY_REUSE_UNMODIFIED_FILE_BASED_METAMODELS, reuseUnmodifiedFileBasedMetamodelsButton.getSelection() + "");
	}

	protected void createEmfGroup(Composite parent) {
		final Composite groupContent = DialogUtil.createGroupContainer(parent, "EMF", 3);
	
		expandButton = new Button(groupContent, SWT.CHECK);
		expandButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		expandButton.setText("Include external references");
		expandButton.setSelection(true);
		
		GridData expandButtonData = new GridData();
		expandButtonData.horizontalSpan = 2;
		expandButton.setLayoutData(expandButtonData);

		reuseUnmodifiedFileBasedMetamodelsButton = new Button(groupContent, SWT.CHECK);
		reuseUnmodifiedFileBasedMetamodelsButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		reuseUnmodifiedFileBasedMetamodelsButton.setText("Reuse unmodified file-based metamodels");
		reuseUnmodifiedFileBasedMetamodelsButton.setSelection(true);

		GridData reuseUnmodifiedFileBasedMetamodelsButtonData = new GridData();
		reuseUnmodifiedFileBasedMetamodelsButtonData.horizontalSpan = 2;
		reuseUnmodifiedFileBasedMetamodelsButton.setLayoutData(reuseUnmodifiedFileBasedMetamodelsButtonData);

		validateButton = new Button(groupContent, SWT.CHECK);
		validateButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		validateButton.setText("Validate model");
		validateButton.setSelection(false);
		
		GridData validateButtonData = new GridData();
		validateButtonData.horizontalSpan = 2;
		validateButton.setLayoutData(validateButtonData);
		
		groupContent.layout();
		groupContent.pack();
	}

	protected Composite createFilesGroup(Composite parent) {
		final Composite groupContent = DialogUtil.createGroupContainer(parent, "Files/URIs", 3);
		((GridData)groupContent.getParent().getLayoutData()).grabExcessVerticalSpace = true;

		final Label modelFileLabel = new Label(groupContent, SWT.NONE);
		modelFileLabel.setText("Model file: ");
		
		modelFileText = new Text(groupContent, SWT.BORDER);
		modelFileText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		final Button browseModelFile = new Button(groupContent, SWT.NONE);
		browseModelFile.setText("Browse Workspace...");
		browseModelFile.addListener(SWT.Selection,
			new BrowseWorkspaceForModelsListener(modelFileText, "EMF models in the workspace", "Select an EMF model") {
			@Override
			public void handleEvent(Event event) {
				super.handleEvent(event);
				Collection<EPackage> ePackages = findEPackages(modelFileText.getText());
				if (ePackages.size() > 0) {
					for (EPackage ePkg : ePackages) {
						final URI uri = URI.createURI(ePkg.getNsURI());
						if (!metamodels.contains(uri)) {
							metamodels.add(uri);
						}
					}
					metamodelList.refresh();
				}
			}
		});

		final Label metamodelListLabel = new Label(groupContent, SWT.LEFT | SWT.TOP);
		final GridData metamodelListLabelLayout = new GridData(SWT.LEFT, SWT.TOP, false, false);
		metamodelListLabelLayout.verticalIndent = 4;
		metamodelListLabel.setLayoutData(metamodelListLabelLayout);
		metamodelListLabel.setText("Metamodels:");

		metamodelList = new TableViewer(groupContent);
		metamodelList.setContentProvider(ArrayContentProvider.getInstance());
		metamodelList.setInput(metamodels);
		metamodelList.setLabelProvider(new MetamodelListLabelProvider());
		GridData metamodelListLayout = new GridData(SWT.FILL, SWT.FILL, true, true);
		metamodelList.getControl().setLayoutData(metamodelListLayout);

		final Composite metamodelButtons = new Composite(groupContent, SWT.NONE);
		final GridData metamodelButtonsLayout = new GridData();
		metamodelButtonsLayout.horizontalAlignment = SWT.FILL;
		metamodelButtons.setLayoutData(metamodelButtonsLayout);
		metamodelButtons.setLayout(new FillLayout(SWT.VERTICAL));
		final Button addFileMetamodelButton = new Button(metamodelButtons, SWT.NONE);
		addFileMetamodelButton.setText("Add file...");
		addFileMetamodelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final String path = BrowseWorkspaceUtil.browseFilePath(getShell(),
						"EMF meta-models in the workspace",
						"Select an EMF meta-model (ECore)",
						"ecore", null);
				if (path != null && !metamodels.contains(path)) {
					metamodels.add(path);
					metamodelList.refresh();
				}
			}
		});

		final Button addURIMetamodelButton = new Button(metamodelButtons, SWT.NONE);
		addURIMetamodelButton.setText("Add URI...");
		addURIMetamodelButton.addListener(SWT.Selection, new BrowseEPackagesListener(true) {
			@Override
			public void selectionChanged(String ePackageUri) {
				URI uri = URI.createURI(ePackageUri);
				if (!metamodels.contains(uri)) {
					metamodels.add(uri);
					metamodelList.refresh();
				}
			}
		});

		final Button removeMetamodelButton = new Button(metamodelButtons, SWT.NONE);
		removeMetamodelButton.setText("Remove");
		removeMetamodelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (metamodelList.getSelection() instanceof IStructuredSelection) {
					final IStructuredSelection sel = (IStructuredSelection)metamodelList.getSelection();
					for (Iterator<?> it = sel.iterator(); it.hasNext(); ) {
						metamodels.remove(it.next());
					}
					metamodelList.refresh();
				}
			}
		});

		final Button clearAllMetamodelsButton = new Button(metamodelButtons, SWT.NONE);
		clearAllMetamodelsButton.setText("Clear");
		clearAllMetamodelsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				metamodels.clear();
				metamodelList.refresh();
			}
		});

		groupContent.layout();
		groupContent.pack();
		return groupContent;
	}

	private String createFullyQualifiedUri(String relativePath) {
		if (relativePath == null || relativePath.isEmpty())
			return "";
		else
			return EmfUtil.createPlatformResourceURI(relativePath).toString();
	}
	
	/**
	 * This can be a time consuming operation since the model and all its references are loaded.
	 * @param resourcePath
	 * @return
	 */
	private Collection<EPackage> findEPackages(String resourcePath) {
		Set<EPackage> ePackages = new HashSet<>();
		
		try {
			ResourceSet rs = new ResourceSetImpl();
			Resource r = rs.createResource(URI.createPlatformResourceURI(resourcePath, true));

			// Some loaders don't handle well the case where the map is null (e.g. Sirius .odesign)
			r.load(Collections.EMPTY_MAP);

			if (expandButton.getSelection()) {
				EcoreUtil.resolveAll(r);
			}
			for (Resource res : rs.getResources()) {
				Iterator<EObject> it = res.getAllContents();
				while (it.hasNext()) {
					ePackages.add(EmfUtil.getTopEPackage(it.next().eClass().getEPackage()));
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return ePackages;
	}
	
}
