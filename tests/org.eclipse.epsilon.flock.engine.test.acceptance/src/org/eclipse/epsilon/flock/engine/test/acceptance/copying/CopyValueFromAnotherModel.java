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
package org.eclipse.epsilon.flock.engine.test.acceptance.copying;

import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfModelFactory;
import org.eclipse.epsilon.emc.emf.EmfModelFactory.AccessMode;
import org.eclipse.epsilon.flock.engine.test.acceptance.models.FlockAcceptanceTestModels;
import org.eclipse.epsilon.flock.engine.test.acceptance.util.FlockAcceptanceTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class CopyValueFromAnotherModel extends FlockAcceptanceTest {

	private static final String strategy = "";
	
	@BeforeClass
	public static void setup() throws Exception {
		migrateFamiliesToFamilies(strategy, loadModelWithACrossReference());	
		migrated.setVariable("sally", "Person.all.first");
	}

	private static EmfModel loadModelWithACrossReference() throws Exception {
		final EmfModel model = EmfModelFactory.getInstance().loadEmfModel("Original", FlockAcceptanceTestModels.getDoesModelFile(), "families", AccessMode.READ_ONLY);
		model.setExpand(false);
		model.load();
		return model;
	}

	
	@Test
	public void shouldProduceOnePerson() throws Throwable {
		migrated.assertEquals(1, "Person.all.size");
	}
	
	@Test
	public void friendShouldBeCopied() throws Throwable {
		migrated.assertEquals(1, "sally.friends.size");
	}
}
