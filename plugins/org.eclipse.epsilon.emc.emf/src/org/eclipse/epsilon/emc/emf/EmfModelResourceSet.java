/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

public class EmfModelResourceSet extends ResourceSetImpl {

	@Override
	public Resource createResource(URI uri) {
		Resource.Factory resourceFactory = getResourceFactoryRegistry().getFactory(uri);
		if (resourceFactory != null) {
			Resource result = resourceFactory.createResource(uri);
			
			// Added code to copy the EPackages of the old resource set
			// into the new resource set
			if (result.getResourceSet() != null) {
				for (String s : result.getResourceSet().getPackageRegistry().keySet()) {
					this.getPackageRegistry().put(s, result.getResourceSet().getPackageRegistry().get(s));
				}
			}
			
			getResources().add(result);
			return result;
		} else {
			return null;
		}
	}

}
