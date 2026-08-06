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

public abstract class EolPrimitive extends EolAny implements Comparable<Object> {
	
	@Override
	public boolean equals(Object opposite) {
		if (this == opposite) return true;
		if (opposite == null) return false;
		if (opposite.getClass() == this.getClass()) {
			return this.toString().equals(opposite.toString());
		}
		return false;
	}
	
	@Override
	public int compareTo(Object opposite) {
		if (opposite != null && opposite.getClass() == this.getClass()) {
			return this.toString().compareTo(opposite.toString());
		}
		return -1;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
