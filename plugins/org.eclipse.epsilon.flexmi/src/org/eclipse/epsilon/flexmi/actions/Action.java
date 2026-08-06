/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.actions;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.epsilon.flexmi.FlexmiResource;

public abstract class Action {
	
	protected EObject eObject;

	protected int lineNumber;
	protected URI uri;
	
	public abstract void perform(FlexmiResource resource) throws Exception;
	
	public EObject getEObject() {
		return eObject;
	}
	
	public int getLineNumber() {
		return lineNumber;
	}
	
	public URI getUri() {
		return uri;
	}
}
