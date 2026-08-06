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

public class PlainXmlProperty {
	
	protected boolean many;
	protected PlainXmlPropertyType type;
	protected String property;
	protected PlainXmlPropertyDataType dataType;
	
	public static PlainXmlProperty parse(String property) {
		PlainXmlProperty p = new PlainXmlProperty();
		
		if (property.startsWith("a_") || property.startsWith("b_") ||
			property.startsWith("i_") || property.startsWith("f_") || 
			property.startsWith("d_") || property.startsWith("s_")) {
			
			p.dataType = p.dataTypeFor(property.charAt(0) + "");
			p.type = PlainXmlPropertyType.Attribute;
			p.many = false;
			
		} else if (property.startsWith("e_")) {
			
			p.type = PlainXmlPropertyType.Element;
			p.many = false;
			
		} else if (property.startsWith("c_")) {
			
			p.type = PlainXmlPropertyType.Element;
			p.many = true;
		
		} else if (property.startsWith("x_")) {
			
			p.type = PlainXmlPropertyType.Reference;
			
		} else {
			p = null;
		}
		
		if (p!=null) {
			p.property = property.substring(2);
		}
		
		return p;
	}
	
	public Object cast(String value) {
		value = value.trim();
		
		if (dataType == PlainXmlPropertyDataType.BOOLEAN) {
			return Boolean.parseBoolean(value);
		}
		else if (dataType == PlainXmlPropertyDataType.INTEGER) {
			try {
				return Integer.parseInt(value);
			}
			catch (NumberFormatException ex) {
				return 0;
			}
		}
		else if (dataType == PlainXmlPropertyDataType.FLOAT) {
			try {
				return Float.parseFloat(value);
			}
			catch (NumberFormatException ex) {
				return 0.0f;
			}
		}
		else if (dataType == PlainXmlPropertyDataType.DOUBLE) {
			try {
				return Double.parseDouble(value);
			}
			catch (NumberFormatException ex) {
				return 0.0d;
			}
		}
		else {
			return value;
		}
	
	}
	
	private PlainXmlPropertyDataType dataTypeFor(String letter) {
		if (letter.equals("b")) {
			return PlainXmlPropertyDataType.BOOLEAN;
		}
		else if (letter.equals("f")) {
			return PlainXmlPropertyDataType.FLOAT;
		}
		else if (letter.equals("d")) {
			return PlainXmlPropertyDataType.DOUBLE;
		}
		else if (letter.equals("i")){
			return PlainXmlPropertyDataType.INTEGER;
		}
		else {
			return PlainXmlPropertyDataType.STRING;
		}
	}
	
	public String getProperty() {
		return property;
	}

	public boolean isAttribute() {
		return type == PlainXmlPropertyType.Attribute;
	}

	public boolean isElement() {
		return type == PlainXmlPropertyType.Element;
	}
	
	public boolean isMany() {
		return many;
	}

	public boolean isReference() {
		return type == PlainXmlPropertyType.Reference;
	}
	
}
