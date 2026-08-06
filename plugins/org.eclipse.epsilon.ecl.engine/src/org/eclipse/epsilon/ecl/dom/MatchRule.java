/*******************************************************************************
 * Copyright (c) 2008-2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - protect against NPE in toString()
 ******************************************************************************/
package org.eclipse.epsilon.ecl.dom;

import java.util.Collection;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.ecl.exceptions.EclNotApplicableSuperRuleException;
import org.eclipse.epsilon.ecl.execute.context.IEclContext;
import org.eclipse.epsilon.ecl.parse.EclParser;
import org.eclipse.epsilon.ecl.trace.Match;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolMap;
import org.eclipse.epsilon.erl.dom.ExtensibleNamedRule;

public class MatchRule extends ExtensibleNamedRule {
	
	protected ExecutableBlock<Boolean> compareBlock;
	protected ExecutableBlock<Boolean> guardBlock;
	protected ExecutableBlock<Void> doBlock;
	protected ExecutableBlock<Collection<?>> leftDomainBlock;
	protected ExecutableBlock<Collection<?>> rightDomainBlock;
	protected Parameter leftParameter, rightParameter;

	@Override
	public AST getSuperRulesAst(AST cst) {
		return AstUtil.getChild(cst, EclParser.EXTENDS);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		this.leftDomainBlock = (ExecutableBlock<Collection<?>>) module.createAst(AstUtil.getChild(cst, EclParser.LEFTDOMAIN), this);
		this.rightDomainBlock = (ExecutableBlock<Collection<?>>) module.createAst(AstUtil.getChild(cst, EclParser.RIGHTDOMAIN), this);
		leftParameter = (Parameter) module.createAst(cst.getSecondChild(), this);
		if(leftDomainBlock == null) {
			rightParameter = (Parameter) module.createAst(cst.getThirdChild(), this);
		}
		else {
			rightParameter = (Parameter) module.createAst(cst.getFourthChild(), this);
		}
		this.compareBlock = (ExecutableBlock<Boolean>) module.createAst(AstUtil.getChild(cst, EclParser.COMPARE), this);
		this.doBlock = (ExecutableBlock<Void>) module.createAst(AstUtil.getChild(cst, EclParser.DO), this);
		this.guardBlock = (ExecutableBlock<Boolean>) module.createAst(AstUtil.getChild(cst, EclParser.GUARD), this);
	}
	
	public boolean appliesTo(Object left, Object right, IEclContext context, boolean ofTypeOnly) throws EolRuntimeException {
		final boolean guardSatisfied, oto = !(!ofTypeOnly || isGreedy(context));
		final boolean appliesToTypes = getAllInstances(leftParameter, context, oto).contains(left) &&
			getAllInstances(rightParameter, context, oto).contains(right);
		
		if (!isAbstract(context) && appliesToTypes && guardBlock != null) {
			guardSatisfied = guardBlock.execute(context, 
				Variable.createReadOnlyVariable(leftParameter.getName(), left),
				Variable.createReadOnlyVariable(rightParameter.getName(), right),
				Variable.createReadOnlyVariable("self", this)
			);
		}
		else guardSatisfied = true;
		
		return appliesToTypes && guardSatisfied;
	}
	
	public Collection<?> getLeftInstances(IEclContext context, boolean ofTypeOnly) throws EolRuntimeException {
		if (leftDomainBlock == null) {
			return getAllInstances(leftParameter, context, ofTypeOnly);
		}
		else {
			return leftDomainBlock.execute(context, true);
		}
	}
	
	public Collection<?> getRightInstances(IEclContext context, boolean ofTypeOnly) throws EolRuntimeException {
		if (rightDomainBlock == null) {
			return getAllInstances(rightParameter, context, ofTypeOnly);
		}
		else { 
			return rightDomainBlock.execute(context, true);
		}
	}
	
	public Collection<?> getRightInstances(IEclContext context, boolean ofTypeOnly, Object left) throws EolRuntimeException {
		if (rightDomainBlock == null) {
			return getAllInstances(rightParameter, context, ofTypeOnly);
		}
		else {
			if(rightDomainBlock.getText().equals("from")) {
				FrameStack scope = context.getFrameStack();
				scope.enterLocal(FrameType.PROTECTED, this);
				scope.put(Variable.createReadOnlyVariable(leftParameter.getName(), left));
			}
			return rightDomainBlock.execute(context, true);
		}
	}
	
	public Match matchPair(IEclContext context, boolean ofTypeOnly, Object leftInstance, Object rightInstance) throws EolRuntimeException {
		if (!ofTypeOnly && context.getMatchTrace().getMatch(leftInstance, rightInstance) != null) {
			return null;
		}
		
		// Try to find a rule with ofTypeOnly = true
		if (appliesTo(leftInstance, rightInstance, context, true)) {
			return match(leftInstance, rightInstance, context, null, false);
		}
		//TODO: Why both branches do same thing?
		// Else find a rule with onlyOfClass = false
		else if (appliesTo(leftInstance, rightInstance, context, false)) {
			return match(leftInstance, rightInstance, context, null, false);
		}
		return null;
	}
	
