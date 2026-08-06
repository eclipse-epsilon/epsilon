/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.introspection.java;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertySetter;
import org.eclipse.epsilon.eol.execute.introspection.IReflectivePropertySetter;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributorRegistry;

public class JavaPropertySetter extends AbstractPropertySetter implements IReflectivePropertySetter {
	
	protected ObjectMethod getMethodFor(Object object, String property, Object value, IEolContext context) {
		OperationContributorRegistry registry = context.getOperationContributorRegistry();
		
		// Look for a setX() method
		ObjectMethod om = registry.findContributedMethodForEvaluatedParameters(object, "set" + property, new Object[]{value}, context);
		if (om != null) return om;
		
		return new ObjectMethod(object);
	}
	
	@Override
	public void invoke(Object target, String property, Object value, IEolContext context) throws EolRuntimeException {
		try (ObjectMethod objectMethod = getMethodFor(target, property, value, context)) {
			ModuleElement ast = context.getExecutorFactory().getActiveModuleElement();
			if (objectMethod.method == null) {
				throw new EolIllegalPropertyException(target, property, ast, context);
			}
			objectMethod.execute(ast, context, value);
		}
	}

	@Override
	public Object coerce(Object target, String property, Object value, IEolContext context) throws EolIllegalPropertyException {
		return value;
	}

	@Override
	public boolean conforms(Object target, String property, Object value, IEolContext context) throws EolIllegalPropertyException {
		// TODO implement this method
		throw new UnsupportedOperationException("Not yet implemented.");
	}
}
