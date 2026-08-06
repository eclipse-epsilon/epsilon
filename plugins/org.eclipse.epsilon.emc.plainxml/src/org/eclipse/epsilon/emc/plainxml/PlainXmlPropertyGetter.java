/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.plainxml;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertyGetter;
import org.w3c.dom.Element;

public class PlainXmlPropertyGetter extends JavaPropertyGetter {
	
	protected PlainXmlModel model;
	
	public PlainXmlPropertyGetter(PlainXmlModel model) {
		this.model = model;
	}
	
	@Override
	public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
		if (object instanceof Element) synchronized (model) {
			final Element e = (Element) object;
			
			if ("children".equals(property)) {
				return DomUtil.getChildren(e);
			}
			
			if ("text".equals(property)) {
				return e.getTextContent();
			}
			
			if ("name".equals(property)) {
				int colonIndex = e.getTagName().indexOf(":");
				if (colonIndex >= 0) {
					return e.getTagName().substring(colonIndex + 1);
				}
				else {
					return e.getTagName();
				}
			}
			
			if ("descendants".equals(property)) {
				return getDescendants(e, new ArrayList<Element>());
			}
			
			if ("parent".equals(property)) {
				if (e.getParentNode() instanceof Element) {
					return e.getParentNode();
				}
				else return null;
			}
			
			if ("tagName".equals(property)) {
				return e.getTagName();
			}
			
			PlainXmlProperty p = PlainXmlProperty.parse(property);
			
			if (p != null) {
				
				if (p.isAttribute()) {
					return p.cast(e.getAttribute(p.getProperty()));
				}
				else if (p.isReference()) {
					
					for (final Binding binding : BindingMatcher.getMatchingBindings(model, e, p.getProperty())) {
						if (binding.isMany()) {
							LoudList<Element> referenced = new LoudList<>();
							String[] referencedIds = e.getAttribute(p.getProperty()).split(",");
							for (Object o : model.allContents()) {
								Element candidate = (Element) o;
								if (TagMatcher.matches(candidate, binding.getTargetTag())) {
									for (String referencedId : referencedIds) {
										if (candidate.getAttribute(binding.getTargetAttribute()).equals(referencedId.trim())) {
											referenced.add(candidate);
										}
									}
								}	
							}
							
							referenced.addListener((LoudListChangeListener<Element>) list -> {
								String bindTarget = binding.getTargetAttribute();
								String newAttributeValue = list.stream()
									.map(element -> element.getAttribute(bindTarget))
									.collect(Collectors.joining(", "));
								
								e.setAttribute(binding.getSourceAttribute(), newAttributeValue);
							});
							
							return referenced;
						}
						else {
							String referencedId = e.getAttribute(p.getProperty());
							for (Object o : model.allContents()) {
								Element candidate = (Element) o;
								if (candidate.getTagName().matches(binding.getTargetTag())) {
									if (candidate.getAttribute(binding.getTargetAttribute()).equals(referencedId.trim())) {
										return candidate;
									}						
								}	
							}
						}
					}
				}
				else {
					List<Element> children = DomUtil.getChildren(e);
					List<Element> result = new ArrayList<>();
					
					// Look for elements with this specific tag name
					for (Element child : children) {
						if (model.tagMatches(child, p.getProperty())) {
							result.add(child);
						}
					}
					
					if (p.isMany()) {
						return result;
					}
					else if (result.size() > 0) {
						return result.get(0);
					}
					else {
						return null;
					}
				}
			}
		}
		
		return super.invoke(object, property, context);
	}
	
	protected List<Element> getDescendants(Element root, List<Element> descendants) {
		for (Element child : DomUtil.getChildren(root)) {
			descendants.add(child);
			getDescendants(child, descendants);
		}
		return descendants;
	}
}
