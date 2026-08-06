/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.editor.autoclose;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;

public class AutoCloseEditStrategy implements IAutoEditStrategy {

	protected List<AutoClosePair> autoClosePairs = new ArrayList<>();
	protected AutoCloseAction lastAutoCloseAction = AutoCloseAction.NoAutoCloseAction;
	
	public AutoCloseEditStrategy() {
		autoClosePairs.add(new AutoClosePair('"', '"'));
		autoClosePairs.add(new AutoClosePair('\'', '\''));
		autoClosePairs.add(new AutoClosePair('(', ')'));
		autoClosePairs.add(new AutoClosePair('{', '}'));
		//autoClosePairs.add(new AutoClosePair('[', ']'));
	}
	
	@Override
	public void customizeDocumentCommand(IDocument document, DocumentCommand command) {
		
		AutoCloseCandidate autoCloseCandidate = new AutoCloseCandidate(document, command);
		
		AutoCloseAction autoCloseAction = AutoCloseAction.NoAutoCloseAction;
		if (autoCloseCandidate.isBackspace() 
				&& lastAutoCloseAction != AutoCloseAction.NoAutoCloseAction
				&& lastAutoCloseAction.getOffset() == command.offset) {
			
			command.text = "";
			command.length = 2;
			command.shiftsCaret = false;
		}
		else if (autoCloseCandidate.isSingleChar() && !Character.isJavaIdentifierPart(autoCloseCandidate.getNextChar())) {
		
			char ch = autoCloseCandidate.getChar();
			
			for (AutoClosePair pair : autoClosePairs) {
				if (pair.getLeft() == ch || pair.getRight() == ch) {
					if (pair.getRight() == ch && lastAutoCloseAction.matches(pair, command.offset)) {
						autoCloseCandidate.ignore();
					}
					else if (pair.getLeft() == ch){
						if (pair.isDifferent() || autoCloseCandidate.evenInCurrentLine(ch)) {
							if (Character.isWhitespace(autoCloseCandidate.getNextChar())) {
								autoCloseAction = autoCloseCandidate.close(pair);
							}
						}
					}
					break;
				}
			}
		}
		this.lastAutoCloseAction = autoCloseAction;
	}
}
