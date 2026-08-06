/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.contributors;

import java.util.function.Supplier;

public class BooleanOperationContributor extends OperationContributor {

	@Override
	public boolean contributesTo(Object target) {
		return target instanceof Boolean;
	}
	
	@Override
	protected Boolean getTarget() {
		return (Boolean) super.getTarget();
	}
	
	public boolean or(boolean operand) {
		return operand || getTarget();
	}
	
	public boolean and(boolean operand) {
		return operand && getTarget();
	}
	
	public boolean not() {
		return !getTarget();
	}
	
	public boolean xor(boolean operand) {
		return getTarget() != operand;		
	}
	
	public String asString() {
		return getTarget() + "";
	}
	
	/**
	 * Lazy functional-style ternary operation.
	 * 
	 * @param sIfTrue Value getter if the target is <code>true</code>
	 * @param sIfFalse Value getter if the target is <code>false</code>
	 * @return The value given by the Supplier parameters based on whether the target is true or false.
	 * @since 1.6
	 */
	public Object ternary(Supplier<?> sIfTrue, Supplier<?> sIfFalse) {
		boolean value = getTarget();
		return value ? sIfTrue.get() : sIfFalse.get();
	}
}
