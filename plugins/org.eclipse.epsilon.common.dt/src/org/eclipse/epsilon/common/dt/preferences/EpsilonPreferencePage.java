/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.preferences;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.dt.EpsilonCommonsPlugin;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class EpsilonPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {


	public static final String ENABLE_STATIC_ANALYSIS = "enableStaticAnalysis";
	
	protected List<FieldEditor> fieldEditors = new ArrayList<>();
	
	@Override
	protected Control createContents(Composite parent) {
		
		Composite composite = new Composite(parent, SWT.FILL);

		fieldEditors.add(new BooleanFieldEditor(ENABLE_STATIC_ANALYSIS, "Enable static analysis (experimental)", composite));

		for (FieldEditor fieldEditor : fieldEditors) {
			fieldEditor.setPreferenceStore(EpsilonCommonsPlugin.getDefault().getPreferenceStore());
			fieldEditor.load();
		}
		
		return composite;
	}
	
	@Override
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
