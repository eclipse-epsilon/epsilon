/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.xmi.postprocessor;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.ClassObject;


public class ClassObjectNamer {

	private final Map<String, Integer> counters = new HashMap<>(); 
	
	public String name(ClassObject co) {
		final String name;
		
		if (hasANameSlot(co)) {
			name = getNameSlotValue(co);
		
		} else {
			name = getIdentifierBasedOnType(co);
		}
		
		return name;
	}
	
	private boolean hasANameSlot(ClassObject co) {
		return co.findSlot("name") instanceof AttributeSlot;
	}

	private String getNameSlotValue(ClassObject co) {
		return co.findSlot("name").getValues().get(0).toString();
	}
	
	private String getIdentifierBasedOnType(ClassObject co) {
		final String type = co.getType() == null ? "UnknownType" : co.getType();
		return type + counterFor(type);
	}
	
	private int counterFor(String type) {
		if (!counters.containsKey(type)) {
			counters.put(type, 1);
		}
		
		int current = counters.get(type);
		counters.put(type, current+1);
		
		return current;
	}
}
