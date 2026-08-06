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
public class ReferencedCellPropertySetterTest {
	private SpreadsheetModel model = null;

	public ReferencedCellPropertySetterTest(SpreadsheetModel model) {
		this.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception {
		String PATH_TO_FILE = "resources/propertysetter/PropertySetterTest.xlsx";
		String CONFIG = "resources/propertysetter/ReferencedCellPropertySetterTest.xml";
		return TestModelFactory.getModelsToTest("", PATH_TO_FILE, CONFIG, "PropertySetterTest");
	}

	@Test
	public void testNotMany_SetToNull() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_2";

		SpreadsheetColumn column = model.getWorksheetByType("Sheet2").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, "v1, v2");
		assertTrue(referencedRow1.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "2", col, "v3, v4");
		assertTrue(referencedRow2.getVisibleCellValue(column).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("2"));

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencedRow1, column.getPrefixedIndex(), null, null);

		assertTrue(referencedRow1.getVisibleCellValue(column).equals("null"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));

	}

	@Test
	public void testNotMany_SetToEmpty() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_2";

		SpreadsheetColumn column = model.getWorksheetByType("Sheet2").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, "v1, v2");
		assertTrue(referencedRow1.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "2", col, "v3, v4");
		assertTrue(referencedRow2.getVisibleCellValue(column).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("2"));

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencedRow1, column.getPrefixedIndex(), "", null);

		assertTrue(referencedRow1.getVisibleCellValue(column).equals(""));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(column).size() == 0);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));
		assertTrue(referencedRow2.getVisibleCellValue(column).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("2"));
	}

	@Test
	public void testNotMany_SetToSpace() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_2";

		SpreadsheetColumn column = model.getWorksheetByType("Sheet2").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, "v1, v2");
		assertTrue(referencedRow1.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "2", col, "v3, v4");
		assertTrue(referencedRow2.getVisibleCellValue(column).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("2"));

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencedRow1, column.getPrefixedIndex(), " ", null);

		assertTrue(referencedRow1.getVisibleCellValue(column).equals(" "));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));
		assertTrue(referencedRow2.getVisibleCellValue(column).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("2"));
	}

	@Test
	public void testNotMany() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_2";

		SpreadsheetColumn column = model.getWorksheetByType("Sheet2").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, "v1, v2");
		assertTrue(referencedRow1.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "2", col, "v3, v4");
		assertTrue(referencedRow2.getVisibleCellValue(column).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("2"));

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencedRow1, column.getPrefixedIndex(), "v1, v2, v3", null);

		assertTrue(referencedRow1.getVisibleCellValue(column).equals("v1, v2, v3"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));
		assertTrue(referencedRow2.getVisibleCellValue(column).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("2"));
	}

	@Test
	public void testMany_SetToNull() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_3";

		SpreadsheetColumn column = model.getWorksheetByType("Sheet2").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, "v1, v2");
		assertTrue(referencedRow1.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(column).size() == 2);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "2", col, "v3, v4");
		assertTrue(referencedRow2.getVisibleCellValue(column).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(column).size() == 2);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("2"));

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencedRow1, column.getPrefixedIndex(), null, null);

		assertTrue(referencedRow1.getVisibleCellValue(column).equals("null"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));
	}

	@Test
	public void testMany_SetToEmpty() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_3";

		SpreadsheetColumn column = model.getWorksheetByType("Sheet2").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, "v1, v2");
		assertTrue(referencedRow1.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(column).size() == 2);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "2", col, "v3, v4");
		assertTrue(referencedRow2.getVisibleCellValue(column).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(column).size() == 2);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("2"));

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencedRow1, column.getPrefixedIndex(), "", null);

		assertTrue(referencedRow1.getVisibleCellValue(column).equals(""));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(column).size() == 0);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));
		assertTrue(referencedRow2.getVisibleCellValue(column).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(column).size() == 2);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("2"));
	}

	@Test
	public void testMany_SetToSpace() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_3";

		SpreadsheetColumn column = model.getWorksheetByType("Sheet2").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, "v1, v2");
		assertTrue(referencedRow1.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(column).size() == 2);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "2", col, "v3, v4");
		assertTrue(referencedRow2.getVisibleCellValue(column).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(column).size() == 2);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("2"));

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencedRow1, column.getPrefixedIndex(), " ", null);

		assertTrue(referencedRow1.getVisibleCellValue(column).equals(" "));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));
		assertTrue(referencedRow2.getVisibleCellValue(column).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(column).size() == 2);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("2"));
	}

	@Test
	public void testMany_SetToSingleValue() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_3";

		SpreadsheetColumn column = model.getWorksheetByType("Sheet2").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, "v1, v2");
		assertTrue(referencedRow1.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(column).size() == 2);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "2", col, "v3, v4");
		assertTrue(referencedRow2.getVisibleCellValue(column).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(column).size() == 2);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("2"));

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencedRow1, column.getPrefixedIndex(), "v5", null);

		assertTrue(referencedRow1.getVisibleCellValue(column).equals("v5"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(column).size() == 1);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));
		assertTrue(referencedRow2.getVisibleCellValue(column).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(column).size() == 2);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("2"));
	}

	@Test
	public void testMany_SetToMultipleValues() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_3";

		SpreadsheetColumn column = model.getWorksheetByType("Sheet2").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, "v1, v2");
		assertTrue(referencedRow1.getVisibleCellValue(column).equals("v1, v2"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(column).size() == 2);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "2", col, "v3, v4");
		assertTrue(referencedRow2.getVisibleCellValue(column).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(column).size() == 2);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("2"));

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencedRow1, column.getPrefixedIndex(), "v1,v6,v8", null);

		assertTrue(referencedRow1.getVisibleCellValue(column).equals("v1,v6,v8"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(column).size() == 3);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));
		assertTrue(referencedRow2.getVisibleCellValue(column).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(column).size() == 2);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("2"));
	}
}
