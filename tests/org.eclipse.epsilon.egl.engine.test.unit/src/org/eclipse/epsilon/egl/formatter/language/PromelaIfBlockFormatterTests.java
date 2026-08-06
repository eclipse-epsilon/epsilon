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
import static org.eclipse.epsilon.egl.util.FileUtil.NEWLINE;
import org.junit.Test;

public class PromelaIfBlockFormatterTests {

	@Test
	public void indentsAfterADoubleColon() throws Exception {
		final String input = "if"        + NEWLINE +
		                     ":: a && b" + NEWLINE + 
		                     "c;";
		
		final String expected = "if"         + NEWLINE + 
		                         ":: a && b" + NEWLINE + 
		                        "\tc;";

		assertEquals(expected, format(input));
	}
	
	@Test
	public void indentationContinuesUntilNextDoubleColon() throws Exception {
		final String input = "if"        + NEWLINE + 
		                     ":: a && b" + NEWLINE + 
		                     "c;"        + NEWLINE +
		                     "d;"        + NEWLINE +
		                     ":: e || f";
		
		final String expected = "if"        + NEWLINE + 
		                        ":: a && b" + NEWLINE + 
		                        "\tc;"      + NEWLINE +
		                        "\td;"      + NEWLINE +
		                        ""          + NEWLINE +
		                        ":: e || f";

		assertEquals(expected, format(input));
	}
	
	@Test
	public void indentationContinuesUntilEndIfKeyword() throws Exception {
		final String input = "if"        + NEWLINE +
		                     ":: a && b" + NEWLINE + 
		                     "c;"        + NEWLINE +
		                     "d;"        + NEWLINE +
		                     "fi;";
		
		final String expected = "if"        + NEWLINE +
		                        ":: a && b" + NEWLINE + 
		                        "\tc;"      + NEWLINE +
		                        "\td;"      + NEWLINE +
		                        "fi;";

		assertEquals(expected, format(input));
	}
	
	@Test
	public void reducesIndentationAtNextDoubleColon() throws Exception {
		final String input = "if"        + NEWLINE +
		                     ":: a && b" + NEWLINE +
		                     "c;"        + NEWLINE +
		                     ":: d || e" + NEWLINE +
		                     "f;";
		
		final String expected = "if"        + NEWLINE +
		                        ":: a && b" + NEWLINE + 
		                        "\tc;"      + NEWLINE +
		                        ""          + NEWLINE +
		                        ":: d || e" + NEWLINE +
		                        "\tf;";

		assertEquals(expected, format(input));
	}
	
	@Test
	public void noIndentationUnlessInAnIfBlock() throws Exception {
		final String input = ":: a && b" + NEWLINE + 
		                     "c;"        + NEWLINE +
		                     ":: d || e" + NEWLINE +
		                     "f;";
		
		assertEquals(input, format(input));
	}

	private static String format(String text) {
		return new PromelaFormatter().format(text);
	}
}
