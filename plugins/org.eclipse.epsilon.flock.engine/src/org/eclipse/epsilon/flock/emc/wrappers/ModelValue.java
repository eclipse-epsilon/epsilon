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

import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.flock.context.ConservativeCopyContext;
import org.eclipse.epsilon.flock.execute.exceptions.ConservativeCopyException;

public abstract class ModelValue<UnwrappedType> {

	public Variable createReadOnlyVariable(String name) {
		return Variable.createReadOnlyVariable(name, unwrap());
	}

	public abstract UnwrappedType unwrap();
	
	public abstract ModelValue<?> getEquivalentIn(Model model, ConservativeCopyContext context) throws ConservativeCopyException;
	
	public Object getUnwrappedEquivalentIn(Model model, ConservativeCopyContext context) throws ConservativeCopyException {
		return getEquivalentIn(model, context).unwrap();
	}
	
	@Override
	public abstract boolean equals(Object o);

	@Override
	public abstract int hashCode();

	@Override
	public abstract String toString();
}
