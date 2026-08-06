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
package org.eclipse.epsilon.hutn.unparser.packageObject;

import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createClassObject;
import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createPackageObject;
import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.hutn.unparser.internal.AbstractPackageObjectUnparserTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class PackageObjectWithManyClassObjects extends AbstractPackageObjectUnparserTest {

	@BeforeClass
	public static void setup() {
		packageObjectUnparserTest(createPackageObject("families",
		                                              createClassObject("John",       "Person"),
		                                              createClassObject("The Smiths", "Family"),
		                                              createClassObject("Pet")));
	}
	
	@Test
	public void hasOneClassObject() {
		assertEquals(3, numberOfClassObjects());
	}
	
	@Test
	public void hasJohnAsFirstClassObject() {
		assertEquals("Person \"John\" {}", classObject(0));
	}
	
	@Test
	public void hasSmithsAsFirstClassObject() {
		assertEquals("Family \"The Smiths\" {}", classObject(1));
	}
	
	@Test
	public void hasAPetAsFirstClassObject() {
		assertEquals("Pet  {}", classObject(2));
	}
}
