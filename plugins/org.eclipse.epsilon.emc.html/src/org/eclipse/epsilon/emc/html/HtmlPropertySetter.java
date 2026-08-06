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
import org.eclipse.epsilon.eol.execute.introspection.java.JavaPropertySetter;
import org.jsoup.nodes.Element;

public class HtmlPropertySetter extends JavaPropertySetter {
	
	@Override
	public void invoke(Object target, String property, Object value, IEolContext context) throws EolRuntimeException {
		PlainXmlProperty p = PlainXmlProperty.parse(property);
		if (p != null && p.isAttribute()) {
			Element element = (Element) target;
			element.attr(p.getProperty(), String.valueOf(value));
			return;
		}
		super.invoke(target, property, value, context);
	}
	
}
