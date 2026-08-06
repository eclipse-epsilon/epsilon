/*******************************************************************************
 * Copyright (c) 2012-2016 Antonio Garcia-Dominguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eunit.junit.dt;

import org.eclipse.core.runtime.Platform;
import org.eclipse.epsilon.eol.dt.launching.EclipseContextManager;
import org.eclipse.epsilon.eol.userinput.JavaConsoleUserInput;
import org.eclipse.epsilon.eunit.junit.EUnitTestRunner;
import org.eclipse.epsilon.eunit.junit.IEUnitSuite;
import org.junit.runners.model.InitializationError;

/**
 * Specialization of {@link EUnitTestRunner} for running within Eclipse.
 */
public class EclipseEUnitTestRunner extends EUnitTestRunner {

	public EclipseEUnitTestRunner(Class<? extends IEUnitSuite> testClass) throws InitializationError {
		super(testClass);
		if (Platform.getExtensionRegistry() != null) {
			EclipseContextManager.setup(module.getContext());

			// Disable notification through dialogs: it's bad for automated test cases.
			// Use the console instead.
			module.getContext().setUserInput(new JavaConsoleUserInput());
		}
	}
}
