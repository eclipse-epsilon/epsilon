/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.views;

import java.net.URI;

import org.eclipse.epsilon.egl.traceability.Template;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

public class TemplateView extends ViewPart implements CurrentTemplateObserver {

	private TreeViewer treeViewer;
	private Display display;
	
	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		
		display = site.getShell().getDisplay();
		CurrentTemplate.getInstance().addObserver(this);
	}

	@Override
	public void createPartControl(Composite parent) {
		treeViewer = new TreeViewer(parent);
		treeViewer.setContentProvider(new TemplateTreeContentProvider());
		treeViewer.setLabelProvider(new TemplateLabelProvider());
		
		treeViewer.addDoubleClickListener(new TemplateViewDoubleClickListener());
		
		treeViewer.expandAll();
		treeViewer.refresh();
	}

	public void templateChanged(final CurrentTemplateObserverEvent e) {
		if (display!=null) display.syncExec(new Runnable() {
			
			public void run() {
				treeViewer.setInput(installRoot(e.getNewTemplate()));
				treeViewer.expandAll();
				treeViewer.refresh();
			}
		});
	}
	
	@Override
	public void setFocus() {}
	
	private Template installRoot(Template template) {
		final Template root = new Template((URI)null);
		root.add(template);
		return root;
	}
}
