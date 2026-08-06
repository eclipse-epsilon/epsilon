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
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
public class ReferencingCellPropertySetterTest {
	private static SpreadsheetModel model = null;

	public ReferencingCellPropertySetterTest(SpreadsheetModel model) {
		ReferencingCellPropertySetterTest.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception {
		String PATH_TO_FILE = "resources/propertysetter/PropertySetterTest.xlsx";
		String CONFIG = "resources/propertysetter/ReferencingCellPropertySetterTest.xml";
		return TestModelFactory.getModelsToTest("", PATH_TO_FILE, CONFIG, "PropertySetterTest");
	}

	@Test
	public void testReferencingCellNotManyReferencedNotMany() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet3");

		String col = "c_2";

		SpreadsheetColumn columnSheet3 = model.getWorksheetByType("Sheet3").getColumn(col);
		SpreadsheetColumn columnSheet1 = model.getWorksheetByType("Sheet1").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "1", col, "v1, v2");
		assertTrue(referencedRow1.getVisibleCellValue(columnSheet3).equals("v1, v2"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "2", col, "v3, v4");
		assertTrue(referencedRow2.getVisibleCellValue(columnSheet3).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("2"));

		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", col, referencedRow1);
		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v1, v2"));
		assertTrue(referencingRow.getAllVisibleCellValuesAsIs(columnSheet1).size() == 1);

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencingRow, columnSheet1.getPrefixedIndex(), referencedRow2, null);

		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v3, v4"));
		assertTrue(referencingRow.getVisibleCellValue(model.getWorksheetByType("Sheet1").getColumn("c_1")).equals("1"));
	}

	@Test
	public void testReferencingCellNotManyReferencedNotMany_MultipleRows() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet3");

		String col = "c_2";

		SpreadsheetColumn columnSheet3 = model.getWorksheetByType("Sheet3").getColumn(col);
		SpreadsheetColumn columnSheet1 = model.getWorksheetByType("Sheet1").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "1", col, "v1, v2");
		assertTrue(referencedRow1.getVisibleCellValue(columnSheet3).equals("v1, v2"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "2", col, "v3, v4");
		assertTrue(referencedRow2.getVisibleCellValue(columnSheet3).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("2"));

		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", col, referencedRow1);
		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v1, v2"));
		assertTrue(referencingRow.getAllVisibleCellValuesAsIs(columnSheet1).size() == 1);

		List<SpreadsheetRow> referencedRows = new ArrayList<>(Arrays.asList(referencedRow1, referencedRow2));

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencingRow, columnSheet1.getPrefixedIndex(), referencedRows, null);

		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v1, v2"));
		assertTrue(referencingRow.getVisibleCellValue(model.getWorksheetByType("Sheet1").getColumn("c_1")).equals("1"));
	}

	@Test
	public void testReferencingCellNotManyReferencedMany_OneValue() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet3");

		String col = "c_3";

		SpreadsheetColumn columnSheet3 = model.getWorksheetByType("Sheet3").getColumn(col);
		SpreadsheetColumn columnSheet1 = model.getWorksheetByType("Sheet1").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "1", col, "v1");
		assertTrue(referencedRow1.getVisibleCellValue(columnSheet3).equals("v1"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "2", col, "v3");
		assertTrue(referencedRow2.getVisibleCellValue(columnSheet3).equals("v3"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("2"));

		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", col, referencedRow1);
		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v1"));
		assertTrue(referencingRow.getAllVisibleCellValuesAsIs(columnSheet1).size() == 1);

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencingRow, columnSheet1.getPrefixedIndex(), referencedRow2, null);

		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v3"));
		assertTrue(referencingRow.getVisibleCellValue(model.getWorksheetByType("Sheet1").getColumn("c_1")).equals("1"));
	}

	@Test
	public void testReferencingCellNotManyReferencedMany_TwoValues() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet3");

		String col = "c_3";

		SpreadsheetColumn columnSheet3 = model.getWorksheetByType("Sheet3").getColumn(col);
		SpreadsheetColumn columnSheet1 = model.getWorksheetByType("Sheet1").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "1", col, "v1, v2");
		assertTrue(referencedRow1.getVisibleCellValue(columnSheet3).equals("v1, v2"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(columnSheet3).size() == 2);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "2", col, "v3, v4");
		assertTrue(referencedRow2.getVisibleCellValue(columnSheet3).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(columnSheet3).size() == 2);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("2"));

		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", col, referencedRow1);
		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v1"));
		assertTrue(referencingRow.getAllVisibleCellValuesAsIs(columnSheet1).size() == 1);

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencingRow, columnSheet1.getPrefixedIndex(), referencedRow2, null);

		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v3"));
		assertTrue(referencingRow.getVisibleCellValue(model.getWorksheetByType("Sheet1").getColumn("c_1")).equals("1"));
	}

	@Test
	public void testReferencingCellNotManyReferencedMany_MultipleRows() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet3");

		String col = "c_3";

		SpreadsheetColumn columnSheet3 = model.getWorksheetByType("Sheet3").getColumn(col);
		SpreadsheetColumn columnSheet1 = model.getWorksheetByType("Sheet1").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "1", col, "v1, v2");
		assertTrue(referencedRow1.getVisibleCellValue(columnSheet3).equals("v1, v2"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(columnSheet3).size() == 2);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "2", col, "v3, v4");
		assertTrue(referencedRow2.getVisibleCellValue(columnSheet3).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(columnSheet3).size() == 2);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("2"));

		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", col, referencedRow1);
		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v1"));
		assertTrue(referencingRow.getAllVisibleCellValuesAsIs(columnSheet1).size() == 1);

		List<SpreadsheetRow> referencedRows = new ArrayList<>(Arrays.asList(referencedRow1, referencedRow2));

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencingRow, columnSheet1.getPrefixedIndex(), referencedRows, null);

		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v1"));
		assertTrue(referencingRow.getVisibleCellValue(model.getWorksheetByType("Sheet1").getColumn("c_1")).equals("1"));
	}

	@Test
	public void testReferencingCellManyReferencedNotMany_OneValue() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet3");

		String col = "c_4";

		SpreadsheetColumn columnSheet3 = model.getWorksheetByType("Sheet3").getColumn(col);
		SpreadsheetColumn columnSheet1 = model.getWorksheetByType("Sheet1").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "1", col, "v1");
		assertTrue(referencedRow1.getVisibleCellValue(columnSheet3).equals("v1"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "2", col, "v3");
		assertTrue(referencedRow2.getVisibleCellValue(columnSheet3).equals("v3"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("2"));

		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", col, referencedRow1);
		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v1"));
		assertTrue(referencingRow.getAllVisibleCellValuesAsIs(columnSheet1).size() == 1);

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencingRow, columnSheet1.getPrefixedIndex(), referencedRow2, null);

		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v3"));
		assertTrue(referencingRow.getVisibleCellValue(model.getWorksheetByType("Sheet1").getColumn("c_1")).equals("1"));
	}

	@Test
	public void testReferencingCellManyReferencedNotMany_TwoValues() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet3");

		String col = "c_4";

		SpreadsheetColumn columnSheet3 = model.getWorksheetByType("Sheet3").getColumn(col);
		SpreadsheetColumn columnSheet1 = model.getWorksheetByType("Sheet1").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "1", col, "v1, v2");
		assertTrue(referencedRow1.getVisibleCellValue(columnSheet3).equals("v1, v2"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "2", col, "v3, v4");
		assertTrue(referencedRow2.getVisibleCellValue(columnSheet3).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("2"));

		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", col, referencedRow1);
		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v1, v2"));
		assertTrue(referencingRow.getAllVisibleCellValuesAsIs(columnSheet1).size() == 2);

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencingRow, columnSheet1.getPrefixedIndex(), referencedRow2, null);

		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v3, v4"));
		assertTrue(referencingRow.getAllVisibleCellValuesAsIs(columnSheet1).size() == 2);
		assertTrue(referencingRow.getVisibleCellValue(model.getWorksheetByType("Sheet1").getColumn("c_1")).equals("1"));
	}

	@Test
	public void testReferencingCellManyReferencedNotMany_MultipleRows() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet3");

		String col = "c_4";

		SpreadsheetColumn columnSheet3 = model.getWorksheetByType("Sheet3").getColumn(col);
		SpreadsheetColumn columnSheet1 = model.getWorksheetByType("Sheet1").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "1", col, "v1, v2");
		assertTrue(referencedRow1.getVisibleCellValue(columnSheet3).equals("v1, v2"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "2", col, "v3, v4");
		assertTrue(referencedRow2.getVisibleCellValue(columnSheet3).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("2"));

		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", col, referencedRow1);
		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v1, v2"));
		assertTrue(referencingRow.getAllVisibleCellValuesAsIs(columnSheet1).size() == 2);

		List<SpreadsheetRow> referencedRows = new ArrayList<>(Arrays.asList(referencedRow1, referencedRow2));

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencingRow, columnSheet1.getPrefixedIndex(), referencedRows, null);

		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v1, v2,v3, v4"));
		assertTrue(referencingRow.getAllVisibleCellValuesAsIs(columnSheet1).size() == 4);
		assertTrue(referencingRow.getVisibleCellValue(model.getWorksheetByType("Sheet1").getColumn("c_1")).equals("1"));
	}

	@Test
	public void testReferencingCellManyReferencedMany_OneValue() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet3");

		String col = "c_5";

		SpreadsheetColumn columnSheet3 = model.getWorksheetByType("Sheet3").getColumn(col);
		SpreadsheetColumn columnSheet1 = model.getWorksheetByType("Sheet1").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "1", col, "v1");
		assertTrue(referencedRow1.getVisibleCellValue(columnSheet3).equals("v1"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "2", col, "v3");
		assertTrue(referencedRow2.getVisibleCellValue(columnSheet3).equals("v3"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(columnSheet3).size() == 1);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("2"));

		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", col, referencedRow1);
		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v1"));
		assertTrue(referencingRow.getAllVisibleCellValuesAsIs(columnSheet1).size() == 1);

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencingRow, columnSheet1.getPrefixedIndex(), referencedRow2, null);

		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v3"));
		assertTrue(referencingRow.getVisibleCellValue(model.getWorksheetByType("Sheet1").getColumn("c_1")).equals("1"));
	}

	@Test
	public void testReferencingCellManyReferencedMany_TwoValues() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet3");

		String col = "c_5";

		SpreadsheetColumn columnSheet3 = model.getWorksheetByType("Sheet3").getColumn(col);
		SpreadsheetColumn columnSheet1 = model.getWorksheetByType("Sheet1").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "1", col, "v1, v2");
		assertTrue(referencedRow1.getVisibleCellValue(columnSheet3).equals("v1, v2"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(columnSheet3).size() == 2);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "2", col, "v3, v4");
		assertTrue(referencedRow2.getVisibleCellValue(columnSheet3).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(columnSheet3).size() == 2);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("2"));

		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", col, referencedRow1);
		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v1, v2"));
		assertTrue(referencingRow.getAllVisibleCellValuesAsIs(columnSheet1).size() == 2);

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencingRow, columnSheet1.getPrefixedIndex(), referencedRow2, null);

		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v3,v4"));
		assertTrue(referencingRow.getAllVisibleCellValuesAsIs(columnSheet1).size() == 2);
		assertTrue(referencingRow.getVisibleCellValue(model.getWorksheetByType("Sheet1").getColumn("c_1")).equals("1"));
	}

	@Test
	public void testInvalidReferencedRow() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet3");

		String col = "c_5";

		SpreadsheetColumn columnSheet3 = model.getWorksheetByType("Sheet3").getColumn(col);
		SpreadsheetColumn columnSheet1 = model.getWorksheetByType("Sheet1").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "1", col, "v1, v2");
		assertTrue(referencedRow1.getVisibleCellValue(columnSheet3).equals("v1, v2"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(columnSheet3).size() == 2);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("1"));

		SpreadsheetRow invalidReferencedRow = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, "123");
		try {
			invalidReferencedRow.getVisibleCellValue(columnSheet3).equals("123");
			fail("Column from another worksheet");
		}
		catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		SpreadsheetColumn columnSheet2 = model.getWorksheetByType("Sheet2").getColumn(col);
		assertTrue(invalidReferencedRow.getAllVisibleCellValuesAsIs(columnSheet2).size() == 1);
		assertTrue(
			invalidReferencedRow.getVisibleCellValue(model.getWorksheetByType("Sheet2").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "1", col, referencedRow1);
		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v1, v2"));
		assertTrue(referencingRow.getAllVisibleCellValuesAsIs(columnSheet1).size() == 2);

		try {
			final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
			setter.invoke(referencingRow, columnSheet1.getPrefixedIndex(), invalidReferencedRow, null);
			fail("Expecting not to be able to write row from another worksheet");
		}
		catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testReferencingCellManyReferencedMany_MultipleRows() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet3");

		String col = "c_5";

		SpreadsheetColumn columnSheet3 = model.getWorksheetByType("Sheet3").getColumn(col);
		SpreadsheetColumn columnSheet1 = model.getWorksheetByType("Sheet1").getColumn(col);

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "1", col, "v1, v2");
		assertTrue(referencedRow1.getVisibleCellValue(columnSheet3).equals("v1, v2"));
		assertTrue(referencedRow1.getAllVisibleCellValuesAsIs(columnSheet3).size() == 2);
		assertTrue(referencedRow1.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("1"));

		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "2", col, "v3, v4");
		assertTrue(referencedRow2.getVisibleCellValue(columnSheet3).equals("v3, v4"));
		assertTrue(referencedRow2.getAllVisibleCellValuesAsIs(columnSheet3).size() == 2);
		assertTrue(referencedRow2.getVisibleCellValue(model.getWorksheetByType("Sheet3").getColumn("c_1")).equals("2"));

		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", col, referencedRow1);
		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v1, v2"));
		assertTrue(referencingRow.getAllVisibleCellValuesAsIs(columnSheet1).size() == 2);

		List<SpreadsheetRow> referencedRows = new ArrayList<>(Arrays.asList(referencedRow1, referencedRow2));
		referencedRows.add(null); // ignored

		final SpreadsheetPropertySetter setter = new SpreadsheetPropertySetter(model);
		setter.invoke(referencingRow, columnSheet1.getPrefixedIndex(), referencedRows, null);

		assertTrue(referencingRow.getVisibleCellValue(columnSheet1).equals("v1,v2,v3,v4"));
		assertTrue(referencingRow.getAllVisibleCellValuesAsIs(columnSheet1).size() == 4);
		assertTrue(referencingRow.getVisibleCellValue(model.getWorksheetByType("Sheet1").getColumn("c_1")).equals("1"));
	}

	// @AfterClass
	// public static void tearDown()
	// {
	// if (model instanceof GSModel)
	// {
	// ((GSModel) model).deleteWorksheet(model.getWorksheetByType("Sheet3"));
	// }
	// }

}
