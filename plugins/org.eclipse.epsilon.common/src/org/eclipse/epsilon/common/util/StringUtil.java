/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringUtil {
	
	public static Collection<String> split(String str, String delimiter) {
		if (str == null) return Collections.emptyList();
		
		return Stream.of(str.split(delimiter))
			.map(String::trim)
			.collect(Collectors.toList());
	}
	
	public static boolean isEmpty(String str)  {
		return str == null || str.trim().isEmpty();
	}
	
	public static Object print(Object o) {
		return o;
	}
	
	public static String firstToUpper(String str) {
		String result = str.substring(1, str.length());
		result = str.substring(0,1).toUpperCase() + result;
		return result;
	}
	
	public static String firstToLower(String str) {
		String result = str.substring(1, str.length());
		result = str.substring(0,1).toLowerCase() + result;
		return result;
	}
	
	public static boolean areEqual(String s1, String s2) {
		return toString(s1).equalsIgnoreCase(toString(s2));
	}
	
	public static String toString(Object o) {
		return Objects.toString(o, "");
	}
	
	public static boolean isOneOf(String target, String...candidates) {
		for (String candidate : candidates) {
			if (candidate.equals(target)) return true;
		}
		return false;
	}
	
	public static String escapeHtml(String string) {
		if (string == null) return "";
	    StringBuffer sb = new StringBuffer(string.length());
	    // true if last char was blank
	    boolean lastWasBlankChar = false;
	    int len = string.length();
	    char c;

	    for (int i = 0; i < len; i++) {
	        c = string.charAt(i);
	        if (c == ' ') {
	            // blank gets extra work,
	            // this solves the problem you get if you replace all
	            // blanks with &nbsp;, if you do that you loss 
	            // word breaking
	            if (lastWasBlankChar) {
	                lastWasBlankChar = false;
	                sb.append("&nbsp;");
	                }
	            else {
	                lastWasBlankChar = true;
	                sb.append(' ');
	                }
	            }
	        else {
	            lastWasBlankChar = false;
	            //
	            // HTML Special Chars
	            if (c == '"')
	                sb.append("&quot;");
	            else if (c == '&')
	                sb.append("&amp;");
	            else if (c == '<')
	                sb.append("&lt;");
	            else if (c == '>')
	                sb.append("&gt;");
	            else if (c == '\n')
	                // Handle Newline
	                sb.append("&lt;br/&gt;");
	            else {
	                int ci = 0xffff & c;
	                if (ci < 160) {
	                    // nothing special only 7 Bit
	                    sb.append(c);
	                }
	                else {
	                    // Not 7 Bit use the unicode system
	                    sb.append("&#");
	                    sb.append(ci);
	                    sb.append(';');
	                }
	            }
	        }
	    }
	    return sb.toString();
	}

	public static String reverse(String text) {
		return new StringBuilder(text).reverse().toString();
	}

	public static String concat(String separator, Object[] items) {
		final StringBuffer sbuf = new StringBuffer();
		boolean first = true;
		for (Object o : items) {
			if (first) {
				first = false;
			} else {
				sbuf.append(separator);
			}
			sbuf.append(o + "");
		}
		return sbuf.toString();
	}
	
	/**
	 * 
	 * @param obj
	 * @return
	 * @since 1.6
	 */
	public static String stripHashCodes(Object obj) {
		if (obj != null) {
			return obj.toString().replaceAll("@([0-9a-fA-F]{1,8})", "");
		}
		return null;
	}
	
	/**
	 * 
	 * @param inputStream
	 * @return
	 * @since 2.2
	 */
	public static String inputStreamToString(java.io.InputStream inputStream) {
	    try (java.util.Scanner s = new java.util.Scanner(inputStream).useDelimiter("\\A")) {
	    	return s.hasNext() ? s.next() : "";
	    }
	}
}
