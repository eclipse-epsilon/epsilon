/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eml.trace;

import java.util.Collection;

import org.eclipse.epsilon.ecl.trace.Match;
import org.eclipse.epsilon.eml.dom.MergeRule;

public class Merge {
	
	//protected Object left;
	//protected Object right;
	protected Match match;
	protected Collection<Object> targets;
	protected MergeRule rule;
	

	public Merge(){
		
	}
	
	public Merge(Match match, Collection<Object> merged) {
		super();
		//this.left = left;
		this.match = match;
		this.targets = merged;
		//this.right = right;
	}
	
	//public Object getLeft() {
	///	return left;
	//}
	
	//public void setLeft(Object left) {
	//	this.left = left;
	//}
	
	public Collection<Object> getTargets() {
		return targets;
	}
	
	public void setTargets(Collection<Object> merged) {
		this.targets = merged;
	}
	
	//public Object getRight() {
	//	return right;
	//}

	//public void setRight(Object right) {
	//	this.right = right;
	//}
	
	public MergeRule getRule() {
		return rule;
	}

	public void setRule(MergeRule rule) {
		this.rule = rule;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	//public boolean contains(Object left, Object right){
	//	return (this.left == left && this.right == right) || (this.left == right && this.right == left);
	//}
}
