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
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.flock.context.ConservativeCopyContext;
import org.eclipse.epsilon.flock.execute.exceptions.ConservativeCopyException;
import org.eclipse.epsilon.hutn.test.model.families.DogBreed;
import org.junit.Test;

public class EnumValueTests {

	private static final Model     dummyModel  = mock(Model.class);
	private static final EnumValue value       = new EnumValue(dummyModel, DogBreed.LABRADOR);
	
	@Test
	public void unwrapShouldReturnUnderlyingModelObject() {
		assertEquals(DogBreed.LABRADOR, value.unwrap());
	}
	
	@Test
	public void getEquivalentShouldCreateEquivalentValue() throws ConservativeCopyException, EolRuntimeException {
		final Model               mockMigratedModel = mock(Model.class);
		final ConservativeCopyContext dummyContext  = mock(ConservativeCopyContext.class);
		
		when(mockMigratedModel.getEquivalentEnumerationValue(DogBreed.LABRADOR))
			.thenReturn(DogBreed.POODLE);
		
		assertEquals(new EnumValue(mockMigratedModel, DogBreed.POODLE),
			value.getEquivalentIn(mockMigratedModel, dummyContext)
		);
	}
}
