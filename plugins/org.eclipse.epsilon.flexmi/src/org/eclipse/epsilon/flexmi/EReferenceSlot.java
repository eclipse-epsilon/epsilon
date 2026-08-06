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

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

public class EReferenceSlot {
	
	protected EReference eReference;
	protected EObject eObject;
	
	public EReferenceSlot(EReference eReference, EObject eObject) {
		super();
		this.eReference = eReference;
		this.eObject = eObject;
	}
	
	public EReference getEReference() {
		return eReference;
	}
	
	public void setEReference(EReference eReference) {
		this.eReference = eReference;
	}
	
	public EObject getEObject() {
		return eObject;
	}
	
	public void getEObject(EObject container) {
		this.eObject = container;
	}
	
	@SuppressWarnings("unchecked")
	public void newValue(EObject value) {
		if (eReference.isMany()) ((List<Object>)eObject.eGet(eReference)).add(value);
		else eObject.eSet(eReference, value);
	}
	
}
