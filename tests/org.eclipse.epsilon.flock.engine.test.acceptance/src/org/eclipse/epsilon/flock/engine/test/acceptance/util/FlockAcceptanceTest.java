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
package org.eclipse.epsilon.flock.engine.test.acceptance.util;

import static org.junit.Assert.fail;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.emc.emf.AbstractEmfModel;
import org.eclipse.epsilon.emc.emf.EmfPrettyPrinter;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.flock.IFlockModule;
import org.eclipse.epsilon.flock.execute.FlockResult;
import org.eclipse.epsilon.flock.FlockModule;
import org.eclipse.epsilon.hutn.test.model.HutnTestWithFamiliesMetaModel;
import org.eclipse.epsilon.test.util.ModelWithEolAssertions;

public abstract class FlockAcceptanceTest extends HutnTestWithFamiliesMetaModel {
	
	protected static ModelWithEolAssertions migrated;
	protected static FlockResult result;
	
	protected static void migrateFamiliesToFamilies(String strategy, AbstractEmfModel original) throws Exception {
		final InMemoryEmfModel migratedModel = new InMemoryEmfModel("Migrated", EmfUtil.createResource(), "families");
		migrate(strategy, original, migratedModel);
	}
	
	protected static void migrateFamiliesToFamilies(String strategy, String hutnForOriginalModel) throws Exception {
		final InMemoryEmfModel migratedModel = new InMemoryEmfModel("Migrated", EmfUtil.createResource(), "families");
		migrate(strategy, hutnToFamily(hutnForOriginalModel), migratedModel);
	}
	
	protected static void migrateFamilies(String strategy, String hutnForOriginalModel, EPackage... evolvedMetamodel) throws Exception {
		final InMemoryEmfModel migratedModel = new InMemoryEmfModel("Migrated", EmfUtil.createResource(), evolvedMetamodel);
		migrate(strategy, hutnToFamily(hutnForOriginalModel), migratedModel);
	}
	
	protected static AbstractEmfModel hutnToFamily(String hutnForOriginalModel) {
		return new FamiliesModelConstructor().constructModel("Original", hutnForOriginalModel);
	}
	
	private static void migrate(String strategy, AbstractEmfModel original, InMemoryEmfModel migratedModel) throws Exception {
		final IFlockModule migrator = new FlockModule();
		migrator.getContext().getPrettyPrinterManager().addPrettyPrinter(new EmfPrettyPrinter());
		original.setExpand(false);
		
		if (migrator.parse(strategy) && migrator.getParseProblems().isEmpty()) {		
			result = migrator.execute(original, migratedModel);
			migrated = new ModelWithEolAssertions(migratedModel);
			result.printWarnings(System.err);
		}
		else {
			migrator.getParseProblems().forEach(System.err::println);
			fail("Could not parse migration strategy.");
		}
	}
}
