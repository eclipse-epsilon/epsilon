/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.dom;

import java.util.Collection;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.prettyprinting.PrettyPrinterManager;
import org.eclipse.epsilon.eol.types.EolCollectionType;
import org.eclipse.epsilon.eol.types.NumberUtil;

public class PlusOperatorExpression extends EagerOperatorExpression {
	
	public PlusOperatorExpression() {}
	
	public PlusOperatorExpression(Expression firstOperand, Expression secondOperand) {
		super(firstOperand, secondOperand);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Object o1, Object o2, IEolContext context) throws EolRuntimeException {
		if (o1 instanceof Number && o2 instanceof Number){
			return NumberUtil.add((Number) o1, (Number) o2);
		}
		else if (o1 instanceof Collection && o2 instanceof Collection) {
			return EolCollectionType.join((Collection<Object>) o1, (Collection<Object>) o2);
		}
		
		// If we can do nothing more, we concat their string representations
		PrettyPrinterManager prettyPrinterManager = context.getPrettyPrinterManager();	
		return prettyPrinterManager.print(o1) + prettyPrinterManager.print(o2);
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
