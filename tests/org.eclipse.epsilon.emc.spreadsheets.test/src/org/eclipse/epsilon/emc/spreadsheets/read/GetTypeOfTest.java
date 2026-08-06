/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.spreadsheets.read;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModel;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetWorksheet;
import org.eclipse.epsilon.emc.spreadsheets.test.SharedTestMethods;
import org.eclipse.epsilon.emc.spreadsheets.test.TestModelFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class GetTypeOfTest {
	private SpreadsheetModel model = null;

	public GetTypeOfTest(SpreadsheetModel model) {
		this.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception {
		String pathToFile = "resources/read/ReadTest.xlsx";
		String pathToConfig = "resources/read/ReadTestConfig.xml";
		return TestModelFactory.getModelsToTest("", pathToFile, pathToConfig, "ReadTest");
	}

	@Test
	public void testGetTypeOf() throws Exception {
		String worksheetName = "Sheet1";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		SpreadsheetRow row1 = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", "c_2", "v1");

		assertTrue(model.getAllOfType(worksheetName).size() == 1);

		SpreadsheetWorksheet worksheet = model.getTypeOf(row1);
		assertTrue(worksheet == row1.getWorksheet());
	}

	@Test
	public void testGetTypeOfString() throws Exception {
		String worksheetName = "Sheet1";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", "c_2", "v1");

		assertTrue(model.getAllOfType(worksheetName).size() == 1);

		SpreadsheetWorksheet worksheet = model.getTypeOf("STRING");
		assertTrue(worksheet == null);
	}

	@Test
	public void testGetTypeNameOf() throws Exception {
		String worksheetName = "Sheet1";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		SpreadsheetRow row1 = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", "c_2", "v1");

		assertTrue(model.getAllOfType(worksheetName).size() == 1);

		String worksheet = model.getTypeNameOf(row1);
		assertTrue(worksheet.equals("Sheet1"));
	}

	@Test
	public void testGetTypeNameOfString() throws Exception {
		String worksheetName = "Sheet1";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", "c_2", "v1");

		assertTrue(model.getAllOfType(worksheetName).size() == 1);

		String worksheet = model.getTypeNameOf("STRING");
		assertTrue(worksheet == null);
	}

}
