/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.evl.engine.test.acceptance.builtins;

import java.io.File;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.test.builtins.CanAccessBuiltinsTests;

public class EvlCanAccessBuiltinsTests extends CanAccessBuiltinsTests {

	@Override
	protected IEolModule createModule() {
		return new EvlModule();
	}

	@Override
	protected File getProgram() throws Exception {
		return FileUtil.getFileStandalone(("System.evl"), EvlCanAccessBuiltinsTests.class);
	}

	@Override
	protected String getExpectedPrintedValue() {
		return "Running EVL";
	}
}
