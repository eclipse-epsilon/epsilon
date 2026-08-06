/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.formatter.linebyline;

public class Line {
	
	protected String rawLine;
	
	public Line(String rawLine) {
		this.rawLine = rawLine;
	}

	public Line addPrefix(String prefix) {
		this.rawLine = prefix + rawLine;
		return this;
	}
	
	public Line addSuffix(String suffix) {
		this.rawLine = rawLine + suffix;
		return this;
	}
	
	public String getText() {
		return rawLine;
	}
	
	void join(Line joinee) {
		this.rawLine += joinee.rawLine.trim();
	}
	
	@Override
	public String toString() {
		return "Line: " + rawLine;
	}
}
