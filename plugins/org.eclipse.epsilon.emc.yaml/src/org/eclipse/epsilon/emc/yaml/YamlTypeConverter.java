/*******************************************************************************
 * Copyright (c) 2022 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Ionut Predoaia - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.yaml;

public class YamlTypeConverter {

	protected static YamlDataType dataType;
	
	public static final char PROPERTY_STRING = 's';
	public static final char PROPERTY_BOOLEAN = 'b';
	public static final char PROPERTY_INTEGER = 'i';
	public static final char PROPERTY_FLOAT = 'f';
	public static final char PROPERTY_DOUBLE = 'd';

	public static Object getValue(Object object, String property) {
		YamlDataType yamlPropertyDataType = null;
		if(property.charAt(1) == YamlProperty.PROPERTY_SEPARATOR) {
			yamlPropertyDataType = dataTypeFor(property.charAt(0));
		}
		return (yamlPropertyDataType == null) ? object : cast(object.toString(), yamlPropertyDataType);
	}
	
	public static Object cast(String value, YamlDataType dataType) {
		value = value.trim();
		switch(dataType)
        {  
	        case BOOLEAN:
	        	return Boolean.parseBoolean(value);
	        	
	        case INTEGER: 
	        	return parseInteger(value);
				
        	case FLOAT:
        		return parseFloat(value);
        		
        	case DOUBLE:
        		return parseDouble(value);
				
        	default:
        		return value;
         }
	}
	
	private static int parseInteger(String value) {
		try {
			return Integer.parseInt(value);
		}
		catch (NumberFormatException ex) {
			return 0;
		}
	}
	
	private static float parseFloat(String value) {
		try {
			return Float.parseFloat(value);
		}
		catch (NumberFormatException ex) {
			return 0.0f;
		}
	}
	
	private static double parseDouble(String value) {
		try {
			return Double.parseDouble(value);
		}
		catch (NumberFormatException ex) {
			return 0.0d;
		}
	}
	
	private static YamlDataType dataTypeFor(char prefix) {
		YamlDataType dataType = null;
		switch(prefix)
        {  
	        case PROPERTY_STRING:
	    		dataType = YamlDataType.STRING;
				break;
				
	        case PROPERTY_BOOLEAN: 
        		dataType = YamlDataType.BOOLEAN;
				break; 
				
        	case PROPERTY_INTEGER:
        		dataType = YamlDataType.INTEGER;
				break;
				
        	case PROPERTY_FLOAT:
        		dataType = YamlDataType.FLOAT;
				break;
				
        	case PROPERTY_DOUBLE:
        		dataType = YamlDataType.DOUBLE;
				break;
         }
		return dataType;
	}
}