/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute;

public class Return {
	
	protected Object value;
	
	public Return(Object value) {
		this.value = value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}
	
	public static Object getValue(Object result) {
		if (result instanceof Return) {
			return ((Return) result).getValue();
		}
		else {
			return result;
		}
	}
	
}
