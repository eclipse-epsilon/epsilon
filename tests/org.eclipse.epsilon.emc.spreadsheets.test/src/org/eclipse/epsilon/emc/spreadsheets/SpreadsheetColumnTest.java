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
import org.junit.Test;

public class SpreadsheetColumnTest {

	@Test
	public void testIsIdentifiablyByNull() {
		SpreadsheetModel model = new ConcreteModel();
		SpreadsheetWorksheet worksheet = new ConcreteWorksheet(model, "WORKSHEET", true);
		SpreadsheetColumn column = new ConcreteColumn(worksheet, 1);
		assertTrue(!column.isIdentifiableBy(null));
	}

	@Test
	public void testIsIdentifiablyByName() {
		String name = "NAME";
		SpreadsheetModel model = new ConcreteModel();
		SpreadsheetWorksheet worksheet = new ConcreteWorksheet(model, "WORKSHEET", true);
		SpreadsheetColumn column = new ConcreteColumn(worksheet, 1);
		column.setName(name);
		assertTrue(column.isIdentifiableBy(name));
	}

	@Test
	public void testIsIdentifiablyByNameNotEqual() {
		String name = "NAME";
		String alias = "NAMEa";
		SpreadsheetModel model = new ConcreteModel();
		SpreadsheetWorksheet worksheet = new ConcreteWorksheet(model, "WORKSHEET", true);
		SpreadsheetColumn column = new ConcreteColumn(worksheet, 1);
		column.setName(name);
		assertTrue(!column.isIdentifiableBy(alias));
	}

	@Test
	public void testIsIdentifiablyByNameAliasIndex() {
		String name = "NAME";
		String alias = "NAMEa";
		SpreadsheetModel model = new ConcreteModel();
		SpreadsheetWorksheet worksheet = new ConcreteWorksheet(model, "WORKSHEET", true);
		SpreadsheetColumn column = new ConcreteColumn(worksheet, 1);
		column.setName(name);
		column.setAlias(alias);
		assertTrue(column.isIdentifiableBy(name));
		assertTrue(column.isIdentifiableBy(alias));
		assertTrue(column.isIdentifiableBy(column.getPrefixedIndex()));
		assertTrue(!column.isIdentifiableBy(column.getPrefixedIndex() + "2"));
	}

	@Test
	public void testGetIdentifier() {
		String name = "NAME";
		String alias = "NAMEa";
		SpreadsheetModel model = new ConcreteModel();
		SpreadsheetWorksheet worksheet = new ConcreteWorksheet(model, "WORKSHEET", true);
		SpreadsheetColumn column = new ConcreteColumn(worksheet, 1);
		column.setName(name);
		assertTrue(column.getIdentifier().equals(name));
		assertTrue(!column.getIdentifier().equals(name + "s"));
		column.setName(null);
		column.setAlias(alias);
		assertTrue(column.getIdentifier().equals(alias));
		assertTrue(!column.getIdentifier().equals(alias + "s"));
		column.setAlias(null);
		assertTrue(column.getIdentifier().equals(column.getPrefixedIndex()));
	}

	// @Test
	// public void testIndexPrefixing()
	// {
	// SpreadsheetModel model = new ConcreteModel();
	// SpreadsheetWorksheet worksheet = new ConcreteWorksheet(model, "WORKSHEET",
	// true);
	// SpreadsheetColumn column = new ConcreteColumn(worksheet, 1);
	// assertTrue(column.isValidPrefixedIndex(column.getPrefixedIndex()));
	// assertTrue(worksheet == column.getWorksheet());
	// }

	@Test
	public void testWorksheetNull() {
		try {
			new ConcreteColumn(null, 1);
			fail();
		}
		catch (IllegalArgumentException e) {
			assert (true);
		}
	}

	@Test
	public void testNegativeColumnIndex() {
		SpreadsheetModel model = new ConcreteModel();
		SpreadsheetWorksheet worksheet = new ConcreteWorksheet(model, "WORKSHEET", true);
		try {
			new ConcreteColumn(worksheet, -1);
			fail();
		}
		catch (IllegalArgumentException e) {
			assert (true);
		}
	}

}
