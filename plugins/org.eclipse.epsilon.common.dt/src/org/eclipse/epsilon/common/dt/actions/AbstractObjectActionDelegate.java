/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public abstract class AbstractObjectActionDelegate implements IObjectActionDelegate {
	
	protected ISelection selection;
	
	
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}
	
	protected Object getFirstElementOf(ISelection selection) {
		if (selection instanceof IStructuredSelection)
			return ((IStructuredSelection)selection).getFirstElement();
		
		else
			return null;	
	}
	
	protected Object getFirstElementInSelection() {
		return getFirstElementOf(selection);	
	}
	
	@Override
	public abstract void run(IAction action);	
	
	protected static void refreshProjectContaining(IFile file) throws CoreException {
		file.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
	}
}
