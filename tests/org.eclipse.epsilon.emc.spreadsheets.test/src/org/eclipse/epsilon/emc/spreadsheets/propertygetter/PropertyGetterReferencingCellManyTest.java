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

import static org.junit.Assert.assertEquals;
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
public class PropertyGetterReferencingCellManyTest {
	private SpreadsheetModel model = null;

	public PropertyGetterReferencingCellManyTest(SpreadsheetModel model) {
		this.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception {
		String pathToFile = "resources/propertygetter/PropertyGetterTest.xlsx";
		String pathToConfig = "resources/propertygetter/PropertyGetterTest.xml";
		return TestModelFactory.getModelsToTest("", pathToFile, pathToConfig, "PropertyGetterTest");
	}

	@SuppressWarnings("unused")
	@Test
	public void test_CellsManyToNotMany_ReferenceOneToOne_OneValueInReferencingCell() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_6";

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "1", col, "v1, v2");
		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "2", col, "v1, v2");
		SpreadsheetRow referencedRow3 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "3", col, "v1");
		SpreadsheetRow referencedRow4 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "4", col, "v1");
		assertTrue(model.getAllOfType("Sheet2").size() == 4);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_0", "1", col, referencedRow3);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		assertTrue(referencingRow.getVisibleCellValue(referencingRow.getColumn(col)).equals("v1"));

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();

		Object getterValue = getter.invoke(referencingRow, col, null);
		assertTrue(getterValue instanceof List);
		List<?> referencedObjectList = (List<?>) getterValue;
		assertTrue(referencedObjectList.size() == 1);
		assertTrue(referencedObjectList.get(0) instanceof SpreadsheetRow);

		Object referencedRowIdObject = getter.invoke(referencedObjectList.get(0), "c_0", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("3"));
		Object referencedRowValueObject = getter.invoke(referencedObjectList.get(0), col, null);
		assertTrue(referencedRowValueObject instanceof String);
		assertTrue(referencedRowValueObject.equals("v1"));
	}

	@SuppressWarnings("unused")
	@Test
	public void test_CellsManyToNotMany_ReferenceOneToOne_TwoValuesInReferencingCell() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		assertTrue(model.getAllOfType("Sheet1").size() == 0);
		assertTrue(model.getAllOfType("Sheet2").size() == 0);

		String col = "c_6";

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, "v1, v2");
		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "2", col, "v1, v2");
		SpreadsheetRow referencedRow3 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "3", col, "v2");
		SpreadsheetRow referencedRow4 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "4", col, "v2");
		assertTrue(model.getAllOfType("Sheet2").size() == 4);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", col, referencedRow1);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		assertTrue(referencingRow.getVisibleCellValue(referencingRow.getColumn(col)).equals("v1, v2"));

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();

		Object getterValue = getter.invoke(referencingRow, col, null);
		assertTrue(getterValue instanceof List);
		List<?> referencedObjectList = (List<?>) getterValue;
		assertTrue(referencedObjectList.size() == 1);
		assertTrue(referencedObjectList.get(0) instanceof SpreadsheetRow);

		Object referencedRowIdObject = getter.invoke(referencedObjectList.get(0), "c_1", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("3"));
		Object referencedRowValueObject = getter.invoke(referencedObjectList.get(0), col, null);
		assertTrue(referencedRowValueObject instanceof String);
		assertTrue(referencedRowValueObject.equals("v2"));
	}

	@SuppressWarnings("unused")
	@Test
	public void testCellsManyToNotManyReferenceOneToOne_ThreeValuesInReferencingCell() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_6";

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "1", col, "v1, v2");
		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "2", col, "v1, v2");
		SpreadsheetRow referencedRow3 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "3", col, "v1, v2, v3");
		assertTrue(model.getAllOfType("Sheet2").size() == 3);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_0", "1", col, referencedRow3);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		assertTrue(referencingRow.getVisibleCellValue(referencingRow.getColumn(col)).equals("v1, v2, v3"));
		model.deleteElement(referencedRow3);
		assertTrue(model.getAllOfType("Sheet2").size() == 2);

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();

		Object referencedObjects = getter.invoke(referencingRow, col, null);
		assertTrue(referencedObjects instanceof List);
		List<?> referencedObjectList = (List<?>) referencedObjects;
		assertEquals(0, referencedObjectList.size());
	}

	@SuppressWarnings("unused")
	@Test
	public void testCellsManyToNotMany_ReferenceOneToMany_OneValueInReferencingCell() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_7";

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, "v1, v2");
		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "2", col, "v1, v2");
		SpreadsheetRow referencedRow3 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "3", col, "v1");
		SpreadsheetRow referencedRow4 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "4", col, "v1");
		assertTrue(model.getAllOfType("Sheet2").size() == 4);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", col, referencedRow3);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		assertTrue(referencingRow.getVisibleCellValue(referencingRow.getColumn(col)).equals("v1"));

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();

		Object referencedObjects = getter.invoke(referencingRow, col, null);
		assertTrue(referencedObjects instanceof List);
		List<?> referencedObjectList = (List<?>) referencedObjects;
		assertTrue(referencedObjectList.size() == 2);

		Object referencedRowIdObject = getter.invoke(referencedObjectList.get(0), "c_1", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("3"));
		referencedRowIdObject = getter.invoke(referencedObjectList.get(1), "c_1", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("4"));

		Object referencedRowValueObject = getter.invoke(referencedObjectList.get(0), col, null);
		assertTrue(referencedRowValueObject instanceof String);
		assertTrue(referencedRowValueObject.equals("v1"));
		referencedRowValueObject = getter.invoke(referencedObjectList.get(1), col, null);
		assertTrue(referencedRowValueObject instanceof String);
		assertTrue(referencedRowValueObject.equals("v1"));
	}

	@SuppressWarnings("unused")
	@Test
	public void testCellsManyToNotMany_ReferenceOneToMany_TwoValuesInReferencingCell() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_7";

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, "v1, v2");
		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "2", col, "v1, v2");
		SpreadsheetRow referencedRow3 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "3", col, "v2");
		SpreadsheetRow referencedRow4 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "4", col, "v2");
		assertTrue(model.getAllOfType("Sheet2").size() == 4);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", col, referencedRow1);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		assertTrue(referencingRow.getVisibleCellValue(referencingRow.getColumn(col)).equals("v1, v2"));

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();

		Object referencedObjects = getter.invoke(referencingRow, col, null);
		assertTrue(referencedObjects instanceof List);
		List<?> referencedObjectList = (List<?>) referencedObjects;
		assertTrue(referencedObjectList.size() == 2);

		Object referencedRowIdObject = getter.invoke(referencedObjectList.get(0), "c_1", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("3"));
		referencedRowIdObject = getter.invoke(referencedObjectList.get(1), "c_1", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("4"));

		Object referencedRowValueObject = getter.invoke(referencedObjectList.get(0), col, null);
		assertTrue(referencedRowValueObject instanceof String);
		assertTrue(referencedRowValueObject.equals("v2"));
		referencedRowValueObject = getter.invoke(referencedObjectList.get(1), col, null);
		assertTrue(referencedRowValueObject instanceof String);
		assertTrue(referencedRowValueObject.equals("v2"));
	}

	@SuppressWarnings("unused")
	@Test
	public void testCellsManyToNotMany_ReferenceOneToMany_ThreeValuesInReferencingCell() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_7";

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "1", col, "v1, v2");
		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "2", col, "v1, v2");
		SpreadsheetRow referencedRow3 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "3", col, "v1, v2, v3");
		assertTrue(model.getAllOfType("Sheet2").size() == 3);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_0", "1", col, referencedRow3);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		assertTrue(referencingRow.getVisibleCellValue(referencingRow.getColumn(col)).equals("v1, v2, v3"));
		model.deleteElement(referencedRow3);
		assertTrue(model.getAllOfType("Sheet2").size() == 2);

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();

		Object referencedObjects = getter.invoke(referencingRow, col, null);
		assertTrue(referencedObjects instanceof List);
		List<?> referencedObjectList = (List<?>) referencedObjects;
		assertTrue(referencedObjectList.size() == 0);
	}

	@SuppressWarnings("unused")
	@Test
	public void testCellsManyToMany_ReferenceOneToOne_OneValueInReferencingCell() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_8";

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, "v1, v2");
		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "2", col, "v1, v2");
		SpreadsheetRow referencedRow = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "3", col, "v1");
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", col, referencedRow);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		assertTrue(referencingRow.getVisibleCellValue(referencingRow.getColumn(col)).equals("v1"));

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();

		Object referencedObjects = getter.invoke(referencingRow, col, null);
		assertTrue(referencedObjects instanceof List);
		List<?> referencedObjectList = (List<?>) referencedObjects;
		assertTrue(referencedObjectList.size() == 1);

		Object referencedRowIdObject = getter.invoke(referencedObjectList.get(0), "c_1", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("1"));

		Object referencedRowValueObject = getter.invoke(referencedObjectList.get(0), col, null);
		assertTrue(referencedRowValueObject instanceof List);
		List<?> referencedRowValueList = (List<?>) referencedRowValueObject;
		assertTrue(referencedRowValueList.size() == 2);
		assertTrue(referencedRowValueList.get(0).equals("v1"));
		assertTrue(referencedRowValueList.get(1).equals("v2"));
	}

	@SuppressWarnings("unused")
	@Test
	public void testCellsManyToMany_ReferenceOneToOne_TwoValuesInReferencingCell() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_8";

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "1", col, "v1, v2");
		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "2", col, "v1, v2");
		SpreadsheetRow referencedRow3 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "3", col, "v1");
		assertTrue(model.getAllOfType("Sheet2").size() == 3);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_0", "S1_1", col, referencedRow1);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		assertTrue(referencingRow.getVisibleCellValue(referencingRow.getColumn(col)).equals("v1, v2"));

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();

		Object referencedObjects = getter.invoke(referencingRow, col, null);
		assertTrue(referencedObjects instanceof List);
		List<?> referencedObjectList = (List<?>) referencedObjects;
		assertTrue(referencedObjectList.size() == 1);

		Object referencedRowIdObject = getter.invoke(referencedObjectList.get(0), "c_0", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("1"));

		Object referencedRowValueObject = getter.invoke(referencedObjectList.get(0), col, null);
		assertTrue(referencedRowValueObject instanceof List);
		List<?> referencedRowValueList = (List<?>) referencedRowValueObject;
		assertTrue(referencedRowValueList.size() == 2);
		assertTrue(referencedRowValueList.get(0).equals("v1"));
		assertTrue(referencedRowValueList.get(1).equals("v2"));
	}

	@SuppressWarnings("unused")
	@Test
	public void testCellsManyToMany_ReferenceOneToOne_ThreeValuesInReferencingCell() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_8";

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, "v1, v2");
		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "2", col, "v1, v2");
		SpreadsheetRow referencedRow3 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "3", col, "v1, v2, v3");
		SpreadsheetRow referencedRow4 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "4", col, "v3");
		assertTrue(model.getAllOfType("Sheet2").size() == 4);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "S1_1", col, referencedRow3);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		assertTrue(referencingRow.getVisibleCellValue(referencingRow.getColumn(col)).equals("v1, v2, v3"));
		model.deleteElement(referencedRow3);
		assertTrue(model.getAllOfType("Sheet2").size() == 3);

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();

		Object referencedObjects = getter.invoke(referencingRow, col, null);
		assertTrue(referencedObjects instanceof List);
		List<?> referencedObjectList = (List<?>) referencedObjects;
		assertTrue(referencedObjectList.size() == 2);

		Object referencedRowIdObject = getter.invoke(referencedObjectList.get(0), "c_1", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("1"));
		referencedRowIdObject = getter.invoke(referencedObjectList.get(1), "c_1", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("4"));

		Object referencedRowValueObject = getter.invoke(referencedObjectList.get(0), col, null);
		assertTrue(referencedRowValueObject instanceof List);
		List<?> referencedRowValueList = (List<?>) referencedRowValueObject;
		assertTrue(referencedRowValueList.size() == 2);
		assertTrue(referencedRowValueList.get(0).equals("v1"));
		assertTrue(referencedRowValueList.get(1).equals("v2"));

		referencedRowValueObject = getter.invoke(referencedObjectList.get(1), col, null);
		assertTrue(referencedRowValueObject instanceof List);
		referencedRowValueList = (List<?>) referencedRowValueObject;
		assertTrue(referencedRowValueList.size() == 1);
		assertTrue(referencedRowValueList.get(0).equals("v3"));
	}

	@SuppressWarnings("unused")
	@Test
	public void testCellsManyToMany_ReferenceOneToMany_OneValueInReferencingCell() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_9";

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "1", col, "v1, v2");
		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "2", col, "v1, v2");
		SpreadsheetRow referencedRow = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "3", col, "v1");
		assertTrue(model.getAllOfType("Sheet2").size() == 3);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_0", "1", col, referencedRow);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		assertTrue(referencingRow.getVisibleCellValue(referencingRow.getColumn(col)).equals("v1"));

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();

		Object referencedObjects = getter.invoke(referencingRow, col, null);
		assertTrue(referencedObjects instanceof List);
		List<?> referencedObjectList = (List<?>) referencedObjects;
		assertTrue(referencedObjectList.size() == 3);

		Object referencedRowIdObject = getter.invoke(referencedObjectList.get(0), "c_0", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("1"));
		referencedRowIdObject = getter.invoke(referencedObjectList.get(1), "c_0", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("2"));
		referencedRowIdObject = getter.invoke(referencedObjectList.get(2), "c_0", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("3"));

		Object referencedRowValueObject = getter.invoke(referencedObjectList.get(0), col, null);
		assertTrue(referencedRowValueObject instanceof List);
		List<?> referencedRowValueList = (List<?>) referencedRowValueObject;
		assertTrue(referencedRowValueList.size() == 2);
		assertTrue(referencedRowValueList.get(0).equals("v1"));
		assertTrue(referencedRowValueList.get(1).equals("v2"));

		referencedRowValueObject = getter.invoke(referencedObjectList.get(1), col, null);
		assertTrue(referencedRowValueObject instanceof List);
		referencedRowValueList = (List<?>) referencedRowValueObject;
		assertTrue(referencedRowValueList.size() == 2);
		assertTrue(referencedRowValueList.get(0).equals("v1"));
		assertTrue(referencedRowValueList.get(1).equals("v2"));

		referencedRowValueObject = getter.invoke(referencedObjectList.get(2), col, null);
		assertTrue(referencedRowValueObject instanceof List);
		referencedRowValueList = (List<?>) referencedRowValueObject;
		assertTrue(referencedRowValueList.size() == 1);
		assertTrue(referencedRowValueList.get(0).equals("v1"));
	}

	@SuppressWarnings("unused")
	@Test
	public void testCellsManyToMany_ReferenceOneToMany_TwoValuesInReferencingCell() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_9";

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "1", col, "v1, v2");
		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "2", col, "v1, v2");
		SpreadsheetRow referencedRow3 = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "3", col, "v3");
		assertTrue(model.getAllOfType("Sheet2").size() == 3);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_0", "S1_1", col, referencedRow1);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		assertTrue(referencingRow.getVisibleCellValue(referencingRow.getColumn(col)).equals("v1, v2"));

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();

		Object referencedObjects = getter.invoke(referencingRow, col, null);
		assertTrue(referencedObjects instanceof List);
		List<?> referencedObjectList = (List<?>) referencedObjects;
		assertTrue(referencedObjectList.size() == 2);

		Object referencedRowIdObject = getter.invoke(referencedObjectList.get(0), "c_0", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("1"));
		referencedRowIdObject = getter.invoke(referencedObjectList.get(1), "c_0", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("2"));

		Object referencedRowValueObject = getter.invoke(referencedObjectList.get(0), col, null);
		assertTrue(referencedRowValueObject instanceof List);
		List<?> referencedRowValueList = (List<?>) referencedRowValueObject;
		assertTrue(referencedRowValueList.size() == 2);
		assertTrue(referencedRowValueList.get(0).equals("v1"));
		assertTrue(referencedRowValueList.get(1).equals("v2"));

		referencedRowValueObject = getter.invoke(referencedObjectList.get(1), col, null);
		assertTrue(referencedRowValueObject instanceof List);
		referencedRowValueList = (List<?>) referencedRowValueObject;
		assertTrue(referencedRowValueList.size() == 2);
		assertTrue(referencedRowValueList.get(0).equals("v1"));
		assertTrue(referencedRowValueList.get(1).equals("v2"));
	}

	@SuppressWarnings("unused")
	@Test
	public void testCellsManyToManyReferenceOneToMany_ThreeValuesInReferencingCell() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_9";

		SpreadsheetRow referencedRow1 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", col, "v1, v2");
		SpreadsheetRow referencedRow2 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "2", col, "v1, v2");
		SpreadsheetRow referencedRow3 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "3", col, "v1, v2, v3");
		SpreadsheetRow referencedRow4 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "4", col, "v3");
		assertTrue(model.getAllOfType("Sheet2").size() == 4);
		SpreadsheetRow referencingRow = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "S1_1", col, referencedRow3);
		assertTrue(model.getAllOfType("Sheet1").size() == 1);
		assertTrue(referencingRow.getVisibleCellValue(referencingRow.getColumn(col)).equals("v1, v2, v3"));
		model.deleteElement(referencedRow3);
		assertTrue(model.getAllOfType("Sheet2").size() == 3);

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();

		Object referencedObjects = getter.invoke(referencingRow, col, null);
		assertTrue(referencedObjects instanceof List);
		List<?> referencedObjectList = (List<?>) referencedObjects;
		assertTrue(referencedObjectList.size() == 3);

		Object referencedRowIdObject = getter.invoke(referencedObjectList.get(0), "c_1", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("1"));
		referencedRowIdObject = getter.invoke(referencedObjectList.get(1), "c_1", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("2"));
		referencedRowIdObject = getter.invoke(referencedObjectList.get(2), "c_1", null);
		assertTrue(referencedRowIdObject instanceof String);
		assertTrue(referencedRowIdObject.equals("4"));

		Object referencedRowValueObject = getter.invoke(referencedObjectList.get(0), col, null);
		assertTrue(referencedRowValueObject instanceof List);
		List<?> referencedRowValueList = (List<?>) referencedRowValueObject;
		assertTrue(referencedRowValueList.size() == 2);
		assertTrue(referencedRowValueList.get(0).equals("v1"));
		assertTrue(referencedRowValueList.get(1).equals("v2"));

		referencedRowValueObject = getter.invoke(referencedObjectList.get(1), col, null);
		assertTrue(referencedRowValueObject instanceof List);
		referencedRowValueList = (List<?>) referencedRowValueObject;
		assertTrue(referencedRowValueList.size() == 2);
		assertTrue(referencedRowValueList.get(0).equals("v1"));
		assertTrue(referencedRowValueList.get(1).equals("v2"));

		referencedRowValueObject = getter.invoke(referencedObjectList.get(2), col, null);
		assertTrue(referencedRowValueObject instanceof List);
		referencedRowValueList = (List<?>) referencedRowValueObject;
		assertTrue(referencedRowValueList.size() == 1);
		assertTrue(referencedRowValueList.get(0).equals("v3"));
	}

}
