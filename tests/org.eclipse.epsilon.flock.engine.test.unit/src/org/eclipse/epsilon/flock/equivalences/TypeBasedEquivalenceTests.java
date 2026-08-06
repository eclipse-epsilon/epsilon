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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.flock.context.ConservativeCopyContext;
import org.eclipse.epsilon.flock.emc.wrappers.ModelElement;
import org.eclipse.epsilon.flock.emc.wrappers.ModelValue;
import org.eclipse.epsilon.flock.equivalences.TypeBasedEquivalence.ConservativeCopy;
import org.eclipse.epsilon.flock.execute.FlockExecution;
import org.eclipse.epsilon.flock.execute.exceptions.ConservativeCopyException;
import org.eclipse.epsilon.flock.execute.exceptions.FlockRuntimeException;
import org.eclipse.epsilon.flock.model.domain.rules.IgnoredProperties;
import org.junit.Test;
import org.mockito.ArgumentMatchers;

public class TypeBasedEquivalenceTests {

	private final IEolContext             eolContext   = mock(IEolContext.class);
	private final FlockExecution          execution    = mock(FlockExecution.class);
	private final ModelElement            original     = mock(ModelElement.class);
	private final ModelElement            equivalent   = mock(ModelElement.class);
	private final ConservativeCopyContext context      = mock(ConservativeCopyContext.class);
	
	private final Equivalence equivalence = new TypeBasedEquivalence(eolContext, execution, original, equivalent);
	
	@Test
	public void automaticallyPopulateEquivalentShouldPreserveIdentity() throws FlockRuntimeException {		
		equivalence.automaticallyPopulateEquivalent(context, new IgnoredProperties());
		
		verify(equivalent).copyIdentityFrom(original);
	}
	
	@Test
	public void conservativeCopyShouldCopySharedProperties() throws EolRuntimeException {		
		when(original.getPropertiesSharedWith(equivalent))
			.thenReturn(Arrays.asList("name", "age"));
		
		conservativelyCopyAllPropertiesExcept();
		
		verify(equivalent).conservativelySetValueForProperty(ArgumentMatchers.isNull(), eq("name"), eq(context));
		verify(equivalent).conservativelySetValueForProperty(ArgumentMatchers.isNull(), eq("age"), eq(context));
		verify(equivalent, never()).conservativelySetValueForProperty(any(ModelValue.class), eq("sex"), eq(context));
	}
	
	@Test
	public void conservativeCopyShouldNotCopyIgnoredProperties() throws EolRuntimeException {		
		when(original.getPropertiesSharedWith(equivalent))
			.thenReturn(Arrays.asList("name", "age"));
	
		conservativelyCopyAllPropertiesExcept("name");
		
		verify(equivalent, never()).conservativelySetValueForProperty(any(ModelValue.class), eq("name"), eq(context));
		verify(equivalent).conservativelySetValueForProperty(ArgumentMatchers.isNull(), eq("age"), eq(context));
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void conservativeCopyShouldDelegateToContextToFindEquivalentValue() throws EolRuntimeException {				
		final ModelValue<?> originalValue = mock(ModelValue.class);
		final ModelValue<?> equivalentValue = mock(ModelValue.class);
		
		when(original.getValueOfProperty("name"))
			.thenReturn((ModelValue)originalValue);
		
		when(context.getEquivalentValue(originalValue))
			.thenReturn((ModelValue)equivalentValue);
		
		new ConservativeCopy(context).copyProperty("name", original, equivalent);
		
		verify(context).getEquivalentValue(originalValue);
		verify(equivalent).conservativelySetValueForProperty(equivalentValue, "name", context);
	}
	
	private void conservativelyCopyAllPropertiesExcept(String... ignoredProperties) throws ConservativeCopyException {
		new ConservativeCopy(context, new IgnoredProperties(ignoredProperties)).copyProperties(original, equivalent);
	}
}
