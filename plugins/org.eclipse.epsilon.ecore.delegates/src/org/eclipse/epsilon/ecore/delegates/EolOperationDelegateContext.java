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

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.TokenStream;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParser;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.ecore.delegates.execution.EolOperation;
import org.eclipse.epsilon.ecore.delegates.notify.DelegateResourceAdapter;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.parse.EolLexer;
import org.eclipse.epsilon.eol.parse.EolParser;

/**
 * A context for EOL operations.
 * 
 * @since 2.5
 */
public class EolOperationDelegateContext extends EpsilonDelegateContext<EolModule, Object> implements DelegateContext {

	public EolOperationDelegateContext(
		DelegateUri delegateURI,
		EPackage ePackage,
		DelegateResourceAdapter delegateRA) {
		super(delegateURI, ePackage, delegateRA, new EolModule(), "eolModule");
	}

	@Override
	protected EolOperation program() {
		return new EolOperation();
	}

	@Override
	protected EpsilonParser createParser(TokenStream stream) {
		return new EolParser(stream);
	}

	@Override
	protected EolLexer createLexer(ANTLRInputStream inputStream) throws IOException {
		return new EolLexer(inputStream);
	}

	@Override
	protected void preParse() {
		this.module.getDeclaredOperations().clear();
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

	@Override
	protected EolOperation program(List<ParseProblem> parseProblems) {
		if (this.module.getDeclaredOperations().isEmpty()) {
			return  program().error(new IllegalStateException("Error parsing EOL, operation is malformed."));
		};
		return new EolOperation(this.module.getDeclaredOperations().get(0), parseProblems, this.module.getContext());
	}

}