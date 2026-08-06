/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.etl.dom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolModelElementType;
import org.eclipse.epsilon.eol.types.EolType;
import org.eclipse.epsilon.erl.dom.ExtensibleNamedRule;
import org.eclipse.epsilon.etl.execute.context.IEtlContext;
import org.eclipse.epsilon.etl.parse.EtlParser;

public class TransformationRule extends ExtensibleNamedRule {
	
	protected Parameter sourceParameter;

	protected List<Parameter> targetParameters = new ArrayList<>(2);
	protected List<Optional<Expression>> targetParameterInitializers = new ArrayList<>(2);

	protected ExecutableBlock<Boolean> guard;
	protected ExecutableBlock<Void> body;
	protected IEtlContext context;
	protected Boolean isPrimary = null;
	
	@Override
	public AST getSuperRulesAst(AST cst) {
		return AstUtil.getChild(cst, EtlParser.EXTENDS);
	}
	
	public Parameter getSourceParameter() {
		return sourceParameter;
	}
	
	public void setSourceParameter(Parameter sourceParameter) {
		this.sourceParameter = sourceParameter;
	}
	
	public ExecutableBlock<Void> getBody() {
		return body;
	}
	
	public void setBody(ExecutableBlock<Void> body) {
		this.body = body;
	}
	
	public ExecutableBlock<Boolean> getGuard() {
		return guard;
	}
	
	public void setGuard(ExecutableBlock<Boolean> guard) {
		this.guard = guard;
	}
	
	public List<Parameter> getTargetParameters() {
		return targetParameters;
	}

	public List<Optional<Expression>> getTargetParameterInitializers() {
		return targetParameterInitializers;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		
		this.guard = (ExecutableBlock<Boolean>) module.createAst(AstUtil.getChild(cst, EtlParser.GUARD), this);
		this.body = (ExecutableBlock<Void>) module.createAst(AstUtil.getChild(cst, EtlParser.BLOCK), this);	
		
		//Parse the formal parameters
		AST sourceParameterAst = cst.getFirstChild().getNextSibling();;
		sourceParameter = (Parameter) module.createAst(sourceParameterAst, this);
		
		AST targetParametersAst = sourceParameterAst.getNextSibling();
		CollectionUtil.addCapacityIfArrayList(targetParameters, targetParametersAst.getChildCount());
		
		for (AST targetParameterAst = targetParametersAst.getFirstChild();
			targetParameterAst != null;
			targetParameterAst = targetParameterAst.getNextSibling()
		) {
			// The actual formal parameter
			AST formalParameterAst = targetParameterAst.getFirstChild();
			targetParameters.add((Parameter) module.createAst(formalParameterAst, this));

			// The optional expression
			Expression expr = null;
			if (targetParameterAst.getChildCount() > 1) {
				AST initializerExpression = formalParameterAst.getNextSibling();
				expr = (Expression) module.createAst(initializerExpression, this);
			}
			targetParameterInitializers.add(Optional.ofNullable(expr));
		}
	}
	
	@Override
	public boolean isLazy(IEolContext context) throws EolRuntimeException {
		if (isLazy == null) {
			isLazy = super.isLazy(context)
				|| (!(sourceParameter.getType(context) instanceof EolModelElementType) && !isAbstract(context));
		}
		return isLazy;
	}
	
	public boolean hasTransformed(Object source) {
		return transformedElements.contains(source);
	}
			
	protected Collection<Object> rejected = new HashSet<>();
	
	public boolean appliesTo(Object source, IEtlContext context, boolean asSuperRule) throws EolRuntimeException {
		return appliesTo(source, context, asSuperRule, true);
	}
	
