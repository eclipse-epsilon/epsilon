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

import org.eclipse.epsilon.common.dt.util.ResourceFieldEditor;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.emf.dt.BrowseEPackagesListener;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.preference.ListEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

public class WizardsExtensionPreferenceDialog extends TitleAreaDialog {
	
	private final class SettableListEditor extends ListEditor {
		public SettableListEditor(String name, String label, Composite composite) {
			super(name, label, composite);
		}

		@Override
		public String createList(String[] items) {
			return StringUtil.concat(WizardsExtensionPreference.EXTRA_PKG_SEPARATOR, items);
		}

		@Override
		protected String getNewInputObject() {
			SyncBrowseEPackagesListener lst = new SyncBrowseEPackagesListener();
			lst.handleEvent(new Event());
			return lst.result;
		}

		@Override
		protected String[] parseString(String stringList) {
			return StringUtil
				.split(stringList, WizardsExtensionPreference.EXTRA_PKG_SEPARATOR)
				.toArray(new String[0]);
		}

		public void setStringValues(String[] values) {
			for (String value : values) {
				getList().add(value);
			}
		}

		public String getStringValue() {
			return createList(getList().getItems());
		}
	}

	private final class SyncBrowseEPackagesListener extends BrowseEPackagesListener {
		private String result;

		@Override
		public void selectionChanged(String ePackageUri) {
			this.result = ePackageUri;
		}
	}

	protected StringFieldEditor extensionEditor;
	protected ResourceFieldEditor wizardsEditor;
	protected SettableListEditor extraPackagesEditor;
	protected WizardsExtensionPreference preference;
	
	public WizardsExtensionPreferenceDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite zuper = (Composite) super.createDialogArea(parent);
		
		setTitle("Configure wizards");
		getShell().setText("EMF Wizards");
		setMessage("Configure wizards for an EMF namespace URI");
		
		GridLayout layout = new GridLayout(2, false);
		GridData fillData = new GridData(GridData.FILL_BOTH);
		fillData.horizontalIndent = 8;

		final Composite composite = new Composite(zuper, SWT.FILL);
		composite.setLayout(layout);
		composite.setLayoutData(fillData);

		// Main namespace URI (for activating the wizard)
		final Composite cNamespaceURIRow = new Composite(composite, SWT.FILL);
		cNamespaceURIRow.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		extensionEditor = new StringFieldEditor("", "Namespace URI", cNamespaceURIRow);
		extensionEditor.getTextControl(cNamespaceURIRow).setLayoutData(fillData);
		Button browseMetamodelUri = new Button(composite, SWT.NONE);
		browseMetamodelUri.setText("Browse EPackages...");
		browseMetamodelUri.addListener(SWT.Selection, new BrowseEPackagesListener() {
			@Override
			public void selectionChanged(String ePackageUri) {
				extensionEditor.getTextControl(cNamespaceURIRow).setText(ePackageUri);
			}
		});

		// EWL file picker
		Composite cEWLFileRow = new Composite(composite, SWT.FILL);
		wizardsEditor = new ResourceFieldEditor("", "Wizards", "EWL Files", "EWL files in the workspace", "ewl", cEWLFileRow);
		fillData = new GridData(GridData.FILL_BOTH);
		fillData.horizontalSpan = 2;
		cEWLFileRow.setLayoutData(fillData);

		// Extra packages picker
		extraPackagesEditor = new SettableListEditor("", "Extra packages:", composite);
		extraPackagesEditor.fillIntoGrid(composite, 2);

		// Read values back into the dialog
		extensionEditor.setStringValue(preference.getNamespaceURI());
		wizardsEditor.setStringValue(preference.getWizards());
		extraPackagesEditor.setStringValues(preference.getExtraPackages());
		
		return composite;
	}

	public WizardsExtensionPreference getPreference() {
		return preference;
	}

	public void setPreference(WizardsExtensionPreference preference) {
		this.preference = preference;
	}

	@Override
	protected void okPressed() {
		preference.setNamespaceURI(extensionEditor.getStringValue());
		preference.setWizards(wizardsEditor.getStringValue());
		preference.setExtraPackages(extraPackagesEditor.getStringValue());
		super.okPressed();
	}
	
}
