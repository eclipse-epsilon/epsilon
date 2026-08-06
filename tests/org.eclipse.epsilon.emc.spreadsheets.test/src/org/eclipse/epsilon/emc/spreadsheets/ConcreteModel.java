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

import java.util.Collection;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.emc.spreadsheets.ISpreadsheetMetadata.SpreadsheetWorksheetMetadata;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;

public class ConcreteModel extends SpreadsheetModel {

	@Override
	protected void loadSpreadsheet() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected ISpreadsheetMetadata getSpreadsheetMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isMetadataConfigurationDefined() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected SpreadsheetWorksheet createWorksheet(SpreadsheetWorksheetMetadata worksheetMetadata) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SpreadsheetRow> find(Variable iterator, ModuleElement ast, IEolContext context)
		throws EolRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void deleteWorksheet(SpreadsheetWorksheet worksheet) {
		// TODO Auto-generated method stub

	}

}
