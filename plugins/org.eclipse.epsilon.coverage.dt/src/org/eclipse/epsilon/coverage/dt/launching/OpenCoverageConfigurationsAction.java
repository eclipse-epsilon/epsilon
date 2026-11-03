/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage.dt.launching;

import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.epsilon.coverage.dt.CoverageDtPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * The "Coverage Configurations..." item of the Run menu.
 */
public class OpenCoverageConfigurationsAction implements IWorkbenchWindowActionDelegate {
	
	protected IWorkbenchWindow window;
	protected IStructuredSelection selection = StructuredSelection.EMPTY;
	
	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
	
	@Override
	public void run(IAction action) {
		DebugUITools.openLaunchConfigurationDialogOnGroup(window.getShell(), selection, CoverageDtPlugin.LAUNCH_GROUP_ID);
	}
	
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection instanceof IStructuredSelection ? (IStructuredSelection) selection : StructuredSelection.EMPTY;
	}
	
	@Override
	public void dispose() {}
}
