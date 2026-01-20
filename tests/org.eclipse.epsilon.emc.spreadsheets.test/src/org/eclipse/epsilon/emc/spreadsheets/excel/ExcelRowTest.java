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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.test.SharedTestMethods;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExcelRowTest {
	private final static String PATH_TO_FILE = SharedTestMethods.getBasePath() + "resources/excel/RowTest.xlsx";
	private final static String PATH_TO_CONFIG = SharedTestMethods.getBasePath()
		+ "resources/excel/ModelTestConfig.xml";

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
	public void testBelongsToModel() throws EolRuntimeException {
		ExcelWorksheet worksheet = (ExcelWorksheet) model.getWorksheetByType("Sheet1");
		ExcelRow row = (ExcelRow) worksheet.getRows().get(0);
		assertSame(model, row.getModel());
	}

	@Test
	public void testCannotCreateRowWithoutWorksheet() throws EolRuntimeException {
		try {
			new ExcelRow(null, null);
			fail("Should not be able to create row without valid worksheet");
		}
		catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testUnknownColumnIndex() {
		try {
			testReadType(100);
			fail();
		}
		catch (Exception e) {
			assertTrue(true);
		}
	}

	@Test
	public void testReadString() {
		try {
			String value = testReadType(0);
			assertTrue(value.equals("value"));
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testReadInteger() {
		try {
			String value = testReadType(1);
			assertEquals("12345", value);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testReadDouble() {
		try {
			String value = testReadType(2);
			assertTrue(value.equals("1.15"));
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testReadBoolean() {
		try {
			String value = testReadType(3);
			assertTrue(value.equalsIgnoreCase("TRUE"));
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testReadEvaluatedFormula() {
		try {
			String value = testReadType(4);
			assertEquals("3", value);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testReadBlank() {
		try {
			String value = testReadType(5);
			assertEquals("", value);
		}
		catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public String testReadType(final int columnIndex) throws Exception {
		List<SpreadsheetRow> rows = (List<SpreadsheetRow>) model.getAllOfKind("Sheet1");
		SpreadsheetRow row = rows.get(0);
		return (String) model.getPropertyGetter().invoke(row, "c_" + columnIndex, null);
	}

	@Test
	public void testColumnIsNull() throws Exception {
		ExcelWorksheet worksheet = (ExcelWorksheet) model.getWorksheetByType("Sheet1");
		ExcelRow firstRow = (ExcelRow) worksheet.getRows().get(0);
		try {
			firstRow.getVisibleCellValue(null);
			fail("Column input must not be null");
		}
		catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testReadWhereCellIsEmpty() throws Exception {
		ExcelWorksheet worksheet = (ExcelWorksheet) model.getWorksheetByType("Sheet1");
		ExcelRow firstRow = (ExcelRow) worksheet.getRows().get(0);
		firstRow.getVisibleCellValue(new ExcelColumn(worksheet, 100));
	}

	@Test
	public void testOverwriteCellValue() throws Exception {
		ExcelWorksheet worksheet = (ExcelWorksheet) model.getWorksheetByType("Sheet1");
		ExcelRow firstRow = (ExcelRow) worksheet.getRows().get(0);
		ExcelColumn column = (ExcelColumn) worksheet.getColumn(0);
		String oldCellValue = firstRow.getVisibleCellValue(column);
		firstRow.overwriteCellValue(column, oldCellValue + "_new");
		firstRow.overwriteCellValue(column, oldCellValue);
	}

	@Test
	public void testEquals() throws Exception {
		ExcelWorksheet worksheet1 = (ExcelWorksheet) model.getWorksheetByType("Sheet1");
		ExcelRow firstRow1 = (ExcelRow) worksheet1.getRows().get(0);
		ExcelRow firstRow2 = (ExcelRow) worksheet1.getRows().get(0);
		assertTrue(firstRow1.equals(firstRow1));
		assertTrue(firstRow1.equals(firstRow2));
		assertTrue(firstRow2.equals(firstRow1));
		firstRow2.worksheet = null;
		assertTrue(!firstRow1.equals(firstRow2));
		assertTrue(!firstRow2.equals(firstRow1));
		assertTrue(!firstRow1.equals(null));
		assertTrue(!firstRow1.equals(worksheet1));
		assertTrue(!worksheet1.equals(firstRow1));
		SharedTestMethods.clearWorksheet(model, "Sheet2");
		ExcelWorksheet worksheet2 = (ExcelWorksheet) model.getWorksheetByType("Sheet2");
		SharedTestMethods.writeRow(model, "Sheet2", "c_0", "1", "c_0", "v1");
		ExcelRow firstRowSheet2 = (ExcelRow) worksheet2.getRows().get(0);
		assertTrue(!firstRow1.equals(firstRowSheet2));
		SharedTestMethods.writeRow(model, "Sheet2", "c_0", "2", "c_0", "v2");
		ExcelRow secondRowSheet2 = (ExcelRow) worksheet2.getRows().get(1);
		assertTrue(!firstRow1.equals(secondRowSheet2));
		firstRow1.worksheet = null;
		assertTrue(firstRow1.equals(firstRow2));
		assertTrue(firstRow2.equals(firstRow1));
		SharedTestMethods.clearWorksheet(model, "Sheet2");
	}

}
