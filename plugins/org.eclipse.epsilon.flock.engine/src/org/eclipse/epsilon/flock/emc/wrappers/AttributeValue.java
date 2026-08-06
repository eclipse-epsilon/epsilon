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

import org.eclipse.epsilon.flock.context.ConservativeCopyContext;

class AttributeValue extends BackedModelValue<Object> {

	AttributeValue(Model model, Object underlyingModelObject) {
		super(model, underlyingModelObject);
	}
	
	@Override
	public AttributeValue getEquivalentIn(Model model, ConservativeCopyContext context) {
		return new AttributeValue(model, underlyingModelObject);
	}
}
