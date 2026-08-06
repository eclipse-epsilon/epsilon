/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.etl.engine.test.acceptance.builtins;

import java.io.File;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.etl.IEtlModule;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.test.builtins.CanAccessBuiltinsTests;

public class EtlCanAccessBuiltinsTests extends CanAccessBuiltinsTests {

	@Override
	protected IEtlModule createModule() {
		return new EtlModule();
	}

	@Override
	protected File getProgram() throws Exception {
		return FileUtil.getFileStandalone(("System.etl"), EtlCanAccessBuiltinsTests.class);
	}

	@Override
	protected String getExpectedPrintedValue() {
		return "Running ETL";
	}		
}
