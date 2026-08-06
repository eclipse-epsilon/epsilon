/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.extensions.formatter;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.epsilon.egl.dt.extensions.ExtensionSpecificationFactory;

public class FormatterSpecificationFactory extends ExtensionSpecificationFactory<FormatterSpecification> {

	private static final String FORMATTER_EXT_POINT_ID = "org.eclipse.epsilon.egl.dt.formatter";
	
	@Override
	protected String getExtensionPointIdentifier() {
		return FORMATTER_EXT_POINT_ID;
	}

	@Override
	protected FormatterSpecification createExtensionSpecification(IConfigurationElement extension) {
		return new FormatterSpecification(extension);
	}
}
