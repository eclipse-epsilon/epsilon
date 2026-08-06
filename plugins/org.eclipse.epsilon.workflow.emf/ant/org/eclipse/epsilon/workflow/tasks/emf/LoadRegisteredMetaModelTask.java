/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.emf;

import org.apache.tools.ant.BuildException;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfMetaModel;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.workflow.tasks.AbstractLoadModelTask;

public class LoadRegisteredMetaModelTask extends AbstractLoadModelTask {

	protected String aliases;
	protected String metamodelUri;
	protected boolean cached;
	
	@Override
	public IModel loadModel() throws BuildException {
		EmfMetaModel model = new EmfMetaModel();
		StringProperties properties = new StringProperties();
		properties.put(EmfModel.PROPERTY_NAME, name + "");
		properties.put(EmfModel.PROPERTY_ALIASES, aliases + "");
		properties.put(EmfModel.PROPERTY_METAMODEL_URI, metamodelUri + "");
		properties.put(EmfModel.PROPERTY_METAMODEL_URI, metamodelUri + "");
		properties.put(EmfModel.PROPERTY_CACHED, cached + "");
		
		try {
			model.load(properties);
			return model;
		}
		catch (EolModelLoadingException e) {
			throw new BuildException(e);
		}
	}

	public String getAliases() {
		return aliases;
	}

	public void setAliases(String aliases) {
		this.aliases = aliases;
	}

	public String getMetamodelUri() {
		return metamodelUri;
	}

	public void setMetamodelUri(String metamodelUri) {
		this.metamodelUri = metamodelUri;
	}

	/**
	 * 
	 * @return
	 * @since 1.6
	 */
	public boolean isCached() {
		return cached;
	}

	/**
	 * 
	 * @param cached
	 * @since 1.6
	 */
	public void setCached(boolean cached) {
		this.cached = cached;
	}
}
 
