/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.validation.config;

import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.hutn.validation.AbstractFixer;

class HutnConfigFileFixer extends AbstractFixer {

	private final int line;
	private final int col;
	
	public HutnConfigFileFixer(int line, int col) {
		this.line = line;
		this.col  = col;
	}
	
	@Override
	protected ParseProblem interpretUnsatisfiedConstraint(UnsatisfiedConstraint constraint) {
		return new ParseProblem(line, col, constraint.getMessage());
	}

}
