/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.spreadsheets.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModel;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetPropertyGetter;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetWorksheet;
import org.eclipse.epsilon.emc.spreadsheets.test.TestModelFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CellMultiplicityTest {
	private SpreadsheetModel model = null;

	public CellMultiplicityTest(SpreadsheetModel model) {
		this.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception {
		String PATH_TO_FILE = "resources/common/CellMultiplicityTest.xlsx";
		String CONFIG = "resources/common/CellMultiplicityTestConfig.xml";
		return TestModelFactory.getModelsToTest("", PATH_TO_FILE, CONFIG, "CellMultiplicityTest");
	}

	@Test
	public void testWriteValueToWorksheetNotEnforcingDataTypes() throws Exception {
		SpreadsheetWorksheet worksheet = model.getWorksheetByType("Sheet1");
		for (SpreadsheetRow row : worksheet.getRows()) {
			worksheet.removeRow(row);
		}
		assertTrue(worksheet.getRows().size() == 0);

		List<Object> list = new ArrayList<>();
		Map<String, Object> values = new HashMap<>();
		values.put("c_0", "456# value2"); // driver id
		values.put("column1", "123# abc"); // name
		list.add(values);
		SpreadsheetRow row = (SpreadsheetRow) model.createInstance("Sheet1", list);
		assertTrue(worksheet.getRows().size() == 1);

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();
		Object visibleValue = getter.invoke(row, "v_column1", null);
		assertTrue(visibleValue.equals("123# abc"));

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind("S1");
		SpreadsheetRow row1 = rows.get(0);
		row1.getAllVisibleCellValuesAsIs(worksheet.getColumn(0));

		final List<String> column0value = row1.getAllVisibleCellValuesAsIs(worksheet.getColumn(0));
		assertTrue(column0value.size() == 1);
		assertTrue(column0value.get(0).equals("456# value2"));
		final List<String> column1value = row1.getAllVisibleCellValuesAsIs(worksheet.getColumn(1));
		assertTrue(column1value.size() == 2);
		assertTrue(column1value.get(0).equals("123"));
		assertTrue(column1value.get(1).equals(" abc"));

		worksheet.removeRow(row1);
		assertTrue(worksheet.getRows().size() == 0);
	}

	@Test
	public void testWriteValueToStringDataTypeStrictWorksheet() throws Exception {
		SpreadsheetWorksheet worksheet = model.getWorksheetByType("Sheet2");
		for (SpreadsheetRow row : worksheet.getRows()) {
			worksheet.removeRow(row);
		}
		assertTrue(worksheet.getRows().size() == 0);

		List<Object> list = new ArrayList<>();
		Map<String, Object> values = new HashMap<>();
		values.put("column0", "456; value2");
		values.put("column1", "123; abc");
		list.add(values);
		model.createInstance("Sheet2", list);
		assertTrue(worksheet.getRows().size() == 1);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind("S2");
		SpreadsheetRow row1 = rows.get(0);
		row1.getAllVisibleCellValuesAsIs(worksheet.getColumn(0));

		final List<String> column0value = row1.getAllVisibleCellValuesAsIs(worksheet.getColumn(0));
		assertTrue(column0value.size() == 1);
		assertTrue(column0value.get(0).equals("456; value2"));
		final List<String> column1value = row1.getAllVisibleCellValuesAsIs(worksheet.getColumn(1));
		assertTrue(column1value.size() == 2);
		assertTrue(column1value.get(0).equals("123"));
		assertTrue(column1value.get(1).equals("abc"));

		worksheet.removeRow(row1);
		assertTrue(worksheet.getRows().size() == 0);
	}

	@Test
	public void testWriteValueToIntegerDataTypeStrictWorksheet() throws Exception {
		SpreadsheetWorksheet worksheet = model.getWorksheetByType("Sheet2");
		for (SpreadsheetRow row : worksheet.getRows()) {
			worksheet.removeRow(row);
		}
		assertTrue(worksheet.getRows().size() == 0);

		List<Object> list = new ArrayList<>();
		Map<String, Object> values = new HashMap<>();
		values.put("column2", "456; 123");
		values.put("column3", "123; 789");
		list.add(values);
		model.createInstance("Sheet2", list);
		assertTrue(worksheet.getRows().size() == 1);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind("S2");
		SpreadsheetRow row1 = rows.get(0);
		row1.getAllVisibleCellValuesAsIs(worksheet.getColumn(0));

		final List<String> column2value = row1.getAllVisibleCellValuesAsIs(worksheet.getColumn(2));
		assertTrue(column2value.size() == 1);
		assertTrue(column2value.get(0).equals("0"));
		final List<String> column3value = row1.getAllVisibleCellValuesAsIs(worksheet.getColumn(3));
		assertTrue(column3value.size() == 2);
		assertTrue(column3value.get(0).equals("123"));
		assertTrue(column3value.get(1).equals("789"));

		worksheet.removeRow(row1);
		assertTrue(worksheet.getRows().size() == 0);
	}

	@Test
	public void testWriteValueToDoubleDataTypeStrictWorksheet() throws Exception {
		SpreadsheetWorksheet worksheet = model.getWorksheetByType("Sheet2");
		for (SpreadsheetRow row : worksheet.getRows()) {
			worksheet.removeRow(row);
		}
		assertTrue(worksheet.getRows().size() == 0);

		List<Object> list = new ArrayList<>();
		Map<String, Object> values = new HashMap<>();
		values.put("column4", "4.3; 123.1567");
		values.put("column5", "123.456 ;  789.00098");
		list.add(values);
		model.createInstance("Sheet2", list);
		assertTrue(worksheet.getRows().size() == 1);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind("S2");
		SpreadsheetRow row1 = rows.get(0);
		final List<String> column4value = row1.getAllVisibleCellValuesAsIs(worksheet.getColumn(4));
		assertEquals(1, column4value.size());
		assertEquals("0", column4value.get(0));
		final List<String> column5value = row1.getAllVisibleCellValuesAsIs(worksheet.getColumn(5));
		assertEquals(2, column5value.size());
		assertEquals("123.456", column5value.get(0));
		assertEquals("789.00098", column5value.get(1));

		worksheet.removeRow(row1);
		assertTrue(worksheet.getRows().size() == 0);
	}

	@Test
	public void testWriteValueToBooleanDataTypeStrictWorksheet() throws Exception {
		SpreadsheetWorksheet worksheet = model.getWorksheetByType("Sheet2");
		for (SpreadsheetRow row : worksheet.getRows()) {
			worksheet.removeRow(row);
		}
		assertTrue(worksheet.getRows().size() == 0);

		List<Object> list = new ArrayList<>();
		Map<String, Object> values = new HashMap<>();
		values.put("column6", "true;false");
		values.put("column7", "false;  true; false");
		list.add(values);
		model.createInstance("Sheet2", list);
		assertTrue(worksheet.getRows().size() == 1);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind("S2");
		SpreadsheetRow row1 = rows.get(0);
		final List<String> column6value = row1.getAllVisibleCellValuesAsIs(worksheet.getColumn(6));
		assertTrue(column6value.size() == 1);
		assertTrue(column6value.get(0).equalsIgnoreCase("false"));
		final List<String> column7value = row1.getAllVisibleCellValuesAsIs(worksheet.getColumn(7));
		assertTrue(column7value.size() == 3);
		assertTrue(column7value.get(0).equalsIgnoreCase("false"));
		assertTrue(column7value.get(1).equalsIgnoreCase("true"));
		assertTrue(column7value.get(2).equalsIgnoreCase("false"));

		worksheet.removeRow(row1);
		assertTrue(worksheet.getRows().size() == 0);
	}

	@Test
	public void testWriteValueToFloatDataTypeStrictWorksheet() throws Exception {
		SpreadsheetWorksheet worksheet = model.getWorksheetByType("Sheet2");
		for (SpreadsheetRow row : worksheet.getRows()) {
			worksheet.removeRow(row);
		}
		assertTrue(worksheet.getRows().size() == 0);

		List<Object> list = new ArrayList<>();
		Map<String, Object> values = new HashMap<>();
		values.put("column8", "4.3; 123.1567");
		values.put("column9", "123.456 ;  789.00098");
		list.add(values);
		model.createInstance("Sheet2", list);
		assertTrue(worksheet.getRows().size() == 1);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind("S2");
		SpreadsheetRow row1 = rows.get(0);
		final List<String> column8value = row1.getAllVisibleCellValuesAsIs(worksheet.getColumn(8));
		assertTrue(column8value.size() == 1);
		assertTrue(column8value.get(0).equals("0"));
		final List<String> column9value = row1.getAllVisibleCellValuesAsIs(worksheet.getColumn(9));
		assertTrue(column9value.size() == 2);
		assertTrue(column9value.get(0).equals("123.456"));
		System.out.println(Float.parseFloat("789.00098"));
		assertTrue(column9value.get(1).equals(Float.parseFloat("789.00098") + ""));

		worksheet.removeRow(row1);
		assertTrue(worksheet.getRows().size() == 0);
	}

	@Test
	public void testWriteNoncompatibleValues() throws Exception {
		SpreadsheetWorksheet worksheet = model.getWorksheetByType("Sheet2");
		for (SpreadsheetRow row : worksheet.getRows()) {
			worksheet.removeRow(row);
		}
		assertTrue(worksheet.getRows().size() == 0);

		List<Object> list = new ArrayList<>();
		Map<String, Object> values = new HashMap<>();
		values.put("column3", "12a4; 789");
		values.put("column5", "123.456 ;  789.0009a");
		values.put("column7", "false;  abc; false");
		list.add(values);
		model.createInstance("Sheet2", list);
		assertTrue(worksheet.getRows().size() == 1);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind("S2");
		SpreadsheetRow row1 = rows.get(0);
		final List<String> column3value = row1.getAllVisibleCellValuesAsIs(worksheet.getColumn(3));
		assertTrue(column3value.size() == 2);
		assertTrue(column3value.get(0).equals("0"));
		assertTrue(column3value.get(1).equals("789"));
		final List<String> column5value = row1.getAllVisibleCellValuesAsIs(worksheet.getColumn(5));
		assertTrue(column5value.size() == 2);
		assertTrue(column5value.get(0).equals("123.456"));
		assertTrue(column5value.get(1).equals("0"));
		final List<String> column7value = row1.getAllVisibleCellValuesAsIs(worksheet.getColumn(7));
		assertTrue(column7value.size() == 3);
		assertTrue(column7value.get(0).equalsIgnoreCase("false"));
		assertTrue(column7value.get(1).equalsIgnoreCase("false"));
		assertTrue(column7value.get(2).equalsIgnoreCase("false"));

		worksheet.removeRow(row1);
		assertTrue(worksheet.getRows().size() == 0);
	}

}
