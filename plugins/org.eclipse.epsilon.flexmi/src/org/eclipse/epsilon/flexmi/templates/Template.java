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

import org.eclipse.epsilon.flexmi.FlexmiResource;
import org.eclipse.epsilon.flexmi.xml.Xml;
import org.w3c.dom.Element;

public abstract class Template {
	
	protected String name;
	protected ArrayList<Parameter> parameters = new ArrayList<>();
	protected Element content;
	protected URI uri;
	protected Element slot;
	protected FlexmiResource resource;
	
	public static final String NODE_NAME = ":template";
	public static final String PREFIX = ":";
	
	public Template(Element element, FlexmiResource resource, URI uri) {
		this.uri = uri;
		this.resource = resource;
		this.name = element.getAttribute("name");
		List<? extends Element> elements = Xml.getChildren(element, "parameter");
		parameters.ensureCapacity(elements.size());
		for (Element parameterElement : elements) {
			parameters.add(new Parameter(parameterElement.getAttribute("name"), parameterElement.getAttribute("default")));
		}
		content = Xml.getChild(element, "content");
		List<? extends Element> slots = Xml.getDescendant(element, Template.PREFIX + "slot");
		if (!slots.isEmpty()) slot = slots.get(0);
	}
	
	public String getName() {
		return name;
	}
	
	public List<Parameter> getParameters() {
		return parameters;
	}
	
	public Element getContent() {
		return content;
	}
	
	public Element getSlot() {
		return slot;
	}
	
	public abstract List<Element> apply(Element call);
	
}
