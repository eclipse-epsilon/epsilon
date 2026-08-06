/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.xmi.util.hutn;

import static org.junit.Assert.assertEquals;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage;
import org.eclipse.epsilon.hutn.xmi.util.HutnUtil;
import org.junit.Test;

public class DetermineTypeFromMetaFeature {

	private static void determineFeatureTest(String featureName, EStructuralFeature expectedFeature) {
		determineFeatureTest(featureName, expectedFeature, FamiliesPackage.eINSTANCE.getFamily());
	}

	private static void determineFeatureTest(String featureName, EStructuralFeature expectedFeature, EClass metaClass) {
		final EStructuralFeature feature = HutnUtil.determineFeatureFromMetaClass(new ClassObjectStub(metaClass), featureName);
		
		assertEquals(expectedFeature, feature);
	}
	
	@Test
	public void determineFeature() {
		determineFeatureTest("name", FamiliesPackage.eINSTANCE.getNamedElement_Name());
	}
	
	@Test
	public void determineSecondFeature() {
		determineFeatureTest("averageAge", FamiliesPackage.eINSTANCE.getFamily_AverageAge());
	}
	
	@Test
	public void determineFeatureFromDifferentMetaClass() {
		determineFeatureTest("breed", FamiliesPackage.eINSTANCE.getDog_Breed(), FamiliesPackage.eINSTANCE.getDog());
	}
	
	@Test
	public void determineUndefinedFeature() {
		determineFeatureTest("foo", null);
	}
}
