/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.epl.dt.launching.tabs;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.epsilon.common.dt.EpsilonPlugin;
import org.eclipse.epsilon.common.dt.launching.tabs.AbstractSourceConfigurationTab;
import org.eclipse.epsilon.common.dt.launching.tabs.LabeledControl;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.epsilon.epl.dt.EplPlugin;
import org.eclipse.epsilon.epl.dt.launching.EplLaunchConfigurationAttributes;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class EplSourceConfigurationTab extends AbstractSourceConfigurationTab {
	
	protected Button repeatWhileMatchesFoundButton;
	protected LabeledControl<Text> maxLoopsControl = null;
	protected Text maxLoopsText = null;
	
	@Override
	public EpsilonPlugin getPlugin() {
		return EplPlugin.getDefault();
	}

	@Override
	public String getImagePath() {
		return "icons/epl.gif";
	}

	@Override
	public String getFileExtension() {
		return "epl";
	}

	@Override
	public String getSelectionTitle() {
		return "Select an EPL Program";
	}

	@Override
	public String getSelectionSubtitle() {
		return "EPL Programs in Workspace";
	}

	public String getLaunchConfigurationKey() {
		return "SOURCE.EPL";
	}
	
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		extras.setLayout(new GridLayout(1, true));
		
		repeatWhileMatchesFoundButton = new Button(extras, SWT.CHECK);
		repeatWhileMatchesFoundButton.setSelection(false);
		repeatWhileMatchesFoundButton.setText("Repeat while matches are found");
		repeatWhileMatchesFoundButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				maxLoopsControl.setEnabled(repeatWhileMatchesFoundButton.getSelection());
				setDirty(true);
				updateLaunchConfigurationDialog();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}
		});
		
		maxLoopsControl = new LabeledControl<Text>(extras, SWT.NONE, "Maximum number of iterations (-1 for infinite)") {
			
			@Override
			protected Text createLabeled(Composite parent) {
				return new Text(parent, SWT.BORDER);
			}
		};
		maxLoopsText = maxLoopsControl.getLabeled();
		
		maxLoopsText.addModifyListener(this);
	}
	
	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		configuration.setAttribute(EplLaunchConfigurationAttributes.REPEAT_WHILE_MATCHES_FOUND, repeatWhileMatchesFoundButton.getSelection());
		configuration.setAttribute(EplLaunchConfigurationAttributes.MAX_LOOPS, Integer.parseInt(maxLoopsText.getText()));
	}
	
	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		
		super.initializeFrom(configuration);
		try {
			repeatWhileMatchesFoundButton.setSelection(configuration.getAttribute(EplLaunchConfigurationAttributes.REPEAT_WHILE_MATCHES_FOUND, false));;
		}
		catch (Exception ex) {
			LogUtil.log(ex);
		}
		try {
			maxLoopsText.setText("" + configuration.getAttribute(EplLaunchConfigurationAttributes.MAX_LOOPS, EplModule.INFINITE));
		}
		catch (Exception ex) {
			LogUtil.log(ex);
			maxLoopsText.setText(EplModule.INFINITE + "");
		}
		
		maxLoopsControl.setEnabled(repeatWhileMatchesFoundButton.getSelection());
	}

}
