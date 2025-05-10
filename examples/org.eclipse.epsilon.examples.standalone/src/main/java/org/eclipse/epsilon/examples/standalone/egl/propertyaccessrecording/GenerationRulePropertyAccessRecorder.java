/*********************************************************************
 * Copyright (c) 2008-2025 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/
package org.eclipse.epsilon.examples.standalone.egl.propertyaccessrecording;

import org.eclipse.epsilon.egl.dom.GenerationRule;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccess;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccessRecorder;

public class GenerationRulePropertyAccessRecorder extends PropertyAccessRecorder {

	protected GenerationRule rule = null;
	protected Object element = null;
	
	public GenerationRulePropertyAccessRecorder() {
		super();
	}
	
	public Object getElement() {
		return element;
	}
	
	public void setElement(Object element) {
		this.element = element;
	}
	
	public void setRule(GenerationRule rule) {
		this.rule = rule;
	}
	
	public GenerationRule getRule() {
		return rule;
	}
	
	@Override
	protected PropertyAccess createPropertyAccess(Object modelElement, String propertyName) {
		return new GenerationRulePropertyAccess(modelElement, propertyName, rule, element);
	}

}