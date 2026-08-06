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

import java.util.Collection;
import java.util.LinkedList;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;

class ModelType {

	private final Model model;
	private final String name, unqualifiedName;
	
	ModelType(Model model, String name, String unqualifiedName) {
		this.model = model;
		this.name  = name;
		this.unqualifiedName = unqualifiedName;
	}
	
	String getName() {
		return name;
	}
	
	String getUnqualifiedName() {
		return unqualifiedName;
	}
	
	Collection<String> getProperties() throws EolModelElementTypeNotFoundException {
		return model.getPropertiesOf(name);
	}
		
	Collection<String> getPropertiesSharedWith(ModelType other) throws EolModelElementTypeNotFoundException {
		final Collection<String> sharedProperties = new LinkedList<>(getProperties());
		
		sharedProperties.retainAll(other.getProperties());
		
		return sharedProperties;
	}
}
