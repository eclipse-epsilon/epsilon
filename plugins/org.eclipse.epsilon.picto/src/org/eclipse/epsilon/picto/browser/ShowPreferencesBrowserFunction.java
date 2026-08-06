/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.browser;

import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.epsilon.picto.preferences.PictoPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class ShowPreferencesBrowserFunction implements PictoBrowserFunction {

	@Override
	public void accept(PictoView view, Object[] args) {
		PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(
			view.getSite().getShell(), PictoPreferencePage.ID,  
			new String[] {PictoPreferencePage.ID}, null);
		dialog.open();
	}

	@Override
	public String getName() {
		return "showPreferences";
	}
}
