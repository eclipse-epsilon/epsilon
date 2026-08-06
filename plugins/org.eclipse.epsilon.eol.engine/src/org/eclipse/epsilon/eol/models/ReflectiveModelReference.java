/*******************************************************************************
 * Copyright (c) 2011 The University of York.
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
package org.eclipse.epsilon.eol.models;

import java.util.Collection;

import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotAnEnumerationValueException;
import org.eclipse.epsilon.eol.execute.introspection.IReflectivePropertySetter;

public class ReflectiveModelReference extends ModelReference implements IReflectiveModel {
	
	public ReflectiveModelReference(IReflectiveModel target) {
		super(target);
	}
	
	@Override
	public boolean preventLoadingOfExternalModelElements() {
		return getTypeSafeTarget().preventLoadingOfExternalModelElements();
	}
	
	@Override
	public IReflectivePropertySetter getPropertySetter() {
		return getTypeSafeTarget().getPropertySetter();
	}
	
	@Override
	public Collection<String> getPropertiesOf(String type) throws EolModelElementTypeNotFoundException {
		return getTypeSafeTarget().getPropertiesOf(type);
	}

	@Override
	public boolean hasProperty(String type, String property) throws EolModelElementTypeNotFoundException {
		return getTypeSafeTarget().hasProperty(type, property);
	}
	
	@Override
	public boolean hasPackage(String packageName) {
		return getTypeSafeTarget().hasPackage(packageName);
	}

	@Override
	public boolean isEnumerationValue(Object object) {
		return getTypeSafeTarget().isEnumerationValue(object);
	}

	@Override
	public String getEnumerationTypeOf(Object literal) throws EolNotAnEnumerationValueException {
		return getTypeSafeTarget().getEnumerationTypeOf(literal);
	}

	@Override
	public String getEnumerationLabelOf(Object literal) throws EolNotAnEnumerationValueException {
		return getTypeSafeTarget().getEnumerationLabelOf(literal);
	}

	@Override
	public Object getContainerOf(Object object) {
		return getTypeSafeTarget().getContainerOf(object);
	}
	

	private IReflectiveModel getTypeSafeTarget() {
		return (IReflectiveModel)target;
	}
}
