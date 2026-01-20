/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.emf.xmi;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

public class EClassStructuralFeature {

	protected EClass eClass;
	protected EStructuralFeature eStructuralFeature;

	public EClassStructuralFeature(EClass eClass, EStructuralFeature eStructuralFeature) {
		super();
		this.eClass = eClass;
		this.eStructuralFeature = eStructuralFeature;
	}

	public EClass getEClass() {
		return eClass;
	}

	public EStructuralFeature getEStructuralFeature() {
		return eStructuralFeature;
	}

}