/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

//TODO : Read the book to find out more about preference pages!
public class ExeedPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	
	public static final String KEEP_PROPERTY_DECLARATION_ORDER = "keepPropertyDeclarationOrder";
	public static final String HIDE_REFERENCE_NAMES = "hideReferenceNames";
	public static final String SHOW_STRUCTURAL_INFO = "showStructuralInfo";
	public static final String SHOW_ALL_RESOURCES= "showAllResources";
	
	protected List<FieldEditor> fieldEditors = new ArrayList<>();
	
	@Override
	protected Control createContents(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.FILL);

		this.setDescription("Some text here...");
		
		fieldEditors.add(new BooleanFieldEditor(HIDE_REFERENCE_NAMES, "Hide reference names", composite));
		fieldEditors.add(new BooleanFieldEditor(SHOW_STRUCTURAL_INFO, "Show structural information", composite));
		fieldEditors.add(new BooleanFieldEditor(SHOW_ALL_RESOURCES, "Show all resources", composite));
		fieldEditors.add(new BooleanFieldEditor(KEEP_PROPERTY_DECLARATION_ORDER, "Maintain property declaration order", composite));

		for (FieldEditor fieldEditor : fieldEditors) {
			fieldEditor.setPreferenceStore(ExeedPlugin.getDefault().getPreferenceStore());
			fieldEditor.load();
		}
		
		return composite;
	}
	
	public void init(IWorkbench workbench) {

	}

	@Override
	public boolean performOk() {
		for (FieldEditor fieldEditor : fieldEditors) {
			fieldEditor.store();
		}
		return true;
	}

}
