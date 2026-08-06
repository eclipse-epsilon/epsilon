/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.translate;

class Detail {

	private final int line;
	private final int column;
	private final String text;

	public Detail(int line, int column, String text) {
		this.line   = line;
		this.column = column;
		this.text   = text;
	}
	
	public int getLine() {
		return line;
	}
	
	public int getColumn() {
		return column;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return "[" + line + ", " + column + "] " + text;
	}
}
