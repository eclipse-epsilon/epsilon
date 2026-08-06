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

import static org.eclipse.epsilon.test.util.HutnTestUtil.slotTest;
import static org.junit.Assert.assertEquals;

import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.ContainmentSlot;
import org.eclipse.epsilon.hutn.model.hutn.PackageObject;
import org.eclipse.epsilon.hutn.test.model.HutnTestWithFamiliesMetaModel;
import org.junit.Before;
import org.junit.Test;

public class GenerateNestedClassObject extends HutnTestWithFamiliesMetaModel {

	private static final int DUMMY_LINE_NUMBER = -1;
	
	private SpecGenerator generator;
	
	@Before
	public void setup() {
		generator = new SpecGenerator();
		generator.initialise("families");
	}
	
	@Test
	public void generatesNestedClassObject() {
		generator.generateTopLevelClassObject("f", "Family", DUMMY_LINE_NUMBER);
		generator.generateContainedClassObject("members", "p", "Person", DUMMY_LINE_NUMBER);
		
		assertEquals(1, getFirstPackage().getClassObjects().size());
		assertEquals(1, getFirstClassObject().getSlots().size());
		
		assertEquals("p", getNestedClassObject().getIdentifier());
		
		slotTest(getFirstClassObject().getSlots().get(0), ContainmentSlot.class, "members", "Person");
		
		assertEquals(getNestedClassObject(), generator.getCurrentClassObject());
	}
	
	@Test
	public void generatedContainmentSlotShouldHaveLineNumberOfFirstValue() {
		generator.generateTopLevelClassObject("f", "Family", DUMMY_LINE_NUMBER);
		generator.generateContainedClassObject("members", "p1", "Person", 1);
		generator.stopGeneratingCurrentClassObject();
		generator.generateContainedClassObject("members", "p2", "Person", 2);
		
		assertEquals(1, getFirstClassObject().getSlots().get(0).getLine());
	}
	
	@Test(expected=IllegalStateException.class)
	public void cannotCreateUnnestedContainedClassObject() {
		generator.generateContainedClassObject("members", "p", "Person", DUMMY_LINE_NUMBER);
	}
	
	@Test
	public void canInferTypeFromMetamodel() {
		generator.generateTopLevelClassObject("f", "Family", DUMMY_LINE_NUMBER);
		generator.generateContainedClassObject("members", "p", DUMMY_LINE_NUMBER);
		
		slotTest(getFirstClassObject().getSlots().get(0), ContainmentSlot.class, "members", "Person");
	}
	
	@Test
	public void inferTypeForNonContainment() {
		generator.generateTopLevelClassObject("f", "Family", DUMMY_LINE_NUMBER);
		generator.generateContainedClassObject("name", "n", DUMMY_LINE_NUMBER);
		
		slotTest(getFirstClassObject().getSlots().get(0), ContainmentSlot.class, "name", "UnknownType");
	}
	
	@Test
	public void cannotInferTypeForUnknownMetamodelFeature() {
		generator.generateTopLevelClassObject("f", "Family", DUMMY_LINE_NUMBER);
		generator.generateContainedClassObject("foobars", "x", DUMMY_LINE_NUMBER);
		
		slotTest(getFirstClassObject().getSlots().get(0), ContainmentSlot.class, "foobars", "UnknownType");
	}
	
	
	private PackageObject getFirstPackage() {
		return generator.getSpec().getObjects().get(0);
	}
	
	private ClassObject getFirstClassObject() {
		return getFirstPackage().getClassObjects().get(0);
	}
	
	private ClassObject getNestedClassObject() {
		return (ClassObject)getFirstClassObject().getSlots().get(0).getValues().get(0);
	}
}
