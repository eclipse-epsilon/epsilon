/*******************************************************************************
 * Copyright (c) 2008-2014 The University of York, Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio García-Domínguez - reuseUnmodifiedMetamodelFiles flag
 *     Sina Madani - concurrency flag
******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.emf;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.workflow.tasks.AbstractLoadModelTask;

public class LoadEmfModelTask extends AbstractLoadModelTask {

	protected String alias;
	protected File modelFile;
	protected String modelUri;
	protected File metamodelFile;
	protected String metamodelUri;
	protected boolean read = true;
	protected boolean store = false;
	protected boolean expand = false;
	protected boolean reuseUnmodifiedMetamodelFile = true;
	protected boolean cached = true;
	protected boolean concurrent = false;
	protected boolean validate = false;
	
	@Override
	public IModel loadModel() throws BuildException {
		
		ResourceFactoryRegistryManager.configure();
		final EmfModel model = createEmfModel();
		
		final StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, name + "");
		properties.put(EmfModel.PROPERTY_ALIASES, alias + "");
		properties.put(EmfModel.PROPERTY_READONLOAD, read + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, store + "");
		properties.put(EmfModel.PROPERTY_EXPAND, expand + "");
		properties.put(EmfModel.PROPERTY_CACHED, cached + "");
		properties.put(EmfModel.PROPERTY_CONCURRENT, concurrent + "");
		properties.put(EmfModel.PROPERTY_REUSE_UNMODIFIED_FILE_BASED_METAMODELS, reuseUnmodifiedMetamodelFile + "");
		properties.put(EmfModel.PROPERTY_VALIDATE, validate + "");
		
		if (metamodelUri != null) {
			properties.put(EmfModel.PROPERTY_METAMODEL_URI, EmfUtil.createUri(metamodelUri));
		}
		if (metamodelFile != null) {
			properties.put(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, EmfUtil.convertFileToUri(metamodelFile));
		}
		if (modelFile != null && modelUri != null) {
			throw new BuildException("Only one of modelFile or modelUri may be used");
		}
		else if (modelUri != null) {
			properties.put(EmfModel.PROPERTY_MODEL_URI, EmfUtil.createUri(modelUri));
		}
		else {
			properties.put(EmfModel.PROPERTY_MODEL_URI, EmfUtil.convertFileToUri(modelFile));
		}
		
		try {
			model.load(properties);
			return model;
		}
		catch (EolModelLoadingException e) {
			/*
			 * We should release the model that did not load properly, as the user may want
			 * to fix the model and try again. Otherwise, this model may be cached
			 * indefinitely in the CachedResourceSet (see bug #445967).
			 */
			model.dispose();

			e.printStackTrace();
			throw new BuildException(e);
		}
	}

	// This logic has been extracted so that it can be stubbed out in tests
	protected EmfModel createEmfModel() {
		return new EmfModel();
	}
	
	public boolean isExpand() {
		return expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public void setCached(boolean cached) {
		this.cached = cached;
	}
	
	public boolean isCached() {
		return cached;
	}

	public void setAlias(String alias) {
		System.out.println("[WARNING] Attribute 'alias' is deprecated. Please use 'aliases' instead.");
		this.alias = alias;
	}

	public void setAliases(String aliases) {
		this.alias = aliases;
	}
	
	public String getAliases() {
		return this.alias;
	}
	
	public File getModelFile() {
		return modelFile;
	}

	public void setModelFile(File modelFile) {
		this.modelFile = modelFile;
	}

	public String getModelUri() {
		return modelUri;
	}

	public void setModelUri(String modelUri) {
		this.modelUri = modelUri;
	}

	public File getMetamodelFile() {
		return metamodelFile;
	}

	public void setMetamodelFile(File metamodelFile) {
		this.metamodelFile = metamodelFile;
	}

	public String getMetamodelUri() {
		return metamodelUri;
	}

	public void setMetamodelUri(String metamodelUri) {
		this.metamodelUri = metamodelUri;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public boolean isStore() {
		return store;
	}

	public void setStore(boolean store) {
		this.store = store;
	}

	public boolean isReuseUnmodifiedMetamodelFile() {
		return reuseUnmodifiedMetamodelFile;
	}

	public void setReuseUnmodifiedMetamodelFile(boolean reuseUnmodifiedMetamodelFiles) {
		this.reuseUnmodifiedMetamodelFile = reuseUnmodifiedMetamodelFiles;
	}

	/**
	 * 
	 * @return
	 * @since 1.6
	 */
	public boolean isConcurrent() {
		return concurrent;
	}

	/**
	 * 
	 * @param concurrent
	 * @since 1.6
	 */
	public void setConcurrent(boolean concurrent) {
		this.concurrent = concurrent;
	}
	
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	
	public boolean isValidate() {
		return validate;
	}
}
 
