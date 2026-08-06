/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.models;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;

public class EmptyModel extends Model {

	@Override
	public void load() throws EolModelLoadingException {
		
	}

	public String getMetaModel() {
		return "";
	}

	@Override
	public Object getEnumerationValue(String enumeration, String label) {
		return null;
	}

	@Override
	public Collection<?> getAllOfType(String metaClass) throws EolModelElementTypeNotFoundException {
		return Collections.emptyList();
	}
 
	@Override
	public Collection<?> getAllOfKind(String metaClass) throws EolModelElementTypeNotFoundException {
		return Collections.emptyList();
	}

	public Collection<?> getAllSupertypes(String metaClass) throws EolModelElementTypeNotFoundException {
		return Collections.emptyList();
	}

	public Collection<?> getAllSubtypes(String metaClass) throws EolModelElementTypeNotFoundException {
		return Collections.emptyList();
	}

	public Collection<?> getAllClasses(boolean onlyConcrete) {
		return Collections.emptyList();
	}

	@Override
	public Object getTypeOf(Object instance) {
		return null;
	}
	
	@Override
	public String getTypeNameOf(Object instance) {
		throw new UnsupportedOperationException("An empty model contains no elements.");
	}
	
	public Collection<String> getPropertiesOf(String type) {		
		return Collections.emptyList();
	}

	@Override
	public Object createInstance(String metaClass) throws EolModelElementTypeNotFoundException, EolNotInstantiableModelElementTypeException {
		return null;
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
		// do nothing
	}

	@Override
	public void deleteElement(Object instance) {
		
	}

	public String getClassOf(Object instance) {
		return null;
	}

	@Override
	public boolean owns(Object instance) {
		return false;
	}

	@Override
	public boolean store(String fileName) {
		return false;
	}

	@Override
	public boolean store() {
		return false;
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public Collection<?> allContents() {
		return Collections.emptyList();
	}

	@Override
	public boolean isInstantiable(String metaClass) {
		return false;
	}

	@Override
	public boolean hasType(String metaClass) {
		return true;
	}

	@Override
	public void load(StringProperties properties, IRelativePathResolver resolver) throws EolModelLoadingException {
		
	}

	@Override
	public boolean isModelElement(Object instance) {
		return false;
	}
	
	/**
	 * @since 1.6
	 */
	public boolean isLoaded() {
		return true;
	}
}
