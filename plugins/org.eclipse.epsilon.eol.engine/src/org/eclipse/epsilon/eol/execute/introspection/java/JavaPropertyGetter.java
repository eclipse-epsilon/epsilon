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
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertyGetter;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributorRegistry;
import org.eclipse.epsilon.eol.types.EolNativeType;

public class JavaPropertyGetter extends AbstractPropertyGetter {
	
	@Override
	public boolean hasProperty(Object object, String property, IEolContext context) {
		try (ObjectMethod om = getMethodFor(object, property, context)) {
			return om.getMethod() != null;
		}
	}
	
	@SuppressWarnings("unchecked")
	protected ObjectMethod getMethodFor(Object object, String property, IEolContext context) {
		OperationContributorRegistry registry = context.getOperationContributorRegistry();
		
		// Look for a getX() method
		ObjectMethod om = registry.findContributedMethodForEvaluatedParameters(object, "get" + property, new Object[]{}, context);
		if (om != null) return om;

		// Look for an X() method
		om = registry.findContributedMethodForEvaluatedParameters(object, property, new Object[]{}, context);
		if (om != null) return om;
	
		// Look for an isX() method
		om = registry.findContributedMethodForEvaluatedParameters(object, "is" + property, new Object[]{}, context);
		if (om != null) return om;
		
		if (object instanceof EolNativeType) {
			Class<?> clazz = ((EolNativeType) object).getJavaClass();
			if (clazz.isEnum()) {	
				return new EnumObjectMethod((Class<? extends Enum<?>>) clazz, property);
			}
		}
		
		return new ObjectMethod(object);
	}
	
	@Override
	public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
		try (ObjectMethod objectMethod = getMethodFor(object, property, context)) {
			ModuleElement ast = context.getExecutorFactory().getActiveModuleElement();
			if (objectMethod.method == null) {
				throw new EolIllegalPropertyException(object, property, ast, context);
			}
			return objectMethod.execute(ast, context);
		}
	}
}
