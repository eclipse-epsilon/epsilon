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
import org.eclipse.epsilon.eol.models.IModel;

public class DisposeModelsTask extends EpsilonTask {

	@Override
	public void executeImpl() throws BuildException {
		for (IModel model : getProjectRepository().getModels()) {
			model.dispose();
		}
		getProjectRepository().getModels().clear();
	}

}
