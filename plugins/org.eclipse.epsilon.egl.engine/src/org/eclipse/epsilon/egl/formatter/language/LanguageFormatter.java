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

import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.epsilon.egl.formatter.Formatter;
import org.eclipse.epsilon.egl.util.StringUtil;

public abstract class LanguageFormatter implements Formatter {

	private final Pattern increasePattern, decreasePattern;
	
	protected LanguageFormatter(String increasePattern, String decreasePattern) {
		this.increasePattern = Pattern.compile(increasePattern);
		this.decreasePattern = Pattern.compile(decreasePattern);
	}

	protected LanguageFormatter(Pattern increasePattern, Pattern decreasePattern) {
		this.increasePattern = increasePattern;
		this.decreasePattern = decreasePattern;
	}
	
    // Removes any existing indentation
	private static String clean(String text) {
		final StringBuilder sb = new StringBuilder();
		
		// Iterate over each line in the text
		for (String line : Pattern.compile("^", Pattern.MULTILINE).split(text)) {
			
			if (line.length() > 0) {
				int  position = 0;
				char current  = line.charAt(0);
			
				// Count the number of leading space and tab characters
				while ((position < line.length() - 1) && (current == ' ' || current == '\t')) {
					position++;
					current = line.charAt(position);
				}

				// Append the contents of the line, excluding any leading 
				// space and tab characters
				sb.append(line.substring(position));
			}
		}
		
		return sb.toString();
	}
	
	@Override
	public String format(String text) {
		final String cleaned = clean(text);
		
		final Matcher increaseMatcher = increasePattern.matcher(cleaned);
		final Matcher decreaseMatcher = decreasePattern.matcher(cleaned);
		
		final Queue<Integer> increaseIndentationAt = new LinkedList<>();
		final Queue<Integer> decreaseIndentationAt = new LinkedList<>();
		
		// Determine the positions at which indentation should be increased
		while (increaseMatcher.find()) {
			increaseIndentationAt.offer(increaseMatcher.end());
		}
		
		// Determine the positions at which indentation should be decreased
		while (decreaseMatcher.find()) {
			decreaseIndentationAt.offer(decreaseMatcher.start());
		}

		final StringBuilder sb = new StringBuilder();
		
		int position = 0;
		int indent   = 0;
		
		// Iterate over each line of the text to be beautified
		for (String line : Pattern.compile("^", Pattern.MULTILINE).split(cleaned)) {
			position += line.length();
			
			// Add any leading indentation
			sb.append(StringUtil.tabs(indent));
			
			// Determine whether the next line should be indented more
			while (!increaseIndentationAt.isEmpty() &&
				       position > increaseIndentationAt.peek()) {
					indent++;
					increaseIndentationAt.remove();
				}
			
			// Determine whether the next line should be indented less
			while (!decreaseIndentationAt.isEmpty() &&
				       position > decreaseIndentationAt.peek()) {
				
				// If the indentation is to be decreased on the next line, and the
				// matching decrease character sequence starts at the beginning
				// of this line, remove one of the tabs already added to this line
				if (position - line.length() == decreaseIndentationAt.peek() &&
				    sb.charAt(sb.length()-1) == '\t') {
						sb.deleteCharAt(sb.length()-1);
				}
				
				indent--;
				decreaseIndentationAt.remove();
			}
			
			// Append the contents of the line, including the line terminator char(s)
			sb.append(line);
		}
		
		return sb.toString();
	}

}
