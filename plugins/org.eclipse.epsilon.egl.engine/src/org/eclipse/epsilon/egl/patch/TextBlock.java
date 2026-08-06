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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @since 1.6
 */
public class TextBlock {
	
	protected List<Line> lines = new ArrayList<>();
	
	public TextBlock(String... lines) {
		for (int i=0; i<lines.length; i++) {
			this.lines.add(new Line(LineType.REGULAR, lines[i], i+1));
		}
	}
	
	protected Line getFirstLine() {
		if (lines.isEmpty()) return null;
		else return lines.get(0);
	}
	
	protected Line getLastLine() {
		if (lines.isEmpty()) return null;
		else return lines.get(lines.size()-1);
	}
	
	public List<Line> getLines() {
		return lines;
	}
	
	protected boolean isFirstLine(Line line) {
		if (line == null) return false;
		return lines.indexOf(line) == 0;
	}
	
	protected boolean isLastLine(Line line) {
		if (line == null) return false;
		return lines.indexOf(line) == lines.size() - 1;
	}
	
	protected Line getPreviousLine(Line line) {
		if (isFirstLine(line)) return null;
		else return lines.get(lines.indexOf(line) - 1);
	}
	
	protected Line getNextLine(Line line) {
		if (isLastLine(line)) return null;
		else return lines.get(lines.indexOf(line) + 1);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TextBlock)) return false;
		TextBlock other = (TextBlock) obj;
		if (other.getLines().size() != this.getLines().size()) return false;
		for (int i=0;i<getLines().size();i++) {
			if (!other.getLines().get(i).getTrimmedText().equals(this.getLines().get(i).getTrimmedText())) return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return getLines().toString();
	}
	
}
