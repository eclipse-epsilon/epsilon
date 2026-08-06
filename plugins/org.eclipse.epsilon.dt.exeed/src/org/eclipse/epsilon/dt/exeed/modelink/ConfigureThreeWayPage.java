/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed.modelink;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class ConfigureThreeWayPage extends WizardPage {

	protected ModeLink modeLink;
	
	protected ConfigureThreeWayPage(String pageName, ModeLink modeLink) {
		super(pageName);
		this.modeLink = modeLink;
		this.setTitle("Editor layout");
		this.setDescription("Specify the layout of the editor");
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.FILL);
		composite.setLayout(new RowLayout());
		
		Button threeWayButton = new Button(composite, SWT.RADIO | SWT.TOP);
		threeWayButton.setText("Three panels");
		Button twoWayButton = new Button(composite, SWT.RADIO | SWT.TOP);
		twoWayButton.setText("Two panels");
		
		if (modeLink.isThreeWay())
			threeWayButton.setSelection(true);
		else 
			twoWayButton.setSelection(true);
		
		final ModeLink modeLink = this.modeLink;
		threeWayButton.addListener(SWT.Selection, new Listener(){

			public void handleEvent(Event event) {
				modeLink.setThreeWay(true);
			}
			
		});
		
		twoWayButton.addListener(SWT.Selection, new Listener(){

			public void handleEvent(Event event) {
				modeLink.setThreeWay(false);
			}
			
		});
		
		setControl(composite);
	}

}
