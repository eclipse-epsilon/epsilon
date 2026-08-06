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

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetColumn;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModel;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetPropertySetter;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.test.SharedTestMethods;
import org.eclipse.epsilon.emc.spreadsheets.test.TestModelFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CascadeUpdatesCellsNotManyPropertySetterTest {
	private SpreadsheetModel model = null;

	public CascadeUpdatesCellsNotManyPropertySetterTest(SpreadsheetModel model) {
		this.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception {
		String PATH_TO_FILE = "resources/propertysetter/PropertySetterTest.xlsx";
		String CONFIG = "resources/propertysetter/CascadeUpdatesCellsNotManyPropertySetterTest.xml";
		return TestModelFactory.getModelsToTest("", PATH_TO_FILE, CONFIG, "PropertySetterTest");
	}

	@Test
	public void testCellsNotManyToNotMany() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");
		SharedTestMethods.clearWorksheet(model, "Sheet3");

		String col = "c_2";

		SpreadsheetColumn columnSheet1 = model.getWorksheetByType("Sheet1").getColumn(col);
		SpreadsheetColumn columnSheet2 = model.getWorksheetByType("Sheet2").getColumn(col);
		SpreadsheetColumn columnSheet3 = model.getWorksheetByType("Sheet3").getColumn(col);

		SpreadsheetRow rowSheet3 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "1", col, "REQ1");
		assertTrue(rowSheet3.getVisibleCellValue(columnSheet3).equals("REQ1"));
		assertTrue(rowSheet3.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(rowSheet3.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("1"));

		SpreadsheetRow rowSheet3_req3 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "2", col, "REQ3");
		assertTrue(rowSheet3_req3.getVisibleCellValue(columnSheet3).equals("REQ3"));
		assertTrue(rowSheet3_req3.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(rowSheet3_req3.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("2"));

		SpreadsheetRow rowSheet2 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, rowSheet3);
		assertTrue(rowSheet2.getVisibleCellValue(columnSheet2).equals("REQ1"));
		assertTrue(rowSheet2.getAllVisibleCellValuesAsIs(columnSheet2).size() == 1);
		assertTrue(rowSheet2.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));

		SpreadsheetRow rowSheet1 = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", col, rowSheet2);
		assertTrue(rowSheet1.getVisibleCellValue(columnSheet1).equals("REQ1"));
		assertTrue(rowSheet1.getAllVisibleCellValuesAsIs(columnSheet1).size() == 1);
		assertTrue(rowSheet1.getVisibleCellValue(model.getWorksheetByType("Sheet1").getColumn("c_1")).equals("1"));

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(rowSheet3, columnSheet3.getPrefixedIndex(), "REQ2", null);

		assertTrue(rowSheet3.getVisibleCellValue(columnSheet3).equals("REQ2"));
		assertTrue(rowSheet3_req3.getVisibleCellValue(columnSheet3).equals("REQ3"));
		assertTrue(rowSheet2.getVisibleCellValue(columnSheet2).equals("REQ2"));
		assertTrue(rowSheet1.getVisibleCellValue(columnSheet1).equals("REQ1"));
	}

	@Test
	public void testCellsNotManyToNotMany_CascadeToSecondLevel() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");
		SharedTestMethods.clearWorksheet(model, "Sheet3");

		String col = "c_4";

		SpreadsheetColumn columnSheet1 = model.getWorksheetByType("Sheet1").getColumn(col);
		SpreadsheetColumn columnSheet2 = model.getWorksheetByType("Sheet2").getColumn(col);
		SpreadsheetColumn columnSheet3 = model.getWorksheetByType("Sheet3").getColumn(col);

		SpreadsheetRow rowSheet3 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "1", col, "REQ1");
		assertTrue(rowSheet3.getVisibleCellValue(columnSheet3).equals("REQ1"));
		assertTrue(rowSheet3.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(rowSheet3.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("1"));

		SpreadsheetRow rowSheet3_req3 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "2", col, "REQ3");
		assertTrue(rowSheet3_req3.getVisibleCellValue(columnSheet3).equals("REQ3"));
		assertTrue(rowSheet3_req3.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(rowSheet3_req3.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("2"));

		SpreadsheetRow rowSheet2 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, rowSheet3);
		assertTrue(rowSheet2.getVisibleCellValue(columnSheet2).equals("REQ1"));
		assertTrue(rowSheet2.getAllVisibleCellValuesAsIs(columnSheet2).size() == 1);
		assertTrue(rowSheet2.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));

		SpreadsheetRow rowSheet1 = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", col, rowSheet2);
		assertTrue(rowSheet1.getVisibleCellValue(columnSheet1).equals("REQ1"));
		assertTrue(rowSheet1.getAllVisibleCellValuesAsIs(columnSheet1).size() == 1);
		assertTrue(rowSheet1.getVisibleCellValue(model.getWorksheetByType("Sheet1").getColumn("c_1")).equals("1"));

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(rowSheet3, columnSheet3.getPrefixedIndex(), "REQ2", null);

		assertTrue(rowSheet3.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(rowSheet3.getVisibleCellValue(columnSheet3).equals("REQ2"));
		assertTrue(rowSheet3_req3.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(rowSheet3_req3.getVisibleCellValue(columnSheet3).equals("REQ3"));
		assertTrue(rowSheet2.getAllVisibleCellValuesAsIs(columnSheet2).size() == 1);
		assertTrue(rowSheet2.getVisibleCellValue(columnSheet2).equals("REQ2"));
		assertTrue(rowSheet1.getAllVisibleCellValuesAsIs(columnSheet1).size() == 1);
		assertTrue(rowSheet1.getVisibleCellValue(columnSheet1).equals("REQ2"));
	}

}
