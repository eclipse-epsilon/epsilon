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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

public class EAttributeSlot {
	
	protected EAttribute eAttribute;
	protected EObject eObject;
	
	public EAttribute getEAttribute() {
		return eAttribute;
	}
	
	public void setEAttribute(EAttribute eAttribute) {
		this.eAttribute = eAttribute;
	}
	
	public EObject getEObject() {
		return eObject;
	}
	
	public void setEObject(EObject eObject) {
		this.eObject = eObject;
	}
	
	public void newValue(String value) {
		
	}
	
}
