/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.test.builtins;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.junit.Test;

public abstract class CanAccessBuiltinsTests {
	
	@Test
	public void builtinVariableShouldBeAvailable() throws Exception {
		final IEolModule module = createModule();
		module.parse(getProgram());
		
		final ByteArrayOutputStream printed = new ByteArrayOutputStream();
		module.getContext().setOutputStream(new PrintStream(printed));
		
		execute(module);
		
		assertEquals(getExpectedPrintedValue(), printed.toString());
	}
	
	protected abstract IEolModule createModule() throws Exception;
	protected abstract File getProgram() throws Exception;
	protected abstract String getExpectedPrintedValue() throws Exception;
	
	protected void execute(IEolModule module) throws EolRuntimeException {
		if (module!= null) {
			module.execute();
		}
		else {
			throw new IllegalArgumentException("CanAccessBuiltins does not know how to execute the following module. (Please override this method). " + module);
		}
	}
}
