/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitris Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.graphml;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

public class DomUtil {
	
	public static List<Element> getChildren(Element parent, String name) {
		List<Element> children = new ArrayList<>();
		for (Object o : parent.getChildren()) {
			if (o instanceof Element) {
				Element e = (Element) o;
				if (e.getName().equals(name)) {
					children.add(e);
				}
			}
		}
		return children;
	}
	
}
