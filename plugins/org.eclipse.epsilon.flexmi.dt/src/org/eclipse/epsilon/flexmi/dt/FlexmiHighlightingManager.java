/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.dt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.epsilon.common.dt.editor.highlighting.AbstractHighlightingManager;
import org.eclipse.epsilon.common.dt.util.EclipseUtil;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

public class FlexmiHighlightingManager extends AbstractHighlightingManager {

	static final RGB XML_COMMENT_COLOR = new RGB(128, 0, 0);
	static final RGB PROC_INSTR_COLOR = new RGB(128, 128, 128);
	static final RGB STRING_COLOR = new RGB(0, 128, 0);
	static final RGB DEFAULT_COLOR = new RGB(0, 0, 0);
	static final RGB TAG_COLOR = new RGB(0, 0, 128);

	static final RGB XML_COMMENT_COLOR_DARK = new RGB(118, 167, 37);
	static final RGB PROC_INSTR_COLOR_DARK = new RGB(255, 255, 255);
	static final RGB STRING_COLOR_DARK = new RGB(243, 191, 0);
	static final RGB DEFAULT_COLOR_DARK = new RGB(255, 255, 255);
	static final RGB TAG_COLOR_DARK = new RGB(115, 148, 255);

	private static final String PREFERENCE_PREFIX = FlexmiPlugin.PLUGIN_ID;

	private static String prefixPreference(String preference) {
		return String.format("%s.%s", PREFERENCE_PREFIX, preference);
	}

	public static final String XML_COMMENT_COLOR_PREF = prefixPreference("commentColor");
	public static final String PROC_INSTR_COLOR_PREF = prefixPreference("procInstrColor");
	public static final String STRING_COLOR_PREF = prefixPreference("stringColor");
	public static final String DEFAULT_COLOR_COLOR_PREF = prefixPreference("defaultColor");
	public static final String TAG_COLOR_PREF = prefixPreference("tagColor");

	public FlexmiHighlightingManager() {
		preferenceStore = FlexmiPlugin.getDefault().getPreferenceStore();
	}

	public static final List<String> COLOR_PREFERENCES = 
			new ArrayList<> (Arrays.asList(
					DEFAULT_COLOR_COLOR_PREF,
					PROC_INSTR_COLOR_PREF,
					XML_COMMENT_COLOR_PREF,
					STRING_COLOR_PREF,
					TAG_COLOR_PREF));

	@Override
	protected boolean areDefaultColorsCorrect() {
		return 
			(EclipseUtil.isDarkThemeEnabled() &&
				PreferenceConverter.getDefaultColor(preferenceStore, TAG_COLOR_PREF).equals(TAG_COLOR_DARK))
			||
			(!EclipseUtil.isDarkThemeEnabled() &&
				 PreferenceConverter.getDefaultColor(preferenceStore, TAG_COLOR_PREF).equals(TAG_COLOR));
	}
	
	@Override
	protected void setDefaults() {
		if (EclipseUtil.isDarkThemeEnabled()) {
			PreferenceConverter.setDefault(preferenceStore, XML_COMMENT_COLOR_PREF, XML_COMMENT_COLOR_DARK);
			PreferenceConverter.setDefault(preferenceStore, PROC_INSTR_COLOR_PREF, PROC_INSTR_COLOR_DARK);
			PreferenceConverter.setDefault(preferenceStore, STRING_COLOR_PREF, STRING_COLOR_DARK);
			PreferenceConverter.setDefault(preferenceStore, DEFAULT_COLOR_COLOR_PREF, DEFAULT_COLOR_DARK);
			PreferenceConverter.setDefault(preferenceStore, TAG_COLOR_PREF, TAG_COLOR_DARK);
		} else {
			PreferenceConverter.setDefault(preferenceStore, XML_COMMENT_COLOR_PREF, XML_COMMENT_COLOR);
			PreferenceConverter.setDefault(preferenceStore, PROC_INSTR_COLOR_PREF, PROC_INSTR_COLOR);
			PreferenceConverter.setDefault(preferenceStore, STRING_COLOR_PREF, STRING_COLOR);
			PreferenceConverter.setDefault(preferenceStore, DEFAULT_COLOR_COLOR_PREF, DEFAULT_COLOR);
			PreferenceConverter.setDefault(preferenceStore, TAG_COLOR_PREF, TAG_COLOR);
		}
	}

	@Override
	public boolean isColorPreference(String preference) {
		return preference.startsWith(PREFERENCE_PREFIX);
	}

	public Color getCommentColor() {
		return getColor(XML_COMMENT_COLOR_PREF);
	}

	public Color getProcInstrColor() {
		return getColor(PROC_INSTR_COLOR_PREF);
	}

	public Color getStringColor() {
		return getColor(STRING_COLOR_PREF);
	}

	public Color getDefaultColor() {
		return getColor(DEFAULT_COLOR_COLOR_PREF);
	}

	public Color getTagColor() {
		return getColor(TAG_COLOR_PREF);
	}
}
