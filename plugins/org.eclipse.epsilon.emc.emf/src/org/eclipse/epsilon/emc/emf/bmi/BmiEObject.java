/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf.bmi;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;

public class BmiEObject extends DynamicEObjectImpl {

	public BmiEObject() {
		super();
	}

	public BmiEObject(EClass eClass) {
		super(eClass);
	}

	@Override
	public Object eGet(EStructuralFeature feature) {
		return super.eGet(feature);
	}
	
	
	
}
