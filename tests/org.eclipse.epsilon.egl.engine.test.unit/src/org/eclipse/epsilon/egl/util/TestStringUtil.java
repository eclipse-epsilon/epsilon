/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.util;

import static org.eclipse.epsilon.egl.util.StringUtil.normalizeNewlines;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestStringUtil {

	@Test
	public void normaliseNewlineShouldNotChangeStringWithoutNewlines() {
		assertEquals("foo", normalizeNewlines("foo"));
	}
	
	@Test
	public void normaliseNewlineShouldReplaceWindowsNewLineWithUnixNewLine() {
		assertEquals("foo\nbar", normalizeNewlines("foo\r\nbar"));
	}
	
	@Test
	public void normaliseNewlineShouldReplaceOldMacOsNewLineWithUnixNewLine() {
		assertEquals("foo\nbar", normalizeNewlines("foo\rbar"));
	}
	
	@Test
	public void normaliseNewlineShouldReplaceAllNewLinesWithUnixNewLines() {
		assertEquals("foo\nbar\nbaz\nbaaz", normalizeNewlines("foo\r\nbar\rbaz\nbaaz"));
	}
}
