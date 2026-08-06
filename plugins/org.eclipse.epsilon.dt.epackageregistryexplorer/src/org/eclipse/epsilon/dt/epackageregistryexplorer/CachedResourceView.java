/*******************************************************************************
 * Copyright (c) 2014-2018 University of York, Aston University.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitris Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - double click to see acquired/released stack traces
******************************************************************************/
package org.eclipse.epsilon.dt.epackageregistryexplorer;

import org.eclipse.epsilon.emc.emf.CachedResourceSet;
import org.eclipse.epsilon.emc.emf.CachedResourceSet.Cache;
import org.eclipse.epsilon.emc.emf.CachedResourceSet.Cache.CacheItem;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class CachedResourceView extends ViewPart {

	protected TableViewer cacheViewer = null;

	@Override
	public void createPartControl(Composite parent) {
		cacheViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		cacheViewer.setContentProvider(new CachedResourceViewerContentProvider());
		cacheViewer.setLabelProvider(new CachedResoureViewerLabelProvider());
		cacheViewer.setComparator(new ViewerComparator((o1, o2) -> o1.compareTo(o2)));
		cacheViewer.setInput(CachedResourceSet.getCache());
		cacheViewer.addDoubleClickListener(event -> {
			IStructuredSelection sel = (IStructuredSelection) event.getSelection();
			CacheItem cItem = (CacheItem) sel.getFirstElement();
			new CacheItemDialog(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					cItem).open();
		});

		TableColumn column = new TableColumn(cacheViewer.getTable(), SWT.FULL_SELECTION);
		column.setText("Resource");
		column.setWidth(300);

		column = new TableColumn(cacheViewer.getTable(), SWT.FULL_SELECTION);
		column.setText("Garbage-collected");
		column.setWidth(120);

		cacheViewer.getTable().setHeaderVisible(true);
		cacheViewer.getTable().setLinesVisible(true);

		IActionBars bars = getViewSite().getActionBars();
		bars.getToolBarManager().add(new RefreshAction());
		bars.getToolBarManager().add(new ClearAction());
	}

	@Override
	public void setFocus() {

	}

	class RefreshAction extends Action {

		public RefreshAction() {
			this.setText("Refresh");
			this.setImageDescriptor(Activator.getDefault().getImageDescriptor("icons/refresh.gif"));
		}

		@Override
		public void run() {
			cacheViewer.refresh();
		}
	}

	class ClearAction extends Action {

		public ClearAction() {
			this.setText("Clear cache");
			this.setImageDescriptor(Activator.getDefault().getImageDescriptor("icons/clear.gif"));
		}

		@Override
		public void run() {
			CachedResourceSet.getCache().clear();
			cacheViewer.refresh();
		}
	}

	public class CachedResoureViewerLabelProvider extends LabelProvider implements ITableLabelProvider {

		protected Image image = Activator.getDefault().getImageDescriptor("icons/emfmodel.gif").createImage();

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			if (columnIndex == 0)
				return image;
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			if (columnIndex == 0)
				return ((CacheItem) element).getUri().toString();
			else
				return (((CacheItem) element).getResource() == null) + "";
		}

	}

	public class CachedResourceViewerContentProvider implements IStructuredContentProvider {

		@Override
		public void dispose() {

		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		}

		@Override
		public Object[] getElements(Object inputElement) {
			return CachedResourceSet.getCache().getItems().toArray(new Cache.CacheItem[] {});
		}

	}

}
