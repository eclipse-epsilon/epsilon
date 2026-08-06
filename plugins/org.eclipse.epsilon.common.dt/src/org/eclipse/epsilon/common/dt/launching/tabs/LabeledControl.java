/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.launching.tabs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

public abstract class LabeledControl<T extends Control> extends Composite {
	
	protected Label label;
	protected T labeled;
	
	public LabeledControl(Composite parent, int style, String labelText) {
		super(parent, style);
	
		this.setLayout(new GridLayout(2, false));
		
		label = new Label(this, SWT.NONE);
		label.setText(labelText);
		
		labeled = createLabeled(this);
		GridData labeledData = new GridData(GridData.FILL_HORIZONTAL);
		labeled.setLayoutData(labeledData);
		
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		label.setEnabled(enabled);
		labeled.setEnabled(enabled);
	}
	
	protected abstract T createLabeled(Composite parent);

	public T getLabeled() {
		return labeled;
	}
	
	public Label getLabel() {
		return label;
	}
	
}
