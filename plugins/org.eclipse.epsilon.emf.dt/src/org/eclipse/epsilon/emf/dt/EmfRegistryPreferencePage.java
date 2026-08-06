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
import java.util.List;

import org.eclipse.epsilon.common.dt.util.ListContentProvider;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class EmfRegistryPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private List<String> metamodels = null;
	private List<String> removedMetamodels = new ArrayList<>();
	private TableViewer metamodelsViewer;

	public EmfRegistryPreferencePage() {
	}

	public EmfRegistryPreferencePage(String title) {
		super(title);
	}

	public EmfRegistryPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	protected Control createContents(Composite parent) {
		metamodels = EmfRegistryManager.getInstance().getMetamodels();
		
		Composite control = new Composite(parent, SWT.FILL);
		GridLayout controlLayout = new GridLayout(2, false);
		control.setLayout(controlLayout);

		metamodelsViewer = new TableViewer(control, SWT.BORDER);

		metamodelsViewer.setContentProvider(new ListContentProvider());
		metamodelsViewer.setLabelProvider(new MetamodelLabelProvider());
		metamodelsViewer.setInput(metamodels);
		
		GridData buttonsData = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);

		GridData viewerData = new GridData(GridData.FILL_BOTH);
		metamodelsViewer.getControl().setLayoutData(viewerData);

		Composite buttons = new Composite(control, SWT.FILL | SWT.TOP);
		buttons.setLayoutData(buttonsData);

		GridLayout buttonsLayout = new GridLayout(1, true);
		buttons.setLayout(buttonsLayout);

		createButton(buttons, "Remove").addListener(SWT.Selection, new RemoveMetamodelListener());
		
		control.pack();
		control.layout();
		
		return control;
	}
	
	class MetamodelLabelProvider implements ILabelProvider {
		
		protected Image registerImage = EmfUtilPlugin.getImageDescriptor("icons/register.gif").createImage();
		
		public Image getImage(Object element) {
			return registerImage;
		}

		public String getText(Object element) {
			return element.toString();
		}

		public void addListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}

		public void dispose() {
			// TODO Auto-generated method stub
			
		}

		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
		}
		
	}
	
	class RemoveMetamodelListener implements Listener{

		public void handleEvent(Event event) {
			IStructuredSelection selection = (IStructuredSelection) metamodelsViewer.getSelection();
			if (selection.getFirstElement() == null) return;
			String metamodel = selection.getFirstElement().toString();
			int index = metamodels.indexOf(selection.getFirstElement());
			metamodels.remove(index);
			removedMetamodels.add(metamodel);
			metamodelsViewer.refresh(true);
		}
		
	}
	
	private Button createButton(Composite parent, String text) {
		Button button = new Button(parent, SWT.NONE);
		button.setText(text);
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return button;
	}

	public void init(IWorkbench workbench) {
		
	}
	
	@Override
	protected void performDefaults() {
		super.performDefaults();
		metamodels.clear();
		metamodels.addAll(EmfRegistryManager.getInstance().getMetamodels());
		metamodelsViewer.refresh();
	}

	@Override
	public boolean performOk() {
		for (String removedMetamodel : removedMetamodels) {
			EmfRegistryManager.getInstance().removeMetamodel(removedMetamodel);
		}
		return super.performOk();
	}

}
