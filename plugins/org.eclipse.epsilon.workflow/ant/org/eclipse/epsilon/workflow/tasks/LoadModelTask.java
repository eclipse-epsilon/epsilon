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

import org.apache.tools.ant.BuildException;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.workflow.tasks.hosts.HostManager;
import org.eclipse.epsilon.workflow.tasks.nestedelements.ParameterNestedElement;

public class LoadModelTask extends AbstractLoadModelTask {

	protected String type, impl, config;
	protected ArrayList<ParameterNestedElement> parameterNestedElements = new ArrayList<>();
	
	@Override
	public IModel loadModel() throws BuildException {
		
		IModel model = createModel(type);
		
		if (model == null) {
			if (impl != null) {
				try {
					model = (IModel) Class.forName(impl).getConstructor().newInstance();
				}
				catch (Exception ex) {
					throw new BuildException(ex);
				}
			}
			else {
				throw new BuildException("Could not instantiate a model of type " + type + ". " + 
						"This is either due to a typo or because you're running ANT outside Eclipse. " +
						"Try setting the impl property of the task to the fully-qualified name of the " +
						"class that implements the IModel interface.");
			}
		}
		
		try {
			model.load(getStringProperties());
			model.setName(name);
			return model;
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
	}
	
	public String getImpl() {
		return impl;
	}
	
	public void setImpl(String impl) {
		this.impl = impl;
	}
	
	protected StringProperties getStringProperties() {
		StringProperties properties = new StringProperties();
		for (ParameterNestedElement parameterNestedElement : parameterNestedElements) {
			properties.put(parameterNestedElement.getName(), parameterNestedElement.getValue());
		}
		return properties;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ParameterNestedElement createParameter() {
		ParameterNestedElement parameterNestedElement = new ParameterNestedElement();
		parameterNestedElements.add(parameterNestedElement);
		return parameterNestedElement;
	}

	protected IModel createModel(String type) throws BuildException {
		return HostManager.getHost().createModel(type);
	}
}
