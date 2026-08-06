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
import java.util.List;

import org.w3c.dom.Element;

public class BindingMatcher {
	
	public static Collection<Binding> getMatchingBindings(PlainXmlModel model, Element e, String property) {
		List<Binding> bindings = new ArrayList<>();
		for (Binding binding : model.getBindings()) {
			if (TagMatcher.matches(e, binding.getSourceTag())
					&& property.equals(binding.getSourceAttribute())) {
				bindings.add(binding);
			}
		}
		return bindings;
	}
	
}
