/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eml.dom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.ecl.trace.Match;
import org.eclipse.epsilon.eml.execute.context.IEmlContext;
import org.eclipse.epsilon.eml.parse.EmlParser;
import org.eclipse.epsilon.eml.trace.MergeTrace;
import org.eclipse.epsilon.eml.trace.Merges;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.dom.StatementBlock;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolType;
import org.eclipse.epsilon.erl.dom.ExtensibleNamedRule;

public class MergeRule extends ExtensibleNamedRule {
	
	protected ExecutableBlock<Boolean> guardBlock;
	protected StatementBlock bodyBlock;
	protected Parameter leftParameter, rightParameter;
	protected List<Parameter> targetParameters = new ArrayList<>();
	protected boolean auto = false;
	
	public MergeRule() { }
	
	@SuppressWarnings("unchecked")
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		
		this.guardBlock = (ExecutableBlock<Boolean>) module.createAst(AstUtil.getChild(cst, EmlParser.GUARD), this);
		this.bodyBlock = (StatementBlock) module.createAst(AstUtil.getChild(cst, EmlParser.BLOCK), this);
		
		//Parse the formal parameters
		leftParameter = (Parameter) module.createAst(cst.getSecondChild(), this);
		rightParameter = (Parameter) module.createAst(cst.getThirdChild(), this);
		
		for (AST mergedParameterAst : cst.getFourthChild().getChildren()) {
			targetParameters.add((Parameter) module.createAst(mergedParameterAst, this));
		}
		
	}
	
	public boolean isPrimary(IEmlContext context) throws EolRuntimeException {
		return getBooleanAnnotationValue("primary", context);
	}
	
	public boolean appliesTo(Match match, IEmlContext context) throws EolRuntimeException{
		
		if (hasMerged(match)) return true;
		
		Object left = match.getLeft();
		Object right = match.getRight();
		
		boolean appliesToTypes = getAllInstances(leftParameter, context, !isGreedy(context)).contains(left) && 
				getAllInstances(rightParameter, context, !isGreedy(context)).contains(right);
	
		boolean guardSatisfied = true;
		
		if (appliesToTypes && guardBlock != null) {
			
			guardSatisfied = guardBlock.execute(context, 
				Variable.createReadOnlyVariable(leftParameter.getName(), left), 
				Variable.createReadOnlyVariable(rightParameter.getName(), right),
				Variable.createReadOnlyVariable("self", this));
		}
		
		return appliesToTypes && guardSatisfied;
	}
	

	public Collection<?> merge(Match match, Collection<Object> targets, IEmlContext context) throws EolRuntimeException {
		
		MergeTrace mergeTrace =(context).getMergeTrace();
		Merges merges = mergeTrace.getMerges(match, this);
		
		if (!merges.isEmpty()) return merges.getTargets();
		
		executeSuperRulesAndBody(match,targets,context);
		
		return targets;
	}

	
	public boolean hasMerged(Match match) {
		return mergedMatches.contains(match);
	}
	
	HashSet<Match> mergedMatches = new HashSet<>();
	
	public Collection<?> merge(Match match, IEmlContext context) throws EolRuntimeException{
		
		MergeTrace mergeTrace = context.getMergeTrace();
		
		if (hasMerged(match)) {
			return mergeTrace.getMerges(match, this).getTargets();
		}
		else {
			mergedMatches.add(match);
		}
		
		Collection<Object> targets = CollectionUtil.createDefaultList();
		
		for (Parameter targetParameter : targetParameters) {
			EolType targetParameterType = targetParameter.getType(context);
			targets.add(targetParameterType.createInstance());
		}

		mergeTrace.add(match, targets, this);
	
		executeSuperRulesAndBody(match, targets, context);
		
		return targets;
	}
		
	@Override
	public String toString() {
		return
			getName() + " (" +
			leftParameter.getTypeName() + ", " +
			rightParameter.getTypeName() + ") : " +
			targetParameters.stream()
				.map(Parameter::getTypeName)
				.collect(Collectors.joining(", "));
	}
	
	public void executeSuperRulesAndBody(Match match, Collection<Object> targets, IEmlContext context) throws EolRuntimeException {		
		// Execute the super rules
		for (ExtensibleNamedRule superRule : superRules){
			((MergeRule) superRule).merge(match, targets, context);
		}
		
		FrameStack scope = context.getFrameStack();
		
		scope.enterLocal(FrameType.PROTECTED, this);
		scope.put(
			new Variable(leftParameter.getName(), match.getLeft(), leftParameter.getType(context), true),
			new Variable(rightParameter.getName(), match.getRight(), rightParameter.getType(context), true),
			Variable.createReadOnlyVariable("self", this)
		);
		
		assert targets.size() == targetParameters.size();
		Iterator<?> targetsIter = targets.iterator();
		
		for (Parameter targetParameter : targetParameters) {
			scope.put(new Variable(targetParameter.getName(), targetsIter.next(), targetParameter.getType(context), true));
		}
		
		ExecutorFactory executorFactory = context.getExecutorFactory();
		executorFactory.execute(bodyBlock, context);
		
		scope.leaveLocal(this);	
	}

	@Override
	public AST getSuperRulesAst(AST cst) {
		return AstUtil.getChild(cst, EmlParser.EXTENDS);
	}
	
}
