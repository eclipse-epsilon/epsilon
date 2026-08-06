/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.launching.dialogs;

import org.eclipse.epsilon.common.dt.launching.extensions.ToolExtension;
import org.eclipse.epsilon.common.dt.util.ListContentProvider;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class ToolConfigurationDialog extends TitleAreaDialog implements ISelectionChangedListener {
	
	public ToolConfigurationDialog(Shell parentShell) {
		super(parentShell);
	}

	private StringProperties properties;
	private ComboViewer classCombo;
	private Label classLabel;
	private Text nameText;
	
	@Override
	protected void setShellStyle(int newShellStyle) {
		   super.setShellStyle(newShellStyle | SWT.RESIZE);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		
		Composite superControl = (Composite) super.createDialogArea(parent);
		
		this.setTitle("Configure Tool");
		this.setMessage("Configure the details of the tool");
		this.getShell().setText("Configure Tool");
		
		Composite control = new Composite(superControl, SWT.FILL);
		control.setLayout(new GridLayout(1,true));
		control.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Composite nameComposite = new Composite(control, SWT.FILL);
		nameComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout nameCompositeLayout = new GridLayout(2,false);
		nameComposite.setLayout(nameCompositeLayout);
		
		
		Label nameLabel = new Label(nameComposite, SWT.NONE);
		nameLabel.setText("Name:");
		nameLabel.setLayoutData(new GridData());
		
		nameText = new Text(nameComposite, SWT.BORDER);
		nameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		classLabel = new Label(nameComposite, SWT.NONE);
		classLabel.setText("Class:");
		classLabel.setLayoutData(new GridData());
		
		classCombo = new ComboViewer(nameComposite, SWT.BORDER | SWT.READ_ONLY);
		classCombo.getControl().setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		classCombo.setContentProvider(new ListContentProvider());
		classCombo.setInput(ToolExtension.getInstances());
		classCombo.addPostSelectionChangedListener(this);
		loadProperties();
		
		nameComposite.layout();
		nameComposite.pack();
		
		control.layout();
		control.pack();
		return control;
	}
	
	@Override
	protected void okPressed() {
		storeProperties();
		super.okPressed();
	}
	
	private void loadProperties(){
		if (properties == null) return;
		nameText.setText(properties.getProperty("name"));
		classCombo.setSelection(new StructuredSelection(ToolExtension.forClass(properties.getProperty("class"))));
	}
	
	private void storeProperties(){
		properties = new StringProperties();
		properties.put("name", nameText.getText());
		properties.put("class", ((ToolExtension)((StructuredSelection)classCombo.getSelection()).getFirstElement()).getClazz());
	}
	
	public StringProperties getProperties(){
		return properties;
	}
	
	public void setProperties(StringProperties properties){
		this.properties = properties;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ToolExtension extension = (ToolExtension)((StructuredSelection)classCombo.getSelection()).getFirstElement();
		if (extension == null) return;
		else nameText.setText(extension.getDefaultName());
	}
}
