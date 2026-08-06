/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import org.apache.tools.ant.BuildException;
import org.eclipse.epsilon.profiling.Profiler;
import org.eclipse.epsilon.eol.exceptions.models.EolModelNotFoundException;
import org.eclipse.epsilon.eol.models.IModel;
 
public class DisposeModelTask extends EpsilonTask {
	 
	protected String model;
	
	@Override 
	public void executeImpl() throws BuildException {
		if (profile) Profiler.INSTANCE.start("Dispose model : " + model); 
		try {
			IModel eolModel = getProjectRepository().getModelByName(model);
			eolModel.dispose();
			getProjectRepository().getModels().remove(eolModel);
		}
		catch (EolModelNotFoundException e) {
			throw new BuildException(e);
		}
		finally {
			if (profile) Profiler.INSTANCE.stop("Dispose model : " + model);
		}
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}	
}
