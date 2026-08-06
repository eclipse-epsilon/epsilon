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

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.emc.composite.CompositeModel;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.workflow.tasks.nestedelements.ModelNestedElement;

public class LoadCompositeModelTask extends AbstractLoadModelTask {
	
	protected List<ModelNestedElement> modelNestedElements = new ArrayList<>();
	protected String matchTrace = null;
	
	@Override
	public IModel loadModel() throws BuildException {
		
		ArrayList<IModel> models = new ArrayList<>();
		
		for (ModelNestedElement modelNestedElement : modelNestedElements) {
			try {
				models.add(getProjectRepository().getModelByName(modelNestedElement.getRef()));
			}
			catch (Exception ex) {
				throw new BuildException(ex);
			}
		}
		
		MatchTrace matchTrace = null; 
		
		try {
			matchTrace = (MatchTrace) getProjectStackFrame().get(this.matchTrace).getValue();
		}
		catch (Exception ex) {
			throw new BuildException("A variable named " + 
					this.matchTrace + " of type MatchTrace has not been found in the context.");
		}
		
		CompositeModel composite = new CompositeModel(models, matchTrace);
		composite.setName(name);
		return composite;
		
		
	}
	
	public ModelNestedElement createModel() {
		ModelNestedElement model = new ModelNestedElement();
		modelNestedElements.add(model);
		return model;
	}
	
	public void setMatchTrace(String matchTrace) {
		this.matchTrace = matchTrace;
	}
}
