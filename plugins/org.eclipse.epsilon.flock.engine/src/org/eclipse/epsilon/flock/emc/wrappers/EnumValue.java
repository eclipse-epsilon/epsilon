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

import org.eclipse.epsilon.eol.exceptions.models.EolEnumerationValueNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotAnEnumerationValueException;
import org.eclipse.epsilon.flock.context.ConservativeCopyContext;
import org.eclipse.epsilon.flock.execute.exceptions.ConservativeCopyException;

class EnumValue extends BackedModelValue<Object> {

	EnumValue(Model model, Object underlyingModelObject) {
		super(model, underlyingModelObject);
	}

	@Override
	public ModelValue<?> getEquivalentIn(Model model, ConservativeCopyContext context) throws ConservativeCopyException {
		try {
			return new EnumValue(model, model.getEquivalentEnumerationValue(underlyingModelObject));
			
		} catch (EolEnumerationValueNotFoundException e) {
			context.addWarning("Could not find in migrated metamodel an enumeration value equivalent to: " + underlyingModelObject);
		
		} catch (EolNotAnEnumerationValueException e) {
			context.addWarning("The following value in the original model could not be treated as an enumeration value: " + underlyingModelObject);
		}
		
		return model.wrap(null);
	}
}
