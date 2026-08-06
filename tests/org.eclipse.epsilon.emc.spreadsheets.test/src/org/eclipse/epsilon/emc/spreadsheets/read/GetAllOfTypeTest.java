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
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.List;

import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModel;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.test.SharedTestMethods;
import org.eclipse.epsilon.emc.spreadsheets.test.TestModelFactory;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class GetAllOfTypeTest {
	private SpreadsheetModel model = null;

	public GetAllOfTypeTest(SpreadsheetModel model) {
		this.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception {
		String pathToFile = "resources/read/ReadTest.xlsx";
		String pathToConfig = "resources/read/ReadTestConfig.xml";
		return TestModelFactory.getModelsToTest("", pathToFile, pathToConfig, "ReadTest");
	}

	@Test
	public void testGetAllOfType() throws Exception {
		String worksheetName = "Sheet1";

		SharedTestMethods.clearWorksheet(model, worksheetName);
		assertTrue(model.getAllOfType(worksheetName).size() == 0);

		SpreadsheetRow row1 = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "1", "c_2", "v1");
		SpreadsheetRow row2 = SharedTestMethods.writeRow(model, "Sheet1", "c_1", "2", "c_2", "v2");

		assertTrue(model.getAllOfType(worksheetName).size() == 2);

		List<SpreadsheetRow> allRows = model.getAllOfType("Sheet1");
		assertTrue(allRows.contains(row1));
		assertTrue(allRows.contains(row2));
	}

	@Test
	public void testUnknownWorksheet() throws Exception {
		try {
			model.getAllOfType("UNKNOWN");
			fail("Should fail on unknown worksheet");
		}
		catch (EolModelElementTypeNotFoundException e) {
			assertTrue(true);
		}
	}

}
