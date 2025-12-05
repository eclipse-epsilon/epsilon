/********************************************************************************
 * Copyright (c) 2024 University of York
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garcia-Dominguez - initial API and implementation
 ********************************************************************************/
package org.eclipse.epsilon.rdf.emf.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.epsilon.rdf.emf.RDFGraphResourceFactory;
import org.eclipse.epsilon.rdf.emf.RDFGraphResourceImpl;
import org.eclipse.epsilon.rdf.validation.RDFValidation.ValidationMode;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigModelValidationTest {
	
	@BeforeClass
	public static void setupDrivers() {
		Resource.Factory.Registry.INSTANCE
			.getExtensionToFactoryMap()
			.put("rdfres", new RDFGraphResourceFactory());
	}
	
	private final File VALIDATION_BLANK = new File("resources/rdfresConfigs/Validation_blank.rdfres");
	private final File VALIDATION_JENA_CLEAN = new File("resources/rdfresConfigs/Validation_jena-clean.rdfres");
	private final File VALIDATION_RUBBISH = new File("resources/rdfresConfigs/Validation_rubbish.rdfres");
	
	@Test
	public void ValidationBlank () throws IOException {
		RDFGraphResourceImpl graph = getGraphResourceImpl(VALIDATION_BLANK);
		ValidationMode mode = graph.getConfig().getRawValidationMode();
		assertEquals("none", mode.getId());
	}
	
	@Test
	public void ValidationJenaClean () throws IOException {
		RDFGraphResourceImpl graph = getGraphResourceImpl(VALIDATION_JENA_CLEAN);
		ValidationMode mode = graph.getConfig().getRawValidationMode();
		assertEquals("jena-clean", mode.getId());
	}
	
	@Test
	public void ValidationRubbish() {
		try {
			getGraphResourceImpl(VALIDATION_RUBBISH);
			fail("An IllegalArgumentException should have been thrown for `rubbish` validation mode configuration");
		} catch (IllegalArgumentException | IOException e) {
			String sErrors = e.getMessage();
			assertEquals(e.getClass(), IllegalArgumentException.class);
			assertTrue("Loading a configuration with an invalid validation mode should report an error",
				sErrors.contains("Validation mode not found:"));
		}
	}
	
	protected RDFGraphResourceImpl getGraphResourceImpl(File file) throws IOException {
		ResourceSet rsRDF = new ResourceSetImpl();
		loadFile(file, rsRDF);
		return (RDFGraphResourceImpl) rsRDF.getResources().get(0);
	}

	protected void loadFile(File file, ResourceSet rs) throws IOException {
		Resource rMetamodel = rs.createResource(URI.createFileURI(file.getAbsolutePath()));
		rs.getResources().add(rMetamodel);
		for (Resource r : rs.getResources()) {
			r.load(null);
		}
	}

}
