/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecl.exceptions;

import org.eclipse.epsilon.ecl.dom.MatchRule;
import org.eclipse.epsilon.ecl.execute.context.IEclContext;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class EclNotApplicableSuperRuleException extends EolRuntimeException {
	
	protected MatchRule rule;
	protected Object left, right;
	protected IEclContext context;
	
	public EclNotApplicableSuperRuleException(Object left, Object right, MatchRule rule, IEclContext context) {
		this.rule = rule;
		this.left = left;
		this.right = right;
		this.context = context;
		this.ast = rule;
	}
	
	@Override
	public String getReason() {
		return rule.getName() + " does not apply to objects \r\n" +  
		context.getPrettyPrinterManager().toString(left) + "\r\n" + 
		context.getPrettyPrinterManager().toString(right) + "\r\n";
	}
}
