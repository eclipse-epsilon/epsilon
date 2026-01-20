/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecl.trace;

import java.util.Objects;

import org.eclipse.epsilon.ecl.dom.MatchRule;
import org.eclipse.epsilon.eol.types.EolMap;

public class Match {

	/**
	 * The left object of the match
	 */
	protected Object left;
	
	/**
	 * The right object of the match
	 */
	protected Object right;
	
	/**
	 * The result of the ECL match rule
	 */
	protected boolean matching;
	
	/**
	 * The ECL match rule that created the match
	 */
	protected MatchRule rule;
	
	/**
	 * If the match is user-specified or has
	 * been calculated by ECL
	 */
	protected boolean userSpecified = false;
	
	/**
	 * Additional info that the user can attach
	 * in the do part of the match rule
	 */
	protected EolMap<?, ?> info = new EolMap<>();
	
	public Match() {
		matching = false;
	}
	
	public Match(Object left, Object right, boolean matching, MatchRule rule) {
		this.left = left;
		this.right = right;
		this.matching = matching;
		this.rule = rule;
	}
	
	public MatchRule getRule() {
		return rule;
	}

	public void setRule(MatchRule rule) {
		this.rule = rule;
	}
	
	public boolean isMatching() {
		return matching;
	}
	
	public void setMatching(boolean matching) {
		this.matching = matching;
	}
	
	public Object getLeft() {
		return left;
	}
	
	public void setLeft(Object left) {
		this.left = left;
	}
	
	public Object getRight() {
		return right;
	}
	
	public void setRight(Object right) {
		this.right = right;
	}
	
	public boolean contains(Object left, Object right) {
		return this.left == left && this.right == right;
	}
	
	public boolean contains(Object object) {
		return this.left == object || this.right == object;
	}
	
	@Override
	public String toString() {
		return Objects.toString(left, "") + " <-> " + Objects.toString(right, "");
	}
	
	public boolean isUserSpecified() {
		return userSpecified;
	}

	public void setUserSpecified(boolean userSpecified) {
		this.userSpecified = userSpecified;
	}

	public EolMap<?, ?> getInfo() {
		return info;
	}

	public void setInfo(EolMap<?, ?> info) {
		this.info = info;
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public int hashCode() {
		return Objects.hash(matching, userSpecified, rule, left, right);
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Match)) return false;
		Match other = (Match) obj;
		
		return
			Objects.equals(this.matching, other.matching) &&
			Objects.equals(this.userSpecified, other.userSpecified) &&
			Objects.equals(this.rule, other.rule) &&
			Objects.equals(this.left, other.left) &&
			Objects.equals(this.right, other.right);
	}
}
