/*******************************************************************************
 * Copyright (c) 2008-2020 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - equals, toString, hashCode
 ******************************************************************************/
package org.eclipse.epsilon.evl.trace;

import java.util.Objects;
import org.eclipse.epsilon.evl.dom.Constraint;

public class ConstraintTraceItem {
	
	protected Object instance;
	protected Constraint constraint;
	protected boolean result;
	
	public ConstraintTraceItem(Object instance, Constraint constraint, boolean result) {
		this.instance = instance;
		this.constraint = constraint;
		this.result = result;
	}

	public Constraint getConstraint() {
		return constraint;
	}

	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}

	public Object getInstance() {
		return instance;
	}

	public void setInstance(Object instance) {
		this.instance = instance;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
	
	/**
	 * Used by {@link ConstraintTrace#isChecked(Constraint, Object)}.
	 * 
	 * @param constraint
	 * @param instance
	 * @return
	 * @since 1.6
	 */
	boolean equals(Constraint constraint, Object instance) {
		return Objects.equals(this.constraint, constraint) &&
			   Objects.equals(this.instance, instance);
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public int hashCode() {
		return Objects.hash(instance, constraint, result);
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof ConstraintTraceItem))
			return false;
		
		ConstraintTraceItem cti = (ConstraintTraceItem) other;
		return  this.result == cti.result &&
				Objects.equals(this.instance, cti.instance) &&
				Objects.equals(this.constraint, cti.constraint);
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public String toString() {
		return
			getClass().getSimpleName()+
			": instance="+Objects.toString(instance)+
			", constraint="+constraint+
			", result="+result;
	}
}
