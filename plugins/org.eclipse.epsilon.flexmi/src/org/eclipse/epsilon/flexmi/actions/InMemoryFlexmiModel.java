/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.actions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.epsilon.emc.emf.EmfPropertyGetter;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.flexmi.FlexmiResource;

public class InMemoryFlexmiModel extends InMemoryEmfModel {
	
	public InMemoryFlexmiModel(FlexmiResource resource) {
		super(resource);
		propertyGetter = new FlexmiModelPropertyGetter();
	}
	
	@Override
	public boolean owns(Object instance) {
		return super.owns(instance);
	}
	
	@Override
	public boolean knowsAboutProperty(Object instance, String property) {
		return (owns(instance) && propertyGetter.hasProperty(instance, property, null)) || super.knowsAboutProperty(instance, property);
	}
	
	@Override
	public FlexmiResource getResource() {
		return (FlexmiResource) super.getResource();
	}
	
	class FlexmiModelPropertyGetter extends EmfPropertyGetter {
		
		@Override
		public boolean hasProperty(Object object, String property, IEolContext context) {
			if (property.startsWith("^")) return getChild(object, property.substring(1)) != null;
			return getChild(object, property) != null || super.hasProperty(object, property, context);
		}
		
		@Override
		public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
			if (property.startsWith("^")) return getChild(object, property.substring(1));
			
			if (super.hasProperty(object, property, context)) {
				return super.invoke(object, property, context);
			}
			else {
				return getChild(object, property);
			}
		}
		
		protected EObject getChild(Object container, String id) {
			for (EObject content : ((EObject) container).eContents()) {
				if (id.equals(getResource().getLocalId(content))) {
					return content;
				}
			}
			return null;
		}
	}
	
}
