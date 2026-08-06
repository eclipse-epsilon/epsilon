/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
/*
 * Copyright 2017 Albert Tregnaghi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 */
package org.eclipse.epsilon.flexmi.dt.yaml;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

/**
 * A special rule to scan yaml variables.
 * 
 * @author Albert Tregnaghi
 *
 */
public class YamlMappingRule implements IPredicateRule {

	/*
	 * This is a security break: The prefix is the space before a mapping. If
	 * the rule comes to an infinite loop (which shall no longer be possible)
	 * this is a fallback to terminate at least the editor. There was an issue
	 * with a file starting with "---" freezing eclipse on startup when editor
	 * was opened. This "fuse" does prevent a freeze.
	 * 
	 */
	private static final int MAXIMUM_PREFIX_BEFORE_INFINIT_LOOP_DETECTED = 10000;

	private IToken token;

	public YamlMappingRule(IToken token) {
		this.token = token;
	}

	@Override
	public IToken getSuccessToken() {
		return token;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		return evaluate(scanner, false);
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		ScanSlider slider = new ScanSlider(scanner);
		boolean startOfDocument = scanner.getColumn() == 0;
		boolean newLine = startOfDocument;
		if (!startOfDocument) {

			/* get char before */
			int cbefore = slider.readBefore();

			newLine = isNewLine(scanner, (char) cbefore);

			StringBuilder prefixSb = null;
			while (!newLine) {
				if (prefixSb == null) {
					prefixSb = new StringBuilder();
				}
				if (prefixSb.length() > MAXIMUM_PREFIX_BEFORE_INFINIT_LOOP_DETECTED) {
					/*
					 * infinite loop detected - but this situation should never
					 * happen any more
					 */
					System.err.println(
							"Yaml editor: Infinite loop detected. Prevent eclipse freeze by terminating editor.");
					// we use the success token to prevent any longer calls of
					// evaluate method
					// will destroy editor when scanner is on wrong position but
					// at least will never freeze eclipse again.
					return getSuccessToken();
				}
				char cb = (char) cbefore;
				/* only when its a space before inspect further */
				boolean hyphenAlreadyInPrefix = prefixSb.indexOf("-") != -1;
				boolean possibleListEntry = cb == '-' && !hyphenAlreadyInPrefix;
				prefixSb.append(cb);
				if (cb == ' ' || cb == '\t' || possibleListEntry) {
					slider.moveBack(); /*
										 * go one step back - so position of
										 * former space
										 */
					slider.moveBack(); /*
										 * additional step back - now one char
										 * before former space
										 */
					if (scanner.getColumn() == 0) {
						newLine = true;
						break;
					}
					cbefore = slider.readBefore();/*
													 * get char before former
													 * space, position now again
													 * at former space
													 */
					newLine = isNewLine(scanner, (char) cbefore);
				} else {
					/* no space, so not an indent, so reset */
					break;
				}
			}
			slider.resetScanner();

		}

		if (!newLine) {
			slider.resetScanner();
			return Token.UNDEFINED;
		}

		char start = (char) slider.moveForward();
		if (!isWordStart(start)) {
			slider.resetScanner();
			return Token.UNDEFINED;
		}
		/* okay is a variable, so read until end reached */
		StringBuilder sb = new StringBuilder();
		do {
			int read = slider.moveForward(); // use int for EOF detection, char makes
										// problems here!
			char c = (char) read;
			if (ICharacterScanner.EOF == read || (!isWordPart(sb, c))) {
				slider.resetScanner();
				return Token.UNDEFINED;
			}
			if (c == ':') {
				// Commented out so that - task:\n is also syntax highlighted
			    /* check if we got a space afterwards. If not its part of the key!*/
			    //c = (char) slider.moveForward();
			    //if (Character.isWhitespace(c)) {
			        return getSuccessToken();
			    //}
			}
		} while (true);
	}

	protected boolean isNewLine(ICharacterScanner scanner, char cbefore) {
		boolean newLine = scanner.getColumn() == 0;
		newLine = newLine || cbefore == '\n';
		newLine = newLine || cbefore == '\r';
		return newLine;
	}

	private boolean isWordPart(StringBuilder sb, char c) {
		if (c == ':' || c == '-' || c == '_'|| c=='.' || c=='*') {
			return true;
		}
		// spaces are allowed inside mappings, see
		// http://yaml.org/spec/1.2/spec.html#id2761803
		if (c == ' ') {
			if (sb.indexOf(":") != -1) {
				return false;// colon detected
			}
			return true;
		}
		if (Character.isLetterOrDigit(c)) {
			return true;
		}
		return false;
	}

	protected boolean isWordStart(char c) {
		return Character.isAlphabetic(c);
	}

}
