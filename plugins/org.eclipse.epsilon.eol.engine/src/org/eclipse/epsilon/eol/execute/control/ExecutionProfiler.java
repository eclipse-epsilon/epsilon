/*******************************************************************************
 * Copyright (c) 2008-2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - Refactoring and enhancements
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.control;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.eclipse.epsilon.common.module.ModuleElement;
import static org.eclipse.epsilon.common.util.profiling.BenchmarkUtils.formatDuration;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class ExecutionProfiler implements ExecutionController, IExecutionListener {
	
	protected final Map<ModuleElement, Duration> executionTimes = new HashMap<>();
	private ModuleElement currentAst;
	private long currentStart;
	
	/**
	 * Determines whether the ModuleElement should be profiled.
	 * 
	 * @param ast The ModuleElement under scrutiny.
	 * @param context The context passed to {@link #control(ModuleElement, IEolContext)}
	 * @return <code>true</code> if the ModuleElement should be profiled, <code>false</code> otherwise.
	 * @since 1.6
	 */
	protected boolean screenAST(ModuleElement ast, IEolContext context) {
		return true;
	}
	
	@Override
	public void control(ModuleElement ast, IEolContext context) {
		if (ast != null && screenAST(ast, context)) {
			currentAst = ast;
			currentStart = System.nanoTime();
		}
	}
	
	@Override
	public void done(ModuleElement ast, IEolContext context) {
		if (ast == null || ast != currentAst) return;
		
		long execTime = System.nanoTime() - currentStart;
		Duration currentDuration = executionTimes.get(ast);
		
		executionTimes.put(
			currentAst, 
			currentDuration != null ? currentDuration.plusNanos(execTime) : Duration.ofNanos(execTime)
		);
		
		currentAst = null;
	}

	@Override
	public void dispose() {
		executionTimes.clear();
		currentAst = null;
		currentStart = 0;
	}

	@Override
	public void report(IEolContext context) {
		context.getErrorStream().println(this.toString());
	}
	
	@Override
	public String toString() {
		return getExecutionTimes()
			.entrySet()
			.stream()
			.sorted((a, b) -> b.getValue().compareTo(a.getValue()))
			.map(entry -> "'"+entry.getKey()+"' took "+formatDuration(entry.getValue()))
			.collect(Collectors.joining(","+System.lineSeparator()));
	}
	
	@SafeVarargs
	public final void mergeExecutionTimes(Map<? extends ModuleElement, Duration>... others) {
		for (Map<? extends ModuleElement, Duration> execTimes : others) {
			for (Map.Entry<? extends ModuleElement, Duration> entry : execTimes.entrySet()) {
				Duration ruleDuration = executionTimes.get(entry.getKey());
				Duration entryDuration = entry.getValue();
				executionTimes.put(
					entry.getKey(),
					ruleDuration != null ? ruleDuration.plus(entryDuration) : entryDuration
				);
			}
		}
	}
	
	public Map<ModuleElement, Duration> getExecutionTimes() {
		return executionTimes;
	}

	@Override
	public boolean isTerminated() {
		return false;
	}
	
	@Override
	public void aboutToExecute(ModuleElement ast, IEolContext context) {
		control(ast, context);
	}

	@Override
	public void finishedExecuting(ModuleElement ast, Object result, IEolContext context) {
		done(ast, context);
	}

	@Override
	public void finishedExecutingWithException(ModuleElement ast, EolRuntimeException exception, IEolContext context) {
		done(ast, context);
	}
}
