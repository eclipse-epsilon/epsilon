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
package org.eclipse.epsilon.hutn.unparser.formatter;

import java.util.regex.Pattern;

import org.eclipse.epsilon.egl.formatter.language.JavaFormatter;

public class HutnFormatter extends JavaFormatter {

	@Override
	public String format(String text) {
		return super.format(correctLineBreaks(text));
	}

	private String correctLineBreaks(String text) {
		final StringBuilder sb = new StringBuilder();
				
		// Iterate over each line in text
		for (String line : Pattern.compile("^", Pattern.MULTILINE).split(text)) {
			sb.append(line.replaceAll("\\{", "{\n").replaceAll("\\}\\}", "}\n}"));
		}
		
		return sb.toString();
	}
}
