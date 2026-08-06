/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.dt.yaml;

import org.eclipse.jface.text.rules.IToken;

public class YamlProcessingInstructionRule extends YamlMappingRule {
	
	public YamlProcessingInstructionRule(IToken token) {
		super(token);
	}
	
	@Override
	protected boolean isWordStart(char c) {
		return c == '?';
	}
	
}
