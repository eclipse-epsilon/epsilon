/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emf.dt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.dialogs.IDialogPage;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (ecore).
 */

public class NewEmfModelWizardPage extends WizardPage {
	
	protected Text containerText;
	protected Text fileText;
	protected ISelection selection;
	protected Text rootClassText;
	
	protected Text metamodelUriText;
	protected Button browseMetamodelUriButton;
	
	protected Combo rootClassCombo;
	//protected Combo metamodelUriCombo;
	
	protected String metamodelUri;
	protected String rootClass;
	protected String modelType;
	protected String modelExtension;
	
	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public NewEmfModelWizardPage(ISelection selection, String metamodelUri, String rootClass, String modelType, String modelExtension) {
		super("wizardPage");
		this.metamodelUri = metamodelUri;
		this.modelType = modelType;
		this.rootClass = rootClass;
		this.modelExtension = modelExtension;
		setTitle(modelType + " Model");
		setDescription("This wizard creates a new " + modelType + " model.");
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText("&Container:");

		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		containerText.setLayoutData(gd);
		containerText.addModifyListener(e -> dialogChanged());

		Button button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText("&File name:");

		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fileText.setLayoutData(gd);
		fileText.addModifyListener(e -> dialogChanged());
	
		new Label(container, SWT.NONE);
		
		Label namespaceLabel = new Label(container, SWT.NULL);
		namespaceLabel.setText("&Metamodel URI");
		
		metamodelUriText = new Text(container,SWT.BORDER|SWT.SINGLE);
		
		gd = new GridData(GridData.FILL_HORIZONTAL);
		metamodelUriText.setLayoutData(gd);
		
		browseMetamodelUriButton = new Button(container, SWT.PUSH);
		browseMetamodelUriButton.setText("Browse...");
		browseMetamodelUriButton.addListener(SWT.Selection, new BrowseEPackagesListener() {

			@Override
			public void selectionChanged(String ePackageUri) {
				metamodelUriText.setText(ePackageUri);
				populateRootClassCombo(ePackageUri);
			}
			
		});
		
		//Iterator it = EPackage.Registry.INSTANCE.keySet().iterator();
		
		//while (it.hasNext()){
		//	metamodelUriCombo.add(it.next().toString());
		//}
		
		//new Label(container, SWT.NONE);
		
		Label rootClassLabel = new Label(container, SWT.NULL);
		rootClassLabel.setText("&Root instance type");
		
		rootClassCombo = new Combo(container,SWT.BORDER|SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		rootClassCombo.setLayoutData(gd);
		
		/*
		metamodelUriCombo.addSelectionListener(new SelectionListener(){

			public void widgetSelected(SelectionEvent e) {
				populateRootClassCombo(((Combo)e.widget).getText());
			}

			public void widgetDefaultSelected(SelectionEvent e) {

			}
			
		});
		
		if (metamodelUri != null) {
			metamodelUriCombo.setText(metamodelUri);
			int index = 0;
			for (String item : metamodelUriCombo.getItems()) {
				if (item.equalsIgnoreCase(metamodelUri)) {
					metamodelUriCombo.select(index);
					populateRootClassCombo(metamodelUri);
				}
				else {
					index++;
				}
			}
			namespaceLabel.setVisible(false);
			metamodelUriCombo.setVisible(false);
		}
		
		if (rootClass != null) {
			rootClassCombo.setText(rootClass);
			rootClassLabel.setVisible(false);
			rootClassCombo.setVisible(false);
		}
		*/
		
		initialize();
		dialogChanged();
		setControl(container);
		fileText.setFocus();
		fileText.selectAll();
	}
	
	
	void populateRootClassCombo(String namespaceUri) {
		rootClassCombo.removeAll();
		ArrayList<String> classNames = new ArrayList<>();
		EPackage ePackage = (EPackage) EPackage.Registry.INSTANCE.get(namespaceUri);
		Iterator<EObject> it = ePackage.eAllContents();
		while (it.hasNext()){
			EObject next = it.next();
			if (next instanceof EClass){
				EClass eClass = (EClass) next;
				if (!eClass.isAbstract()) {
					classNames.add(eClass.getName());
					//rootClassCombo.add(eClass.getName());
				}
			}
		}
		
		Collections.sort(classNames);
		
		for (String className : classNames) {
			rootClassCombo.add(className);
		}
	}
	
	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
				containerText.setText(container.getFullPath().toString());
			}
		}
		if (modelExtension == null) {
			fileText.setText("untitled.model");
		}
		else {
			fileText.setText("untitled." + modelExtension);
		}
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	private void handleBrowse() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
				"Select new file container");
		if (dialog.open() == Window.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				containerText.setText(((Path) result[0]).toString());
			}
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		IResource container = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(new Path(getContainerName()));
		String fileName = getFileName();

		if (getContainerName().length() == 0) {
			updateStatus("File container must be specified");
			return;
		}
		if (container == null
				|| (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			updateStatus("File container must exist");
			return;
		}
		if (!container.isAccessible()) {
			updateStatus("Project must be writable");
			return;
		}
		if (fileName.length() == 0) {
			updateStatus("File name must be specified");
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus("File name must be valid");
			return;
		}
		
		int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc != -1) {
			String ext = fileName.substring(dotLoc + 1);
			if (modelExtension != null && !ext.equalsIgnoreCase(modelExtension)) {
				updateStatus("File extension must be \"" + modelExtension + "\"");
				return;
			}
		}
		
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getContainerName() {
		return containerText.getText();
	}

	public String getFileName() {
		return fileText.getText();
	}
	
	public String getRootClass(){
		return rootClassCombo.getText();
	}

	public String getMetaModelUri(){
		return metamodelUriText.getText();
	}
	
}
