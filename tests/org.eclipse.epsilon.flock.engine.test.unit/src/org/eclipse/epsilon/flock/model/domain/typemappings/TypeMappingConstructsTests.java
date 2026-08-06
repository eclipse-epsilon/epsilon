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
package org.eclipse.epsilon.flock.model.domain.typemappings;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.flock.context.TypeMappingContext;
import org.eclipse.epsilon.flock.equivalences.factory.EquivalenceFactory;
import org.junit.Before;
import org.junit.Test;

public class TypeMappingConstructsTests {

	private final TypeMappingContext context = mock(TypeMappingContext.class);
	
	private final TypeMappingConstruct applicable        = mock(TypeMappingConstruct.class);
	private final TypeMappingConstruct anotherApplicable = mock(TypeMappingConstruct.class);
	private final TypeMappingConstruct inapplicable      = mock(TypeMappingConstruct.class);
	
	@Before
	public void setup() throws EolRuntimeException {
		when(context.isEligibleFor(applicable)).thenReturn(true);
		when(context.isEligibleFor(anotherApplicable)).thenReturn(true);
		
		when(context.isEligibleFor(inapplicable)).thenReturn(false);
	}
	
	@Test
	public void shouldUseApplicableTypeMappingConstruct() throws EolRuntimeException {
		final TypeMappingConstructs typeMappingConstructs = new TypeMappingConstructs(applicable);
		
		typeMappingConstructs.createEquivalence(context);
		
		verify(context).createEquivalenceUsing(applicable);
	}
	
	@Test
	public void shouldUseFirstApplicableTypeMappingConstruct() throws EolRuntimeException {
		final TypeMappingConstructs typeMappingConstructs = new TypeMappingConstructs(inapplicable, applicable);
				
		typeMappingConstructs.createEquivalence(context);
		
		verify(context, never()).createEquivalenceUsing(inapplicable);
		verify(context).createEquivalenceUsing(applicable);
	}
	
	@Test
	public void shouldUseOnlyTheFirstApplicableTypeMappingConstruct() throws EolRuntimeException {
		final TypeMappingConstructs typeMappingConstructs = new TypeMappingConstructs(applicable, anotherApplicable);
		
		typeMappingConstructs.createEquivalence(context);
		
		verify(context).createEquivalenceUsing(applicable);
		verify(context, never()).createEquivalenceUsing(anotherApplicable);
	}
	
	@Test
	public void shouldUseDefaultFactoryWhenNoTypeMappingsAreApplicable() throws EolRuntimeException {
		final EquivalenceFactory equivalenceFactory = mock(EquivalenceFactory.class);	
		final TypeMappingConstructs typeMappingConstructs = new TypeMappingConstructs(equivalenceFactory);		
		
		typeMappingConstructs.createEquivalence(context);
		
		verify(context).createEquivalenceUsing(equivalenceFactory);
	}
}
