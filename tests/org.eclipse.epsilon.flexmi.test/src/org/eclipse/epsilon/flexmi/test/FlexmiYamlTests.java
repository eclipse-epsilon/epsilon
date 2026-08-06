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

import org.eclipse.epsilon.flexmi.FlexmiParseException;
import org.junit.Test;

public class FlexmiYamlTests extends FlexmiTests {
	
	@Test
	public void testEmptyClasses() throws Exception {
		assertEval("EClass.all.size()", 2, "yaml/empty-classes.yaml");
	}
	
	@Test(expected = FlexmiParseException.class)
	public void testInvalidYaml() throws Exception {
		assertEval("EClass.all.size()", 2, "yaml/invalid.yaml");
	}
	
	@Test
	public void testListOfSuperclasses() throws Exception {
		assertEval("EClass.all.first().eSuperTypes.first().name", "C1", "yaml/list-of-superclasses.yaml");
	}
	
	@Test
	public void testArray() throws Exception {
		assertEval("EClass.all.size()", 2, "yaml/array.yaml");
	}
	
}
