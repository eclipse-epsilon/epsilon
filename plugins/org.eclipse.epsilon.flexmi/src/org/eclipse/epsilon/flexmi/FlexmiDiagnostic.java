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
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;

public class FlexmiDiagnostic implements Diagnostic {

	protected String message;
	protected int line;
	protected URI uri;
	
	public FlexmiDiagnostic(String message, URI uri, int line) {
		this.message = message;
		this.line = line;
		this.uri = uri;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getLocation() {
		return uri.toString();
	}

	@Override
	public int getLine() {
		return line;
	}

	@Override
	public int getColumn() {
		return 0;
	}
	
}
