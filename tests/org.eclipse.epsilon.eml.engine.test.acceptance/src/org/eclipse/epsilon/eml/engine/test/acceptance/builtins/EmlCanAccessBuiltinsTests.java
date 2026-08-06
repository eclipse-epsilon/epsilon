/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eml.engine.test.acceptance.builtins;

import java.io.File;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.eml.EmlModule;
import org.eclipse.epsilon.test.builtins.CanAccessBuiltinsTests;

public class EmlCanAccessBuiltinsTests extends CanAccessBuiltinsTests {
	
	@Override
	protected EmlModule createModule() throws Exception {
		return new EmlModule();
	}
	
	@Override
	protected File getProgram() throws Exception {
		return FileUtil.getFileStandalone(("System.eml"), EmlCanAccessBuiltinsTests.class);
	}

	@Override
	protected String getExpectedPrintedValue() {
		return "Running EML";
	}
}
