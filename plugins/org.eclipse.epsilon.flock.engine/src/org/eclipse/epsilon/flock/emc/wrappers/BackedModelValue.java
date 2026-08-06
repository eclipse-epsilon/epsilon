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

/**
 * An implementation of ModelValue that is backed by an underlying
 * Java object, which represents a model value.
 */
public abstract class BackedModelValue<UnwrappedType> extends ModelValue<UnwrappedType> {

	protected final Model model;
	protected final UnwrappedType underlyingModelObject;
	
	protected BackedModelValue(Model model, UnwrappedType underlyingModelObject) {
		this.model                 = model;
		this.underlyingModelObject = underlyingModelObject;
	}
	
	@Override
	public UnwrappedType unwrap() {
		return underlyingModelObject;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BackedModelValue<?>))
			return false;
		
		final BackedModelValue<?> other = (BackedModelValue<?>)o;
		
		return model.equals(other.model) &&
		       underlyingModelObject == null ? 
		       	other.underlyingModelObject == null : 
		       	underlyingModelObject.equals(other.underlyingModelObject);
	}
	
	@Override
	public int hashCode() {
		return model.hashCode() + 
		       (underlyingModelObject == null ? 0 : underlyingModelObject.hashCode());
	}
	
	@Override
	public String toString() {
		return "<" + underlyingModelObject + ">";
	}
}
