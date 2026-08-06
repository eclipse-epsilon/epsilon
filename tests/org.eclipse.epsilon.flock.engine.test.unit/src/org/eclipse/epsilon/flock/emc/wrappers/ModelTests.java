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
import java.util.Collection;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.IReflectivePropertySetter;
import org.eclipse.epsilon.eol.models.IReflectiveModel;
import org.eclipse.epsilon.hutn.test.model.families.DogBreed;
import org.junit.Test;

public class ModelTests {

	@SuppressWarnings("rawtypes")
	@Test
	public void setPropertyValueShouldDelegateToPropertySetterAndUnwrapValue() throws EolRuntimeException {
		final IReflectiveModel          mockUnderlyingModel = mock(IReflectiveModel.class);
		final IReflectivePropertySetter mockPropertySetter  = mock(IReflectivePropertySetter.class);
		final BackedModelValue          mockWrappedValue    = mock(BackedModelValue.class);
		
		when(mockWrappedValue.unwrap())
			.thenReturn("bar");
		
		String property = "foo";
		Object target = "dummy model element";
		
		when(mockPropertySetter.coerce(target, property, "bar", null)).thenReturn("bar");
		mockPropertySetter.invoke(target, property, "bar", null);
		
		when(mockUnderlyingModel.getPropertySetter())
			.thenReturn(mockPropertySetter);

		new Model(mockUnderlyingModel).setValueOfProperty("dummy model element", "foo", mockWrappedValue);
		
		verify(mockUnderlyingModel).getPropertySetter();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void getPropertyValueShouldDelegateToPropertyGetterAndWrapValue() throws EolRuntimeException {
		final IReflectiveModel  mockUnderlyingModel = mock(IReflectiveModel.class);
		final IPropertyGetter   mockPropertyGetter  = mock(IPropertyGetter.class);
		final ModelValueWrapper mockWrapper         = mock(ModelValueWrapper.class);
		final Model model = new Model(mockUnderlyingModel, mockWrapper);
		
		when(mockUnderlyingModel.getPropertyGetter())
			.thenReturn(mockPropertyGetter);
		
		when(mockPropertyGetter.invoke("dummy model element", "foo", null))
			.thenReturn("bar");
		
		when(mockWrapper.wrapValue("bar"))
			.thenReturn((BackedModelValue)new AttributeValue(model, "bar"));
		
		assertEquals(new AttributeValue(model, "bar"), model.getValueOfProperty("dummy model element", "foo"));
	}
	
	@Test
	public void createInstanceDelegatesToUnderlyingModelAndWrapsValue() throws EolRuntimeException {
		final IReflectiveModel  mockUnderlyingModel = mock(IReflectiveModel.class);
		final ModelValueWrapper mockWrapper         = mock(ModelValueWrapper.class);
		final Model model = new Model(mockUnderlyingModel, mockWrapper);
		
		when(mockUnderlyingModel.createInstance("DummyType"))
			.thenReturn("foo");
		
		when(mockWrapper.wrapModelElement("foo"))
			.thenReturn(new ModelElement(model, new ModelType(model, "DummyType", "DummyUnqualifiedType"), "foo"));

		assertEquals(new ModelElement(model, new ModelType(model, "DummyType", "DummyUnqualifiedType"), "foo"), model.createInstance("DummyType"));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void allContentsDelegatesToUnderlyingModelAndWrapsValues() throws EolRuntimeException {
		final IReflectiveModel  mockUnderlyingModel = mock(IReflectiveModel.class);
		final ModelValueWrapper mockWrapper         = mock(ModelValueWrapper.class);
		
		final Model model = new Model(mockUnderlyingModel, mockWrapper);
		
		final ModelType dummyType = new ModelType(model, "DummyType", "DummyUnqualifiedType");
		
		// Expectations
		
		when(mockUnderlyingModel.allContents())
			.thenReturn((Collection)Arrays.asList("foo", "bar", "baz"));
		
		when(mockUnderlyingModel.owns("foo")).thenReturn(true);
		when(mockUnderlyingModel.owns("bar")).thenReturn(false);
		when(mockUnderlyingModel.owns("baz")).thenReturn(true);
		
		when(mockWrapper.wrapModelElement("foo"))
			.thenReturn(new ModelElement(model, dummyType, "foo"));
		
		when(mockWrapper.wrapModelElement("baz"))
			.thenReturn(new ModelElement(model, dummyType, "baz"));

		assertEquals(
			Arrays.asList(
				new ModelElement(model, dummyType, "foo"),
				new ModelElement(model, dummyType, "baz")
			),
			model.directContents()
		);
	}
	
	
	@Test
	public void getEquivalentForEnumeratorDelegatesToUnderlyingModel() throws EolRuntimeException {
		final IReflectiveModel  mockUnderlyingModel = mock(IReflectiveModel.class);
		
		final Model model = new Model(mockUnderlyingModel);
		
		when(mockUnderlyingModel.getEnumerationTypeOf(DogBreed.LABRADOR))
			.thenReturn("DogBreed");
		
		when(mockUnderlyingModel.getEnumerationLabelOf(DogBreed.LABRADOR))
			.thenReturn("labrador");
		
		when(mockUnderlyingModel.getEnumerationValue("DogBreed", "labrador"))
			.thenReturn(DogBreed.LABRADOR);
		
		assertEquals(DogBreed.LABRADOR, model.getEquivalentEnumerationValue(DogBreed.LABRADOR));
	}
	
	@Test
	public void getIdentityDelegatesToUnderlyingModel() throws EolEnumerationValueNotFoundException {
		final IReflectiveModel  mockUnderlyingModel = mock(IReflectiveModel.class);
		
		final Model model = new Model(mockUnderlyingModel);
		
		when(mockUnderlyingModel.getElementId("dummyModelElement"))
			.thenReturn("anIdentity");

		assertEquals("anIdentity", model.getIdentity("dummyModelElement"));
	}
	
	@Test
	public void setIdentityDelegatesToUnderlyingModel() throws EolEnumerationValueNotFoundException {
		final IReflectiveModel  mockUnderlyingModel = mock(IReflectiveModel.class);
		final Model model = new Model(mockUnderlyingModel);
		mockUnderlyingModel.setElementId("dummyModelElement", "anIdentity");
		model.setIdentity("dummyModelElement", "anIdentity");
	}
}
