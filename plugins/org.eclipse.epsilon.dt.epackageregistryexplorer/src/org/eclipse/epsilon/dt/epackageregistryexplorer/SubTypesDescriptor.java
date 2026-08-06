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

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

public class SubTypesDescriptor {
	
	protected EClass eClass;
	
	public SubTypesDescriptor(EClass eClass) {
		this.eClass = eClass;
	}
	
	public EClass getEClass() {
		return eClass;
	}
	
	public Collection<EClass> getSubtypes(Collection<EPackage> ePackages) {
		
		Collection<EClass> subtypes = new ArrayList<>();
		
		for (EPackage p : ePackages) {
			for (EClassifier o : p.getEClassifiers()) {
				if (o instanceof EClass) {
					EClass eClass = (EClass) o;
					if (eClass.getESuperTypes().contains(this.eClass)) {
						subtypes.add(eClass);
					}
				}
			}
		}
		
		return subtypes;
	}
	
	public boolean hasSubtypes(Collection<EPackage> ePackages) {
		
		for (EPackage p : ePackages) {
			for (EClassifier o : p.getEClassifiers()) {
				if (o instanceof EClass) {
					EClass eClass = (EClass) o;
					if (eClass.getESuperTypes().contains(this.eClass)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
 
