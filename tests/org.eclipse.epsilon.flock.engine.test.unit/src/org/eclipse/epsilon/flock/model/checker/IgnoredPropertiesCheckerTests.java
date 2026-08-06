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
package org.eclipse.epsilon.flock.model.checker;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.eclipse.epsilon.flock.context.MigrationStrategyCheckingContext;
import org.eclipse.epsilon.flock.execute.exceptions.FlockRuntimeException;
import org.junit.Test;

public class IgnoredPropertiesCheckerTests {

	private final MigrationStrategyCheckingContext context = mock(MigrationStrategyCheckingContext.class);
	
	@Test
	public void ignoringAnUnknownPropertyProducesAWarning() throws FlockRuntimeException {
		when(context.isPropertyInOriginalMetamodel("Person", "unknownProperty"))
			.thenReturn(false);
		
		new IgnoredPropertyChecker("Person", context).check("unknownProperty");
		
		verify(context).addWarning("The unknownProperty property should be ignored, " + 
		                           "but there is no unknownProperty property defined for Person in the original metamodel.");
	}
	
	@Test
	public void ignoringAnKnownPropertyDoesNotProduceAWarning() throws FlockRuntimeException {
		when(context.isPropertyInOriginalMetamodel("Person", "knownProperty"))
			.thenReturn(true);
		
		new IgnoredPropertyChecker("Person", context).check("knownProperty");
		
		verify(context, never()).addWarning(anyString());
	}
}
