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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.jena.rdf.model.Model;
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
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.rdf.emf.RDFGraphResourceFactory;
import org.eclipse.epsilon.rdf.emf.RDFGraphResourceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * <p>
 * Parameterized test which uses EMF Compare to compare the {@code .xmi} version
 * of a given model with its {@code .rdfres} version. It fails if any
 * differences are found after running an EOL program on the XMI and RDF
 * representations.
 * </p>
 *
 * <p>
 * Test cases are eol files in subfolders of resources/equivalence_multivalue,
 * where metamodels are in .emf format, XMI models use the .xmi extension, and
 * RDF models use the .rdfres extension and RDF data models are .ttl.
 * </p>
 */
public abstract class AbstractChangeEquivalenceTest {
	
	private final boolean isConsoleOutputActive;
	
	protected final File eolTestFile;
	protected final File eolTestFolder;
	
	public AbstractChangeEquivalenceTest(File eolTestFile) {
		 this(eolTestFile, false);
	}
	
	public AbstractChangeEquivalenceTest(File eolTestFile, boolean outputToConsole) {
		this.eolTestFile = eolTestFile;
		this.eolTestFolder = eolTestFile.getParentFile();
		isConsoleOutputActive = outputToConsole;
	}

	public static List<File> findEOLScriptsWithin(File baseFolder) {
		List<File> fileList = new ArrayList<File>();
		File[] subdirs = baseFolder.listFiles(f -> f.isDirectory());
		for (File subdir : subdirs) {
			File[] eolTestFiles = subdir.listFiles(fn -> fn.getName().endsWith(".eol"));
			Arrays.sort(eolTestFiles, (a, b) -> a.getName().compareTo(b.getName()));
			for (File file : eolTestFiles) {
				fileList.add(file);
			}
		}
		return fileList;
	}

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
	public void loadModelsAndRunEolTest() throws Exception {
		if(isConsoleOutputActive) {
			System.out.println(String.format("\n ** TEST: %s ** ",eolTestFile.getName()));
		}
		
		restoreRdfFiles(); // Clean up any backup files from failed tests.
		
		// Load the meta model
		ResourceSet rsMetamodels = new ResourceSetImpl();		
		loadModelsWithExtension(eolTestFolder, ".emf", rsMetamodels);

		// Load and change XMI model resource, this is what we want the RDF to match
		if(isConsoleOutputActive) {
			System.out.println("\n == XMI ==");
		}
		ResourceSet rsXMI = new ResourceSetImpl();
		registerEPackages(rsMetamodels, rsXMI);
		loadModelsWithExtension(eolTestFolder, ".xmi", rsXMI);
		Resource xmiModelResource = rsXMI.getResources().get(0);
		executeEol(xmiModelResource, eolTestFile);
		
		// Load and change RDF model resource, this should match the XMI after save and reload		
		if(isConsoleOutputActive) {
			System.out.println("\n\n == RDF ==");
		}
		ResourceSet rsRDF = new ResourceSetImpl();
		registerEPackages(rsMetamodels, rsRDF);
		loadModelsWithExtension(eolTestFolder, ".rdfres", rsRDF);
		Resource rdfModelResource = rsRDF.getResources().get(0);
		if(isConsoleOutputActive) {
			printModelToConsole(((RDFGraphResourceImpl)rdfModelResource).getFirstNamedModel(), "TTL before change: ");
		}
		// CHANGE
		executeEol(rdfModelResource, eolTestFile);
		
		// SAVE RDF resource, clear the resource set
		backupRdfFiles();
		rdfModelResource.save(null);
		rdfModelResource.unload();
		rsRDF.getResources().remove(0);
		
		// Reload RDF model resource
		loadModelsWithExtension(eolTestFolder, ".rdfres", rsRDF);
		rdfModelResource = rsRDF.getResources().get(0);
		
		restoreRdfFiles(); // We may crash out on the test
		if(isConsoleOutputActive) {
			printModelToConsole(((RDFGraphResourceImpl)rdfModelResource).getFirstNamedModel(), "TTL after change: ");
		}
		// Compare reloaded RDF and XMI models
		EMFCompare compareEngine = EMFCompare.builder().build();
		final IComparisonScope scope = new DefaultComparisonScope(rsXMI, rsRDF, null);
		final Comparison cmp = compareEngine.compare(scope);
		assertNoDifferences(eolTestFile, cmp);

		rsXMI.getResources().forEach(Resource::unload);
		rsRDF.getResources().forEach(Resource::unload);
	}
	
	protected void backupRdfFiles() throws FileNotFoundException, IOException {
		File[] rdfFiles = eolTestFolder.listFiles(fn -> fn.getName().endsWith(".ttl"));
		for (File file : rdfFiles) {
			String backupFilePath = file.getAbsolutePath().concat("_backup");
			File backupFile = new File(backupFilePath);
			Files.copy(file.toPath(), backupFile.toPath());
		}
	}
	
	protected void restoreRdfFiles() throws FileNotFoundException, IOException {
		File[] rdfFiles = eolTestFolder.listFiles(fn -> fn.getName().endsWith(".ttl_backup"));
		for (File file : rdfFiles) {
			String backupFilePath = file.getAbsolutePath().replace(".ttl_backup",".ttl");
			File backupFile = new File(backupFilePath);
			Files.copy(file.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			Files.delete(file.toPath());
		}
	}
	
	protected void registerEPackages(ResourceSet rsMetamodels, ResourceSet rsTarget) {
		for (Resource rMetamodel : rsMetamodels.getResources()) {
			for (EObject eob : rMetamodel.getContents()) {
				if (eob instanceof EPackage epkg) {
					rsTarget.getPackageRegistry().put(epkg.getNsURI(), epkg);
				}
			}
		}
	}

	protected void loadModelsWithExtension(File modelFolder, String fileNameSuffix, ResourceSet rs) throws IOException {		
		for (File fEmf : modelFolder.listFiles(fn -> fn.getName().endsWith(fileNameSuffix))) {
			Resource rMetamodel = rs.createResource(URI.createFileURI(fEmf.getAbsolutePath()));
			rs.getResources().add(rMetamodel);
		}
		for (Resource r : rs.getResources()) {
			r.load(null);
		}
	}
	
	protected void assertNoDifferences(File testCase, Comparison cmp) {
		if (cmp.getDifferences().isEmpty()) {
			return;
		}

		System.err.println("Differences were found in " + testCase + ": ");
		for (Diff diff : cmp.getDifferences()) {
			System.err.println("* " + diff);
			// We could output the Turtle and XMI for the models here?
		}
		fail("Differences were reported: see error messages");
	}
	
	protected void executeEol(Resource resource, File eolCode) throws Exception {
		// Loads the EMF model from the resource
		InMemoryEmfModel model = new InMemoryEmfModel(resource);
		model.setName("theModel");
		// Parses and executes the EOL program
		EolModule module = new EolModule();
		module.parse(eolCode);
		
		module.getContext().getFrameStack().put(
				Variable.createReadOnlyVariable("consoleOutput", isConsoleOutputActive)
			);
		
		module.getContext().getModelRepository().addModel(model);
		module.execute();
		model.dispose();
	}

	public static void printModelToConsole(Model model, String label) {
		System.out.println(String.format("\n %s \n", label));
		OutputStream console = System.out;
		model.write(console, "ttl");
	}
}