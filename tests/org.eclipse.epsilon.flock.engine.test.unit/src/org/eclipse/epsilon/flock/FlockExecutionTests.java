/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.eclipse.epsilon.flock.emc.wrappers.Model;
import org.eclipse.epsilon.flock.execute.FlockExecution;
import org.eclipse.epsilon.flock.execute.FlockResult;
import org.eclipse.epsilon.flock.execute.MigrationStrategyRunner;
import org.eclipse.epsilon.flock.execute.exceptions.FlockRuntimeException;
import org.junit.Test;

public class FlockExecutionTests {

	final FlockExecution execution = new FlockExecution(mock(MigrationStrategyRunner.class));
	final Model originalModel = mock(Model.class);
	
	@Test
	public void runShouldEnsureExpandIsOff() throws FlockRuntimeException {
		run();
		
		verify(originalModel).preventLoadingOfExternalModelElements();
	}
	
	@Test
	public void runShouldAddWarningIfExpandWasTurnedOff() throws FlockRuntimeException {
		when(originalModel.preventLoadingOfExternalModelElements())
			.thenReturn(true);
		
		assertEquals(1, run().getWarnings().size());
	}
	
	@Test
	public void runShouldNotAddWarningIfExpandWasNotTurnedOff() throws FlockRuntimeException {
		when(originalModel.preventLoadingOfExternalModelElements())
			.thenReturn(false);
		
		assertEquals(0, run().getWarnings().size());
	}
	
	private FlockResult run() throws FlockRuntimeException {
		return execution.run(originalModel);
	}
}
