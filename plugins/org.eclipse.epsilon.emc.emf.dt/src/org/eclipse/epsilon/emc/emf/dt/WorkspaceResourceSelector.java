/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.emf.dt;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ResourceListSelectionDialog;

public class WorkspaceResourceSelector {

	protected Button browseButton = null;
	protected Button newButton = null;
	protected Text pathText = null;
	protected Label label = null;
	protected ArrayList<WorkspaceResourceSelectorListener> listeners = new ArrayList<>();
	protected String browseTitle = "Select a file";
	protected String browseMessage = "Files in the workspace";
	protected boolean newEnabled = false;
	protected Composite parent = null;
	
	public WorkspaceResourceSelector(Composite parent, boolean newEnabled) {
		this.parent = parent;
		this.newEnabled = newEnabled;
	}
	
	public void inject() {
		
		label = new Label(parent, SWT.NONE);
		label.setLayoutData(new GridData());
		
		pathText = new Text(parent, SWT.BORDER);
		GridData pathTextData = new GridData(GridData.FILL_HORIZONTAL);
		pathTextData.grabExcessHorizontalSpace = true;
		pathText.setLayoutData(pathTextData);
		
		browseButton = new Button(parent, SWT.PUSH);
		browseButton.setText("Browse...");
		browseButton.setLayoutData(new GridData());
		
		browseButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				ResourceListSelectionDialog elementSelector = new ResourceListSelectionDialog(parent.getShell(), ResourcesPlugin.getWorkspace().getRoot(), IResource.DEPTH_INFINITE | IResource.FILE );
				elementSelector.setTitle(browseTitle);
				elementSelector.setMessage(browseMessage);
				elementSelector.open();
				IFile file = null;
				if (elementSelector.getReturnCode() == Window.OK){
					file = (IFile) elementSelector.getResult()[0];
				}
				for (WorkspaceResourceSelectorListener listener : listeners) {
					listener.fileSelected(file);
				}
			}
		});
		
		if (newEnabled) {
			newButton = new Button(parent, SWT.NONE);
			newButton.setText("New...");
			newButton.setLayoutData(new GridData());
		}
		else {
			((GridData)browseButton.getLayoutData()).horizontalSpan = 2;
		}
		
		addListener(new WorkspaceResourceSelectorListener() {
			
			@Override
			public void fileSelected(IFile file) {
				if (file != null) {
					pathText.setText(file.getFullPath().toPortableString());
				}
			}
		});
		
	}

	public void addListener(WorkspaceResourceSelectorListener listener) {
		this.listeners.add(listener);
	}
	
	public String getLabelText() {
		return label.getText();
	}
	
	public void setLabelText(String text) {
		label.setText(text);
	}
}
