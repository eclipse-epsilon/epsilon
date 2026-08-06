/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.dt.examples;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

public class WizardNewImmuatableProjectCreationPage extends WizardNewProjectCreationPage {

	public WizardNewImmuatableProjectCreationPage(String pageName) {
		super(pageName);
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		
		Composite composite = (Composite) getControl();
		List<Control> texts = getDescendants(composite, Text.class);
		
		if (texts.size() > 0) {
			((Text) texts.get(0)).setEnabled(false);
		}
		
		if (visible) { validatePage(); }
		
	}
	
	protected List<Control> getDescendants(Composite composite, Class<?> c) {
		List<Control> controls = new ArrayList<>();
		if (c.isInstance(composite)) controls.add(composite);
		
		for (Control control : composite.getChildren()) {
			if (control instanceof Composite) {
				controls.addAll(getDescendants((Composite) control, c));
			}
			else {
				if (c.isInstance(control)) controls.add(control);
			}
		}
		return controls;
	}
	
}
