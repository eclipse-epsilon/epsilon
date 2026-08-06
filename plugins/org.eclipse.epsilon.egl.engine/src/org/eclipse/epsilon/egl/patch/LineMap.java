/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.patch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

/**
 * 
 * @since 1.6
 */
public class LineMap {
	
	protected Map<Line, List<Line>> map = new HashMap<>();
	
	public void put(Line source, Line target) {
		List<Line> lines = map.get(source);
		if (lines == null) {
			lines = new ArrayList<>();
			map.put(source, lines);
		}
		lines.add(target);
	}
	
	public List<Line> get(Line souce) {
		List<Line> lines = map.get(souce);
		if (lines == null) lines = Collections.emptyList();
		return lines;
	}
	
	@Override
	public String toString() {
		return map.toString();
	}
	
}
