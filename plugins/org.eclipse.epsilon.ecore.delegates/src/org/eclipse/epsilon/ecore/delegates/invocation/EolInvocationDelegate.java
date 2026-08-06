/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Horacio Hoyos Rodriguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecore.delegates.invocation;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.ecore.delegates.EolOperationDelegateContext;
import org.eclipse.epsilon.ecore.delegates.execution.EolOperation;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;

/**
 * An {@link EpsilonInvocationDelegate} that uses an Eol Operation for execution
 * 
 * @since 2.5
 */
public class EolInvocationDelegate implements EpsilonInvocationDelegate {
	
	public EolInvocationDelegate(
		EOperation eOperation, 
		EolOperationDelegateContext delegateContext,
		InvocationUri uri) {
		super();
		this.eOperation = eOperation;
		this.delegateContext = delegateContext;
		this.uri = uri;
	}

	@Override
	public Object dynamicInvoke(InternalEObject target, EList<?> arguments) throws InvocationTargetException {
		if (this.errors.contains(target)) {
			// No need to throw the same exception multiple times.
			return false;
		}
		if (this.program == null) {
			this.program = (EolOperation) this.delegateContext.parse(
					toEolOperation(this.expression()));
		}
		this.program.invokeWith(arguments);
		try {
			return this.program.execute(
					target,
					this.models.computeIfAbsent(this.eOperation.eResource(), r -> new InMemoryEmfModel(r)));
		} catch (Throwable e) {
			this.errors.add(target);
			throw new InvocationTargetException(e);
		}
	}
	
	@Override
	public void reset() {
		this.program = null;
		this.errors.clear();
		this.models.clear();
	}

	private final Map<Resource, InMemoryEmfModel> models = new HashMap<>();
	private final Set<InternalEObject> errors = new HashSet<>();
	private final EOperation eOperation;
	private final EolOperationDelegateContext delegateContext;
	private final InvocationUri uri;
	private EolOperation program;
	
	private String toEolOperation(String body) {
		StringBuilder result = new StringBuilder();
		result.append("operation ");
		result.append(this.eOperation.getName());
		result.append("(");
		result.append(this.eOperation.getEParameters()
				.stream()
				.map(p -> p.getName())
			.collect(Collectors.joining(",")));
		result.append(")");
		result.append("{");
		result.append(body);
		result.append("}");
		return result.toString();
	}

	private String expression() {
		return this.uri.getEannotionValue(this.eOperation, "body");
	}
}
