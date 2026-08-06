/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eml;

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
import org.eclipse.epsilon.eml.debug.EmlDebugger;
import org.eclipse.epsilon.eml.dom.EquivalentAssignmentStatement;
import org.eclipse.epsilon.eml.dom.MergeRule;
import org.eclipse.epsilon.eml.execute.context.EmlContext;
import org.eclipse.epsilon.eml.execute.context.IEmlContext;
import org.eclipse.epsilon.eml.execute.operations.EmlOperationFactory;
import org.eclipse.epsilon.eml.parse.EmlLexer;
import org.eclipse.epsilon.eml.parse.EmlParser;
import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.eol.dom.Import;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.erl.dom.NamedRuleList;
import org.eclipse.epsilon.etl.EtlModule;

public class EmlModule extends EtlModule implements IEmlModule {
	
	protected NamedRuleList<MergeRule> declaredMergeRules = new NamedRuleList<>();
	protected NamedRuleList<MergeRule> mergeRules;
	
	public EmlModule() {
		this(null);
	}
	
	/**
	 * Instantiates the module with the specified execution context.
	 * 
	 * @param context The execution context
	 * @since 1.6
	 */
	public EmlModule(IEmlContext context) {
		super(context != null ? context : new EmlContext());
	}
	
	@Override
	protected Lexer createLexer(ANTLRInputStream inputStream) {
		return new EmlLexer(inputStream);
	}

	@Override
	public EpsilonParser createParser(TokenStream tokenStream) {
		return new EmlParser(tokenStream);
	}

	@Override
	public String getMainRule() {
		return "emlModule";
	}

	@Override
	public HashMap<String, Class<? extends IModule>> getImportConfiguration() {
		HashMap<String, Class<? extends IModule>> importConfiguration = super.getImportConfiguration();
		importConfiguration.put("eml", EmlModule.class);
		return importConfiguration;
	}

	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		// Parse the merge rules
		for (AST mergeRuleAst : AstUtil.getChildren(cst, EmlParser.MERGE)) {
			declaredMergeRules.add((MergeRule) module.createAst(mergeRuleAst, this));
		}
	}
	
	@Override
	protected void prepareContext() throws EolRuntimeException {
		super.prepareContext();
		IEmlContext context = getContext();
		context.setOperationFactory(new EmlOperationFactory());
		context.getFrameStack().put(
			Variable.createReadOnlyVariable("matchTrace", context.getMatchTrace()),
			Variable.createReadOnlyVariable("mergeTrace", context.getMergeTrace()),
			Variable.createReadOnlyVariable("transTrace", context.getTransformationTrace()),
			Variable.createReadOnlyVariable("context", context),
			Variable.createReadOnlyVariable("thisModule", this)
		);
	}
	
	/**
	 * Main execution logic.
	 * 
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	@Override
	protected Object processRules() throws EolRuntimeException {
		IEmlContext context = getContext();
		context.getMergingStrategy().mergeModels(context);
		return null;
	}
	
	@Override
	public IEmlContext getContext() {
		return (IEmlContext) super.getContext();
	}
	
	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		if (cst.getType() == EmlParser.MERGE) {
			return new MergeRule();
		}
		else if (cst.getType() == EmlParser.SPECIAL_ASSIGNMENT) {
			return new EquivalentAssignmentStatement();
		}
		return super.adapt(cst, parentAst);
	}
	
	@Override
	public List<MergeRule> getDeclaredMergeRules(){
		return declaredMergeRules;
	}
	
	@Override
	public List<MergeRule> getMergeRules() {
		if (mergeRules == null) {
			mergeRules = new NamedRuleList<>();
			for (Import import_ : imports) {
				if (import_.isLoaded() && (import_.getModule() instanceof EmlModule)) {
					IEmlModule module = (IEmlModule) import_.getModule();
					mergeRules.addAll(module.getMergeRules());
				}
			}
			mergeRules.addAll(declaredMergeRules);
		}
		return mergeRules;
	}

	@Override
	public EolDebugger createDebugger() {
		return new EmlDebugger();
	}
	
}
