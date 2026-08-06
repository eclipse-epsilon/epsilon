/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.context;

import java.util.Map.Entry;
import java.util.Objects;
import org.eclipse.epsilon.eol.exceptions.EolIllegalVariableAssignmentException;
import org.eclipse.epsilon.eol.exceptions.EolReadOnlyVariableException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.types.EolAnyType;
import org.eclipse.epsilon.eol.types.EolType;

public class Variable {
	
	protected String name = "";
	protected Object value;
	protected EolType type;
	protected boolean readOnly = false;
	protected String deprecationInfo;
	
	public static Variable createReadOnlyVariable(String name, Object value) {
		return new Variable(name, value, EolAnyType.Instance, true);
	}

	/**
	 * 
	 * @param entry
	 * @return
	 * @since 1.6
	 */
	public static Variable createReadOnlyVariable(Entry<String, ?> entry) {
		return createReadOnlyVariable(entry.getKey(), entry.getValue());
	}

	public Variable() {
		
	}
	
	/**
	 * 
	 * @param entry
	 * @since 1.6
	 */
	public Variable(Entry<String, ?> entry) {
		this(entry.getKey(), entry.getValue(), EolAnyType.Instance, false);
	}
	
	public Variable(String name, Object value, EolType type, boolean readOnly) {
		this.name = name;
		this.type = type;
		this.value = value;
		this.readOnly = readOnly;
	}
	
	public void dispose() {
		this.type = null;
		this.value = null;
	}
	
	public Variable(String name, EolType type) {
		this.name = name;
		this.type = type;
	}
	
	public Variable(String name, Object value, EolType type) {
		this.name = name;
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Creates a shallow copy of the given Variable.
	 * @param v The variable to shallow copy from
	 * @since 1.6
	 */
	public Variable(Variable v) {
		this(v.name, v.value, v.type, v.readOnly);
		this.deprecationInfo = v.deprecationInfo;
	}

	public Object getValue() {
		return value;
	}
	
	public void setValue(Object newValue, IEolContext context) throws EolRuntimeException {
		if (this.isReadOnly())
			throw new EolReadOnlyVariableException(this);
		
		if (newValue != null && !this.getType().isKind(newValue))
			throw new EolIllegalVariableAssignmentException(this, this.getType(), newValue, context);
		
		this.value = newValue;
	}
	
	public void setValueBruteForce(Object newValue) {
		this.value = newValue;
	}
	
	public void setType(EolType type) {
		this.type = type;
	}
	
	public EolType getType() {
		return type;
	}
	
	public boolean isReadOnly() {
		return readOnly;
	}
	
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDeprecationInfo() {
		return deprecationInfo;
	}

	public void setDeprecationInfo(String deprecationInfo) {
		this.deprecationInfo = deprecationInfo;
	}
	
	@Override
	public Variable clone() {
		return new Variable(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Variable))
			return false;
		
		final Variable other = (Variable) obj;
		
		return Objects.equals(name,     other.name)  &&
			   Objects.equals(value,    other.value) &&
			   Objects.equals(type,     other.type)  &&
			   Objects.equals(readOnly, other.readOnly);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}
	
	@Override
	public String toString() {
		return value + " " + type;
	}
}
