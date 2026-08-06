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
import org.eclipse.epsilon.hutn.test.model.HutnTestWithFamiliesMetaModel;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.junit.Test;

public class HutnContentAssistProcessorTests extends HutnTestWithFamiliesMetaModel {

	@Test
	public void shouldSuggestConcreteClassNamesWhenAtPackageLevel() throws Exception {
		final String text = "@Spec {"                    +
		                    "	Metamodel {"             +
		                    "		nsUri: \"families\"" + 
		                    "	}"                       +
		                    "}"                          +
		                    "families { ";
				
		assertContainsAll(text,
			"Band", "Bike", "District", "Dog", "Family", "Model", "Person", "Pet", "Suburb"
		);
	}
	
	
	@Test
	public void shouldSuggestSlotNamesWhenAtClassLevel() throws Exception {
		final String text = "@Spec {"                    +
		                    "	Metamodel {"             +
		                    "		nsUri: \"families\"" + 
		                    "	}"                       +
		                    "}"                          +
		                    "families { " +
		                    "  Person { ";
		
		assertContainsAll(text,
			"accounts: ", "allParents: ", "friends: ", "name: ", "parents: ", "sharedAccounts: "
		);
		
	}
	
	private static void assertContainsAll(String text, String... expecteds) throws Exception {
		ICompletionProposal[] proposals = new HutnContentAssistProcessor().computeCompletionProposals(text);
		assertEquals(expecteds.length, proposals.length);
		for (int i = 0; i < proposals.length; i++) {
			assertEquals(proposals[i].getDisplayString(), expecteds[i]);
		}
	}
	
	// For debugging
	protected static void print(ICompletionProposal[] proposals) {
		for (ICompletionProposal proposal : proposals) {
			System.err.println(proposal.getDisplayString());
		}
	}
}
