/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - refactoring
 ******************************************************************************/
package org.eclipse.epsilon.erl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.TokenStream;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParser;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dom.Import;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.erl.dom.*;
import org.eclipse.epsilon.erl.exceptions.ErlCircularRuleInheritanceException;
import org.eclipse.epsilon.erl.exceptions.ErlRuleNotFoundException;
import org.eclipse.epsilon.erl.execute.context.ErlContext;
import org.eclipse.epsilon.erl.execute.context.IErlContext;
import org.eclipse.epsilon.erl.parse.ErlLexer;
import org.eclipse.epsilon.erl.parse.ErlParser;

public class ErlModule extends EolModule implements IErlModule {
	
	protected NamedRuleList<Pre> pre;
	protected NamedRuleList<Pre> declaredPre = new NamedRuleList<>();
	protected NamedRuleList<Post> post;
	protected NamedRuleList<Post> declaredPost = new NamedRuleList<>();
	
	public ErlModule() {
		this(null);
	}
	
	/**
	 * Instantiates the module with the specified execution context.
	 * 
	 * @param context The execution context
	 * @since 1.6
	 */
	public ErlModule(IErlContext context) {
		super(context != null ? context : new ErlContext());
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		List<AST>
			preBlockASTs = AstUtil.getChildren(cst, ErlParser.PRE),
			postBlockASTs = AstUtil.getChildren(cst, ErlParser.POST);
		
		declaredPre.ensureCapacity(preBlockASTs.size());
		for (AST preBlockAst : preBlockASTs){
			declaredPre.add((Pre) module.createAst(preBlockAst, this));
		}
		
		declaredPost.ensureCapacity(preBlockASTs.size());
		for (AST postBlockAst : postBlockASTs) {
			declaredPost.add((Post) module.createAst(postBlockAst, this));
		}
	}
	
	@Override
	protected Lexer createLexer(ANTLRInputStream inputStream) {
		return new ErlLexer(inputStream);
	}
	
	@Override
	public EpsilonParser createParser(TokenStream tokenStream) {
		return new ErlParser(tokenStream);
	}
	
	@Override
	public String getMainRule() {
		return "erlModule";
	}

	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		switch (cst.getType()) {
			case ErlParser.PRE:
				return new Pre();
			case ErlParser.POST:
				return new Post();
			default:
				return super.adapt(cst, parentAst);
		}
	}
	
	@Override
	public HashMap<String, Class<? extends IModule>> getImportConfiguration() {
		HashMap<String, Class<? extends IModule>> importConfiguration = super.getImportConfiguration();
		importConfiguration.put("erl", ErlModule.class);
		return importConfiguration;
	}
	
	@Override
	public List<Post> getPost() {
		if (post == null) {
			post = new NamedRuleList<>();
			for (Import import_ : imports) {
				if (import_.isLoaded() && (import_.getModule() instanceof IErlModule)) {
					IErlModule module = (IErlModule) import_.getModule();
					post.addAll(module.getPost());
				}
			}
			post.addAll(declaredPost);
		}
		return post;
	}

	@Override
	public List<Pre> getPre() {
		if (pre == null) {
			pre = new NamedRuleList<>();
			for (Import import_ : imports) {
				if (import_.isLoaded() && (import_.getModule() instanceof IErlModule)) {
					IErlModule module = (IErlModule) import_.getModule();
					pre.addAll(module.getPre());
				}
			}
			pre.addAll(declaredPre);
		}
		return pre;
	}
	
	@Override
	public List<Post> getDeclaredPost() {
		return declaredPost;
	}

	@Override
	public List<Pre> getDeclaredPre() {
		return declaredPre;
	}
	
	public void prepareExecution() throws EolRuntimeException {
		prepareContext();
		execute(getPre(), getContext());
	}
	
	public void postExecution() throws EolRuntimeException {
		execute(getPost(), getContext());
	}
	
	protected void execute(List<? extends NamedStatementBlockRule> namedRules) throws EolRuntimeException {
		execute(namedRules, getContext());
	}
	
	protected void execute(List<? extends NamedStatementBlockRule> namedRules, IEolContext context) throws EolRuntimeException {
		ExecutorFactory executorFactory = context.getExecutorFactory();
		for (NamedStatementBlockRule namedRule : namedRules) {
			executorFactory.execute(namedRule, context);
		}
	}
	
	@Override
	public Object executeImpl() throws EolRuntimeException {
		prepareExecution();
		Object result = processRules();
		postExecution();
		return result;
	}
	
	/**
	 * Main rule processing logic. Non-abstract for compatibility.
	 * 
	 * @return The result of executing this module.
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	protected Object processRules() throws EolRuntimeException {
		return null;
	}

	public List<ParseProblem> calculateSuperRules(List<? extends ExtensibleNamedRule> allRules) {
		List<ParseProblem> parseProblems = new ArrayList<>();
		for (ExtensibleNamedRule rule : allRules) {
			try {
				rule.calculateSuperRules(allRules);
			}
			catch (ErlRuleNotFoundException | ErlCircularRuleInheritanceException e) {
				ParseProblem problem = new ParseProblem();
				problem.setLine(rule.getRegion().getStart().getLine());
				problem.setReason(e.getReason());
				parseProblems.add(problem);
			}
		}
		return parseProblems;
	}
	
	@Override
	public IErlContext getContext() {
		return (IErlContext) super.getContext();
	}
}
