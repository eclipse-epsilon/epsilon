/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.spreadsheets;

import java.util.List;
import java.util.Map;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;

public class ConcreteWorksheet extends SpreadsheetWorksheet {

	public ConcreteWorksheet(SpreadsheetModel model, String name, boolean existsInSpreadsheet) {
		super(model, name, existsInSpreadsheet);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void createInSpreadsheet() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void loadHeader() throws EolModelLoadingException {
		// TODO Auto-generated method stub

	}

	@Override
	protected SpreadsheetColumn createColumn(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDefaultEmptyCellValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected SpreadsheetRow insertRow(Map<SpreadsheetColumn, String> values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeRow(SpreadsheetRow row) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<SpreadsheetRow> getRows() {
		// TODO Auto-generated method stub
		return null;
	}

}
