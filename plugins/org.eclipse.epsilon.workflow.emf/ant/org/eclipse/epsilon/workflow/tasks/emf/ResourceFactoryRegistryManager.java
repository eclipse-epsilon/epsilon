/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.workflow.tasks.emf;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;

public class ResourceFactoryRegistryManager {
	
	public static void configure() {
		Registry resourceFactoryRegistry = Resource.Factory.Registry.INSTANCE;
		if (!resourceFactoryRegistry.getExtensionToFactoryMap().containsKey("flexmi")) {
			resourceFactoryRegistry.getExtensionToFactoryMap().put("flexmi", new FlexmiResourceFactory());
		}
		if (!resourceFactoryRegistry.getExtensionToFactoryMap().containsKey("emf")) {
			try {
				Resource.Factory emfaticResourceFactory = 
						(Factory) Class.forName("org.eclipse.emf.emfatic.core.EmfaticResourceFactory").newInstance();
				resourceFactoryRegistry.getExtensionToFactoryMap().put("emf", emfaticResourceFactory);
			}
			catch (Exception ex) {
				// Ignore exception
			}
		}
	}
	
}
