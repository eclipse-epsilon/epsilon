/*******************************************************************************
 * Copyright (c) 2008 The University of York.
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
package org.eclipse.epsilon.hutn.xmi.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;

public abstract class EmfUtil {

	private EmfUtil() {}
	
	public static Object createFromString(EDataType type, String value) {
		try {
			return type.getEPackage().getEFactoryInstance().createFromString(type, value);
		
		} catch (NumberFormatException e) {
			return value;
		
		} catch (IllegalArgumentException e) {
			return value;
		}
	}
	
	public static boolean isContainmentReference(EStructuralFeature feature) {
		return feature != null && feature instanceof EReference && ((EReference)feature).isContainment();
	}
	
		
	/* Copied from EMC EmfUtil */
	public static List<EClass> getAllEClassesFromSameMetamodelAs(EModelElement metamodelElement) {
		return getAllModelElementsOfType(metamodelElement, EClass.class);
	}
	
	public static <T extends EObject> List<T> getAllModelElementsOfType(EObject modelElement, Class<T> type) {
		return getAllModelElementsOfType(modelElement.eResource(), type);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends EObject> List<T> getAllModelElementsOfType(Resource resource, Class<T> type) {
		final List<T> results = new LinkedList<>();
		
		if (resource != null) {
			final TreeIterator<EObject> iterator = resource.getAllContents();
			
			while (iterator.hasNext()) {
				final EObject object = iterator.next();
				
				if (type.isInstance(object))
					results.add((T)object);
			}
		}
		
		return Collections.unmodifiableList(results);
	}
	
	private final static URI DEFAULT_URI = URI.createFileURI("foo.ecore");
	
	public static Resource createResource() {
		return createResource(DEFAULT_URI);
	}
	
	public static Resource createResource(URI uri) {
		return createResource(null, uri);
	}
	
	public static Resource createResource(EObject rootObject) {
		return createResource(rootObject, DEFAULT_URI);
	}
	
	public static Resource createResource(EObject rootObject, URI uri) {
		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new EcoreResourceFactoryImpl());

		final Resource resource = resourceSet.createResource(uri);
		
		if (rootObject != null) {
			resource.getContents().add(rootObject);
		}
		
		return resource;
	}
}
