/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.dt.util;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

public abstract class ThemeChangeListener implements IPropertyChangeListener {

	protected String activeTheme = null;
	
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (EclipseUtil.getActiveTheme().equals(activeTheme)) return;
		else {
			activeTheme = EclipseUtil.getActiveTheme();
			themeChange();
		}
	}

	public abstract void themeChange();
	
}
