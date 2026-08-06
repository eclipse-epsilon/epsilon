/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

public abstract class StringUtil {

	private StringUtil() {}
	
	public static String stripQuotes(String quoted) {
		if (quoted.startsWith("\"") && quoted.endsWith("\""))
			return quoted.substring(1, quoted.length() - 1);
		
		return quoted;
	}
	
	public static List<String> getLines(String s) {
		final List<String> lines = new LinkedList<>();
		final BufferedReader reader = new BufferedReader(new StringReader(s));
		
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			lines.clear();
		}
		
		return lines;
	}
	
	public static String getFirstLine(String s) {
		return getLines(s).get(0);
	}
}
