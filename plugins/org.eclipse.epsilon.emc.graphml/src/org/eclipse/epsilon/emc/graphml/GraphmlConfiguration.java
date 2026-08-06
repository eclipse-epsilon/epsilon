/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitris Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.graphml;

import org.jdom.Element;

public class GraphmlConfiguration {
	
	protected String nodeTypeKey;
	protected String nodePropertiesKey;
	protected String nodeContentsKey;
	protected String nodePrimarySlotPrototypeNameKey;
	
	protected String edgeTypeKey;
	protected String edgePropertiesKey;
	protected String edgePrimarySlotPrototypeNameKey;
	protected String edgeSourceKey;
	protected String edgeTargetKey;
	protected String edgeRoleInSourceKey;
	protected String edgeRoleInTargetKey;
	
	protected Element root;
	
	public GraphmlConfiguration(Element root) {
		this.root = root;
		
		nodeTypeKey = getKeyId("Type", "node");
		nodePropertiesKey = getKeyId("Properties", "node");
		nodePrimarySlotPrototypeNameKey = getKeyId("Default", "node");
		nodeContentsKey = getKeyId("Contents", "node");
		
		edgeTypeKey = getKeyId("Type", "edge");
		edgePropertiesKey = getKeyId("Properties", "edge");
		edgePrimarySlotPrototypeNameKey = getKeyId("Default", "edge");
		edgeSourceKey = getKeyId("Source", "edge");
		edgeTargetKey = getKeyId("Target", "edge");
		edgeRoleInSourceKey = getKeyId("Role in source", "edge");
		edgeRoleInTargetKey = getKeyId("Role in target", "edge");
	}
	
	protected String getKeyId(String attrName, String forName) {
		for (Element key : DomUtil.getChildren(root, "key")) {
			if (key.getAttributeValue("attr.name","").equalsIgnoreCase(attrName) && key.getAttributeValue("for","").equals(forName)) {
				return key.getAttributeValue("id");
			}
		}
		return null;
	}
	
	public String getNodePropertiesKey() {
		return nodePropertiesKey;
	}
	
	public String getNodeContentsKey() {
		return nodeContentsKey;
	}
	
	public String getEdgePropertiesKey() {
		return edgePropertiesKey;
	}
	
	public String getNodeTypeKey() {
		return nodeTypeKey;
	}
	
	public String getEdgeTypeKey() {
		return edgeTypeKey;
	}
	
	public String getNodePrimarySlotPrototypeNameKey() {
		return nodePrimarySlotPrototypeNameKey;
	}
	
	public String getEdgePrimarySlotPrototypeNameKey() {
		return edgePrimarySlotPrototypeNameKey;
	}
	
	public String getEdgeSourceKey() {
		return edgeSourceKey;
	}
	
	public String getEdgeTargetKey() {
		return edgeTargetKey;
	}
	
	public String getEdgeRoleInSourceKey() {
		return edgeRoleInSourceKey;
	}
	
	public String getEdgeRoleInTargetKey() {
		return edgeRoleInTargetKey;
	}
	
}
