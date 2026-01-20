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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.epsilon.common.dt.EpsilonPlugin;
import org.eclipse.epsilon.common.dt.launching.dialogs.BrowseWorkspaceUtil;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

public abstract class AbstractSourceConfigurationTab extends AbstractLaunchConfigurationTab implements ModifyListener {
		
	protected Label fileLabel;
	protected Text filePath;
	protected Button browse;
	protected Composite extras;
	
	@Override
	public void createControl(Composite parent) {
		
		GridLayout parentLayout = new GridLayout();
		parent.setLayout(parentLayout);

		Composite control = new Composite(parent, SWT.NONE);
		setControl(control);
		
		GridLayout controlLayout = new GridLayout(3, false);
		control.setLayout(controlLayout);
		
		final Group sourceGroup = createGroup(control, "Source:", 2);
		
		GridData filePathData = new GridData(GridData.FILL_HORIZONTAL);
		filePath = new Text(sourceGroup, SWT.BORDER);
		filePath.setLayoutData(filePathData);
		filePath.addModifyListener(this);
		createBrowseWorkspaceForFileButton(sourceGroup, filePath);
		
		extras = new Composite(control, SWT.NONE);
		GridData extrasData = new GridData(GridData.FILL_BOTH);
		extrasData.horizontalSpan = 3;
		extras.setLayoutData(extrasData);
		
		control.setBounds(0, 0, 300, 300);
		control.layout();
		control.pack();
		
		canSave();
		
	}
	
	protected Button createBrowseWorkspaceForFileButton(Composite parent, final Text target) {
		final Button button = new Button(parent, SWT.NONE);
		button.setText("Browse Workspace...");
		button.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				String selected = 
					BrowseWorkspaceUtil.browseFilePath(getShell(), getSelectionTitle()
					,getSelectionSubtitle(), getPlugin().createImage(getImagePath()));
				
				if (selected!=null) target.setText(selected);
			}
		});
		return button;
	}
	
	protected Button createBrowseWorkspaceForContainerButton(Composite parent, final Text target,
			final String title, final String subtitle) {
		final Button button = new Button(parent, SWT.NONE);
		button.setText("Browse Workspace...");
		button.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				String selected = 
					BrowseWorkspaceUtil.browseContainerPath(getShell(), title, subtitle);
				
				if (selected!=null) target.setText(selected);
			}
		});
		return button;
	}
	
	protected Group createGroup(Composite control, String name, int numberOfColumns) {
		final Group group = new Group(control, SWT.SHADOW_ETCHED_IN);
		group.setLayout(new GridLayout(numberOfColumns, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setText(name);
		return group;
	}
	
	protected Composite createTwoColumnComposite(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		composite.setLayout(new GridLayout(2, false));
		return composite;
	}
	
	public Composite getExtras() {
		return extras;
	}
	
	public String getFileLabel() {
		return "Source";
	}
	
	public String getActiveEditorPath(){
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		IWorkbenchPage page = win.getActivePage();
		IEditorPart part = page.getActiveEditor();
		
		if (part.getEditorInput() instanceof FileEditorInput){
			FileEditorInput fileEditorInput = (FileEditorInput) part.getEditorInput();
			String path = fileEditorInput.getFile().getFullPath().toString();
			return path;
		}
		
		return "";
		
	}
	
	public String getActiveEditorName(){
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		IWorkbenchPage page = win.getActivePage();
		IEditorPart part = page.getActiveEditor();
		
		if (part != null && part.getEditorInput() instanceof FileEditorInput){
			FileEditorInput fileEditorInput = (FileEditorInput) part.getEditorInput();
			String fileName = fileEditorInput.getFile().getName();
			fileName = FileUtil.removeExtension(fileName);
			return fileName;
		}
		
		return "";
		
	}
	
	@Override
	public Image getImage() {
		return getPlugin().createImage(getImagePath());
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		String activeEditorName = getActiveEditorName();
		if (activeEditorName.length() > 0){
			configuration.rename(newLaunchConfigurationName(activeEditorName));
			configuration.setAttribute("source", getActiveEditorPath());
		}
		
		PlatformUI.getWorkbench();
	}
	
	// Return a new launch configuration name that does not
	// already exists
	protected String newLaunchConfigurationName(String fileName) {
		
		if (!launchConfigurationExists(fileName)) return fileName;
		
		int i = 1;
		while (true) {
			String configurationName = fileName + " (" + i + ")";
			if (!launchConfigurationExists(configurationName)) return configurationName;
			i++;
		}
	}
	
	private boolean launchConfigurationExists(String name) {
		ILaunchConfiguration[] cs = new ILaunchConfiguration[]{};
		try {
			cs = getLaunchManager().getLaunchConfigurations();
		}
		catch (CoreException ex){
			ex.printStackTrace();
		}
		for (int i = 0; i < cs.length; i++) {
			ILaunchConfiguration c = cs[i];
			if (c.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			filePath.setText(configuration.getAttribute(getSourceAttributeName(), ""));
			canSave();
			updateLaunchConfigurationDialog();
		} catch (CoreException e) {
			LogUtil.log(e);
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute(getSourceAttributeName(), filePath.getText());
	}

	@Override
	public String getName() {
		return getTitle();
	}
	
	@Override
	public boolean canSave() {
		
		IFile file = null;
		
		try {
			file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(filePath.getText()));
		}
		catch (Exception ex) {
			// do nothing
		}
		
		if (file == null || !file.exists()) {
			setErrorMessage("Selected file " + filePath.getText() + " does not exist");
				return false;
			}
			else {
				setErrorMessage(null);
				return true;			
			}
	}
 
	@Override
	public void modifyText(ModifyEvent e) {
		canSave();
		updateLaunchConfigurationDialog();
	}
	
	public abstract EpsilonPlugin getPlugin();
	
	public abstract String getImagePath();
		
		/**
	 * @deprecated We don't care about file extensions any more
	 * @return
	 */
	@Deprecated
	public String getFileExtension() {return "";}
	
	public abstract String getSelectionTitle();
	
	public abstract String getSelectionSubtitle();
	
	public String getTitle() {
		return "Source";
	}
	
	public String getSourceAttributeName() {
		return "source";
	}
}
