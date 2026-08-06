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
package org.eclipse.epsilon.hutn.xmi.parser.generator;

import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.PackageObject;
import org.junit.BeforeClass;
import org.junit.Test;

public class GenerateTopLevelClassObject {

	private static final int DUMMY_LINE_NUMBER = -1;
	
	private static final SpecGenerator generator = new SpecGenerator();
	
	@BeforeClass
	public static void setup() {
		generator.initialise();
		generator.generateTopLevelClassObject("f", "foo", DUMMY_LINE_NUMBER);
	}
	
	@Test
	public void packageShouldContainOneClassObject() {
		assertEquals(1, getFirstPackage().getClassObjects().size());
	}
	
	@Test
	public void classObjectShouldHaveCorrectIdentifier() {
		assertEquals("f", getFirstClassObject().getIdentifier());
	}
	
	@Test
	public void classObjectShouldHaveCorrectType() {
		assertEquals("foo", getFirstClassObject().getType());
	}
	
	@Test
	public void currentClassObjectShouldBeTheNewClassObject() {
		assertEquals(getFirstClassObject(), generator.getCurrentClassObject());
	}
	
	@Test(expected=IllegalStateException.class)
	public void cannotCreateNestedTopLevelClassObject() {
		generator.generateTopLevelClassObject("b", "bar", DUMMY_LINE_NUMBER);
	}
	
	
	private static PackageObject getFirstPackage() {
		return generator.getSpec().getObjects().get(0);
	}
	
	private static ClassObject getFirstClassObject() {
		return getFirstPackage().getClassObjects().get(0);
	}
}