	/**
	 * Matches left against right
	 * @param left The left object
	 * @param right The right object
	 * @param context The context in which the ECL program is running
	 * @param asSuperRule Shows if the rule is executed as a super-rule of another rule
	 * @param forcedMatch Whether to add the match to the trace (?)
	 * @return
	 * @throws EolRuntimeException
	 */
	//TOOD: Variables defined in the guard should be accessible in the compare and do parts
	public Match match(Object left, Object right, IEclContext context, EolMap<?, ?> matchInfo, boolean forcedMatch) throws EolRuntimeException {
		MatchTrace matchTrace = context.getMatchTrace(), tempMatchTrace = context.getTempMatchTrace();
		Match match = null, tempMatch = null;
		boolean asSuperRule = matchInfo != null;
		 
		if (!asSuperRule) {
			// See if there is a match for left<->right in the temp match trace
			// If there is no match in the temp trace, create one
			// and add it to the temp trace
			// Else, return the temp match
			if ((tempMatch = tempMatchTrace.getMatch(left, right)) == null) {
				tempMatchTrace.add(tempMatch = new Match(left, right, true, this));
			}
			else {
				return tempMatch;
			}
			
			// See if left<->right have been already matched
			if ((match = matchTrace.getMatch(left, right)) != null) {
				tempMatchTrace.remove(tempMatch);
				return match;
			}
		}
		else if (!appliesTo(left, right, context, false)) {
			throw new EclNotApplicableSuperRuleException(left, right, this, context);
		}
		// If they have not been matched, construct their Match
		match = new Match(left, right, true, this);
		
		// Execute all the super-rules
		if (!superRules.isEmpty()) {
			boolean matching = true;
			for (ExtensibleNamedRule rule : superRules) {
				MatchRule matchRule = (MatchRule) rule;
				Match superRuleMatch = matchRule.match(left, right, context, match.getInfo(), false);
				matching = matching && superRuleMatch.isMatching();
			}
			match.setMatching(matching);
			
			if (!matching) {
				tempMatchTrace.remove(tempMatch);
				matchTrace.add(match);
				return match;
			}
		}

		FrameStack scope = context.getFrameStack();
		scope.enterLocal(FrameType.PROTECTED, this);
		
		// Execute the compare part of the rule
		
		EolMap<?, ?> info = asSuperRule ? matchInfo : match.getInfo();
		
		scope.put(
			Variable.createReadOnlyVariable(leftParameter.getName(), left),
			Variable.createReadOnlyVariable(rightParameter.getName(), right),
			Variable.createReadOnlyVariable("matchInfo", info),
			Variable.createReadOnlyVariable("self", this)
		);
		
		if (compareBlock != null) {
			match.setMatching(compareBlock.execute(context, false));
		}
		else if (superRules.isEmpty()) {
			match.setMatching(false);
		}
		
		if (!asSuperRule) {
			// Before exiting remove the temp match
			// we created from the temp match trace
			tempMatchTrace.remove(tempMatch);
			
			// If there are temp matches, it means
			// that the matching decision is based
			// upon rules that may not eventually hold
			// Therefore, in that case we should not
			// add a permanent match
			
			//TODO : See if this affects cyclic dependencies
			if (forcedMatch || tempMatchTrace.isEmpty()) {
				matchTrace.add(match);
			}
		}
		
		// Execute the do part of the rule
		if (doBlock != null && match.isMatching()) {
			doBlock.execute(context, false);
		}
		
		scope.leaveLocal(this);

		return match;
	}

	@Override
	public String toString() {
		return String.format("%s (%s, %s)", getName(),
			leftParameter == null ? "(no left)" : leftParameter.getTypeName(),
			rightParameter == null ? "(no right)" : rightParameter.getTypeName());
	}
	
	public boolean isRightDomainDynamic() {
		return rightDomainBlock!=null && rightDomainBlock.getText().equals("from");
	}
	
	public ExecutableBlock<Boolean> getCompareBlock(){
		return compareBlock;
	}
	
	public ExecutableBlock<Collection<?>> getLeftDomainBlock(){
		return leftDomainBlock;
	}
	
	public ExecutableBlock<Collection<?>> getRightDomainBlock(){
		return rightDomainBlock;
	}
	
	public Parameter getLeftParameter(){
		return leftParameter;
	}
	
	public Parameter getRightParameter(){
		return rightParameter;
	}
	
	public ExecutableBlock<Boolean> getGuardBlock() {
		return guardBlock;
	}
	
	public ExecutableBlock<Void> getDoBlock() {
		return doBlock;
	}
	
	public void accept(IEclVisitor visitor) {
		visitor.visit(this);
	}
}
