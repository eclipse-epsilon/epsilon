/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ewl.engine.test.acceptance.builtins;

import java.io.File;
import java.util.List;

import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.ewl.EwlModule;
import org.eclipse.epsilon.ewl.execute.WizardInstance;
import org.eclipse.epsilon.test.builtins.CanAccessBuiltinsTests;

public class EwlCanAccessBuiltinsTests extends CanAccessBuiltinsTests {

	@Override
	protected IEolModule createModule() {
		return new EwlModule();
	}

	@Override
	protected File getProgram() throws Exception {
		return FileUtil.getFileStandalone(("System.ewl"), EwlCanAccessBuiltinsTests.class);
	}

	@Override
	protected String getExpectedPrintedValue() {
		return "Running EWL";
	}
	
	@Override
	protected void execute(IEolModule module) throws EolRuntimeException {
		final List<WizardInstance> wizards = ((EwlModule)module).getWizardsFor("Running EWL");
		
		for (WizardInstance wizard : wizards) {
			wizard.process();
		}
	}
}
