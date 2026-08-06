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
import java.util.List;
import org.apache.commons.lang3.text.StrLookup;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.eclipse.epsilon.flexmi.FlexmiResource;
import org.eclipse.epsilon.flexmi.xml.Location;
import org.eclipse.epsilon.flexmi.xml.Xml;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XmlTemplate extends Template {
	
	public XmlTemplate(Element element, FlexmiResource resource, URI uri) {
		super(element, resource, uri);
	}

	public List<Element> apply(Element call) {
		
		List<Element> application = getApplication(call);
		
		if (!application.isEmpty()) {
			Element firstElement = application.get(0);
			for (String attributeName : Xml.getAttributeNames(call)) {
				if (!getParameters().stream().anyMatch(p -> p.getName().equals(attributeName))) {
					firstElement.setAttribute(attributeName, call.getAttribute(attributeName));
				}
			}
		}
		
		for (Element applicationElement : application) {
			applicationElement.setUserData(Location.ID, call.getUserData(Location.ID), null);
			replaceParameters(applicationElement, call);
		}
		
		return application;
	}
	
	protected void replaceSlots(Element call, Element content) {
		List<Element> slots = Xml.getDescendant(content, Template.PREFIX + "slot");
		Element slot = null;
		if (!slots.isEmpty()) slot = slots.get(0);
		
		if (slot != null) {
			
			if (!Xml.getChildren(call).isEmpty()) {
				for (Element child : Xml.getChildren(call)) {
					Node clonedNode = child.cloneNode(true);
					clonedNode.setUserData(Location.ID, child.getUserData(Location.ID), null);
					slot.getOwnerDocument().adoptNode(clonedNode);
					slot.getParentNode().insertBefore(clonedNode, slot);
				}
			}
			slot.getParentNode().removeChild(slot);
		}
	}
	
	public List<Element> getApplication(Element call) {
		
		Element clonedContent = (Element) content.cloneNode(true);
		replaceSlots(call, clonedContent);
		return Xml.getChildren(clonedContent);
	}
	
	protected void replaceParameters(Element element, Element call) {
		
		StrSubstitutor substitutor = new StrSubstitutor(new StrLookup<String>() {
			@Override
			public String lookup(String name) {
				if (call.hasAttribute(Template.PREFIX + name)) {
					return call.getAttribute(Template.PREFIX + name);
				}
				return call.getAttribute(name);
			}
		});
		
		for (Node attribute : Xml.getAttributes(element)) {
			if (attribute.getNodeValue().contains("$")) {
				attribute.setNodeValue(substitutor.replace(attribute.getNodeValue()));
			}
		}
		
		for (Element child : Xml.getChildren(element)) {
			replaceParameters(child, call);
		}
		
	}
	
}
