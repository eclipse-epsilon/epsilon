/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import static org.junit.Assert.assertEquals;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.eclipse.epsilon.eol.models.IModel;
import org.junit.Test;

/**
 * Tests that check whether instances of the loadModel ANT task produce
 * the data needed to create a model with EMC. 
 */
public class LoadModelTaskTests extends WorkflowTaskTestCase {
	
	@Test
	public void nameAndTypeAreSetFromAttributeValue() throws Exception {
		final String loadModelStatement = "<epsilon.loadModel name=\"M\" type=\"EMF\"/>";
		
		runTask(loadModelStatement);
		
		assertEquals("M",   LoadModelTaskWithSpy.spy.name);
		assertEquals("EMF", LoadModelTaskWithSpy.spy.type);
	}
	
	@Test
	public void propertiesAreSetFromContainedParameters() throws Exception {
		final String loadModelStatement = "<epsilon.loadModel>" + '\n' +
		                                  "	<parameter name=\"metamodelUri\" value=\"http://www.eclipse.org/uml2/2.1.0/UML\"/>" + '\n' + 
		                                  "	<parameter name=\"readOnLoad\" value=\"true\"/>"    + '\n' + 
		                                  "</epsilon.loadModel>";
		
		runTask(loadModelStatement);
		
		final Set<Entry<Object, Object>>      properties       = LoadModelTaskWithSpy.spy.properties.entrySet();
		final Iterator<Entry<Object, Object>> propertyIterator = properties.iterator();
		
		final Entry<Object, Object> firstProperty  = propertyIterator.next();
		final Entry<Object, Object> secondProperty = propertyIterator.next();
		
		
		assertEquals(2,            properties.size());		
		
		assertEquals("metamodelUri",                          firstProperty.getKey());
		assertEquals("http://www.eclipse.org/uml2/2.1.0/UML", firstProperty.getValue());
		
		assertEquals("readOnLoad", secondProperty.getKey());
		assertEquals("true",       secondProperty.getValue());		
	}

	@Override
	protected void addTaskDefinitionsTo(Project project) {
		project.addTaskDefinition("epsilon.loadModel", LoadModelTaskWithSpy.class);
	}	
	
	/**
	 * An extension to LoadModelTask that stubs out the createModel
	 * method to return a spy (which can be interrogated by the tests)
	 * rather than a real model. 
	 */
	public static class LoadModelTaskWithSpy extends LoadModelTask {
		
		public static ModelSpy spy;
		
		public LoadModelTaskWithSpy() {}
		
		@Override
		protected IModel createModel(String type) throws BuildException {
			spy = new ModelSpy(type);
			return spy;
		}
	}
}
