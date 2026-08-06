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

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;

class ListContentProvider<T> implements IStructuredContentProvider, ListListener {
	
	private TableViewer viewer;
	private ListModel<T> model;
	
	@SuppressWarnings("unchecked")
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = (TableViewer) viewer;
		if (oldInput != null) ((ListModel<T>)oldInput).removeChangeListener(this);
		if (newInput != null) {
			model = ((ListModel<T>)newInput);
			model.addChangeListener(this);
		}
	}
	
	@Override
	public void dispose() {
		if (model != null) model.removeChangeListener(this);
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return model.getItems().toArray();
	}
	
	@Override
	public void changed(ChangeType changeType, Object item, int position) {
		viewer.refresh();
	}
}
