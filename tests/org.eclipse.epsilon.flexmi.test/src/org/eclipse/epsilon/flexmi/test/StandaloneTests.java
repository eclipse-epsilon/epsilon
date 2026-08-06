/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.flexmi.FlexmiResource;
import org.junit.Test;

public class StandaloneTests extends FlexmiTests {
	
	@Test
	public void testNoWarnings() throws Exception {
		assertNoWarnings("standalone/valid-model.flexmi");
	}
	
	@Test
	public void testUnresolvedReferenceWarning() throws Exception {
		assertEquals(1, loadResource("standalone/unresolved-reference.flexmi").getWarnings().size());
	}

	@Test
	public void testUnresolvedReferenceYAMLWarning() throws Exception {
		assertEquals(1, loadResource("standalone/unresolved-reference.yaml").getWarnings().size());
	}
	
	@Test
	public void testAbstractNotBoolean() throws Exception {
		assertEquals(loadResource("standalone/abstract-not-boolean.flexmi").getWarnings().size(), 1);
	}
	
	@Test
	public void testEContents() throws Exception {
		assertEval("EPackage.all.first.eContents().size()", 4, "standalone/valid-model.flexmi");
	}
	
	@Test
	public void testESuperTypes() throws Exception {
		assertEval("EClass.all.selectOne(c|c.name='c4').eSuperTypes.size()", 2, "standalone/valid-model.flexmi");
	}
	
	@Test
	public void testESuperType() throws Exception {
		assertEval("EClass.all.first().eSuperTypes.first().name", "c2", "standalone/valid-model.flexmi");
	}
	
	@Test
	public void testNameAsElement() throws Exception {
		assertEval("EPackage.all.first().name", "p1", "standalone/name-as-element.flexmi");
	}

	@Test
	public void testMixedFeature() throws Exception {
		FlexmiResource res = loadResource("standalone/mixed-features.flexmi");
		EClass cls = (EClass) res.getContents().get(0);
		checkForMixedFeatures(cls);
	}

	@Test
	public void testMixedFeatureYAML() throws Exception {
		FlexmiResource res = loadResource("standalone/mixed-features.yaml");
		EClass cls = (EClass) res.getContents().get(0);
		checkForMixedFeatures(cls);
	}

	protected void checkForMixedFeatures(EClass cls) {
		EList<EStructuralFeature> features = cls.getEStructuralFeatures();

		assertEquals("r1", features.get(0).getName());
		assertTrue("First feature is an EReference",
			features.get(0) instanceof EReference);

		assertEquals("a1", features.get(1).getName());
		assertTrue("Second feature is an EAttribute",
			features.get(1) instanceof EAttribute);

		assertEquals("r2", features.get(2).getName());
		assertTrue("Third feature is an EReference",
			features.get(2) instanceof EReference);
	}
}
