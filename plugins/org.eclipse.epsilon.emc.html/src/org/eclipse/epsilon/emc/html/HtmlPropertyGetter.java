/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.html;

import org.eclipse.epsilon.emc.plainxml.PlainXmlProperty;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertyGetter;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlPropertyGetter extends JavaPropertyGetter {

	@Override
	public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
		
		Element element = (Element) object;
		PlainXmlProperty p = PlainXmlProperty.parse(property);
		
		if (p == null) return super.invoke(object, property, context);
		
		if (p.isAttribute()) {
			return p.cast(element.attr(p.getProperty()));
		}
		else {
			Elements elements = element.getElementsByTag(p.getProperty());
			if (p.isMany()) {
				return elements;
			}
			else {
				if (elements.size() > 0) return elements.get(0);
				else return null;
			}
		}
	}

}
