/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.introspection.recording;

import java.util.Objects;

public class PropertyAccess implements IPropertyAccess {
	
	protected Object modelElement;
	protected String propertyName;
	
	public PropertyAccess(Object modelElement, String propertyName) {
		this.modelElement = modelElement;
		this.propertyName = propertyName;
	}

	@Override
	public Object getModelElement() {
		return this.modelElement;
	}

	public void setModelElement(Object modelElement) {
		this.modelElement = modelElement;
	}
	
	@Override
	public String getPropertyName() {
		return this.propertyName;
	}
	
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (!(object instanceof PropertyAccess)) return false;
		final PropertyAccess other = (PropertyAccess)object;
		return
			Objects.equals(this.modelElement, other.modelElement) &&
			Objects.equals(this.propertyName, other.propertyName);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(modelElement, propertyName);
	}
	
	@Override
	public String toString() {
		return "PropertyAccess of '" + propertyName + "' on model element: '" + modelElement + "'";
	}
}
