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
import java.util.Collection;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertySetter;
import org.eclipse.epsilon.eol.execute.operations.contributors.IterableOperationContributor;
import org.w3c.dom.Element;

public class PlainXmlPropertySetter extends JavaPropertySetter {
	
	protected PlainXmlModel model;
	
	public PlainXmlPropertySetter(PlainXmlModel model) {
		this.model = model;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void invoke(Object object, String property, Object value, IEolContext context) throws EolRuntimeException {
		if (object instanceof Element) synchronized (model) {
			Element e = (Element) object;
			
			if ("text".equals(property)) {
				e.setTextContent(String.valueOf(value));
				return;
			}
			
			PlainXmlProperty p = PlainXmlProperty.parse(property);
			if (p != null) {
				if (p.isAttribute()) {
					e.setAttribute(p.getProperty(), p.cast(String.valueOf(value)) + "");
					return;
				}
				else if (p.isReference()) {
					String sourceTag = e.getTagName();
					
					for (Binding binding : model.getBindings()) {
						if (sourceTag.matches(binding.getSourceTag()) && p.getProperty().matches(binding.getSourceAttribute())) {
							String sourceAttribute = p.getProperty();
							if (binding.isMany()) {
								
								ArrayList<String> referenceIds = new ArrayList<>();
								
								Collection<Object> values = null;
								if (value instanceof Collection) {
									values = (Collection) value;
								}
								else {
									values = new ArrayList<>();
									values.add(value);
								}
								
								for (Object o : values) {
									Element candidate = (Element) o;
									if (candidate.getTagName().matches(binding.getTargetTag())) {
										String referenceId = candidate.getAttribute(binding.getTargetAttribute());
										if (referenceId != null && !referenceId.trim().isEmpty()) {
											referenceIds.add(referenceId);
										}
									}	
								}
								try (IterableOperationContributor ic = new IterableOperationContributor(referenceIds)) {
									e.setAttribute(sourceAttribute, ic.concat(", "));
									return;
								}
							}
							else {
								if (value instanceof Element) {
									Element candidate = (Element) value;
									if (candidate.getTagName().matches(binding.getTargetTag())) {
										String referenceId = candidate.getAttribute(binding.getTargetAttribute());
										if (referenceId != null && referenceId.trim().length() > 0) {
											e.setAttribute(sourceAttribute, referenceId);
											return;
										}					
									}	
								}
							}
						}
					}
				}
			}
		}
		
		super.invoke(object, property, value, context);
	}
	
}
