/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.output;

import static org.junit.Assert.assertEquals;
import java.util.regex.Pattern;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({LineCounterTests.WhenNewlineIsSingleChar.class, LineCounterTests.WhenNewlineIsTwoChars.class})
public class LineCounterTests {

	public static class WhenNewlineIsSingleChar extends CurrentLineNumberTests {
		@Override protected String getNewLine() {
			return "\n";
		}
	}
	
	public static class WhenNewlineIsTwoChars extends CurrentLineNumberTests {
		@Override protected String getNewLine() {
			return "\r\n";
		}
		
		@Test
		public void partialNewLineDoesNotContributeToCurrentLineNumber() {
			assertEquals(2, getCurrentLineNumberFor("abc" + getNewLine() + "def" + "\n" + "ghi" + "\r" + "jkl"));
		}
	}
	
	
	public static abstract class CurrentLineNumberTests {
				
		protected abstract String getNewLine();

		@Test
		public void shouldBeDeterminedFromText() {
			assertEquals(2, getCurrentLineNumberFor("abc" + getNewLine() + "def"));
		}
		
		@Test
		public void shouldBeOneForBlankText() {
			assertEquals(1, getCurrentLineNumberFor(""));
		}
		
		@Test
		public void shouldCountANewLineAtTheEndOfTheText() {
			assertEquals(2, getCurrentLineNumberFor("abc" + getNewLine()));
		}
		
		@Test
		public void shouldCountBlankLinesAtTheEndOfTheText() {
			assertEquals(4, getCurrentLineNumberFor("abc" + getNewLine() + getNewLine() + getNewLine()));
		}
		
		@Test
		public void shouldCountBlankLinesInTheMiddleOfText() {
			assertEquals(4, getCurrentLineNumberFor("abc" + getNewLine() + getNewLine() + getNewLine() + "def"));
		}
	
		protected int getCurrentLineNumberFor(String text) {
			return new OutputBuffer(){{
				newLinePattern = Pattern.compile(CurrentLineNumberTests.this.getNewLine());
				buffer = new StringBuffer(text);
			}}
			.getCurrentLineNumber();
		}
	}
}
