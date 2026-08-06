/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.evl.execute;

import java.util.*;
import org.eclipse.epsilon.evl.dom.Constraint;

public class UnsatisfiedConstraint {
	
	protected Constraint constraint;
	protected String message;
	protected Object instance;
	protected final List<FixInstance> fixes = new LinkedList<>();
	protected boolean fixed = false;
	protected Map<String, Object> extras = new HashMap<>();
	
	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}

	public Constraint getConstraint() {
		return constraint;
	}

	public void setConstraint(Constraint constraint) {
		this.constraint = constraint;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getInstance() {
		return instance;
	}

	public void setInstance(Object result) {
		this.instance = result;
	}
	
	public List<FixInstance> getFixes() {
		return fixes;
	}
	
	public Map<String, Object> getExtras() {
		return extras;
	}
	
	public void setExtras(Map<String, Object> extras) {
		this.extras = extras;
	}
	
	@Override
	public String toString() {
		return getMessage();
	}

	/**
	 * @since 1.6
	 */
	@Override
	public int hashCode() {
		return Objects.hash(message, instance, constraint, fixed);
	}

	/**
	 * @since 1.6
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!(other instanceof UnsatisfiedConstraint))
			return false;
		
		UnsatisfiedConstraint uc = (UnsatisfiedConstraint) other;
		return
				Objects.equals(this.message, uc.message) &&
				Objects.equals(this.instance, uc.instance) &&
				Objects.equals(this.constraint, uc.constraint) &&
				Objects.equals(this.fixed, uc.fixed);
	}
}
