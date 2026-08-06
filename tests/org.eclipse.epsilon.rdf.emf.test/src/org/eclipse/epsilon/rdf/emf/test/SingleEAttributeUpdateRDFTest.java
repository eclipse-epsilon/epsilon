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

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.Date;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.Comparison;
import org.eclipse.emf.compare.Diff;
import org.eclipse.emf.compare.EMFCompare;
import org.eclipse.emf.compare.scope.DefaultComparisonScope;
import org.eclipse.emf.compare.scope.IComparisonScope;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.emfatic.core.EmfaticResourceFactory;
import org.eclipse.epsilon.rdf.emf.RDFGraphResourceFactory;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SingleEAttributeUpdateRDFTest {

	static final boolean CONSOLE_OUTPUT_ACTIVE = true;

	@BeforeClass
	public static void setupDrivers() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("rdfres", new RDFGraphResourceFactory());
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("emf", new EmfaticResourceFactory());
	}

	private final File originalTTL = new File("resources/EAttribute_test/model_data.ttl.org");
	private final File workingTTL = new File("resources/EAttribute_test/model_data.ttl");

	private final File rdfGraphModel = new File("resources/EAttribute_test/model.rdfres");
	private final File xmiModelBefore = new File("resources/EAttribute_test/model_before.xmi");
	// This file is generated _AFTER_ changing the RDFGraphResource
	private final File xmiModelAfter = new File("resources/EAttribute_test/model_after.xmi");
	private final File metaModelFile = new File("resources/EAttribute_test/metamodel.emf");

	ResourceSet metaModel = null;
	ResourceSet rdf = null; 
	ResourceSet xmiBefore = null;
	ResourceSet xmiAfter = null;

	// Entity from the Model(s) used to test Attributes
	EObject rdfEntity = null;
	EObject xmiEntity = null;

	private String testType = "";

	public void setUpWorkingTTL() throws IOException {
		copyFile(originalTTL, workingTTL);

		metaModel = getMetaModelResourceSet(metaModelFile);
		rdf = getGraphResourceSet(rdfGraphModel, metaModel);
		xmiBefore = getXMIResourceSet(xmiModelBefore, metaModel);

		rdfEntity = getRdfEntityForAttributeTest(rdf);
		xmiEntity = getRdfEntityForAttributeTest(xmiBefore);
	}

	@After
	public void cleanUp() throws IOException {
		if(workingTTL.exists()) {
			Files.delete(workingTTL.toPath());
		}
		if(xmiModelAfter.exists()) {
			Files.delete(xmiModelAfter.toPath());
		}
	}

	@Test
	public void baselineBeforeTest() throws IOException {
		// Check we can load the beforeXMI and that it matched the RDF version
		testType = "baselineBeforeTest";	
		equivalentModels("baselineBeforeTest : ", rdf , xmiBefore);
	}

	@Test
	public void nameString() throws IOException {
		testType = "name"; // String - eString
		changeSetExistingAndTest("firstEntity1" );
		changeUnsetExisting();
	}

	@Test
	public void eBoolean() throws IOException {
		testType = "eBoolean";
		changeSetExistingAndTest((boolean) false);
		changeUnsetExisting();
	}
	
	@Test
	public void eBooleanObject() throws IOException {
		testType = "eBooleanObject";
		changeSetExistingAndTest((Boolean) false);
		changeUnsetExisting();
	}
	
	@Test
	public void eByte() throws IOException {
		testType = "eByte";	
		changeSetExistingAndTest((byte) 126);
		changeUnsetExisting();
	}
	
	@Test
	public void eByteObject() throws IOException {
		testType = "eByteObject";			
		changeSetExistingAndTest((Byte) ((byte) 126));
		changeUnsetExisting();
	}
	
	@Test
	public void eChar() throws IOException {
		testType = "eChar";		
		changeSetExistingAndTest((char) 'Z');
		changeUnsetExisting();
	}
	
	@Test
	public void eCharacterObject() throws IOException {
		testType = "eCharacterObject";
		changeSetExistingAndTest((Character) 'X');
		changeUnsetExisting();
	}
	
	@Test
	public void eDouble() throws IOException {
		testType = "eDouble";
		changeSetExistingAndTest((double) 3.0);
		changeUnsetExisting();
	}
	
	@Test
	public void eDoubleObject() throws IOException {
		testType = "eDoubleObject";
		changeSetExistingAndTest((Double) ((double)30.0));
		changeUnsetExisting();
	}
	
	@Test
	public void eFloat() throws IOException {
		testType = "eFloat";
		changeSetExistingAndTest((float) 2.0);
		changeUnsetExisting();
	}
	
	@Test
	public void eFloatObject() throws IOException {
		testType = "eFloatObject";
		changeSetExistingAndTest((Float) ((float)3.0));
		changeUnsetExisting();
	}
	
	@Test
	public void eInt() throws IOException {
		testType = "eInt";
		changeSetExistingAndTest((int) 2);
		changeUnsetExisting();
	}
	
	@Test
	public void eIntegerObject() throws IOException {
		testType = "eIntegerObject";
		changeSetExistingAndTest((Integer) 3);
		changeUnsetExisting();
	}
	
	@Test
	public void eLong() throws IOException {
		testType = "eLong";
		changeSetExistingAndTest((long) 2);
		changeUnsetExisting();
	}
	
	@Test
	public void eLongObject() throws IOException {
		testType = "eLongObject";
		changeSetExistingAndTest((Long) ((long) 3));
		changeUnsetExisting();
	}
	
	@Test
	public void eShort() throws IOException {
		testType = "eShort";
		changeSetExistingAndTest((short) 2);
		changeUnsetExisting();
	}
	
	@Test
	public void eShortObject() throws IOException {
		testType = "eShortObject";
		changeSetExistingAndTest((Short) ((short) 3));
		changeUnsetExisting();
	}
	
	@Test
	public void eDate() throws IOException {
		testType = "eDate";
		changeSetExistingAndTest((Date) Date.from(Instant.now()));
		changeUnsetExisting();
	}

	public void changeUnsetExisting() throws IOException {
		setUpWorkingTTL();
		EAttribute attribute = getEAttribute(rdfEntity, testType);
		if(attribute.getClass().equals(char.class)) {
			rdfEntity.eUnset(getEAttribute(rdfEntity, testType));
			xmiEntity.eUnset(getEAttribute(xmiEntity, testType));
		}
		else {
			rdfEntity.eUnset(getEAttribute(rdfEntity, testType));
			xmiEntity.eUnset(getEAttribute(xmiEntity, testType));
		}
		saveReloadAndTest("change Existing");
	}

	public void changeUnSetThenSet(Object value) throws IOException {
		setUpWorkingTTL();
		if(value.getClass().equals(char.class)) {
			rdfEntity.eUnset(getEAttribute(rdfEntity, testType));
			xmiEntity.eUnset(getEAttribute(xmiEntity, testType));
		}
		else {
			rdfEntity.eUnset(getEAttribute(rdfEntity, testType));
			xmiEntity.eUnset(getEAttribute(xmiEntity, testType));
		}

		saveReload();
		rdfEntity = getRdfEntityForAttributeTest(rdf);
		xmiEntity = getRdfEntityForAttributeTest(xmiAfter);

		if (value.getClass().equals(char.class)) {
			rdfEntity.eSet(getEAttribute(rdfEntity, testType), (Character) value);
			xmiEntity.eSet(getEAttribute(xmiEntity, testType), (Character) value);
		}
		else {
			rdfEntity.eSet(getEAttribute(rdfEntity, testType), value);
			xmiEntity.eSet(getEAttribute(xmiEntity, testType), value);
		}

		saveReloadAndTest("unSet existing");
	}

	public void changeSetExistingAndTest (Object value) throws IOException {
		setUpWorkingTTL();

		if (value.getClass().equals(char.class)) {
			rdfEntity.eSet(getEAttribute(rdfEntity, testType), (Character) value);
			xmiEntity.eSet(getEAttribute(xmiEntity, testType), (Character) value);
		}
		else {
			rdfEntity.eSet(getEAttribute(rdfEntity, testType), value);
			xmiEntity.eSet(getEAttribute(xmiEntity, testType), value);
		}

		saveReloadAndTest("unset existing");
	}

	public void saveReload() throws IOException {
		rdf.getResources().get(0).save(null);
		saveBeforeXmi(xmiModelAfter);
		xmiAfter = getXMIResourceSet(xmiModelAfter, metaModel);
		rdf = getGraphResourceSet(rdfGraphModel, metaModel);
	}

	public void saveReloadAndTest(String changeType) throws IOException {
		rdf.getResources().get(0).save(null);
		saveBeforeXmi(xmiModelAfter);

		xmiAfter = getXMIResourceSet(xmiModelAfter, metaModel);
		rdf = getGraphResourceSet(rdfGraphModel, metaModel);

		equivalentModels(testType + " After " + changeType, rdf , xmiAfter);
	}
		
	public void saveBeforeXmi (File destinationFile) throws FileNotFoundException, IOException {
		if (destinationFile.exists()) {
			destinationFile.delete();
		}
		if (destinationFile.createNewFile()) {
			try (OutputStream destinationStream = new FileOutputStream(destinationFile)) {
				xmiBefore.getResources().get(0).save(destinationStream, null);
				//delaySeconds(1);
			}
		} else {
			System.err.println("Failed to save XMI : " + destinationFile.toPath().toString());
		}
	}

	protected void copyFile(File source, File destination) throws FileNotFoundException, IOException {
		Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
	}

	protected EObject getRdfEntityForAttributeTest (ResourceSet rdf) {
		Resource rdfResource = rdf.getResources().get(0);
		EObject rdfModel = rdfResource.getContents().get(0);
		EObject rdfEntity = rdfModel.eContents().get(0);
		return rdfEntity;
	}

	public EAttribute getEAttribute(EObject eObject, String AttributeName ) {
		EList<EAttribute> attributes = eObject.eClass().getEAttributes();
		for (EAttribute eAttribute : attributes) {
			if(eAttribute.getName().equals(AttributeName)) {
				return eAttribute;
			}
		}
		return null;
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

	protected void loadFileIntoResourceSet(File file, ResourceSet rs) throws IOException {
		Resource resource = rs.createResource(URI.createFileURI(file.getAbsolutePath()));
		resource.load(null);
		rs.getResources().add(resource);
	}
	
	protected ResourceSet getMetaModelResourceSet (File file) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		loadFileIntoResourceSet(file, resourceSet);
		return resourceSet;
	}
	
	protected ResourceSet getGraphResourceSet(File file, ResourceSet rsMetamodels) throws IOException {
		ResourceSet rsRDF = new ResourceSetImpl();
		registerEPackages(rsMetamodels, rsRDF);
		loadFileIntoResourceSet(file, rsRDF);
		return rsRDF;
	}
	
	protected ResourceSet getXMIResourceSet(File file, ResourceSet rsMetamodels) throws IOException {
		if (file.exists()) {
			ResourceSet rsXmi = new ResourceSetImpl();
			registerEPackages(rsMetamodels, rsXmi);
			loadFileIntoResourceSet(file, rsXmi);
			return rsXmi;
		} else {
			System.err.println("Missing file : " + file.toPath().toString());
			return null;
		}
	}
	
	protected void assertNoDifferences(String testLabel, Comparison cmp) {
		StringBuilder report = new StringBuilder();
		if (cmp.getDifferences().isEmpty()) {
			return;
		}

		report.append("Differences were found in " + testLabel + ": ");
		for (Diff diff : cmp.getDifferences()) {
			report.append("\n   - " + diff);
		}
		fail("Differences were reported: see error messages\n" + report);
	}

	protected void equivalentModels (String testLabel, ResourceSet rsXmi, ResourceSet rsRDF) {
		EMFCompare compareEngine = EMFCompare.builder().build();
		final IComparisonScope scope = new DefaultComparisonScope(rsXmi, rsRDF, null);
		final Comparison cmp = compareEngine.compare(scope);
		assertNoDifferences(testLabel, cmp);
	}
}
