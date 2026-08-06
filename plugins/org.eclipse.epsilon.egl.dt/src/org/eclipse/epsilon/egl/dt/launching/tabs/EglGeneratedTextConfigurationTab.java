/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.launching.tabs;

import static org.eclipse.epsilon.egl.dt.launching.EglLaunchConfigurationAttributes.DEFAULT_FORMATTERS;
import static org.eclipse.epsilon.egl.dt.launching.EglLaunchConfigurationAttributes.TEMPLATE_FACTORY_TYPE;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.egl.dt.EglPlugin;
import org.eclipse.epsilon.egl.dt.extensions.ExtensionLocatorException;
import org.eclipse.epsilon.egl.dt.extensions.formatter.FormatterSpecification;
import org.eclipse.epsilon.egl.dt.extensions.formatter.FormatterSpecificationFactory;
import org.eclipse.epsilon.egl.dt.extensions.templateFactoryType.TemplateFactoryTypeSpecification;
import org.eclipse.epsilon.egl.dt.extensions.templateFactoryType.TemplateFactoryTypeSpecificationFactory;
import org.eclipse.epsilon.egl.dt.widgets.ListListener;
import org.eclipse.epsilon.egl.dt.widgets.ListWithControls;
import org.eclipse.epsilon.egl.dt.widgets.ListWithControls.ItemFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;

public class EglGeneratedTextConfigurationTab extends AbstractLaunchConfigurationTab {

	private ListWithControls<FormatterSpecification> defaultFormattersTable;
	private Combo templateFactoryTypeCombo;
		
	@Override
	public void createControl(Composite parent) {
		try {
			final GridLayout parentLayout = new GridLayout();
			parent.setLayout(parentLayout);

			final Composite control = new Composite(parent, SWT.NONE);
			setControl(control);
			control.setLayout(new GridLayout(1, false));
			
			PlatformUI.getWorkbench().getHelpSystem().setHelp(control, "org.eclipse.epsilon.help.egl_generated_text_tab");
			
			createDefaultFormattersGroup(control);
			createTemplateFactoryTypeGroup(control);
			
			control.setBounds(0, 0, 300, 300);
			control.layout();
			control.pack();
			
		    defaultFormattersTable.setFocus();
		
		} catch (ExtensionLocatorException e) {
			LogUtil.log(e);
		}
	}

	private void createDefaultFormattersGroup(Composite control) {
		final Group group = createGroup(control, "Default Formatters: ");
		
		final GridData tableData = new GridData();
		tableData.horizontalAlignment = SWT.FILL;
		tableData.verticalAlignment = SWT.FILL;
		tableData.grabExcessHorizontalSpace = true;
		
		defaultFormattersTable = new ListWithControls<>(group, SWT.NONE);
	    defaultFormattersTable.setSize(500, 200);
	    defaultFormattersTable.setLayoutData(tableData);
	    	    
	    defaultFormattersTable.setLabelProvider(new FormatterSpecificationLabelProvider());
	    defaultFormattersTable.setItemFactory(new ItemFactory<FormatterSpecification>() {

			@Override
			public FormatterSpecification createItem() {
				final FormatterSelectionDialogue dialogue = new FormatterSelectionDialogue(getShell());
				dialogue.setBlockOnOpen(true);
				dialogue.open();
				
				if (dialogue.getReturnCode() == Window.OK){
					return dialogue.getSelectedFormatter();
					
				} else {
					return null;
				}
			}
		});
	    
	    
	    defaultFormattersTable.addModelListener(new ListListener() {

			@Override
			public void changed(ChangeType changeType, Object item, int index) {
				updateLaunchConfigurationDialog();
			}
	    	
	    });
	    
	    defaultFormattersTable.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
	}
	
	private void createTemplateFactoryTypeGroup(Composite control) {
		final Group group = createGroup(control, "Type of Template Factory: ");
		
		final Collection<TemplateFactoryTypeSpecification> specs = new TemplateFactoryTypeSpecificationFactory().loadAllFromExtensionPoints();
		final Collection<String> templateFactoryTypeNames = new LinkedList<>();
		
		for (TemplateFactoryTypeSpecification spec : specs) {
			templateFactoryTypeNames.add(spec.getName());
		}
		
		templateFactoryTypeCombo = new Combo(group, SWT.READ_ONLY);
		templateFactoryTypeCombo.setItems(templateFactoryTypeNames.toArray(new String[]{}));
		templateFactoryTypeCombo.select(0);
		
		templateFactoryTypeCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				updateLaunchConfigurationDialog();
			}
		});
	}
	
	private Group createGroup(Composite control, final String name) {
		final Group group = new Group(control, SWT.SHADOW_ETCHED_IN);
		group.setLayout(new GridLayout(2, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		group.setText(name);
		return group;
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			initializeDefaultFormattersFrom(configuration);
			initializeTemplateFactoryTypeFrom(configuration);
			
		} catch (CoreException e) {
			LogUtil.log("Error encountered whilst attempting to restore selection of default formatters from launch configuration", e);
		}
		
	}

	private void initializeDefaultFormattersFrom(ILaunchConfiguration configuration) throws CoreException {
		final Collection<String> defaultFormatterIdentifiers = configuration.getAttribute(DEFAULT_FORMATTERS, new ArrayList<String>());
		defaultFormattersTable.setItems(new FormatterSpecificationFactory().findByIdentifiers(defaultFormatterIdentifiers));
	}

	private void initializeTemplateFactoryTypeFrom(ILaunchConfiguration configuration) throws CoreException {
		final TemplateFactoryTypeSpecificationFactory factory = new TemplateFactoryTypeSpecificationFactory();
		
		final String templateFactoryTypeIdentifier = configuration.getAttribute(TEMPLATE_FACTORY_TYPE, factory.findByIndex(0).getIdentifier());
		templateFactoryTypeCombo.select(factory.indexOf(templateFactoryTypeIdentifier));
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		saveDefaultFormattersTo(configuration);
		saveTemplateFactoryTypeTo(configuration);
	}

	private void saveDefaultFormattersTo(ILaunchConfigurationWorkingCopy configuration) {
		final List<String> defaultFormatterIdentifiers = new LinkedList<>();
		
		for (FormatterSpecification spec : defaultFormattersTable.getItems()) {
			defaultFormatterIdentifiers.add(spec.getIdentifier());
		}
		
		configuration.setAttribute(DEFAULT_FORMATTERS, defaultFormatterIdentifiers);
	}

	private void saveTemplateFactoryTypeTo(ILaunchConfigurationWorkingCopy configuration) {
		final TemplateFactoryTypeSpecificationFactory factory = new TemplateFactoryTypeSpecificationFactory();
		
		final String indexOfSelectedTemplateFactoryType = factory.findByIndex(templateFactoryTypeCombo.getSelectionIndex()).getIdentifier();
		configuration.setAttribute(TEMPLATE_FACTORY_TYPE, indexOfSelectedTemplateFactoryType);
	}

	@Override
	public String getName() {
		return "Generated Text";
	}
	
	@Override
	public Image getImage() {
		return EglPlugin.getDefault().createImage("icons/formatter.png");
	}
}
