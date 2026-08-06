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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

public class ProposalsFactory {
	
	private final int offset;
	private final String replacee;
	
	private final List<String> completions = new LinkedList<>();
	
	public ProposalsFactory(int offset, String replacee) {
		this.offset   = offset;
		this.replacee = replacee;
	}

	
	public void propose(String completion) {
		if (fitsContext(completion))
			completions.add(completion);
	}

	private boolean fitsContext(String completion) {
		return completion.startsWith(replacee);
	}

	
	public ICompletionProposal[] proposals() {
		final Collection<ICompletionProposal> proposals = new LinkedList<>();
		
		Collections.sort(completions);
		
		for (String completion : completions) {
			proposals.add(createCompletionProposal(completion));
		}
		
		return proposals.toArray(new ICompletionProposal[0]);
	}
	
	private ICompletionProposal createCompletionProposal(String completion) {
		return new CompletionProposal(completion, offset - replacee.length(), replacee.length(), completion.length());
	}
}
