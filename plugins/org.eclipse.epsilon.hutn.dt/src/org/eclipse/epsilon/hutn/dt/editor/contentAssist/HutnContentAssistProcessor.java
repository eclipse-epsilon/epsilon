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

import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

public class HutnContentAssistProcessor implements IContentAssistProcessor {
	
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		try {
			return computeCompletionProposals(getDocumentText(viewer, offset));
		
		} catch (EolModelLoadingException e) {
			LogUtil.log("Could not compute HUTN completion proposals.", e);
			return new ICompletionProposal[0];
		}
	}
	
	private String getDocumentText(ITextViewer viewer, int offset) {
		try {
			return viewer.getDocument().get(0, offset);
		
		} catch (BadLocationException e) {
			throw new IllegalArgumentException("Offset, " + offset + " is past the end of this document, length: " + viewer.getDocument().getLength());
		}
	}
	
	public ICompletionProposal[] computeCompletionProposals(String text) throws EolModelLoadingException {
		final ContentAssistHelper helper = new ContentAssistHelper(text);
		
		final ProposalsFactory proposalsFactory = new ProposalsFactory(text.length(), helper.lastWord());
		
		for (String proposal : helper.computeCompletionProposals()) {
			proposalsFactory.propose(proposal);
		}
			
		return proposalsFactory.proposals();
	}

	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		return new IContextInformation[0];
	}

	
	public char[] getCompletionProposalAutoActivationCharacters() {
		return null;
	}
	
	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}

	public String getErrorMessage() {
		return null;
	}

}
