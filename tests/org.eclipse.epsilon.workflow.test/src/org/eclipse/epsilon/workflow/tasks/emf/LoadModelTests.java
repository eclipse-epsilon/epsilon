/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.emf;

import static org.junit.Assert.assertEquals;
import java.io.File;
import org.apache.tools.ant.Project;
import org.eclipse.emf.common.util.URI;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.workflow.tasks.ModelSpy;
import org.eclipse.epsilon.workflow.tasks.WorkflowTaskTestCase;
import org.junit.Test;

public class LoadModelTests extends WorkflowTaskTestCase {

	@Test
	public void nameIsMappedToName() throws Exception {
		runLoadModelStatementWithProperty("name", "MyModel");
		assertEquals("MyModel", valueOfPropertyInEmfModel(EmfModel.PROPERTY_NAME));
	}
	
	@Test
	public void readIsMappedToReadOnLoad() throws Exception {
		runLoadModelStatementWithProperty("read", "false");
		assertEquals("false", valueOfPropertyInEmfModel(EmfModel.PROPERTY_READONLOAD));
	}
	
	@Test
	public void metamodelFileIsConvertedToUri() throws Exception {
		runLoadModelStatementWithProperty("metamodelfile", "My.ecore");
		assertEquals(locationOfRelativePath("My.ecore").toString(), valueOfPropertyInEmfModel(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI));
	}
	
	@Test
	public void modelFileIsConvertedToUri() throws Exception {
		runLoadModelStatementWithProperty("modelFile", "My.model");
		assertEquals(locationOfRelativePath("My.model").toString(), valueOfPropertyInEmfModel(EmfModel.PROPERTY_MODEL_URI));
	}

	private URI locationOfRelativePath(String relativePath) {
		final String absolutePath = new File(antFile.getParent(), relativePath).getAbsolutePath();
		return URI.createFileURI(absolutePath);
	}
	

	private void runLoadModelStatementWithProperty(String propertyName, String propertyValue) throws Exception {
		runTask("<epsilon.emf.loadModel " + propertyName + "=\"" + propertyValue + "\"/>");
	}
	
	private String valueOfPropertyInEmfModel(String emfModelPropertyName) {
		return LoadModelWithSpy.spy.properties.getProperty(emfModelPropertyName);
	}

	@Override
	protected void addTaskDefinitionsTo(Project project) {
		project.addTaskDefinition("epsilon.emf.loadModel", LoadModelWithSpy.class);
	}	
	
	/**
	 * An extension to LoadModelTask that stubs out the createModel
	 * method to return a spy (which can be interrogated by the tests)
	 * rather than a real model. 
	 */
	public static class LoadModelWithSpy extends LoadEmfModelTask {
		
		public static ModelSpy spy;
		
		public LoadModelWithSpy() {}
		
		@Override
		protected EmfModel createEmfModel() {
			spy = new ModelSpy("EMF");
			return spy;
		}
	}
}
