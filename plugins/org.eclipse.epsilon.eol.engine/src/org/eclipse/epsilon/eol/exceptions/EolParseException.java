/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.exceptions;

import java.util.Collection;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;

/**
 *
 *
 * @author Sina Madani
 * @since 1.6
 */
public class EolParseException extends Exception {

	protected final Collection<? extends ParseProblem> parseProblems;
	
	public EolParseException() {
		this("Parse problems found!");
	}

	public EolParseException(String message) {
		this(message, null);
	}

	public EolParseException(Collection<? extends ParseProblem> problems) {
		this(null, problems);
	}
	
	public EolParseException(String message, Collection<? extends ParseProblem> problems) {
		super(message);
		this.parseProblems = problems;
	}
	
	@Override
	public String getMessage() {
		String detail = super.getMessage();
		if (detail == null) detail = "";
		if (parseProblems != null && !parseProblems.isEmpty()) {
			for (ParseProblem pp : parseProblems) {
				detail += System.lineSeparator() + pp.toString();
			}
		}
		return detail;
	}
}
