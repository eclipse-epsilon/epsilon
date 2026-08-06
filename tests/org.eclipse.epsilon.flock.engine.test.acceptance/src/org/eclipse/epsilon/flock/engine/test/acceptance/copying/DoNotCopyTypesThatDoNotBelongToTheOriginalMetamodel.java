/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.flock.engine.test.acceptance.copying;

import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfModelFactory;
import org.eclipse.epsilon.emc.emf.EmfModelFactory.AccessMode;
import org.eclipse.epsilon.flock.engine.test.acceptance.models.FlockAcceptanceTestModels;
import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * When we have an original model that conforms to an original metamodel which
 * may reference types in another metamodel, Flock must not copy into the 
 * migrated model types that belong to the referenced metamodel.
 * 
 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=371213
 */
public class DoNotCopyTypesThatDoNotBelongToTheOriginalMetamodel extends FlockAcceptanceTest {

	private static final String strategy = "";
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesToFamilies(strategy, loadModelWithACrossReferenceToExternalType());
		
		migrated.setVariable("joe", "Person.all.first");
	}

	private static EmfModel loadModelWithACrossReferenceToExternalType() throws Exception {
		final EmfModel model = EmfModelFactory.getInstance().loadEmfModel("Original", FlockAcceptanceTestModels.getBloggsModelFile(), "families", AccessMode.READ_ONLY);
		model.setExpand(false);
		model.load();
		return model;
	}

	
	@Test
	public void shouldProduceOnePerson() throws Throwable {
		migrated.assertEquals(1, "Person.all.size");
	}
	
	@Test
	public void referenceToSharedAccountsShouldBeCopied() throws Throwable {
		migrated.assertEquals(1, "joe.accounts.size");
	}
	
	@Test
	public void shouldCopyAccountIntoMigratedModel() throws Throwable {
		migrated.assertEquals(1, "Account.all.size");
	}
	
	@Test
	public void shouldNotCopyAccountsIntoMigratedModel() throws Throwable {
		migrated.assertEquals(0, "Accounts.all.size");
	}
}
