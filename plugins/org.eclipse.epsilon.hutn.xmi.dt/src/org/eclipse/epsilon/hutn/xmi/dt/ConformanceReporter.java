/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
/**
 * 
 */
package org.eclipse.epsilon.hutn.xmi.dt;

import java.util.Collection;

import org.eclipse.epsilon.common.parse.problem.ParseProblem;

public interface ConformanceReporter {
	public void reportConformant(String name);

	public void reportNonConformant(String name, Collection<ParseProblem> conformanceProblems);
}