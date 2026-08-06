/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.dt.preferences;

import java.util.HashMap;
import org.eclipse.epsilon.common.dt.preferences.EpsilonSyntaxColoringPreferencePage;
import org.eclipse.epsilon.flexmi.dt.FlexmiHighlightingManager;
import org.eclipse.ui.IWorkbench;

public class FlexmiSyntaxColoringPreferencePage extends EpsilonSyntaxColoringPreferencePage {

	@Override
	public void init(IWorkbench workbench) {
		// set the default colors in the preferences store (not saved in workspace)
		highlightingManager = new FlexmiHighlightingManager();
		highlightingManager.initialiseDefaultColors();
		preferenceStore = highlightingManager.getPreferenceStore();
		colorPreferences = FlexmiHighlightingManager.COLOR_PREFERENCES;

		preferenceLabels = new HashMap<>();
		preferenceLabels.put(FlexmiHighlightingManager.XML_COMMENT_COLOR_PREF, "Comments: ");
		preferenceLabels.put(FlexmiHighlightingManager.PROC_INSTR_COLOR_PREF, "Processing Instructions: ");
		preferenceLabels.put(FlexmiHighlightingManager.STRING_COLOR_PREF, "Strings: ");
		preferenceLabels.put(FlexmiHighlightingManager.DEFAULT_COLOR_COLOR_PREF, "Default (parsing errors): ");
		preferenceLabels.put(FlexmiHighlightingManager.TAG_COLOR_PREF, "Tags: ");

		// This listener is necessary because, when themes change, for those color
		//   selectors that stored the default value of the old theme, this default
		//   value is treated as a custom one if the user clicks "Apply and Close".
		//   So, when themes change the value of the color selector is overriden,
		//     either with the custom value of the preferences page, or with the
		//     default value of the new theme
		workbench.getThemeManager().addPropertyChangeListener(themeChangeListener);
	}

}
