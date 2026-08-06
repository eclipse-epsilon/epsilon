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
package org.eclipse.epsilon.hutn.unparser.classObject;

import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createAttributeSlot;
import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createClassObject;
import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createContainmentSlot;
import static org.eclipse.epsilon.hutn.test.util.HutnUtil.createReferenceSlot;
import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.hutn.unparser.internal.AbstractClassObjectUnparserTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class ClassObjectWithManySlots extends AbstractClassObjectUnparserTest {

	@BeforeClass
	public static void setup() {
		classObjectUnparserTest(createClassObject("John",
		                                          "Person",
		                                          createAttributeSlot("name", "John Doe"),
		                                          createReferenceSlot("self", "John"),
		                                          createContainmentSlot("accounts",
		                                                                createClassObject("current", "Account"),
		                                  				                createClassObject("savings", "Account"))));
	}
	
	@Test
	public void hasThreeFeatures() {
		assertEquals(3, numberOfFeatures());
	}
	
	@Test
	public void firstFeatureNameIsName() {
		assertEquals("name", featureName(0));
	}
	
	@Test
	public void firstFeatureValueIsJohnDoe() {
		assertEquals("\"John Doe\"", featureValues(0));
	}
	
	@Test
	public void secondFeatureNameIsSelf() {
		assertEquals("self", featureName(1));
	}
	
	@Test
	public void secondFeatureValueIsJohn() {
		assertEquals("Person \"John\"", featureValues(1));
	}
	
	@Test
	public void thirdFeatureNameIsAccounts() {
		assertEquals("accounts", featureName(2));
	}
	
	@Test
	public void thirdFeatureValueIsJohnsAccounts() {
		assertEquals("Account \"current\" {}, Account \"savings\" {}", featureValues(2));
	}
}
