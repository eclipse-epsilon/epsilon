/*******************************************************************************
 * Copyright (c) 2010 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.bibtex.domain;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.epsilon.common.util.StringUtil;

public class Publication {

	public final String type;
	public final String id;
	
	private final Map<String, String> properties;

	public Publication(String type, Map<String, String> properties) {
		this(type, "", properties);
	}
	
	public Publication(String type, String id, Map<String, String> properties) {
		this.type = type;
		this.id   = id;
		
		this.properties = properties;
	}
	
	public boolean hasProperty(String propertyName) {
		return properties.containsKey(propertyName);
	}

	public String getProperty(String propertyName) {
		return properties.get(propertyName);
	}

	public void setProperty(String propertyName, Object value) {
		properties.put(propertyName, value.toString());
	}

	public Collection<Entry<String, String>> getProperties() {
		return properties.entrySet();
	}
	
	@Override
	public String toString() {
		return StringUtil.firstToUpper(type) + getTitle();
	}

	private String getTitle() {
		return properties.containsKey("title") ? " '" + properties.get("title") + "'" : "";
	}
}
