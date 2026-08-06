/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.dt.preferences;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.epsilon.common.dt.editor.highlighting.AbstractHighlightingManager;
import org.eclipse.epsilon.common.dt.editor.highlighting.EpsilonHighlightingManager;
import org.eclipse.epsilon.common.dt.util.ThemeChangeListener;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class EpsilonSyntaxColoringPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	protected AbstractHighlightingManager highlightingManager;
	protected IPreferenceStore preferenceStore;
	protected List<String> colorPreferences;
	protected Map<String, String> preferenceLabels;
	protected List<EpsilonColorFieldEditor> colorFieldEditors;

	protected ThemeChangeListener themeChangeListener = new ThemeChangeListener() {
		@Override
		public void themeChange() {
			highlightingManager.initialiseDefaultColors();
			for (EpsilonColorFieldEditor fieldEditor : colorFieldEditors) {
				if (preferenceStore.contains(fieldEditor.getPreferenceName())) {
					fieldEditor.load();
				} else {
					fieldEditor.loadDefault();
				}
			}
		}
	};
	
	@Override
	public void init(IWorkbench workbench) {
		highlightingManager = new EpsilonHighlightingManager();
		highlightingManager.initialiseDefaultColors();
		colorPreferences = EpsilonHighlightingManager.COLOR_PREFERENCES;
		preferenceStore = highlightingManager.getPreferenceStore();

		preferenceLabels = new HashMap<>();
		preferenceLabels.put(EpsilonHighlightingManager.COMMENT_COLOR_PREF, "Comments: ");
		preferenceLabels.put(EpsilonHighlightingManager.ANNOTATION_COLOR_PREF, "Annotations: ");
		preferenceLabels.put(EpsilonHighlightingManager.STRING_COLOR_PREF, "Strings: ");
		preferenceLabels.put(EpsilonHighlightingManager.DEFAULT_COLOR_PREF, "Default: ");
		preferenceLabels.put(EpsilonHighlightingManager.KEYWORD_COLOR_PREF, "Keywords: ");
		preferenceLabels.put(EpsilonHighlightingManager.BUILTIN_COLOR_PREF, "Builtins: ");
		preferenceLabels.put(EpsilonHighlightingManager.ASSERTION_COLOR_PREF, "Assertions: ");
		preferenceLabels.put(EpsilonHighlightingManager.TYPE_COLOR_PREF, "Types: ");
		preferenceLabels.put(EpsilonHighlightingManager.MARKER_COLOR_PREF, "Markers: ");
		preferenceLabels.put(EpsilonHighlightingManager.EGL_STATIC_TEXT_COLOR_PREF, "Static text (EGL): ");

		// This listener is necessary because, when themes change, for those color
		//   selectors that stored the default value of the old theme, this default
		//   value is treated as a custom one if the user clicks "Apply and Close".
		//   So, when themes change the value of the color selector is overriden,
		//     either with the custom value of the preferences page, or with the
		//     default value of the new theme
		workbench.getThemeManager().addPropertyChangeListener(themeChangeListener);
	}

	@Override
	protected void createFieldEditors() {
		// generate field editors sorted by label
		List<String> sortedPreferences = new ArrayList<>(colorPreferences);
		sortedPreferences.sort(new Comparator<String>() {
			@Override
			public int compare(String pref1, String pref2) {
				return preferenceLabels.get(pref1).compareTo(preferenceLabels.get(pref2));
			}
		});
		
		colorFieldEditors = new ArrayList<>();
		for (String preference : sortedPreferences) {
			EpsilonColorFieldEditor fieldEditor = new EpsilonColorFieldEditor(preference,
					preferenceLabels.get(preference), getFieldEditorParent());
			colorFieldEditors.add(fieldEditor);
			addField(fieldEditor);
		}
	}

	@Override
	public IPreferenceStore getPreferenceStore() {
		return preferenceStore;
	}
}
