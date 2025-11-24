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
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.vocabulary.RDF;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.epsilon.rdf.emf.RDFGraphResourceFactory;
import org.eclipse.epsilon.rdf.emf.RDFGraphResourceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class CustomEObjectIRITest {

	@BeforeClass
	public static void setupDrivers() {
		Resource.Factory.Registry.INSTANCE
			.getExtensionToFactoryMap()
			.put("rdfres", new RDFGraphResourceFactory());
	}

	@Test
	public void createAtIRI() throws IOException {
		ResourceSet rs = new ResourceSetImpl();

		Path rdfResPath = Paths.get("resources", "emptyGraph", "empty.rdfres");
		URI rdfResURI = URI.createFileURI(rdfResPath.toAbsolutePath().toString());
		RDFGraphResourceImpl r = (RDFGraphResourceImpl) rs.getResource(rdfResURI, true);

		assertTrue("The resource is initially empty", r.getContents().isEmpty());
		String expectedIRI = "http://eclipse.org/epsilon/test/#example";
		EPackage ePkg = (EPackage) r.createInstanceAt(EcorePackage.eINSTANCE.getEPackage(), expectedIRI);
		assertEquals("The object should have been added to the resource", List.of(ePkg), r.getContents());

		org.apache.jena.rdf.model.Resource rdfEPkg = r.getRDFResource(ePkg);
		assertEquals("The underlying RDF resource should have the expected IRI", expectedIRI, rdfEPkg.getURI());

		List<Property> preds = new ArrayList<>();
		rdfEPkg.listProperties().forEach(stmt -> preds.add(stmt.getPredicate()));
		assertEquals("Only the rdf:type property should be listed for the EPackage's RDF resource",
			List.of(RDF.type), preds);
	}

	@Test
	public void createAtFromNotLoadedModel() {
		ResourceSet rs = new ResourceSetImpl();

		// Do not load the graph: we will work from an in-memory model
		Path rdfResPath = Paths.get("resources", "emptyGraph", "empty.rdfres");
		URI rdfResURI = URI.createFileURI(rdfResPath.toAbsolutePath().toString());
		RDFGraphResourceImpl r = (RDFGraphResourceImpl) rs.createResource(rdfResURI);

		assertTrue("The resource is initially empty", r.getContents().isEmpty());
		String expectedIRI = "http://eclipse.org/epsilon/test/#example";
		EPackage ePkg = (EPackage) r.createInstanceAt(EcorePackage.eINSTANCE.getEPackage(), expectedIRI);
		assertEquals("The object should have been added to the resource", List.of(ePkg), r.getContents());
	}

}
