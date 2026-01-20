/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.json.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.epsilon.emc.json.JsonModel;
import org.eclipse.epsilon.emc.json.JsonModelObject;
import org.junit.Test;

/**
 * Tests for creating a new instance of a JSON object/array within a model,
 * before it is "properly" put into the model's containment tree.
 */
public class JSONCreateInstanceTests {

	@Test
	public void createInstance() throws Exception {
		try (JsonModel model = new JsonModel(); JsonModel model2 = new JsonModel()) {
			JsonModelObject o = (JsonModelObject) model.createInstance(JsonModel.JSON_OBJECT_TYPE);
			assertTrue(
				"Initially, the object belongs to the creator",
				model.owns(o));
			assertFalse(
				"Initially, the object does not belong to any other JSON models besides its creator",
				model2.owns(o));

			model2.setRoot(o);
			assertFalse(
				"After the object is within the second model containment hierarchy, it is no longer owned by its creator",
				model.owns(o));
			assertTrue(
				"After the object is within the second model containment hierarchy, it is owned by the second model",
				model2.owns(o));
		}
	}

}
