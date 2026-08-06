/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.console;

import java.io.InputStream;
import java.io.PrintStream;

import org.eclipse.epsilon.eol.userinput.ConsoleUserInput;

public class EpsilonConsoleUserInput extends ConsoleUserInput {

	public EpsilonConsoleUserInput() {
		super();
	}

	@Override
	public InputStream getInputStream() {
		return EpsilonConsole.getInstance().getInputStream();
	}

	@Override
	public PrintStream getOutputStream() {
		return EpsilonConsole.getInstance().getDebugStream();
	}

	@Override
	public PrintStream getErrorStream() {
		return EpsilonConsole.getInstance().getErrorStream();
	}
	
}
