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

import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class EpsilonColorFieldEditor extends ColorFieldEditor {

	protected static int LABEL_WIDTH = 180;
	protected static int LABEL_HEIGHT = 25;

	public EpsilonColorFieldEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
	}
	
	@Override
	protected void doFillIntoGrid(Composite parent, int numColumns) {
		Label label = getLabelControl(parent);

		GridData gd = new GridData(LABEL_WIDTH, LABEL_HEIGHT);
		gd.horizontalSpan = numColumns - 1;
		gd.verticalAlignment = SWT.CENTER;
		label.setLayoutData(gd);

		Button colorButton = getChangeControl(parent);
		colorButton.setLayoutData(new GridData());
	}

}
