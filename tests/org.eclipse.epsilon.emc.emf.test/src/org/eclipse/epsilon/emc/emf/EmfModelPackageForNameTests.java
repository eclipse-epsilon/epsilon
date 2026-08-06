/*******************************************************************************
 * Copyright (c) 2013 The University of York.
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
package org.eclipse.epsilon.emc.emf;

import static org.eclipse.epsilon.test.util.builders.emf.EPackageBuilder.aMetamodel;
import static org.eclipse.epsilon.test.util.builders.emf.MetamodelBuilder.aMetamodelWithSeveralPackages;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.File;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.emc.emf.EmfModelFactory.AccessMode;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmfModelPackageForNameTests {
	
	private static EmfModel model;
	
	@BeforeClass
	public static void setup() throws Exception {
		EPackage[] metamodel = aMetamodelWithSeveralPackages()
			.with(aMetamodel()
					.named("top")
					.withNsURI("top")
					.with(aMetamodel()
						.named("middle")
						.withNsURI("middle")
						.with(aMetamodel().named("bottom").withNsURI("bottom"))
					)
			)
			.with(aMetamodel().named("top2").withNsURI("top2"))
			.build();
			
		for (EPackage p : metamodel) {
			EPackage.Registry.INSTANCE.put(p.getNsURI(), p);
		}
		
		final File modelFile = FileUtil.createTempFile("Test.model");
		model = EmfModelFactory.getInstance().loadEmfModel("Test", modelFile, metamodel, AccessMode.WRITE_ONLY);
	}
	
	@Test
	public void hasTopLevelPackage() throws Exception {
		assertTrue(model.hasPackage("top"));
	}
	
	@Test
	public void hasSecondTopLevelPackage() throws Exception {
		assertTrue(model.hasPackage("top2"));
	}
	
	@Test
	public void hasNestedPackage() throws Exception {
		assertTrue(model.hasPackage("top::middle"));
	}
	
	@Test
	public void hasDoublyNestedPackage() throws Exception {
		assertTrue(model.hasPackage("top::middle::bottom"));
	}
	
	@Test
	public void doesNotHaveNonExistentPackage() throws Exception {
		assertFalse(model.hasPackage("nonexistent"));
	}
}
