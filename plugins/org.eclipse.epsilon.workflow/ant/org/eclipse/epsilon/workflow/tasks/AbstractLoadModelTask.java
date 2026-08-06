/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import org.apache.tools.ant.BuildException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.profiling.Profiler;

public abstract class AbstractLoadModelTask extends EpsilonTask {
	
	protected String name;
	
	@Override
	public void executeImpl() throws BuildException {
		if (profile) Profiler.INSTANCE.start("Load model : " + name);
		
		if (usesSharedModelRepository(getProject())) {
			if (getProjectRepository().getModels().stream().anyMatch(m -> m.getName().equals(name))) {
				return;
			}
		}
		try {
			getProjectRepository().addModel(loadModel());
		}
		finally {
			if (profile) Profiler.INSTANCE.stop("Load model : " + name);
		}
	}
	
	public abstract IModel loadModel() throws BuildException;
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
