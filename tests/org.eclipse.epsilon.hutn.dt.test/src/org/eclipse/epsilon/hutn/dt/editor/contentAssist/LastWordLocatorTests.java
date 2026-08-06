/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.dt.editor.contentAssist;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LastWordLocatorTests {
	
	@Test
	public void shouldTreatSpaceAsDelimiter() {
		assertEquals("bar", lastWord("foo bar"));
	}
	
	@Test
	public void shouldTreatTabAsDelimiter() {
		assertEquals("bar", lastWord("foo\tbar"));
	}
	
	@Test
	public void shouldTreatNewlineAsDelimiter() {
		assertEquals("bar", lastWord("foo\nbar"));
	}
	
	@Test
	public void shouldTreatAllWhitespaceAsDelimiter() {
		assertEquals("bar", lastWord("foo \n\t bar"));
	}
	
	@Test
	public void shouldTreatStartOfInputAsDelimiter() {
		assertEquals("foo", lastWord("foo"));
	}
	
	@Test
	public void shouldReturnEmptyStringWhenInputEndsWithWhitespace() {
		assertEquals("", lastWord("foo "));
	}

	
	private String lastWord(String input) {
		return new LastWordLocator().lastWord(input);
	}
}
