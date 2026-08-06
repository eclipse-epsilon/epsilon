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

import java.util.ArrayList;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.epsilon.common.dt.EpsilonCommonsPlugin;
import org.eclipse.epsilon.common.dt.launching.extensions.ModelTypeExtension;
import org.eclipse.epsilon.common.dt.launching.tabs.ModelTypeLabelProvider;
import org.eclipse.epsilon.common.dt.util.ListContentProvider;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class ModelTypeSelectionDialog extends TitleAreaDialog implements ISelectionChangedListener {
	
	protected ArrayList<ModelTypeExtension> modelTypes;
	protected TableViewer modelTypesViewer;
	protected ModelTypeExtension modelType;
	protected Button showAllButton;
	
	public ModelTypeSelectionDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected void setShellStyle(int newShellStyle) {
	   super.setShellStyle(newShellStyle | SWT.RESIZE);
	}
	
	@Override
	protected Rectangle getConstrainedShellBounds(Rectangle preferredSize) {
		preferredSize.height = 350;
		return super.getConstrainedShellBounds(preferredSize);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		
		Composite dialogArea = (Composite) super.createDialogArea(parent);
		GridData dialogAreaData = new GridData(GridData.FILL_BOTH);
		dialogArea.setLayoutData(dialogAreaData);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, "org.eclipse.epsilon.help.emc_dialogs");
		
		this.setTitle("Select type of model");
		this.setMessage("Select the type of model to add");
		this.getShell().setText("Select type of model");
		this.getShell().setImage(EpsilonCommonsPlugin.getDefault().createImage("icons/model.gif"));
		
		//GridLayout controlLayout = new GridLayout(1, false);
		//control.setLayout(controlLayout);
		
		Composite control = new Composite(dialogArea, SWT.NONE);
		GridLayout controlLayout = new GridLayout(1, false);
		control.setLayout(controlLayout);
		GridData controlData = new GridData(GridData.FILL_BOTH);
		//controlData.horizontalIndent = 10;
		//controlData.verticalIndent = 10;
		control.setLayoutData(controlData);
		
		findModelTypes();
		
		modelTypesViewer = new TableViewer(control, SWT.BORDER);
		
		
		GridData viewerData = new GridData(GridData.FILL_BOTH);
		modelTypesViewer.getControl().setLayoutData(viewerData);
		modelTypesViewer.getControl().setFocus();
		
		showAllButton = new Button(control, SWT.CHECK);
		GridData showAllButtonGridData = new GridData(GridData.FILL_HORIZONTAL);
		showAllButtonGridData.horizontalAlignment = SWT.END;
		showAllButton.setLayoutData(showAllButtonGridData);
		showAllButton.setText("Show all model types");
		showAllButton.setSelection(false);
		
		//modelTypesViewer.getControl().setLayoutData(viewerData);
		
		modelTypesViewer.setContentProvider(new ListContentProvider());
		modelTypesViewer.setInput(getStableModelTypeExtensions());
		modelTypesViewer.addSelectionChangedListener(this);
		modelTypesViewer.setLabelProvider(new ModelTypeLabelProvider());
		modelTypesViewer.getControl().addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				ModelTypeSelectionDialog.this.modelType = (ModelTypeExtension)((IStructuredSelection)modelTypesViewer.getSelection()).getFirstElement();
				if (ModelTypeSelectionDialog.this.modelType != null) {
					ModelTypeSelectionDialog.this.close();
				}
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		modelTypesViewer.refresh();
		

		showAllButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				
				ArrayList<ModelTypeExtension> filtered;
				
				if (showAllButton.getSelection()) {
					filtered = modelTypes;
				}
				else {
					filtered = getStableModelTypeExtensions();
				}
				
				modelTypesViewer.setInput(filtered);
				modelTypesViewer.refresh();
				
			}
		});
		
		return control;
	}
	
	private ArrayList<ModelTypeExtension> getStableModelTypeExtensions() {
		ArrayList<ModelTypeExtension> filtered = new ArrayList<>();
		for (ModelTypeExtension ext : modelTypes) {
			if (ext.isStable()) {
				filtered.add(ext);
			}
		}
		return filtered;
	}
	
	private void findModelTypes() {		
		modelTypes = new ArrayList<>();
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		
		IExtensionPoint extensionPoint = registry.getExtensionPoint("org.eclipse.epsilon.common.dt.modelType");
		IConfigurationElement[] configurationElements =  extensionPoint.getConfigurationElements();
		
		try {
			for (int i=0;i<configurationElements.length; i++){
				IConfigurationElement configurationElement = configurationElements[i];
				ModelTypeExtension modelType = new ModelTypeExtension();
				modelType.setClazz(configurationElement.getAttribute("class"));
				modelType.setType(configurationElement.getAttribute("type"));
				modelType.setLabel(configurationElement.getAttribute("label"));
				modelType.setStable(Boolean.parseBoolean(configurationElement.getAttribute("stable")));
				modelType.setContributingPlugin(configurationElement.getDeclaringExtension().getNamespaceIdentifier());
				modelType.setConfigurationElement(configurationElement);
				modelTypes.add(modelType);
			}
		}
		catch (Exception ex) {
			LogUtil.log(ex);
		}
		
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		this.modelType = (ModelTypeExtension)((IStructuredSelection)event.getSelection()).getFirstElement();
	}
	
	public ModelTypeExtension getModelType(){
		return modelType;
	}

}
