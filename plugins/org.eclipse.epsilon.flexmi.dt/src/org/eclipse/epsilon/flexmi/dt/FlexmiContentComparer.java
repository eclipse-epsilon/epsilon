/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.dt;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.IElementComparer;

public class FlexmiContentComparer implements IElementComparer {
	
	@Override
	public int hashCode(Object element) {
		if (element instanceof ResourceSet) return 1;
		else if (element instanceof Resource) return ((Resource) element).getURI().hashCode();
		else if (element instanceof EObject) {
			EObject eObject = (EObject) element;
			return (eObject.eResource().getURIFragment(eObject) + eObject.eResource().getURI()).hashCode();
		}
		else return element.hashCode();
	}
	
	@Override
	public boolean equals(Object a, Object b) {
		return hashCode(a) == hashCode(b);
	}
	
}
