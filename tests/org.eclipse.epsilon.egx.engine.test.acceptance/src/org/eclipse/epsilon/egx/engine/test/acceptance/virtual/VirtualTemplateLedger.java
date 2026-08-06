/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egx.engine.test.acceptance.virtual;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class VirtualTemplateLedger {

	private final Map<String, String> output = new HashMap<>();
	
	public void add(String path, String contents) {
		output.put(path, contents);
	}
	
	public Collection<String> getOutputFiles() {
		return output.keySet();
	}

	public Object getContentFor(String path) {
		return output.get(path);
	}
}
