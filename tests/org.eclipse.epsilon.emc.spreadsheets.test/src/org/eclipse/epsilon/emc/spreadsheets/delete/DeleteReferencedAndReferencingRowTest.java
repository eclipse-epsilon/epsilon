/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.spreadsheets.delete;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetColumn;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModel;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.test.SharedTestMethods;
import org.eclipse.epsilon.emc.spreadsheets.test.TestModelFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class DeleteReferencedAndReferencingRowTest {
	private SpreadsheetModel model = null;

	public DeleteReferencedAndReferencingRowTest(SpreadsheetModel model) {
		this.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception {
		String pathToFile = "resources/delete/DeleteRowTest.xlsx";
		String pathToConfig = "resources/delete/DeleteRowTestConfig.xml";
		return TestModelFactory.getModelsToTest("", pathToFile, pathToConfig, "DeleteRowTest");
	}

	@Test
	public void testDeleteReferencedAndReferencingRow() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");
		SharedTestMethods.clearWorksheet(model, "Sheet3");

		String col = "c_3";

		SpreadsheetColumn columnSheet1 = model.getWorksheetByType("Sheet1").getColumn(col);
		SpreadsheetColumn columnSheet2 = model.getWorksheetByType("Sheet2").getColumn(col);
		SpreadsheetColumn columnSheet3 = model.getWorksheetByType("Sheet3").getColumn(col);
		SpreadsheetColumn sheet1column0 = model.getWorksheetByType("Sheet1").getColumn("c_1");
		SpreadsheetColumn sheet2column0 = model.getWorksheetByType("Sheet2").getColumn("c_1");
		SpreadsheetColumn sheet3column0 = model.getWorksheetByType("Sheet3").getColumn("c_1");

		SpreadsheetRow rowSheet3_req1 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "1", col, "REQ1");
		SpreadsheetRow rowSheet3_req2 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "2", col, "REQ2");
		SpreadsheetRow rowSheet3_req3 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "3", col, "REQ3");
		assertTrue(model.getAllOfKind("Sheet3").size() == 3);

		SpreadsheetRow rowSheet2_req1 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, rowSheet3_req1);
		SpreadsheetRow rowSheet2_req2 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "2", col, rowSheet3_req2);
		SpreadsheetRow rowSheet2_req3 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "3", col, rowSheet3_req3);
		assertTrue(model.getAllOfKind("Sheet2").size() == 3);

		SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", col, rowSheet2_req1);
		SharedTestMethods.writeRow(model, "Sheet1", "c_1", "2", col, rowSheet2_req2);
		SharedTestMethods.writeRow(model, "Sheet1", "c_1", "3", col, rowSheet2_req3);
		assertTrue(model.getAllOfKind("Sheet1").size() == 3);

		model.deleteElement(rowSheet3_req1);
		assertTrue(model.getAllOfKind("Sheet3").size() == 2);
		assertTrue(model.getAllOfType("Sheet3").get(0).getVisibleCellValue(columnSheet3).equals("REQ2"));
		assertTrue(model.getAllOfType("Sheet3").get(0).getVisibleCellValue(sheet3column0).equals("2"));
		assertTrue(model.getAllOfType("Sheet3").get(1).getVisibleCellValue(columnSheet3).equals("REQ3"));
		assertTrue(model.getAllOfType("Sheet3").get(1).getVisibleCellValue(sheet3column0).equals("3"));
		assertTrue(model.getAllOfKind("Sheet2").size() == 3);
		assertTrue(model.getAllOfType("Sheet2").get(0).getVisibleCellValue(columnSheet2).equals(""));
		assertTrue(model.getAllOfType("Sheet2").get(0).getVisibleCellValue(sheet2column0).equals("1"));
		assertTrue(model.getAllOfType("Sheet2").get(1).getVisibleCellValue(columnSheet2).equals("REQ2"));
		assertTrue(model.getAllOfType("Sheet2").get(1).getVisibleCellValue(sheet2column0).equals("2"));
		assertTrue(model.getAllOfType("Sheet2").get(2).getVisibleCellValue(columnSheet2).equals("REQ3"));
		assertTrue(model.getAllOfType("Sheet2").get(2).getVisibleCellValue(sheet2column0).equals("3"));
		assertTrue(model.getAllOfKind("Sheet1").size() == 3);
		assertTrue(model.getAllOfType("Sheet1").get(0).getVisibleCellValue(columnSheet1).equals(""));
		assertTrue(model.getAllOfType("Sheet1").get(0).getVisibleCellValue(sheet1column0).equals("1"));
		assertTrue(model.getAllOfType("Sheet1").get(1).getVisibleCellValue(columnSheet1).equals("REQ2"));
		assertTrue(model.getAllOfType("Sheet1").get(1).getVisibleCellValue(sheet1column0).equals("2"));
		assertTrue(model.getAllOfType("Sheet1").get(2).getVisibleCellValue(columnSheet1).equals("REQ3"));
		assertTrue(model.getAllOfType("Sheet1").get(2).getVisibleCellValue(sheet1column0).equals("3"));

		model.deleteElement(rowSheet2_req2);
		assertTrue(model.getAllOfKind("Sheet3").size() == 2);
		assertTrue(model.getAllOfType("Sheet3").get(0).getVisibleCellValue(columnSheet3).equals("REQ2"));
		assertTrue(model.getAllOfType("Sheet3").get(0).getVisibleCellValue(sheet3column0).equals("2"));
		assertTrue(model.getAllOfType("Sheet3").get(1).getVisibleCellValue(columnSheet3).equals("REQ3"));
		assertTrue(model.getAllOfType("Sheet3").get(1).getVisibleCellValue(sheet3column0).equals("3"));
		assertTrue(model.getAllOfKind("Sheet2").size() == 2);
		assertTrue(model.getAllOfType("Sheet2").get(0).getVisibleCellValue(columnSheet2).equals(""));
		assertTrue(model.getAllOfType("Sheet2").get(0).getVisibleCellValue(sheet2column0).equals("1"));
		assertTrue(model.getAllOfType("Sheet2").get(1).getVisibleCellValue(columnSheet2).equals("REQ3"));
		assertTrue(model.getAllOfType("Sheet2").get(1).getVisibleCellValue(sheet2column0).equals("3"));
		assertTrue(model.getAllOfKind("Sheet1").size() == 3);
		assertTrue(model.getAllOfType("Sheet1").get(0).getVisibleCellValue(columnSheet1).equals(""));
		assertTrue(model.getAllOfType("Sheet1").get(0).getVisibleCellValue(sheet1column0).equals("1"));
		assertTrue(model.getAllOfType("Sheet1").get(1).getVisibleCellValue(columnSheet1).equals(""));
		assertTrue(model.getAllOfType("Sheet1").get(1).getVisibleCellValue(sheet1column0).equals("2"));
		assertTrue(model.getAllOfType("Sheet1").get(2).getVisibleCellValue(columnSheet1).equals("REQ3"));
		assertTrue(model.getAllOfType("Sheet1").get(2).getVisibleCellValue(sheet1column0).equals("3"));

		model.deleteElement(rowSheet2_req3);
		assertTrue(model.getAllOfKind("Sheet3").size() == 2);
		assertTrue(model.getAllOfType("Sheet3").get(0).getVisibleCellValue(columnSheet3).equals("REQ2"));
		assertTrue(model.getAllOfType("Sheet3").get(0).getVisibleCellValue(sheet3column0).equals("2"));
		assertTrue(model.getAllOfType("Sheet3").get(1).getVisibleCellValue(columnSheet3).equals("REQ3"));
		assertTrue(model.getAllOfType("Sheet3").get(1).getVisibleCellValue(sheet3column0).equals("3"));
		assertTrue(model.getAllOfKind("Sheet2").size() == 1);
		assertTrue(model.getAllOfType("Sheet2").get(0).getVisibleCellValue(columnSheet2).equals(""));
		assertTrue(model.getAllOfType("Sheet2").get(0).getVisibleCellValue(sheet2column0).equals("1"));
		assertTrue(model.getAllOfKind("Sheet1").size() == 3);
		assertTrue(model.getAllOfType("Sheet1").get(0).getVisibleCellValue(columnSheet1).equals(""));
		assertTrue(model.getAllOfType("Sheet1").get(0).getVisibleCellValue(sheet1column0).equals("1"));
		assertTrue(model.getAllOfType("Sheet1").get(1).getVisibleCellValue(columnSheet1).equals(""));
		assertTrue(model.getAllOfType("Sheet1").get(1).getVisibleCellValue(sheet1column0).equals("2"));
		assertTrue(model.getAllOfType("Sheet1").get(2).getVisibleCellValue(columnSheet1).equals(""));
		assertTrue(model.getAllOfType("Sheet1").get(2).getVisibleCellValue(sheet1column0).equals("3"));

		model.deleteElement(rowSheet3_req3);
		assertTrue(model.getAllOfKind("Sheet3").size() == 1);
		assertTrue(model.getAllOfType("Sheet3").get(0).getVisibleCellValue(columnSheet3).equals("REQ2"));
		assertTrue(model.getAllOfType("Sheet3").get(0).getVisibleCellValue(sheet3column0).equals("2"));
		assertTrue(model.getAllOfKind("Sheet2").size() == 1);
		assertTrue(model.getAllOfType("Sheet2").get(0).getVisibleCellValue(columnSheet2).equals(""));
		assertTrue(model.getAllOfType("Sheet2").get(0).getVisibleCellValue(sheet2column0).equals("1"));
		assertTrue(model.getAllOfKind("Sheet1").size() == 3);
		assertTrue(model.getAllOfType("Sheet1").get(0).getVisibleCellValue(columnSheet1).equals(""));
		assertTrue(model.getAllOfType("Sheet1").get(0).getVisibleCellValue(sheet1column0).equals("1"));
		assertTrue(model.getAllOfType("Sheet1").get(1).getVisibleCellValue(columnSheet1).equals(""));
		assertTrue(model.getAllOfType("Sheet1").get(1).getVisibleCellValue(sheet1column0).equals("2"));
		assertTrue(model.getAllOfType("Sheet1").get(2).getVisibleCellValue(columnSheet1).equals(""));
		assertTrue(model.getAllOfType("Sheet1").get(2).getVisibleCellValue(sheet1column0).equals("3"));
	}

}
