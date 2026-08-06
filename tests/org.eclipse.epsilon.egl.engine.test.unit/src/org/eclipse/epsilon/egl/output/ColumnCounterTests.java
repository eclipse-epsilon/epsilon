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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ColumnCounterTests.WhenNewlineIsSingleChar.class, ColumnCounterTests.WhenNewlineIsTwoChars.class})
public class ColumnCounterTests {

	public static class WhenNewlineIsSingleChar extends CurrentColumnNumberTests {
		@Override protected String getNewLine() {
			return "\n";
		}
	}
	
	public static class WhenNewlineIsTwoChars extends CurrentColumnNumberTests {
		@Override protected String getNewLine() {
			return "\r\n";
		}
	}
	
	public abstract static class CurrentColumnNumberTests {
	
		protected abstract String getNewLine();

		@Test
		public void shouldBeDeterminedFromText() {
			assertEquals(4, getCurrentColumnNumberFrom("abc"));
		}

		@Test
		public void shouldBeOneWhenThereIsNoText() {
			assertEquals(1, getCurrentColumnNumberFrom(""));
		}
		
		@Test
		public void shouldNotIncludeTextOnLinesOtherThanTheLast() throws Exception {
			assertEquals(3, getCurrentColumnNumberFrom("abc" + getNewLine() + "de"));
		}
		
		@Test
		public void shouldBeOneWhenThereIsNoTextOnTheCurrentLine() {
			assertEquals(1, getCurrentColumnNumberFrom("abc" + getNewLine()));
		}
		
		@Test
		public void shouldBeOneAfterSeveralBlankLines() {
			assertEquals(1, getCurrentColumnNumberFrom("abc" + getNewLine() + getNewLine() + getNewLine()));
		}
		
		@Test
		public void shouldCountNumberOfColumnsInLastLineAfterSeveralBlankLines() {
			assertEquals(3, getCurrentColumnNumberFrom("abc" + getNewLine() + getNewLine() + "de"));
		}
		
		@Test
		public void aTabShouldOccupyOneColumn() {
			assertEquals(2, getCurrentColumnNumberFrom("\t"));
		}

		protected int getCurrentColumnNumberFrom(final String text) {
			return new OutputBuffer(){
				{
					buffer = new StringBuffer(text);
				}
				public String getNewline() {
					return CurrentColumnNumberTests.this.getNewLine();
				}
			}
			.getCurrentColumnNumber();
		}
	}
}
