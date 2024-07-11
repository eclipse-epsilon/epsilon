/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.variables;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertyGetter;
import org.eclipse.epsilon.eol.util.ReflectionUtil;

public class JavaObjectReference extends IdentifiableReference<Object> {

	private static final Logger LOGGER = Logger.getLogger(JavaObjectReference.class.getCanonicalName());
	
	/**
	 * We have to limit ourselves to the getter method prefixes used by
	 * {@link JavaPropertyGetter}, as calling arbitrary methods might have side
	 * effects. Fetching arbitrary fields from an object likely will not work
	 * reliably in Java 9+ programs, either.
	 */
	private final String[] METHOD_PREFIXES = { "get", "is" };

	private final String name;

	public JavaObjectReference(IEolContext context, String name, Object target) {
		super(context, target);
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getTypeName() {
		return String.format("Native('%s')", target.getClass().getName());
	}

	@Override
	public List<IVariableReference> getVariables(SuspendedState state) {
		List<IVariableReference> refs = new ArrayList<>();

		Set<String> methodNames = ReflectionUtil.getMethodNames(target, true);
		for (String methodName : methodNames) {
			String propertyName = null;
			for (String prefix : METHOD_PREFIXES) {
				if (methodName.startsWith(prefix)) {
					final String firstLetter = methodName.substring(prefix.length(), prefix.length() + 1);
					propertyName = firstLetter.toLowerCase() + methodName.substring(prefix.length() + 1);
					break;
				}
			}
			if (propertyName == null) {
				// Not a getter method
				continue;
			}

			Method method = ReflectionUtil.getMethodFor(target, methodName, new Object[0], true, false);
			if (method != null) {
				try {
					Object value = method.invoke(target);
					refs.add(state.getValueReference(context, propertyName, value));
				} catch (Exception ex) {
					LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
				}
			}
		}
		
		return refs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(name);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JavaObjectReference other = (JavaObjectReference) obj;
		return Objects.equals(name, other.name);
	}

}
