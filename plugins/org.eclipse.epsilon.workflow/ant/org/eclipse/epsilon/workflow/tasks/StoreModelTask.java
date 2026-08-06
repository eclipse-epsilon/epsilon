/*******************************************************************************
 * Copyright (c) 2008-2018 The University of York, Aston University.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - add targetUri (#534149)
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.tools.ant.BuildException;
import org.eclipse.epsilon.common.util.UriUtil;
import org.eclipse.epsilon.eol.exceptions.models.EolModelNotFoundException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.profiling.Profiler;

public class StoreModelTask extends EpsilonTask {
	
	protected String model;
	protected File target;
	protected URI targetUri;
	
	@Override
	public void executeImpl() throws BuildException {
		if (profile) Profiler.INSTANCE.start("Store model : " + model);
		try {
			IModel eolModel = getProjectRepository().getModelByName(model);
			if (targetUri != null) {
				eolModel.store(targetUri.toString());
			}
			else if (target != null) {
				eolModel.store(target.getAbsolutePath());
			}
			else {
				eolModel.store();
			}
		}
		catch (EolModelNotFoundException e) {
			throw new BuildException(e);
		}
		finally {
			if (profile) Profiler.INSTANCE.stop("Store model : " + model);
		}
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public File getTarget() {
		return target;
	}

	public void setTarget(File target) {
		this.target = target;
	}

	public URI getTargetUri() {
		return targetUri;
	}
	
	public void setTargetUri(String uriStr) throws URISyntaxException {
		// Don't create one that takes a URI directly: that would mean ANT tries to reflectively
		// create a bad URI and fail, which is a nightmare to debug!
		this.targetUri = UriUtil.sanitize(uriStr);
	}
	
	/**
	 * @since 1.6
	 */
	public void setTargetFile(File file) {
		this.targetUri = file.toURI();
	}
}
