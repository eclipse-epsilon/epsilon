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

public class Contextualiser {

	public String contextualise(String text) {
		if (containsNoLeftBraces(text))
			return null;
		
		return lastWordOf(removeIdentifiers(textBeforeLastUnclosedBrace(text)));
	}

	private boolean containsNoLeftBraces(String text) {
		return !text.contains("{");
	}

	private String textBeforeLastUnclosedBrace(String text) {
		int level = 0;
		
		for (int index = text.length() - 1; index >= 0; index--) {
			final char current = text.charAt(index);
			
			if (current == '}') {
				level++;
			
			} else if (current == '{') {
				if (level == 0) {
					return text.substring(0, index - 1);
				
				} else {
					level--;
				}
			}
		}
		
		return "";
	}

	private String removeIdentifiers(String text) {
		final String quoteRegex      = "\"";
		final String identifierRegex = quoteRegex + ".*" + quoteRegex;
		
		return text.replaceAll(identifierRegex, "");
	}

	private String lastWordOf(String text) {
		return new LastWordLocator().lastWord(text.trim());
	}
}
