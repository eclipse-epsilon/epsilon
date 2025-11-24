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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;
import org.eclipse.epsilon.rdf.emf.RDFGraphResourceFactory;
import org.eclipse.epsilon.rdf.emf.RDFGraphResourceImpl;
import org.eclipse.epsilon.rdf.emf.RDFGraphResourceNotificationAdapterChangeRDF;
import org.eclipse.epsilon.rdf.emf.RDFGraphResourceNotificationAdapterTrace;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * <p>
 * Checks that the RDF eAdapters propagate to children elements when they are
 * added to an RDF resource, and that they are automatically remove from
 * children when they are detached from the resource.
 * </p>
 */
public class AdapterPropagationTest {

	@BeforeClass
	public static void setupDrivers() {
		Resource.Factory.Registry.INSTANCE
			.getExtensionToFactoryMap()
			.put("emf", new EmfaticResourceFactory());
		Resource.Factory.Registry.INSTANCE
			.getExtensionToFactoryMap()
			.put("rdfres", new RDFGraphResourceFactory());
	}

	@Test
	public void changeAdapterMatches() throws Exception {
		var rsMetamodels = new ResourceSetImpl();
		var rMetamodel = rsMetamodels.getResource(URI.createFileURI(
			new File("resources/rdfresConfigs/book.emf").getCanonicalPath()), true);
		var ePkg = (EPackage) rMetamodel.getContents().get(0);

		var rsModel = new ResourceSetImpl();
		rsModel.getPackageRegistry().put(ePkg.getNsURI(), ePkg);
		var r = rsModel.getResource(URI.createFileURI(
			new File("resources/rdfresConfigs/Validation_none.rdfres").getCanonicalPath()), true);

		var changeAdapter = r.eAdapters().stream()
			.filter(e -> e instanceof RDFGraphResourceNotificationAdapterChangeRDF).findFirst();
		assertTrue("There should be a change adapter in the resource", changeAdapter.isPresent());

		EObject root = (EObject) r.getContents().get(0);
		var changeAdapterEob = root.eAdapters().stream()
			.filter(e -> e instanceof RDFGraphResourceNotificationAdapterChangeRDF).findFirst();
		assertTrue("There should be a change adapter in the root EObject", changeAdapterEob.isPresent());
		assertSame("Both resource and root should use the same change adapter",
			changeAdapter.get(), changeAdapterEob.get());
	}

	@Test
	public void traceAdapterPropagates() throws IOException {
		var r = new RDFGraphResourceImpl();
		r.setURI(URI.createFileURI(File.createTempFile("tmp", ".rdfres").toString()));

		var adapter = new RDFGraphResourceNotificationAdapterTrace(r);
		assertEAdaptersPropagate(r, adapter);
	}

	private void assertEAdaptersPropagate(RDFGraphResourceImpl r, Adapter adapter) {
		r.eAdapters().add(adapter);

		var ePackage = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		r.getContents().add(ePackage);
		assertTrue("Adapter is added to EPackage when added to resource", ePackage.eAdapters().contains(adapter));

		r.getContents().remove(ePackage);
		assertFalse("Adapter is removed from EPackage when removed from resource", ePackage.eAdapters().contains(adapter));
	}

}
