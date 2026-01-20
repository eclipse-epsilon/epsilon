/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.templates;

import java.io.File;
import java.net.URI;

import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.flexmi.FlexmiResource;
import org.w3c.dom.Element;

public abstract class DynamicTemplate extends XmlTemplate {

	public DynamicTemplate(Element element, FlexmiResource resource, URI uri) {
		super(element, resource, uri);
	}
	
	protected void prepareModule(IEolModule module, Element call) {
		for (Parameter parameter : getParameters()) {
			String parameterName = parameter.getName();
			String value = call.getAttribute(parameterName);
			if (call.hasAttribute(Template.PREFIX + parameterName)) value = call.getAttribute(Template.PREFIX + parameterName);
			module.getContext().getFrameStack().put(Variable.createReadOnlyVariable(parameterName, value));
		}
		
		FlexmiResource resource = this.resource;
		while (resource != null) {
			module.getOperations().addAll(resource.getOperations());
			resource = resource.getImportedFrom();
		}
	}
	
	protected String getScript() {
		if (content.hasAttribute("script")) return content.getAttribute("script");
		else return content.getTextContent().trim();
	}
	
	protected void parse(IEolModule module, String code, URI uri) throws Exception {
		File file = null;
		
		try {
			file = new File(uri);
		}
		catch (Exception ex) {}
		
		if (file == null) module.parse(code);
		else module.parse(code, file);
	}
	
}
