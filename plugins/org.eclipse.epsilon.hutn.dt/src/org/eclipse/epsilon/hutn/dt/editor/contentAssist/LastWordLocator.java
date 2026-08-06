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

import static java.lang.Character.isWhitespace;


public class LastWordLocator {

	public String lastWord(String input) {
		if (isWhitespace(lastChar(input)))
			return "";
		
		return lastElement(input.split("\\s"));
	}

	
	private static char lastChar(String s) {
		return s.charAt(s.length() - 1);
	}
	
	private static <T> T lastElement(T[] array) {
		return array[array.length - 1];
	}

}
