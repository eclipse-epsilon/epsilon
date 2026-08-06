/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.dt.editor.highlighting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.epsilon.common.dt.EpsilonCommonsPlugin;
import org.eclipse.epsilon.common.dt.util.EclipseUtil;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

public class EpsilonHighlightingManager extends AbstractHighlightingManager {
	
	static final RGB COMMENT_COLOR = new RGB(63, 127, 95);
	static final RGB ANNOTATION_COLOR = new RGB(184, 160, 0);
	static final RGB STRING_COLOR = new RGB(42, 0, 255);
	static final RGB DEFAULT_COLOR = new RGB(0, 0, 0);
	static final RGB KEYWORD_COLOR = new RGB(127, 0, 85);
	static final RGB BUILTIN_COLOR = new RGB(42, 0, 255);
	static final RGB ASSERTION_COLOR = new RGB(255, 0, 0);
	static final RGB TYPE_COLOR = new RGB(0, 192, 0);
	static final RGB MARKER_COLOR = COMMENT_COLOR;
	static final RGB EGL_STATIC_TEXT_COLOR = new RGB(42, 0, 255);

	static final RGB COMMENT_COLOR_DARK = new RGB(190, 218, 0);
	static final RGB ANNOTATION_COLOR_DARK = new RGB(190, 214, 255);
	static final RGB STRING_COLOR_DARK = new RGB(115, 148, 255);
	static final RGB DEFAULT_COLOR_DARK = new RGB(255, 255, 255);
	static final RGB KEYWORD_COLOR_DARK = new RGB(243, 191, 0);
	static final RGB BUILTIN_COLOR_DARK = new RGB(182, 252, 255);
	static final RGB ASSERTION_COLOR_DARK = new RGB(243, 0, 70);
	static final RGB TYPE_COLOR_DARK = new RGB(118, 167, 37);
	static final RGB MARKER_COLOR_DARK = COMMENT_COLOR_DARK;
	static final RGB EGL_STATIC_TEXT_COLOR_DARK = new RGB(115, 148, 255);

	private static final String PREFERENCE_PREFIX = EpsilonCommonsPlugin.PLUGIN_ID;

	private static String prefixPreference(String preference) {
		return String.format("%s.%s", PREFERENCE_PREFIX, preference);
	}

	public static final String COMMENT_COLOR_PREF = prefixPreference("commentColor");
	public static final String ANNOTATION_COLOR_PREF = prefixPreference("annotationColor");
	public static final String STRING_COLOR_PREF = prefixPreference("stringColor");
	public static final String DEFAULT_COLOR_PREF = prefixPreference("defaultColor");
	public static final String KEYWORD_COLOR_PREF = prefixPreference("keywordColor");
	public static final String BUILTIN_COLOR_PREF = prefixPreference("builtinColor");
	public static final String ASSERTION_COLOR_PREF = prefixPreference("assertionColor");
	public static final String TYPE_COLOR_PREF = prefixPreference("typeColor");
	public static final String MARKER_COLOR_PREF = prefixPreference("markerColor");
	public static final String EGL_STATIC_TEXT_COLOR_PREF = prefixPreference("eglStaticText");
	
	public static final List<String> COLOR_PREFERENCES = 
			new ArrayList<> (Arrays.asList(
					COMMENT_COLOR_PREF,
					ANNOTATION_COLOR_PREF,
					STRING_COLOR_PREF,
					DEFAULT_COLOR_PREF,
					KEYWORD_COLOR_PREF,
					BUILTIN_COLOR_PREF,
					ASSERTION_COLOR_PREF,
					TYPE_COLOR_PREF,
					MARKER_COLOR_PREF,
					EGL_STATIC_TEXT_COLOR_PREF));

	public EpsilonHighlightingManager() {
		preferenceStore = EpsilonCommonsPlugin.getDefault().getPreferenceStore();
	}

	@Override
	protected boolean areDefaultColorsCorrect() {
		return 
			(EclipseUtil.isDarkThemeEnabled() &&
				PreferenceConverter.getDefaultColor(preferenceStore, EGL_STATIC_TEXT_COLOR_PREF).equals(EGL_STATIC_TEXT_COLOR_DARK))
			||
			(!EclipseUtil.isDarkThemeEnabled() &&
				 PreferenceConverter.getDefaultColor(preferenceStore, EGL_STATIC_TEXT_COLOR_PREF).equals(EGL_STATIC_TEXT_COLOR));
	}
	
