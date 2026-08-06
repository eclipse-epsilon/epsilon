/*******************************************************************************
 * Copyright (c) 2008-2012 The University of York, Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio García-Domínguez - clean up, add "Force Exeed" check box
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed.modelink;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.epsilon.common.dt.launching.dialogs.BrowseWorkspaceUtil;
import org.eclipse.epsilon.dt.exeed.ExeedPlugin;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ConfigureLinkedModelsPage extends WizardPage {
	
	private ModeLink modeLink;
	private TableViewer modelsViewer;
	protected ModelPosition position;
	private Button btnUseExeed;
	
	protected ConfigureLinkedModelsPage(String pageName, ModeLink modeLink, ModelPosition position) {
		super(pageName);
		this.modeLink = modeLink;
		this.position = position;
		this.setTitle("Models in the " + position.toString().toLowerCase() + " panel");
		this.setDescription("Specify the models to be displayed in the " + position.toString().toLowerCase() + " panel of the editor");
	}
	
	public void createControl(Composite parent) {
		final Composite control = new Composite(parent, SWT.FILL);
		setControl(control);
		control.setLayout(new GridLayout(2, false));

		modelsViewer = new TableViewer(control, SWT.BORDER);
		modelsViewer.setContentProvider(new LinkedModelsContentProvider());
		modelsViewer.setLabelProvider(new ModelLabelProvider());
		modelsViewer.setInput(modeLink.getLinkedModels());
		modelsViewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));

		final Composite buttons = new Composite(control, SWT.FILL | SWT.TOP);
		createButton(buttons, "Add...").addListener(SWT.Selection, new AddModelListener());
		createButton(buttons, "Remove").addListener(SWT.Selection, new RemoveModelListener());
		buttons.setLayout(new GridLayout(1, true));
		buttons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

		btnUseExeed = new Button(control, SWT.CHECK);
		btnUseExeed.setText("Force E&xeed");
		btnUseExeed.setToolTipText(
			"Forces the usage of the Exeed editor, even if another default editor has been set." +
			" Required for the weaving model when using ModeLink's drag-and-drop weaving.");
		btnUseExeed.addSelectionListener(new UseExeedSelectionListener());
		btnUseExeed.setSelection(modeLink.isForceExeed(position));

		control.pack();
		control.layout();
	}

	
	private Button createButton(Composite parent, String text) {
		Button button = new Button(parent, SWT.NONE);
		button.setText(text);
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return button;
	}

	private class AddModelListener implements Listener{
		public void handleEvent(Event event) {
			IFile selection = BrowseWorkspaceUtil.browseFile(ConfigureLinkedModelsPage.this.getShell(), "Select a model", "Select a model", "", null);
			modeLink.getLinkedModels().add(new LinkedModel(selection.getFullPath().toOSString(), position.toString()));
			modelsViewer.refresh();
		}
	}

	private class RemoveModelListener implements Listener {
		public void handleEvent(Event event) {
			IStructuredSelection selection = (IStructuredSelection) modelsViewer.getSelection();
			if (selection.getFirstElement() == null) return;
			modeLink.getLinkedModels().remove(selection.getFirstElement());
			modelsViewer.refresh(true);
		}
	}

	private class ModelLabelProvider implements ILabelProvider {
		
		Image emfModelImage = ExeedPlugin.getDefault().getImageDescriptor("icons/emfmodel.gif").createImage();
		
		public Image getImage(Object element) {
			return emfModelImage;
		}

		public String getText(Object element) {
			return ((LinkedModel) element).getPath();
		}

		public void addListener(ILabelProviderListener listener) {
			// nothing
		}

		public void dispose() {
			// nothing
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
			// nothing
		}
	}
	
	private class LinkedModelsContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			ArrayList<LinkedModel> filtered = new ArrayList<>();
			for (LinkedModel m : modeLink.getLinkedModels()) {
				if (m.getPosition() == position) {
					filtered.add(m);
				}
			}
			return filtered.toArray();
		}

		public void dispose() {
			
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			
		}
	}

	private class UseExeedSelectionListener implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			modeLink.setForceExeed(position, btnUseExeed.getSelection());
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// nothing
		}
	}
}
