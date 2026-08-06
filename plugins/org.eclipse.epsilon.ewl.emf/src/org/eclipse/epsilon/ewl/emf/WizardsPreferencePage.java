/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ewl.emf;

import java.util.List;

import org.eclipse.epsilon.common.dt.util.ListContentProvider;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class WizardsPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	
	public class RemoveListener implements Listener {

		@Override
		public void handleEvent(Event event) {
			WizardsExtensionPreference preference = (WizardsExtensionPreference) ((IStructuredSelection) viewer.getSelection()).getFirstElement();
			if (preference!=null) {
				preferences.remove(preference);
				viewer.refresh();
			}
		}

	}

	public class AddListener implements Listener {

		@Override
		public void handleEvent(Event event) {
			WizardsExtensionPreference preference = new WizardsExtensionPreference();
			WizardsExtensionPreferenceDialog dialog = new WizardsExtensionPreferenceDialog(getShell());
			dialog.setPreference(preference);
			if (dialog.open() == Window.OK) {
				preferences.add(preference);
				viewer.refresh();
			}
		}

	}

	public class EditListener implements Listener {

		@Override
		public void handleEvent(Event event) {
			WizardsExtensionPreference preference = (WizardsExtensionPreference) ((IStructuredSelection) viewer.getSelection()).getFirstElement();
			if (preference!=null) {
				WizardsExtensionPreferenceDialog dialog = new WizardsExtensionPreferenceDialog(getShell());
				dialog.setPreference(preference);
				dialog.open();
				viewer.refresh();
			}
		}

	}

	TableViewer viewer;
	protected List<WizardsExtensionPreference> preferences;
	
	@Override
	protected Control createContents(Composite parent) {
		Composite control = new Composite(parent, SWT.FILL);
		//setControl(control);
		GridLayout controlLayout = new GridLayout(2, false);
		viewer = new TableViewer(control,SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		control.setLayout(controlLayout);
		
		
		TableColumn namespaceURIcolumn = new TableColumn(viewer.getTable(),SWT.NONE);
		namespaceURIcolumn.setText("Namespace URI");
		namespaceURIcolumn.setWidth(100);
		
		TableColumn wizardsColumn = new TableColumn(viewer.getTable(),SWT.NONE);
		wizardsColumn.setText("Wizards");
		wizardsColumn.setWidth(200);
		
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		
		
		preferences = WizardsExtensionPreference.getPreferences();
		viewer.setContentProvider(new ListContentProvider());
		viewer.setInput(preferences);
		viewer.setLabelProvider(new WizardsPreferenceLabelProvider());
		
		GridData buttonsData = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);

		GridData viewerData = new GridData(GridData.FILL_BOTH);
		viewer.getControl().setLayoutData(viewerData);

		Composite buttons = new Composite(control, SWT.FILL | SWT.TOP);
		buttons.setLayoutData(buttonsData);

		GridLayout buttonsLayout = new GridLayout(1, true);
		buttons.setLayout(buttonsLayout);

		createButton(buttons, "Add...").addListener(SWT.Selection, new AddListener());
		createButton(buttons, "Edit...").addListener(SWT.Selection, new EditListener());
		createButton(buttons, "Remove").addListener(SWT.Selection, new RemoveListener());
		
		control.pack();
		control.layout();
		
		return control;
	}

	@Override
	public void init(IWorkbench workbench) {
		
	}
	
	private Button createButton(Composite parent, String text) {
		Button button = new Button(parent, SWT.NONE);
		button.setText(text);
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return button;
	}
	
	class WizardsPreferenceLabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			WizardsExtensionPreference preference = (WizardsExtensionPreference) element;
			if (columnIndex == 0) {
				return preference.getNamespaceURI();
			}
			else if (columnIndex == 1) {
				return preference.getWizards();
			}
			else {
				return "";
			}
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

	@Override
	protected void performApply() {
		super.performApply();
		WizardsExtensionPreference.storePreferences(preferences);
	}

	@Override
	public boolean performOk() {
		WizardsExtensionPreference.storePreferences(preferences);
		return super.performOk();
	}
	
	
}
