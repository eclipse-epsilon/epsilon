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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.epsilon.emc.json.Contained;
import org.eclipse.epsilon.emc.json.JsonModel;
import org.eclipse.epsilon.emc.json.JsonModelArray;
import org.eclipse.epsilon.emc.json.JsonModelObject;
import org.eclipse.epsilon.eol.EolEvaluator;
import org.eclipse.epsilon.eol.types.EolSequence;
import org.junit.Before;
import org.junit.Test;

public class JSONModelReadTests {

	protected JsonModel model;
	protected EolEvaluator evaluator;

	@Before
	public void setUp() throws Exception {
		model = new JsonModel();
		model.setName("M");
		model.setReadOnLoad(true);
		model.setFile(new File(JSONModelTestSuite.RESOURCES_BASE_FOLDER + "/commits.json"));

		assertFalse(model.isLoaded());
		model.load();
		assertTrue(model.isLoaded());

		for (Object o : model.allContents()) {
			if (o instanceof Contained) {
				((Contained) o).isContainedBy(model);
			}
		}

		evaluator = new EolEvaluator(model);
	}

	@Test
	public void rootIsArrayWithOneElement() {
		assertEquals(1, evaluator.evaluate("M.root.size()"));
	}

	@Test
	public void authorName() {
		assertEquals("Louis Rose", evaluator.evaluate("M.root.get(0).commit.author.name"));
	}

	@Test
	public void parentKeys() {
		assertEquals(sequence(1L, 23L),
			evaluator.evaluate("M.root.get(0).parents.collect(e|e.key)"));
	}

	@Test
	public void keySetMethod() {
		// A JsonObject is a Map, so we can use the usual Map methods. It's important
		// to note that JsonObject method names take precedence over actual property
		// names: when there is a property that has the same name as one of these methods,
		// we can say we want that property with a "p_" prefix (e.g. x.p_keySet).

		assertEquals(9, evaluator.evaluate("M.root.get(0).keySet().size()"));
	}

	@Test
	public void keySetProperty() {
		assertEquals(3, evaluator.evaluate("M.root.get(0).p_keySet.size()"));
	}

	@Test
	public void nonExistingProperty() {
		assertTrue((Boolean) evaluator.evaluate("M.root.nonExisting.isUndefined()"));
	}

	@Test
	public void nonExistingPrefixedProperty() {
		assertFalse((Boolean) evaluator.evaluate("M.root.p_nonExisting.isDefined()"));
	}

	@Test
	public void typeNameOfObject() {
		assertTrue(model.hasType(JsonModel.JSON_OBJECT_TYPE));
		assertEquals(JsonModel.JSON_OBJECT_TYPE, model.getTypeNameOf(new JsonModelObject()));
	}

	@Test
	public void typeNameOfArray() {
		assertTrue(model.hasType(JsonModel.JSON_ARRAY_TYPE));
		assertEquals(JsonModel.JSON_ARRAY_TYPE, model.getTypeNameOf(new JsonModelArray()));
	}

	@Test
	public void allObjects() {
		assertEquals(7, evaluator.evaluate("M!JSONObject.all.size()"));
	}

	@Test
	public void allArrays() {
		assertEquals(3, evaluator.evaluate("M!JSONArray.all.size()"));
	}

	@Test
	public void allContents() {
		assertEquals(10, evaluator.evaluate("M.allContents.size()"));
	}

	@SafeVarargs
	protected final <T> EolSequence<T> sequence(T... values) {
		EolSequence<T> seq = new EolSequence<>();
		for (T value : values) {
			seq.add(value);
		}
		return seq;
	}
}
