/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed.modelink;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.dt.exeed.ExeedEditor;
import org.eclipse.epsilon.dt.exeed.UniqueArrayList;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IEditorPart;

public class LinkingModelSelectionListener implements ISelectionChangedListener{
	
	protected ExeedEditor editor;
	protected ModeLinkEditor modeLinkEditor;
	
	public LinkingModelSelectionListener(ExeedEditor editor, ModeLinkEditor modeLinkEditor) {
		this.editor = editor;
		this.modeLinkEditor = modeLinkEditor;
	}
	
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();
		
		UniqueArrayList<String> linkedObjects = new UniqueArrayList<>();
		
		Iterator<?> it = selection.iterator();
		while (it.hasNext()) {
			Object selected = it.next();
			if (!(selected instanceof EObject)) continue;
			
			EObject eObject = (EObject) selected;
			
			linkedObjects.addAll(getLinkedObjects(eObject));
			
		}
		
		IEditorPart[] innerEditors = modeLinkEditor.getInnerEditors();
		
		for (IEditorPart editor : innerEditors) {
			if (editor != this.editor) {
				selectEObjects(editor, linkedObjects);
			}
		}
		
	}
	
	protected List<String> getLinkedObjects(EObject eObject) {
		Iterator<EReference> it = eObject.eClass().getEAllReferences().iterator();
		UniqueArrayList<String> linkedObjects = new UniqueArrayList<>();
		
		while (it.hasNext()) {
			EReference ref = it.next();
			if (ref.isContainer() || ref.isContainment()) continue;
			Object value = eObject.eGet(ref);
			
			Collection<?> values = CollectionUtil.asCollection(value);
			
			Iterator<?> vit = values.iterator();
			
			while (vit.hasNext()) {
				Object next = vit.next();
				if (!(next instanceof EObject)) continue;
				linkedObjects.add(getInstanceId(next));
			}
			
		}
		
		return linkedObjects;
	
	}
	
	
	
	protected void selectEObjects(Object editor, List<String> ids) {
		
		if (!(editor instanceof IEditingDomainProvider && editor instanceof IViewerProvider)) return;

		if (editor == this.editor) return;
		
		TreeViewer viewer = (TreeViewer) ((IViewerProvider)editor).getViewer();
		viewer.setSelection(new StructuredSelection(Collections.EMPTY_LIST));
		
		UniqueArrayList<EObject> toSelect = new UniqueArrayList<>();
		
		//Resource resource = (Resource) ((IEditingDomainProvider)editor).getEditingDomain().getResourceSet().getResources().get(0);
		ResourceSet resourceSet =  ((IEditingDomainProvider)editor).getEditingDomain().getResourceSet();
		
		Iterator<?> it = resourceSet.getAllContents();
		while (it.hasNext()) {
			Object next = it.next();
			if (!(next instanceof EObject)) continue;
			if (ids.contains(getInstanceId(next))) {
				toSelect.add((EObject) next);
			}
		}
		
		viewer.setSelection(new StructuredSelection(toSelect));
	}
	
	protected String getInstanceId(Object instance) {
		EObject eObject = (EObject) instance;
		String instanceId = "";
		
		if (eObject.eResource() instanceof XMIResource){
			String id = ((XMIResource) eObject.eResource()).getID(eObject);
			if (id != null && id.trim().length() > 0) 
				instanceId = id;
		}
		
		if (eObject.eResource() instanceof XMLResource){
			instanceId = ((XMLResource) eObject.eResource()).getURIFragment(eObject);
		}
		
		if (instanceId.trim().length() > 0) {
			instanceId = eObject.eResource().getURI().toString() + instanceId;
		}

		return instanceId;
	}
}
