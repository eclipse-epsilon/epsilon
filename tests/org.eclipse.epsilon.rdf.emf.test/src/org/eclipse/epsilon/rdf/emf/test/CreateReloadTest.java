/********************************************************************************
 * Copyright (c) 2025 University of York
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

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;
import org.eclipse.epsilon.rdf.emf.RDFGraphResourceFactory;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CreateReloadTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@BeforeClass
	public static void setupDrivers() {
		Resource.Factory.Registry.INSTANCE
			.getExtensionToFactoryMap()
			.put("xmi", new XMIResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE
			.getExtensionToFactoryMap()
			.put("rdfres", new RDFGraphResourceFactory());
		Resource.Factory.Registry.INSTANCE
			.getExtensionToFactoryMap()
			.put("emf", new EmfaticResourceFactory());
	}

	@Test
	public void createThenReload() throws IOException {
		final File fRes = new File(tempFolder.getRoot(), "new.rdfres");
		final String pkgName = "example";

		// Create a new .rdfres from scratch with one EPackage
		{
			ResourceSet rs = new ResourceSetImpl();
			Resource r = rs.createResource(URI.createFileURI(fRes.getAbsolutePath()));
			EPackage ePkg = EcoreFactory.eINSTANCE.createEPackage();
			ePkg.setName("example");
			r.getContents().add(ePkg);
			r.save(null);
		}

		// Load the saved .rdfres and check the EPackage is there
		{
			ResourceSet rs = new ResourceSetImpl();
			Resource r = rs.getResource(URI.createFileURI(fRes.getAbsolutePath()), true);
			assertEquals(1, r.getContents().size());
			EPackage ePkg = (EPackage) r.getContents().get(0);
			assertEquals(pkgName, ePkg.getName());
		}
	}

}
