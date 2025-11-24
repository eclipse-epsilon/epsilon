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

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;
import org.eclipse.epsilon.rdf.emf.RDFGraphResourceFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * <p>
 * Parameterized test which uses EMF Compare to compare the {@code .xmi} version
 * of a given model with its {@code .rdfres} version. It fails if any differences
 * are found.
 * </p>
 *
 * <p>
 * Test cases are subfolders of resources/equivalence, where metamodels are in .emf
 * format, XMI models use the .xmi extension, and RDF models use the .rdfres extension.
 * </p>
 */
@RunWith(Parameterized.class)
public class LoadEquivalenceTest {

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

	@Parameters(name = "{0}")
	public static Object[] data() {
		final File baseFolder = new File(new File("resources"), "loadEquivalence");
		File[] subdirs = baseFolder.listFiles(f -> f.isDirectory());
		Arrays.sort(subdirs, (a, b) -> a.getName().compareTo(b.getName()));
		return subdirs;
	}

	private final File testCaseFolder;

	public LoadEquivalenceTest(File testCaseFolder) {
		this.testCaseFolder = testCaseFolder;
	}

	@Test
	public void equivalentModels() throws Exception {
		ResourceSet rsMetamodels = new ResourceSetImpl();
		loadModelsWithExtension(testCaseFolder, ".emf", rsMetamodels, (r) -> {
			registerEPackages(r);
		});

		ResourceSet rsXmi = new ResourceSetImpl();
		loadModelsWithExtension(testCaseFolder, ".xmi", rsXmi);

		ResourceSet rsRDF = new ResourceSetImpl();
		loadModelsWithExtension(testCaseFolder, ".rdfres", rsRDF);

		EMFCompare compareEngine = EMFCompare.builder().build();
		final IComparisonScope scope = new DefaultComparisonScope(rsXmi, rsRDF, null);
		final Comparison cmp = compareEngine.compare(scope);
		assertNoDifferences(testCaseFolder, cmp);

		rsXmi.getResources().forEach(r -> r.unload());
		rsRDF.getResources().forEach(r -> r.unload());
	}

	protected void assertNoDifferences(File testCaseFolder, Comparison cmp) {
		if (cmp.getDifferences().isEmpty()) {
			return;
		}

		System.err.println("Differences were found in " + testCaseFolder + ": ");
		for (Diff diff : cmp.getDifferences()) {
			System.err.println("* " + diff);
		}

		fail("Differences were reported: see error messages");
	}

	protected void registerEPackages(Resource rMetamodel) {
		for (EObject eob : rMetamodel.getContents()) {
			if (eob instanceof EPackage epkg) {
				// Global registration is needed to allow Emfatic itself
				// to reference the package (e.g. from an 'import' statement)
				EPackage.Registry.INSTANCE.put(epkg.getNsURI(), epkg);
			}
		}
	}

	protected void loadModelsWithExtension(File testCaseFolder, String fileNameSuffix, ResourceSet rs) throws IOException {
		loadModelsWithExtension(testCaseFolder, fileNameSuffix, rs, (x) -> {});
	}

	protected void loadModelsWithExtension(File testCaseFolder, String fileNameSuffix, ResourceSet rs, Consumer<Resource> onResource) throws IOException {
		// Sort by name (e.g. for interdependent metamodels)
		File[] filesToLoad = testCaseFolder.listFiles(fn -> fn.getName().endsWith(fileNameSuffix));
		Arrays.sort(filesToLoad, (a, b) -> a.getName().compareTo(b.getName()));

		for (File fEmf : filesToLoad) {
			rs.createResource(URI.createFileURI(fEmf.getAbsolutePath()));
		}
		for (Resource r : rs.getResources()) {
			r.load(null);
			onResource.accept(r);
		}
	}

 
}
