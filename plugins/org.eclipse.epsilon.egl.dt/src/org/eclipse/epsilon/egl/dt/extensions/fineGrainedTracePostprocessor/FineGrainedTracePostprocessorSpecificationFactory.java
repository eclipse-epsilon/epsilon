/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.extensions.fineGrainedTracePostprocessor;


import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.epsilon.egl.dt.extensions.ExtensionSpecificationFactory;

public class FineGrainedTracePostprocessorSpecificationFactory extends ExtensionSpecificationFactory<FineGrainedTracePostprocessorSpecification> {

	private static final String EXT_POINT_ID = "org.eclipse.epsilon.egl.dt.fineGrainedTracePostprocessor";
	
	@Override
	protected String getExtensionPointIdentifier() {
		return EXT_POINT_ID;
	}

	@Override
	protected FineGrainedTracePostprocessorSpecification createExtensionSpecification(IConfigurationElement extension) {
		return new FineGrainedTracePostprocessorSpecification(extension);
	}
}
