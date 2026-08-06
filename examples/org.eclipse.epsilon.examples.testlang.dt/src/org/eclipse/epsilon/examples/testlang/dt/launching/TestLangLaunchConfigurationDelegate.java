/*******************************************************************************
 * Copyright (c) 2016 Aston University.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.examples.testlang.dt.launching;

import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dt.launching.EolLaunchConfigurationDelegate;
import org.eclipse.epsilon.examples.testlang.engine.TestLangModule;

public class TestLangLaunchConfigurationDelegate extends EolLaunchConfigurationDelegate {

	@Override
	public IEolModule createModule() {
		return new TestLangModule();
	}

	
}
