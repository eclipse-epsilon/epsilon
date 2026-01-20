/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Horacio Hoyos Rodriguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecore.delegates;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.TokenStream;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParser;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.ecore.delegates.execution.EvlConstraint;
import org.eclipse.epsilon.ecore.delegates.notify.DelegateResourceAdapter;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.dom.ConstraintContext;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.evl.parse.EvlLexer;
import org.eclipse.epsilon.evl.parse.EvlParser;

/**
 * A context for Evl
 * 
 * @since 2.5
 */
public class EvlDelegateContext extends EpsilonDelegateContext<EvlModule, Optional<UnsatisfiedConstraint>> implements DelegateContext {

	public EvlDelegateContext(
		DelegateUri delegateURI,
		EPackage ePackage,
		DelegateResourceAdapter delegateRA) {
		super(delegateURI, ePackage, delegateRA, new EvlModule(), "evlModule");
	}
	
	@Override
	protected EpsilonParser createParser(TokenStream stream) {
		return new EvlParser(stream);
	}
	
	@Override
	protected EvlLexer createLexer(ANTLRInputStream inputStream) throws IOException {
		return new EvlLexer(inputStream);
	}

	@Override
	protected EvlConstraint program(List<ParseProblem> parseProblems) {
		if (this.module.getDeclaredConstraintContexts().isEmpty()) {
			return  program().error(new IllegalStateException("Error parsing EVL, context is malformed."));
		};
		ConstraintContext contexts = this.module.getDeclaredConstraintContexts().get(0);
		if (contexts.getConstraints().isEmpty()) {
			return  program().error(new IllegalStateException("Error parsing EVL, constraint is malformed."));
		}
		return new EvlConstraint(contexts.getConstraints().get(0), parseProblems, this.module.getContext());
	}

	@Override
	protected void preParse() {
		this.module.getDeclaredConstraintContexts().clear();
	}
	
	@Override
	protected EvlConstraint program() {
		return new EvlConstraint();
	}
	
	@Override
	protected void createAst(AST cst) {
		if (cst == null)
			return;
		ModuleElement moduleElement = this.module.adapt(cst, null);
		if (moduleElement != null) {
			moduleElement.setUri(cst.getUri());
			moduleElement.setModule(cst.getModule());
			moduleElement.setRegion(cst.getRegion());
			moduleElement.build(cst, cst.getModule());
		}

	}
}
