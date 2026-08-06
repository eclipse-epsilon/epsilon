/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.generate;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.hutn.exceptions.HutnGenerationException;

public abstract class AbstractGenerator {

	public EmfModel generate(String uri) throws HutnGenerationException {
		final Resource resource;
		
		if (uri == null) {
			resource = EmfUtil.createResource();	
		} else {
			resource = EmfUtil.createResource(URI.createURI(uri));
		}
		
		return generate(resource);
	}
	
	public void store(File path) throws HutnGenerationException {
		EmfModel target = null;
		
		try {
			final URI fileUri = URI.createFileURI(path.getAbsolutePath());
			
			target = generate(EmfUtil.createResource(fileUri));
			
			target.store(fileUri);
	
		} finally {
			// Ensure that the model is always disposed, so that
			// its contents don't remain in memory and included
			// in the next call to generate
			
			if (target != null)
				target.dispose();
		}
	}
	
	protected abstract EmfModel generate(Resource resource) throws HutnGenerationException;
}
