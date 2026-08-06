/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.engine.traceability.fine.internal;

import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Region;
import org.eclipse.epsilon.eol.execute.introspection.recording.IPropertyAccess;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccess;

/**
 * A {@link PropertyAccess} with additional information ({@link #getRegion()) that
 * indicates the portion of the output that was generated from this property access. 
 */
class TracedPropertyAccess extends PropertyAccess {
	
	protected Region region;
	
	public TracedPropertyAccess(Object modelElement, String propertyName, Region region) {
		super(modelElement, propertyName);
		this.region = region;
	}
	
	public TracedPropertyAccess(IPropertyAccess propertyAccess, Region region) {
		this(propertyAccess.getModelElement(), propertyAccess.getPropertyName(), region);
	}
	
	public Region getRegion() {
		return region;
	}
	
	public void setRegion(Region region) {
		this.region = region;
	}
}
