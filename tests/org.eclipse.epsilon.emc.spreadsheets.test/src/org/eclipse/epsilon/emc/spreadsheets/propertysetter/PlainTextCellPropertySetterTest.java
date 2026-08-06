/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.spreadsheets.propertysetter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetColumn;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetConstants;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModel;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetPropertySetter;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.test.SharedTestMethods;
import org.eclipse.epsilon.emc.spreadsheets.test.TestModelFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PlainTextCellPropertySetterTest {
	private SpreadsheetModel model = null;

	public PlainTextCellPropertySetterTest(SpreadsheetModel model) {
		this.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception {
		String PATH_TO_FILE = "resources/propertysetter/PropertySetterTest.xlsx";
		String CONFIG = "resources/propertysetter/PlainTextCellPropertySetterTest.xml";
		return TestModelFactory.getModelsToTest("", PATH_TO_FILE, CONFIG, "PropertySetterTest");
	}

	@Test
	public void testSetNotManyCellToNull() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");

		SpreadsheetColumn column = model.getWorksheetByType("Sheet1").getColumn("c_2");
		assertTrue(!column.isMany());
		assertTrue(column.isNotMany());

		SpreadsheetRow row = SharedTestMethods.writeRow(model, "Sheet1", "1", column.getPrefixedIndex(), "v1, v2");

		assertTrue(row.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 1);

		final SpreadsheetPropertySetter setter = (SpreadsheetPropertySetter) model.getPropertySetter();
		setter.invoke(row, column.getPrefixedIndex(), null, null);

		assertTrue(row.getVisibleCellValue(column).equals("null"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 1);
	}

	@Test
	public void testSetNotManyCellToEmpty() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");

		SpreadsheetColumn column = model.getWorksheetByType("Sheet1").getColumn("c_2");

		SpreadsheetRow row = SharedTestMethods.writeRow(model, "Sheet1", "1", column.getPrefixedIndex(), "v1, v2");

		assertTrue(row.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 1);

		final SpreadsheetPropertySetter setter = (SpreadsheetPropertySetter) model.getPropertySetter();
		setter.invoke(row, column.getPrefixedIndex(), "", null);

		assertTrue(row.getVisibleCellValue(column).equals(""));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 0);
	}

	@Test
	public void testSetNotManyCellToSpace() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");

		SpreadsheetColumn column = model.getWorksheetByType("Sheet1").getColumn("c_2");

		SpreadsheetRow row = SharedTestMethods.writeRow(model, "Sheet1", "1", column.getPrefixedIndex(), "v1, v2");

		assertTrue(row.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 1);

		final SpreadsheetPropertySetter setter = (SpreadsheetPropertySetter) model.getPropertySetter();
		setter.invoke(row, column.getPrefixedIndex(), " ", null);

		assertTrue(row.getVisibleCellValue(column).equals(" "));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 1);
	}

	@Test
	public void testSetNotManyCellToValue() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");

		SpreadsheetColumn column = model.getWorksheetByType("Sheet1").getColumn("c_2");

		SpreadsheetRow row = SharedTestMethods.writeRow(model, "Sheet1", "1", column.getPrefixedIndex(), "v1, v2");

		assertTrue(row.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 1);

		final SpreadsheetPropertySetter setter = (SpreadsheetPropertySetter) model.getPropertySetter();
		setter.invoke(row, column.getPrefixedIndex(), "v2", null);

		assertTrue(row.getVisibleCellValue(column).equals("v2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 1);
	}

	@Test
	public void testSetNotManyCellToSeparatedValues() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");

		SpreadsheetColumn column = model.getWorksheetByType("Sheet1").getColumn("c_2");

		SpreadsheetRow row = SharedTestMethods.writeRow(model, "Sheet1", "1", column.getPrefixedIndex(), "v1, v2");

		assertTrue(row.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 1);

		final SpreadsheetPropertySetter setter = (SpreadsheetPropertySetter) model.getPropertySetter();
		setter.invoke(row, column.getPrefixedIndex(), "v1, v2", null);

		assertTrue(row.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 1);
	}

	@Test
	public void testSetNotManyCellOfMultipleRowsToValue() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");

		SpreadsheetColumn column = model.getWorksheetByType("Sheet1").getColumn("c_2");

		SpreadsheetRow row1 = SharedTestMethods.writeRow(model, "Sheet1", "1", column.getPrefixedIndex(), "v1, v2");
		SpreadsheetRow row2 = SharedTestMethods.writeRow(model, "Sheet1", "2", column.getPrefixedIndex(), "v3, v4");

		assertTrue(row1.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(row1.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(row2.getVisibleCellValue(column).equals("v3, v4"));
		assertTrue(row2.getAllVisibleCellValuesAsIs(column).size() == 1);

		List<SpreadsheetRow> rowsToEdit = Arrays.asList(row1, row2);

		final SpreadsheetPropertySetter setter = (SpreadsheetPropertySetter) model.getPropertySetter();
		setter.invoke(rowsToEdit, column.getPrefixedIndex(), "value1, value2, value3", null);

		assertTrue(row1.getVisibleCellValue(column).equals("value1, value2, value3"));
		assertTrue(row1.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(row2.getVisibleCellValue(column).equals("value1, value2, value3"));
		assertTrue(row2.getAllVisibleCellValuesAsIs(column).size() == 1);
	}

	@Test
	public void testSetNotManyCellToNonInt() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		SpreadsheetColumn column = model.getWorksheetByType("Sheet2").getColumn("c_2");

		SpreadsheetRow row = SharedTestMethods.writeRow(model, "Sheet2", "1", column.getPrefixedIndex(), "123");

		assertTrue(row.getVisibleCellValue(column).equals("123"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 1);

		final SpreadsheetPropertySetter setter = (SpreadsheetPropertySetter) model.getPropertySetter();
		setter.invoke(row, column.getPrefixedIndex(), "v1, v2", null);

		assertEquals(SpreadsheetConstants.DEFAULT_DT_INTEGER, row.getVisibleCellValue(column));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 1);
	}

	@Test
	public void testSetNotManyCellToOneNonIntValue() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		SpreadsheetColumn column = model.getWorksheetByType("Sheet2").getColumn("c_2");

		SpreadsheetRow row = SharedTestMethods.writeRow(model, "Sheet2", "1", column.getPrefixedIndex(), "123");

		assertTrue(row.getVisibleCellValue(column).equals("123"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 1);

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(row, column.getPrefixedIndex(), "v1", null);

		System.out.println(row.getVisibleCellValue(column).getClass() + "/" + row.getVisibleCellValue(column));
		
		assertEquals(SpreadsheetConstants.DEFAULT_DT_INTEGER, row.getVisibleCellValue(column));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 1);
	}

	@Test
	public void testSetManyCellToNull() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		SpreadsheetColumn column = model.getWorksheetByType("Sheet1").getColumn("c_3");
		assertTrue(column.isMany());
		assertTrue(!column.isNotMany());

		SpreadsheetRow row = SharedTestMethods.writeRow(model, "Sheet1", "1", column.getPrefixedIndex(), "v1, v2");

		assertTrue(row.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 2);

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(row, column.getPrefixedIndex(), null, null);

		assertTrue(row.getVisibleCellValue(column).equals("null"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 1);
	}

	@Test
	public void testSetManyCellToEmpty() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");

		SpreadsheetColumn column = model.getWorksheetByType("Sheet1").getColumn("c_3");

		SpreadsheetRow row = SharedTestMethods.writeRow(model, "Sheet1", "1", column.getPrefixedIndex(), "v1, v2");

		assertTrue(row.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 2);

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(row, column.getPrefixedIndex(), "", null);

		assertTrue(row.getVisibleCellValue(column).equals(""));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 0);
	}

	@Test
	public void testSetManyCellToSpace() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");

		SpreadsheetColumn column = model.getWorksheetByType("Sheet1").getColumn("c_3");

		SpreadsheetRow row = SharedTestMethods.writeRow(model, "Sheet1", "1", column.getPrefixedIndex(), "v1, v2");

		assertTrue(row.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 2);

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(row, column.getPrefixedIndex(), " ", null);

		assertTrue(row.getVisibleCellValue(column).equals(" "));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 1);
	}

	@Test
	public void testSetManyCellToValue() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");

		SpreadsheetColumn column = model.getWorksheetByType("Sheet1").getColumn("c_3");

		SpreadsheetRow row = SharedTestMethods.writeRow(model, "Sheet1", "1", column.getPrefixedIndex(), "v1, v2");

		assertTrue(row.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 2);

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(row, column.getPrefixedIndex(), "v1", null);

		assertTrue(row.getVisibleCellValue(column).equals("v1"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 1);
	}

	@Test
	public void testSetManyCellOfMultipleRowsToValue() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");

		SpreadsheetColumn column = model.getWorksheetByType("Sheet1").getColumn("c_3");

		SpreadsheetRow row1 = SharedTestMethods.writeRow(model, "Sheet1", "1", column.getPrefixedIndex(), "v1, v2");
		SpreadsheetRow row2 = SharedTestMethods.writeRow(model, "Sheet1", "2", column.getPrefixedIndex(), "v3, v4");

		assertTrue(row1.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(row1.getAllVisibleCellValuesAsIs(column).size() == 2);
		assertTrue(row2.getVisibleCellValue(column).equals("v3, v4"));
		assertTrue(row2.getAllVisibleCellValuesAsIs(column).size() == 2);

		List<SpreadsheetRow> rowsToEdit = Arrays.asList(row1, row2);

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(rowsToEdit, column.getPrefixedIndex(), "value1, value2, value3", null);

		assertTrue(row1.getVisibleCellValue(column).equals("value1, value2, value3"));
		assertTrue(row1.getAllVisibleCellValuesAsIs(column).size() == 3);
		assertTrue(row2.getVisibleCellValue(column).equals("value1, value2, value3"));
		assertTrue(row2.getAllVisibleCellValuesAsIs(column).size() == 3);
	}

	@Test
	public void testSetManyCellToSeparatedValues() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");

		SpreadsheetColumn column = model.getWorksheetByType("Sheet1").getColumn("c_3");

		SpreadsheetRow row = SharedTestMethods.writeRow(model, "Sheet1", "1", column.getPrefixedIndex(), "v1, v2");

		assertTrue(row.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 2);

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(row, column.getPrefixedIndex(), "v3,   v4", null);

		assertTrue(row.getVisibleCellValue(column).equals("v3,   v4"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 2);
	}

	@Test
	public void testSetManyCellToNonInt() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		SpreadsheetColumn column = model.getWorksheetByType("Sheet2").getColumn("c_3");

		SpreadsheetRow row = SharedTestMethods.writeRow(model, "Sheet2", "1", column.getPrefixedIndex(), "123, 456");

		assertTrue(row.getVisibleCellValue(column).equals("123,456"));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 2);

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(row, column.getPrefixedIndex(), "v1, v2", null);

		// Each value set to default INT
		String newValue = SpreadsheetConstants.DEFAULT_DT_INTEGER + "," + SpreadsheetConstants.DEFAULT_DT_INTEGER;
		assertTrue(row.getVisibleCellValue(column).equals(newValue));
		assertTrue(row.getAllVisibleCellValuesAsIs(column).size() == 2);
	}

}
