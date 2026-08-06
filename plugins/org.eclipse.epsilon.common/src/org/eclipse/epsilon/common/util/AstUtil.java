/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.common.parse.AST;

public class AstUtil {
	
	public static int getChildrenCount(AST parent) {
		int count = 0;
		if (parent == null) return count;
		AST child = parent.getFirstChild();
		while (child != null) {
			count++;
			child = child.getNextSibling();
		}
		return count;
	}
	
	public static AST getChildAt(AST parent, int index) {
		if (parent == null) return null;
		int count = 0;
		AST child = parent.getFirstChild();
		while (child != null) {
			if (count == index) {
				return child;
			}
			else {
				child = child.getNextSibling();
				count++;
			}
		}
		return null;
	}
	
	public static List<AST> getChildren(AST parent) {
		return getChildren(parent, -1);
	}
	
	public static List<AST> getChildrenBut(AST parent, int type) {
		List<AST> children = new ArrayList<>();
		if (parent == null) return children;
		for (AST child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
			if (!(child.getType() == type)) {
				children.add(child);
			}
		}
		return children;
	}
	
	public static List<AST> getChildren(AST parent, int... types) {
		List<AST> children = new ArrayList<>();
		if (parent == null || types == null || types.length == 0) return children;
		for (AST child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
			for (int type : types) {
				if (type == -1 || child.getType() == type) {
					children.add(child);
				}
			}
		}
		return children;
	}
	
	/**
	 * 
	 * @param n
	 * @param parent
	 * @param type
	 * @return
	 */
	public static boolean hasAtMostNChildrenOfTypes(int n, AST parent, int... types) {
		if (parent == null || types == null || types.length == 0) return true;
		int count = 0;
		for (AST child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
			for (int type : types) {
				if ((type == -1 || child.getType() == type) && ++count > n) {
					return false;
				}
			}
		}
		return count <= n;
	}
	
	public static AST getChild(AST parent, int type) {
		if (parent == null) return null;
		for (AST child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
			if (child.getType() == type) {
				return child;
			}
		}
		return null;
	}
	
	public static AST getFirstConcreteChild(AST parent) {
		if (parent.getLine() > 0) {
			return parent;
		}
		else {
			AST child = parent.getFirstChild();
			while (child != null) {
				AST result = getFirstConcreteChild(child);
				if (result != null) {
					return result;
				}
			}
			return null;
		}
	}
	
	public static int getParentType(AST child) {
		AST parent = child.getParent();
		return parent != null ? parent.getType() : -1;
	}
}
