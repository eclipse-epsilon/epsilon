/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.types;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public abstract class EolType {
	
	public abstract String getName();
	
	public abstract boolean isType(Object o);
	
	public abstract boolean isKind(Object o);
	
	public abstract Object createInstance() throws EolRuntimeException;
	
	public abstract Object createInstance(List<Object> parameters) throws EolRuntimeException;
	
	public boolean isNot(EolType type) {
		return this != type && this != EolAnyType.Instance;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getName());
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null) return false;
		
		if (this.getClass() != other.getClass()) return false;
		
		EolType eolType = (EolType) other;
		
		return Objects.equals(this.getName(), eolType.getName());
	}
	
	public List<EolType> getParentTypes() {
		EolType parentType = getParentType();
		if (parentType == null) return Collections.emptyList();
		else return Arrays.asList(parentType);
	}
	
	protected EolType getParentType() {
		return EolAnyType.Instance;
	}
}
