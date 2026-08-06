/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.csv;

import java.util.Map;
import java.util.Objects;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertySetter;

public class CsvPropertySetter extends AbstractPropertySetter {

	@SuppressWarnings("unchecked")
	@Override
	public void invoke(Object object, String property, Object value, IEolContext context) throws EolRuntimeException {
		if (!(object instanceof Map)) {
			throw new EolIllegalPropertyException(object, property, context);
		}
		Map<String, String> row = (Map<String, String>) object;
		if (!row.keySet().contains(property)) {
			throw new EolIllegalPropertyException(object, property, context);
		}
		row.put(property, Objects.toString(value));
	}
}
