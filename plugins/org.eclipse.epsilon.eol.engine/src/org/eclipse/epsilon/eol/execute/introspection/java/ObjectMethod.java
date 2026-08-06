/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.introspection.java;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.stream.BaseStream;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.concurrent.EolContextParallel;
import org.eclipse.epsilon.eol.execute.context.concurrent.IEolContextParallel;
import org.eclipse.epsilon.eol.execute.operations.contributors.IterableOperationContributor;
import org.eclipse.epsilon.eol.util.ReflectionUtil;

public class ObjectMethod extends DisposableObject {

	protected Method method;
	
	public ObjectMethod() {}
	
	public ObjectMethod(Object object) {
		this.object = object;
	}
	
	public ObjectMethod(Object object, Method method) {
		this.object = object;
		this.method = method;
	}
	
	public Object getObject() {
		return object;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}
	
	public Method getMethod() {
		return method;
	}
	
	public void setMethod(Method method) {
		this.method = method;
	}
	
	/**
	 * Convenience method for invoking {@link #execute(Object[], ModuleElement)}
	 * without needing to wrap parameters into an array.
	 * 
	 * @param ast
	 * @param parameters
	 * @return
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	public final Object execute(ModuleElement ast, Object... parameters) throws EolRuntimeException {
		return execute(parameters, ast);
	}
	
	public Object execute(Object[] parameters, ModuleElement ast) throws EolRuntimeException {
		if (method.isVarArgs()) {
			int varargIndex = method.getParameterCount() - 1;
			Object[] adjustedParams = new Object[method.getParameterCount()];
			Class<?> varargType = method.getParameterTypes()[varargIndex].getComponentType();
			for (int i = 0; i < varargIndex; i++) {
				adjustedParams[i] = parameters[i];
			}
			int numberOfVarargs = parameters.length - varargIndex;
			if (numberOfVarargs < 0) numberOfVarargs = 0;
			Object varargParams = Array.newInstance(varargType, numberOfVarargs);
			for (int i = 0; i < numberOfVarargs; i++) {
				Array.set(varargParams, i, parameters[varargIndex + i]);
			}
			adjustedParams[adjustedParams.length-1] = varargParams;
			parameters = adjustedParams;
		}
		return ReflectionUtil.executeMethod(object, method, ast, parameters);
	}
	
	/**
	 * Special handling (pre/post-processing) of method invocations.
	 * 
	 * @param ast
	 * @param context
	 * @param parameters
	 * @return
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	public Object execute(ModuleElement ast, IEolContext context, Object... parameters) throws EolRuntimeException {
		boolean isParallelOperation = object instanceof BaseStream && ((BaseStream<?,?>) object).isParallel();
		
		if (isParallelOperation && !BaseStream.class.isAssignableFrom(method.getReturnType())) {
			// At some point in the chain, StringUtil.isOneOf(operationName, "parallel", "parallelStream") must've been true
			IEolContextParallel pContext = (IEolContextParallel) (context = EolContextParallel.convertToParallel(context));
			if (pContext.isParallelisationLegal()) {
				pContext.beginParallelTask(ast, true);
			}
			else {
				((BaseStream<?,?>) object).sequential();
			}
		}
		
		Object result = execute(parameters, ast);
		
		if (isParallelOperation && context instanceof IEolContextParallel) {
			IEolContextParallel pContext = (IEolContextParallel) context;
			pContext.endParallelTask();
		}
		
		return result;
	}
}
