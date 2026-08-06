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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

public class UnresolvedReference {
	
	protected EObject eObject;
	protected EReference eReference;
	protected String value;
	protected int line;
	protected String attributeName;
	protected URI uri;
	
	public UnresolvedReference(EObject eObject, URI uri, EReference eReference, String attributeName, String value, int line) {
		super();
		this.eObject = eObject;
		this.uri = uri;
		this.eReference = eReference;
		this.value = value;
		this.line = line;
		this.attributeName = attributeName;
	}

	public EObject getEObject() {
		return eObject;
	}
	
	public void setEObject(EObject eObject) {
		this.eObject = eObject;
	}
	
	public EReference getEReference() {
		return eReference;
	}
	
	public void seteReference(EReference eReference) {
		this.eReference = eReference;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public int getLine() {
		return line;
	}
	
	public void setLine(int line) {
		this.line = line;
	}
	
	public String getAttributeName() {
		return attributeName;
	}
	
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	public URI getUri() {
		return uri;
	}
	
	public void setUri(URI uri) {
		this.uri = uri;
	}
	
	public boolean resolve(EObject candidate) {
		if (eReference.getEReferenceType().isInstance(candidate)) {
			new EReferenceSlot(eReference, getEObject()).newValue(candidate);
			return true;
		}
		return false;
	}
	
}
