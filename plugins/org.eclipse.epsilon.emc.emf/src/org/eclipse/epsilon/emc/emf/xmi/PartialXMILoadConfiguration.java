/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.emf.xmi;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

public class PartialXMILoadConfiguration {
	
	protected Set<EClass> allOfType = new LinkedHashSet<>();
	protected Set<EClass> allOfKind = new LinkedHashSet<>();
	protected Set<EClassStructuralFeature> features = new LinkedHashSet<>();
	protected HashMap<EClass, EObject> placeholders = new HashMap<>();
	
	public EObject getPlaceholder(EClass eClass) {
		if (!placeholders.containsKey(eClass)) {
			placeholders.put(eClass, eClass.getEPackage().getEFactoryInstance().create(eClass));
		}
		return placeholders.get(eClass);
	}
	
	public Collection<EObject> getPlaceholders() {
		return placeholders.values();
	}
	
	public boolean isPlaceholder(EObject eObject) {
		return placeholders.values().contains(eObject);
	}
	
	public Set<EClass> getAllOfType() {
		return allOfType;
	}
	
	public Set<EClass> getAllOfKind() {
		return allOfKind;
	}
	
	public void addFeature(EClass eClass, EStructuralFeature eStructuralFeature) {
		features.add(new EClassStructuralFeature(eClass, eStructuralFeature));
		if (eStructuralFeature instanceof EReference) {
			EReference eReference = (EReference) eStructuralFeature;
			if (!eReference.isContainment()) allOfKind.add((EClass) eStructuralFeature.getEType());
		}
	}
	
	public void addAllOfType(EClass eClass) {
		allOfType.add(eClass);
	}
	
	public void addAllOfKind(EClass eClass) {
		allOfKind.add(eClass);
	}
	
	public Set<EClassStructuralFeature> getFeatures() {
		return features;
	}
	
	public boolean shouldSetValue(EObject eObject, String name) {
		return features.stream().anyMatch(f -> 
			f.getEClass().isInstance(eObject) && 
			f.getEStructuralFeature().getName().equals(name));
	}
	
	public boolean shouldCreateObject(EClassifier eClassifier, EObject parent, String feature) {
		
		if (eClassifier instanceof EClass) {
			EClass eClass = (EClass) eClassifier;
			if (allOfType.stream().anyMatch(t -> t == eClass) ||
				allOfKind.stream().anyMatch(t -> t.isSuperTypeOf(eClass) || t == eClassifier)) {
				return true;
			}
			else {
				if (feature != null && parent != null) {
					EClass parentEClass = parent.eClass();
					EStructuralFeature eStructuralFeature = parentEClass.getEStructuralFeature(feature);
					return (features.stream().anyMatch(f -> 
						f.getEStructuralFeature() == eStructuralFeature &&
						(f.getEClass() == parentEClass || f.getEClass().isSuperTypeOf(parentEClass))));
				}
			}
		}
		else {
			return true;
		}
		
		return !(eClassifier instanceof EClass) || 
			allOfKind.stream().anyMatch(t -> t == eClassifier);
	}
}
