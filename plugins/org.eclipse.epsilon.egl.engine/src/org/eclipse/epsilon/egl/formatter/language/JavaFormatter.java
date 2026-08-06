/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.formatter.language;

import java.util.regex.Pattern;

public class JavaFormatter extends LanguageFormatter {

	// Increase indentation after every open bracket that terminates a line
	// (allowing for any whitespace between the bracket and the line terminator)
	private static final String increasePattern = "\\{\\s*$";
	
	// Decrease indentation after every close bracket that begins a line
	private static final String decreasePattern = "^\\}";
	
	public JavaFormatter() {
		super(Pattern.compile(increasePattern, Pattern.MULTILINE),
		      Pattern.compile(decreasePattern, Pattern.MULTILINE));
	}

}
