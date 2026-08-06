/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.launching.tabs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.epsilon.common.dt.EpsilonCommonsPlugin;
import org.eclipse.epsilon.common.dt.launching.dialogs.AbstractModelConfigurationDialog;
import org.eclipse.epsilon.common.dt.launching.dialogs.ModelTypeSelectionDialog;
import org.eclipse.epsilon.common.dt.launching.extensions.ModelTypeExtension;
import org.eclipse.epsilon.common.dt.util.ListContentProvider;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;

public class ModelsConfigurationTab extends AbstractLaunchConfigurationTab{
	
	protected List<String> models = new ArrayList<>();
	private TableViewer modelsViewer;
	
	private final List<Button> modelControls = new LinkedList<>();

	@Override
	public void createControl(Composite parent) {
		
		GridLayout parentLayout = new GridLayout();
		parent.setLayout(parentLayout);

		Composite control = new Composite(parent, SWT.FILL);
		setControl(control);
		GridLayout controlLayout = new GridLayout(1, false);
		control.setLayout(controlLayout);
		
		createModelViewerControl(control);
		
		createBottomControl(control);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(control, "org.eclipse.epsilon.help.emc_dialogs");
		
		control.pack();
		control.layout();
		
		canSave();
	}

	private void createModelViewerControl(Composite parent) {
		Composite topControl = new Composite(parent, SWT.FILL);
		GridLayout topControlLayout = new GridLayout(2, false);
		topControl.setLayout(topControlLayout);
		
		GridData topControlData = new GridData(GridData.FILL_BOTH);
		topControl.setLayoutData(topControlData);

		modelsViewer = new TableViewer(topControl, SWT.BORDER);
		modelsViewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				new EditModelListener().handleEvent(null);
			}
		});
		
		modelsViewer.setContentProvider(new ListContentProvider());
		modelsViewer.setLabelProvider(new ModelLabelProvider());
		modelsViewer.setInput(models);
		
		
		GridData buttonsData = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);

		GridData viewerData = new GridData(GridData.FILL_BOTH);
		modelsViewer.getControl().setLayoutData(viewerData);
		modelsViewer.getControl().setFocus();

		Composite buttons = new Composite(topControl, SWT.FILL | SWT.TOP);
		buttons.setLayoutData(buttonsData);

		GridLayout buttonsLayout = new GridLayout(1, true);
		buttons.setLayout(buttonsLayout);

		createButton(buttons, "Add...").addListener(SWT.Selection, new AddModelListener());
		createButton(buttons, "Edit...").addListener(SWT.Selection, new EditModelListener());
		createButton(buttons, "Remove").addListener(SWT.Selection, new RemoveModelListener());
		createButton(buttons, "Duplicate").addListener(SWT.Selection, new DuplicateModelListener());
		//createButton(buttons, "Up");
		//createButton(buttons, "Down");
		//createButton(buttons, "Test");
	}
	
	/**
	 *  Subclasses may implement this method to populate the bottom portion
	 *  of the tab with further controls
	 */
	protected void createBottomControl(Composite parent) {}
	
	/**
	 *  The given listener will be notified of subsequent selections made on
	 *  the buttons used to manage model choices.
	 */
	protected void addListenerToButtonControls(SelectionListener listener) {
		for (Button control : modelControls) {
			control.addSelectionListener(listener);
		}
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			models = new ArrayList<>(configuration.getAttribute("models", new ArrayList<>()));
			modelsViewer.setInput(models);
			modelsViewer.refresh(true);
			canSave();
			updateLaunchConfigurationDialog();
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute("models", models);
	}

	@Override
	public String getName() {
		return "Models";
	}
	
	@Override
	public Image getImage() {
		return EpsilonCommonsPlugin.getDefault().createImage("icons/model.gif");
	}
	
	@Override
	public boolean canSave(){
		setErrorMessage(null);
		return true;
	}
	
	private Button createButton(Composite parent, String text) {
		Button button = new Button(parent, SWT.NONE);
		button.setText(text);
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		modelControls.add(button);
		
		return button;
	}
	
	class AddModelListener implements Listener{

		@Override
		public void handleEvent(Event event) {
			
			ModelTypeSelectionDialog dialog = new ModelTypeSelectionDialog(getShell());
			dialog.setBlockOnOpen(true);
			dialog.open();
			
			if (dialog.getReturnCode() == Window.OK){
				
				ModelTypeExtension modelType = dialog.getModelType();
				
				if (modelType == null) return;
				
				try {
					AbstractModelConfigurationDialog modelConfigurationDialog = modelType.createDialog();
					modelConfigurationDialog.setBlockOnOpen(true);
					modelConfigurationDialog.open();
					if (modelConfigurationDialog.getReturnCode() == Window.OK){
						models.add(modelConfigurationDialog.getProperties().toString());
						modelsViewer.refresh(true);
						canSave();
						updateLaunchConfigurationDialog();
					}
				} catch (Exception e) {
					//PDE.logException(e);
					e.printStackTrace();
				}

			}
		}
	}

	class EditModelListener implements Listener{

		@Override
		public void handleEvent(Event event) {
			
			IStructuredSelection selection = (IStructuredSelection) modelsViewer.getSelection();
			if (selection.getFirstElement() == null) return;
			
			StringProperties properties = new StringProperties();
			properties.load(selection.getFirstElement().toString());
			
			ModelTypeExtension modelType = ModelTypeExtension.forType(properties.getProperty("type"));
			
			AbstractModelConfigurationDialog dialog = null;
			try {
				dialog = modelType.createDialog();
			} catch (Exception e) {
				LogUtil.log(e);
				return;
			}
			dialog.setBlockOnOpen(true);
			
			dialog.setProperties(properties);
			dialog.open();
			if (dialog.getReturnCode() == Window.OK){
				int index = models.indexOf(selection.getFirstElement());
				models.add(index, dialog.getProperties().toString());
				models.remove(index + 1);
				modelsViewer.refresh(true);
				canSave();
				updateLaunchConfigurationDialog();				
			}
		}
	}	
	
	class RemoveModelListener implements Listener{

		@Override
		public void handleEvent(Event event) {
			IStructuredSelection selection = (IStructuredSelection) modelsViewer.getSelection();
			if (selection.getFirstElement() == null) return;
			int index = models.indexOf(selection.getFirstElement());
			models.remove(index);
			modelsViewer.refresh(true);
			canSave();
			updateLaunchConfigurationDialog();
		}
		
	}

	class DuplicateModelListener implements Listener{

		@Override
		public void handleEvent(Event event) {
			IStructuredSelection selection = (IStructuredSelection) modelsViewer.getSelection();
			if (selection.getFirstElement() == null) return;
			int index = models.indexOf(selection.getFirstElement());
			models.add(index, (String) selection.getFirstElement());
			modelsViewer.refresh(true);
			canSave();
			updateLaunchConfigurationDialog();
		}
		
	}
	class ModelLabelProvider implements ILabelProvider{

		@Override
		public Image getImage(Object element) {
			StringProperties properties = new StringProperties();
			properties.load(element.toString());
			ModelTypeExtension modelTypeExtension = ModelTypeExtension.forType(properties.getProperty("type"));
			if (modelTypeExtension != null) return modelTypeExtension.getImage();
			else return EpsilonCommonsPlugin.getDefault().createImage("icons/unknown.gif");
		}

		@Override
		public String getText(Object element) {
			StringProperties properties = new StringProperties();
			properties.load(element.toString());
			return properties.getProperty("name");
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
		}

		@Override
		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
