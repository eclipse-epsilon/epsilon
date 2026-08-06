/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.dt.launching.contextual;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.debug.ui.ILaunchShortcut2;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.eol.dt.launching.EolLaunchConfigurationAttributes;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PlatformUI;

public class EolLaunchShortcut implements ILaunchShortcut2 {
	
	protected void launch(IFile file, String mode) {
		try {
			ILaunchConfiguration[] launchConfigurations = getLaunchConfigurations(file);
			
			if (launchConfigurations.length == 0) {
				ILaunchConfigurationWorkingCopy configuration = getLaunchConfigurationType().
					newInstance(file.getParent(), DebugPlugin.getDefault().getLaunchManager().generateLaunchConfigurationName(file.getName())).getWorkingCopy();
				
				configuration.setAttribute(EolLaunchConfigurationAttributes.SOURCE, file.getFullPath().toPortableString());
				configuration.doSave();
				
				String groupId;
				if (mode.equals(ILaunchManager.DEBUG_MODE)) {
				    groupId= IDebugUIConstants.ID_DEBUG_LAUNCH_GROUP;
				} else {
				    groupId= IDebugUIConstants.ID_RUN_LAUNCH_GROUP;
				}
				DebugUITools.openLaunchConfigurationDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), configuration, groupId, null);
			}
			else {
				launchConfigurations[0].launch(mode, new NullProgressMonitor());				
			}
		}
		catch (CoreException ex) {ex.printStackTrace();}
	}
	
	protected ILaunchConfigurationType getLaunchConfigurationType() {
		return DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurationType(getLaunchConfigurationTypeId());
	}
	
	protected String getLaunchConfigurationTypeId() {
		return "org.epsilon.eol.eclipse.dt.launching.EolLaunchConfigurationDelegate";
	}
	
	protected ILaunchConfiguration[] getLaunchConfigurations(IFile file) {
		try {
			ILaunchConfigurationType type = getLaunchConfigurationType();
			ArrayList<ILaunchConfiguration> launchConfigurationsList = new ArrayList<>();
			
			for (ILaunchConfiguration config : DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurations(type)) {
				Path launchConfigutationPath = new Path(config.getAttribute(EolLaunchConfigurationAttributes.SOURCE, ""));
				if (file.getFullPath().equals(launchConfigutationPath)) {
					launchConfigurationsList.add(config);
				}
			}
			
			ILaunchConfiguration[] launchConfigurations = new ILaunchConfiguration[launchConfigurationsList.size()];
			int i = 0;
			for (ILaunchConfiguration launchConfiguration : launchConfigurationsList) {
				launchConfigurations[i] = launchConfiguration;
				i++;
			}
			return launchConfigurations;
			
		} catch (CoreException e) {LogUtil.log(e);}
		
		return null;
	}
	
	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Object o = ((IStructuredSelection) selection).getFirstElement();
			if (o instanceof IFile) {
				return getLaunchConfigurations((IFile)o);
			}
		}
		
		return null;
	}
	
	@Override
	public ILaunchConfiguration[] getLaunchConfigurations(IEditorPart editorpart) {
		if (editorpart.getEditorInput() instanceof IFileEditorInput) {
			return getLaunchConfigurations(((IFileEditorInput)editorpart.getEditorInput()).getFile());
		}
		
		return null;
	}
	
	@Override
	public void launch(ISelection selection, String mode) {
		if (selection instanceof IStructuredSelection) {
			Object o = ((IStructuredSelection) selection).getFirstElement();
			if (o instanceof IFile) {
				launch((IFile)o, mode);
			}
		}
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		if (editor.getEditorInput() instanceof IFileEditorInput) {
			launch(((IFileEditorInput)editor.getEditorInput()).getFile(), mode);
		}
	}
	
	@Override
	public IResource getLaunchableResource(ISelection selection) {
		return null;
	}

	@Override
	public IResource getLaunchableResource(IEditorPart editorpart) {
		return null;
	}

}
