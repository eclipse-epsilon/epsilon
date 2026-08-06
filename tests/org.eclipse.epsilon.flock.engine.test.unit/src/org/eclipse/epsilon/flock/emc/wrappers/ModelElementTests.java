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
package org.eclipse.epsilon.flock.emc.wrappers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.flock.context.ConservativeCopyContext;
import org.eclipse.epsilon.flock.execute.exceptions.ConservativeCopyException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@SuppressWarnings({ "unchecked", "rawtypes" })
@RunWith(Suite.class)
@Suite.SuiteClasses({ModelElementTests.GetEquivalentTests.class,
                     ModelElementTests.ConservativeCopyTests.class})
public class ModelElementTests {

	private static final Object       underlyingElement = "foo";
	private static final Model        migratedModel     = mock(Model.class, "mockModel");
	private static final ModelType    mockModelType     = mock(ModelType.class);
	private static final ModelElement element           = new ModelElement(migratedModel, mockModelType, underlyingElement);
	
	private static final ConservativeCopyContext context = mock(ConservativeCopyContext.class);
	
	
	public static class GetEquivalentTests {
		@Test
		public void getEquivalentShouldDelegateToContextWhenModelElementIsContainedInModel() throws ConservativeCopyException {
			final Model                   mockMigratedModel = mock(Model.class, "mockMigratedModel");
			final ConservativeCopyContext mockContext       = mock(ConservativeCopyContext.class);
			
			final ModelElement dummyMigratedModelElement = new ModelElement(mockMigratedModel, mockModelType, "bar");

			when(migratedModel.owns(underlyingElement))
				.thenReturn(true);
			
			when(mockContext.getEquivalent(element))
				.thenReturn(dummyMigratedModelElement);
			
			when(mockMigratedModel.wrap(dummyMigratedModelElement))
				.thenReturn((ModelValue)dummyMigratedModelElement);		
			

			assertEquals(dummyMigratedModelElement,
			             element.getEquivalentIn(mockMigratedModel, mockContext));
			
			
			verify(mockContext).getEquivalent(element);
		}
		
		@Test
		public void getEquivalentShouldReturnTheModelElementWhenItIsNotContainedInTheModel() throws ConservativeCopyException {
			final Model                   dummyMigratedModel = mock(Model.class, "mockMigratedModel");
			final ConservativeCopyContext dummyContext       = mock(ConservativeCopyContext.class);
			
			when(migratedModel.owns(underlyingElement))
				.thenReturn(false);
					
			
			assertEquals(element,
			             element.getEquivalentIn(dummyMigratedModel, dummyContext));
			
			verify(dummyContext, never()).getEquivalent(element);
		}
	}
	
	public static class ConservativeCopyTests {
		@Test
		public void copyConformantValue() throws ConservativeCopyException, EolRuntimeException {				
			final ModelValue conformantValue = mock(ModelValue.class);
			
			when(migratedModel.conforms(underlyingElement, "name", conformantValue))
				.thenReturn(true);
	
			
			element.conservativelySetValueForProperty(conformantValue, "name", context);
					
			verify(migratedModel).setValueOfProperty(underlyingElement, "name", conformantValue);
		}
		
		@Test
		public void doNotCopyNonConformantValue() throws ConservativeCopyException, EolRuntimeException {				
			final ModelValue nonConformantValue = mock(ModelValue.class);
			
			when(migratedModel.conforms(underlyingElement, "name", nonConformantValue))
				.thenReturn(false);
	
			
			element.conservativelySetValueForProperty(nonConformantValue, "name", context);
					
			verify(migratedModel, never()).setValueOfProperty(underlyingElement, "name", nonConformantValue);
		}
	}
}
