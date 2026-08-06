/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.evl.execute.context.concurrent;

import java.util.Collection;
import java.util.Set;
import org.eclipse.epsilon.common.concurrent.ConcurrencyUtils;
import org.eclipse.epsilon.common.util.SizeCachingConcurrentQueue;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.erl.execute.context.concurrent.ErlContextParallel;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.dom.Constraint;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.evl.execute.context.EvlContext;
import org.eclipse.epsilon.evl.execute.context.IEvlContext;
import org.eclipse.epsilon.evl.trace.ConstraintTrace;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EvlContextParallel extends ErlContextParallel implements IEvlContextParallel {

	protected Collection<UnsatisfiedConstraint> unsatisfiedConstraints;
	protected ConstraintTrace constraintTrace;
	protected boolean optimizeConstraintTrace = false;
	protected boolean shortCircuiting = false;
	protected boolean terminate = false;
	
	public EvlContextParallel() {
		this(0);
	}
	
	/**
	 * @param parallelism The number of threads to use.
	 * @param threadSafeBaseFrames whether the base FrameStack should use a thread-safe collection.
	 * default is <code>true</code>
	 */
	public EvlContextParallel(int parallelism) {
		super(parallelism);
		unsatisfiedConstraints = new SizeCachingConcurrentQueue<>();
		constraintTrace = new ConstraintTrace(true);
	}
	
	protected EvlContextParallel(IEolContext other) {
		super(other);
		if (other instanceof IEvlContext) {
			IEvlContext evlContext = (IEvlContext) other;
			this.unsatisfiedConstraints = evlContext.getUnsatisfiedConstraints();
			this.optimizeConstraintTrace = evlContext.isOptimizeConstraintTrace();
			this.shortCircuiting = evlContext.isShortCircuiting();
			this.constraintTrace = evlContext.getConstraintTrace();
		}
	}
	
	@Override
	protected IEvlContext createShadowThreadLocalContext() {
		return new EvlContext(this);
	}
	
	@Override
	public IEvlContext getShadow() {
		return (IEvlContext) super.getShadow();
	}
	
	@Override
	public synchronized Set<UnsatisfiedConstraint> uniqueUnsatisfiedConstraints() {
		if (!(unsatisfiedConstraints instanceof Set)) {
			unsatisfiedConstraints = ConcurrencyUtils.concurrentSet(unsatisfiedConstraints);
		}
		return (Set<UnsatisfiedConstraint>) unsatisfiedConstraints;
	}
	
	@Override
	public Collection<UnsatisfiedConstraint> getUnsatisfiedConstraints() {
		return unsatisfiedConstraints;
	}

	@Override
	public ConstraintTrace getConstraintTrace() {
		return constraintTrace;
	}

	@Override
	public void setOptimizeConstraintTrace(boolean optimize) {
		this.optimizeConstraintTrace = optimize;
	}

	@Override
	public boolean isOptimizeConstraintTrace() {
		return optimizeConstraintTrace;
	}

	@Override
	public boolean isShortCircuiting() {
		return shortCircuiting;
	}

	@Override
	public void setShortCircuit(boolean shortCircuit) {
		this.shortCircuiting = shortCircuit;
	}

	@Override
	public boolean shouldShortCircuit(Constraint constraint) throws EolRuntimeException {
		if (!terminate) {
			terminate = IEvlContextParallel.super.shouldShortCircuit(constraint);
		}
		return terminate;
	}
	
	@Override
	public IEvlModule getModule() {
		return (IEvlModule) super.getModule();
	}
	
	@Override
	public synchronized void dispose() {
		super.dispose();
		if (unsatisfiedConstraints != null)
			unsatisfiedConstraints.clear();
		if (constraintTrace != null)
			constraintTrace.clear();
		terminate = false;
	}
}