/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.dummydriver;

import java.util.HashMap;
import java.util.Map;

public class DummyModelElement {
	
	protected String type = "";
	protected Map<String, Object> props = new HashMap<>();
	
	public DummyModelElement(String type) {
		this.type = type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public Map<String, Object> getProps() {
		return props;
	}
	
	public DummyModelElement put(String name, Object value) {
		props.put(name, value);
		return this;
	}
	
	@Override
	public String toString() {
		return "Object of type " + type;
	}
	
}
