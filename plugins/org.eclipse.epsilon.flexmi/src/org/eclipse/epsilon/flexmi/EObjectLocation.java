/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi;

import org.eclipse.emf.common.util.URI;

public class EObjectLocation {
	
	protected URI uri;
	protected int line;
	
	public EObjectLocation(URI uri, int line) {
		super();
		this.uri = uri;
		this.line = line;
	}

	public URI getUri() {
		return uri;
	}
	
	public int getLine() {
		return line;
	}
	
	@Override
	public int hashCode() {
		return getUri().toString().hashCode() + line;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj.hashCode() == this.hashCode();
	}
	
}
