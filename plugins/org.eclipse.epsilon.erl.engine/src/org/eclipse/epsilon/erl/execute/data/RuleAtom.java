/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.erl.execute.data;

import java.util.Objects;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElementParameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.erl.execute.context.IErlContext;

/**
 * A single rule-based construct and model element combination.
 * This class represents the most atomic (i.e. highest level of granularity) data structure possible.
 * 
 * @author Sina Madani
 * @since 1.6
 */
public abstract class RuleAtom<T extends IExecutableModuleElementParameter> {

	public final T rule;
	public final Object element;
	protected int hashCode;
	
	public RuleAtom(T construct, Object modelElement) {
		this.rule = construct;
		this.element = modelElement;
		this.hashCode = Objects.hash(rule, element);
	}
	
	public Object execute(IErlContext context) throws EolRuntimeException {
		return context.getExecutorFactory().execute(rule, context, element);
	}
	
	public Entry<T, Object> asEntry() {
		return new SimpleEntry<>(rule, element);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+" [unit=" + rule + ", element=" + element + "]";
	}

	@Override
	public int hashCode() {
		return hashCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof RuleAtom)) return false;
		RuleAtom<?> other = (RuleAtom<?>) obj;
		return
			Objects.equals(this.element, other.element) &&
			Objects.equals(this.rule, other.rule);
	}
}
