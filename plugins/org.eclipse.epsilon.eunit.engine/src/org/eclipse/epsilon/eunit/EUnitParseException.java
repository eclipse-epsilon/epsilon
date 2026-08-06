/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eunit;

import java.util.List;

import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

/**
 * Exception for when EUnit modules have parsing problems. Normally we just
 * hang on and continue processing, but with EUnit we prefer to just abort
 * execution.
 *
 * @author Antonio García-Domínguez
 * @version 1.0
 */
public class EUnitParseException extends EolRuntimeException {

	private static final long serialVersionUID = 1L;

	private List<ParseProblem> parseProblems;

	public EUnitParseException(List<ParseProblem> parseProblems) {
		this.parseProblems = parseProblems;
	}

	public List<ParseProblem> getProblems() {
		return parseProblems;
	}

	@Override
	public String getMessage() {
		return "Found " + parseProblems.size() + " parse problems: " + parseProblems;
	}
}
