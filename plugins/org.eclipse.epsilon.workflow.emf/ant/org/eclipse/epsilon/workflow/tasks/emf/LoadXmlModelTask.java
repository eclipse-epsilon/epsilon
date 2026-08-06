/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.workflow.tasks.emf;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emc.emf.xml.XmlModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.workflow.tasks.AbstractLoadModelTask;

public class LoadXmlModelTask extends AbstractLoadModelTask {

	protected String aliases;
	protected File modelFile;
	protected File xsdFile;
	protected boolean read = true;
	protected boolean store = false;
	protected boolean expand = false;
	protected boolean cached = true;
	protected boolean concurrent = false;
	
	@Override
	public IModel loadModel() throws BuildException {
		
		final StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, name + "");
		properties.put(EmfModel.PROPERTY_ALIASES, aliases + "");
		properties.put(EmfModel.PROPERTY_MODEL_URI, ""+EmfUtil.createFileBasedURI(modelFile.getAbsolutePath()));
		properties.put(XmlModel.PROPERTY_XSD_FILE, xsdFile.getAbsolutePath());
		properties.put(EmfModel.PROPERTY_READONLOAD, read + "");
		properties.put(EmfModel.PROPERTY_STOREONDISPOSAL, store + "");
		properties.put(EmfModel.PROPERTY_EXPAND, expand + "");
		properties.put(EmfModel.PROPERTY_CACHED, cached + "");
		properties.put(EmfModel.PROPERTY_CONCURRENT, concurrent + "");
		
		XmlModel model = new XmlModel();
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

			throw new BuildException(e);
		}
		
	}

	public String getAliases() {
		return aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

	public File getModelFile() {
		return modelFile;
	}

	public void setModelFile(File modelFile) {
		this.modelFile = modelFile;
	}

	public File getXsdFile() {
		return xsdFile;
	}

	public void setXsdFile(File xsdFile) {
		this.xsdFile = xsdFile;
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

	public boolean isExpand() {
		return expand;
	}

	public void setExpand(boolean expand) {
		this.expand = expand;
	}
	
	public void setCached(boolean cached) {
		this.cached = cached;
	}
	
	public boolean isCached() {
		return cached;
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
}
