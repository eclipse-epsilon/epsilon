/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.m3;

import java.util.ArrayList;
import java.util.List;

public class MetaClass extends MetaType {
	
	protected List<MetaClass> superTypes = new ArrayList<>();
	protected List<StructuralFeature> structuralFeatures = new ArrayList<>();
	protected boolean isAbstract;
	
	public List<MetaClass> getSuperTypes() {
		return superTypes;
	}
	
	public List<StructuralFeature> getStructuralFeatures() {
		return structuralFeatures;
	}
	
	public List<StructuralFeature> getAllStructuralFeatures() {
		List<StructuralFeature> allStructuralFeatures = new ArrayList<>();
		for (MetaClass superType : superTypes) {
			allStructuralFeatures.addAll(superType.getAllStructuralFeatures());
		}
		allStructuralFeatures.addAll(getStructuralFeatures());
		return allStructuralFeatures;
	}
	
	public boolean isAbstract() {
		return isAbstract;
	}
	
	public void setAbstract(boolean isAbstract) {
		this.isAbstract = isAbstract;
	}
	
	public StructuralFeature getStructuralFeature(String name) {
		for (StructuralFeature structuralFeature : getAllStructuralFeatures()) {
			if (structuralFeature.getName().equals(name)) {
				return structuralFeature;
			}
		}
		return null;
	}
	
}
