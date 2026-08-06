/*******************************************************************************
 * Copyright (c) 2010 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.bibtex.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.epsilon.emc.bibtex.domain.Bibliography;
import org.eclipse.epsilon.emc.bibtex.domain.Publication;

public class BibtexParser {

	private final String bibtex;
	
	public BibtexParser(String bibtex) {
		this.bibtex = bibtex;
	}

	public Bibliography parse() {
		final Bibliography bibliography = new Bibliography();
		final Matcher entryMatcher = getEntryPattern().matcher(bibtex);
		
		while (entryMatcher.find()) {
			final String type       = entryMatcher.group(1);
			final String id         = entryMatcher.group(2);
			final String properties = entryMatcher.group(3);
			
			bibliography.add(new Publication(type, id, parseProperties(properties)));
		}
		
		return bibliography;
	}

	private Map<String, String> parseProperties(String rawProperties) {
		final Map<String, String> properties = new HashMap<>();
		final Matcher propertyMatcher = getPropertyPattern().matcher(rawProperties);
		
		while (propertyMatcher.find()) {
			final String key = propertyMatcher.group(1);
			final String partialValue = propertyMatcher.group(2);
			final int endOfPartialValue = propertyMatcher.end(2);
			
			properties.put(key, complete(partialValue, endOfPartialValue, rawProperties));
		}
		
		return properties;
	}

	private String complete(String partialValue, int endOfPartialValue, String rawProperties) {
		return new BibtexPropertyValueCompleter(partialValue, endOfPartialValue, rawProperties).complete();
	}
	
	private final static String WHITESPACE = "\\s*";
	private final static String WORD = "\\w*";
	
	private final static String ANYTHING_BUT_AT = "[^@]*";
	private final static  String ANYTHING_BUT_A_BRACKET = "[^\\}]*";
	
	private final static  String BRACKETED_TEXT = "\\{" + "(" + ANYTHING_BUT_A_BRACKET + ")" + "\\}";
	private final static  String PROPERTY_NAME = "(" + WORD + ")";
	
	
	private static Pattern getEntryPattern() {
		final String type = "@" + "(" + WORD + ")" + WHITESPACE; // @article
		final String id   = "\\{" + "(" + WORD + ")";            // {selic02uml
		final String body = "(" + ANYTHING_BUT_AT + ")";         //  ... },
		
		return Pattern.compile(type + id + body);
	}

	private static Pattern getPropertyPattern() {
		return Pattern.compile(PROPERTY_NAME + WHITESPACE + "=" + WHITESPACE + BRACKETED_TEXT);
	}
}
