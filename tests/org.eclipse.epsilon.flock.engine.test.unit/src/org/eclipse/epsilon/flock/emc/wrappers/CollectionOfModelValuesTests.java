/*******************************************************************************
 * Copyright (c) 2009-2020 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 *     Sina Madani - refactor to Mockito
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.flock.emc.wrappers;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import org.eclipse.epsilon.flock.context.ConservativeCopyContext;
import org.eclipse.epsilon.flock.execute.exceptions.ConservativeCopyException;
import org.junit.Test;

public class CollectionOfModelValuesTests {

	private static final Model dummyModel  = mock(Model.class);

	@SuppressWarnings("rawtypes")
	@Test
	public void unwrapShouldDelegateToUnwrapOfEachElement() {
		final BackedModelValue firstMockModelValue  = mock(BackedModelValue.class);
		final BackedModelValue secondMockModelValue = mock(BackedModelValue.class);
		
		when(firstMockModelValue.unwrap()).thenReturn("foo");
		
		when(secondMockModelValue.unwrap()).thenReturn("bar");
		
		assertEquals(
			Arrays.asList("foo", "bar"),
			new CollectionOfModelValues(dummyModel, firstMockModelValue, secondMockModelValue).unwrap()
		);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void getEquivalentShouldDelegateToGetEquivalentOfEachElement() throws ConservativeCopyException {
		final Model               dummyMigratedModel = mock(Model.class);
		final ConservativeCopyContext dummyContext       = mock(ConservativeCopyContext.class);
		
		final BackedModelValue firstMockModelValue  = mock(BackedModelValue.class);
		final BackedModelValue secondMockModelValue = mock(BackedModelValue.class);

		
		// Expectations
		
		when(firstMockModelValue.getEquivalentIn(dummyMigratedModel, dummyContext))
			.thenReturn(new AttributeValue(dummyMigratedModel, "foo"));
		
		when(secondMockModelValue.getEquivalentIn(dummyMigratedModel, dummyContext))
			.thenReturn(new AttributeValue(dummyMigratedModel, "bar"));
		
		assertEquals(
			new CollectionOfModelValues(dummyMigratedModel, new AttributeValue(dummyMigratedModel, "foo"), new AttributeValue(dummyMigratedModel, "bar")),
			new CollectionOfModelValues(dummyModel, firstMockModelValue, secondMockModelValue).getEquivalentIn(dummyMigratedModel, dummyContext)
		);
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void getEquivalentShouldExcludeValuesThatUnwrapToNull() throws ConservativeCopyException {
		final Model               dummyMigratedModel = mock(Model.class);
		final ConservativeCopyContext dummyContext       = mock(ConservativeCopyContext.class);
		
		final BackedModelValue mockOriginalModelValue    = mock(BackedModelValue.class);
		final BackedModelValue mockEquivalentModelValue  = mock(BackedModelValue.class);

		when(mockOriginalModelValue.getEquivalentIn(dummyMigratedModel, dummyContext))
			.thenReturn(mockEquivalentModelValue);
		
		when(mockEquivalentModelValue.unwrap()).thenReturn(null);

		assertEquals(
			new CollectionOfModelValues(dummyMigratedModel),
			new CollectionOfModelValues(dummyModel, mockOriginalModelValue).getEquivalentIn(dummyMigratedModel, dummyContext)
		);
	}
}
