/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.spreadsheets;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.junit.Test;

public class SpreadsheetModelTest {

	@Test
	public void testGetEnumerationValue() {
		SpreadsheetModel model = new ConcreteModel();
		try {
			model.getEnumerationValue(null, null);
			fail("Method has not been implemented");
		}
		catch (UnsupportedOperationException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testGetElementById() {
		SpreadsheetModel model = new ConcreteModel();
		try {
			model.getElementById("");
			fail("Method has not been implemented");
		}
		catch (UnsupportedOperationException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testElementId() {
		SpreadsheetModel model = new ConcreteModel();
		try {
			model.getElementId("");
			fail("Method has not been implemented");
		}
		catch (UnsupportedOperationException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testSetElementId() {
		SpreadsheetModel model = new ConcreteModel();
		try {
			model.setElementId(null, null);
			fail("Method has not been implemented");
		}
		catch (UnsupportedOperationException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testStoreString() {
		SpreadsheetModel model = new ConcreteModel();
		try {
			model.store("");
			fail("Method has not been implemented");
		}
		catch (UnsupportedOperationException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testStore() {
		SpreadsheetModel model = new ConcreteModel();
		try {
			model.store();
			fail("Method has not been implemented");
		}
		catch (UnsupportedOperationException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testDeleteNotRow() {
		SpreadsheetModel model = new ConcreteModel();
		try {
			model.deleteElement("NOT A ROW");
			fail("Should not be able to delete string objects");
		}
		catch (EolRuntimeException e) {
			assertTrue(true);
		}
	}

}
