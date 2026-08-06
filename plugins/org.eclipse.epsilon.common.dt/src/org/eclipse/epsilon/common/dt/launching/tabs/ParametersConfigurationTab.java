/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.launching.tabs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.epsilon.common.dt.EpsilonCommonsPlugin;
import org.eclipse.epsilon.common.dt.util.ListContentProvider;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;

public class ParametersConfigurationTab extends AbstractLaunchConfigurationTab{
	
	protected List<ParameterConfiguration> parameters = new ArrayList<>();
	private TableViewer parametersViewer;
	
	private final List<Button> modelControls = new LinkedList<>();

	@Override
	public void createControl(Composite parent) {
		
		GridLayout parentLayout = new GridLayout();
		parent.setLayout(parentLayout);

		Composite control = new Composite(parent, SWT.FILL);
		setControl(control);
		GridLayout controlLayout = new GridLayout(1, false);
		control.setLayout(controlLayout);
		
		createParametersViewer(control);
		
		createBottomControl(control);
		
		control.pack();
		control.layout();
		
		canSave();
	}

	private void createParametersViewer(Composite parent) {
		Composite topControl = new Composite(parent, SWT.FILL);
		GridLayout topControlLayout = new GridLayout(2, false);
		topControl.setLayout(topControlLayout);
		
		GridData topControlData = new GridData(GridData.FILL_BOTH);
		topControl.setLayoutData(topControlData);

		parametersViewer = new TableViewer(topControl, SWT.BORDER);
		createTableColumns();		
		parametersViewer.getTable().setHeaderVisible(true);
		parametersViewer.getTable().setLinesVisible(true);
		
		parametersViewer.setContentProvider(new ListContentProvider());
		parametersViewer.setLabelProvider(new ParameterLabelProvider());
		parametersViewer.setInput(parameters);
		parametersViewer.getTable().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {}
			
			@Override
			public void mouseDown(MouseEvent e) {}
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				TableItem item = parametersViewer.getTable().getItem(new Point(e.x, e.y));
				if (item == null) {
					addNewParameter();
				}
			}
		});
		
		GridData buttonsData = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);

		GridData viewerData = new GridData(GridData.FILL_BOTH);
		parametersViewer.getControl().setLayoutData(viewerData);
		parametersViewer.getControl().setFocus();

		Composite buttons = new Composite(topControl, SWT.FILL | SWT.TOP);
		buttons.setLayoutData(buttonsData);

		GridLayout buttonsLayout = new GridLayout(1, true);
		buttons.setLayout(buttonsLayout);

		createButton(buttons, "Add").addListener(SWT.Selection, new AddParameterListener());
		createButton(buttons, "Remove").addListener(SWT.Selection, new RemoveParameterListener());
		createButton(buttons, "Duplicate").addListener(SWT.Selection, new DuplicateParameterListener());
	}
	
	/**
	 *  Subclasses may implement this method to populate the bottom portion
	 *  of the tab with further controls
	 */
	protected void createBottomControl(Composite parent) {}
	
	/**
	 *  The given listener will be notified of subsequent selections made on
	 *  the buttons used to manage model choices.
	 */
	protected void addListenerToButtonControls(SelectionListener listener) {
		for (Button control : modelControls) {
			control.addSelectionListener(listener);
		}
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		// TODO Auto-generated method stub
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			parameters.clear();
			List<String> parametersList = configuration.getAttribute("parameters", new ArrayList<String>());
			for (String parameterString : parametersList) {
				parameters.add(new ParameterConfiguration(new StringProperties(parameterString)));
			}
			parametersViewer.setInput(parameters);
			parametersViewer.refresh(true);
			canSave();
			updateLaunchConfigurationDialog();
		} catch (CoreException e) {
			LogUtil.log(e);
		}
		
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		
		
		List<String> parametersList = new ArrayList<>();
		for (ParameterConfiguration parameter : parameters) {
			parametersList.add(parameter.toStringProperties().toString());
		}
		configuration.setAttribute("parameters", parametersList);
	}

	@Override
	public String getName() {
		return "Parameters";
	}
	
	@Override
	public Image getImage() {
		return EpsilonCommonsPlugin.getDefault().createImage("icons/parameter.gif");
	}
	
	@Override
	public boolean canSave(){
		setErrorMessage(null);
		return true;
	}
	
	private void createTableColumns() {
		TableViewerColumn column = new TableViewerColumn(parametersViewer, SWT.NONE);
		column.getColumn().setWidth(100);
		column.getColumn().setText("Name");
		column.setEditingSupport(new TableViewerEditingSupport(parametersViewer) {
			
			@Override
			protected void setValueImpl(Object element, Object value) {
				((ParameterConfiguration) element).setName(value + "");
				canSave();
				updateLaunchConfigurationDialog();
			}
			
			@Override
			protected Object getValue(Object element) {
				return ((ParameterConfiguration) element).getName();
			}
			
			@Override
			protected CellEditor createCellEditor(TableViewer viewer) {
				return new TextCellEditor(viewer.getTable());
			}
			
		});
		
		column = new TableViewerColumn(parametersViewer, SWT.NONE);
		column.getColumn().setWidth(100);
		column.getColumn().setText("Type");
		column.setEditingSupport(new TableViewerEditingSupport(parametersViewer) {
			
			@Override
			protected void setValueImpl(Object element, Object value) {
				((ParameterConfiguration) element).setType(value + "");
				canSave();
				updateLaunchConfigurationDialog();
			}
			
			@Override
			protected Object getValue(Object element) {
				return ((ParameterConfiguration) element).getType();
			}
			
			@Override
			protected CellEditor createCellEditor(TableViewer viewer) {	
				ComboBoxViewerCellEditor editor = new ComboBoxViewerCellEditor(viewer.getTable());
				editor.getViewer().add(new Object[] {"String", "Integer", "Real", "Boolean"});
				return editor;
			}
					
		});
		
		column = new TableViewerColumn(parametersViewer, SWT.NONE);
		column.getColumn().setWidth(200);
		column.getColumn().setText("Value");
		column.setEditingSupport(new TableViewerEditingSupport(parametersViewer) {
			
			@Override
			protected void setValueImpl(Object element, Object value) {
				((ParameterConfiguration) element).setValue(value + "");
				canSave();
				updateLaunchConfigurationDialog();
			}
			
			@Override
			protected Object getValue(Object element) {
				return ((ParameterConfiguration) element).getValue();
			}
			
			@Override
			protected CellEditor createCellEditor(TableViewer viewer) {
				return new TextCellEditor(viewer.getTable());
			}
			
		});
	}
	
	private Button createButton(Composite parent, String text) {
		Button button = new Button(parent, SWT.NONE);
		button.setText(text);
		button.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		modelControls.add(button);
		
		return button;
	}
	
	public void addNewParameter() {
		ParameterConfiguration parameter = new ParameterConfiguration();
		String parameterName = parameter.getName();
		
		boolean uniquename = false; 
		for (int i = 1; !uniquename; i++) {
			parameter.setName("p" + i);
			uniquename = true;
			for (ParameterConfiguration existing : parameters) {
				if (existing.getName().equals(parameterName)) {
					uniquename = false;
				}
			}
		}
		parameter.setType("String");
		parameters.add(parameter);
		parametersViewer.refresh(true);
		canSave();
		updateLaunchConfigurationDialog();
	}
	
	class AddParameterListener implements Listener {
		@Override
		public void handleEvent(Event event) {	
			addNewParameter();
		}
	}
	
	class RemoveParameterListener implements Listener{
		@Override
		public void handleEvent(Event event) {
			IStructuredSelection selection = (IStructuredSelection) parametersViewer.getSelection();
			if (selection.getFirstElement() == null) return;
			int index = parameters.indexOf(selection.getFirstElement());
			parameters.remove(index);
			parametersViewer.refresh(true);
			canSave();
			updateLaunchConfigurationDialog();
		}
	}

	class DuplicateParameterListener implements Listener{
		@Override
		public void handleEvent(Event event) {
			IStructuredSelection selection = (IStructuredSelection) parametersViewer.getSelection();
			if (selection.getFirstElement() == null) return;
			int index = parameters.indexOf(selection.getFirstElement());
			ParameterConfiguration parameter = (ParameterConfiguration) selection.getFirstElement();
			parameters.add(index, new ParameterConfiguration(parameter.getName(), parameter.getType(), parameter.getValue()));
			parametersViewer.refresh(true);
			canSave();
			updateLaunchConfigurationDialog();
		}
	}
	
	class ParameterLabelProvider extends LabelProvider implements ITableLabelProvider {

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			ParameterConfiguration parameter = (ParameterConfiguration) element;
			switch(columnIndex) {
				case 0: return parameter.getName();
				case 1: return parameter.getType() + "";
				case 2: return parameter.getValue();
			}
			return "";
		}

	}
	
}
