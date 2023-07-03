/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance.eunit;

import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.models.IModel;

public class ModelManager {

	private final IEolContext context;
	
	public ModelManager(IEolContext context) throws Exception {
		this.context = context;
		
		register(FileUtil.getFileStandalone("ecore_lite.ecore", ModelManager.class).getAbsolutePath());
		register(FileUtil.getFileStandalone("fruits.ecore",     ModelManager.class).getAbsolutePath());
		register(FileUtil.getFileStandalone("traffic.ecore",    ModelManager.class).getAbsolutePath());
		register(FileUtil.getFileStandalone("configuration.ecore", ModelManager.class).getAbsolutePath());
	}
	
	private List<EPackage> register(String metamodelPath) throws Exception {
		return EmfUtil.register(URI.createFileURI(metamodelPath), EPackage.Registry.INSTANCE);
	}
	
	public EmfModel createInMemoryEmfModel(String modelName, String... metamodelUri) throws EolModelLoadingException {
		return addModel(new InMemoryEmfModel(modelName, EmfUtil.createResource(), metamodelUri));
	}
	
	public void unloadAllModels() {
		context.getModelRepository().dispose();
	}
	
	private <T extends IModel> T addModel(T model) {
		context.getModelRepository().addModel(model);
		return model;
	}
}
