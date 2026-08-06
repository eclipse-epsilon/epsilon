/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.formatter.linebyline;

import static org.eclipse.epsilon.egl.util.FileUtil.NEWLINE;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LineByLineNavigatorTests {

	private final String threeLinesOfText = "foo" + NEWLINE + "bar" + NEWLINE + "baz";
	private final LineByLineNavigator<Line> navigator = new LineByLineNavigator<>(threeLinesOfText, Line::new);
	
	@Test
	public void iteratesOverLines() {
		final StringBuilder joined = new StringBuilder();
		
		while (navigator.hasMoreLines()) {
			joined.append(navigator.getCurrentLine().getText());
			navigator.moveToNextLine();
		}
	
		assertEquals("foobarbaz", joined.toString());
	}
	
	@Test
	public void getTextReflectsChangesToCurrentLine() {
		while (navigator.hasMoreLines()) {
			navigator.getCurrentLine().addSuffix("!");
			navigator.moveToNextLine();
		}
	
		assertEquals("foo!" + NEWLINE + "bar!" + NEWLINE + "baz!", navigator.getText());
	}
	
	@Test
	public void getTextReflectsChangesToPreviousLine() {
		while (navigator.hasMoreLines()) {
			navigator.moveToNextLine();
			navigator.getPreviousLine().addSuffix("!");
		}
	
		assertEquals("foo!" + NEWLINE + "bar!" + NEWLINE + "baz!", navigator.getText());
	}
	
	@Test
	public void getTextContainsInsertedLines() {
		while (navigator.hasMoreLines()) {
			navigator.insertBeforeCurrentLine(new Line("---"));
			navigator.moveToNextLine();
		}
	
		assertEquals("---" + NEWLINE +
		             "foo" + NEWLINE +
		             "---" + NEWLINE +
		             "bar" + NEWLINE +
		             "---" + NEWLINE +
		             "baz", navigator.getText());
	}
	
	@Test
	public void getTextContainsJoinedLines() {
		joinFirstTwoLines();
		navigator.joinCurrentToPrevious("|");
	
		assertEquals("foo|bar|baz", navigator.getText());
	}
	
	@Test
	public void joiningLinesMovesCurrent() {
		final String textOfThirdLine = "baz";
		
		joinFirstTwoLines();
		
		assertEquals(textOfThirdLine, navigator.getCurrentLine().getText());
	}

	private void joinFirstTwoLines() {
		navigator.moveToNextLine();
		navigator.joinCurrentToPrevious("|");
	}
}
