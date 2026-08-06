/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.jdt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.models.CachedModel;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class JdtModel extends CachedModel<Object> {

	protected List<String> supportedTypes = Arrays.asList("JavaProject", "Package");
	protected ReflectiveASTVisitor visitor = null;
	protected boolean resolveBindings = false;
	protected List<IJavaProject> projects = new ArrayList<IJavaProject>();
	protected JdtPropertyGetter propertyGetter;
	
	public static final String PROPERTY_PROJECTS = "projects";
	public static final String PROPERTY_RESOLVE_BINDINGS = "resolveBindings";
	
	@Override
	public Object getEnumerationValue(String enumeration, String label)
			throws EolEnumerationValueNotFoundException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTypeNameOf(Object instance) {
		return instance.getClass().getSimpleName();
	}

	@Override
	public Object getElementById(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getElementId(Object instance) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setElementId(Object instance, String newId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean owns(Object instance) {
		// TODO Auto-generated method stub
		if (instance instanceof IJavaElement || instance instanceof ASTNode) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isInstantiable(String type) {
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> getAllOfType(String type)
			throws EolModelElementTypeNotFoundException {
		if (TypeDeclaration.class.getSimpleName().equals(type)) {
			// disable caching for TypeDeclaration - we want to always return a searchable collection
			return (Collection<Object>) getAllOfTypeFromModel(type);
		}
		return super.getAllOfType(type);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Object> getAllOfKind(String type)
			throws EolModelElementTypeNotFoundException {
		if (TypeDeclaration.class.getSimpleName().equals(type)) {
			// disable caching for TypeDeclaration - we want to always return a searchable collection
			return (Collection<Object>) getAllOfKindFromModel(type);
		}
		return super.getAllOfType(type);
	}

	@Override
	public boolean hasType(String type) {
		try {
			return supportedTypes.contains(type)
					|| (Class.forName("org.eclipse.jdt.core.dom." + type) != null);
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	@Override
	public boolean store(String location) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean store() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Collection<Object> allContentsFromModel() {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	protected Collection<Object> getAllOfTypeFromModel(String type)
			throws EolModelElementTypeNotFoundException {
		try {
			if (TypeDeclaration.class.getSimpleName().equals(type)) {
				return new SearchableTypeCollection(projects.toArray(new IJavaProject[]{}), visitor);
			}
			return visitor.getAllOfType(type);
		} catch (JavaModelException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected Collection<Object> getAllOfKindFromModel(String kind)
			throws EolModelElementTypeNotFoundException {
		return getAllOfTypeFromModel(kind);
	}

	@Override
	protected Object createInstanceInModel(String type)
			throws EolModelElementTypeNotFoundException,
			EolNotInstantiableModelElementTypeException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver)
			throws EolModelLoadingException {
		super.load(properties, resolver);
		
		String[] projectNames = properties.getProperty(JdtModel.PROPERTY_PROJECTS, "").split(",");
		try {
			projects =  JdtUtil.getIJavaProjects(JdtUtil.getIProjects(projectNames));
		} catch (CoreException e) {
			throw new EolModelLoadingException(e, this);
		}
		resolveBindings = Boolean.parseBoolean(properties.getProperty(JdtModel.PROPERTY_RESOLVE_BINDINGS));
		visitor = new ReflectiveASTVisitor(projects, resolveBindings);
		
		loadModel();
	}
	
	@Override
	protected void loadModel() throws EolModelLoadingException {}

	@Override
	protected void disposeModel() {
		propertyGetter = null;
	}

	@Override
	protected boolean deleteElementInModel(Object instance)
			throws EolRuntimeException {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Object getCacheKeyForType(String type)
			throws EolModelElementTypeNotFoundException {
		return type;
	}

	@Override
	public boolean isOfKind(Object instance, String metaClass)
			throws EolModelElementTypeNotFoundException {
		return getAllTypeNamesOf(instance).contains(metaClass);
	}
	
	@Override
	public boolean isOfType(Object instance, String metaClass)
			throws EolModelElementTypeNotFoundException {
		return instance.getClass().getSimpleName().equals(metaClass);
	}
	
	@Override
	protected Collection<String> getAllTypeNamesOf(Object instance) {
		
		Class<?> c = instance.getClass();
		ArrayList<String> allTypeNames = new ArrayList<String>();
		while (c != null && c != Object.class) {
			allTypeNames.add(c.getSimpleName());
			c = c.getSuperclass();
		}
		return allTypeNames;
		
	}
	
	@Override
	public IPropertyGetter getPropertyGetter() {
		if (propertyGetter == null) {
			// If the user wants bindings to be resolved, they won't
			// want SimpleNames to be converted to Strings implicitly,
			// since they'll lose the ability to check the bindings
			// of the name and its type.
			propertyGetter = new JdtPropertyGetter(resolveBindings);
		}
		return propertyGetter;
	}

}
