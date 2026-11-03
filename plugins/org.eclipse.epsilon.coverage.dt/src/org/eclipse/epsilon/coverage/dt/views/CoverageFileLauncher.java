/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage.dt.views;

import org.eclipse.core.runtime.IPath;
import org.eclipse.epsilon.coverage.dt.CoverageDtPlugin;
import org.eclipse.epsilon.coverage.dt.CoverageSessionManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorLauncher;
import org.eclipse.ui.PlatformUI;

/**
 * Opens .ecv files (e.g. when double-clicked in the workspace) in the Coverage view.
 */
public class CoverageFileLauncher implements IEditorLauncher {
	
	@Override
	public void open(IPath file) {
		try {
			CoverageSessionManager.getInstance().load(file.toFile());
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(CoverageDtPlugin.VIEW_ID);
		}
		catch (Exception ex) {
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
				"Coverage", "Could not open " + file + ": " + ex.getMessage());
		}
	}
}
