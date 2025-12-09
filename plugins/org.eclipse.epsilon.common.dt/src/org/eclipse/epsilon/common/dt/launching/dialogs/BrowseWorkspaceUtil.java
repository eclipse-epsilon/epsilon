/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.launching.dialogs;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;
import org.eclipse.ui.dialogs.FilteredResourcesSelectionDialog;

public class BrowseWorkspaceUtil {
	
	public static String browseContainerPath(Shell shell, String title, String message){
		
		IPath container = browseContainer(shell, title, message);
		if (container != null) {
			return container.toString();
		}
		else {
			return null;
		}
	}
	
	public static IPath browseContainer(Shell shell, String title, String message){
		
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(shell, ResourcesPlugin.getWorkspace().getRoot(), true, message);
		
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.open();
		
		if (dialog.getReturnCode() == Window.OK){
			IPath path = (IPath) dialog.getResult()[0];
			return path;
		}
		else {
			return null;
		}
	}
	
	public static String browseFilePath(Shell shell, String title, String message, Image image){
		IFile file = browseFile(shell, title, message, "", image);
		if (file != null) {
			return file.getFullPath().toString();
		}
		else {
			return null;
		}
	}

	public static String browseFilePath(Shell shell, String title, String message, String extension, Image image){
		String pattern = "";
		if (extension != null & extension.length() > 0) {
			pattern = "*." + extension;
		}
		
		IFile file = browseFile(shell, title, message, pattern, image);
		if (file != null) {
			return file.getFullPath().toString();
		}
		else {
			return null;
		}
	}
	
	public static IFile browseFile(Shell shell, String title, String message, String pattern, Image image){
		FilteredResourcesSelectionDialog dialog = new FilteredResourcesSelectionDialog(shell, false, ResourcesPlugin.getWorkspace().getRoot(), IResource.FILE);
		
		dialog.setInitialPattern(pattern, FilteredItemsSelectionDialog.FULL_SELECTION);
		dialog.setTitle(title);
		dialog.setMessage(message);

		dialog.open();
		
		if (dialog.getReturnCode() == Window.OK){
			IFile f = (IFile) dialog.getResult()[0];
			return f;
		}
		else {
			return null;
		}
	}
	
}
