/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.models.java;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotAnEnumerationValueException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.introspection.IReflectivePropertySetter;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertySetter;
import org.eclipse.epsilon.eol.models.IReflectiveModel;
import org.eclipse.epsilon.eol.models.Model;

public class JavaModel extends Model implements IReflectiveModel {

	protected Collection<Object> objects;
	protected Collection<Class<?>> classes;
	
	public JavaModel(String name, Collection<? extends Object> objects, Collection<? extends Class<?>> classes) {
		this(objects, classes);
		this.name = name;
	}
	
	public JavaModel(Collection<? extends Object> objects, Collection<? extends Class<?>> classes) {
		this.objects = new ArrayList<>(objects);
		this.classes = new ArrayList<>(classes);
	}
	
	// used by tests
	JavaModel(Collection<Object> objects) {
		this(objects, classesFor(objects));
	}
	
	private static Collection<Class<?>> classesFor(Collection<Object> objects) {
		final List<Class<?>> classes = new ArrayList<>(objects.size());
		for (Object object : objects) {
			classes.add(object.getClass());
		}
		return classes;
	}
	
	@Override
	public Collection<Object> allContents() {
		return objects;
	}
	
	public Class<?> classForName(String name) {
		for (Class<?> c : classes) {
			if (c.getSimpleName().equals(name) || c.getCanonicalName().replaceAll("::", ".").equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	@Override
	public Object createInstance(String type) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		Class<?> c = classForName(type);
		if (c != null) {
			if (isInstantiable(c)) {
				try {
					Object newInstance = c.getDeclaredConstructor().newInstance();
					objects.add(newInstance);
					return newInstance;
				} catch (Exception e) {
					throw new EolNotInstantiableModelElementTypeException(this.getName(), type);
				}
			}
			else {
				throw new EolNotInstantiableModelElementTypeException(this.getName(), type);
			}
		}
		throw new EolModelElementTypeNotFoundException(this.getName(), type);
	}
	
	public boolean isInstantiable(Class<?> c) {
		return !c.isInterface();
	}
	
	
	@Override
	public void deleteElement(Object instance) throws EolRuntimeException {
		objects.remove(instance);
	}

	@Override
	public Collection<?> getAllOfKind(String type) throws EolModelElementTypeNotFoundException {
		Class<?> c = classForName(type);
		Collection<Object> allOfKind = new ArrayList<>();
		if (c != null) {
			for (Object o : objects) {
				if (c.isInstance(o)) {
					allOfKind.add(o);
				}
			}
			return allOfKind;
		}
		else {
			throw new EolModelElementTypeNotFoundException(this.getName(), type);
		}
	}

	@Override
	public Collection<?> getAllOfType(String type) throws EolModelElementTypeNotFoundException {
		Class<?> c = classForName(type);
		Collection<Object> allOfType = new ArrayList<>();
		if (c != null) {
			for (Object o : objects) {
				if (o.getClass() == c) {
					allOfType.add(o);
				}
			}
			return allOfType;
		}
		else {
			throw new EolModelElementTypeNotFoundException(this.getName(), type);
		}
	}

	@Override
	public boolean isOfKind(Object instance, String metaClass) throws EolModelElementTypeNotFoundException {
		Class<?> c = classForName(metaClass);
		return c.isAssignableFrom(instance.getClass());
	}

	@Override
	public boolean isOfType(Object instance, String metaClass) throws EolModelElementTypeNotFoundException {
		Class<?> c = classForName(metaClass);
		return instance.getClass() == c;
	}

	@Override
	public Object getElementById(String id) {
		int hashCode = 0;
		try {
			hashCode = Integer.parseInt(id);
			for (Object o : objects) {
				if (o.hashCode() == hashCode) {
					return o;
				}
			}
			return null;
		}
		catch (Exception ex) {
			return null;
		}
	}
	
	@Override
	public String getElementId(Object instance) {
		return instance.hashCode() + "";
	}
	
	@Override
	public void setElementId(Object instance, String newId) {
		// do nothing
	}

	
	@Override
	public Object getEnumerationValue(String enumeration, String label)
			throws EolEnumerationValueNotFoundException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Object getTypeOf(Object instance) {
		return instance.getClass();
	}

	@Override
	public String getTypeNameOf(Object instance) {
		return instance.getClass().getCanonicalName();
	}
	
	@Override
	public boolean hasType(String type) {
		return classForName(type) != null;
	}
	
	@Override
	public boolean hasPackage(String packageName) {
		return false;
	}
	
	@Override
	public Object getContainerOf(Object object) {
		return null;
	}

	@Override
	public boolean isInstantiable(String type) {
		return isInstantiable(classForName(type));
	}

	@Override
	public Collection<String> getPropertiesOf(String type) throws EolModelElementTypeNotFoundException {
		final Class<?> clazz = classForName(type);
		
		if (clazz == null)
			throw new EolModelElementTypeNotFoundException(this.name, type);
		
		
		final Collection<String> properties = new LinkedList<>();
				
		for (Method method : clazz.getMethods()) {
			if (method.getName().startsWith("set")) {
				properties.add(StringUtil.firstToLower(method.getName().substring(3)));
			
			} else if (method.getName().startsWith("is")) {
				properties.add(StringUtil.firstToLower(method.getName().substring(2)));
			
			} else if (method.getName().startsWith("get") && method.getReturnType().isAssignableFrom(Collection.class)) {
				properties.add(StringUtil.firstToLower(method.getName().substring(3)));
			}
		}
		
		return properties;
	}
	
	@Override
	public boolean hasProperty(String type, String property) throws EolModelElementTypeNotFoundException {
		return getPropertiesOf(type).contains(property);
	}
	
	@Override
	public IReflectivePropertySetter getPropertySetter() {
		return new JavaPropertySetter();
	}
	
	@Override
	public boolean isModelElement(Object instance) {
		return true;
	}
	
	@Override
	public boolean isEnumerationValue(Object object) {
		return false;
	}

	@Override
	public String getEnumerationTypeOf(Object literal) throws EolNotAnEnumerationValueException {
		throw new EolNotAnEnumerationValueException(literal);
	}

	@Override
	public String getEnumerationLabelOf(Object literal) throws EolNotAnEnumerationValueException {
		throw new EolNotAnEnumerationValueException(literal);
	}

	@Override
	public boolean preventLoadingOfExternalModelElements() {
		return false;
	}
	
	@Override
	public void load() throws EolModelLoadingException {
		
	}
	
	@Override
	public boolean owns(Object instance) {
		return objects.contains(instance);
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
	public void dispose() {
		objects.clear();
		classes.clear();
		objects = null;
		classes = null;
	}
	
	/**
	 * @since 1.6
	 */
	public boolean isLoaded() {
		return objects != null;
	}
}
