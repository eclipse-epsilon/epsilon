/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.spreadsheets.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.epsilon.emc.spreadsheets.ORMConstants;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetConstants;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetModel;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetRow;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetUtils;
import org.eclipse.epsilon.emc.spreadsheets.SpreadsheetWorksheet;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;

public class SharedTestMethods {
	static {
		new SpreadsheetConstants();
		new ORMConstants();
		new SpreadsheetUtils();
	}

	/**
	 * This method should be called to test ENUM classes. This will achieve 100%
	 * coverage on them. Serves no purpose apart from getting the satisfaction of
	 * seeing 100%.
	 * 
	 * http://stackoverflow.com/questions/4512358/emma-coverage-on-enum-types
	 * 
	 * @param enumClass
	 */
	public static void superficialEnumCodeCoverage(Class<? extends Enum<?>> enumClass) {
		try {
			for (Object o : (Object[]) enumClass.getMethod("values").invoke(null)) {
				enumClass.getMethod("valueOf", String.class).invoke(null, o.toString());
			}
		}
		catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static void clearWorksheet(final SpreadsheetModel model, final String worksheetName)
		throws EolRuntimeException {
		SpreadsheetWorksheet worksheet = model.getWorksheetByType(worksheetName);
		for (SpreadsheetRow row : worksheet.getRows()) {
			model.deleteElement(row);
		}
	}

	public static SpreadsheetRow writeRow(SpreadsheetModel model, String worksheetName, String id, String columnName,
		String value) throws EolModelElementTypeNotFoundException {
		return SharedTestMethods.writeRow(model, worksheetName, "c_0", id, columnName, value);
	}

	public static SpreadsheetRow writeRow(SpreadsheetModel model, String worksheetName, String idCol, String id,
		String columnName, String value) throws EolModelElementTypeNotFoundException {
		List<Object> listOfMap = new ArrayList<>();
		Map<String, Object> valueMap = new HashMap<>();
		valueMap.put(idCol, id);
		valueMap.put(columnName, value);
		listOfMap.add(valueMap);
		return (SpreadsheetRow) model.createInstance(worksheetName, listOfMap);
	}

	public static SpreadsheetRow writeRow(SpreadsheetModel model, String worksheetName, String id, String columnName,
		SpreadsheetRow referencedRow) throws EolModelElementTypeNotFoundException {
		return SharedTestMethods.writeRow(model, worksheetName, "c_0", id, columnName, referencedRow);
	}

	public static SpreadsheetRow writeRow(SpreadsheetModel model, String worksheetName, String idCol, String id,
		String columnName, SpreadsheetRow referencedRow) throws EolModelElementTypeNotFoundException {
		List<Object> listOfMap = new ArrayList<>();
		Map<String, Object> mapOfValues = new HashMap<>();
		mapOfValues.put(idCol, id);
		mapOfValues.put(columnName, referencedRow);
		listOfMap.add(mapOfValues);
		return (SpreadsheetRow) model.createInstance(worksheetName, listOfMap);
	}

	public static String getBasePath() {
		return "../org.eclipse.epsilon.emc.spreadsheets.test/";
	}

}
