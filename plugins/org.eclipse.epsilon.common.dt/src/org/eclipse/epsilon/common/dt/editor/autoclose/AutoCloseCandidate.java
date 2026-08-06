/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.dt.editor.autoclose;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

public class AutoCloseCandidate {

	protected IDocument document;
	protected DocumentCommand command;

	public boolean isBackspace() {
		return command.length > 0 && command.text.length() == 0;
	}
	
	public boolean isSingleChar() {
		return command.length >= 0 && command.text != null
				&& command.text.length() == 1;
	}

	public char getChar() {
		return command.text.charAt(0);
	}

	public char getNextChar() {
		try {
			return document.getChar(command.offset);
		} catch (BadLocationException e) {
			return ' ';
		}
	}
	
	public AutoCloseAction close(AutoClosePair pair) {
		command.text += pair.getRight();
		command.caretOffset = command.offset + 1;
		command.shiftsCaret = false;
		return new AutoCloseAction(pair, command.offset);
	}

	public void ignore() {
		command.caretOffset = command.offset + 1; // already there -> shift
													// caret
		command.shiftsCaret = false;
		command.text = "";
	}

	public AutoCloseCandidate(IDocument document, DocumentCommand command) {
		super();
		this.document = document;
		this.command = command;
	}
	
	public boolean evenInCurrentLine(char what) {
		return !oddInCurrentLine(what);
	}
	
	public boolean oddInCurrentLine(char what) {
		return countInCurrentLine(what) % 2 == 1;
	}
	
	public int countInCurrentLine(char what) {
		int count = 0;
		
		try {
			IRegion info = document.getLineInformationOfOffset(command.offset);
	
			int start = info.getOffset();
			int end = command.offset;
	
			for (int i = start; i < end; ++i) {
				char ch = document.getChar(i);
				if (ch == what) {
					++count;
				}
			}
		}
		catch (Exception ex) {}
		
		return count;
	}

	public IDocument getDocument() {
		return document;
	}

	public void setDocument(IDocument document) {
		this.document = document;
	}

	public DocumentCommand getCommand() {
		return command;
	}

	public void setCommand(DocumentCommand command) {
		this.command = command;
	}

}
