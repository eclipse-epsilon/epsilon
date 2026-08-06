/*******************************************************************************
 * Copyright (c) 2008-2012 The University of York, Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio García-Domínguez - code cleanup, add support for forcing the Exeed editor,
 *                                workarounds for the Properties view in Eclipse 3.x and
 *                                4.x
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed.modelink;

import java.lang.reflect.Method;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.epsilon.dt.exeed.ExeedEditor;
import org.eclipse.epsilon.dt.exeed.ExeedPlugin;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.part.MultiEditor;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;

/**
 * Two-way or three-way model editor. Can use both the default registered editors or the Exeed editors.
 */
public class ModeLinkEditor extends MultiEditor implements ISelectionChangedListener {

	//TODO: When a tab is closed prompt the user to save editor if dirty
	//TODO: Figure out how to add an editor at runtime

	private CTabFolder leftFolder;
	private CTabFolder rightFolder;
	private CTabFolder middleFolder;
	private ToolBar toolbar;

	// Eclipse 4.x: the ModeLink editor requires its own property sheet page.
	// The property sheet page delegates on any editor that implements
	// ISelectionProvider and IEditingDomainProvider, and whose editing domain
	// is an AdapterFactoryEditingDomain. It's stricter than in Eclipse 3.x,
	// but the Exeed editor and all EMF-based editors support it, so it should
	// be good enough. We cannot activate the inner editors in E4, due to some
	// changes in the codebase.
	//
	// We use lazy initialization on this field, as it is not required in
	// Eclipse 3.x.
	private PropertySheetPage properties;

	public ToolBar getToolbar() {
		return toolbar;
	}
	
	@Override
	public void createPartControl(Composite parent) {
		ViewForm container = new ViewForm(parent, SWT.NONE);
	
		SashForm form = new SashForm(container, SWT.HORIZONTAL);
		leftFolder = createFolder(form);
		if (getEditorInput().isThreeWay()) {
			middleFolder = createFolder(form);
		}
		rightFolder = createFolder(form);

		for (int i=0;i<getInnerEditors().length;i++) {
			createInnerPartControl(form, getInnerEditors()[i]);
		}

		initFolder(leftFolder);
		if (getEditorInput().isThreeWay()) {
			initFolder(middleFolder);
		}
		initFolder(rightFolder);

		toolbar = new ToolBar(container, SWT.FLAT | SWT.WRAP);
		ToolBarManager tbm = new ToolBarManager(toolbar);
		tbm.update(true);
		container.setTopCenter(toolbar);
		container.setContent(form);
		toolbar.setVisible(true);

		/*
		 * WORKAROUND for Eclipse 3.x: this is required in order to make the control of each
		 * inner editor's site's pane visible and with a non-zero size. Otherwise,
		 * org.eclipse.ui.internal.PartService#firePartActivated will not notify
		 * its listeners properly, since deferControl will return a non-null value.
		 * If the listeners are not properly notified, the Properties view will not
		 * work properly in Eclipse 3.x with the ModeLink editor.
		 *
		 * Also, we need to use reflection, as getPane() is no longer available in PartSite
		 * in Eclipse 4.x. Eclipse 4.x requires a different set of workarounds, which are also
		 * mentioned in the rest of this file.
		 */
		for (IEditorPart innerEditor : getInnerEditors()) {
			final IWorkbenchPartSite site = innerEditor.getSite();
			try {
				final Method getPane = site.getClass().getMethod("getPane");
				final Object pane = getPane.invoke(site);
				final Method getControl = pane.getClass().getMethod("getControl");
				final Control paneControl = (Control)getControl.invoke(pane);
				paneControl.setSize(1, 1);
				paneControl.setVisible(true);
			}
			catch (NoSuchMethodException e) {
				// workaround is unavailable in Eclipse 4.x
			}
			catch (Exception e) {
				ExeedPlugin.getDefault().getLog().log(new Status(
					IStatus.ERROR, ExeedPlugin.PLUGIN_ID,
					"There was an error while setting up a workaround for the " +
					"Properties view in Eclipse 3.x", e));
			}
		}
	}

	@Override
	public ModeLinkEditorInput getEditorInput() {
		return (ModeLinkEditorInput) super.getEditorInput();
	}

	@Override
	public Composite createInnerPartControl(Composite parent, final IEditorPart e) {
    	CTabFolder folder;
    	ModeLinkInnerEditorInput input = (ModeLinkInnerEditorInput) e.getEditorInput();
    	if (input.getPosition() == ModelPosition.LEFT) {
    		folder = leftFolder;
    	} else if (input.getPosition() == ModelPosition.RIGHT){
    		folder = rightFolder;
    	}
    	else {
    		folder = middleFolder;
    	}
    	
    	return createEditorTab(folder, e);
    }
	
