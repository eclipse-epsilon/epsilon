/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.json;

import java.util.Map;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;

public class JsonPropertySetter implements IPropertySetter {

	@SuppressWarnings("unchecked")
	@Override
	public void invoke(Object target, String property, Object value, IEolContext context) throws EolRuntimeException {
		if (!(target instanceof Map)) {
			ModuleElement ast = context.getExecutorFactory().getActiveModuleElement();
			throw new EolRuntimeException("Cannot set properties on something that is not a JSON object: "
				+ (context == null ? target : context.getPrettyPrinterManager().toString(target)), ast); 
		}

		Map<String, Object> jsonObject = (Map<String, Object>) target;
		jsonObject.put(property, value);
	}

}
