/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.introspection.java;

import org.eclipse.epsilon.emc.simulink.model.element.ISimulinkModelElement;
import org.eclipse.epsilon.emc.simulink.types.Struct;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertyGetter;

public class SimulinkPropertyGetter extends JavaPropertyGetter {

	@Override
	public boolean hasProperty(Object object, String property, IEolContext context) {
		if (object instanceof Struct) {
			return ((Struct) object).containsKey(property);
		}
		return super.hasProperty(object, property, context);
	}

	@Override
	public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
		try {
			return super.invoke(object, property, context);
		}
		catch (Exception e) {
			if (object instanceof ISimulinkModelElement) {
				ISimulinkModelElement element = (ISimulinkModelElement) object;
				if (property.equalsIgnoreCase("type")) {
					return element.getType();
				}
				return element.getProperty(property);
			}
			if (object instanceof Struct) {
				return ((Struct)object).get(property);
			}
			throw new EolRuntimeException(e.getMessage());
		}
	}
}
