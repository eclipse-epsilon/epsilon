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

import static org.eclipse.epsilon.egl.util.FileUtil.NEWLINE;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LineByLineNavigator<T extends Line> {
	
	private final List<T> lines = new LinkedList<>();
	
	private int lineIndex = 0;
	
	public LineByLineNavigator(String text, LineFactory<T> lineFactory) {
		for (String unformattedRawLine : Arrays.asList(text.split(NEWLINE))) {				
			this.lines.add(lineFactory.createLine(unformattedRawLine));
		}
	}

	public boolean hasPreviousLine() {
		return lineIndex != 0;
	}
	
	public T getPreviousLine() {
		return hasPreviousLine() ? lines.get(lineIndex-1) : null;
	}
	
	public T getCurrentLine() {
		return lines.get(lineIndex);
	}
	
	public boolean hasMoreLines() {
		return lineIndex < lines.size();
	}
	
	public void insertBeforeCurrentLine(T formattedLine) {
		lines.add(lineIndex, formattedLine);
		lineIndex++;
	}
	
	public void joinCurrentToPrevious() {
		joinCurrentToPrevious("");
	}
	
	public void joinCurrentToPrevious(String glue) {
		getPreviousLine().addSuffix(glue).join(getCurrentLine());
		lines.remove(lineIndex);
	}
	
	public void moveToNextLine() {
		lineIndex++;
	}
	
	public String getText() {
		final StringBuilder formattedText = new StringBuilder();
		
		for (Line line : lines) {
			formattedText.append(line.getText());
			formattedText.append(NEWLINE);
		}
		
		return formattedText.toString().trim();
	}
}
