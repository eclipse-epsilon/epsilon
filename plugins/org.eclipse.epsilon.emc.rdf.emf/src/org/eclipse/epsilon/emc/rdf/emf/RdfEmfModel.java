/*******************************************************************************
 * Copyright (c) 2025 The University of York.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at:
 *
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.epsilon.emc.rdf.emf;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.rdf.emf.RDFGraphResourceFactory;

public class RdfEmfModel extends EmfModel {

	@Override
	protected ResourceSet createResourceSet() {
		ResourceSet resourceSet = super.createResourceSet();
		resourceSet.getResourceFactoryRegistry()
			.getExtensionToFactoryMap()
			.put("rdfres", new RDFGraphResourceFactory());
		return resourceSet;
	}

}
