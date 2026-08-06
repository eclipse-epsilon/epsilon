/*******************************************************************************
 * Copyright (c) 2008-2022 The University of York, Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 *     Antonio García-Domínguez - detect invalid trace configuration
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.launching.tabs;

import static org.eclipse.epsilon.egl.dt.launching.EglLaunchConfigurationAttributes.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.epsilon.common.dt.EpsilonPlugin;
import org.eclipse.epsilon.common.dt.launching.tabs.AbstractSourceConfigurationTab;
import org.eclipse.epsilon.egl.dt.EglPlugin;
import org.eclipse.epsilon.egl.dt.extensions.fineGrainedTracePostprocessor.FineGrainedTracePostprocessorSpecificationFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EglSourceConfigurationTab extends AbstractSourceConfigurationTab implements SelectionListener {

	protected static final String ERROR_TRACE_CONSOLE = "Cannot generate a trace when printing output to the console";

	protected Text outputFilePath;
	protected Text outputDirPath;
	protected Button generateToConsole;
	protected Button generateToFile;
	protected Button generateToDefaultDir;
	protected Button generateToCustomDir;
	protected Button browseForOutputFile;
	protected Button browseForOutputDir;
	protected Group eglTargetGroup;
	protected Group egxTargetGroup;
	protected Group traceGroup;
	protected Button appendToFile;
	protected Text traceDestination;
	protected Button browseForTraceDestination;
	protected Button produceTrace;
	
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout( new RowLayout());
		
		new EglSourceConfigurationTab().createControl(shell);
		
		shell.pack();
		shell.open();
		while (!shell.isDisposed() && !display.readAndDispatch()) {
			display.sleep();
		}
		display.dispose();
	}
	
	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		
		extras.setLayout(new GridLayout(1, true));
		createEglTargetGroup(extras);
		createEgxTargetGroup(extras);
		createTraceGroup(extras);
		
		filePath.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				
				boolean egx = filePath.getText().endsWith("egx");
				egxTargetGroup.setVisible(egx);
				((GridData) egxTargetGroup.getLayoutData()).exclude = !egx;
				eglTargetGroup.setVisible(!egx);
				((GridData) eglTargetGroup.getLayoutData()).exclude = egx;
				
				extras.layout(true, true);
			}
		});
	}

	protected void createEglTargetGroup(Composite control) {
		eglTargetGroup = createGroup(control, "Text generated should be printed to:", 1);
		
		generateToConsole = new Button(eglTargetGroup, SWT.RADIO);
		generateToConsole.setText("The console");
		generateToConsole.addSelectionListener(this);
		
		generateToFile = new Button(eglTargetGroup, SWT.RADIO);
		generateToFile.setText("The following file:");
		generateToFile.addSelectionListener(new SelectionListener() {
			
			public void widgetDefaultSelected(SelectionEvent e) {
				updateEnabledStateOfOutputWidgets();
			}

			public void widgetSelected(SelectionEvent e) {
				updateEnabledStateOfOutputWidgets();
				
				if (generateToFile.getSelection())
					outputFilePath.setFocus();
			}
			
		});
		
		final Composite outputFileContainer = createTwoColumnComposite(eglTargetGroup);
		outputFilePath = createPathTextBox(outputFileContainer);		
		browseForOutputFile = createBrowseWorkspaceForFileButton(outputFileContainer, outputFilePath);
		
		GridData appendToFileData = new GridData(GridData.FILL_HORIZONTAL);
		appendToFileData.horizontalIndent = 25;
		appendToFile = new Button(outputFileContainer, SWT.CHECK);
		appendToFile.setText("Append to file");
		appendToFile.setLayoutData(appendToFileData);
		appendToFile.addSelectionListener(this);
	}
	
	protected void createEgxTargetGroup(Composite control) {
		egxTargetGroup = createGroup(control, "Files should be generated in:", 1);
		
		generateToDefaultDir = new Button(egxTargetGroup, SWT.RADIO);
		generateToDefaultDir.setText("The current directory");
		generateToDefaultDir.addSelectionListener(this);
		
		generateToCustomDir = new Button(egxTargetGroup, SWT.RADIO);
		generateToCustomDir.setText("The following directory:");
		generateToCustomDir.addSelectionListener(new SelectionListener() {
			
			public void widgetDefaultSelected(SelectionEvent e) {
				updateEnabledStateOfOutputWidgets();
			}

			public void widgetSelected(SelectionEvent e) {
				updateEnabledStateOfOutputWidgets();
				
				if (generateToFile.getSelection())
					outputFilePath.setFocus();
			}
			
		});
		
		final Composite outputDirContainer = createTwoColumnComposite(egxTargetGroup);
		outputDirPath = createPathTextBox(outputDirContainer);		
		browseForOutputDir = createBrowseWorkspaceForContainerButton(outputDirContainer, outputDirPath, "Output folder", "Select a folder:");
	}
	
	protected void createTraceGroup(Composite control) {
		if (thereAreAnyFineGrainedTracePostprocessors()) {
			traceGroup = createGroup(control, "Trace:", 1);
			
			produceTrace = new Button(traceGroup, SWT.CHECK);
			produceTrace.setText("Produce a trace model?");
			produceTrace.addSelectionListener(this);
			produceTrace.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					updateEnabledStateOfTraceWidgets();
				}
				
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					updateEnabledStateOfTraceWidgets();
				}
			});		
			
			final Composite traceDestinationContainer = createTwoColumnComposite(traceGroup);
			traceDestination = createPathTextBox(traceDestinationContainer);
			browseForTraceDestination = createBrowseWorkspaceForFileButton(traceDestinationContainer, traceDestination);
		}
	}

	protected boolean thereAreAnyFineGrainedTracePostprocessors() {
		return !(new FineGrainedTracePostprocessorSpecificationFactory().loadAllFromExtensionPoints().isEmpty());
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
		
		outputDirPath.setEnabled(generateToCustomDir.getSelection());
		browseForOutputDir.setEnabled(generateToCustomDir.getSelection());		
		
		outputFilePath.setEnabled(generateToFile.getSelection());
		browseForOutputFile.setEnabled(generateToFile.getSelection());
		appendToFile.setEnabled(generateToFile.getSelection());
	}
	
	protected void updateEnabledStateOfTraceWidgets() {
		if (traceDestination != null) traceDestination.setEnabled(produceTrace.getSelection());
		if (browseForTraceDestination != null) browseForTraceDestination.setEnabled(produceTrace.getSelection());
	}

	@Override
	public boolean canSave() {
		if (eglTargetGroup != null && eglTargetGroup.isVisible()
				&& generateToConsole != null && generateToConsole.getSelection()
				&& produceTrace != null && produceTrace.getSelection()) {
			setErrorMessage(ERROR_TRACE_CONSOLE);
			return false;
		} else {
			setErrorMessage(null);
			return super.canSave();
		}
	}

	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		super.setDefaults(configuration);
		
		configuration.setAttribute(EGL_GENERATE_TO, GENERATE_TO_CONSOLE);
		configuration.setAttribute(OUTPUT_FILE_PATH, "");
		configuration.setAttribute(APPEND_TO_FILE, false);
		configuration.setAttribute(PRODUCE_TRACE, false);
	}
	
	public void initializeFrom(ILaunchConfiguration configuration) {
		super.initializeFrom(configuration);
		
		try {
			switch (configuration.getAttribute(EGL_GENERATE_TO, GENERATE_TO_CONSOLE)) {
				case GENERATE_TO_CONSOLE:
					generateToConsole.setSelection(true);
					generateToFile.setSelection(false);
					break;
				
				case GENERATE_TO_FILE:
					generateToFile.setSelection(true);
					generateToConsole.setSelection(false);
					break;
			}
			
			switch (configuration.getAttribute(EGX_GENERATE_TO, GENERATE_TO_DEFAULT_DIR)) {
			case GENERATE_TO_DEFAULT_DIR:
				generateToDefaultDir.setSelection(true);
				generateToCustomDir.setSelection(false);
				break;
			
			case GENERATE_TO_CUSTOM_DIR:
				generateToCustomDir.setSelection(true);
				generateToDefaultDir.setSelection(false);
				break;
			}
			
			outputDirPath.setText(configuration.getAttribute(OUTPUT_DIR_PATH, ""));
			outputFilePath.setText(configuration.getAttribute(OUTPUT_FILE_PATH, ""));			
			appendToFile.setSelection(configuration.getAttribute(APPEND_TO_FILE, false));
			
			if (traceDestination != null) traceDestination.setText(configuration.getAttribute(TRACE_DESTINATION, ""));
			if (produceTrace != null) produceTrace.setSelection(configuration.getAttribute(PRODUCE_TRACE, false));
			
			updateEnabledStateOfOutputWidgets();
			updateEnabledStateOfTraceWidgets();
			
		} catch (CoreException e) {
			// ignore
		}
		
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		super.performApply(configuration);
		
		configuration.setAttribute(EGL_GENERATE_TO, generateToFile.getSelection() ? GENERATE_TO_FILE : GENERATE_TO_CONSOLE);
		configuration.setAttribute(EGX_GENERATE_TO, generateToCustomDir.getSelection() ? GENERATE_TO_CUSTOM_DIR : GENERATE_TO_DEFAULT_DIR);
		configuration.setAttribute(OUTPUT_FILE_PATH, outputFilePath.getText());
		configuration.setAttribute(OUTPUT_DIR_PATH, outputDirPath.getText());
		configuration.setAttribute(APPEND_TO_FILE, appendToFile.getSelection());
		
		if (traceDestination != null) configuration.setAttribute(TRACE_DESTINATION, traceDestination.getText());
		if (produceTrace != null) configuration.setAttribute(PRODUCE_TRACE, produceTrace.getSelection());
	}
	
	@Override
	public EpsilonPlugin getPlugin() {
		return EglPlugin.getDefault();
	}

	@Override
	public String getImagePath() {
		return "icons/egl.png";
	}

	@Override
	public String getFileExtension() {
		return "egl";
	}

	@Override
	public String getSelectionTitle() {
		return "Select an EGL Program";
	}

	@Override
	public String getSelectionSubtitle() {
		return "EGL Programs in Workspace";
	}

	public String getLaunchConfigurationKey() {
		return "SOURCE.EGL";
	}

	@Override
	public String getTitle() {
		return "Template";
	}

	public void widgetDefaultSelected(SelectionEvent e) {
		updateEnabledStateOfOutputWidgets();
	}

	public void widgetSelected(SelectionEvent e) {
		canSave();
		updateEnabledStateOfOutputWidgets();
		updateLaunchConfigurationDialog();
	}
}
