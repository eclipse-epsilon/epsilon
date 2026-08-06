/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.composite;

import java.util.ArrayList;

public class Tree {
	
	protected String label;
	protected ArrayList<Tree> children = new ArrayList<>();
	
	public String toString() {
		return this.label;
	}
	
	public Tree(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public ArrayList<Tree> getChildren() {
		return children;
	}
	
	public Tree createChild(String label) {
		Tree child = new Tree(label);
		this.children.add(child);
		return child;
	}
	
	public Tree getChild(String label) {
		for (Tree child : getAllChildren()) {
			if (child.label.equalsIgnoreCase(label)) return child; 
		}
		return null;
	}
	
	public void createChildren (String... children) {
		for (String child : children) {
			createChild(child);
		}
	}
	
	public ArrayList<Tree> getAllChildren() {
		
		ArrayList<Tree> allChildren = new ArrayList<>();
		
		allChildren.addAll(children);
		
		for (Tree child : children) {
			allChildren.addAll(child.getAllChildren());
		}
		
		return allChildren;
	}
	
}
