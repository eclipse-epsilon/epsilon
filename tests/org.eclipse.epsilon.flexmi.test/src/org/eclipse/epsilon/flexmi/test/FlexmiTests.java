/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.EolEvaluator;
import org.eclipse.epsilon.flexmi.FlexmiResource;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;
import org.junit.Before;

public abstract class FlexmiTests {
	
	@Before
	public void setup()  throws Exception {
		// Load imported files
		FileUtil.getFileStandalone("models/templates/subdir/template-importing-eol.flexmi", FlexmiTests.class);
		FileUtil.getFileStandalone("models/templates/imported-template.flexmi", FlexmiTests.class);
		FileUtil.getFileStandalone("models/templates/imported-template.yaml", FlexmiTests.class);
		FileUtil.getFileStandalone("models/templates/imported-egl-templates.flexmi", FlexmiTests.class);
		FileUtil.getFileStandalone("models/templates/imported-egl-templates.yaml", FlexmiTests.class);
		FileUtil.getFileStandalone("models/templates/imported-templates.flexmi", FlexmiTests.class);
		FileUtil.getFileStandalone("models/templates/library.eol", FlexmiTests.class);
	}
	
	protected FlexmiResource loadResource(String filename) throws Exception {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new FlexmiResourceFactory());
		resourceSet.getPackageRegistry().put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
		File resourceFile;
		try {
			resourceFile = FileUtil.getFileStandalone("models/" + filename, FlexmiTestSuite.class);
		}
		catch (NullPointerException ex) {
			// Let tests for invalid paths have their joy
			resourceFile = new File("models/" + filename);
		}
		FlexmiResource resource = (FlexmiResource) resourceSet.createResource(URI.createURI(resourceFile.toURI().toString()));
		resource.load(null);
		return resource;
	}
	
	protected FlexmiResource loadResource(String filename, String... metamodels) throws Exception {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new FlexmiResourceFactory());
		resourceSet.getPackageRegistry().put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
		
		for (String metamodel : metamodels) {
			EmfUtil.register(URI.createURI(FileUtil.getFileStandalone(
				"models/" + metamodel, FlexmiTestSuite.class).toURI().toString()), EPackage.Registry.INSTANCE
			);
		}
		
		File resourceFile = FileUtil.getFileStandalone("models/" + filename, FlexmiTestSuite.class);
		FlexmiResource resource = (FlexmiResource) resourceSet.createResource(URI.createURI(resourceFile.toURI().toString()));
		resource.load(null);
		return resource;
	}
	
	protected boolean yamlModelExists(String modelFileName) {
		if (modelFileName.endsWith(".yaml")) return false;
		return FlexmiTestSuite.class.getResourceAsStream("models/" + getYamlModelFileName(modelFileName)) != null;
	}
	
	protected String getYamlModelFileName(String modelFileName) {
		return modelFileName.replace(".flexmi", ".yaml");
	}
	
	protected void assertEval(String expression, Object result, String modelFileName) throws Exception {
		InMemoryEmfModel model = new InMemoryEmfModel(loadResource(modelFileName));
		EolEvaluator evaluator = new EolEvaluator(model);
		assertEquals(result, evaluator.evaluate(expression));
		if (yamlModelExists(modelFileName)) {
			assertEval(expression, result, getYamlModelFileName(modelFileName));
		}
	}
	
	protected void assertWarning(String message, int line, String modelFileName) throws Exception {
		assertTrue(loadResource(modelFileName).getWarnings().stream().
				anyMatch(w -> w.getMessage().contains(message) && w.getLine() == line));
	}
	
	protected void assertNoWarnings(String model, String... metamodels) throws Exception {
		FlexmiResource resource = loadResource(model, metamodels);
		for (Diagnostic d : resource.getWarnings()) System.out.println(d.getMessage());
		assertEquals(0, resource.getWarnings().size());
		if (yamlModelExists(model)) {
			assertNoWarnings(getYamlModelFileName(model), metamodels);
		}
	}
}
