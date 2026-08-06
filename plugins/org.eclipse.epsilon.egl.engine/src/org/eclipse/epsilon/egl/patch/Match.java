/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.patch;

/**
 * 
 * @since 1.6
 */
public class Match {
	
	protected TextBlock block;
	protected Line startLine;
	protected Line endLine;
	protected Patch patch;
	protected LineMap lineMap;
	
	public Match(TextBlock block, Line startLine, Line endLine, Patch patch, LineMap lineMap) {
		super();
		this.block = block;
		this.startLine = startLine;
		this.endLine = endLine;
		this.patch = patch;
		this.lineMap = lineMap;
	}

	public TextBlock getBlock() {
		return block;
	}

	public void setBlock(TextBlock block) {
		this.block = block;
	}

	public Line getStartLine() {
		return startLine;
	}

	public void setStartLine(Line startLine) {
		this.startLine = startLine;
	}

	public Line getEndLine() {
		return endLine;
	}

	public void setEndLine(Line endLine) {
		this.endLine = endLine;
	}

	public Patch getPatch() {
		return patch;
	}

	public void setPatch(Patch patch) {
		this.patch = patch;
	}
	
	public LineMap getLineMap() {
		return lineMap;
	}
	
	public void setLineMap(LineMap lineMap) {
		this.lineMap = lineMap;
	}
	
}
