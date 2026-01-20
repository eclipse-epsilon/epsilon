/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.etl;

import java.util.HashMap;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.TokenStream;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParser;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.Import;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.erl.ErlModule;
import org.eclipse.epsilon.erl.dom.NamedRuleList;
import org.eclipse.epsilon.etl.debug.EtlDebugger;
import org.eclipse.epsilon.etl.dom.EquivalentAssignmentStatement;
import org.eclipse.epsilon.etl.dom.TransformationRule;
import org.eclipse.epsilon.etl.execute.context.EtlContext;
import org.eclipse.epsilon.etl.execute.context.IEtlContext;
import org.eclipse.epsilon.etl.execute.operations.EtlOperationFactory;
import org.eclipse.epsilon.etl.parse.EtlLexer;
import org.eclipse.epsilon.etl.parse.EtlParser;
import org.eclipse.epsilon.etl.strategy.DefaultTransformationStrategy;
import org.eclipse.epsilon.etl.strategy.FastTransformationStrategy;
import org.eclipse.epsilon.etl.strategy.ITransformationStrategy;

public class EtlModule extends ErlModule implements IEtlModule {
	
	protected NamedRuleList<TransformationRule> declaredTransformationRules = new NamedRuleList<>();
	protected NamedRuleList<TransformationRule> transformationRules;
	
	public EtlModule() {
		this(null);
	}
	
	/**
	 * Instantiates the module with the specified execution context.
	 * 
	 * @param context The execution context
	 * @since 1.6
	 */
	public EtlModule(IEtlContext context) {
		super(context != null ? context : new EtlContext());
	}
	
	@Override
	protected Lexer createLexer(ANTLRInputStream inputStream) {
		return new EtlLexer(inputStream);
	}

	@Override
	public EpsilonParser createParser(TokenStream tokenStream) {
		return new EtlParser(tokenStream);
	}

	@Override
	public String getMainRule() {
		return "etlModule";
	}
	
	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		if (cst.getType() == EtlParser.TRANSFORM) {
			return new TransformationRule();
		}
		else if (cst.getType() == EtlParser.GUARD) {
			return new ExecutableBlock<>(Boolean.class);
		}
		else if (cst.getType() == EtlParser.BLOCK && cst.getParent() != null && cst.getParent().getType() == EtlParser.TRANSFORM) {
			return new ExecutableBlock<>(Void.class);
		}
		else if (cst.getType() == EtlParser.SPECIAL_ASSIGNMENT) {
			return new EquivalentAssignmentStatement();
		}
		else {
			return super.adapt(cst, parentAst);
		}
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		
		List<AST> transformationChildrenAsts = AstUtil.getChildren(cst, EtlParser.TRANSFORM);
		declaredTransformationRules.ensureCapacity(transformationChildrenAsts.size());
		
		// Parse the transform rules
		for (AST transformationRuleAst : transformationChildrenAsts) {
			declaredTransformationRules.add((TransformationRule) module.createAst(transformationRuleAst, this));
		}

		getParseProblems().addAll(calculateSuperRules(getTransformationRules()));
	}
	
	@Override
	protected void prepareContext() throws EolRuntimeException {
		super.prepareContext();
		IEtlContext context = getContext();
		
		context.getFrameStack().put(
			Variable.createReadOnlyVariable("transTrace", context.getTransformationTrace()),
			Variable.createReadOnlyVariable("context", context),
			Variable.createReadOnlyVariable("module", this)
		);
		
		context.setOperationFactory(new EtlOperationFactory());
		
		if (context.getTransformationStrategy() == null) {
			ITransformationStrategy transformStrat = hasLazyRules() ? 
				new DefaultTransformationStrategy() : new FastTransformationStrategy();
			
			context.setTransformationStrategy(transformStrat);
		}
	}
	
	/**
	 * Main execution logic.
	 * 
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	@Override
	protected Object processRules() throws EolRuntimeException {
		IEtlContext context = getContext();
		ITransformationStrategy transformStrat = context.getTransformationStrategy();
		if (transformStrat != null) {
			transformStrat.transformModels(context);
		}
		return context.getTransformationTrace();
	}
	
	@Override
	public HashMap<String, Class<? extends IModule>> getImportConfiguration() {
		HashMap<String, Class<? extends IModule>> importConfiguration = super.getImportConfiguration();
		importConfiguration.put("etl", EtlModule.class);
		return importConfiguration;
	}

	@Override
	public IEtlContext getContext() {
		return (IEtlContext) super.getContext();
	}
	
	@Override
	public List<TransformationRule> getDeclaredTransformationRules() {
		return declaredTransformationRules;
	}

	protected boolean hasLazyRules() throws EolRuntimeException {
		IEtlContext context = getContext();
		for (TransformationRule rule : getTransformationRules()) {
			if (rule.isLazy(context)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<TransformationRule> getTransformationRules() {
		if (transformationRules == null) {
			transformationRules = new NamedRuleList<>();
			for (Import import_ : imports) {
				if (import_.isLoaded() && (import_.getModule() instanceof IEtlModule)) {
					IEtlModule module = (IEtlModule) import_.getModule();
					transformationRules.addAll(module.getTransformationRules());
				}
			}
			transformationRules.addAll(declaredTransformationRules);
		}
		return transformationRules;
	}

	@Override
	public EolDebugger createDebugger() {
		return new EtlDebugger();
	}

	
}
