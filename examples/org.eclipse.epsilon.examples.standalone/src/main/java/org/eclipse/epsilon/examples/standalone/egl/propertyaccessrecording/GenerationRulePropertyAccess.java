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

public class GenerationRulePropertyAccess extends PropertyAccess {

	protected GenerationRule rule;
	protected Object element;

	public GenerationRulePropertyAccess(Object modelElement, String propertyName, GenerationRule rule, Object element) {
		super(modelElement, propertyName);
		this.rule = rule;
		this.element = element;
	}

	public GenerationRule getRule() {
		return rule;
	}

	public Object getElement() {
		return element;
	}

}