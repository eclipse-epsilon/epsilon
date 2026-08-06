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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IEditorPart;

public class TabbedEditor<T extends IEditorPart> extends Composite {

	private final CTabFolder folder;
	private final List<T> innerEditors;
	
	public TabbedEditor(Composite parent, Collection<T> innerEditors) {
		super(parent, SWT.NONE);
		this.innerEditors = new LinkedList<>(innerEditors);
		
		this.setLayout(new FillLayout());
		
		folder = createFolder();
		populateFolderWithEditors(innerEditors);
		
		if (innerEditors.size() > 0) switchTo(innerEditors.iterator().next());
	}
	
	private CTabFolder createFolder(String... tabItemNames) {
		final CTabFolder folder = new CTabFolder(this, SWT.BORDER);
		
		final ToolBar t = new ToolBar(folder, SWT.HORIZONTAL | SWT.FLAT);
		final ToolBarManager tbm = new ToolBarManager(t);

		tbm.update(true);
		folder.setTabPosition(SWT.BOTTOM);
		folder.setSimple(true);
		t.setVisible(true);
		
		folder.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item instanceof CTabItem) {
					((CTabItem) e.item).getControl().setFocus();
				}
			}
			
		});
		
		return folder;
	}
	
	private void populateFolderWithEditors(Collection<T> innerEditors) {
		for (T innerEditor : innerEditors) {
			populateFolderWithEditor(innerEditor);
		}
	}
	
	private void populateFolderWithEditor(final T innerEditor) {
		final CTabItem item = new CTabItem(folder, SWT.NONE);
		item.setText(innerEditor.getEditorInput().getName());
		
		Composite content = new Composite(folder, SWT.NONE);
		content.setLayout(new FillLayout());
		innerEditor.createPartControl(content);
		
		item.setControl(content);
		
//		parent.addListener(SWT.Activate, new Listener() {
//            public void handleEvent(Event event) {
//                if (event.type == SWT.Activate) {
//					activateEditor(innerEditor);
//				}
//            }
//        });
	}
	
	public void addMouseListenerToEditors(MouseListener listener) {
		for (T innerEditor : innerEditors) {
			if (innerEditor instanceof IViewerProvider) {
				((IViewerProvider) innerEditor).getViewer().getControl().addMouseListener(listener);
			}
		}
	}
	
	public T getActiveEditor() {
		return innerEditors.get(folder.getSelectionIndex());
	}

	public void switchTo(T innerEditor) {
		if (indexOf(innerEditor) >= 0) {
			folder.setSelection(indexOf(innerEditor));
		}
	}

	private int indexOf(T innerEditor) {
		return innerEditors.indexOf(innerEditor);
	}
}
