/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.dt.emf.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.epsilon.common.dt.actions.AbstractObjectActionDelegate;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.jface.action.IAction;

public class RestoreBackup extends AbstractObjectActionDelegate {

	private IFile backedUpFile;

	@Override
	public void run(IAction action) {
		try {
			if (getFirstElementInSelection() instanceof IFile) {
				backedUpFile = (IFile)getFirstElementInSelection();
				restoreBackup();
			}
		
		} catch (CoreException ex) {
			LogUtil.log("Error encountered while restoring backup: " + backedUpFile.getLocation(), ex);
		}
	}
	
	private void restoreBackup() throws CoreException {		
		final IPath originalPath = getOriginalPath();
		
		final IFile originalFile = backedUpFile.getProject().getFile(originalPath.removeFirstSegments(1));
		if (originalFile.exists())
			originalFile.delete(false, new NullProgressMonitor());
		
		backedUpFile.move(originalPath, false, new NullProgressMonitor());
	}

	private IPath getOriginalPath() {
		final IPath backedUpPath = backedUpFile.getFullPath();
		
		return backedUpPath
		       	.removeLastSegments(1)
		       	.append(backedUpPath.lastSegment().replace("_backup.", "."));
	}
}
