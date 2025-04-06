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

import org.junit.Test;

public class StandaloneTests extends FlexmiTests {
	
	@Test
	public void testNoWarnings() throws Exception {
		assertNoWarnings("standalone/valid-model.flexmi");
	}
	
	@Test
	public void testUnresolvedReferenceWarning() throws Exception {
		assertEquals(loadResource("standalone/unresolved-reference.flexmi").getWarnings().size(), 1);
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
	
}
