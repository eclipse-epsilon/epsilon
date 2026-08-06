/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.dom;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolNotInstantiableModelElementTypeException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;
import org.eclipse.epsilon.eol.types.EolCollectionType;
import org.eclipse.epsilon.eol.types.EolMapType;
import org.eclipse.epsilon.eol.types.EolModelElementType;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;
import org.eclipse.epsilon.eol.types.EolTupleType;
import org.eclipse.epsilon.eol.types.EolType;

public abstract class TypeInitialiser extends Expression {
	
	protected Object initialiseType(EolType type, List<Expression> parameters, IEolContext context, boolean createIfNonPrimitive) throws EolRuntimeException {
		
		if (type instanceof EolPrimitiveType || type instanceof EolCollectionType || type instanceof EolMapType) {
			return type.createInstance();
		}
		else if (createIfNonPrimitive) {
			
			if (type instanceof EolModelElementType && !((EolModelElementType) type).isInstantiable()) {
				EolModelElementType modelElementType = (EolModelElementType) type;
				throw new EolNotInstantiableModelElementTypeException(modelElementType);
			}
			
			ExecutorFactory executorFactory = context.getExecutorFactory();
			
			final ArrayList<Object> parameterValues = new ArrayList<>();
			for (Expression parameter : parameters) {
				if (parameter.getClass() != EqualsOperatorExpression.class) {
					parameterValues.add(executorFactory.execute(parameter, context));
				}
			}
			
			Object instance = null;
			
			instance = parameterValues.isEmpty() ? type.createInstance() : type.createInstance(parameterValues);
			
			IPropertySetter setter = null;
			LinkedHashMap<String, Object> properties = new LinkedHashMap<String, Object>();
			
			for (Expression parameter : parameters) {
				if (parameter.getClass() == EqualsOperatorExpression.class) {
					EqualsOperatorExpression equalsOperatorExpression = (EqualsOperatorExpression) parameter;
					if (equalsOperatorExpression.getFirstOperand() instanceof NameExpression) {
						String property = ((NameExpression) equalsOperatorExpression.getFirstOperand()).getName();
						if (setter == null) setter = context.getIntrospectionManager().getPropertySetterFor(instance, property, context);
						
						if (setter != null) {
							Object value = executorFactory.execute(equalsOperatorExpression.getSecondOperand(), context);
							properties.put(property, value);
						}
						else throw new EolIllegalPropertyException(instance, property, equalsOperatorExpression.getFirstOperand(), context);
					}
					else {
						throw new EolRuntimeException("Property name expected", equalsOperatorExpression.getFirstOperand());
					}
				}
			}
			
			if (setter != null) {
				try {
					setter.invoke(instance, properties, context);
				}
				catch (EolRuntimeException eox) {
					if (eox.getAst() == null) {
						eox.setAst(this);
					}
					throw eox;
				}
			}
			
			return instance;
		}
		else if (type instanceof EolTupleType) {
			return type.createInstance();
		}
		return null;
	}
	
	public abstract void accept(IEolVisitor visitor);
	
}
