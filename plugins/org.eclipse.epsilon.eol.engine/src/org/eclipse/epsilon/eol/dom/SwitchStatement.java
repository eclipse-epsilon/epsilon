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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.Continue;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.parse.EolParser;

public class SwitchStatement extends Statement {
	
	protected Expression conditionExpression;
	protected List<Case> cases = new ArrayList<>();
	protected Case _default;
	
	public SwitchStatement() {}
	
	public SwitchStatement(Expression conditionExpression, List<Case> cases, Case _default) {
		this.conditionExpression = conditionExpression;
		this.cases = cases;
		this._default = _default;
	}

	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		for (AST child : cst.getChildren()) {
			
			ModuleElement moduleElement = module.createAst(child, this);
			
			if (child.getType() == EolParser.DEFAULT) {
				_default = (Case) moduleElement;
			}
			else if (child.getType() == EolParser.CASE) {
				cases.add((Case) moduleElement);
			}
			else if (moduleElement instanceof Expression) {
				conditionExpression = (Expression) moduleElement;
			}
		}
	}
	
	public List<Case> getCases() {
		return cases;
	}
	
	public Case getDefault() {
		return _default;
	}
	
	public Expression getConditionExpression() {
		return conditionExpression;
	}
	
	public void setConditionExpression(Expression conditionExpression) {
		this.conditionExpression = conditionExpression;
	}
	
	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		ExecutorFactory executorFactory = context.getExecutorFactory();
		FrameStack frameStack = context.getFrameStack();
		Object switchValue = executorFactory.execute(conditionExpression, context);
		
		boolean continue_ = false;
		
		for (Case c : cases) {
			frameStack.enterLocal(FrameType.UNPROTECTED, c);
			try {
				Object caseValue = executorFactory.execute(c.getCondition(), context);
				
				if (continue_ || Objects.equals(switchValue, caseValue)) {
					Object result = executeCaseBody(c, context, executorFactory);
					if (result instanceof Continue) {
						continue_ = true;
					}
					else {
						return result;
					}
				
				}
			}
			finally {
				frameStack.leaveLocal(c);
			}
		}
		
		return executeCaseBody(getDefault(), context, executorFactory);
	}
	
	private Object executeCaseBody(Case c, IEolContext context, ExecutorFactory executorFactory) throws EolRuntimeException {
		if (c == null) return null;
		Object result = executorFactory.execute(c.getBody(), context);
		return result instanceof Return || result instanceof Continue ? result : null;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
