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
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;
import org.eclipse.epsilon.hutn.test.model.families.DogBreed;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

public class ModelValueWrapperTests {
	
	@Test
	public void wrapValue() {
		wrapValueTest("a model value", AttributeValue.class);
	}
	
	@Test
	public void wrapModelElement() {		
		wrapValueTest("dummy model element", ModelElement.class, "dummy model element");
	}
	
	@Test
	public void wrapCollectionOfValues() {
		final CollectionOfModelValues wrappedValues = wrapValueTest(
			Arrays.asList("a model value", "dummy model element"),
			CollectionOfModelValues.class,
			"dummy model element"
		);
		
		for (ModelValue<?> wrappedValue : wrappedValues) {
			if ("dummy model element".equals(wrappedValue.unwrap())) {
				assertWrappedValueTypeEquals(ModelElement.class, wrappedValue);
			}
			else {
				assertWrappedValueTypeEquals(AttributeValue.class, wrappedValue);
			}
		}
	}
	
	@Test
	public void wrapEnumerationValue() {
		wrapValueTest(DogBreed.LABRADOR, true, EnumValue.class);
	}
	
	@Test
	public void wrapingAWrappedValueShouldNotReturnADoublyWrappedValue() {
		final ModelValue<?> wrappedValue = wrapValue("a model value");
		
		// Unwrapping a doubly wrapped value is the same as unwrapping a wrapped value
		assertEquals(wrappedValue.unwrap(), wrapValue(wrappedValue).unwrap());
	}	
	

	
	private static <T extends ModelValue<?>> T wrapValueTest(Object unwrappedValue, Class<T> expectedWrappedType, Object... modelElements) {	
		return wrapValueTest(unwrappedValue, false, expectedWrappedType, modelElements);
	}
	
	private static <T extends ModelValue<?>> T wrapValueTest(Object unwrappedValue, boolean isEnumValue, Class<T> expectedWrappedType, Object... modelElements) {	
		final ModelValue<?> wrappedValue = wrapValue(unwrappedValue, isEnumValue, modelElements);
		assertWrappedValueTypeEquals(expectedWrappedType, wrappedValue);
		return expectedWrappedType.cast(wrappedValue);
	}
	
	private static ModelValue<?> wrapValue(Object unwrappedValue, Object... modelElements) {	
		return wrapValue(unwrappedValue, false, modelElements);
	}
	
	private static ModelValue<?> wrapValue(Object unwrappedValue, boolean isEnumValue, Object... modelElements) {	
		final ModelValueWrapper wrapper = new ModelValueWrapper(createModelStubWithModelElements(isEnumValue, modelElements));
		return wrapper.wrapValue(unwrappedValue);
	}

	private static <T extends ModelValue<?>> void assertWrappedValueTypeEquals(Class<T> expectedWrappedType, ModelValue<?> wrappedValue) {
		assertTrue("Expected an instance of <" + expectedWrappedType.getCanonicalName() + "> " +
			"but was " + (wrappedValue == null ? "null" : "an instance of <" + wrappedValue.getClass().getCanonicalName() + ">"),
			expectedWrappedType.isInstance(wrappedValue)
		);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Model createModelStubWithModelElements(boolean isEnumValue, Object... modelElements) {
		final Collection<?> elementsCol = Arrays.asList(modelElements);
		final ArgumentMatcher contains = elementsCol::contains;
		
		final Model modelStub = mock(Model.class);
		
		// Respond as to whether this is an enumeration value
		when(modelStub.isEnumeration(isA(Object.class)))
			.thenReturn(isEnumValue);
		
		// Return true for any object contained in modelElements
		when(modelStub.isModelElement(argThat(contains)))
			.thenReturn(true);
		
		// Return dummy type for type of any object contained in modelElements
		when(modelStub.getTypeNameOf(argThat(contains)))
			.thenReturn("dummy type");
		
		when(modelStub.getUnqualifiedTypeNameOf(argThat(contains)))
			.thenReturn("dummy unqualified type");

		// CollectionOfModelValues will use model to wrap values
		when(modelStub.wrap(argThat(contains)))
			.thenReturn((BackedModelValue) new ModelElement(modelStub,
					new ModelType(modelStub, "dummy type", "dummy unqualified type"), "dummy model element")
			);
		
		when(modelStub.wrap(isA(Object.class)))
			.thenReturn((BackedModelValue)new AttributeValue(modelStub, "dummy wrapped value"));
		
		return modelStub;
	}
}
