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
import org.eclipse.epsilon.egl.dt.extensions.ExtensionSpecification;
import org.eclipse.epsilon.egl.formatter.Formatter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class FormatterSpecification extends ExtensionSpecification<Formatter> {

	private final Image icon;
	private final String name;
	
	public FormatterSpecification(IConfigurationElement extension) {
		super(extension);
		
		this.name = extension.getAttribute("name");
		
		if (extension.getAttribute("icon") != null) {
			this.icon = AbstractUIPlugin.imageDescriptorFromPlugin(extension.getContributor().getName(),
			                                                       extension.getAttribute("icon")).createImage();
		} else {
			this.icon = null;
		}
	}
	
	public String getName() {
		return name;
	}
	
	public Image getIcon() {
		return icon;
	}
}
