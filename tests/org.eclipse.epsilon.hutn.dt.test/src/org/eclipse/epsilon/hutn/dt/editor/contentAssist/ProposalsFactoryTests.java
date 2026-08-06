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

import static org.junit.Assert.assertEquals;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.junit.Test;

public class ProposalsFactoryTests {

	@Test
	public void allProposalsAcceptedWhenNoContext() throws Exception {
		final ProposalsFactory fac = new ProposalsFactory(0, "");
		fac.propose("cat");
		fac.propose("dog");
		
		assertContainsAll(fac, "cat", "dog");
	}
	
	@Test
	public void proposalsAreFilteredToFitContext() throws Exception {
		final ProposalsFactory fac = new ProposalsFactory(1, "d");
		fac.propose("cat");
		fac.propose("dog");
		
		assertContainsAll(fac, "dog");
	}
	
	@Test
	public void proposalsAreSortedAlphabetically() throws Exception {
		final ProposalsFactory fac = new ProposalsFactory(0, "");
		fac.propose("dog");
		fac.propose("cat");
		fac.propose("emu");
		
		assertContainsAll(fac, "cat", "dog", "emu");
	}
	
	private static void assertContainsAll(ProposalsFactory fac, String... expecteds) throws Exception {
		ICompletionProposal[] proposals = fac.proposals();
		assertEquals(expecteds.length, proposals.length);
		for (int i = 0; i < proposals.length; i++) {
			assertEquals(proposals[i].getDisplayString(), expecteds[i]);
		}
	}
}
