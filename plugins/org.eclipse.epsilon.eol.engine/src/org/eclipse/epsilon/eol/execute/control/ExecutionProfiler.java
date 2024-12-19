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

import static org.eclipse.epsilon.common.util.profiling.BenchmarkUtils.formatDuration;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class ExecutionProfiler implements ExecutionController, IExecutionListener {
	
	protected final Map<ModuleElement, Duration> executionTimes = new HashMap<>();

	private class StackEntry {
		final ModuleElement element;
		final long startNanos;

		StackEntry(ModuleElement element, long start) {
			this.element = element;
			this.startNanos = start;
		}
	}
	private Deque<StackEntry> astStack = new ArrayDeque<>();

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
			astStack.push(new StackEntry(ast, System.nanoTime()));
		}
	}
	
	@Override
	public void done(ModuleElement ast, IEolContext context) {
		if (ast == null || astStack.isEmpty() || ast != astStack.peek().element) return;
		
		StackEntry top = astStack.pop();
		long execTime = System.nanoTime() - top.startNanos;

		executionTimes.merge(top.element,
			Duration.ofNanos(execTime),
			(d1, d2) -> d1.plus(d2));
	}

	@Override
	public void dispose() {
		executionTimes.clear();
		astStack.clear();
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
