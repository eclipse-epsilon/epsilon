/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.dt.extensions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.epsilon.common.dt.util.LogUtil;

public abstract class ExtensionPointManager<T> {
	
	protected static HashMap<Class<?>, ExtensionPointManager<?>> managers = new HashMap<>();
	protected List<T> extensions;
	
	public ExtensionPointManager() {
		managers.put(this.getClass(), this);
	}
	
	public List<T> getExtensions() {
		if (extensions == null) {
			extensions = new ArrayList<>();
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			if (registry == null) {
				return extensions;
			}

			IExtensionPoint extensionPoint = registry.getExtensionPoint(getExtensionPointId());
			
			for (IConfigurationElement element : extensionPoint.getConfigurationElements()) {
				try {
					extensions.add(parse(element));
				}
				catch (Exception e) {
					handleParseException(e);
				}
			}			
		}
		return extensions;
	}
	
	public void reset() {
		extensions = null;
	}
	
	protected void handleParseException(Exception ex) {
		LogUtil.log(ex);
	}
	
	protected abstract T parse(IConfigurationElement element) throws Exception;
	
	protected abstract String getExtensionPointId();
}
