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
package org.eclipse.epsilon.emc.emf;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;

public final class EmfModelFactory {

	private static final EmfModelFactory instance = new EmfModelFactory();
	
	private EmfModelFactory() {}
	
	public static EmfModelFactory getInstance() {
		return instance;
	}
	
	/**
	 * The metamodel parameter can be of type EPackage, File or URI. Any other
	 * type will cause an IllegalArgumentException to be raised.
	 */
	public EmfModel createEmfModel(String name, File model, Object metamodel) {
		final EmfModel emfModel = createEmfModel(name, model);	
		if (metamodel instanceof String) {
			emfModel.setMetamodelUri((String) metamodel);
		}
		else if (metamodel instanceof EPackage) {
			emfModel.setMetamodelUri(((EPackage)metamodel).getNsURI());
		}
		else if (metamodel instanceof EPackage[]) {
			EPackage[] arr = (EPackage[]) metamodel;
			final List<String> nsUris = new ArrayList<>(arr.length);
			for (EPackage p : arr) {
				nsUris.add(p.getNsURI());
			}
			emfModel.setMetamodelUris(nsUris);
		}
		else if (metamodel instanceof File) {
			emfModel.setMetamodelFile(((File)metamodel).getAbsolutePath());
		}
		else if (metamodel instanceof File[]) {
			File[] arr = (File[]) metamodel;
            final List<String> files = new ArrayList<>(arr.length);
            for (File f : arr) {
            	files.add(f.getAbsolutePath());
            }
            emfModel.setMetamodelFiles(files);
        } 
		else if (metamodel instanceof URI) {
			emfModel.setMetamodelFileUri((URI)metamodel);
		}
		else {
			throw new IllegalArgumentException("Not a recognised metamodel type: " + metamodel.getClass().getCanonicalName());
		}
		return emfModel;
	}
	

	/**
	 * The metamodel parameter can be of type EPackage, File or URI. Any other
	 * type will cause an IllegalArgumentException to be raised.
	 */
	public EmfModel createEmfModel(String name, File model, Object metamodel, AccessMode accessMode) {
		final EmfModel emfModel = createEmfModel(name, model, metamodel);
		accessMode.applyTo(emfModel);
		return emfModel;
	}
	
	
	/**
	 * The metamodel parameter can be of type EPackage, File or URI. Any other
	 * type will cause an IllegalArgumentException to be raised.
	 */
	public EmfModel loadEmfModel(String name, File model, Object metamodel) throws EolModelLoadingException {
		final EmfModel emfModel = createEmfModel(name, model, metamodel);
		emfModel.load();
		return emfModel;
	}
	
	/**
	 * The metamodel parameter can be of type EPackage, File or URI. Any other
	 * type will cause an IllegalArgumentException to be raised.
	 */
	public EmfModel loadEmfModel(String name, File model, Object metamodel, AccessMode accessMode) throws EolModelLoadingException {
		final EmfModel emfModel = createEmfModel(name, model, metamodel, accessMode);
		emfModel.load();
		return emfModel;
	}
	
	private EmfModel createEmfModel(String name, File model) {
		final EmfModel emfModel = new EmfModel();	
		emfModel.setName(name);
		emfModel.setModelFile(model.getAbsolutePath());		
		return emfModel;
	}

	public enum AccessMode {
		READ_ONLY (true, false),
		WRITE_ONLY(false, true),
		READ_WRITE(true, true);
		
		private final boolean readOnLoad, storedOnDisposal;
		
		private AccessMode(boolean readOnLoad, boolean storedOnDisposal) {
			this.readOnLoad       = readOnLoad;
			this.storedOnDisposal = storedOnDisposal;
		}
		
		public void applyTo(EmfModel model) {
			model.setReadOnLoad(readOnLoad);
			model.setStoredOnDisposal(storedOnDisposal);
		}
	}
}
