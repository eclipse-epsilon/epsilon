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
import java.util.List;

import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModel;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.test.SharedTestMethods;
import org.eclipse.epsilon.emc.spreadsheets.test.TestModelFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class GetAllContentsTest {
	private SpreadsheetModel model = null;

	public GetAllContentsTest(SpreadsheetModel model) {
		this.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception {
		String pathToFile = "resources/read/ReadTest.xlsx";
		String pathToConfig = "resources/read/ReadTestConfig.xml";
		return TestModelFactory.getModelsToTest("", pathToFile, pathToConfig, "ReadTest");
	}

	@Test
	public void testAllContents() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet1");
		SharedTestMethods.clearWorksheet(model, "Sheet2");
		SharedTestMethods.clearWorksheet(model, "Sheet3");

		assertTrue(model.getAllOfType("Sheet1").size() == 0);
		assertTrue(model.getAllOfType("Sheet2").size() == 0);
		assertTrue(model.getAllOfType("Sheet3").size() == 0);

		SpreadsheetRow row1 = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", "c_2", "v1");
		SpreadsheetRow row2 = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "2", "c_2", "v2");
		SpreadsheetRow row3 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "1", "c_2", "v1");
		SpreadsheetRow row4 = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "2", "c_2", "v2");
		SpreadsheetRow row5 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "1", "c_2", "v1");
		SpreadsheetRow row6 = SharedTestMethods.writeRow(model, "Sheet3", "c_1", "2", "c_2", "v2");

		assertTrue(model.getAllOfType("Sheet1").size() == 2);
		assertTrue(model.getAllOfType("Sheet2").size() == 2);
		assertTrue(model.getAllOfType("Sheet3").size() == 2);

		List<SpreadsheetRow> allRows = model.allContents();
		assertTrue(allRows.contains(row1));
		assertTrue(allRows.contains(row2));
		assertTrue(allRows.contains(row3));
		assertTrue(allRows.contains(row4));
		assertTrue(allRows.contains(row5));
		assertTrue(allRows.contains(row6));
	}

}
