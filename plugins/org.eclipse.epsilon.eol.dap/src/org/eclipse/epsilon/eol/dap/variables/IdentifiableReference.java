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

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.epsilon.eol.types.EolAnyType;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;
import org.eclipse.epsilon.eol.types.EolType;

public abstract class IdentifiableReference<T> implements IVariableReference {

	private static final EolType[] PREDEFINED_TYPES = {
		EolPrimitiveType.Integer,
		EolPrimitiveType.Boolean,
		EolPrimitiveType.Real,
		EolPrimitiveType.String
	};

	protected int id;
	protected T target;

	public IdentifiableReference(T t) {
		this.target = t;
	}

	public T getTarget() {
		return target;
	}

	public void setTarget(T target) {
		this.target = target;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public String getValue() {
		return target + "";
	}

	@Override
	public String getTypeName() {
		if (target == null) {
			return new EolAnyType().getName();
		}

		for (EolType type : PREDEFINED_TYPES) {
			if (type.isKind(target)) {
				return type.getName();
			}
		}
		
		return target.getClass().getName();
	}

	@Override
	public List<IVariableReference> getVariables(SuspendedState state) {
		return Collections.emptyList();
	}

	@Override
	public int hashCode() {
		return Objects.hash(target);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IdentifiableReference<?> other = (IdentifiableReference<?>) obj;
		return Objects.equals(target, other.target);
	}
	
}
