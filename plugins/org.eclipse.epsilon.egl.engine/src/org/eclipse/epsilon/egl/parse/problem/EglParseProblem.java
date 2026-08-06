/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.parse.problem;

import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.egl.preprocessor.PreprocessorTrace;

public class EglParseProblem extends ParseProblem {
	
	protected final ParseProblem problem;
	protected final PreprocessorTrace trace;
	
	public EglParseProblem(ParseProblem anomaly, PreprocessorTrace trace) {
		this.setLine(anomaly.getLine());
		this.setReason(anomaly.getReason());
		this.problem = anomaly;
		this.trace = trace;
		setColumn(trace.getEglColumnNumberFor(anomaly.getLine(), anomaly.getColumn()));
	}
	
	@Override
	public int getLine() {
		return trace.getEglLineNumberFor(problem.getLine());
	}
}
