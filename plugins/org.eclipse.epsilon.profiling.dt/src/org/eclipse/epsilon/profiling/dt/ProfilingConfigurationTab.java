/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.profiling.dt;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.epsilon.common.dt.EpsilonCommonsPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class ProfilingConfigurationTab extends AbstractLaunchConfigurationTab {
	
	private Button enableProfilerButton;
	private Button resetProfilerButton;
	private Button fineGrainedProfilingButton;
	private Button profileModelLoadingButton;
	
	public static final String PROFILING_ENABLED = "profiling_enabled";
	public static final String RESET_PROFILER = "reset_profiler";
	public static final String FINE_GRAINED_PROFILING = "fine_grained_profiling";
	public static final String PROFILE_MODEL_LOADING = "profile_model_loading";
	
	@Override
	public void createControl(Composite parent) {
		
		GridLayout parentLayout = new GridLayout();
		parent.setLayout(parentLayout);

		Composite control = new Composite(parent, SWT.FILL);
		setControl(control);
		GridLayout controlLayout = new GridLayout(1, false);
		control.setLayout(controlLayout);
		
		enableProfilerButton = createCheckbox(control, "Enable profiling", null);
		resetProfilerButton = createCheckbox(control, "Reset profiler before launch", enableProfilerButton);
		fineGrainedProfilingButton = createCheckbox(control, "Fine-grained profiling", enableProfilerButton);
		profileModelLoadingButton = createCheckbox(control, "Profile model loading", enableProfilerButton);
		profileModelLoadingButton.setSelection(enableProfilerButton.getSelection());
		
		control.pack();
		control.layout();
		
		canSave();
	}

	
	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		
	}
	
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			boolean profilerEnabled = configuration.getAttribute(PROFILING_ENABLED, false);
			boolean resetProfiler = configuration.getAttribute(RESET_PROFILER, false);
			boolean fineGrainedProfiling = configuration.getAttribute(FINE_GRAINED_PROFILING, false);
			boolean profileModelLoading = configuration.getAttribute(PROFILE_MODEL_LOADING, false);
			enableProfilerButton.setSelection(profilerEnabled);
			resetProfilerButton.setSelection(resetProfiler);
			fineGrainedProfilingButton.setSelection(fineGrainedProfiling);
			profileModelLoadingButton.setSelection(profileModelLoading);
			
			resetProfilerButton.setEnabled(enableProfilerButton.getSelection());
			fineGrainedProfilingButton.setEnabled(enableProfilerButton.getSelection());
			profileModelLoadingButton.setEnabled(enableProfilerButton.getSelection());
			
			canSave();
			updateLaunchConfigurationDialog();
		}
		catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		boolean pEnabled = enableProfilerButton.getSelection();
		configuration.setAttribute(PROFILING_ENABLED, pEnabled);
		configuration.setAttribute(RESET_PROFILER, pEnabled && resetProfilerButton.getSelection());
		configuration.setAttribute(FINE_GRAINED_PROFILING, pEnabled && fineGrainedProfilingButton.getSelection());
		configuration.setAttribute(PROFILE_MODEL_LOADING, pEnabled && profileModelLoadingButton.getSelection());
	}

	@Override
	public String getName() {
		return "Profiling";
	}
	
	@Override
	public Image getImage() {
		return EpsilonCommonsPlugin.getDefault().createImage("icons/profiling.png");
	}
	
	@Override
	public boolean canSave() {
		setErrorMessage(null);
		return true;
	}
	
	private Button createCheckbox(Composite parent, String text, final Button parentCheckBox) {
		final Button button = new Button(parent, SWT.CHECK);
		button.setText(text);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		
		if (parentCheckBox != null) {
			gridData.horizontalIndent = 8;
			parentCheckBox.addSelectionListener(new SelectionListener() {
				
				@Override
				public void widgetSelected(SelectionEvent e) {
					button.setEnabled(parentCheckBox.getSelection());
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					
				}
			});
		}
		
		button.setLayoutData(gridData);
		button.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		return button;
	}
	
}