	@Override
	public String getPartName() {
		return (getEditorInput()).getOriginalInput().getFile().getName();
	}

	/*
     * @see IWorkbenchPart#setFocus()
     */
    @Override
	public void setFocus() {
        try {
        	super.setFocus();
        }
        catch (Exception ex) {}
    }

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> key) {
		if (IPropertySheetPage.class.equals(key)) {
			// WORKAROUND for Eclipse 4.x
			if (properties == null) {
				properties = new PropertySheetPage();
			}
			return (T) properties;
		}
		return super.getAdapter(key);
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		// WORKAROUND for Eclipse 4.x
		if (properties != null && event.getSource() instanceof IWorkbenchPart) {
			properties.selectionChanged((IWorkbenchPart)event.getSource(), event.getSelection());
		}
	}

	@Override
	protected void drawGradient(IEditorPart innerEditor, Gradient g) {
		// do nothing
	}

	private void addSelectionPropagationSupport(ExeedEditor editor, ModeLinkEditor modeLinkEditor) {
		((TreeViewer) editor.getViewer()).addSelectionChangedListener(
				new LinkingModelSelectionListener(editor, modeLinkEditor)
		);
	}

	private Composite createEditorTab(CTabFolder folder, final IEditorPart editor) {
		Composite content = new Composite(folder, SWT.NONE);
	    final InnerEditorTabItem item = new InnerEditorTabItem(folder, SWT.NONE, editor);
	    item.setControl(content);
	    editor.addPropertyListener(new IPropertyListener() {
	    	public void propertyChanged(Object source, int propId) {
	    		if (propId == IEditorPart.PROP_DIRTY) {
	    			ModeLinkEditor.this.firePropertyChange(IEditorPart.PROP_DIRTY);
	    			String text = editor.getEditorInput().getName();
	    			if (editor.isDirty()) {
						text = "*" + text;
					}
					item.setText(text);
				}
			}
	    });
	
	    item.setText(editor.getEditorInput().getName());
	    content.setLayout(new FillLayout());
	    editor.createPartControl(content);
	    
	    if (folder == middleFolder || (!getEditorInput().isThreeWay() && folder == rightFolder)) {
	    	if (editor instanceof ExeedEditor) {
	    		addSelectionPropagationSupport((ExeedEditor)editor, this);
	    	}
	    }
	    
	    content.addListener(SWT.Activate, new Listener() {
	        public void handleEvent(Event event) {
	            if (event.type == SWT.Activate) {
	            	removeListenerFromActiveEditor();
	            	if (properties != null && editor instanceof IEditingDomainProvider) {
	        			// WORKAROUND for Eclipse 4.x for the Properties view
	            		final IEditingDomainProvider iedp = (IEditingDomainProvider)editor;
	            		if (iedp.getEditingDomain() instanceof AdapterFactoryEditingDomain) {
	            			final AdapterFactoryEditingDomain afed = (AdapterFactoryEditingDomain)iedp.getEditingDomain();
	            			final AdapterFactory af = afed.getAdapterFactory();
            				properties.setPropertySourceProvider(new AdapterFactoryContentProvider(af));
	            		}
	            	}
					activateEditor(editor);
					addListenerToActiveEditor();

					// WORKAROUND for Eclipse 4.x for the "New Child" and "New Sibling" popup menu entries
					editor.getEditorSite().getActionBarContributor().setActiveEditor(editor);
				}
	        }

			private void addListenerToActiveEditor() {
				// WORKAROUND for Eclipse 4.x for the Properties view
				if (getActiveEditor() instanceof ISelectionProvider) {
					final ISelectionProvider oldSP = (ISelectionProvider)getActiveEditor();
					oldSP.addSelectionChangedListener(ModeLinkEditor.this);
				}
			}

			private void removeListenerFromActiveEditor() {
				// WORKAROUND for Eclipse 4.x for the Properties view
				if (getActiveEditor() instanceof ISelectionProvider) {
					final ISelectionProvider oldSP = (ISelectionProvider)getActiveEditor();
					oldSP.removeSelectionChangedListener(ModeLinkEditor.this);
				}
			}
	    });
	    
	    return content;
	}

	private CTabFolder createFolder(Composite parent) {
		CTabFolder folder = new CTabFolder(parent, SWT.FLAT);
		folder.setTabPosition(SWT.BOTTOM);
		folder.setSimple(true);
	
		folder.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}
	
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item instanceof CTabItem) {
					((CTabItem) e.item).getControl().setFocus();
				}
			}
		});
	
		return folder;
	}

	private void initFolder(CTabFolder folder) {
		if (folder.getItemCount() > 0) {
			folder.setSelection(0);
		}
	}
}
