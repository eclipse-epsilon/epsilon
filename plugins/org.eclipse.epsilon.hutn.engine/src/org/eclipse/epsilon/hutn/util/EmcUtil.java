/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.util;

import java.io.File;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.epsilon.emc.emf.EmfModelFactory;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.hutn.exceptions.HutnMetaModelRegistrationException;

public abstract class EmcUtil {

	private EmcUtil() {}
	
	
	public static IModel loadModel(String modelName, File modelFile, File metaModelFile) throws EolModelLoadingException {
		return EmfModelFactory.getInstance().loadEmfModel(modelName, modelFile, metaModelFile);
	}
	
	public static IModel loadModel(String modelName, File modelFile, EPackage metaModelPackage) throws EolModelLoadingException {
		return EmfModelFactory.getInstance().loadEmfModel(modelName, modelFile, metaModelPackage);
	}
	
	public static IModel loadMetaModel(String modelName, File metaModelFile) throws EolModelLoadingException {
		return loadModel(modelName, metaModelFile, EcorePackage.eINSTANCE);
	}
	
	public static void register(String nsUri, EPackage pkg) throws HutnMetaModelRegistrationException {
		EPackage.Registry.INSTANCE.put(nsUri, pkg);
	}
}
