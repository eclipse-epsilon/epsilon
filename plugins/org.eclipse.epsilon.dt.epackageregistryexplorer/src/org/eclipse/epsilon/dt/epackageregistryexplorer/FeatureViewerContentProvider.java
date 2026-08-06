/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
******************************************************************************/

package org.eclipse.epsilon.dt.epackageregistryexplorer;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class FeatureViewerContentProvider implements ITreeContentProvider {
	
	protected PackageRegistryExplorerView view;
	
	public FeatureViewerContentProvider(PackageRegistryExplorerView view) {
		this.view = view;
	}
	
	protected Collection<EStructuralFeature> getFeatures(EClass eClass) {
		Collection<EStructuralFeature> features;
		
		if (view.isShowInheritedFeatures()) {
			features = eClass.getEAllStructuralFeatures();
		}
		else {
			features = eClass.getEStructuralFeatures();
		}
		
		if (view.isShowDerivedFeatures()) {
			return features;
		}
		else {
			ArrayList<EStructuralFeature> filtered = new ArrayList<>();
			for (EStructuralFeature sf : features) {
				if (!sf.isDerived()) {
					filtered.add(sf);
				}
			}
			return filtered;
		}
		
	}
	
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof EClass) {
			ArrayList<Object> eStructuralFeatures = new ArrayList<>();
			eStructuralFeatures.addAll(getFeatures((EClass) inputElement));
			eStructuralFeatures.addAll(DecoratorSupport.getHooks((EClass)inputElement, view.getEPackages(), view.isShowInheritedFeatures()));
			if (view.isShowOperations()) {
				eStructuralFeatures.addAll(((EClass)inputElement).getEAllOperations());
			}
			return eStructuralFeatures.toArray();
		}
		else if (inputElement instanceof EEnum) {
			return ((EEnum) inputElement).getELiterals().toArray();
		}
		return new Object[0];
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}
}
 
