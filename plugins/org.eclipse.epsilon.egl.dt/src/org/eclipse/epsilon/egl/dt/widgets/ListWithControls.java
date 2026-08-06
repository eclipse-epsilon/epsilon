/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.widgets;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

/**
 * An SWT widget that provides a means for displaying and editing
 * a list of items. This implementation provides controls for adding, 
 * deleting, and re-ordering items in the the list.
 */
public class ListWithControls<T> extends Composite {

	public static interface ItemFactory<T> {
		public T createItem();
	}
	
	private final Table list; 
	private final TableViewer viewer;
	private final Composite buttons;
	
	private final ListModel<T> model = new ListModel<>();
	private ItemFactory<T> itemFactory;
	
	public ListWithControls(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new GridLayout(2, false));
		setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		list = new Table(this, SWT.BORDER | SWT.MULTI);
		viewer = new TableViewer(list);
		viewer.setContentProvider(new ListContentProvider<T>());
		viewer.setInput(model);
		
		final GridData tableData = new GridData();
		tableData.horizontalAlignment = SWT.FILL;
		tableData.verticalAlignment = SWT.FILL;
		tableData.grabExcessHorizontalSpace = true;
	    list.setLayoutData(tableData);
	    	    
	    buttons = new Composite(this, SWT.NONE);
	    buttons.setLayout(new GridLayout(1, false));
	    buttons.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
	    
		createButton(buttons, "Add...", new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (itemFactory != null) {
					model.add(itemFactory.createItem());
					list.deselectAll();
					list.select(list.getItemCount()-1);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		
	    createButton(buttons, "Remove", new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (list.getSelectionCount() == 1) {
					final int selectionIndex = list.getSelectionIndex();
					model.remove(selectionIndex);
					list.setSelection(selectionIndex);
				} else {
					model.remove(list.getSelectionIndices());
					list.deselectAll();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
	    	
	    });
	    
	    createButton(buttons, "Up", new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				model.moveUp(list.getSelectionIndices());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
	    	
	    }, createLayoutDataWithVerticalSpacing());
	    
	    createButton(buttons, "Down", new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				model.moveDown(list.getSelectionIndices());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}
	
	private GridData createLayoutDataWithVerticalSpacing() {
		final GridData layoutData = new GridData();
		layoutData.verticalIndent = 25;
		return layoutData;
	}

	private void createButton(Composite parent, String text, SelectionListener listener) {
		createButton(parent, text, listener, new GridData());
	}
	
	private void createButton(Composite parent, String text, SelectionListener listener, GridData layoutData) {
		final Button button = new Button(parent, SWT.NONE);
		button.setText(text);
		
		layoutData.widthHint = 100;
		layoutData.horizontalAlignment = SWT.FILL;
		button.setLayoutData(layoutData);
		
		if (listener != null)
			button.addSelectionListener(listener);
	}

	@SuppressWarnings("unchecked")
	public void setItems(T... items) {
		setItems(Arrays.asList(items));
	}
	
	public void setItems(Collection<T> items) {
		model.replaceAllWith(items);
		viewer.refresh();
	}
	
	public Collection<T> getItems() {
		return Collections.unmodifiableCollection(model.getItems());
	}
	
	public void setLabelProvider(ITableLabelProvider labelProvider) {
		viewer.setLabelProvider(labelProvider);
	}
	
	public void setItemFactory(ItemFactory<T> itemFactory) {
		this.itemFactory = itemFactory;
	}
	
	@Override
	public void setBackground(Color color) {
		super.setBackground(color);
		buttons.setBackground(color);
	}

	public void addSelectionListener(SelectionListener selectionListener) {
		list.addSelectionListener(selectionListener);
	}
	
	public void addModelListener(ListListener modelListener) {
		model.addChangeListener(modelListener);
	}
}
