/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ewl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.TokenStream;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParser;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.ewl.dom.Wizard;
import org.eclipse.epsilon.ewl.execute.WizardInstance;
import org.eclipse.epsilon.ewl.execute.WizardLoopInstance;
import org.eclipse.epsilon.ewl.execute.context.EwlContext;
import org.eclipse.epsilon.ewl.execute.context.IEwlContext;
import org.eclipse.epsilon.ewl.parse.EwlLexer;
import org.eclipse.epsilon.ewl.parse.EwlParser;

public class EwlModule extends EolModule implements IEwlModule {
	
	protected List<Wizard> wizards = new ArrayList<>();
	
	public EwlModule() {
		this(null);
	}
	
	/**
	 * Instantiates the module with the specified execution context.
	 * 
	 * @param context The execution context
	 * @since 1.6
	 */
	public EwlModule(IEwlContext context) {
		super(context != null ? context : new EwlContext());
	}
	
	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		switch (cst.getType()) {
			case EwlParser.TITLE: return new ExecutableBlock<>(String.class);
			case EwlParser.GUARD: return new ExecutableBlock<>(Boolean.class);
			case EwlParser.DO: return new ExecutableBlock<>(Void.class);
			case EwlParser.WIZARD: return new Wizard();
			default: return super.adapt(cst, parentAst);
		}
	}
	
	@Override
	protected Lexer createLexer(ANTLRInputStream inputStream) {
		return new EwlLexer(inputStream);
	}

	@Override
	public EpsilonParser createParser(TokenStream tokenStream) {
		return new EwlParser(tokenStream);
	}

	@Override
	public String getMainRule() {
		return "ewlModule";
	}

	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		for (AST wizardAst : AstUtil.getChildren(cst,EwlParser.WIZARD)) {
			wizards.add((Wizard) module.createAst(wizardAst, this));
		}
	}
	
	@Override
	public List<WizardInstance> getWizardsFor(Object self) throws EolRuntimeException {
		prepareContext();
		IEwlContext context = getContext();
		
		final List<WizardInstance> applicableWizards = new ArrayList<>();
		
		for (Wizard wizard : wizards) {
			if (wizard.appliesTo(self, context)) {
				applicableWizards.add(new WizardInstance(wizard, self, context));
			}
			else if (self instanceof Collection && !((Collection<?>)self).isEmpty()) {
				// Run the same wizard over a collection of applicable objects

				@SuppressWarnings("unchecked")
				final Collection<Object> collection = (Collection<Object>)self;
				if (allApply(wizard, collection)) {
					applicableWizards.add(new WizardLoopInstance(wizard, collection, context));
				}
			}
		}

		return applicableWizards;
	}
	
	@Override
	public List<Wizard> getWizards() {
		return wizards;
	}
	
	private boolean allApply(Wizard wizard, Collection<Object> self) throws EolRuntimeException {
		IEwlContext context = getContext();
		for (Object o : self) {
			if (!wizard.appliesTo(o, context)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public IEwlContext getContext() {
		return (IEwlContext) super.getContext();
	}
}
