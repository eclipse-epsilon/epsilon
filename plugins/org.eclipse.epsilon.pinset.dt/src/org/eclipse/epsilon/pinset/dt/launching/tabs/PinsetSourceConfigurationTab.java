/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package org.eclipse.epsilon.pinset.dt.launching.tabs;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.epsilon.common.dt.EpsilonPlugin;
import org.eclipse.epsilon.common.dt.launching.tabs.AbstractSourceConfigurationTab;
import org.eclipse.epsilon.pinset.dt.PinsetPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

/**
 * PinsetSourceConfigurationTab.
 *
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class PinsetSourceConfigurationTab extends AbstractSourceConfigurationTab implements SelectionListener {

	public static final String OUTPUT_FOLDER = "outputFolder";

	public static final String GENERATE_TO = "generateTo";
	public static final int GENERATE_TO_DEFAULT_FOLDER = 1;
	public static final int GENERATE_TO_CUSTOM_FOLDER = 2;

	protected Group outputFolderGroup;
	protected Text outputFolderPath;
	protected Button generateToDefaultFolder;
	protected Button generateToCustomFolder;
	protected Button browseForOutputFolder;

	@Override
	public EpsilonPlugin getPlugin() {
		return PinsetPlugin.getDefault();
	}

	@Override
	public String getImagePath() {
		return "icons/edl.png";
	}

	@Override
	public String getFileExtension() {
		return "pinset";
	}

	@Override
	public String getSelectionTitle() {
		return "Select a Pinset file";
	}

	@Override
	public String getSelectionSubtitle() {
		return "Pinset files in Workspace";
	}

	public String getLaunchConfigurationKey() {
		return "SOURCE.Pinset";
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);

		extras.setLayout(new GridLayout(1, true));
		createOutputFolderGroup(extras);
	}

	protected void createOutputFolderGroup(Composite control) {
		outputFolderGroup = createGroup(control, "Datasets should be generated in:", 1);

		generateToDefaultFolder = new Button(outputFolderGroup, SWT.RADIO);
		generateToDefaultFolder.setText("The current folder (where the Pinset file is)");
		generateToDefaultFolder.addSelectionListener(this);

		generateToCustomFolder = new Button(outputFolderGroup, SWT.RADIO);
		generateToCustomFolder.setText("The following folder:");
		generateToCustomFolder.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				updateEnabledStateOfOutputWidgets();
			}

			public void widgetSelected(SelectionEvent e) {
				updateEnabledStateOfOutputWidgets();
			}

		});

		final Composite outputFolderContainer = createTwoColumnComposite(outputFolderGroup);
		outputFolderPath = createPathTextBox(outputFolderContainer);
		browseForOutputFolder = createBrowseWorkspaceForContainerButton(
				outputFolderContainer, outputFolderPath, "Output folder", "Select a folder:");
	}

	protected Text createPathTextBox(Composite parent) {
		final Text text = new Text(parent, SWT.BORDER);

		final GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = 25;
		text.setLayoutData(data);

		text.addModifyListener(this);

		return text;
	}

	protected void updateEnabledStateOfOutputWidgets() {
		outputFolderPath.setEnabled(generateToCustomFolder.getSelection());
		browseForOutputFolder.setEnabled(generateToCustomFolder.getSelection());
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		canSave();
		updateEnabledStateOfOutputWidgets();
		updateLaunchConfigurationDialog();
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		updateEnabledStateOfOutputWidgets();
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);

		configuration.setAttribute(OUTPUT_FOLDER, "");
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);

		configuration.setAttribute(OUTPUT_FOLDER, outputFolderPath.getText());
		configuration.setAttribute(GENERATE_TO,
				generateToCustomFolder.getSelection() ? GENERATE_TO_CUSTOM_FOLDER : GENERATE_TO_DEFAULT_FOLDER);
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		super.initializeFrom(configuration);

		try {
			switch (configuration.getAttribute(GENERATE_TO, GENERATE_TO_DEFAULT_FOLDER)) {
			case GENERATE_TO_DEFAULT_FOLDER:
				generateToDefaultFolder.setSelection(true);
				generateToCustomFolder.setSelection(false);
				break;

			case GENERATE_TO_CUSTOM_FOLDER:
				generateToCustomFolder.setSelection(true);
				generateToDefaultFolder.setSelection(false);
				break;
			}

			outputFolderPath.setText(configuration.getAttribute(OUTPUT_FOLDER, ""));

			updateEnabledStateOfOutputWidgets();
		}
		catch (CoreException e) {
			// ignore
		}
	}

	@Override
	public String getTitle() {
		return "Pinset";
	}

}
