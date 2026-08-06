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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.Set;
import java.util.List;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public class YamlNodeUtility {
	
	public static Collection<Entry> getNodes(Object yamlContent, YamlProperty yamlProperty, boolean areAllNodes) {
		Collection<Entry> nodes = new ArrayList<>();
		setNodes(yamlContent, nodes, yamlProperty, areAllNodes);
		return nodes;
	}
	
	private static void setNodes(Object yamlContent, Collection<Entry> nodes, YamlProperty yamlProperty, boolean areAllNodes) {
		if (yamlContent instanceof List) {
			setNodes((List)yamlContent, nodes, yamlProperty, areAllNodes);
		}
		else if (yamlContent instanceof Map) {
			setNodes((Map)yamlContent, nodes, yamlProperty, areAllNodes);
		}
	}
	
	private static void setNodes(List listNode, Collection<Entry> nodes, YamlProperty yamlProperty, boolean areAllNodes) {
		for (Object node: listNode) {
			if (node instanceof Map) {
				setNodes((Map) node, nodes, yamlProperty, areAllNodes);
			}
		}
	}
	
	private static void setNodes(Map mappingNode, Collection<Entry> nodes, YamlProperty yamlProperty, boolean areAllNodes) {	
		Set<Entry> entries = (Set<Entry>) mappingNode.entrySet();	
		for (Entry entry: entries) {
			setNodes(entry, nodes, yamlProperty, areAllNodes);	
		}			
	}
	
	private static void setNodes(Entry entry, Collection<Entry> nodes, YamlProperty yamlProperty, boolean areAllNodes) {
		Object entryValue = entry.getValue();
		if (entryValue instanceof List) {
			if (yamlProperty.isListNode()) {
				addNodeIfNecessary(entry, yamlProperty, nodes);
			}
			if (areAllNodes) {
				setNodes((List) entryValue, nodes, yamlProperty, areAllNodes);
			}	
		}
		else if (entryValue instanceof Map) {
			if (yamlProperty.isMappingNode()) {
				addNodeIfNecessary(entry, yamlProperty, nodes);
			}
			if (areAllNodes) {
				setNodes((Map) entryValue, nodes, yamlProperty, areAllNodes);
			}
		}
		else {
			if (yamlProperty.isScalarNode()) {
				addNodeIfNecessary(entry, yamlProperty, nodes);
			}
		}
	}
	
	private static void addNodeIfNecessary(Entry entry, YamlProperty yamlProperty, Collection<Entry> nodes) {
		String property = yamlProperty.getProperty();
		if (property == null) {
			nodes.add(entry);
		}
		else {
			if (entry.getKey().equals(property)) {
				nodes.add(entry);
			}
		}
	}
	
	public static Object getQueryResult(Collection<Entry> queryResult, YamlProperty yamlProperty) {	
		if (queryResult == null || queryResult.isEmpty()) {
			return null;
		}
		else {
			return yamlProperty.isMany() ? queryResult : queryResult.iterator().next();
		}
	}
	
	public static boolean isListNode(List list) {
		for (Object object : list) {
			if (!(object instanceof LinkedHashMap))
				return false;
		}
		return true;
	}
	
	public static YamlNodeType getNodeType(String type) {
		YamlNodeType nodeType;
		try {
			nodeType = YamlNodeType.valueOf(type);
		}
		catch(Exception ex) {
			nodeType = null;
		}
		return nodeType;
	}
	
	public static YamlNodeType getNodeType(Entry node) {
		Object nodeValue = node.getValue();
		YamlNodeType nodeType = null;
		if (nodeValue instanceof Map) {
			nodeType = YamlNodeType.MappingNode;
		}
		else if (nodeValue instanceof List) {
			nodeType = YamlNodeType.ListNode;
		}
		else {
			nodeType = YamlNodeType.ScalarNode;
		}
		return nodeType;
	}
	
	private static Entry getNode(YamlProperty yamlProperty, Collection<Object> parameters) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		switch(yamlProperty.getType()) {
			case ScalarNode:
				return getScalarNode(yamlProperty, new ArrayList(parameters));
				
			case MappingNode:
				return getMappingNode(yamlProperty, new ArrayList(parameters));
				
			case ListNode:
				return getListNode(yamlProperty, new ArrayList(parameters));
		}
		return map.entrySet().iterator().next();	
	}
	
	public static Entry getNode(String type, int indexOfSeparator, Collection<Object> parameters) {
		YamlProperty yamlProperty = YamlProperty.parse(type, indexOfSeparator);
		return YamlNodeUtility.getNode(yamlProperty, parameters);		
	}
	
	private static Entry getScalarNode(YamlProperty yamlProperty, List parameters) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		String nodeName = getNameParameter(yamlProperty, parameters, YamlNodeType.ScalarNode);
		Object nodeValue = getValueParameter(yamlProperty, parameters);
		map.put(nodeName, nodeValue);
		return map.entrySet().iterator().next();
	}
	
	private static Entry getListNode(YamlProperty yamlProperty, List parameters) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		String nodeName = getNameParameter(yamlProperty, parameters, YamlNodeType.ListNode);
		Object valueParameter = getValueParameter(yamlProperty, parameters);
		int listSize = (int) YamlTypeConverter.cast(valueParameter.toString(), YamlDataType.INTEGER);
		List list = new ArrayList<>();
		for(int i=0; i < listSize; i++) {
			list.add(new LinkedHashMap());
		}	
		map.put(nodeName, list);
		return map.entrySet().iterator().next();
	}
	
	private static Entry getMappingNode(YamlProperty yamlProperty, List parameters) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		String nodeName = getNameParameter(yamlProperty, parameters, YamlNodeType.MappingNode);
		map.put(nodeName, new LinkedHashMap<String, Object>());
		return map.entrySet().iterator().next();
	}
	
	private static String getNameParameter(YamlProperty yamlProperty, List parameters, YamlNodeType yamlNodeType) {
		String nodeName;
		if(yamlProperty.getProperty() != null) {
			nodeName = yamlProperty.getProperty();
		}
		else if(parameters.size() > 0) {
			nodeName = parameters.get(0).toString();
		}
		else {
			nodeName = yamlNodeType.toString();
		}
		return nodeName;
	}
	
	private static Object getValueParameter(YamlProperty yamlProperty, List parameters) {
		int indexOfValueParameter = (yamlProperty.getProperty() != null) ? 0 : 1;
		Object nodeValue = (parameters.size() > indexOfValueParameter) ? parameters.get(indexOfValueParameter) : "";
		return nodeValue;
	}
	
	public static boolean deleteNode(Object yamlContent, List<Entry> createdNodes, Entry instance) {
		boolean isCreatedNode = createdNodes.contains(instance);
		if (isCreatedNode) {
			createdNodes.remove(instance);
			return true;
		}
		else {
			YamlObject yamlObject = new YamlObject(instance);
			findYamlObject(yamlContent, yamlObject);
			if(yamlObject.isFound()) {
				yamlObject.getParentNode().remove(instance.getKey());
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	public static boolean ownsYamlObject(Object yamlContent, List<Entry> createdNodes, Object instance) {
		boolean isOwnYamlObject = ownsYamlObject(yamlContent, instance);
		boolean isCreated = (!isOwnYamlObject) ? isCreatedYamlObject(createdNodes, new YamlObject(instance)) : false;
		return isOwnYamlObject || isCreated;
	}
	
	private static boolean ownsYamlObject(Object yamlContent, Object instance) {
		if (instance instanceof List) {
			 return ownsYamlObjects(yamlContent, (List)instance);
		}
		else {
			YamlObject yamlObject = null;
			if (instance instanceof YamlModel) {
				yamlObject = new YamlObject(((YamlModel)instance).getYamlContent());
			}
			else if (instance instanceof Entry) {
				Entry entry = (Entry) instance;
				yamlObject = (YamlProperty.PROPERTY_ROOT.equals(entry.getKey())) ? new YamlObject(entry.getValue()) : new YamlObject(instance);
			}
			else {
				yamlObject = new YamlObject(instance);
			}
			return ownsYamlObject(yamlContent, yamlObject);
		}
	}
	
	private static boolean ownsYamlObjects(Object yamlContent, List yamlObjects) {
		for (Object yamlObject : yamlObjects) {
			boolean isOwnYamlObject = (yamlObject instanceof List) ? ownsYamlObjects(yamlContent, (List)yamlObject) : ownsYamlObject(yamlContent, new YamlObject(yamlObject));
			if (!isOwnYamlObject) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean ownsYamlObject(Object yamlContent, YamlObject yamlObject) {
		findYamlObject(yamlContent, yamlObject);
		return yamlObject.isFound();
	}
	
	private static void findYamlObject(Object yamlContent, YamlObject yamlObject) {
		if (Objects.equals(yamlContent, yamlObject.getValue())) {
			yamlObject.setIsFound(true);
		}
		else {
			if (yamlContent instanceof List) {
				findYamlObject((List)yamlContent, yamlObject);
			}
			else if (yamlContent instanceof Map) {
				findYamlObject((Map)yamlContent, yamlObject);
			}
		}
	}
	
	private static void findYamlObject(List listNode, YamlObject yamlObject) {
		for (Object node: listNode) {
			if (!yamlObject.isFound()) {
				findYamlObject(node, yamlObject);	
			}
			else {
				break;
			}
		}
	}
	
	private static void findYamlObject(Map mappingNode, YamlObject yamlObject) {
		Set<Entry> entries = (Set<Entry>) mappingNode.entrySet();	
		for (Entry entry: entries) {
			if (!yamlObject.isFound()) {
				if (entry.equals(yamlObject.getValue())) {
					yamlObject.setParentNode(mappingNode);
				}
				else {
					findYamlObject(entry.getValue(), yamlObject);
				}
			}
			else {
				break;
			}
		}
	}
	
	private static boolean isCreatedYamlObject(List<Entry> createdNodes, YamlObject yamlObject) {
		for (Entry createdNode : createdNodes) {
			if (createdNode.equals(yamlObject.getValue())) {
				return true;
			}
			else {
				boolean isCreatedYamlObject = ownsYamlObject(createdNode.getValue(), yamlObject);	
				if (isCreatedYamlObject) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static String getPrefixOfType(YamlNodeType yamlNodeType) {
		String prefix;
		switch(yamlNodeType) {
			case ScalarNode:
				prefix = String.valueOf(YamlProperty.PROPERTY_SCALAR);
				break;
				
			case MappingNode:
				prefix = String.valueOf(YamlProperty.PROPERTY_MAPPING);
				break;
				
			case ListNode:
				prefix = String.valueOf(YamlProperty.PROPERTY_LIST);
				break;
				
			default:
				prefix = null;
		}
		return prefix + YamlProperty.PROPERTY_SEPARATOR;
	}
	
	public static String getTypeNameOf(Entry node) {
		YamlNodeType yamlNodeType = getNodeType(node);
		return getPrefixOfType(yamlNodeType);
	}
	
	public static Object getYamlContent(File file) throws FileNotFoundException, IOException {
		Object yamlContent = null;
		Yaml yaml = new Yaml();		    	
    	try(InputStream inputStream = new FileInputStream(file)) {
        	yamlContent = yaml.load(inputStream);
    	}
    	return yamlContent;
	}
	
	public static Object getYamlContent(String yamlContent) {	
		Yaml yaml = new Yaml();
    	return yaml.load(yamlContent);
	}
	
	public static void storeYamlContent(File file, Object yamlContent) throws IOException {
		try(FileWriter writer = new FileWriter(file)) {			
			DumperOptions dumperOptions = new DumperOptions();
			dumperOptions.setPrettyFlow(true);
			dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
			Yaml yaml = new Yaml(dumperOptions);
			yaml.dump(yamlContent, writer);			 
		}
	}
	
	public static Entry getRootNode(Object yamlContent) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put(YamlProperty.PROPERTY_ROOT, yamlContent);
		return map.entrySet().iterator().next();	
	}
}