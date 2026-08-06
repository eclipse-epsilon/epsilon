/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Martins Francis - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.spreadsheets.excel.dt;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.spreadsheets.excel.ExcelModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.SecurePreferencesFactory;
import org.eclipse.equinox.security.storage.StorageException;

public class SecureExcelModel extends ExcelModel {
	
	@Override
	public void load(final StringProperties properties, final IRelativePathResolver resolver)
		throws EolModelLoadingException {
		try {
			final String password = this.loadPassword(properties);
			properties.put(ExcelModel.SPREADSHEET_PASSWORD, password);

			super.load(properties, resolver);
		}
		catch (Exception e) {
			throw new EolModelLoadingException(e, this);
		}
	}

	public String loadPassword(final StringProperties properties) throws StorageException {
		final ISecurePreferences preferences = SecurePreferencesFactory.getDefault();
		if (preferences != null) {
			// Password is stored in the vault associated with the file name
			final String fileName = properties.getProperty(ExcelModel.SPREADSHEET_FILE);
			if (preferences.nodeExists(fileName)) {
				final ISecurePreferences node = preferences.node(fileName);
				return node.get(ExcelModel.SPREADSHEET_PASSWORD, null);
			}
			else {
				final String message = "Equinox Security could not find password for '" + fileName + "'";
				throw new RuntimeException(message);
			}
		}
		else {
			final String message = "Equinox Security was unable to create secure preferences using default location";
			throw new RuntimeException(message);
		}
	}

}
