/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.dt.editor;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.emc.composite.CompositeModel;
import org.eclipse.epsilon.emc.emf.EmfMetaModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.hutn.model.hutn.NsUri;


public class EmfMetamodelDirectory {

	private final IModel metamodels;
	
	public EmfMetamodelDirectory(String nsUri) throws EolModelLoadingException {
		metamodels = loadMetamodel(nsUri);
	}
	
	public EmfMetamodelDirectory(Collection<NsUri> nsUris) throws EolModelLoadingException {
		final Collection<IModel> mms = new LinkedList<>();
		
		for (NsUri nsUri : nsUris) {
			mms.add(loadMetamodel(nsUri.getValue()));
		}
		
		metamodels = new CompositeModel(mms);
	}
	
	private static EmfMetaModel loadMetamodel(String nsUri) throws EolModelLoadingException {
		final EmfMetaModel metamodel = new EmfMetaModel(nsUri);
		metamodel.load();
		return metamodel;
	}
	
	public Collection<String> classifierNames() {
		return namesOfTypedElements("EClassifier");
	}

	public Collection<String> packageNames() {
		return namesOfTypedElements("EPackage");
	}
	
	public Collection<String> featureNamesFor(String className) {
		final EClass eClass = eClass(className);
		
		if (eClass != null) {
			final Collection<String> featureNames = new LinkedList<>();

			for (EStructuralFeature feature : eClass.getEAllStructuralFeatures()) {
				featureNames.add(feature.getName());
			}
			
			return featureNames;
			
		} else {
			return Collections.emptyList();
		}
	}
	
	private EClass eClass(String name) {
		try {
			for (Object element : metamodels.getAllOfKind("EClass")) {
				if (element instanceof EClass) {
					final EClass eClass = (EClass)element;
					
					if (name.equals(eClass.getName())) {
						return eClass;
					}	
				}
			}
			
		} catch (EolModelElementTypeNotFoundException e) {}
		
		return null;
	}
	
	public Collection<String> concreteClassNames() {
		try {
			final Collection<String> names = new LinkedList<>();

			for (Object element : metamodels.getAllOfKind("EClass")) {
				if (element instanceof EClass && !((EClass)element).isAbstract()) {
					names.add(((ENamedElement)element).getName());
				}
			}
			
			return names;
			
		} catch (EolModelElementTypeNotFoundException e) {
			return Collections.emptyList();
		}
	}
	
	
	private Collection<String> namesOfTypedElements(String type) {
		try {
			final Collection<String> names = new LinkedList<>();

			for (Object element : metamodels.getAllOfKind(type)) {
				if (element instanceof EObject) {
					names.add(((ENamedElement)element).getName());
				}
			}
			
			return names;
			
		} catch (EolModelElementTypeNotFoundException e) {
			return Collections.emptyList();
		}
	}
}
