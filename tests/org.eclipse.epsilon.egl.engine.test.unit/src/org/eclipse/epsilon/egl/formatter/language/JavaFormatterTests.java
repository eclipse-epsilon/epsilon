/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.formatter.language;

import static org.junit.Assert.*;
import org.junit.Test;

public class JavaFormatterTests {

	@Test
	public void formattingALineContainingOnlyWhitespaceDoesNothing() throws Exception {
		final String text = "\t";
		
		assertEquals(text, format(text));
	}

	private static String format(final String text) {
		return new JavaFormatter().format(text);
	}
}
