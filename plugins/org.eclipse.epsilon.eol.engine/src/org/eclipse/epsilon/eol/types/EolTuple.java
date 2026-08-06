/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.types;

import java.util.Collection;
import java.util.Map;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;

/**
 * 
 * @author Sina Madani
 * @since 2.2
 */
public class EolTuple extends EolMap<String, Object> {
	
	public EolTuple() {
		// Empty
	}
	
	public EolTuple(Map<String, ?> properties) {
		if (properties != null) {
			putAll(properties);
		}
	}
	
	public EolTuple(Collection<? extends Variable> variables) {
		if (variables != null) for (Variable v : variables) {
			put(v.getName(), v.getValue());
		}
	}
	
	public boolean hasProperty(String property) {
		return containsKey(property);
	}
	
	public Object getOrThrow(String property, IEolContext context) throws EolIllegalPropertyException {
		if (!containsKey(property)) {
			throw new EolIllegalPropertyException(this, property, context);
		}
		return get(property);
	}
	
	public Object put(Variable variable) {
		if (variable != null) {
			return put(variable.getName(), variable.getValue());
		}
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof EolTuple)) return false;
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return "Tuple {"+wrapped+"}";
	}
}
