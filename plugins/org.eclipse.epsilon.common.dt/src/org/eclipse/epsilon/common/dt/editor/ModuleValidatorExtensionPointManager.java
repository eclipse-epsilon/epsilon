/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.dt.editor;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.epsilon.common.dt.extensions.ExtensionPointManager;
import org.eclipse.epsilon.common.module.IModuleValidator;

public class ModuleValidatorExtensionPointManager extends ExtensionPointManager<IModuleValidator>{
	
	public static ModuleValidatorExtensionPointManager getDefault() {
		if (managers.get(ModuleValidatorExtensionPointManager.class) == null) {
			managers.put(ModuleValidatorExtensionPointManager.class, new ModuleValidatorExtensionPointManager());
		}
		return (ModuleValidatorExtensionPointManager) managers.get(ModuleValidatorExtensionPointManager.class);
	}
	
	@Override
	protected IModuleValidator parse(IConfigurationElement element) throws Exception {
		return (IModuleValidator) element.createExecutableExtension("class");
	}

	@Override
	protected String getExtensionPointId() {
		return "org.eclipse.epsilon.common.dt.moduleValidator";
	}

}
