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

import java.util.ArrayList;
import java.util.Collection;

class ModelValueWrapper {

	private final Model model;
	
	ModelValueWrapper(Model model) {
		this.model = model;
	}
	
	ModelValue<?> wrapValue(Object value) {
		if (value instanceof BackedModelValue<?>) {
			return (BackedModelValue<?>)value;
		}
		else if (model.isEnumeration(value) && !model.owns(value)) {
			return new EnumValue(model, value);
		}
		else if (model.isModelElement(value)) {
			return wrapModelElement(value);
		}
		else if (value instanceof Collection) {
			return new CollectionOfModelValues(model, wrapValues((Collection<?>)value));
		}
		else {
			return new AttributeValue(model, value);
		}
	}
	
	ModelElement wrapModelElement(Object object) {
		return ModelElement.create(model, object);
	}
	
	private Collection<ModelValue<?>> wrapValues(Collection<?> underlyingModelObjects) {
		final Collection<ModelValue<?>> modelValues = new ArrayList<>(underlyingModelObjects.size());
		for (Object underlyingModelObject : underlyingModelObjects) {
			modelValues.add(model.wrap(underlyingModelObject));
		}
		return modelValues;
	}
}
