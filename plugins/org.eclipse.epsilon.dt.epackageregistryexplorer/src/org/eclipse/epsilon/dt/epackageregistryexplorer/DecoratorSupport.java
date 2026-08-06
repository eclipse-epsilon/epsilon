/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
******************************************************************************/

package org.eclipse.epsilon.dt.epackageregistryexplorer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

public class DecoratorSupport {
	
	public static boolean isDecorator(EClass eClass) {
		if (eClass.getEAnnotation("decorator") != null) return true;
		for (EClass superType : eClass.getEAllSuperTypes()) {
			if (isDecorator(superType)) return true;
		}
		return false;
	}
	
	public static boolean isHook(EStructuralFeature eStructuralFeature) {
		return eStructuralFeature.getEAnnotation("decorator.hook") != null;
	}
	
	protected static boolean areCompatible(EClass subtype, EClassifier supertype, boolean inherited) {
		if (subtype == supertype || subtype.getName().equalsIgnoreCase(supertype.getName())) return true;
		if (inherited && subtype.getEAllSuperTypes().contains(supertype)) {
			return true;
		}
		return false;
	}
	
	public static Set<DecoratorHookDescriptor> getHooks(EClass eClass, List<EPackage> ePackages, boolean inherited) {
			
			Set<DecoratorHookDescriptor> hooks = new HashSet<>();
			
			for (EPackage p : ePackages) {
				for (EClassifier o : p.getEClassifiers()) {
					if (o instanceof EClass) {
						EClass decorator = (EClass) o;
						if (isDecorator(decorator)) {
							for (EStructuralFeature hook : decorator.getEStructuralFeatures()) {
								if (isHook(hook) && areCompatible(eClass, hook.getEType(), inherited)) {
									for (EStructuralFeature sf : decorator.getEAllStructuralFeatures()) {
										if (sf != hook) {
											hooks.add(new DecoratorHookDescriptor(sf));
										}
									}
								}
							}
						}
					}
				}
			}
			
			return hooks;
			
		}
}
 
