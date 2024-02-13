/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecl;

import java.util.Collection;
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
import org.eclipse.epsilon.ecl.dom.MatchRule;
import org.eclipse.epsilon.ecl.execute.EclOperationFactory;
import org.eclipse.epsilon.ecl.execute.context.EclContext;
import org.eclipse.epsilon.ecl.execute.context.IEclContext;
import org.eclipse.epsilon.ecl.parse.EclLexer;
import org.eclipse.epsilon.ecl.parse.EclParser;
import org.eclipse.epsilon.ecl.parse.Ecl_EclParserRules.rightDomain_return;
import org.eclipse.epsilon.ecl.trace.MatchTrace;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.Import;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.erl.ErlModule;
import org.eclipse.epsilon.erl.dom.NamedRuleList;

public class EclModule extends ErlModule implements IEclModule {
	
	protected NamedRuleList<MatchRule> matchRules;
	protected final NamedRuleList<MatchRule> declaredMatchRules = new NamedRuleList<>();
	
	public EclModule() {
		this(null);
	}
	
	/**
	 * Instantiates the module with the specified execution context.
	 * 
	 * @param context The execution context
	 * @since 1.6
	 */
	public EclModule(IEclContext context) {
		super(context != null ? context : new EclContext());
	}
	
	@Override
	protected Lexer createLexer(ANTLRInputStream inputStream) {
		return new EclLexer(inputStream);
	}

	@Override
	public EpsilonParser createParser(TokenStream tokenStream) {
		return new EclParser(tokenStream);
	}

	@Override
	public String getMainRule() {
		return "eclModule";
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		
		// Parse the match rules
		Collection<AST> matchChildren = AstUtil.getChildren(cst, EclParser.MATCH);
		declaredMatchRules.ensureCapacity(matchChildren.size());
		for (AST matchRuleAst : matchChildren) {
			declaredMatchRules.add((MatchRule) module.createAst(matchRuleAst, this));
		}
		
		getParseProblems().addAll(calculateSuperRules(getMatchRules()));
	}
	
	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		switch (cst.getType()) {
			case EclParser.MATCH: return new MatchRule();
			case EclParser.GUARD:
			case EclParser.COMPARE:
				return new ExecutableBlock<Boolean>(Boolean.class);
			case EclParser.DO: return new ExecutableBlock<Void>(Void.class);
			case EclParser.LEFTDOMAIN: return new ExecutableBlock<>(Collection.class);
			case EclParser.RIGHTDOMAIN: return new ExecutableBlock<>(Collection.class);
			default: return super.adapt(cst, parentAst);
		}
	}
	
	@Override
	public HashMap<String, Class<? extends IModule>> getImportConfiguration() {
		HashMap<String, Class<? extends IModule>> importConfiguration = super.getImportConfiguration();
		importConfiguration.put("ecl", EclModule.class);
		return importConfiguration;
	}
	
	@Override
	public List<MatchRule> getMatchRules() {
		if (matchRules == null) {
			matchRules = new NamedRuleList<>();
			for (Import import_ : imports) {
				if (import_.isLoaded() && (import_.getModule() instanceof IEclModule)) {
					IEclModule module = (IEclModule) import_.getModule();
					matchRules.addAll(module.getMatchRules());
				}
			}
			matchRules.addAll(declaredMatchRules);
		}
		return matchRules;
	}

	@Override
	protected void prepareContext() throws EolRuntimeException {
		super.prepareContext();
		IEclContext context = getContext();
		context.setOperationFactory(new EclOperationFactory());
		context.getFrameStack().put(
			Variable.createReadOnlyVariable("matchTrace", context.getMatchTrace()),
			Variable.createReadOnlyVariable("context", context),
			Variable.createReadOnlyVariable("self", this)
		);
	}
	
	@Override
	public MatchTrace execute() throws EolRuntimeException {
		return (MatchTrace) super.execute();
	}
	
	@Override
	protected MatchTrace processRules() throws EolRuntimeException {
		matchModels();
		return getContext().getMatchTrace();
	}
	
	@Override
	public void matchModels() throws EolRuntimeException {
		matchAllRules(false);
		matchAllRules(true);
	}

	protected void matchAllRules(boolean greedy) throws EolRuntimeException {
		boolean ofTypeOnly = !greedy;
		IEclContext context = getContext();
		
		for (MatchRule matchRule : getMatchRules()) {
			if (!matchRule.isAbstract(context) && !matchRule.isLazy(context) && (!greedy || matchRule.isGreedy(context))) {
				for (Object left : matchRule.getLeftInstances(context, ofTypeOnly)) {
					if(matchRule.isRightDomainDynamic()) {
					for (Object right : matchRule.getRightInstances(context, ofTypeOnly, left)) {
						matchRule.matchPair(context, ofTypeOnly, left, right);
					}
					}
					else {
						for (Object right : matchRule.getRightInstances(context, ofTypeOnly)) {
							matchRule.matchPair(context, ofTypeOnly, left, right);
						}
					}
				}
			}
		}
	}
	
	@Override
	public IEclContext getContext() {
		return (IEclContext) super.getContext();
	}
	
	@Override
	public List<MatchRule> getDeclaredMatchRules() {
		return declaredMatchRules;
	}
}
