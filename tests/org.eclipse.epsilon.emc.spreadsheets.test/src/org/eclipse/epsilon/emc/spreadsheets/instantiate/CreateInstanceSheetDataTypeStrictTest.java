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

import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetConstants;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModel;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetWorksheet;
import org.eclipse.epsilon.emc.spreadsheets.test.SharedTestMethods;
import org.eclipse.epsilon.emc.spreadsheets.test.TestModelFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CreateInstanceSheetDataTypeStrictTest {
	private static SpreadsheetModel model = null;

	public CreateInstanceSheetDataTypeStrictTest(SpreadsheetModel model) {
		CreateInstanceSheetDataTypeStrictTest.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception {
		String pathToFile = "resources/instantiate/CreateInstanceTest.xlsx";
		String pathToConfig = "resources/instantiate/CreateInstanceTestConfig.xml";
		return TestModelFactory.getModelsToTest("", pathToFile, pathToConfig, "CreateInstanceTest");
	}

	@Test
	public void testNotManyCellsDataTypeStrict() throws Exception {
		String worksheetName = "Sheet3";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		Map<String, Object> values = new HashMap<>();
		values.put("c_1", "string1,  string2");
		values.put("c_2", "123, 456, abc");
		values.put("c_3", "1.52684, 1.23456, abc");
		values.put("c_4", "1.52684, 1.23456, abc");
		values.put("c_5", "true, false, abc");
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
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_INTEGER));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_DOUBLE));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_FLOAT));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase(SpreadsheetConstants.DEFAULT_DT_BOOLEAN));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 1);
	}

	@Test
	public void testNotManyCellsDataTypeStrictEmptyRow() throws Exception {
		String worksheetName = "Sheet3";

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
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_STRING));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_1")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_2"));
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_INTEGER));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_DOUBLE));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_FLOAT));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase(SpreadsheetConstants.DEFAULT_DT_BOOLEAN));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 1);
	}

	@Test
	public void testNotManyCellsDataTypeStrictEmptyValues() throws Exception {
		String worksheetName = "Sheet3";

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
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_INTEGER));
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_DOUBLE));
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_FLOAT));
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase(SpreadsheetConstants.DEFAULT_DT_BOOLEAN));
	}

	@Test
	public void testNotManyCellsDataTypeStrictSetOfValues() throws Exception {
		String worksheetName = "Sheet3";

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
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_INTEGER));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_DOUBLE));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_FLOAT));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase(SpreadsheetConstants.DEFAULT_DT_BOOLEAN));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 1);
	}

	@Test
	public void testManyCellsDataTypeStrict() throws Exception {
		String worksheetName = "Sheet4";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		Map<String, Object> values = new HashMap<>();
		values.put("c_1", "string1,  string2");
		values.put("c_2", "123, 456,  abc");
		values.put("c_3", "1.52684, 1.23456,  abc");
		values.put("c_4", "1.52684789, 1.23456789,  abc");
		values.put("c_5", "true, false,  abc");
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
		assertTrue(value.equals("123,456," + SpreadsheetConstants.DEFAULT_DT_INTEGER));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 3);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals("1.52684,1.23456," + SpreadsheetConstants.DEFAULT_DT_DOUBLE));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 3);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals(Float.parseFloat("1.52684789") + "," + Float.parseFloat("1.23456789") + ","
			+ SpreadsheetConstants.DEFAULT_DT_FLOAT));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 3);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase("true,false," + SpreadsheetConstants.DEFAULT_DT_BOOLEAN));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 3);
	}

	@Test
	public void testManyCellsDataTypeStrictEmptyRow() throws Exception {
		String worksheetName = "Sheet4";

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
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_STRING));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_1")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_2"));
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_INTEGER));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_DOUBLE));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_FLOAT));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase(SpreadsheetConstants.DEFAULT_DT_BOOLEAN));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 1);
	}

	@Test
	public void testManyCellsDataTypeStrictNullValues() throws Exception {
		String worksheetName = "Sheet4";

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
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_INTEGER));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_DOUBLE));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals(SpreadsheetConstants.DEFAULT_DT_FLOAT));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase(SpreadsheetConstants.DEFAULT_DT_BOOLEAN));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 1);
	}

	@Test
	public void testManyCellsDataTypeStrictSetOfValues() throws Exception {
		String worksheetName = "Sheet4";

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
		assertTrue(value.equals("123,456," + SpreadsheetConstants.DEFAULT_DT_INTEGER));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 3);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals("1.52684,1.23456," + SpreadsheetConstants.DEFAULT_DT_DOUBLE));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 3);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals(Float.parseFloat("1.52684789") + "," + Float.parseFloat("1.23456789") + ","
			+ SpreadsheetConstants.DEFAULT_DT_FLOAT));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 3);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase("true,false," + SpreadsheetConstants.DEFAULT_DT_BOOLEAN));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 3);
	}

}
