/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.etl.dt.launching;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.epsilon.etl.strategy.ITransformationStrategy;

public class TransformationStrategyExtension {
	
	protected String type;
	protected String label;
	protected Class<?> clazz;
	protected String dialog;
	protected IConfigurationElement configurationElement;
	
	public TransformationStrategyExtension() {
		super();
	}

	public IConfigurationElement getConfigurationElement() {
		return configurationElement;
	}

	public void setConfigurationElement(IConfigurationElement configurationElement) {
		this.configurationElement = configurationElement;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public ITransformationStrategy createStrategy() {
		try {
			return (ITransformationStrategy) configurationElement.createExecutableExtension("class");
		} catch (CoreException e) {
			//PDE.logException(e);
			return null;
		}
	}
	
	public Object createDialog() {
		try {
			return configurationElement.createExecutableExtension("dialog");
		} catch (CoreException e) {
			//PDE.logException(e);
			return null;
		}
	}	
	
	public static List<TransformationStrategyExtension> getExtensions() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extenstionPoint = registry.getExtensionPoint("org.eclipse.epsilon.etl.dt.transformationStrategy");
		List<TransformationStrategyExtension> extensions = new ArrayList<>();
		IConfigurationElement[] configurationElements = extenstionPoint.getConfigurationElements();
		
		for (int i=0;i<configurationElements.length;i++) {
			IConfigurationElement configurationElement = configurationElements[i];
			TransformationStrategyExtension descriptor = new TransformationStrategyExtension();
			descriptor.setLabel(configurationElement.getAttribute("label"));
			descriptor.setType(configurationElement.getAttribute("type"));
			descriptor.setConfigurationElement(configurationElement);
			extensions.add(descriptor);
		}
		
		return extensions;
	}
	
}
