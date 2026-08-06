/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.actions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

public class ActionMap {
	
	protected Map<EObject, List<Action>> map = new HashMap<>();
	
	public void addAction(EObject eObject, Action action) {
		if (!map.containsKey(eObject)) {
			map.put(eObject, new ArrayList<Action>());
		}
		map.get(eObject).add(action);
	}
	
	public List<Action> getActions(EObject eObject) {
		if (!map.containsKey(eObject)) {
			map.put(eObject, new ArrayList<Action>());
		}
		return map.get(eObject);
	}
	
}
