/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.util;

public abstract class StringUtil {

	// Uninstantiable class
	private StringUtil() {}
	
	public static String normalizeNewlines(String s) {
		return s.replaceAll("\r\n", "\n").replaceAll("\r", "\n");
	}
	
	public static boolean isWhitespace(String s) {
		char[] chars = s.toCharArray();
		
		boolean charsAreWhitespace = true;
		int index = 0; 
		
		while (charsAreWhitespace && index < chars.length) {
			charsAreWhitespace = Character.isWhitespace(chars[index++]);		
		}
		
		return charsAreWhitespace;
	}
	
	public static String repeat(String text, int times) {
		final StringBuilder sb = new StringBuilder();
		
		for (int count = 0; count < times; count++) {
			sb.append(text);
		}
		
		return sb.toString();
	}
	
	public static String tabs(int times) {
		return repeat("\t", times);
	}
}
