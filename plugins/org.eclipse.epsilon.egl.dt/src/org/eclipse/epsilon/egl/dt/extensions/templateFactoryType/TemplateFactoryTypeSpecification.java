/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.dt.extensions.templateFactoryType;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.dt.extensions.ExtensionSpecification;

public class TemplateFactoryTypeSpecification extends ExtensionSpecification<EglTemplateFactory> {

	private final String name;

	public TemplateFactoryTypeSpecification(IConfigurationElement extension) {
		super(extension);
		
		this.name = extension.getAttribute("name");
	}

	public String getName() {
		return name;
	}
}
