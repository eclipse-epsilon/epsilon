/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.spreadsheets.propertygetter;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModel;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetPropertyGetter;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.test.SharedTestMethods;
import org.eclipse.epsilon.emc.spreadsheets.test.TestModelFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PropertyGetterReferencingCellNotManyTest {
	private SpreadsheetModel model = null;

	public PropertyGetterReferencingCellNotManyTest(SpreadsheetModel model) {
		this.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception {
		String pathToFile = "resources/propertygetter/PropertyGetterTest.xlsx";
		String pathToConfig = "resources/propertygetter/PropertyGetterTest.xml";
		return TestModelFactory.getModelsToTest("", pathToFile, pathToConfig, "PropertyGetterTest");
	}

	@Test
	public void test_ReferencedCellNotMany_ReferenceOneToOne_UnknownValue() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_2";

		// Write three base referenced rows, use one to write a referencing row, then
		// delete that referenced row
		SharedTestMethods.writeRow(model, "Sheet2", "c_0", "1", col, "v1, v2");
		SharedTestMethods.writeRow(model, "Sheet2", "c_0", "2", col, "v1, v2");
		SpreadsheetRow referencedRow3 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "3", col, "v1");
		assertTrue(model.getAllOfType("Sheet2").size() == 3);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_0", "1", col, referencedRow3);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		model.deleteElement(referencedRow3);
		assertTrue(model.getAllOfType("Sheet2").size() == 2);

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();
		Object getterValue = getter.invoke(referencingRow, col, null);
		assertNull(getterValue);
	}

	@Test
	public void test_ReferencedCellNotMany_ReferenceOneToOne_KnownValue() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_2";

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "1", col, "v1, v2");
		SharedTestMethods.writeRow(model, "Sheet2", "c_0", "2", col, "v1, v2");
		SharedTestMethods.writeRow(model, "Sheet2", "c_0", "3", col, "v1");
		assertTrue(model.getAllOfType("Sheet2").size() == 3);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_0", "1", col, referencedRow1);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();

		Object getterValue = getter.invoke(referencingRow, col, null);
		assertTrue(getterValue instanceof SpreadsheetRow);

		Object referencedRowIdObject = getter.invoke(getterValue, "c_0", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("1"));
		Object referencingValue2 = getter.invoke(getterValue, col, null);
		assertTrue(referencingValue2.equals("v1, v2"));
	}

	@Test
	public void test_ReferencedCellNotMany_ReferenceOneToMany_UnknownValue() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_3";

		// Write three base referenced rows, use one to write a referencing row, then
		// delete that referenced row
		SharedTestMethods.writeRow(model, "Sheet2", "c_0", "1", col, "v1, v2");
		SharedTestMethods.writeRow(model, "Sheet2", "c_0", "2", col, "v1, v2");
		SpreadsheetRow referencedRow3 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "3", col, "v1");
		assertTrue(model.getAllOfType("Sheet2").size() == 3);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_0", "1", col, referencedRow3);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		model.deleteElement(referencedRow3);
		assertTrue(model.getAllOfType("Sheet2").size() == 2);

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();
		Object getterValue = getter.invoke(referencingRow, col, null);
		assertTrue(getterValue instanceof List);
		List<?> referencedRowList = (List<?>) getterValue;
		assertTrue(referencedRowList.size() == 0);
	}

	@Test
	public void test_ReferencedCellNotMany_ReferenceOneToMany_KnownValue() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_3";

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "1", col, "v1, v2");
		SharedTestMethods.writeRow(model, "Sheet2", "c_0", "2", col, "v1, v2");
		SharedTestMethods.writeRow(model, "Sheet2", "c_0", "3", col, "v1");
		assertTrue(model.getAllOfType("Sheet2").size() == 3);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_0", "1", col, referencedRow1);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();

		Object getterValue = getter.invoke(referencingRow, col, null);
		assertTrue(getterValue instanceof List);
		List<?> referencedRowList = (List<?>) getterValue;
		assertTrue(referencedRowList.size() == 2);
		assertTrue(referencedRowList.get(0) instanceof SpreadsheetRow);
		assertTrue(referencedRowList.get(1) instanceof SpreadsheetRow);

		Object referencedRowIdObject = getter.invoke(referencedRowList.get(0), "c_0", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("1"));
		referencedRowIdObject = getter.invoke(referencedRowList.get(1), "c_0", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("2"));

		Object referencingValue2 = getter.invoke(referencedRowList, col, null); // get value from each row
		assertTrue(referencingValue2 instanceof List);
		List<?> referencingValue2list = (List<?>) referencingValue2;
		assertTrue(referencingValue2list.size() == 2);
		assertTrue(referencingValue2list.get(0).equals("v1, v2"));
		assertTrue(referencingValue2list.get(1).equals("v1, v2"));
	}

	@Test
	public void test_ReferencedCellMany_ReferenceOneToOne_UnknownValue() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_4";

		// Write three base referenced rows, use one to write a referencing row, then
		// delete that referenced row
		SharedTestMethods.writeRow(model, "Sheet2", "c_0", "1", col, "v1, v2");
		SharedTestMethods.writeRow(model, "Sheet2", "c_0", "2", col, "v1, v2");
		SpreadsheetRow referencedRow3 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "3", col, "v3");
		assertTrue(model.getAllOfType("Sheet2").size() == 3);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_0", "1", col, referencedRow3);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		assertTrue(
			referencingRow.getAllVisibleCellValuesAsIs(model.getAllOfType("Sheet1").get(0).getColumn(col)).size() == 1);
		model.deleteElement(referencedRow3);
		assertTrue(model.getAllOfType("Sheet2").size() == 2);

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();
		Object getterValue = getter.invoke(referencingRow, col, null);
		assertNull(getterValue);
	}

	@Test
	public void test_ReferencedCellMany_ReferenceOneToOne_KnownValue() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_4";

		// Write three base referenced rows, use one to write a referencing row, then
		// delete that referenced row
		SharedTestMethods.writeRow(model, "Sheet2", "c_0", "1", col, "v1, v2");
		SharedTestMethods.writeRow(model, "Sheet2", "c_0", "2", col, "v1, v2");
		SpreadsheetRow referencedRow3 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "3", col, "v1");
		assertTrue(model.getAllOfType("Sheet2").size() == 3);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_0", "1", col, referencedRow3);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		assertTrue(
			referencingRow.getAllVisibleCellValuesAsIs(model.getAllOfType("Sheet1").get(0).getColumn(col)).size() == 1);
		model.deleteElement(referencedRow3);
		assertTrue(model.getAllOfType("Sheet2").size() == 2);

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();

		Object getterValue = getter.invoke(referencingRow, col, null);
		assertTrue(getterValue instanceof SpreadsheetRow);

		Object referencedRowIdObject = getter.invoke(getterValue, "c_0", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("1"));

		Object referencingValue2 = getter.invoke(getterValue, col, null);
		assertTrue(referencingValue2 instanceof List);
		List<?> referencingValue2list = (List<?>) referencingValue2;
		assertTrue(referencingValue2list.size() == 2);
		assertTrue(referencingValue2list.get(0).equals("v1"));
		assertTrue(referencingValue2list.get(1).equals("v2"));
	}

	@Test
	public void test_ReferencedCellMany_ReferenceOneToMany_UnknownValue() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_5";

		// Write three base referenced rows, use one to write a referencing row, then
		// delete that referenced row
		SharedTestMethods.writeRow(model, "Sheet2", "c_0", "1", col, "v1, v2");
		SharedTestMethods.writeRow(model, "Sheet2", "c_0", "2", col, "v1, v2");
		SpreadsheetRow referencedRow3 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "3", col, "v3");
		assertTrue(model.getAllOfType("Sheet2").size() == 3);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_0", "1", col, referencedRow3);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		assertTrue(
			referencingRow.getAllVisibleCellValuesAsIs(model.getAllOfType("Sheet1").get(0).getColumn(col)).size() == 1);
		model.deleteElement(referencedRow3);
		assertTrue(model.getAllOfType("Sheet2").size() == 2);

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();

		Object getterValue = getter.invoke(referencingRow, col, null);
		assertTrue(getterValue instanceof List);
		List<?> getterValueList = (List<?>) getterValue;
		assertTrue(getterValueList.size() == 0);
	}

	@Test
	public void test_ReferencedCellMany_ReferenceOneToMany_KnownValue() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_5";

		// Write three base referenced rows, use one to write a referencing row, then
		// delete that referenced row
		SharedTestMethods.writeRow(model, "Sheet2", "c_0", "1", col, "v1, v2");
		SharedTestMethods.writeRow(model, "Sheet2", "c_0", "2", col, "v1, v2, v3");
		SpreadsheetRow referencedRow3 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "3", col, "v1");
		assertTrue(model.getAllOfType("Sheet2").size() == 3);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_0", "1", col, referencedRow3);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		assertTrue(
			referencingRow.getAllVisibleCellValuesAsIs(model.getAllOfType("Sheet1").get(0).getColumn(col)).size() == 1);
		model.deleteElement(referencedRow3);
		assertTrue(model.getAllOfType("Sheet2").size() == 2);

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();

		Object getterValue = getter.invoke(referencingRow, col, null);
		assertTrue(getterValue instanceof List);
		List<?> getterValueList = (List<?>) getterValue;
		assertTrue(getterValueList.size() == 2);
		assertTrue(getterValueList.get(0) instanceof SpreadsheetRow);
		assertTrue(getterValueList.get(1) instanceof SpreadsheetRow);

		Object referencedRowIdObject = getter.invoke(getterValueList.get(0), "c_0", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("1"));
		referencedRowIdObject = getter.invoke(getterValueList.get(1), "c_0", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("2"));

		referencedRowIdObject = getter.invoke(getterValueList, col, null);
		assertTrue(referencedRowIdObject instanceof List);
		List<?> valuesFromReferencedRows = (List<?>) referencedRowIdObject;
		assertTrue(valuesFromReferencedRows.size() == 5);
		assertTrue(valuesFromReferencedRows.get(0).equals("v1"));
		assertTrue(valuesFromReferencedRows.get(1).equals("v2"));
		assertTrue(valuesFromReferencedRows.get(2).equals("v1"));
		assertTrue(valuesFromReferencedRows.get(3).equals("v2"));
		assertTrue(valuesFromReferencedRows.get(4).equals("v3"));
	}

}
