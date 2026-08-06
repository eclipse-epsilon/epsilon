/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.types.EolNoType;

public class SchedulerTool extends AbstractTool{
	
	ArrayList<Job> jobs = new ArrayList<>();
	ArrayList<Operation> queque = new ArrayList<>();
	
	class Job {
		
		Timer timer;
		JobTask jobTask;
		int timesExecuted = 0;
		boolean finished = false;
		EolRuntimeException exception = null;
		int times = 0;
		int period = 0;
		
		public Job(final String operationName, final IEolContext context, int period, final int times) throws EolRuntimeException {
			timer = new Timer(false);
			this.times = times;
			this.period = period;
			jobTask = new JobTask(operationName, context, period){
				@Override
				public void run() {
					if (timesExecuted < Job.this.times || Job.this.times == -1) {
						queque.add(0, operation);
						timesExecuted ++;
					}
					else {
						finished = true;
						timer.cancel();
					}
				}
			};
		}
		
		public void start() {
			timer.schedule(jobTask, 0, jobTask.period);
		}
	}
	
	abstract class JobTask extends TimerTask {
		
		int period = 0;
		Operation operation = null;
		
		public JobTask(String operationName, IEolContext context, int period) throws EolRuntimeException {
			this.period = period;
			this.operation = ((IEolModule) context.getModule()).getOperations().getOperation(operationName);
			if (operation == null) {
				throw new EolRuntimeException("Operation " + operationName + " not found");
			}
		}

	}
	
	public void schedule(String operationName, int period) throws EolRuntimeException{
		schedule(operationName, period, -1);
	}
	
	public void schedule(String operationName, int period, int times) throws EolRuntimeException {
		addJob(operationName, period, times);
		start();
	}
	
	public void addJob(String operationName, int period) throws EolRuntimeException{
		addJob(operationName, period, -1);
	}
	
	public void addJob(String operationName, int period, int times) throws EolRuntimeException {
		Job job = new Job(operationName, context, period, times);
		jobs.add(job);
	}
	
	public void start() throws EolRuntimeException {
		for (Job job : jobs) {
			job.start();
		}
		boolean finished = true;
		while (true) {
			finished = true;
			for (Job job : jobs) {
				finished = finished && job.finished;
			}
			if (finished) return;
			
			synchronized (queque) {
				if (queque.size() > 0) {
					Operation operation = queque.remove(0);
					if (operation != null)
					operation.execute(EolNoType.NoInstance, Collections.emptyList(), context);
				}
			}
		}
	}

	public void start(int time) throws EolRuntimeException {
		for (Job job : jobs) {
			job.times = (int) Math.ceil(time/job.period);
		}
		start();
	}
}
