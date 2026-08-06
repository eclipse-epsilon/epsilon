/*********************************************************************
* Copyright (c) 2008-2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.execute;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.execute.control.IExecutionListener;

/**
 * 
 * @since 1.6 Doesn't implement IExecutionListener to prevent misuse.
 * Not extensible - add an {@link IExecutionListener} to {@link ExecutorFactory}
 * if you want to add functionality.
 */
public final class StackTraceManager {
	
	/** Use Deque instead of Stack to avoid bottlenecks due to synchronisation overhead!
	 * Concurrency can be handled by having a different StackTraceManager for each thread,
	 * or by using a {@linkplain ConcurrentLinkedDeque}
	 * @since 1.6
	 */
	final Deque<ModuleElement> stackTrace = new ArrayDeque<>();
	
	/**
	 * @since 1.6
	 */
	protected void dispose() {
		stackTrace.clear();
	}
	
	/**
	 * 
	 * @return An immutable view of the stack trace
	 * in proper order.
	 */
	public List<ModuleElement> getStackTrace() {
		return new ArrayList<>(this.stackTrace);
	}
	
	public void printStackTrace(PrintWriter writer) {
		writer.print(getStackTraceAsString());
	}
	
	public void printStackTrace(PrintStream stream) {
		stream.print(getStackTraceAsString());
	}
	
	public String getStackTraceAsString() {
		StringBuilder sb = new StringBuilder();
		for (ModuleElement ast : getStackTrace()) {
			sb.append(toString(ast) + System.lineSeparator());
		}
		return sb.toString();
	}
	
	protected String toString(ModuleElement ast) {
		String location = "unknown";
		if (ast == null) return location;
		
		if (ast.getFile() != null) {
			location = ast.getFile().getAbsolutePath();
		}
		else if (ast.getUri() != null) {
			location = ast.getUri().toString();
		}
		
		return "\tat (" + location 
				+ "@" + ast.getRegion().getStart().getLine() + ":" 
				+ ast.getRegion().getStart().getColumn() + "-" + 
				ast.getRegion().getEnd().getLine() + ":" +
				ast.getRegion().getEnd().getColumn() + ")";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(StackTraceManager.class, stackTrace);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof StackTraceManager)) return false;
		StackTraceManager other = (StackTraceManager) obj;
		return Objects.equals(this.stackTrace, other.stackTrace);
	}
}
