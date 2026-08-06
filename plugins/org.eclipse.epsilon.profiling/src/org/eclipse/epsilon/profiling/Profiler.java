/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.profiling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class Profiler {
	
	protected Collection<IProfilerListener> listeners = new ArrayList<>();
	public static final Profiler INSTANCE = new Profiler();
	protected ProfilerTarget root, activeTarget;
	protected Stopwatch stopwatch;
	protected Map<String, Long> targets;
	protected List<String> targetNames;
	protected IEolContext context;
	
	
	public Profiler() {
		stopwatch = new Stopwatch();
		root = new ProfilerTarget("", stopwatch, "", null);
		reset();
	}
	
	public ProfilerTarget start(String targetName) {
		return start(targetName, "", null);
	}
	
	public ProfilerTarget start(String targetName, String data, ModuleElement moduleElement) {
		stopwatch.pause();
		activeTarget.pause();
		ProfilerTarget target = new ProfilerTarget(targetName, stopwatch, data, moduleElement);
		activeTarget.addChild(target);
		activeTarget = target;
		if (targets.containsKey(targetName)) {
			targets.put(targetName, targets.get(targetName) + 1);
		}
		else {
			targets.put(targetName, 1l);
		}
		if (!targetNames.contains(targetName)) {
			targetNames.add(targetName);
		}
		/*
		if (!allTargets.containsKey(target)) {
			allTargets.put(target, 0l);
			ArrayList<Long> history = new ArrayList<Long>();
			history.add(0l);
			targetHistory.put(target, history);
		}
		runningTargets.add(0, new ProfilerTarget(target, stopwatch.getElapsed()));
		
		for (IProfilerListener listener : listeners) {
			listener.targetStarted(target);
		}
		*/
		activeTarget.resume();
		stopwatch.resume();
		return target;
	}
	
	public void refresh() {
		for (IProfilerListener listener : listeners) {
			listener.refresh();
		}
	}
	
	public void stop() {
		stop(null);
	}
	
	public void stop(String target) {
		
		if (activeTarget == root) {
			throw new IllegalStateException("There is no profiling target to stop. Too many calls to the stop method?");
		}
		
		stopwatch.pause();
		activeTarget.stop();
		
		if (target != null && target.compareTo(activeTarget.getName()) != 0) {
			throw new IllegalStateException(
					"Attempted to stop profiling target " 
					+ target + 
					" while the active one is " 
					+ activeTarget.getName());
		}
		
		//activeTarget.setEndTime(stopwatch.getElapsed());
		activeTarget = activeTarget.getParent();
		
		/*
		if (runningTargets.size() == 0) return;
		
		ProfilerTarget profilerTarget = runningTargets.remove(0);
		
		if (!profilerTarget.getName().equalsIgnoreCase(target)) throw new IllegalStateException("Illegal State");
		
		long startTime = profilerTarget.getStartTime();
		long worked = stopwatch.getElapsed() - startTime;
		long previouslyWorked = allTargets.get(target);
		allTargets.put(target, previouslyWorked + worked);
		
		targetHistory.get(target).add(worked);
		*/
		
		for (IProfilerListener listener : listeners) {
			listener.targetStopped(target);
		}
		activeTarget.resume();
		stopwatch.resume();
	}
	
	public List<String> getTargetNames() {
		return targetNames;
	}
	
	public ProfilerOverview getOverview() {
		ProfilerOverview overview = new ProfilerOverview();
		long executionTime = 0;
		long executionCount = 0;
		for (ProfilerTargetSummary summary : getTargetSummaries()) {
			executionTime += summary.getExecutionTime().getIndividual();
			executionCount += summary.getExecutionCount();
		}
		overview.setExecutionCount(executionCount);
		overview.setExecutionTime(executionTime);
		return overview;
	}
	
	public List<ProfilerTargetSummary> getTargetSummaries() {
		
		HashMap<String, ExecutionTime> executionTimes = new HashMap<>();
		collectExecutionTimes(executionTimes, root);
		
		ArrayList<ProfilerTargetSummary> summaries = new ArrayList<>(targetNames.size());
		int i = 0;
		for (String targetName : targetNames) {
			ProfilerTargetSummary summary = new ProfilerTargetSummary();
			summary.setName(targetName);
			summary.setExecutionCount(getExecutionCount(targetName));
			summary.setExecutionTime(executionTimes.get(targetName));
			summary.setIndex(i++);
			summaries.add(summary);
		}
		return summaries;
	}
	
	protected void collectExecutionTimes(HashMap<String, ExecutionTime> executionTimes, ProfilerTarget target) {
		ExecutionTime executionTime = executionTimes.get(target.getName());
		if (executionTime == null) {
			executionTime = new ExecutionTime();
			executionTimes.put(target.getName(), executionTime);
		}
		executionTime.setAggregate(executionTime.getAggregate() + target.getWorked(true));
		executionTime.setIndividual(executionTime.getIndividual() + target.getWorked(false));
		
		for (ProfilerTarget child : target.getChildren()) {
			collectExecutionTimes(executionTimes, child);
		}
	}
	
	protected long getExecutionCount(String targetName) {
		return targets.containsKey(targetName) ? targets.get(targetName) : 0;
	}
	
	public boolean isRunning(String targetName) {
		ProfilerTarget target = activeTarget;
		while (target != null) {
			if (target.getName().equals(targetName)) {
				return true;
			}
			else {
				target = target.getParent();
			}
		}
		return false;
	}
	
	public void reset() {
		root.getChildren().clear();
		activeTarget = root;
		targets = new HashMap<>();
		targetNames = new ArrayList<>();
		context = null;
	}
	
	public void addListener(IProfilerListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}
	
	public void removeListener(IProfilerListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}
	
	public Stopwatch getStopwatch() {
		return stopwatch;
	}
	
	public ProfilerTarget getRoot() {
		return root;
	}

	/**
	 * 
	 * @return
	 * @since 2.3
	 */
	public IEolContext getContext() {
		return context;
	}

	/**
	 * 
	 * @param context
	 * @since 2.3
	 */
	public void setContext(IEolContext context) {
		this.context = context;
	}
}
