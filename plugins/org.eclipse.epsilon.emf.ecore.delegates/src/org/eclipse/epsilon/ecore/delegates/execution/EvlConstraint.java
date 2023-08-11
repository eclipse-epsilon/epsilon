/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Horacio Hoyos Rodriguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecore.delegates.execution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.evl.dom.Constraint;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.evl.execute.context.IEvlContext;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;

/**
 * The {@link Program} implementation for Evl Constriants
 * 
 * @since 2.5
 */
public class EvlConstraint implements Program<Optional<UnsatisfiedConstraint>> {

	public EvlConstraint(Throwable ex, List<ParseProblem> problems) {
		this(null, ex, problems, null);
	}

	public EvlConstraint(Constraint program, List<ParseProblem> problems, IEvlContext context) {
		this(program, null, problems, context);
	}

	public EvlConstraint() {
		this(null, null, Collections.emptyList(), null);
	}

	@Override
	public EvlConstraint error(IOException ex) {
		return new EvlConstraint(ex, Collections.singletonList(
				new ParseProblem("Exception during parsing: " + ex.getLocalizedMessage(), ParseProblem.ERROR)));
	}

	@Override
	public EvlConstraint error(RecognitionException ex) {
		ParseProblem problem = new ParseProblem();
		problem.setLine(ex.line);
		problem.setColumn(ex.charPositionInLine);
		problem.setReason(ex.getMessage());
		return new EvlConstraint(ex, Collections.singletonList(problem));
	}

	@Override
	public EvlConstraint error(Throwable ex, Token next) {
		ParseProblem problem = new ParseProblem();
		problem.setLine(next.getLine());
		problem.setColumn(next.getCharPositionInLine());
		problem.setReason("mismatched input: '" + next.getText() + "'");
		return new EvlConstraint(ex, Collections.singletonList(problem));
	}

	@Override
	public EvlConstraint error(IllegalStateException ex) {
		return new EvlConstraint(ex, Collections.singletonList(new ParseProblem(
				"Exception during invariant evaluation: " + ex.getLocalizedMessage(), ParseProblem.ERROR)));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Optional<UnsatisfiedConstraint> execute(EObject eObject, IModel model) throws Throwable {
		if (this.error != null) {
			throw this.error;
		}
		if (!this.parseProblems.isEmpty()) {
			throw new IllegalStateException(
					this.parseProblems.stream().map(pp -> pp.toString()).collect(Collectors.joining("; ")));
		}
		ExecutorFactory executorFactory = context.getExecutorFactory();
		if (!context.getModelRepository().getModels().contains(model)) {
			context.getModelRepository().addModel(model);
		}
		return (Optional<UnsatisfiedConstraint>) executorFactory.execute(this.module, context, eObject);
	}

	private final Constraint module;
	private List<ParseProblem> parseProblems = new ArrayList<>();
	private final Throwable error;
	private final IEvlContext context;

	private EvlConstraint(
		Constraint module,
		Throwable error,
		List<ParseProblem> problems,
		IEvlContext context) {
		this.module = module;
		this.error = error;
		this.parseProblems.addAll(problems);
		this.context = context;
	}

}