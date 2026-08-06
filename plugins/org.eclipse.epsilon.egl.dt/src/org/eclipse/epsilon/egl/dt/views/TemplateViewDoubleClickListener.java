/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.views;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.epsilon.common.dt.console.EpsilonConsole;
import org.eclipse.epsilon.egl.traceability.Container;
import org.eclipse.epsilon.egl.traceability.ProtectedRegion;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

public class TemplateViewDoubleClickListener implements IDoubleClickListener {

	private String relativePathTo(File file) {
		final File workspace = new File(ResourcesPlugin.getWorkspace().getRoot().getRawLocation().toOSString());
		
		File parent = file;
		boolean workspaceContainsFile = false;
		String relativePath = "";
		
		while (!workspaceContainsFile && parent != null) {
			workspaceContainsFile = parent.equals(workspace);
			if (!workspaceContainsFile) {
				relativePath = parent.getName() + (relativePath.length() == 0 ? "" : File.separatorChar + relativePath);
				parent = parent.getParentFile();
			}
		}
		
		return workspaceContainsFile ? relativePath : null;
	}
	

	private void openEditor(File file) {
		openEditor(file, -1);
	}
		
	
	private void openEditor(File file, int offset) {
		try {
			final String relativePath = relativePathTo(file);
			
			final IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			final IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			final IEditorDescriptor descriptor = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
			
			if (window != null) {
				final IWorkbenchPage page = window.getActivePage();
				
				if (relativePath == null) {
					final IProject externalFilesProject = root.getProject("External Files");
					
					if (!externalFilesProject.exists())
						externalFilesProject.create(null);
					if (!externalFilesProject.isOpen())
						externalFilesProject.open(null);
					
					final IPath location = new Path(file.getAbsolutePath());
					final IFile externalFile = externalFilesProject.getFile(location.lastSegment());
					
					if (!externalFile.exists())
						externalFile.createLink(location, IResource.NONE, null);

					if (page != null)
						page.openEditor(new FileEditorInput(externalFile), descriptor.getId());
				
				} else {
					final IResource resource = root.findMember(relativePath);
					
					if (resource instanceof IFile) {
						if (page != null) {
							final IEditorPart editor = page.openEditor(new FileEditorInput((IFile)resource), descriptor.getId());
							
							if (offset > -1 && editor instanceof ITextEditor) {
								((ITextEditor)editor).selectAndReveal(offset, 0);
							}
						}
						
					}
				}
			}
		} catch (CoreException e) {
			EpsilonConsole.getInstance().getErrorStream().append(e.toString());
		}
	}
	
	public void doubleClick(DoubleClickEvent event) {
		
		if (event.getSelection() instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection)event.getSelection();
			
			for (Object selectedItem : selection.toList()) {
				if (selectedItem instanceof Container) {
					if (((Container)selectedItem).getURI().getScheme().equals("file")) {
						openEditor(new File(((Container)selectedItem).getURI()));
					}
				}
				else if (selectedItem instanceof ProtectedRegion) {
					final ProtectedRegion pr = (ProtectedRegion)selectedItem;
					
					if (pr.getParent().getURI().getScheme().equals("file")) {
						openEditor(new File(pr.getParent().getURI()), pr.getOffset());
					}
				}
			}
		}
	}

}
