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
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.junit.Before;
import org.junit.Test;

public class ModelTypeTests {

	private static final Model     mockModel = mock(Model.class);
	private static final ModelType type      = new ModelType(mockModel, "dummy", "dummy");	
	
	@Before
	public void resetDummyModel() {
		reset(mockModel);
	}
	
	@Test
	public void propertiesSharedWithShouldKeepOnlyThosePropertiesKnownByBothTypes() throws EolModelElementTypeNotFoundException {
		final ModelType mockOtherType = mock(ModelType.class);

		when(mockModel.getPropertiesOf("dummy"))
			.thenReturn(Arrays.asList("name", "age"));
		
		when(mockOtherType.getProperties())
			.thenReturn(Arrays.asList("name", "address"));

		assertEquals(
			Arrays.asList("name"),
			type.getPropertiesSharedWith(mockOtherType)
		);
	}
}
