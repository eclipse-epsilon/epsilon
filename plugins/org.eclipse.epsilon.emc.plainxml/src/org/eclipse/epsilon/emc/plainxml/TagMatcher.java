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

import org.w3c.dom.Element;

public class TagMatcher {
	
	public static boolean matches(Element e, String pattern) {
		if (e == null) return false;
		if (pattern.equals("*")) return true;
		return e.getTagName().equals(pattern);
	}
	
}
