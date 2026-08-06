/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.parse.problem;

import org.eclipse.epsilon.common.module.ModuleElement;

public class ParseProblem {
	
	public static final int ERROR = 0;
	public static final int WARNING = 1;
	
	private int line;
	private int column;
	private String reason;
	private int severity;
	
	public ParseProblem() {
	}
	
	/**
	 * @since 2.1
	 */
	public ParseProblem(String reason, ModuleElement element) {
		this(element.getRegion().getStart().getLine(), element.getRegion().getStart().getColumn(), reason);
	}
	
	public ParseProblem(int line, int column, String reason) {
		this(line, column, reason, ERROR);
	}
	
	public ParseProblem(String reason, int severity) {
		this(-1, -1, reason, severity);
	}
	
	public ParseProblem(int line, int column, String reason, int severity) {
		this.column   = column;
		this.line     = line;
		this.reason   = reason;
		this.severity = severity;
	}
	
	/*
	public ParseProblem(RecognitionException ex, int severity) {
		this.line = ex.line;
		this.column = ex.column;
		this.severity = severity;
		this.reason = ex.getLocalizedMessage();
	}
	*/
	public int getSeverity() {
		return severity;
	}

	public void setSeverity(int severity) {
		this.severity = severity;
	}
	
	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	@Override
	public String toString() {
		String str = "";
		str += "Line: " + getLine() + ",";
		if (getColumn() > 0) {
			str += " Column: " + getColumn() + ",";
		}
		str += " Reason: " + getReason();
		return str;
	}
}
