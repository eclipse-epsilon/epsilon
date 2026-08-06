/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.erl.execute;

import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.control.DefaultExecutionController;
import org.eclipse.epsilon.eol.execute.control.ExecutionController;
import org.eclipse.epsilon.eol.execute.control.ExecutionProfiler;
import org.eclipse.epsilon.erl.execute.control.RuleProfiler;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class RuleExecutorFactory extends ExecutorFactory {

	public RuleExecutorFactory() {
		this(null);
	}

	public RuleExecutorFactory(ExecutorFactory parent) {
		super(parent);
		if (parent != null) {
			setProfilingEnabled(parent.isProfilingEnabled());
		}
	}

	public void setProfilingEnabled(boolean profile) {
		boolean profilingEnabled = isProfilingEnabled();
		if (profile && !profilingEnabled) {
			setExecutionController(new RuleProfiler());
		}
		else if (!profile && profilingEnabled) {
			setExecutionController(new DefaultExecutionController());
		}
	}
	
	@Override
	public void merge(MergeMode mode) {
		ExecutorFactory from = getFrom(mode), to = getTo(mode);
		ExecutionController fromController = from.getExecutionController(),
			toController = to.getExecutionController();
		
		if (fromController instanceof ExecutionProfiler && toController instanceof ExecutionProfiler) {
			((ExecutionProfiler) toController)
				.mergeExecutionTimes(
					((ExecutionProfiler) fromController).getExecutionTimes()
				);
		}
		
		super.merge(mode);
	}
}
