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

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public abstract class AbstractHighlightingManager {

	protected IPreferenceStore preferenceStore;

	public IPreferenceStore getPreferenceStore() {
		return preferenceStore;
	}

	protected Color getColor(String preference) {
		return new Color(Display.getDefault(),
				PreferenceConverter.getColor(preferenceStore, preference));
	}

	public void initialiseDefaultColors() {
		// avoid changes if current defaults match the current theme
		if (!areDefaultColorsCorrect()) {
			setDefaults(); 
		}
	}

	public abstract boolean isColorPreference(String preference);

	protected abstract boolean areDefaultColorsCorrect();

	protected abstract void setDefaults();

}
