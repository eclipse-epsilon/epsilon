/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test.acceptance.eol;

import java.io.File;
import java.io.IOException;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.EglModule;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.test.builtins.CanAccessBuiltinsTests;

public class EglCanAccessBuiltinsFromOperationsTests extends CanAccessBuiltinsTests {

	@Override
	protected IEolModule createModule() {
		return new EglModule(new EglTemplateFactory());
	}

	@Override
	protected File getProgram() throws IOException {
		return FileUtil.getFileStandalone(("SystemInOperation.egl"), EglCanAccessBuiltinsFromOperationsTests.class);
	}

	@Override
	protected String getExpectedPrintedValue() {
		return "Running EGL";
	}
}
