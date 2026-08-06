/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.test.util;

import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class LaunchConfigurationTabTestBed {

	public void run() {
		final Display display = Display.getDefault();
		final Shell shell = new Shell(display);
		getLaunchConfigurationTab().createControl(shell);
		
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected abstract ILaunchConfigurationTab getLaunchConfigurationTab();
}
