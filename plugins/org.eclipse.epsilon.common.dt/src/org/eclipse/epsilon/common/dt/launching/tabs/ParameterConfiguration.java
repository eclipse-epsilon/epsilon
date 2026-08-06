/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.dt.launching.tabs;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.eol.types.EolAnyType;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;
import org.eclipse.epsilon.eol.types.EolType;

public class ParameterConfiguration {
	
	protected String name;
	protected String value = "";
	protected String type;
	
	public ParameterConfiguration() {}
	
	public ParameterConfiguration(StringProperties properties) {
		this.name = properties.getProperty("name");
		this.type = properties.getProperty("type");		
		this.value = properties.getProperty("value");
	}
	
	public StringProperties toStringProperties() {
		StringProperties p = new StringProperties();
		p.put("name", name);
		p.put("type", type);
		p.put("value", value);
		return p;
	}
	
	public ParameterConfiguration(String name, String value, String type) {
		super();
		this.name = name;
		this.value = value;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public EolType getEolType() {
		if ("String".equals(type)) return EolPrimitiveType.String;
		else if ("Integer".equals(type)) return EolPrimitiveType.Integer;
		else if ("Real".equals(type)) return EolPrimitiveType.Real;
		else if ("Boolean".equals(type)) return EolPrimitiveType.Boolean;
		return EolAnyType.Instance;
	}
	
	public Object getCastedValue() {
		if ("String".equals(type)) return value + "";
		else if ("Integer".equals(type)) {
			try { return Integer.parseInt(value); }
			catch (Exception ex) { return 0; }
		}
		else if ("Boolean".equals(type)) {
			try { return Boolean.parseBoolean(value); }
			catch (Exception ex) { return false; }
		}
		else if ("Real".equals(type)) {
			if (value.endsWith("d")) {
				try { return Double.parseDouble(value); }
				catch (Exception ex) { return 0.0d; }
			}
			else {
				try { return Float.parseFloat(value); }
				catch (Exception ex) { return 0.0f; }				
			}
		}
		else return value;
	}
	
}
