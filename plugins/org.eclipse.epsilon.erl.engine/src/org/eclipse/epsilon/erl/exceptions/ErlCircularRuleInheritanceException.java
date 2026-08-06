/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.erl.exceptions;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.erl.dom.ExtensibleNamedRule;

public class ErlCircularRuleInheritanceException extends EolRuntimeException {
	
	protected ExtensibleNamedRule rule;
	
	public ErlCircularRuleInheritanceException(ExtensibleNamedRule rule) {
		this.rule = rule;
	}
	
	@Override
	public String getReason() {
		return "Circular extension detected in rule '" + rule.getName() + "'"; 
	}
	
	@Override
	public ModuleElement getAst() {
		return rule;
	}
}
