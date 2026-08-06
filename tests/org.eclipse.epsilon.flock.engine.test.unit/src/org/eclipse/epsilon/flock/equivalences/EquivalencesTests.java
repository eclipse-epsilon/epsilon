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
package org.eclipse.epsilon.flock.equivalences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.eclipse.epsilon.flock.emc.wrappers.ModelElement;
import org.junit.Test;

public class EquivalencesTests {
	
	@Test
	public void getEquivalentShouldReturnEquivalentOfMatchingEquivalence() {
		final Equivalences equivalences = new Equivalences();
		
		final ModelElement original   = createDummyModelElement();
		final ModelElement equivalent = createDummyModelElement();
		
		final Equivalence anEquivalence = createEquivalence();
		final Equivalence matchingEquivalence = createEquivalence(original, equivalent);
		final Equivalence otherEquivalence = createEquivalence();
		
		equivalences.add(anEquivalence);
		equivalences.add(matchingEquivalence);
		equivalences.add(otherEquivalence);
				
		assertEquals(equivalent, equivalences.getEquivalent(original));
	}
	
	@Test
	public void getEquivalentShouldReturnNullWhenNoEquivalenceMatches() {
		final Equivalences equivalences = new Equivalences();
		
		final Equivalence anEquivalence = createEquivalence();
		final Equivalence otherEquivalence = createEquivalence();
		
		equivalences.add(anEquivalence);
		equivalences.add(otherEquivalence);
				
		assertNull(equivalences.getEquivalent(createDummyModelElement()));
	}
	
	@Test
	public void getEquivalentShouldReturnNullWhenContainingNoEquivalence() {
		final Equivalences equivalences = new Equivalences();
		
		assertNull(equivalences.getEquivalent(createDummyModelElement()));
	}

	private ModelElement createDummyModelElement() {
		return mock(ModelElement.class);
	}
	
	private Equivalence createEquivalence() {
		return createEquivalence(createDummyModelElement(), createDummyModelElement());
	}
	
	private Equivalence createEquivalence(ModelElement original, ModelElement equivalent) {
		final Equivalence eq = mock(Equivalence.class);
		when(eq.getOriginal()).thenReturn(original);
		when(eq.getEquivalent()).thenReturn(equivalent);
		return eq;
	}	
}
