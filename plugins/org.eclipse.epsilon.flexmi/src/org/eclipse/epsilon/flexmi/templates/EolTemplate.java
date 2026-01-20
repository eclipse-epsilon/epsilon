/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.templates;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.emc.plainxml.PlainXmlModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.flexmi.FlexmiResource;
import org.w3c.dom.Element;

public class EolTemplate extends DynamicTemplate {

	public EolTemplate(Element element, FlexmiResource resource, URI uri) {
		super(element, resource, uri);
	}
	
	@Override
	public List<Element> getApplication(Element call) {
		try {
			EolModule module = new EolModule();
			parse(module, getScript(), uri);
			prepareModule(module, call);
			
			PlainXmlModel model = new PlainXmlModel();
			model.setReadOnLoad(false);
			model.load();
			model.setName("M");
			
			module.getContext().getModelRepository().addModel(model);
			module.execute();
			
			List<Element> elements = new ArrayList<>();
			for (Element element : model.allContents()) {
				if (element.getParentNode() == null) {
					elements.add(element);
				}
			}
			return elements;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
		
}
