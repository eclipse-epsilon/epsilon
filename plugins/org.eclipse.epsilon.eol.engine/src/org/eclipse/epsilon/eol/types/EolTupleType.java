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

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.Variable;

/**
 * 
 * @author Sina Madani
 * @since 2.2
 */
public class EolTupleType extends EolType {
	
	@Override
	public String getName() {
		return "Tuple";
	}

	@Override
	public boolean isType(Object o) {
		return o != null && o.getClass() == EolTuple.class;
	}

	@Override
	public boolean isKind(Object o) {
		return o instanceof EolTuple;
	}

	@Override
	public EolTuple createInstance() throws EolRuntimeException {
		return createInstance(null);
	}

	@Override
	public EolTuple createInstance(List<Object> parameters) throws EolRuntimeException {
		EolTuple tuple = new EolTuple();
		if (parameters != null) for (Object param : parameters) {
			String key = null; Object value = null;
			if (param instanceof Variable) {
				Variable variable = (Variable) param;
				key = variable.getName();
				value = variable.getValue();
			}
			else if (param instanceof Map.Entry) {
				Map.Entry<?, ?> entry = (Entry<?, ?>) param;
				key = entry.getKey()+"";
				value = entry.getValue();
			}
			if (key != null) {
				tuple.put(key, value);
			}
		}
		return tuple;
	}

}
