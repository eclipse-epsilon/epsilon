/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.tools;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Queue;
import org.eclipse.epsilon.common.util.OperatingSystem;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.AsyncStatementInstance;
import org.eclipse.epsilon.eol.userinput.IUserInput;

public class EolSystem extends AbstractTool {
	
	public IUserInput getUser() {
		return context.getUserInput();
	}
	
	public PrintStream getOut() {
		return context.getOutputStream();
	}
	
	public PrintStream getErr() {
		return context.getErrorStream();
	}
	
	public String getPlatformProperty(String property) {
		return System.getProperty(property);
	}
	
	public void execAsync() throws EolRuntimeException {
		for (
			Queue<AsyncStatementInstance> queue = context.getAsyncStatementsQueue();
			!queue.isEmpty();
			queue.poll().execute(context)
		);
	}
	

	/**
	 * 
	 * @param command
	 * @return
	 * @throws IOException 
	 * @since 2.2
	 */
	public static final String executeNative(String command) throws IOException {
		return executeNative(command.split(" "));
	}
	
	/**
	 * 
	 * @param commands
	 * @return
	 * @throws IOException 
	 * @since 2.2
	 */
	public static final String executeNative(String... commands) throws IOException {
		return OperatingSystem.executeCommand(commands);
	}
	
	/**
	 * 
	 * @param command
	 * @return
	 * @throws IOException 
	 * @since 2.2
	 */
	public static final Process executeNativeAsync(String command) throws IOException {
		return executeNativeAsync(command.split(" "));
	}
	
	/**
	 * 
	 * @param commands
	 * @return
	 * @throws IOException 
	 * @since 2.2
	 */
	public static final Process executeNativeAsync(String... commands) throws IOException {
		return new ProcessBuilder(commands).start();
	}
}
