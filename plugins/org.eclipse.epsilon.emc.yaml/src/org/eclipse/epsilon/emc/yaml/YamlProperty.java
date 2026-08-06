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

public class YamlProperty {

	protected String property;
	protected YamlNodeType type;
	protected boolean many;
	protected boolean isScalarNode;
	protected boolean isMappingNode;
	protected boolean isListNode;
	
	public static final char PROPERTY_MAPPING = 'm';
	public static final char PROPERTY_LIST = 'l';
	public static final char PROPERTY_SCALAR = 's';
	public static final char PROPERTY_COLLECTION = 'c';
	public static final char PROPERTY_ONE_ELEMENT = 'e';
	public static final char PROPERTY_SEPARATOR = '_';
	public static final String PROPERTY_ROOT = "YamlRoot";
	public static final String PROPERTY_FILE = "file";
	
	public static YamlProperty parse(String property, int indexOfSeparator) {
		YamlProperty yamlProperty = new YamlProperty();
		yamlProperty.type = YamlNodeUtility.getNodeType(property);
		if (yamlProperty.type == null) {
			yamlProperty.many = (indexOfSeparator == 2) ? isManyFor(property) : true;
			yamlProperty.property = property.substring(indexOfSeparator + 1);			
			if (propertyHasPrefix(property, indexOfSeparator, PROPERTY_SCALAR)) {
				yamlProperty.type = YamlNodeType.ScalarNode;
			}
			else if (propertyHasPrefix(property, indexOfSeparator, PROPERTY_MAPPING)) {
				yamlProperty.type = YamlNodeType.MappingNode;
			} else if (propertyHasPrefix(property, indexOfSeparator, PROPERTY_LIST)) {
				yamlProperty.type = YamlNodeType.ListNode;
			} else {
				yamlProperty = null;
			}		
		}
		setNodeType(yamlProperty, property);	
		return yamlProperty;
	}
	
	private static void setNodeType(YamlProperty yamlProperty, String property) {
		if ((yamlProperty != null) && (yamlProperty.type != null)) {
			yamlProperty.isScalarNode = (yamlProperty.type.equals(YamlNodeType.Node)) ? true : (yamlProperty.type.equals(YamlNodeType.ScalarNode));
			yamlProperty.isMappingNode = (yamlProperty.type.equals(YamlNodeType.Node)) ? true : (yamlProperty.type.equals(YamlNodeType.MappingNode));
			yamlProperty.isListNode = (yamlProperty.type.equals(YamlNodeType.Node)) ? true : (yamlProperty.type.equals(YamlNodeType.ListNode));
		}
	}
	
	private static boolean propertyHasPrefix(String property, int indexOfSeparator, char prefix) {
		return (property.charAt(0) == prefix) && (property.charAt(indexOfSeparator) == PROPERTY_SEPARATOR);
	}
	
	private static boolean isManyFor(String property) {
		return (property.charAt(1) == PROPERTY_ONE_ELEMENT) ? false : true;
	}
	
	public String getProperty() {
		return property;
	}

	public YamlNodeType getType() {
		return type;
	}

	public boolean isMany() {
		return many;
	}

	public boolean isScalarNode() {
		return isScalarNode;
	}

	public boolean isMappingNode() {
		return isMappingNode;
	}

	public boolean isListNode() {
		return isListNode;
	}
}