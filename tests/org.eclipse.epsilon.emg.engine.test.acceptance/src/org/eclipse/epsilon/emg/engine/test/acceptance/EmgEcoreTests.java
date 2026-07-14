package org.eclipse.epsilon.emg.engine.test.acceptance;

import java.util.Arrays;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emg.EmgModule;
import org.eclipse.epsilon.eol.EolModule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmgEcoreTests {
	
	protected EmfModel model;
	
	@Test
	public void testIssue223() throws Exception {
		run("issue223.emg");
		assertResult(1, "EClass.all.size()");
		assertResult("Name1", "EClass.all.first().name");
		assertResult(2, "EAttribute.all.size()");
		assertResult(Arrays.asList("Attribute1", "Attribute2"), "EClass.all.first().eStructuralFeatures.name");
	}
	
	@Before
	public void setUp() throws Exception {
		model = new EmfModel();
		model.setMetamodelUri(EcorePackage.eNS_URI);
		model.setModelFileUri(URI.createURI("file:/some.xmi"));
		
		model.setReadOnLoad(false);
		model.setStoredOnDisposal(false);
		model.load();
	}
	
	@After
	public void tearDown() throws Exception {
		model.dispose();
	}
	
	protected void run(String emg) throws Exception {
		EmgModule module = new EmgModule();
		module.parse(FileUtil.getFileStandalone((emg), EmgEcoreTests.class));
		
		module.getContext().getModelRepository().addModel(model);
		module.execute();
	}
	
	protected void assertResult(Object expected, String eol) throws Exception {
		EolModule module = new EolModule();
		module.parse("return " + eol + ";");
		module.getContext().getModelRepository().addModel(model);
		module.execute();
	}
	
}
