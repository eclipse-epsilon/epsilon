/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.editor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.ui.editors.text.FileDocumentProvider;

public class EglProvider extends FileDocumentProvider {

	public static final String EGL_EOL         = "org.eclipse.epsilon.egl.dt.editor.EOL";
	public static final String EGL_EOLSHORTCUT = "org.eclipse.epsilon.egl.dt.editor.EOLSHORTCUT";
	public static final String EGL_COMMENT     = "org.eclipse.epsilon.egl.dt.editor.COMMENT";
	public static final String EGL_MARKER     = "org.eclipse.epsilon.egl.dt.editor.MARKER";
	
	
	@Override
	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		if (document != null) {
			IDocumentPartitioner partitioner = new FastPartitioner(createScanner(), new String[] {
				EGL_EOL,
				EGL_EOLSHORTCUT,
				EGL_COMMENT,
				EGL_MARKER});
		
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
		return document;
	}
	
	private IPartitionTokenScanner createScanner() {
		IToken eolshort = new Token(EGL_EOLSHORTCUT);
		IToken eol      = new Token(EGL_EOL);
		IToken comment  = new Token(EGL_COMMENT);
		IToken marker  = new Token(EGL_MARKER);
		
		IPredicateRule[] rules = new IPredicateRule[4];
		rules[0] = new MultiLineRule("[*-", "*]",  marker,  (char)0, true);
		rules[1] = new MultiLineRule("[*", "*]",  comment,  (char)0, true);
		rules[2] = new MultiLineRule("[%=", "%]", eolshort, (char)0, true);
		rules[3] = new MultiLineRule("[%", "%]",  eol,      (char)0, true);
		
		RuleBasedPartitionScanner scanner = new RuleBasedPartitionScanner();
		scanner.setPredicateRules(rules);
		
		return scanner;
	}
}

