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

import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.test.model.HutnTestWithFamiliesMetaModel;
import org.junit.Before;
import org.junit.Test;

public class AddAttributeValue extends HutnTestWithFamiliesMetaModel {

	private static final int DUMMY_LINE_NUMBER = -1;
	
	private SpecGenerator generator;
	
	@Before
	public void setup() {
		generator = new SpecGenerator();
		generator.initialise("families");
		generator.generateTopLevelClassObject("f", "Family", DUMMY_LINE_NUMBER);
	}
	
	@Test
	public void addsSlotToTopLevelClassObject() {
		generator.addAttributeValue("averageAge", "32.4", DUMMY_LINE_NUMBER);
		
		slotTest(generator.getCurrentClassObject().getSlots().get(0), AttributeSlot.class, "averageAge", 32.4f);
	}
	
	@Test
	public void addsSlotToNestedClassObject() {
		generator.generateContainedClassObject("p", "members", "Person", DUMMY_LINE_NUMBER);
		generator.addAttributeValue("name", "John", DUMMY_LINE_NUMBER);
		
		slotTest(generator.getCurrentClassObject().getSlots().get(0), AttributeSlot.class, "name", "John");
	}
	
	@Test
	public void addSecondValueToSameAttribute() {
		generator.addAttributeValue("address", "10 Main Street", 1);
		generator.addAttributeValue("address", "123 Heslington Road", 2);
		
		slotTest(generator.getCurrentClassObject().getSlots().get(0), AttributeSlot.class, "address", "10 Main Street", "123 Heslington Road");
		
		assertEquals(1, generator.getCurrentClassObject().getSlots().get(0).getLine());
	}
	
	@Test
	public void addsSlotForUnknownFeature() {
		generator.addAttributeValue("numberOfCars", "2", DUMMY_LINE_NUMBER);
		
		slotTest(generator.getCurrentClassObject().getSlots().get(0), AttributeSlot.class, "numberOfCars", "2");
	}
	
	@Test
	public void addsReferenceSlotForContainmentFeature() {
		final ClassObject family = generator.getCurrentClassObject();
		generator.addAttributeValue("members", "_HigxkCT9Ed6m9JDbGM4gGg", DUMMY_LINE_NUMBER);
		generator.stopGeneratingCurrentClassObject();
		
		generator.generateTopLevelClassObject("_HigxkCT9Ed6m9JDbGM4gGg", "Person", DUMMY_LINE_NUMBER);
		
		slotTest(family.getSlots().get(0), ReferenceSlot.class, "members", "Person");
	}
}