	@Override
	protected void setDefaults() {
		if (EclipseUtil.isDarkThemeEnabled()) {
			PreferenceConverter.setDefault(preferenceStore, COMMENT_COLOR_PREF, COMMENT_COLOR_DARK);
			PreferenceConverter.setDefault(preferenceStore, ANNOTATION_COLOR_PREF, ANNOTATION_COLOR_DARK);
			PreferenceConverter.setDefault(preferenceStore, STRING_COLOR_PREF, STRING_COLOR_DARK);
			PreferenceConverter.setDefault(preferenceStore, DEFAULT_COLOR_PREF, DEFAULT_COLOR_DARK);
			PreferenceConverter.setDefault(preferenceStore, KEYWORD_COLOR_PREF, KEYWORD_COLOR_DARK);
			PreferenceConverter.setDefault(preferenceStore, BUILTIN_COLOR_PREF, BUILTIN_COLOR_DARK);
			PreferenceConverter.setDefault(preferenceStore, ASSERTION_COLOR_PREF, ASSERTION_COLOR_DARK);
			PreferenceConverter.setDefault(preferenceStore, TYPE_COLOR_PREF, TYPE_COLOR_DARK);
			PreferenceConverter.setDefault(preferenceStore, MARKER_COLOR_PREF, MARKER_COLOR_DARK);
			PreferenceConverter.setDefault(preferenceStore, EGL_STATIC_TEXT_COLOR_PREF, EGL_STATIC_TEXT_COLOR_DARK);
		} else {
			PreferenceConverter.setDefault(preferenceStore, COMMENT_COLOR_PREF, COMMENT_COLOR);
			PreferenceConverter.setDefault(preferenceStore, ANNOTATION_COLOR_PREF, ANNOTATION_COLOR);
			PreferenceConverter.setDefault(preferenceStore, STRING_COLOR_PREF, STRING_COLOR);
			PreferenceConverter.setDefault(preferenceStore, DEFAULT_COLOR_PREF, DEFAULT_COLOR);
			PreferenceConverter.setDefault(preferenceStore, KEYWORD_COLOR_PREF, KEYWORD_COLOR);
			PreferenceConverter.setDefault(preferenceStore, BUILTIN_COLOR_PREF, BUILTIN_COLOR);
			PreferenceConverter.setDefault(preferenceStore, ASSERTION_COLOR_PREF, ASSERTION_COLOR);
			PreferenceConverter.setDefault(preferenceStore, TYPE_COLOR_PREF, TYPE_COLOR);
			PreferenceConverter.setDefault(preferenceStore, MARKER_COLOR_PREF, MARKER_COLOR);
			PreferenceConverter.setDefault(preferenceStore, EGL_STATIC_TEXT_COLOR_PREF, EGL_STATIC_TEXT_COLOR);
		}
	}

	@Override
	public boolean isColorPreference(String preference) {
		return preference.startsWith(PREFERENCE_PREFIX);
	}
	
	public Color getCommentColor() {
		return getColor(COMMENT_COLOR_PREF);
	}
	
	public Color getAnnotationColor() {
		return getColor(ANNOTATION_COLOR_PREF);
	}
	
	public Color getStringColor() {
		return getColor(STRING_COLOR_PREF);
	}
	
	public Color getDefaultColor() {
		return getColor(DEFAULT_COLOR_PREF);
	}
	
	public Color getKeywordColor() {
		return getColor(KEYWORD_COLOR_PREF);
	}
	
	public Color getBuiltinColor() {
		return getColor(BUILTIN_COLOR_PREF);
	}
	
	public Color getAssertionColor() {
		return getColor(ASSERTION_COLOR_PREF);
	}
	
	public Color getTypeColor() {
		return getColor(TYPE_COLOR_PREF);
	}
	
	public Color getMarkerColor() {
		return getColor(MARKER_COLOR_PREF);
	}
	
	public Color getEGLStaticColor() {
		return getColor(EGL_STATIC_TEXT_COLOR_PREF);
	}
}
