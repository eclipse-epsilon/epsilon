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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.emc.json.JsonModel;
import org.eclipse.epsilon.emc.json.JsonModelArray;
import org.eclipse.epsilon.emc.json.JsonModelObject;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for tracking who contains a given JSON object or array.
 */
public class JSONContainmentTests {

	private JsonModelObject parentObject;
	private JsonModelArray parentArray;
	private JsonModelObject child;

	@Before
	public void setUp() {
		parentObject = new JsonModelObject();
		parentArray = new JsonModelArray();
		child = new JsonModelObject();
	}

	@Test
	public void objectsHaveNoContainerByDefault() {
		assertTrue(parentObject.getContainers().isEmpty());
	}

	@Test
	public void arraysHaveNoContainerByDefault() {
		assertTrue(parentArray.getContainers().isEmpty());
	}

	@Test
	public void objectDoesNotContainItself() {
		assertFalse(child.isContainedBy(child));
	}

	@Test
	public void objectsAsRoot() {
		JsonModel model = new JsonModel();
	
		assertFalse(parentObject.isContainedBy(model));
		model.setRoot(parentObject);
		assertTrue(parentObject.isContainedBy(model));
	}

	@Test
	public void arraysAsRoot() {
		JsonModel model = new JsonModel();
		model.setRoot(parentArray);
		assertTrue(parentArray.isContainedBy(model));
	}

	@Test
	public void objectPutRemove() {
		parentObject.put("x", child);
		assertTrue(child.isContainedBy(parentObject));

		parentObject.remove("x");
		assertFalse(child.isContainedBy(parentObject));
	}

	@Test
	public void objectPutOverwrite() {
		parentObject.put("x", child);

		JsonModelObject child2 = new JsonModelObject();
		parentObject.put("x", child2);

		assertTrue(child2.isContainedBy(parentObject));
		assertFalse(child.isContainedBy(parentObject));
	}

	@Test
	public void objectClear() {
		parentObject.put("x", child);
		parentObject.clear();
		assertFalse(child.isContainedBy(parentObject));
	}

	@Test
	public void objectPutAllRemoveKeyValue() {
		parentObject.putAll(Collections.singletonMap("x", child));
		assertTrue(child.isContainedBy(parentObject));

		parentObject.remove("x", child);
		assertFalse(child.isContainedBy(parentObject));
	}

	@Test
	public void objectPutIfAbsent() {
		parentObject.putIfAbsent("x", child);
		assertTrue(child.isContainedBy(parentObject));
	}

	@Test
	public void arrayAddRemove() {
		parentArray.add(child);
		assertTrue(child.isContainedBy(parentArray));

		parentArray.remove(child);
		assertFalse(child.isContainedBy(parentArray));
	}

	@Test
	public void arrayAddAllClear() {
		parentArray.addAll(Collections.singleton(child));
		assertTrue(child.isContainedBy(parentArray));

		parentArray.clear();
		assertFalse(child.isContainedBy(parentArray));
	}

	@Test
	public void arrayAddAllAtPosition() {
		// For addAll(), we need to check that we get the right insertion *and*
		// that the containment is updated.
		JsonModelObject child2 = new JsonModelObject();
		JsonModelObject child3 = new JsonModelObject();

		parentArray.add(child);
		parentArray.addAll(0, Arrays.asList(child2, child3));
		final List<JsonModelObject> expectedList = Arrays.asList(child2, child3, child);
		assertEquals(expectedList, parentArray);
		expectedList.forEach(e -> assertTrue(e.isContainedBy(parentArray)));
	}

	@Test
	public void arrayRemoveAllRetainAll() {
		JsonModelObject child2 = new JsonModelObject();
		JsonModelObject child3 = new JsonModelObject();

		/*
		 * We have to make the three children distinct from the point of view of the
		 * equals() method, because retainAll() is based on equals().
		 */
		child.put("x", 1);
		child2.put("x", 2);
		child3.put("x", 3);

		parentArray.add(child);
		parentArray.add(child2);
		parentArray.add(child3);

		assertTrue(parentArray.removeAll(Collections.singleton(child2)));
		assertTrue(child.isContainedBy(parentArray));
		assertFalse(child2.isContainedBy(parentArray));
		assertTrue(child3.isContainedBy(parentArray));

		assertTrue(parentArray.retainAll(Collections.singleton(child3)));
		assertEquals(Collections.singletonList(child3), parentArray);
		assertTrue(child3.isContainedBy(parentArray));
		assertFalse(child.isContainedBy(parentArray));
	}

	@Test
	public void arraySet() {
		JsonModelObject child2 = new JsonModelObject();

		parentArray.add(child);
		parentArray.set(0, child2);

		assertEquals(Collections.singletonList(child2), parentArray);
		assertFalse(child.isContainedBy(parentArray));
		assertTrue(child2.isContainedBy(parentArray));
	}

	@Test
	public void arrayAddAt() {
		JsonModelObject child2 = new JsonModelObject();

		parentArray.add(0, child);
		parentArray.add(0, child2);

		assertEquals(Arrays.asList(child2, child), parentArray);
		assertTrue(child.isContainedBy(parentArray));
		assertTrue(child2.isContainedBy(parentArray));
	}

	@Test
	public void arrayRemoveAt() {
		parentArray.add(child);
		parentArray.remove(0);

		assertTrue(parentArray.isEmpty());
		assertFalse(child.isContainedBy(parentArray));
	}

	@Test
	public void objectReuse() {
		JsonModelObject parentObject2 = new JsonModelObject();

		parentObject.put("x", child);
		parentObject2.put("x", child);

		assertTrue(child.isContainedBy(parentObject));
		assertTrue(child.isContainedBy(parentObject2));

		parentObject.remove("x");
		assertFalse(child.isContainedBy(parentObject));

		parentObject2.clear();
		assertFalse(child.isContainedBy(parentObject2));
	}

	@Test
	public void objectDeepClone() {
		JsonModelObject parentObject2 = new JsonModelObject();

		parentObject.put("x", child);
		parentObject2.put("x", JsonModel.deepClone(child));

		assertTrue(child.isContainedBy(parentObject));
		assertFalse(child.isContainedBy(parentObject2));
	}
}
