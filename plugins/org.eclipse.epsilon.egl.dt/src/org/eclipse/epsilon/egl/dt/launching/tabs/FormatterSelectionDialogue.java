/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.launching.tabs;

import org.eclipse.epsilon.common.dt.util.ListContentProvider;
import org.eclipse.epsilon.egl.dt.EglPlugin;
import org.eclipse.epsilon.egl.dt.extensions.formatter.FormatterSpecification;
import org.eclipse.epsilon.egl.dt.extensions.formatter.FormatterSpecificationFactory;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class FormatterSelectionDialogue extends TitleAreaDialog implements ISelectionChangedListener {
	
	private TableViewer formatterViewer;
	private FormatterSpecification selectedFormatter;
	
	public FormatterSelectionDialogue(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected void setShellStyle(int newShellStyle) {
	   super.setShellStyle(newShellStyle | SWT.RESIZE);
	}
	
	@Override
	protected Rectangle getConstrainedShellBounds(Rectangle preferredSize) {
		preferredSize.height = 350;
		return super.getConstrainedShellBounds(preferredSize);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		
		final Composite dialogArea = (Composite) super.createDialogArea(parent);
		final GridData dialogAreaData = new GridData(GridData.FILL_BOTH);
		dialogArea.setLayoutData(dialogAreaData);
		
		this.setTitle("Select a formatter");
		this.setMessage("Select the formatter to add");
		this.getShell().setText("Select a formatter");
		this.getShell().setImage(EglPlugin.getDefault().createImage("icons/formatter.png"));
		
		
		final Composite control = new Composite(dialogArea, SWT.NONE);
		final GridLayout controlLayout = new GridLayout(1, false);
		control.setLayout(controlLayout);
		
		final GridData controlData = new GridData(GridData.FILL_BOTH);
		control.setLayoutData(controlData);
		
		formatterViewer = new TableViewer(control, SWT.BORDER);
		
		
		final GridData viewerData = new GridData(GridData.FILL_BOTH);
		formatterViewer.getControl().setLayoutData(viewerData);
		
		
		formatterViewer.setContentProvider(new ListContentProvider());
		formatterViewer.setInput(new FormatterSpecificationFactory().loadAllFromExtensionPoints());
		formatterViewer.addSelectionChangedListener(this);
		formatterViewer.setLabelProvider(new FormatterSpecificationLabelProvider());
		
		
		formatterViewer.getControl().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				final Object firstInSelection = ((IStructuredSelection)formatterViewer.getSelection()).getFirstElement();
				FormatterSelectionDialogue.this.selectedFormatter = (FormatterSpecification)firstInSelection;
				if (FormatterSelectionDialogue.this.selectedFormatter != null) {
					FormatterSelectionDialogue.this.close();
				}
			}
		});
		
		formatterViewer.refresh();
		
		return control;
	}

	public void selectionChanged(SelectionChangedEvent event) {
		this.selectedFormatter = (FormatterSpecification)((IStructuredSelection)event.getSelection()).getFirstElement();
	}
	
	public FormatterSpecification getSelectedFormatter(){
		return selectedFormatter;
	}
}
