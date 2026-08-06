/*******************************************************************************
 * Copyright (c) 2016 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf;

import static org.junit.Assert.*;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.eol.execute.introspection.IPropertySetter;
import org.junit.Before;
import org.junit.Test;

public class EmfModelIsPropertySetTests {

	private EmfModel model;

	@Before
	public void setUp() throws Exception {
		model = new EmfModel();
		model.setMetamodelFileUri(URI.createURI(FileUtil.getFileStandalone("DefaultValue.ecore", EmfModelIsPropertySetTests.class).toURI().toString()));
		model.setModelFile("model/dummy.model");
		model.setReadOnLoad(false);
		model.setStoredOnDisposal(false);
		model.load();
	}
	
	@Test
	public void defaultValueTest() throws Exception {
		EObject eob = model.createInstance("DVTestModel");

		final String pname = "count";
		assertTrue("The type should know about 'count'", model.knowsAboutProperty(eob, pname));
		assertEquals("The instance should have a default value for 'count'", 0, model.getPropertyGetter().invoke(eob, pname, null));
		assertFalse("The instance should know that 'count' has not been explicitly set", model.isPropertySet(eob, pname));

		final IPropertySetter setter = model.getPropertySetter();
		setter.invoke(eob, "count", 0, null);
		assertEquals("count should be 0", 0, model.getPropertyGetter().invoke(eob, pname, null));
		assertFalse("count is equal to the default, so EMF should still report it as unset", model.isPropertySet(eob, pname));

		setter.invoke(eob, "count", 1, null);
		assertEquals("count should be 1", 1, model.getPropertyGetter().invoke(eob, pname, null));
		assertTrue("count is not equal to the default, so it is now reported as set", model.isPropertySet(eob, pname));

		setter.invoke(eob, "count", null, null);
		assertEquals("count should be back to the default 0 after being unset", 0, model.getPropertyGetter().invoke(eob, pname, null));
		assertFalse("count should have been unset", model.isPropertySet(eob, pname));
	}
}
