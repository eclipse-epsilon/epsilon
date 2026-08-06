/*******************************************************************************
 * Copyright (c) 2008 The University of York.
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
package org.eclipse.epsilon.hutn.unparser.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.ContainmentSlot;
import org.eclipse.epsilon.hutn.model.hutn.HutnFactory;
import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.model.hutn.Slot;
import org.junit.Test;

public abstract class AbstractSlotUnparserTest {

	private static String   expectedFeatureName;
	private static String[] expectedHutns;	
	
	private static String unparsed;
	
	
	public static void slotTest(Slot<?> slot) {
		unparsed = new SlotUnparser(slot).unparse();
	}
	
	public static void attributeSlotTest(String featureName, Object[] values, String[] hutns) {
		AbstractSlotUnparserTest.expectedFeatureName = featureName;
		AbstractSlotUnparserTest.expectedHutns       = hutns;
		 
		final AttributeSlot slot = HutnFactory.eINSTANCE.createAttributeSlot();
		slot.setFeature(expectedFeatureName);
		slot.getValues().addAll(Arrays.asList(values));
		
		slotTest(slot);
	}
	
	public static void referenceSlotTest(String featureName, ClassObject[] classObjects, String[] hutns) {
		final List<String> referenceValues = new LinkedList<>();
		
		for (ClassObject classObject : classObjects) {
			referenceValues.add(classObject.getIdentifier());
		}
		
		referenceSlotTest(featureName, referenceValues.toArray(new String[]{}), classObjects, hutns);
	}
	
	public static void referenceSlotTest(String featureName, String[] referenceValues, ClassObject[] classObjects, String[] hutns) {
		AbstractSlotUnparserTest.expectedFeatureName = featureName;
		AbstractSlotUnparserTest.expectedHutns       = hutns;
		
		final ReferenceSlot slot = HutnFactory.eINSTANCE.createReferenceSlot();
		slot.setFeature(expectedFeatureName);
		slot.getValues().addAll(Arrays.asList(referenceValues));
		
		final Resource resource = EmfUtil.createResource();
		resource.getContents().addAll(Arrays.asList(classObjects));
		resource.getContents().add(slot);
		
		slotTest(slot);
	}
	
	public static void containmentSlotTest(String featureName, ClassObject[] classObjects, String[] hutns) {
		AbstractSlotUnparserTest.expectedFeatureName = featureName;
		AbstractSlotUnparserTest.expectedHutns       = hutns;
		
		final ContainmentSlot slot = HutnFactory.eINSTANCE.createContainmentSlot();
		slot.setFeature(expectedFeatureName);
		slot.setClassObjects(new BasicEList<>(Arrays.asList(classObjects)));
		
		final Resource resource = EmfUtil.createResource();
		resource.getContents().add(slot);
		
		slotTest(slot);
	}
	
	
	@Test
	public void textContainsAColon() {
		assertTrue(unparsed.contains(":"));
	}
	
	@Test
	public void textStartsWithFeatureName() {
		assertEquals(expectedFeatureName, featureName());
	}
	
	@Test
	public void textContainsCorrectNumberOfValues() {
		assertEquals(expectedHutns.length, numberOfValues());
	}
	
	@Test
	public void textContainsCorrectValues() {
		for (int i = 0; i < expectedHutns.length; i++) {
			assertEquals(expectedHutns[i], featureValue(i));
		}
	}
	
	
	
	private static String featureName() {
		return unparsed.split(":")[0].trim();
	}
	
	private static String featureValues() {
		return unparsed.substring(featureName().length()+1).trim();
	}
	
	private static int numberOfValues() {
		return featureValues().split(",").length;
	}
	
	private static String featureValue(int index) {
		return featureValues().split(",")[index].trim();
	}
}
