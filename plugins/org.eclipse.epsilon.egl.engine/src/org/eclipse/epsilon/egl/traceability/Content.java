/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.traceability;

public abstract class Content<P> {

	private P parent;
	
	public Content(P parent) {
		this.parent = parent;
	}
	
	public P getParent() {
		return parent;
	}
	
	protected void setParent(P parent) {
		this.parent = parent;
	}
}
