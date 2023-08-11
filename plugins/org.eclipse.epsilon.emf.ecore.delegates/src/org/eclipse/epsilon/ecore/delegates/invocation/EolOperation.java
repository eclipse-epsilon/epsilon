/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Horacio Hoyos Rodriguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecore.delegates.invocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.ecore.delegates.execution.Program;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.models.IModel;

/**
 * The {@link Program} implementation for Eol Operations
 * 
 * @since 2.5
 */
public class EolOperation implements Program<Object> {
	
	public EolOperation(Throwable ex, List<ParseProblem> problems) {
		this(null, ex, problems, Collections.emptyList(), null);
	}

	public EolOperation(Operation program, List<ParseProblem> problems, IEolContext context) {
		this(program, null, problems, Collections.emptyList(), context);
	}

	public EolOperation() {
		this(null, null, Collections.emptyList(), Collections.emptyList(), null);
	}
	
	public EolOperation withParameters(List<?> paramterValues) {
		return new EolOperation(this.module, this.error, this.parseProblems, this.paramterValues, this.context);
	}

	@Override
	public Object execute(EObject eObject, IModel model) throws Throwable {
		if (this.error != null) {
			throw this.error;
		}
		if (!this.parseProblems.isEmpty()) {
			throw new IllegalStateException(
					this.parseProblems.stream().map(pp -> pp.toString()).collect(Collectors.joining("; ")));
		}
		if (!this.context.getModelRepository().getModels().contains(model)) {
			this.context.getModelRepository().addModel(model);
		}
		Object result = this.module.execute(eObject, this.paramterValues, this.context);
		if (result instanceof Collection) {
			// Collections need to be wrapped into EList
			return new BasicEList<>((Collection<?>)result);
		}
		return result;
	}

	@Override
	public EolOperation error(IOException ex) {
		return new EolOperation(ex, Collections.singletonList(
				new ParseProblem("Exception during parsing: " + ex.getLocalizedMessage(), ParseProblem.ERROR)));
	}

	@Override
	public EolOperation error(RecognitionException ex) {
		ParseProblem problem = new ParseProblem();
		problem.setLine(ex.line);
		problem.setColumn(ex.charPositionInLine);
		problem.setReason(ex.getMessage());
		return new EolOperation(ex, Collections.singletonList(problem));
	}

	@Override
	public EolOperation error(Throwable ex, Token next) {
		ParseProblem problem = new ParseProblem();
		problem.setLine(next.getLine());
		problem.setColumn(next.getCharPositionInLine());
		problem.setReason("mismatched input: '" + next.getText() + "'");
		return new EolOperation(ex, Collections.singletonList(problem));
	}

	@Override
	public EolOperation error(IllegalStateException ex) {
		return new EolOperation(ex, Collections.singletonList(new ParseProblem(
				"Exception during invariant evaluation: " + ex.getLocalizedMessage(), ParseProblem.ERROR)));
	}
	
	public void invokeWith(EList<?> arguments) {
		this.paramterValues = arguments == null ? Collections.emptyList() : arguments;
	}

	private final Operation module;
	private final Throwable error;
	private final IEolContext context;
	private List<ParseProblem> parseProblems = new ArrayList<>();
	private List<?> paramterValues;

	private EolOperation(
		Operation module,
		Throwable error, 
		List<ParseProblem> problems,
		List<?> paramterValues,
		IEolContext context) {
		this.module = module;
		this.error = error;
		this.parseProblems.addAll(problems);
		this.context = context;
		this.paramterValues = paramterValues;
	}

}
