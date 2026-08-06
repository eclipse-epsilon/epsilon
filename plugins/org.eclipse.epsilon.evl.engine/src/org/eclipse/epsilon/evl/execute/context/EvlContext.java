/*******************************************************************************
 * Copyright (c) 2008-2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.evl.execute.context;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;

import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.erl.execute.context.ErlContext;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.dom.Constraint;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.evl.trace.ConstraintTrace;

public class EvlContext extends ErlContext implements IEvlContext {

	protected Collection<UnsatisfiedConstraint> unsatisfiedConstraints;
	protected ConstraintTrace constraintTrace;
	protected boolean optimizeConstraintTrace = false;
	protected boolean shortCircuiting = false;
	protected boolean terminate = false;
	
	public EvlContext() {
		constraintTrace = new ConstraintTrace();
		unsatisfiedConstraints = new LinkedHashSet<>();
	}
	
	/**
	 * Copy constructor, intended for internal use only.
	 * 
	 * @param other The parent context.
	 * @since 1.6
	 */
	public EvlContext(IEvlContext other) {
		super(other);
		optimizeConstraintTrace = other.isOptimizeConstraintTrace();
		shortCircuiting = other.isShortCircuiting();
		unsatisfiedConstraints = other.getUnsatisfiedConstraints();
		constraintTrace = other.getConstraintTrace();
	}
	
	@Override
	public ConstraintTrace getConstraintTrace() {
		return constraintTrace;
	}
	
	@Override
	public Set<UnsatisfiedConstraint> uniqueUnsatisfiedConstraints() {
		if (!(unsatisfiedConstraints instanceof LinkedHashSet || unsatisfiedConstraints instanceof SortedSet)) {
			unsatisfiedConstraints = new LinkedHashSet<>(unsatisfiedConstraints);
		}
		return (Set<UnsatisfiedConstraint>) unsatisfiedConstraints;
	}
	
	@Override
	public Collection<UnsatisfiedConstraint> getUnsatisfiedConstraints() {
		return unsatisfiedConstraints;
	}
	
	@Override
	public IEvlModule getModule() {
		return (IEvlModule) super.getModule();
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
			terminate = IEvlContext.super.shouldShortCircuit(constraint);
		}
		return terminate;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if (unsatisfiedConstraints != null) {
			unsatisfiedConstraints.clear();
		}
		if (constraintTrace != null) {
			constraintTrace.clear();
		}
		terminate = false;
	}
}
