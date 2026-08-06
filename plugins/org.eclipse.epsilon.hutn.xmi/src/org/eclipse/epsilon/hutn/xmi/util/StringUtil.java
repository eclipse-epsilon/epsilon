/*******************************************************************************
 * Copyright (c) 2008 The University of York.
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
package org.eclipse.epsilon.hutn.xmi.util;


public abstract class StringUtil {

	private StringUtil() {}
	
	public static boolean isWhitespace(String s) {
    	return s.trim().length() == 0;
    }
	
	public static boolean isNotWhitespace(String s) {
    	return !isWhitespace(s);
    }
	
	public static String removeLeading(char toRemove, String s) {
		if (s.length() == 0)
			return s;
		
		return s.charAt(0) == toRemove ? s.substring(1) : s;
	}
}
