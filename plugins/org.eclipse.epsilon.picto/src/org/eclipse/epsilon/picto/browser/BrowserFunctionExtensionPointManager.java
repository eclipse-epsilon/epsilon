/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.browser;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.epsilon.common.dt.extensions.ExtensionPointManager;

/**
 * 
 * @author Sina Madani
 * @since 2.2
 */
public class BrowserFunctionExtensionPointManager extends ExtensionPointManager<PictoBrowserFunction> {
	
	@Override
	protected String getExtensionPointId() {
		return "org.eclipse.epsilon.picto.browserFunction";
	}

	@Override
	protected PictoBrowserFunction parse(IConfigurationElement element) throws Exception {
		return (PictoBrowserFunction) element.createExecutableExtension("class");
	}
}
