/*******************************************************************************
 * Copyright (c) 2012-2020 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - Thread safety
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.contributors;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.ObjectMethod;
import org.eclipse.epsilon.eol.util.ReflectionUtil;

/**
 * Implementation note: All extending classes
 * should ensure that the operation contributor is
 * stateless, or if it contains state (i.e. fields)
 * then they are either immutable or ThreadLocal.
 */
public abstract class OperationContributor implements AutoCloseable {
	
	private final ThreadLocal<Object> target = new ThreadLocal<>();
	private final ThreadLocal<IEolContext> context = new ThreadLocal<>();
	protected Set<String> cachedMethodNames;
	
	public abstract boolean contributesTo(Object target);
	
	public ObjectMethod findContributedMethodForUnevaluatedParameters(Object target, String name, List<Expression> parameterExpressions, IEolContext context) {
		// Note that the last parameter is false: we only want to retrieve methods that take an AST as an argument
		// and not methods that take a supertype of AST (such as Object)
		return createObjectMethodFor(target, name, new Object[]{new AST()}, context, false);
	}

	public ObjectMethod findContributedMethodForEvaluatedParameters(Object target, String name, Object[] parameters, IEolContext context) {
		return findContributedMethodForEvaluatedParameters(target, name, parameters, context, true);
	}
	
	public ObjectMethod findContributedMethodForEvaluatedParameters(Object target, String name, Object[] parameters, IEolContext context, boolean overrideContextOperationContributorRegistry) {
		return createObjectMethodFor(target, name, parameters, context, !overrideContextOperationContributorRegistry);
	}

	private ObjectMethod createObjectMethodFor(Object target, String name, Object[] parameters, IEolContext context, boolean allowContravariantConversionForParameters) {	
		Method method = null;
		// Maintain a cache of method names if the reflection target is this
		// so that we don't iterate through all methods every time
		if (getReflectionTarget(target) == this && cachedMethodNames == null) synchronized (this) {
			if (cachedMethodNames == null) {
				cachedMethodNames = ReflectionUtil.getMethodNames(this, includeInheritedMethods());
			}
		}
		
		if (cachedMethodNames == null || cachedMethodNames.contains(name)) {
			method = ReflectionUtil.getMethodFor(getReflectionTarget(target),
		                                   name,
		                                   parameters,
		                                   includeInheritedMethods(),
		                                   allowContravariantConversionForParameters);
		}
		
		if (method != null) {
			Object reflectionTarget = getReflectionTarget(target);
			ObjectMethod objectMethod = new ObjectMethod(reflectionTarget, method);

			/*
			 * If the reflection target is this contributor, then it will need to know about
			 * the actual operand for the method and the intended context.
			 */
			if (reflectionTarget == this) {
				setTarget(target);
				setContext(context);
			}
			return objectMethod;
		}
		else return null;
	}
	
	/**
	 * Specifies whether methods in the supertype of the contributor
	 * should be included when finding contributed operations. Typically,
	 * this should not be the case and, as such, this method returns 
	 * false by default.
	 */
	protected boolean includeInheritedMethods() {
		return false;
	}
	
	protected Object getReflectionTarget(Object target) {
		return this;
	}
	
	/**
	 * 
	 * @return The {@link #target} field.
	 * @since 1.6
	 */
	protected Object getTarget() {
		return target.get();
	}
	
	public void setTarget(Object target) {
		this.target.set(target);
	}
	
	public void setContext(IEolContext context) {
		this.context.set(context);
	}

	/**
	 * 
	 * @return
	 * @since 1.6
	 */
	protected IEolContext getContext() {
		return context.get();
	}
	
	/**
	 * Clears the target and context.
	 * @since 2.2
	 */
	@Override
	public void close() {
		target.remove();
		context.remove();
	}
}
