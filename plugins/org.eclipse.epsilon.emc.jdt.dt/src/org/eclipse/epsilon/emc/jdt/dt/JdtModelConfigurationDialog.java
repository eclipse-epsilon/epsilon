/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.jdt.dt;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.epsilon.common.dt.launching.dialogs.AbstractCachedModelConfigurationDialog;
import org.eclipse.epsilon.emc.jdt.JdtModel;
import org.eclipse.epsilon.emc.jdt.JdtUtil;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;

public class JdtModelConfigurationDialog extends AbstractCachedModelConfigurationDialog {

	private List list;
	private Button selectAll;
	private Button deselectAll;
	private Button bindingSwitch;
	private boolean bindingFlag;

	@Override
	protected String getModelName() {
		return "Java (JDT) Model";
	}

	@Override
	protected String getModelType() {
		return "JDT";
	}

	private void createProjectSelectionGroup(Composite parent) {
		final Composite groupContent = createGroupContainer(parent, "Projects",
				1);

		getList(groupContent);
		createRadioButtonGroup(groupContent);
		groupContent.layout();
	}

	private void createRadioButtonGroup(Composite parent) {
		final Composite groupContent = createGroupContainer(parent, "", 2);
		getSelectButton(groupContent);
		getDeSelectButton(groupContent);
		groupContent.layout();
	}

	private void createBidingSwitchGroup(Composite parent) {
		final Composite groupContent = createGroupContainer(parent,
				"Bindings:", 2);
		getBindingSwitch(groupContent);
		groupContent.layout();
	}

	private List getList(Composite groupContent) {
		list = new List(groupContent, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		
		// project list
		final ArrayList<String> projectNameList = new ArrayList<String>();
		java.util.List<IJavaProject> javaProjects = getJavaProjects();
		for (IJavaProject javaProject : javaProjects) {
			projectNameList.add(javaProject.getProject().getName());
		}

		list.setItems(projectNameList.toArray(new String[projectNameList.size()]));
		list.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		return list;
	}

	private Button getSelectButton(Composite groupContent) {
		selectAll = new Button(groupContent, SWT.RADIO);

		// selection radio buttons
		selectAll.setText("Select all");
		selectAll.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (selectAll.getSelection() == true) {
					list.selectAll();
				}
			}
		});
		return selectAll;
	}

	private Button getDeSelectButton(Composite groupContent) {
		deselectAll = new Button(groupContent, SWT.RADIO);

		// selection radio buttons
		deselectAll.setText("Deselect all");
		deselectAll.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (deselectAll.getSelection() == true) {
					list.deselectAll();
				}
			}
		});
		return deselectAll;
	}

	private Button getBindingSwitch(Composite groupContent) {
		bindingSwitch = new Button(groupContent, SWT.CHECK);

		bindingSwitch.setText("Resolve bindings");
		bindingSwitch.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (bindingSwitch.getSelection() == true) {
					bindingFlag = true;
				} else {
					bindingFlag = false;
				}
			}
		});
		return bindingSwitch;
	}

	private java.util.List<IJavaProject> getJavaProjects() {
		java.util.List<IProject> projects = JdtUtil.getIProjects();
		try {
			// get all java projects
			return JdtUtil.getIJavaProjects(projects);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void createGroups(Composite control) {
		createNameAliasGroup(control);
		createPerformanceGroup(control);
		createProjectSelectionGroup(control);
		createBidingSwitchGroup(control);
	}

	protected void loadProperties() {
		super.loadProperties();
		if (properties == null)
			return;
		// get selected projects'names
		String[] selection = properties.getProperty(JdtModel.PROPERTY_PROJECTS).split(",");
		list.setSelection(selection);
		
		// binding switch
		String bindingStr = properties.getProperty(JdtModel.PROPERTY_RESOLVE_BINDINGS);
		if (bindingStr != "") {
			boolean bindingTemp = Boolean.parseBoolean(bindingStr);
			if (bindingTemp == true) {
				bindingFlag = true;
				bindingSwitch.setSelection(true);
			} else {
				bindingFlag = false;
				bindingSwitch.setSelection(false);
			}
		}

	}

	protected void storeProperties() {
		super.storeProperties();
		String projectStr = "";
		// put the names of selected projects in a string, separated by commas
		for (String str : list.getSelection()) {
			if (projectStr.equals(""))
				projectStr = str;
			else
				projectStr = projectStr + "," + str;
		}
		super.properties.put(JdtModel.PROPERTY_PROJECTS, projectStr);
		super.properties.put(JdtModel.PROPERTY_RESOLVE_BINDINGS, bindingFlag);
	}


}
