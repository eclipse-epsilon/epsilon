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

import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class ErlRuleNotFoundException extends EolRuntimeException{
	
	public ErlRuleNotFoundException(NameExpression ast) {
		this.ast = ast;
	}
	
	@Override
	public String getReason() {
		return "Rule '" + ((NameExpression) ast).getName() + "' not found";
	}
}
