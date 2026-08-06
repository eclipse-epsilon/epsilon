/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.etl.trace;

import java.util.Collection;

import org.eclipse.epsilon.etl.dom.TransformationRule;

public class Transformation {
	
	protected Object source;
	protected Collection<Object> targets;
	protected TransformationRule rule;
	
	public Transformation() {
	}
	
	public Transformation(Object source, Collection<Object> targets) {
		this.source = source;
		this.targets = targets;
	}
	
	public Transformation(Object source, Collection<Object> targets, TransformationRule rule) {
		this(source, targets);
		this.rule = rule;
	}
	
	public Object getSource() {
		return source;
	}
	
	public void setSource(Object source) {
		this.source = source;
	}
	
	public Collection<Object> getTargets() {
		return targets;
	}

	public void setTargets(Collection<Object> targets) {
		this.targets = targets;
	}

	public boolean of(Object source) {
		return this.source == source;
	}

	public TransformationRule getRule() {
		return rule;
	}

	public void setRule(TransformationRule rule) {
		this.rule = rule;
	}
	
}
