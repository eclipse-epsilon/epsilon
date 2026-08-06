/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.dt.launching.tabs;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;

public abstract class TableViewerEditingSupport extends EditingSupport {

	protected CellEditor cellEditor;
	
	public TableViewerEditingSupport(TableViewer viewer) {
		super(viewer);
	}

	protected abstract CellEditor createCellEditor(TableViewer viewer);
	
	@Override
	protected CellEditor getCellEditor(Object element) {
		if (cellEditor == null) {
			cellEditor = createCellEditor((TableViewer) getViewer());
		}
		return cellEditor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected abstract Object getValue(Object element);

	protected abstract void setValueImpl(Object element, Object value);
	
	@Override
	protected void setValue(Object element, Object value) {
		setValueImpl(element, value);
		((TableViewer) getViewer()).refresh();
	}

}
