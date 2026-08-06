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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModel;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetPropertyGetter;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.test.SharedTestMethods;
import org.eclipse.epsilon.emc.spreadsheets.test.TestModelFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PrefixedVisibleValuePopertyGetterTest {
	private SpreadsheetModel model = null;

	public PrefixedVisibleValuePopertyGetterTest(SpreadsheetModel model) {
		this.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception {
		String pathToFile = "resources/propertygetter/PropertyGetterTest.xlsx";
		String pathToConfig = "resources/propertygetter/PropertyGetterTest.xml";
		return TestModelFactory.getModelsToTest("", pathToFile, pathToConfig, "PropertyGetterTest");
	}

	@Test
	public void testReadVisibleValueUsingPrefixedColumnName() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_2";

		SpreadsheetRow referencedRow = SharedTestMethods.writeRow(model, "Sheet2", "c_1", "3", col, "");
		assertTrue(model.getAllOfType("Sheet2").size() == 1);

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();
		Object getterValue = getter.invoke(referencedRow, "v_id", null);
		assertTrue(getterValue.equals("3"));
	}

	@Test
	public void testReadVisibleValueUsingPrefixedColumnId() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_2";

		SpreadsheetRow referencedRow = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "3", col, "");
		assertTrue(model.getAllOfType("Sheet2").size() == 1);

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();
		Object getterValue = getter.invoke(referencedRow, "v_c_0", null);
		assertTrue(getterValue.equals("3"));
	}

	@Test
	public void testPrefixedColumnIdThatIsUnknown() throws Exception {
		SharedTestMethods.clearWorksheet(model, "Sheet2");

		String col = "c_2";

		SpreadsheetRow referencedRow = SharedTestMethods.writeRow(model, "Sheet2", "c_0", "3", col, "");
		assertTrue(model.getAllOfType("Sheet2").size() == 1);

		SpreadsheetPropertyGetter getter = (SpreadsheetPropertyGetter) model.getPropertyGetter();
		try {
			getter.invoke(referencedRow, "v_unknown", null);
			fail("Should not be able to select value for unknown column");
		}
		catch (IllegalArgumentException e) {
			assertTrue(true);
		}

	}

}
