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
/**
 * 
 */
package org.eclipse.epsilon.test.util.builders.emf;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.emf.ecore.EPackage;

public class MetamodelBuilder {
	
	public static MetamodelBuilder aMetamodelWithSeveralPackages() {
		return new MetamodelBuilder();
	}

	private final List<EPackage> ePackages = new LinkedList<>();
	
	public MetamodelBuilder with(EPackageBuilder ePackageBuilder) {
		return with(ePackageBuilder.build());
	}
	
	public MetamodelBuilder with(EPackage ePackage) {
		ePackages.add(ePackage);
		return this;
	}
	
	public EPackage[] build() {
		return ePackages.toArray(new EPackage[]{});
	}
}
