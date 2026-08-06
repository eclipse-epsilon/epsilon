/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.execute.introspection;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.types.EolTuple;

/**
 * 
 * @author Sina Madani
 * @since 2.2
 */
public class TuplePropertySetter extends AbstractPropertySetter {
	
	@Override
	public void invoke(Object object, String property, Object value, IEolContext context) throws EolRuntimeException {
		if (!(object instanceof EolTuple)) {
			throw new IllegalArgumentException("Cannot set tuple property "+property+" on "+object);
		}
		((EolTuple) object).put(property, value);
	}
}
