/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.emf.xml;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;

public class MixedElementOperationContributor extends OperationContributor {
	
	@Override
	public boolean contributesTo(Object target) {
		return target instanceof EObject && 
				((EObject) target).eClass().getEStructuralFeature("mixed") != null;
	}
	
	public void addText(String text) {
		EObject eObject = (EObject) getTarget();
		FeatureMap mixed = (FeatureMap) eObject.eGet(eObject.eClass().getEStructuralFeature("mixed"));
		FeatureMapUtil.addText(mixed, text);
	}
	
	public void addText(String text, int index) {
		EObject eObject = (EObject) getTarget();
		FeatureMap mixed = (FeatureMap) eObject.eGet(eObject.eClass().getEStructuralFeature("mixed"));
		FeatureMapUtil.addText(mixed, index, text);
	}
}
