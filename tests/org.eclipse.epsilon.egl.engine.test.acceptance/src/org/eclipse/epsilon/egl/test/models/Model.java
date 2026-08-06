/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test.models;

import java.io.File;
import java.io.IOException;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfModelFactory;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;

public enum Model {
	OOInstance("OO", "OOInstance.ecore", "OO.ecore");
	
	private final String modelName;
	private final File modelFile;
	private final File metaModelFile;
	
	private Model(String modelName, String modelFileName, String metaModelFileName) {
		this.modelName = modelName;
		try {
			modelFile     = FileUtil.getFileStandalone(modelFileName,     Model.class);
			metaModelFile = FileUtil.getFileStandalone(metaModelFileName, Model.class);
		}
		catch (IOException iox) {
			throw new RuntimeException(iox);
		}
	}
	
	public EmfModel loadEmfModel() throws EolModelLoadingException {
		return EmfModelFactory.getInstance().loadEmfModel(modelName, modelFile, metaModelFile);
	}
}
