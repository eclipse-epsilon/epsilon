/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.common.dt;

import static org.eclipse.epsilon.emc.simulink.model.AbstractSimulinkModel.PROPERTY_ENGINE_JAR_PATH;
import static org.eclipse.epsilon.emc.simulink.model.AbstractSimulinkModel.PROPERTY_LIBRARY_PATH;
import static org.eclipse.epsilon.emc.simulink.model.AbstractSimulinkModel.PROPERTY_MATLAB_PATH;

import java.util.ArrayList;

import org.eclipse.epsilon.common.dt.EpsilonCommonsPlugin;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.emc.simulink.engine.MatlabEnginePool;
import org.eclipse.epsilon.emc.simulink.util.MatlabEngineUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class SimulinkEnginePreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	
	protected final ArrayList<FieldEditor> fieldEditors = new ArrayList<>();
	protected IPreferenceStore preferences = EpsilonCommonsPlugin.getDefault().getPreferenceStore();
	
	@Override
	protected Control createContents(Composite parent) {
		
		final Composite composite = new Composite(parent, SWT.FILL);
		
		final DirectoryFieldEditor matlabPathEditor = new DirectoryFieldEditor(PROPERTY_MATLAB_PATH, "MATLAB installation directory", composite);
		final FileFieldEditor engineJarPathEditor = new FileFieldEditor(PROPERTY_ENGINE_JAR_PATH, "Engine JAR file", true, composite);
		
		fieldEditors.add(matlabPathEditor);
		fieldEditors.add(engineJarPathEditor);
		
		for (FieldEditor fieldEditor : fieldEditors) {
			fieldEditor.setPreferenceStore(preferences);
			fieldEditor.load();
		}
		
		resolvePreferences();
		
		Button testConnectionButton = new Button(composite, SWT.NONE);
		testConnectionButton.setText("Test connection");
		testConnectionButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					MatlabEnginePool.getInstance(engineJarPathEditor.getStringValue()).getMatlabEngine().eval("42");
					MessageDialog.openInformation(composite.getShell(), "Success", "Engine well configured!");
				}
				catch (Exception ex) {
					LogUtil.log(ex);
					MessageDialog.openError(composite.getShell(), "Error", "Engine location misconfigured");
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
		Button resetConnectionButton = new Button(composite, SWT.NONE);
		resetConnectionButton.setText("Reset connection");
		resetConnectionButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				MatlabEnginePool.reset();
				MessageDialog.openInformation(composite.getShell(), "Success", "Engine pool cleared");
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		return composite;
	}
	
	protected void resolvePreferences() {
		String[] currentPaths = {
			preferences.getString(PROPERTY_MATLAB_PATH),
			preferences.getString(PROPERTY_ENGINE_JAR_PATH)
		};
		
		try {
			MatlabEngineUtil.resolvePaths(currentPaths);
			
			preferences.setValue(PROPERTY_MATLAB_PATH, currentPaths[0]);
			preferences.setValue(PROPERTY_ENGINE_JAR_PATH, currentPaths[1]);
		}
		catch (IllegalStateException | IllegalArgumentException iax) {
			// Couldn't resolve, so leave blank
			//iax.printStackTrace();
		}
		
		for (FieldEditor fieldEditor : fieldEditors) {
			fieldEditor.load();
		}
	}
	
	@Override
	public void init(IWorkbench workbench) {
		
	}
	
	@Override
	protected void performApply() {
		super.performApply();
		resolvePreferences();
	}
	
	@Override
	public boolean performOk() {
		super.performOk();
		for (FieldEditor fieldEditor : fieldEditors) {
			fieldEditor.store();
		}
		MatlabEnginePool.reset();
		return true;
	}

}
