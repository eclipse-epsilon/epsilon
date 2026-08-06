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

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetConstants;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModel;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetWorksheet;
import org.eclipse.epsilon.emc.spreadsheets.test.TestModelFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class DataTypeStrictnessTest {
	private SpreadsheetModel model = null;

	public DataTypeStrictnessTest(SpreadsheetModel model) {
		this.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception {
		String PATH_TO_FILE = "resources/common/DataTypeStrictnessTest.xlsx";
		String CONFIG = "resources/common/DataTypeStrictnessTestConfig.xml";
		return TestModelFactory.getModelsToTest("", PATH_TO_FILE, CONFIG, "CellMultiplicityTest");
	}

	@Test
	public void testWriteRowNoDataTypeStrictness() throws Exception {
		SpreadsheetWorksheet worksheet = model.getWorksheetByType("Sheet1");
		for (SpreadsheetRow row : worksheet.getRows()) {
			worksheet.removeRow(row);
		}
		assertTrue(worksheet.getRows().size() == 0);

		List<Object> list = new ArrayList<>();
		Map<String, Object> values = new HashMap<>();
		values.put("c_0", "string"); // driver id
		values.put("column1", 123); // name
		values.put("c2", 1.526); // alias
		values.put("column3", "true");
		list.add(values);
		model.createInstance("Sheet1", list);
		assertTrue(worksheet.getRows().size() == 1);

		SpreadsheetRow row1 = worksheet.getRows().get(0);
		final String column0value = row1.getVisibleCellValue(worksheet.getColumn(0));
		assertTrue(column0value.equals("string"));
		final String column1value = row1.getVisibleCellValue(worksheet.getColumn(1));
		assertTrue(column1value.equals("123"));
		final String column2value = row1.getVisibleCellValue(worksheet.getColumn(2));
		assertTrue(column2value.equals("1.526"));
		final String column3value = row1.getVisibleCellValue(worksheet.getColumn(3));
		assertTrue(column3value.equalsIgnoreCase("true"));

		worksheet.removeRow(row1);
		assertTrue(worksheet.getRows().size() == 0);
	}

	@Test
	public void testWriteEmptyRowNoDataTypeStrictness() throws Exception {
		SpreadsheetWorksheet worksheet = model.getWorksheetByType("S1");
		for (SpreadsheetRow row : worksheet.getRows()) {
			worksheet.removeRow(row);
		}
		assertTrue(worksheet.getRows().size() == 0);

		model.createInstance("S1");
		assertTrue(worksheet.getRows().size() == 1);

		SpreadsheetRow row1 = worksheet.getRows().get(0);
		final String column0value = row1.getVisibleCellValue(worksheet.getColumn(0));
		assertTrue(column0value.equals(worksheet.getDefaultEmptyCellValue()));
		final String column1value = row1.getVisibleCellValue(worksheet.getColumn(1));
		assertTrue(column1value.equals(worksheet.getDefaultEmptyCellValue()));
		final String column2value = row1.getVisibleCellValue(worksheet.getColumn(2));
		assertTrue(column2value.equals(worksheet.getDefaultEmptyCellValue()));
		final String column3value = row1.getVisibleCellValue(worksheet.getColumn(3));
		assertTrue(column3value.equals(worksheet.getDefaultEmptyCellValue()));

		worksheet.removeRow(row1);
		assertTrue(worksheet.getRows().size() == 0);
	}

	@Test
	public void testWriteRowEnforceDataTypeStrictness() throws Exception {
		SpreadsheetWorksheet worksheet = model.getWorksheetByType("S2");
		for (SpreadsheetRow row : worksheet.getRows()) {
			worksheet.removeRow(row);
		}
		assertTrue(worksheet.getRows().size() == 0);

		List<Object> list = new ArrayList<>();
		Map<String, Object> values = new HashMap<>();
		values.put("c_0", "string"); // driver id
		values.put("column1", 123); // name
		values.put("c2", 1.526); // alias
		values.put("column3", "true");
		list.add(values);
		model.createInstance("S2", list);
		assertTrue(worksheet.getRows().size() == 1);

		SpreadsheetRow row1 = worksheet.getRows().get(0);
		final String column0value = row1.getVisibleCellValue(worksheet.getColumn(0));
		assertTrue(column0value.equals("string"));
		final String column1value = row1.getVisibleCellValue(worksheet.getColumn(1));
		assertTrue(column1value.equals("123"));
		final String column2value = row1.getVisibleCellValue(worksheet.getColumn(2));
		assertTrue(column2value.equals("1.526"));
		final String column3value = row1.getVisibleCellValue(worksheet.getColumn(3));
		assertTrue(column3value.equalsIgnoreCase("true"));

		worksheet.removeRow(row1);
		assertTrue(worksheet.getRows().size() == 0);
	}

	@Test
	public void testWriteEmptyRowEnforceDataTypeStrictness() throws Exception {
		SpreadsheetWorksheet worksheet = model.getWorksheetByType("Sheet2");
		for (SpreadsheetRow row : worksheet.getRows()) {
			worksheet.removeRow(row);
		}
		assertTrue(worksheet.getRows().size() == 0);

		model.createInstance("Sheet2");
		assertTrue(worksheet.getRows().size() == 1);

		SpreadsheetRow row1 = worksheet.getRows().get(0);
		final String column0value = row1.getVisibleCellValue(worksheet.getColumn(0));
		assertTrue(column0value.equals(SpreadsheetConstants.DEFAULT_DT_STRING));
		final String column1value = row1.getVisibleCellValue(worksheet.getColumn(1));
		assertTrue(column1value.equals(SpreadsheetConstants.DEFAULT_DT_INTEGER));
		final String column2value = row1.getVisibleCellValue(worksheet.getColumn(2));
		assertTrue(column2value.equals(SpreadsheetConstants.DEFAULT_DT_DOUBLE));
		final String column3value = row1.getVisibleCellValue(worksheet.getColumn(3));
		assertTrue(column3value.equalsIgnoreCase(SpreadsheetConstants.DEFAULT_DT_BOOLEAN));

		worksheet.removeRow(row1);
		assertTrue(worksheet.getRows().size() == 0);
	}

	@Test
	public void testWriteNoncompliantValues() throws Exception {
		SpreadsheetWorksheet worksheet = model.getWorksheetByType("S2");
		for (SpreadsheetRow row : worksheet.getRows()) {
			worksheet.removeRow(row);
		}
		assertTrue(worksheet.getRows().size() == 0);

		List<Object> list = new ArrayList<>();
		Map<String, Object> values = new HashMap<>();
		values.put("c_0", 1234); // driver id
		values.put("column1", "string"); // name
		values.put("c2", "true"); // alias
		values.put("column3", 1.526);
		list.add(values);
		model.createInstance("S2", list);
		assertTrue(worksheet.getRows().size() == 1);

		SpreadsheetRow row1 = worksheet.getRows().get(0);
		final String column0value = row1.getVisibleCellValue(worksheet.getColumn(0));
		assertTrue(column0value.equals("1234"));
		final String column1value = row1.getVisibleCellValue(worksheet.getColumn(1));
		assertTrue(column1value.equals(SpreadsheetConstants.DEFAULT_DT_INTEGER));
		final String column2value = row1.getVisibleCellValue(worksheet.getColumn(2));
		assertTrue(column2value.equals(SpreadsheetConstants.DEFAULT_DT_DOUBLE));
		final String column3value = row1.getVisibleCellValue(worksheet.getColumn(3));
		assertTrue(column3value.equalsIgnoreCase(SpreadsheetConstants.DEFAULT_DT_BOOLEAN));

		worksheet.removeRow(row1);
		assertTrue(worksheet.getRows().size() == 0);
	}
}
