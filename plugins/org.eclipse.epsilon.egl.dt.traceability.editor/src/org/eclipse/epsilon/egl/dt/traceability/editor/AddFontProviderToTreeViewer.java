/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.traceability.editor;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeColumn;

public class AddFontProviderToTreeViewer {

	private final TreeViewer viewer;
	
	public AddFontProviderToTreeViewer(TreeViewer viewer) {
		this.viewer = viewer;
	}

	public void add(ITableFontProvider fontProvider) {
		// Force the viewer to use the ITableFontProvider interface. 
		// When no columns are used, the TreeViewer is not rendered as a table and the ITableFontProvider is ignored.
		// A single column allows us to mimic a normal tree viewer, but also use ITableFontProvider.
		new TreeColumn(viewer.getTree(),SWT.NONE).pack();
		
		// Adapt the existing ILabelProvider to an ITableLabelProvider so that it works with TreeColumn
		// and attach the custom font provider
		final LabelProviderAdapter tabelLabelProvider = new LabelProviderAdapter((ILabelProvider)viewer.getLabelProvider(), fontProvider);
		
		viewer.setLabelProvider(tabelLabelProvider);
	}
	
	private class LabelProviderAdapter implements ITableLabelProvider, ITableFontProvider {

		private final ILabelProvider labelProvider;
		private final ITableFontProvider fontProvider;
		
		public LabelProviderAdapter(ILabelProvider decorated, ITableFontProvider fontProvider) {
			this.labelProvider = decorated;
			this.fontProvider = fontProvider;
		}

		@Override
		public void addListener(ILabelProviderListener listener) {
			labelProvider.addListener(listener);
		}

		@Override
		public void dispose() {
			labelProvider.dispose();
		}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			return labelProvider.isLabelProperty(element, property);
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			labelProvider.removeListener(listener);
		}

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return labelProvider.getImage(element);
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			return labelProvider.getText(element);
		}
		
		@Override
		public Font getFont(Object element, int columnIndex) {	
			return fontProvider.getFont(element, columnIndex);
		}
	}
}
