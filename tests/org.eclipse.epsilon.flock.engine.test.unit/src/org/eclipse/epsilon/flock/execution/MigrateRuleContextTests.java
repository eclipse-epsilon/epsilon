/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.execution;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.flock.context.GuardedConstructContext;
import org.eclipse.epsilon.flock.context.MigrateRuleContext;
import org.eclipse.epsilon.flock.equivalences.Equivalence;
import org.eclipse.epsilon.flock.execute.FlockExecution;
import org.eclipse.epsilon.flock.model.domain.common.ClassifierTypedConstruct;
import org.junit.Test;


public class MigrateRuleContextTests {

	private final Equivalence        equivalence = mock(Equivalence.class);
	private final IEolContext        eolContext  = mock(IEolContext.class);
	private final FlockExecution     execution   = mock(FlockExecution.class);
	private final MigrateRuleContext context     = new MigrateRuleContext(equivalence, eolContext, execution);
	
	private final ClassifierTypedConstruct construct = mock(ClassifierTypedConstruct.class);
	
	
	@Test
	public void firstCallToEvaluateGuardDelegatesToTypedAndGuardedConstruct() throws EolRuntimeException {
		context.isEligibleFor(construct);
		
		verify(construct).appliesIn(any(GuardedConstructContext.class));
	}
	
	@Test
	public void secondCallToEvaluateGuardDoesNotDelegateToTypedAndGuardedConstruct() throws EolRuntimeException {
		context.isEligibleFor(construct);
		context.isEligibleFor(construct);
		
		verify(construct, times(1)).appliesIn(any(GuardedConstructContext.class));
	}
	
	@Test
	public void secondCallToEvaluateGuardReturnsTrueWhenFirstCallReturnedTrue() throws EolRuntimeException {
		when(construct.appliesIn(any(GuardedConstructContext.class))).thenReturn(false);
		
		assertFalse(context.isEligibleFor(construct));
		assertFalse(context.isEligibleFor(construct));
	}
	
	@Test
	public void secondCallToEvaluateGuardReturnsFalseWhenFirstCallReturnedFalse() throws EolRuntimeException {
		when(construct.appliesIn(any(GuardedConstructContext.class))).thenReturn(true);
		
		assertTrue(context.isEligibleFor(construct));
		assertTrue(context.isEligibleFor(construct));
	}
}