	//TODO: Add support for rejected objects to other languages as well!
	public boolean appliesTo(Object source, IEtlContext context, boolean asSuperRule, boolean checkTypes) throws EolRuntimeException {
		
		if (hasTransformed(source)) return true;
		if (rejected.contains(source)) return false;
		
		boolean appliesToTypes;
		
		if (!checkTypes) {
			appliesToTypes = true;
		}
		else {
			boolean ofTypeOnly = !(isGreedy(context) || asSuperRule);
			EolType type = sourceParameter.getType(context);
			appliesToTypes = ofTypeOnly ? type.isType(source) : type.isKind(source);
		}
		
		boolean guardSatisfied = true;
		
		if (appliesToTypes && guard != null) {
			guardSatisfied = guard.execute(context, 
				Variable.createReadOnlyVariable(sourceParameter.getName(), source), 
				Variable.createReadOnlyVariable("self", this)
			);	
		}
		
		boolean applies = appliesToTypes && guardSatisfied;
		
		for (ExtensibleNamedRule superRule : getSuperRules()) {
			TransformationRule rule = (TransformationRule) superRule;
			if (!rule.appliesTo(source, context, true)) {
				applies = false;
				break;
			}
		}
		
		if (!applies) {
			rejected.add(source);
		}
		
		return applies;
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	public Collection<?> getAllInstances(IEtlContext context) throws EolRuntimeException {
		return getAllInstances(sourceParameter, context, !isGreedy(context));
	}
	
	/**
	 * 
	 * @param context
	 * @param excluded
	 * @param includeLazy Whether to transform lazy rules.
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	public void transformAll(IEtlContext context, Collection<Object> excluded, boolean includeLazy) throws EolRuntimeException {
		Collection<?> all = getAllInstances(context);
		for (Object instance : all) {
			if (shouldBeTransformed(instance, excluded, context, includeLazy)) {
				transform(instance, context);
			}
		}
	}
	
	public Collection<?> transform(Object source, Collection<Object> targets, IEtlContext context) throws EolRuntimeException {
		transformedElements.add(source);
		executeSuperRulesAndBody(source, targets, context);
		return targets;
	}
	
	protected Set<Object> transformedElements = new HashSet<>();
	
	public Collection<?> transform(Object source, IEtlContext context) throws EolRuntimeException {
		if (hasTransformed(source)) {
			return context.getTransformationTrace().getTransformationTargets(source, this);
		}
		else {
			transformedElements.add(source);
		}
		
		Collection<Object> targets = CollectionUtil.createDefaultList();
		for (int i = 0; i < targetParameters.size(); i++) {
			Parameter p = targetParameters.get(i);

			Optional<Expression> init = targetParameterInitializers.get(i);
			if (init.isPresent()) {
				context.getFrameStack().enterLocal(
					FrameType.UNPROTECTED, init.get(),
					Collections.singletonMap(sourceParameter.getName(), source));
				try {
					targets.add(init.get().execute(context));
				} finally {
					context.getFrameStack().leaveLocal(init.get());
				}
			} else {
				EolType targetParameterType = p.getType(context);
				targets.add(targetParameterType.createInstance());
			}
		}
			
		context.getTransformationTrace().add(source, targets, this);
		executeSuperRulesAndBody(source, targets, context);
		return targets;
	}
	
	protected void executeSuperRulesAndBody(Object source, Collection<Object> targets_, IEtlContext context) throws EolRuntimeException {
		
		List<Object> targets = CollectionUtil.asList(targets_);
		// Execute super-rules
		for (ExtensibleNamedRule rule : superRules) {
			TransformationRule superRule = (TransformationRule) rule;
			superRule.transform(source, targets, context);
		}
		
		Variable[] variables = new Variable[targetParameters.size() + 2];
		variables[0] = Variable.createReadOnlyVariable("self", this);
		variables[1] = Variable.createReadOnlyVariable(sourceParameter.getName(), source);
		
		for (int i = 2; i < variables.length; ++i) {
			int offset = i-2;
			Parameter tp = targetParameters.get(offset);
			variables[i] = Variable.createReadOnlyVariable(tp.getName(), targets.get(offset));
		}
		
		body.execute(context, variables);
	}
	
	@Override
	public String toString() {	
		String targetTypes = targetParameters
			.stream()
			.map(Parameter::getTypeName)
			.collect(Collectors.joining(", "));
		
		return getName() + " (" + sourceParameter.getTypeName() + ") : " + targetTypes;
	}
	
	public boolean isPrimary(IEtlContext context) throws EolRuntimeException {
		if (isPrimary == null) {
			isPrimary = getBooleanAnnotationValue("primary", context);
		}
		return isPrimary;
	}
	
	public boolean canTransformExcluded(IEtlContext context) throws EolRuntimeException {
		return getBooleanAnnotationValue("excluded", context, false, true);
	}
	
	public boolean shouldBeTransformed(Object instance, Collection<Object> excluded, IEtlContext context, boolean overrideLazy) throws EolRuntimeException {
		return (overrideLazy || !isLazy(context))
			&& !isAbstract(context)
			&& (excluded == null || !excluded.contains(instance))
			&& appliesTo(instance, context, false);
	}
	
	public void accept(IEtlVisitor visitor) {
		visitor.visit(this);
	}

	
	public void dispose() {
		rejected = null;
		transformedElements = null;
	}
}
