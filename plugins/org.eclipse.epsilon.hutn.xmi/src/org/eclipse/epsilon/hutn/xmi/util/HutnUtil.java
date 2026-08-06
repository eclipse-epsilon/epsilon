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
package org.eclipse.epsilon.hutn.xmi.util;

import java.util.Arrays;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.ReferenceSlot;
import org.eclipse.epsilon.hutn.model.hutn.Slot;

public abstract class HutnUtil {

	private HutnUtil() {}
	
	public static EStructuralFeature determineFeatureFromMetaClass(ClassObject classObject, String featureName) {
    	if (classObject.hasEClass()) {
    		for (EStructuralFeature feature : classObject.getEClass().getEAllStructuralFeatures()) {
    			if (featureName.equals(feature.getName())) {
    				return feature;
    			}
    		}
    	}
    	
    	return null;
	}
	
	public static Slot<?> determineSlotFromMetaFeature(ClassObject classObject, String featureName) {
		final EStructuralFeature feature = determineFeatureFromMetaClass(classObject, featureName); 
		
		if (feature == null)
			return null;
		
		
		final Slot<?> slot;
		
		if (feature instanceof EReference) {
			if (((EReference)feature).isContainment()) {
				slot = classObject.findOrCreateContainmentSlot(featureName);
			
			} else {
				slot = classObject.findOrCreateReferenceSlot(featureName);
			}
			
		} else {
			slot = classObject.findOrCreateAttributeSlot(featureName);
		}
		
		slot.setFeature(featureName);    			
	    return slot;
    }
	
	public static void addValueToSlot(Slot<?> slot, String value) {
		if (slot instanceof AttributeSlot) {
			addValueToSlot((AttributeSlot)slot, value);
		
		} else if (slot instanceof ReferenceSlot) {
			addValueToSlot((ReferenceSlot)slot, value);
		
		} else {
			throw new IllegalArgumentException("slot must be of type AttributeSlot or ReferenceSlot, but was: " + slot.getClass().getCanonicalName());
		}
	}
	
	public static void addValueToSlot(AttributeSlot slot, String value) {
		if (slot.hasEStructuralFeature()) {
			slot.getValues().add(EmfUtil.createFromString((EDataType)slot.getEStructuralFeature().getEType(), value));
		
		} else {
			slot.getValues().add(value);
		}
	}
	
	public static void addValueToSlot(ReferenceSlot slot, String value) {
		slot.getValues().addAll(Arrays.asList(value.split(" ")));
	}
}
