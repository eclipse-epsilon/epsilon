/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.spreadsheets.excel;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetColumn;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetDataType;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetWorksheet;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetWorksheetHeader;
import org.eclipse.epsilon.emc.spreadsheets.test.SharedTestMethods;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExcelWorksheetTest {
	private final static String PATH_TO_FILE = SharedTestMethods.getBasePath() + "resources/excel/WorksheetTest.xlsx";
	private final static String PATH_TO_CONFIG = SharedTestMethods.getBasePath()
		+ "resources/excel/WorksheetTestConfig.xml";

	private static ExcelModel model = null;

	@BeforeClass
	public static void createModel() throws Exception {
		model = new ExcelModel();
		model.setName("M");
		model.setSpreadsheetFile(PATH_TO_FILE);
		model.setConfigurationFile(PATH_TO_CONFIG);
		model.load();
	}

	@Test
	public void testColumnComparatorOnModel() throws Exception {
		ExcelModel model1 = new ExcelModel();
		model1.setName("M1");
		model1.setSpreadsheetFile(PATH_TO_FILE);
		model1.setConfigurationFile(PATH_TO_CONFIG);
		model1.load();

		assertTrue(model1.getName().equals("M1"));

		ExcelModel model2 = new ExcelModel();
		model2.setName("M2");
		model2.setSpreadsheetFile(PATH_TO_FILE);
		model2.setConfigurationFile(PATH_TO_CONFIG);
		model2.load();

		assertTrue(model2.getName().equals("M2"));

		SpreadsheetWorksheetHeader header = model1.getWorksheetByType("Sheet1").getHeader();
		int headerSize = header.getColumns().size();
		header.addColumn(model2.getWorksheetByType("Sheet1").getColumn(0));
		System.out.println(header.toString());
		assertTrue(headerSize == header.getColumns().size());
	}

	@Test
	public void testWriteAndDeleteRows() throws Exception {
		ExcelWorksheet worksheet = (ExcelWorksheet) model.getWorksheetByType("Sheet1");
		for (SpreadsheetRow row : worksheet.getRows()) {
			model.deleteElement(row);
		}

		List<Object> list = new ArrayList<>();
		Map<String, Object> values = new HashMap<>();
		values.put("c_0", "value0");
		values.put("c_1", "value1");
		values.put("c_3", "value3");
		list.add(values);
		model.createInstance("Sheet1", list);
		model.createInstance("Sheet1", list);

		assertTrue(model.getAllOfType("Sheet1").size() == 2);

		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind("Sheet1");
		SpreadsheetRow row = rows.get(0);
		final String column0value = row.getVisibleCellValue(worksheet.getColumn(0));
		assertTrue(column0value.equals("value0"));
		final String column1value = row.getVisibleCellValue(worksheet.getColumn(1));
		assertTrue(column1value.equals("value1"));
		final String column2value = row.getVisibleCellValue(worksheet.getColumn(2));
		assertTrue(column2value.equals(worksheet.getDefaultEmptyCellValue()));
		final String column3value = row.getVisibleCellValue(worksheet.getColumn(3));
		assertTrue(column3value.equals("value3"));

		model.deleteElement(row);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		model.deleteElement(rows.get(1));
		assertTrue(model.getAllOfType("Sheet1").size() == 0);

		// model.store();
	}

	@Test
	public void testDeleteRowNull() throws Exception {
		ExcelWorksheet worksheet = (ExcelWorksheet) model.getWorksheetByType("Sheet1");
		worksheet.removeRow(null);
	}

	@Test
	public void testHeaderDoesNotHaveColumn() throws Exception {
		ExcelWorksheet worksheet1 = (ExcelWorksheet) model.getWorksheetByType("Sheet1");
		ExcelWorksheet worksheet2 = (ExcelWorksheet) model.getWorksheetByType("Sheet2");
		SpreadsheetWorksheetHeader header = worksheet1.getHeader();
		SpreadsheetColumn column = header.getColumn("UNKNOWN_COL");
		assertTrue(column == null);
		assertTrue(header.contains(worksheet1.getColumn(0)));
		assertTrue(!header.contains(worksheet2.getColumn(0)));
		assertTrue(header.getWorksheet() == worksheet1);
	}

	@Test
	public void testVerifyColumnMetadata() throws Exception {
		ExcelWorksheet worksheet = (ExcelWorksheet) model.getWorksheetByType("Sheet2");
		assertTrue(worksheet.getColumn(0).getName().equals("column0"));
		assertTrue(worksheet.getColumn(0).getAlias() == null);
		assertTrue(worksheet.getColumn(0).getDataType() == SpreadsheetDataType.STRING);
		assertTrue(!worksheet.getColumn(0).isMany());
		assertTrue(worksheet.getColumn(0).isNotMany());
		assertTrue(worksheet.getColumn(1).getName() == null);
		assertTrue(worksheet.getColumn(1).getAlias().equals("alias1"));
		assertTrue(worksheet.getColumn(1).getDataType() == SpreadsheetDataType.STRING);
		assertTrue(!worksheet.getColumn(1).isMany());
		assertTrue(worksheet.getColumn(1).isNotMany());
	}

	@Test
	public void testSplitColumnNameIfContainsDash_FromWorksheetNoConfig() {
		SpreadsheetWorksheet worksheet = model.getWorksheetByType("Sheet1");
		assertTrue(worksheet.getColumn(10).getName().equals("abc"));
	}

	@Test
	public void testSplitColumnNameIfContainsDash_FromConfig() {
		SpreadsheetWorksheet worksheet = model.getWorksheetByType("Sheet1");
		assertTrue(worksheet.getColumn(11).getName().equals("abcd"));
	}

	@Test
	public void testSplitColumnNameIfContainsDash_FromConfig_StartsWithDash() {
		SpreadsheetWorksheet worksheet = model.getWorksheetByType("Sheet1");
		assertTrue(worksheet.getColumn(12).getName() == null);
	}

}
