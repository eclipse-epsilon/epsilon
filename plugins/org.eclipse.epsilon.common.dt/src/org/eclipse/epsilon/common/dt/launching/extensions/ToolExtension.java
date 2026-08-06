/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.launching.extensions;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.epsilon.eol.exceptions.EolInternalException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.tools.ITool;

public class ToolExtension {
	
	protected IConfigurationElement configurationElement;
	static ArrayList<ToolExtension> instances;
	final static String extensionPoint = "org.eclipse.epsilon.common.dt.tool";
	
	public IConfigurationElement getConfigurationElement() {
		return configurationElement;
	}

	public void setConfigurationElement(IConfigurationElement configurationElement) {
		this.configurationElement = configurationElement;
	}

	public ToolExtension() {
		super();
	}
	
	public static ArrayList<ToolExtension> getInstances() {
		
		if (instances == null) {
			instances = new ArrayList<>();
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			if (registry == null) {
				return instances;
			}

			IExtensionPoint extenstionPoint = registry.getExtensionPoint(extensionPoint);
			
			IConfigurationElement[] configurationElements = extenstionPoint.getConfigurationElements();
			
			for (int i=0;i<configurationElements.length;i++) {
				ToolExtension extension = new ToolExtension();
				IConfigurationElement configurationElement = configurationElements[i];
				extension.configurationElement = configurationElement;
				instances.add(extension);
			}
		}
		
		return instances;
	}
	
	public static ToolExtension forClass(String clazz) {
		for (ToolExtension extension : getInstances()) {
			if (extension.getClazz().equalsIgnoreCase(clazz)) {
				return extension;
			}
		}
		return null;
	}
	
	public String getDefaultName() {
		return configurationElement.getAttribute("defaultName");
	}
	
	public String getClazz() {
		return configurationElement.getAttribute("class");
	}
	
	public String getDescription() {
		return configurationElement.getAttribute("description");
	}
	
	public Object createTool(List<Object> parameters) throws EolRuntimeException {
		try { 
			Object tool = configurationElement.createExecutableExtension("class");
			if (tool instanceof ITool) {
				
			}
			return tool;
		}
		catch (Exception ex) {
			throw new EolInternalException(ex);
		}
	}
	
	// FIXME Show the name of the classs, then the (fully qualified name)
	@Override
	public String toString() {
		//String[] parts = getClazz().split(".");
		//return parts[parts.length-1] + " (" + getClazz() + ")";
		return getClazz();
	}
}
