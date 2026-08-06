/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.launching.tabs;

import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.epsilon.common.dt.test.util.LaunchConfigurationTabTestBed;

public class EglGeneratedTextConfigurationTabTestbed extends LaunchConfigurationTabTestBed {

	@Override
	protected ILaunchConfigurationTab getLaunchConfigurationTab() {
		return new EglGeneratedTextConfigurationTab();
	}
	
	public static void main(String[] args) {
		new EglGeneratedTextConfigurationTabTestbed().run();
	}
}
