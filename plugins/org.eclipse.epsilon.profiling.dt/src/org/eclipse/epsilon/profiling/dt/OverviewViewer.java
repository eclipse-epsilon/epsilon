/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.profiling.dt;

import org.eclipse.epsilon.profiling.ProfilerOverview;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public class OverviewViewer extends Composite {
	
	protected ScrolledForm form = null;
	protected Label executionTimeLabel;
	protected Label executionCountLabel;
	protected Label averageLabel;
	
	public OverviewViewer(Composite parent, int style) {
		super(parent, style);
		this.setLayout(new FillLayout());
		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		form = toolkit.createScrolledForm(this);
		GridLayout layout = new GridLayout();
		form.getBody().setLayout(layout);
		executionTimeLabel = createLabel(toolkit);
		executionCountLabel = createLabel(toolkit);
		averageLabel = createLabel(toolkit);
		setProfilerOverview(null);
	}
	
	protected Label createLabel(FormToolkit toolkit) {
		Label label = toolkit.createLabel(form.getBody(), "");
		label.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		return label;
	}
	
	public void setProfilerOverview(ProfilerOverview overview) {
		if (overview == null) {
			executionTimeLabel.setText("Nothing has been profiled yet.");
			executionCountLabel.setText("");
			averageLabel.setText("");
		}
		else {
			executionTimeLabel.setText( "Total profiled time: " + overview.getExecutionTime() + "ms");
			executionCountLabel.setText("Targets profiled: " + overview.getExecutionCount());
			averageLabel.setText(       "Average target time: " + overview.getAverageExecutionTime() + "ms");
		}
	}
	
}
