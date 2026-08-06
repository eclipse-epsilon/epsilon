/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.spreadsheets.common;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModel;
import org.eclipse.epsilon.emc.spreadsheets.test.TestModelFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class IgnoreWorksheetTest {
	private SpreadsheetModel model = null;

	public IgnoreWorksheetTest(SpreadsheetModel model) {
		this.model = model;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> models() throws Exception {
		String PATH_TO_FILE = "resources/common/IgnoreWorksheetTest.xlsx";
		String CONFIG = "resources/common/IgnoreWorksheetTestConfig.xml";
		return TestModelFactory.getModelsToTest("", PATH_TO_FILE, CONFIG, "IgnoreWorksheetTest");
	}

	@Test
	public void testIgnoreWorksheet() throws Exception {
		assertTrue(model.getWorksheets().size() == 1);
		assertTrue(model.getWorksheetByType("Sheet1") != null);
		assertTrue(model.getWorksheetByType("Sheet2") == null);
	}

}
