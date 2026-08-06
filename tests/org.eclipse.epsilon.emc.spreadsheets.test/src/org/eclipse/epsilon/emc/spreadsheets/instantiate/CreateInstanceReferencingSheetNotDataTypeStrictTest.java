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
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModel;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetWorksheet;
import org.eclipse.epsilon.emc.spreadsheets.test.SharedTestMethods;
import org.eclipse.epsilon.emc.spreadsheets.test.TestModelFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CreateInstanceReferencingSheetNotDataTypeStrictTest {
	private static SpreadsheetModel model = null;

	public CreateInstanceReferencingSheetNotDataTypeStrictTest(SpreadsheetModel model) {
		CreateInstanceReferencingSheetNotDataTypeStrictTest.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception {
		String pathToFile = "resources/instantiate/CreateInstanceTest.xlsx";
		String pathToConfig = "resources/instantiate/CreateInstanceReferencingTestConfig.xml";
		return TestModelFactory.getModelsToTest("", pathToFile, pathToConfig, "CreateInstanceTest");
	}

	@Test
	public void testNotManyCells() throws Exception {
		String worksheetName = "Sheet1";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		SharedTestMethods.clearWorksheet(model, "Sheet5");
		assertTrue(model.getAllOfType("Sheet5").size() == 0);

		Map<String, Object> referencedValues = new HashMap<>();
		referencedValues.put("c_1", "string1,  string2");
		referencedValues.put("c_2", "123, 456,  abc");
		referencedValues.put("c_3", "1.52684, 1.23456,  abc");
		referencedValues.put("c_4", "1.52684789, 1.23456789,  abc");
		referencedValues.put("c_5", "true, false,  abc");
		SpreadsheetRow referencedRow = (SpreadsheetRow) model.createInstance("Sheet5", referencedValues);
		assertTrue(model.getAllOfType("Sheet5").size() == 1);

		Map<String, Object> values = new HashMap<>();
		values.put("c_1", referencedRow);
		values.put("c_2", referencedRow);
		values.put("c_3", referencedRow);
		values.put("c_4", referencedRow);
		values.put("c_5", referencedRow);
		model.createInstance(worksheetName, values);

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
		assertTrue(value.equals("123, 456,  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals("1.52684, 1.23456,  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals("1.52684789, 1.23456789,  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase("true, false,  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 1);
	}

	@Test
	public void testNotManyCellsMultipleRows() throws Exception {
		String worksheetName = "Sheet1";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		SharedTestMethods.clearWorksheet(model, "Sheet5");
		assertTrue(model.getAllOfType("Sheet5").size() == 0);

		Map<String, Object> referencedValues = new HashMap<>();
		referencedValues.put("c_1", "string1,  string2");
		referencedValues.put("c_2", "123, 456,  abc");
		referencedValues.put("c_3", "1.52684, 1.23456,  abc");
		referencedValues.put("c_4", "1.52684789, 1.23456789,  abc");
		referencedValues.put("c_5", "true, false,  abc");
		SpreadsheetRow referencedRow1 = (SpreadsheetRow) model.createInstance("Sheet5", referencedValues);
		SpreadsheetRow referencedRow2 = (SpreadsheetRow) model.createInstance("Sheet5", referencedValues);
		assertTrue(model.getAllOfType("Sheet5").size() == 2);

		Set<SpreadsheetRow> setOfReferencedRows = new LinkedHashSet<>();
		setOfReferencedRows.add(referencedRow1);
		setOfReferencedRows.add(referencedRow2);

		Map<String, Object> values = new HashMap<>();
		values.put("c_1", setOfReferencedRows);
		values.put("c_2", setOfReferencedRows);
		values.put("c_3", setOfReferencedRows);
		values.put("c_4", setOfReferencedRows);
		values.put("c_5", setOfReferencedRows);
		model.createInstance(worksheetName, values);

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
		assertTrue(value.equals("123, 456,  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals("1.52684, 1.23456,  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals("1.52684789, 1.23456789,  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase("true, false,  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 1);
	}

	@Test
	public void testManyCells() throws Exception {
		String worksheetName = "Sheet2";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		SharedTestMethods.clearWorksheet(model, "Sheet5");
		assertTrue(model.getAllOfType("Sheet5").size() == 0);

		Map<String, Object> referencedValues = new HashMap<>();
		referencedValues.put("c_1", "string1,  string2");
		referencedValues.put("c_2", "123, 456,  abc");
		referencedValues.put("c_3", "1.52684, 1.23456,  abc");
		referencedValues.put("c_4", "1.52684789, 1.23456789,  abc");
		referencedValues.put("c_5", "true, false,  abc");
		SpreadsheetRow referencedRow = (SpreadsheetRow) model.createInstance("Sheet5", referencedValues);
		assertTrue(model.getAllOfType("Sheet5").size() == 1);

		Map<String, Object> values = new HashMap<>();
		values.put("c_1", referencedRow);
		values.put("c_2", referencedRow);
		values.put("c_3", referencedRow);
		values.put("c_4", referencedRow);
		values.put("c_5", referencedRow);
		model.createInstance(worksheetName, values);

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
		assertTrue(value.equals("123, 456,  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals("1.52684, 1.23456,  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals("1.52684789, 1.23456789,  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase("true, false,  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 1);
	}

	@Test
	public void testManyCellsMultipleRows() throws Exception {
		String worksheetName = "Sheet2";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		SharedTestMethods.clearWorksheet(model, "Sheet5");
		assertTrue(model.getAllOfType("Sheet5").size() == 0);

		Map<String, Object> referencedValues = new HashMap<>();
		referencedValues.put("c_1", "string1,  string2");
		referencedValues.put("c_2", "123, 456,  abc");
		referencedValues.put("c_3", "1.52684, 1.23456,  abc");
		referencedValues.put("c_4", "1.52684789, 1.23456789,  abc");
		referencedValues.put("c_5", "true, false,  abc");
		SpreadsheetRow referencedRow1 = (SpreadsheetRow) model.createInstance("Sheet5", referencedValues);
		SpreadsheetRow referencedRow2 = (SpreadsheetRow) model.createInstance("Sheet5", referencedValues);
		assertTrue(model.getAllOfType("Sheet5").size() == 2);

		Set<SpreadsheetRow> setOfReferencedRows = new LinkedHashSet<>();
		setOfReferencedRows.add(referencedRow1);
		setOfReferencedRows.add(referencedRow2);

		Map<String, Object> values = new HashMap<>();
		values.put("c_1", setOfReferencedRows);
		values.put("c_2", setOfReferencedRows);
		values.put("c_3", setOfReferencedRows);
		values.put("c_4", setOfReferencedRows);
		values.put("c_5", setOfReferencedRows);
		model.createInstance(worksheetName, values);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind(worksheetName);
		assertTrue(rows != null && !rows.isEmpty());
		assertTrue(rows.size() == 1);

		SpreadsheetWorksheet worksheet = model.getWorksheetByType(worksheetName);
		SpreadsheetRow row = rows.get(0);
		String value;

		value = row.getVisibleCellValue(worksheet.getColumn("c_1"));
		assertTrue(value.equals("string1,  string2;string1,  string2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_1")).size() == 2);
		value = row.getVisibleCellValue(worksheet.getColumn("c_2"));
		assertTrue(value.equals("123, 456,  abc;123, 456,  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 2);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals("1.52684, 1.23456,  abc;1.52684, 1.23456,  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 2);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals("1.52684789, 1.23456789,  abc;1.52684789, 1.23456789,  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 2);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase("true, false,  abc;true, false,  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 2);
	}

	@Test
	public void testNotManyCellsFromManyCells() throws Exception {
		String worksheetName = "Sheet1";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		SharedTestMethods.clearWorksheet(model, "Sheet6");
		assertTrue(model.getAllOfType("Sheet6").size() == 0);

		Map<String, Object> referencedValues = new HashMap<>();
		referencedValues.put("c_1", "string1,  string2");
		referencedValues.put("c_2", "123, 456,  abc");
		referencedValues.put("c_3", "1.52684, 1.23456,  abc");
		referencedValues.put("c_4", "1.52684789, 1.23456789,  abc");
		referencedValues.put("c_5", "true, false,  abc");
		SpreadsheetRow referencedRow = (SpreadsheetRow) model.createInstance("Sheet6", referencedValues);
		assertTrue(model.getAllOfType("Sheet6").size() == 1);

		Map<String, Object> values = new HashMap<>();
		values.put("c_1", referencedRow);
		values.put("c_2", referencedRow);
		values.put("c_3", referencedRow);
		values.put("c_4", referencedRow);
		values.put("c_5", referencedRow);
		model.createInstance(worksheetName, values);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind(worksheetName);
		assertTrue(rows != null && !rows.isEmpty());
		assertTrue(rows.size() == 1);

		SpreadsheetWorksheet worksheet = model.getWorksheetByType(worksheetName);
		SpreadsheetRow row = rows.get(0);
		String value;

		value = row.getVisibleCellValue(worksheet.getColumn("c_1"));
		assertTrue(value.equals("string1"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_1")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_2"));
		assertTrue(value.equals("123"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals("1.52684"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals("1.52684789"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase("true"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 1);
	}

	@Test
	public void testNotManyCellsMultipleRowsFromManyCells() throws Exception {
		String worksheetName = "Sheet1";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		SharedTestMethods.clearWorksheet(model, "Sheet6");
		assertTrue(model.getAllOfType("Sheet6").size() == 0);

		Map<String, Object> referencedValues = new HashMap<>();
		referencedValues.put("c_1", "string1,  string2");
		referencedValues.put("c_2", "123, 456,  abc");
		referencedValues.put("c_3", "1.52684, 1.23456,  abc");
		referencedValues.put("c_4", "1.52684789, 1.23456789,  abc");
		referencedValues.put("c_5", "true, false,  abc");
		SpreadsheetRow referencedRow1 = (SpreadsheetRow) model.createInstance("Sheet6", referencedValues);
		SpreadsheetRow referencedRow2 = (SpreadsheetRow) model.createInstance("Sheet6", referencedValues);
		assertTrue(model.getAllOfType("Sheet6").size() == 2);

		Set<SpreadsheetRow> setOfReferencedRows = new LinkedHashSet<>();
		setOfReferencedRows.add(referencedRow1);
		setOfReferencedRows.add(referencedRow2);

		Map<String, Object> values = new HashMap<>();
		values.put("c_1", setOfReferencedRows);
		values.put("c_2", setOfReferencedRows);
		values.put("c_3", setOfReferencedRows);
		values.put("c_4", setOfReferencedRows);
		values.put("c_5", setOfReferencedRows);
		model.createInstance(worksheetName, values);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind(worksheetName);
		assertTrue(rows != null && !rows.isEmpty());
		assertTrue(rows.size() == 1);

		SpreadsheetWorksheet worksheet = model.getWorksheetByType(worksheetName);
		SpreadsheetRow row = rows.get(0);
		String value;

		value = row.getVisibleCellValue(worksheet.getColumn("c_1"));
		assertTrue(value.equals("string1"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_1")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_2"));
		assertTrue(value.equals("123"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals("1.52684"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals("1.52684789"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 1);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase("true"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 1);
	}

	@Test
	public void testManyCellsFromManyCells() throws Exception {
		String worksheetName = "Sheet2";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		SharedTestMethods.clearWorksheet(model, "Sheet6");
		assertTrue(model.getAllOfType("Sheet6").size() == 0);

		Map<String, Object> referencedValues = new HashMap<>();
		referencedValues.put("c_1", "string1,  string2");
		referencedValues.put("c_2", "123, 456,  abc");
		referencedValues.put("c_3", "1.52684, 1.23456,  abc");
		referencedValues.put("c_4", "1.52684789, 1.23456789,  abc");
		referencedValues.put("c_5", "true, false,  abc");
		SpreadsheetRow referencedRow = (SpreadsheetRow) model.createInstance("Sheet6", referencedValues);
		assertTrue(model.getAllOfType("Sheet6").size() == 1);

		Map<String, Object> values = new HashMap<>();
		values.put("c_1", referencedRow);
		values.put("c_2", referencedRow);
		values.put("c_3", referencedRow);
		values.put("c_4", referencedRow);
		values.put("c_5", referencedRow);
		model.createInstance(worksheetName, values);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind(worksheetName);
		assertTrue(rows != null && !rows.isEmpty());
		assertTrue(rows.size() == 1);

		SpreadsheetWorksheet worksheet = model.getWorksheetByType(worksheetName);
		SpreadsheetRow row = rows.get(0);
		String value;

		value = row.getVisibleCellValue(worksheet.getColumn("c_1"));
		assertTrue(value.equals("string1;  string2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_1")).size() == 2);
		value = row.getVisibleCellValue(worksheet.getColumn("c_2"));
		assertTrue(value.equals("123; 456;  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 3);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals("1.52684; 1.23456;  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 3);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals("1.52684789; 1.23456789;  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 3);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase("true; false;  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 3);
	}

	@Test
	public void testManyCellsMultipleRowsFromManyCells() throws Exception {
		String worksheetName = "Sheet2";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		SharedTestMethods.clearWorksheet(model, "Sheet6");
		assertTrue(model.getAllOfType("Sheet6").size() == 0);

		Map<String, Object> referencedValues = new HashMap<>();
		referencedValues.put("c_1", "string1,  string2");
		referencedValues.put("c_2", "123, 456,  abc");
		referencedValues.put("c_3", "1.52684, 1.23456,  abc");
		referencedValues.put("c_4", "1.52684789, 1.23456789,  abc");
		referencedValues.put("c_5", "true, false,  abc");
		SpreadsheetRow referencedRow1 = (SpreadsheetRow) model.createInstance("Sheet6", referencedValues);
		SpreadsheetRow referencedRow2 = (SpreadsheetRow) model.createInstance("Sheet6", referencedValues);
		assertTrue(model.getAllOfType("Sheet6").size() == 2);

		Set<SpreadsheetRow> setOfReferencedRows = new LinkedHashSet<>();
		setOfReferencedRows.add(referencedRow1);
		setOfReferencedRows.add(referencedRow2);

		Map<String, Object> values = new HashMap<>();
		values.put("c_1", setOfReferencedRows);
		values.put("c_2", setOfReferencedRows);
		values.put("c_3", setOfReferencedRows);
		values.put("c_4", setOfReferencedRows);
		values.put("c_5", setOfReferencedRows);
		model.createInstance(worksheetName, values);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind(worksheetName);
		assertTrue(rows != null && !rows.isEmpty());
		assertTrue(rows.size() == 1);

		SpreadsheetWorksheet worksheet = model.getWorksheetByType(worksheetName);
		SpreadsheetRow row = rows.get(0);
		String value;

		value = row.getVisibleCellValue(worksheet.getColumn("c_1"));
		assertTrue(value.equals("string1;  string2" + ";" + "string1;  string2"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_1")).size() == 4);
		value = row.getVisibleCellValue(worksheet.getColumn("c_2"));
		assertTrue(value.equals("123; 456;  abc" + ";" + "123; 456;  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_2")).size() == 6);
		value = row.getVisibleCellValue(worksheet.getColumn("c_3"));
		assertTrue(value.equals("1.52684; 1.23456;  abc" + ";" + "1.52684; 1.23456;  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_3")).size() == 6);
		value = row.getVisibleCellValue(worksheet.getColumn("c_4"));
		assertTrue(value.equals("1.52684789; 1.23456789;  abc" + ";" + "1.52684789; 1.23456789;  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_4")).size() == 6);
		value = row.getVisibleCellValue(worksheet.getColumn("c_5"));
		assertTrue(value.equalsIgnoreCase("true; false;  abc" + ";" + "true; false;  abc"));
		assertTrue(row.getAllVisibleCellValuesAsIs(worksheet.getColumn("c_5")).size() == 6);
	}

}
