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
import org.eclipse.epsilon.flock.context.ConservativeCopyContext;
import org.eclipse.epsilon.flock.execute.exceptions.ConservativeCopyException;
import org.junit.Test;

public class AttributeValueTests {

	private static final Model          dummyModel  = mock(Model.class);
	private static final AttributeValue value       = new AttributeValue(dummyModel, "foo");
	
	@Test
	public void unwrapShouldReturnUnderlyingModelObject() {
		assertEquals("foo", value.unwrap());
	}
	
	@Test
	public void getEquivalentShouldCreateEquivalentValue() throws ConservativeCopyException {
		final Model               dummyMigratedModel = mock(Model.class);
		final ConservativeCopyContext dummyContext       = mock(ConservativeCopyContext.class);
		
		assertEquals(
			new AttributeValue(dummyMigratedModel, "foo"),
			value.getEquivalentIn(dummyMigratedModel, dummyContext)
		);
	}
}
