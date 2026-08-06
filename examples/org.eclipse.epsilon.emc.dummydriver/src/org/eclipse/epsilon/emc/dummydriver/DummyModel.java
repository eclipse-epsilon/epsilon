/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.dummydriver;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertySetter;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;
import org.eclipse.epsilon.eol.models.Model;

public class DummyModel extends Model {
	
	protected ArrayList<DummyModelElement> contents = new ArrayList<>();
	
	public static void main(String[] args) throws Exception {
		
		DummyModel dummyModel = new DummyModel();
		dummyModel.setName("Dummy");
		dummyModel.load();
		
		EolModule module = new EolModule();
		module.parse("A.all.println(); var a = new A; a.type='bar'; a.type.println(); A.all.println();");
		module.getContext().getModelRepository().addModel(dummyModel);
		module.execute();
		
	}
	
	@Override
	public void load() throws EolModelLoadingException {
		contents.add(new DummyModelElement("A").put("a1", "v1"));
		contents.add(new DummyModelElement("B").put("b1", "v2"));
	}

	@Override
	public Object getEnumerationValue(String enumeration, String label)
			throws EolEnumerationValueNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<?> allContents() {
		return contents;
	}

	@Override
	public Collection<?> getAllOfType(String type) throws EolModelElementTypeNotFoundException {
		
		ArrayList<DummyModelElement> result = new ArrayList<>();
		
		for (DummyModelElement e : contents) {
			if (e.getType().equals(type)) {
				result.add(e);
			}
		}
		
		return result;
	}

	@Override
	public Collection<?> getAllOfKind(String type)
			throws EolModelElementTypeNotFoundException {
		return getAllOfType(type);
	}

	@Override
	public Object getTypeOf(Object instance) {
		return instance.getClass();
	}

	@Override
	public String getTypeNameOf(Object instance) {
		return ((DummyModelElement) instance).getType();
	}

	@Override
	public Object createInstance(String type)
			throws EolModelElementTypeNotFoundException,
			EolNotInstantiableModelElementTypeException {
		
		DummyModelElement newElement = new DummyModelElement(type);
		contents.add(newElement);
		return newElement;
	}

	@Override
	public Object getElementById(String id) {
		return null;
	}

	@Override
	public String getElementId(Object instance) {
		return null;
	}

	@Override
	public void setElementId(Object instance, String newId) {
		
	}

	@Override
	public void deleteElement(Object instance) throws EolRuntimeException {
		contents.remove(instance);
	}

	@Override
	public boolean owns(Object instance) {
		return contents.contains(instance);
	}

	@Override
	public boolean isInstantiable(String type) {
		return true;
	}

	@Override
	public boolean isModelElement(Object instance) {
		return true;
	}

	@Override
	public boolean hasType(String type) {
		for (DummyModelElement e : contents) {
			if (type.equals(e.getType())) return true;
		}
		return false;
	}

	@Override
	public boolean store(String location) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean store() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public IPropertyGetter getPropertyGetter() {
		return new DummyElementPropertyGetter();
	}
	
	@Override
	public IPropertySetter getPropertySetter() {
		return new DummyElementPropertySetter();
	}
	
	class DummyElementPropertyGetter extends AbstractPropertyGetter {

		@Override
		public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
			return ((DummyModelElement) object).getProps().get(property);
		}
		
	}
	
	class DummyElementPropertySetter extends AbstractPropertySetter {

		@Override
		public void invoke(Object target, String property, Object value, IEolContext context) throws EolRuntimeException {
			((DummyModelElement) target).getProps().put(property, value);
		}
		
	}

}
