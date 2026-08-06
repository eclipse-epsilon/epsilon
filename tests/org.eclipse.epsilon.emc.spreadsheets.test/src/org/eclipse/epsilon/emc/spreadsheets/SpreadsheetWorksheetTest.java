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

import org.eclipse.epsilon.emc.spreadsheets.ISpreadsheetMetadata.SpreadsheetColumnMetadata;
import org.eclipse.epsilon.emc.spreadsheets.ISpreadsheetMetadata.SpreadsheetWorksheetMetadata;
import org.junit.Test;

public class SpreadsheetWorksheetTest {

	@Test
	public void testNoWorksheetName() {
		SpreadsheetModel model = new ConcreteModel();
		try {
			new ConcreteWorksheet(model, null, false);
		}
		catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			assertTrue(true);
		}
	}

	@Test
	public void testIsIdentifiableByNull() {
		SpreadsheetModel model = new ConcreteModel();
		SpreadsheetWorksheet worksheet = new ConcreteWorksheet(model, "NAME", false);
		assertTrue(!worksheet.isIdentifiablyBy(null));
	}

	@Test
	public void testIfWorksheetExists() {
		SpreadsheetModel model = new ConcreteModel();
		SpreadsheetWorksheet worksheet = new ConcreteWorksheet(model, "NAME", true);
		worksheet.checkThatWorksheetExists();
	}

	@Test
	public void testIfWorksheetExists_DoesNot() {
		SpreadsheetModel model = new ConcreteModel();
		SpreadsheetWorksheet worksheet = new ConcreteWorksheet(model, "NAME", false);
		try {
			worksheet.checkThatWorksheetExists();
			fail("Worksheet does not exist in spreadsheet");
		}
		catch (IllegalStateException e) {
			System.out.println(e.getMessage());
			assertTrue(true);
		}
	}

	@Test
	public void testAddIncorrectMetadata() {
		SpreadsheetModel model = new ConcreteModel();
		SpreadsheetWorksheet worksheet = new ConcreteWorksheet(model, "NAME", false);
		SpreadsheetWorksheetMetadata metadata = new SpreadsheetWorksheetMetadata();
		metadata.name = "NAME2";
		try {
			worksheet.addWorksheetMetadata(metadata);
			fail("Should not be able to add metadata of another worksheet");
		}
		catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			assertTrue(true);
		}
	}

	@Test
	public void testAddIncorrectColumnMetadata() {
		SpreadsheetModel model = new ConcreteModel();
		SpreadsheetWorksheet worksheet = new ConcreteWorksheet(model, "NAME", false);
		SpreadsheetColumnMetadata metadata = new SpreadsheetColumnMetadata();
		metadata.index = "";
		try {
			worksheet.addColumn(metadata);
			fail("Should not be able to add column where metadata is missing column index");
		}
		catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			assertTrue(true);
		}
	}

}
