/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.editor.outline;

import java.util.List;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public abstract class ModuleContentProvider implements ITreeContentProvider{
	
	@Override
	public Object[] getChildren(Object parentElement) {
		return getVisibleChildren((ModuleElement) parentElement).toArray();
	}

	@Override
	public Object getParent(Object element) {
		return ((ModuleElement) element).getParent();
	}

	@Override
	public boolean hasChildren(Object element) {
		return (element instanceof ModuleElement && getVisibleChildren((ModuleElement)element).size() > 0) ;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getVisibleChildren((ModuleElement) inputElement).toArray();
	}

	@Override
	public void dispose() {}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
	}
	
	public abstract List<ModuleElement> getVisibleChildren(ModuleElement moduleElement);
	
	public abstract ModuleElement getFocusedModuleElement(ModuleElement moduleElement);
	
}
