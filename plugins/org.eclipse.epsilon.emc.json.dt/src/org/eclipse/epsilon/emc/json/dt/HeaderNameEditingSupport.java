package org.eclipse.epsilon.emc.json.dt;

import org.eclipse.epsilon.emc.json.dt.JsonModelConfigurationDialog.Header;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

class HeaderNameEditingSupport extends EditingSupport {
	private final CellEditor editor; 

	public HeaderNameEditingSupport(TableViewer viewer) {
		super(viewer);
		this.editor = new TextCellEditor(viewer.getTable());
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return editor;
	}

	@Override
	protected Object getValue(Object element) {
		return ((Header) element).name;
	}

	@Override
	protected void setValue(Object element, Object newValue) {
		((Header) element).name = String.valueOf(newValue);
		getViewer().update(element, null);
	}

}