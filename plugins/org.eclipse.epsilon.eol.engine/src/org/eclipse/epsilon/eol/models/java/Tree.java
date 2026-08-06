/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.models.java;

import java.util.ArrayList;
import java.util.Collection;

public class Tree {
	
	protected Tree parent;
	protected Collection<Tree> children = new ArrayList<>();
	protected String label;
	
	public Tree() {
		
	}

	public Tree getParent() {
		return parent;
	}

	public void setParent(Tree parent) {
		if (this.parent != null) {
			this.parent.children.remove(this);
		}
		if (!parent.getChildren().contains(this)) {
			parent.getChildren().add(this);
		}
		this.parent = parent;
	}

	public Collection<Tree> getChildren() {
		return children;
	}

	public void setChildren(Collection<Tree> children) {
		for (Tree c : children) {
			if (c.getParent() != this) {
				c.setParent(this);
			}
		}
		this.children = children;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
}
