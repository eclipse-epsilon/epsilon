/*******************************************************************************
 * Copyright (c) 2016 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Saheed Popoola - initial API and implementation
 *     Horacio Hoyos - aditional functionality
 ******************************************************************************/
package org.eclipse.epsilon.emg.dt.launching.tabs;


import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.epsilon.common.dt.EpsilonPlugin;
import org.eclipse.epsilon.common.dt.launching.tabs.LabeledControl;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.emg.dt.EmgPlugin;
import org.eclipse.epsilon.emg.dt.launching.EmgLaunchConfigurationAttributes;
import org.eclipse.epsilon.epl.dt.launching.tabs.EplSourceConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * The Class EmgSourceConfigurationTab extends the EplSourceConfigurationTab to 
 * provide additional fields for specifying a seed for the random generation.
 */
public class EmgSourceConfigurationTab extends EplSourceConfigurationTab {
	
	/** The seed label. */
	//private Label seedLabel;
	
	/** The seed value. */
	private Text seedValueText;
	
	/** The random seed. */
	private Button randomSeed;

	private LabeledControl<Text> seedLoopsControl;

	private Button useSeed;


	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.epl.dt.launching.tabs.EplSourceConfigurationTab#getPlugin()
	 */
	@Override
	public EpsilonPlugin getPlugin() {
		return EmgPlugin.getDefault();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.epl.dt.launching.tabs.EplSourceConfigurationTab#getFileExtension()
	 */
	@Override
	public String getFileExtension() {
		return "emg";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.epl.dt.launching.tabs.EplSourceConfigurationTab#getSelectionTitle()
	 */
	@Override
	public String getSelectionTitle() {
		return "Select an EMG Program";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.epl.dt.launching.tabs.EplSourceConfigurationTab#getSelectionSubtitle()
	 */
	@Override
	public String getSelectionSubtitle() {
		return "EMG Programs in Workspace";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.epl.dt.launching.tabs.EplSourceConfigurationTab#getLaunchConfigurationKey()
	 */
	@Override
	public String getLaunchConfigurationKey() {
		return "SOURCE.EMG";
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.common.dt.launching.AbstractSourceConfigurationTab#getTitle()
	 */
	public String getTitle() {
		return "Source";
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.epl.dt.launching.tabs.EplSourceConfigurationTab#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		useSeed = new Button(extras, SWT.CHECK);
		useSeed.setSelection(false);
		useSeed.setText("Use a specific seed.");
		useSeed.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				seedLoopsControl.setEnabled(useSeed.getSelection());
				randomSeed.setEnabled(useSeed.getSelection());
				setDirty(true);
				updateLaunchConfigurationDialog();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		seedLoopsControl = new LabeledControl<Text>(extras, SWT.NONE, getSeedLabel()) {
			
			@Override
			protected Text createLabeled(Composite parent) {
				Text textField = new Text(parent, SWT.BORDER);
				textField.setSize(textField.getSize().x * 2, textField.getSize().y);
				return textField;
			}
		};
		seedValueText = seedLoopsControl.getLabeled();
		//seedValueText.addModifyListener(this);
		randomSeed = new Button(extras, SWT.NONE);
		randomSeed.setText("Use random seed");
		randomSeed.setToolTipText("Generate a new seed");
		randomSeed.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				seedValueText.setText(String.valueOf(EmgPlugin.getRandomSeed()));
			}

		});
		
	}
	
	/**
	 * Gets the seed label.
	 *
	 * @return the seed label
	 */
	private String getSeedLabel() {
		return "Seed Value";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.epl.dt.launching.tabs.EplSourceConfigurationTab#performApply(org.eclipse.debug.core.ILaunchConfigurationWorkingCopy)
	 */
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		if (useSeed.getSelection())
			configuration.setAttribute(EmgLaunchConfigurationAttributes.SEED, Integer.parseInt(seedValueText.getText()));
		configuration.setAttribute(EmgLaunchConfigurationAttributes.USE_SEED, useSeed.getSelection());
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.epsilon.epl.dt.launching.tabs.EplSourceConfigurationTab#initializeFrom(org.eclipse.debug.core.ILaunchConfiguration)
	 */
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		
		super.initializeFrom(configuration);
		try {
			useSeed.setSelection(configuration.getAttribute(EmgLaunchConfigurationAttributes.USE_SEED, false));
		}
		catch (Exception ex) {
			LogUtil.log(ex);
			useSeed.setSelection(false);
		}
		try {
			seedValueText.setText(String.valueOf(configuration.getAttribute(EmgLaunchConfigurationAttributes.SEED, EmgPlugin.getRandomSeed())));
		}
		catch (Exception ex) {
			LogUtil.log(ex);
			seedValueText.setText(String.valueOf(EmgLaunchConfigurationAttributes.DEFAULT_SEED));
		}
		seedLoopsControl.setEnabled(useSeed.getSelection());
		randomSeed.setEnabled(useSeed.getSelection());
	}
	
	
}
