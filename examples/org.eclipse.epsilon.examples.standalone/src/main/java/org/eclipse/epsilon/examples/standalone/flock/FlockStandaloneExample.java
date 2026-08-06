/*********************************************************************
 * Copyright (c) 2008-2025 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/
package org.eclipse.epsilon.examples.standalone.flock;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.flock.FlockModule;

public class FlockStandaloneExample {
	
	public static void main(String[] args) throws Exception {
		EmfUtil.register(
			URI.createURI(FlockStandaloneExample.class.getResource("/metamodels/Tree.ecore").toString()),
			EPackage.Registry.INSTANCE);
			
		// Set up the original model
		EmfModel original = new EmfModel();
		original.setName("Source");
		original.setReadOnLoad(true);
		original.setStoredOnDisposal(false);
		original.setMetamodelUri("TreeDsl");
		original.setModelFile("models/Tree.xmi");
		original.load();
		
		// Set up the migrated model
		EmfModel migrated = new EmfModel();
		migrated.setName("Migrated");
		migrated.setReadOnLoad(false);
		migrated.setStoredOnDisposal(true);
		migrated.setMetamodelUri("TreeDsl");
		migrated.setModelFile("models/Tree.migrated.xmi");
		migrated.load();

		// Run the migration transformation
		FlockModule module = new FlockModule();
		module.parse(FlockStandaloneExample.class.getResource("/flock/tree2tree.mig"));
		module.getContext().getModelRepository().addModel(original);
		module.getContext().getModelRepository().addModel(migrated);
		module.getContext().setOriginalModel(original);
		module.getContext().setMigratedModel(migrated);

		try {
			module.execute();
		} finally {
			// Save the migrated model and free resources
			module.getContext().getModelRepository().dispose();
			module.getContext().dispose();
		}
	}
	
}
