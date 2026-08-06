/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.m3;

import org.eclipse.epsilon.eol.types.EolAnyType;
import org.eclipse.epsilon.eol.types.EolType;

public abstract class StructuralFeature extends NamedElement {
	
	protected boolean many, unique, ordered, changeable, concurrent;
	protected EolType type = EolAnyType.Instance;
	
	public EolType getType() {
		return type;
	}
	
	public boolean isConcurrent() {
		return concurrent;
	}

	public void setConcurrent(boolean concurrent) {
		this.concurrent = concurrent;
	}

	public void setType(EolType type) {
		this.type = type;
	}
	
	public boolean isMany() {
		return many;
	}
	
	public void setMany(boolean many) {
		this.many = many;
	}
	
	public boolean isUnique() {
		return unique;
	}
	
	public void setUnique(boolean unique) {
		this.unique = unique;
	}
	
	public boolean isOrdered() {
		return ordered;
	}
	
	public void setOrdered(boolean ordered) {
		this.ordered = ordered;
	}
	
	public boolean isChangeable() {
		return changeable;
	}
	
	public void setChangeable(boolean changeable) {
		this.changeable = changeable;
	}
}
