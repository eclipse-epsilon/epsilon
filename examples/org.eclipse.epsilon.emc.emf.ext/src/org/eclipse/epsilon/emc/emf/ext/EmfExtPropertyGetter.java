/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.emf.ext;

import org.eclipse.epsilon.emc.emf.EmfPropertyGetter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class EmfExtPropertyGetter extends EmfPropertyGetter {
	
	@Override
	public boolean hasProperty(Object object, String property, IEolContext context) {
		return property.endsWith("_") || super.hasProperty(object, property, context);
	}
	
	@Override
	public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
		if (property.endsWith("_")) {
			return "hello world";
		}
		else {
			return super.invoke(object, property, context);
		}
	}
	
}
