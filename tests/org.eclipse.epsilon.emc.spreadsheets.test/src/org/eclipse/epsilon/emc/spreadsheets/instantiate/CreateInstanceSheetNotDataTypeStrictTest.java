/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.spreadsheets.instantiate;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModel;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetWorksheet;
import org.eclipse.epsilon.emc.spreadsheets.test.SharedTestMethods;
import org.eclipse.epsilon.emc.spreadsheets.test.TestModelFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CreateInstanceSheetNotDataTypeStrictTest {
	private static SpreadsheetModel model = null;

	public CreateInstanceSheetNotDataTypeStrictTest(SpreadsheetModel model) {
		CreateInstanceSheetNotDataTypeStrictTest.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception {
		String pathToFile = "resources/instantiate/CreateInstanceTest.xlsx";
		String pathToConfig = "resources/instantiate/CreateInstanceTestConfig.xml";
		return TestModelFactory.getModelsToTest("", pathToFile, pathToConfig, "CreateInstanceTest");
	}

	@Test
	public void testNotManyCellsNotDataTypeStrict() throws Exception {
		String worksheetName = "Sheet1";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		Map<String, Object> values = new HashMap<>();
		values.put("c_1", "string1,  string2");
		values.put("c_2", "123, 456");
		values.put("c_3", "1.52684, 1.23456");
		values.put("c_4", "1.52684, 1.23456");
		values.put("c_5", "true, false");
		model.createInstance(worksheetName, values);
		assertTrue(model.getAllOfType(worksheetName).size() == 1);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind(worksheetName);
		assertTrue(rows != null && !rows.isEmpty());
		assertTrue(rows.size() == 1);

		SpreadsheetWorksheet worksheet = model.getWorksheetByType(worksheetName);
		SpreadsheetRow row = rows.get(0);
		String value;

		value = row.getVisibleCellValue(worksheet.getColumn("c_1"));
		assertTrue(value.equals("string1,  string2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_1")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_2"));
		assertTrue(value.equals("123, 456"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals("1.52684, 1.23456"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals("1.52684, 1.23456"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase("true, false"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 1);
	}

	@Test
	public void testNotManyCellsNotDataTypeStrictEmptyRow() throws Exception {
		String worksheetName = "Sheet1";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		model.createInstance(worksheetName, new HashMap<String, Object>());
		assertTrue(model.getAllOfType(worksheetName).size() == 1);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind(worksheetName);
		assertTrue(rows != null && !rows.isEmpty());
		assertTrue(rows.size() == 1);

		SpreadsheetWorksheet worksheet = model.getWorksheetByType(worksheetName);
		SpreadsheetRow row = rows.get(0);
		String value;

		value = row.getVisibleCellValue(worksheet.getColumn("c_1"));
		assertTrue(value.equals(worksheet.getDefaultEmptyCellValue()));
		value = row.getVisibleCellValue(worksheet.getColumn("c_2"));
		assertTrue(value.equals(worksheet.getDefaultEmptyCellValue()));
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals(worksheet.getDefaultEmptyCellValue()));
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals(worksheet.getDefaultEmptyCellValue()));
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equals(worksheet.getDefaultEmptyCellValue()));
	}

	@Test
	public void testNotManyCellsNotDataTypeStrictEmptyValues() throws Exception {
		String worksheetName = "Sheet1";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		Map<String, Object> values = new HashMap<>();
		values.put("c_1", "");
		values.put("c_2", "");
		values.put("c_3", "");
		values.put("c_4", "");
		values.put("c_5", "");
		model.createInstance(worksheetName, values);
		assertTrue(model.getAllOfType(worksheetName).size() == 1);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind(worksheetName);
		assertTrue(rows != null && !rows.isEmpty());
		assertTrue(rows.size() == 1);

		SpreadsheetWorksheet worksheet = model.getWorksheetByType(worksheetName);
		SpreadsheetRow row = rows.get(0);
		String value;

		value = row.getVisibleCellValue(worksheet.getColumn("c_1"));
		assertTrue(value.equals(""));
		value = row.getVisibleCellValue(worksheet.getColumn("c_2"));
		assertTrue(value.equals(""));
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals(""));
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals(""));
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equals(""));
	}

	@Test
	public void testNotManyCellsNotDataTypeStrictSetOfValues() throws Exception {
		String worksheetName = "Sheet1";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		Map<String, Object> values = new HashMap<>();
		values.put("c_1", new LinkedHashSet<Object>(Arrays.asList("string1", "string2")));
		values.put("c_2", new LinkedHashSet<Object>(Arrays.asList("123", "456", "abc")));
		values.put("c_3", new LinkedHashSet<Object>(Arrays.asList("1.52684", "1.23456", "abc")));
		values.put("c_4", new LinkedHashSet<Object>(Arrays.asList("1.52684789", "1.23456789", "abc")));
		values.put("c_5", new LinkedHashSet<Object>(Arrays.asList("true", "false", "abc")));
		model.createInstance(worksheetName, values);
		assertTrue(model.getAllOfType(worksheetName).size() == 1);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind(worksheetName);
		assertTrue(rows != null && !rows.isEmpty());
		assertTrue(rows.size() == 1);

		SpreadsheetWorksheet worksheet = model.getWorksheetByType(worksheetName);
		SpreadsheetRow row = rows.get(0);
		String value;

		value = row.getVisibleCellValue(worksheet.getColumn("c_1"));
		assertTrue(value.equals("string1,string2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_1")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_2"));
		assertTrue(value.equals("123,456,abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals("1.52684,1.23456,abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals("1.52684789,1.23456789,abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase("true,false,abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 1);
	}

	@Test
	public void testManyCellsNotDataTypeStrict() throws Exception {
		String worksheetName = "Sheet2";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		Map<String, Object> values = new HashMap<>();
		values.put("c_1", "string1,  string2");
		values.put("c_2", "123, 456");
		values.put("c_3", "1.52684, 1.23456");
		values.put("c_4", "1.52684, 1.23456");
		values.put("c_5", "true, false");
		model.createInstance(worksheetName, values);
		assertTrue(model.getAllOfType(worksheetName).size() == 1);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind(worksheetName);
		assertTrue(rows != null && !rows.isEmpty());
		assertTrue(rows.size() == 1);

		SpreadsheetWorksheet worksheet = model.getWorksheetByType(worksheetName);
		SpreadsheetRow row = rows.get(0);
		String value;

		value = row.getVisibleCellValue(worksheet.getColumn("c_1"));
		assertTrue(value.equals("string1,  string2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_1")).size() == 2);
		value = row.getVisibleCellValue(worksheet.getColumn("c_2"));
		assertTrue(value.equals("123, 456"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 2);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals("1.52684, 1.23456"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 2);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals("1.52684, 1.23456"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 2);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase("true, false"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 2);
	}

	@Test
	public void testManyCellsNotDataTypeStrictEmptyRow() throws Exception {
		String worksheetName = "Sheet2";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		model.createInstance(worksheetName, new HashMap<String, Object>());
		assertTrue(model.getAllOfType(worksheetName).size() == 1);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind(worksheetName);
		assertTrue(rows != null && !rows.isEmpty());
		assertTrue(rows.size() == 1);

		SpreadsheetWorksheet worksheet = model.getWorksheetByType(worksheetName);
		SpreadsheetRow row = rows.get(0);
		String value;

		value = row.getVisibleCellValue(worksheet.getColumn("c_1"));
		assertTrue(value.equals(worksheet.getDefaultEmptyCellValue()));
		value = row.getVisibleCellValue(worksheet.getColumn("c_2"));
		assertTrue(value.equals(worksheet.getDefaultEmptyCellValue()));
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals(worksheet.getDefaultEmptyCellValue()));
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals(worksheet.getDefaultEmptyCellValue()));
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equals(worksheet.getDefaultEmptyCellValue()));
	}

	@Test
	public void testManyCellsNotDataTypeStrictNullValues() throws Exception {
		String worksheetName = "Sheet2";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		Map<String, Object> values = new HashMap<>();
		values.put("c_1", null);
		values.put("c_2", null);
		values.put("c_3", null);
		values.put("c_4", null);
		values.put("c_5", null);
		model.createInstance(worksheetName, values);
		assertTrue(model.getAllOfType(worksheetName).size() == 1);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind(worksheetName);
		assertTrue(rows != null && !rows.isEmpty());
		assertTrue(rows.size() == 1);

		SpreadsheetWorksheet worksheet = model.getWorksheetByType(worksheetName);
		SpreadsheetRow row = rows.get(0);
		String value;

		value = row.getVisibleCellValue(worksheet.getColumn("c_1"));
		assertTrue(value.equals("null"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_1")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_2"));
		assertTrue(value.equals("null"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals("null"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals("null"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase("null"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 1);
	}

	@Test
	public void testManyCellsNotDataTypeStrictSetOfValues() throws Exception {
		String worksheetName = "Sheet2";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		Map<String, Object> values = new HashMap<>();
		values.put("c_1", new LinkedHashSet<Object>(Arrays.asList("string1", "string2")));
		values.put("c_2", new LinkedHashSet<Object>(Arrays.asList("123", "456", "abc")));
		values.put("c_3", new LinkedHashSet<Object>(Arrays.asList("1.52684", "1.23456", "abc")));
		values.put("c_4", new LinkedHashSet<Object>(Arrays.asList("1.52684789", "1.23456789", "abc")));
		values.put("c_5", new LinkedHashSet<Object>(Arrays.asList("true", "false", "abc")));
		model.createInstance(worksheetName, values);
		assertTrue(model.getAllOfType(worksheetName).size() == 1);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind(worksheetName);
		assertTrue(rows != null && !rows.isEmpty());
		assertTrue(rows.size() == 1);

		SpreadsheetWorksheet worksheet = model.getWorksheetByType(worksheetName);
		SpreadsheetRow row = rows.get(0);
		String value;

		value = row.getVisibleCellValue(worksheet.getColumn("c_1"));
		assertTrue(value.equals("string1,string2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_1")).size() == 2);
		value = row.getVisibleCellValue(worksheet.getColumn("c_2"));
		assertTrue(value.equals("123,456,abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 3);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals("1.52684,1.23456,abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 3);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals("1.52684789,1.23456789,abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 3);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase("true,false,abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 3);
	}

}
