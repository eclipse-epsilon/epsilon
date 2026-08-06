/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.evl.launch;

import java.util.Collection;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.erl.launch.ErlRunConfiguration;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.concurrent.EvlModuleParallelAnnotation;
import org.eclipse.epsilon.evl.concurrent.EvlModuleParallelElements;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.evl.execute.context.IEvlContext;
import org.eclipse.epsilon.evl.execute.context.concurrent.EvlContextParallel;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EvlRunConfiguration extends ErlRunConfiguration {
	
	@SuppressWarnings("unchecked")
	public static class Builder<R extends EvlRunConfiguration, B extends Builder<R, B>> extends ErlRunConfiguration.Builder<R, B> {
		
		public boolean optimizeTrace = true;
		public boolean optimizeConstraints = false;
		public boolean shortCircuit = false;
		
		public B shortCircuiting() {
			return shortCircuiting(true);
		}
		public B shortCircuiting(boolean sc) {
			this.shortCircuit = sc;
			return (B) this;
		}
		
		public B optimizeConstraints() {
			return optimizeConstraints(true);
		}
		public B optimizeConstraints(boolean optimize) {
			this.optimizeConstraints = optimize;
			return (B) this;
		}
		
		public B eagerConstraintTrace() {
			return optimizeConstraintTrace(false);
		}
		public B optimizeConstraintTrace(boolean optimize) {
			this.optimizeTrace = optimize;
			return (B) this;
		}
		
		protected Builder() {
			super();
		}
		protected Builder(Class<R> runConfigClass) {
			super(runConfigClass);
		}
		
		@Override
		protected IEvlModule createModule() {
			if (isSequential()) return new EvlModule();
			EvlContextParallel context = new EvlContextParallel(parallelism);
			return isParallel() ?
				new EvlModuleParallelElements(context) :
				new EvlModuleParallelAnnotation(context);
		}
	}
	
	public static Builder<? extends EvlRunConfiguration, ?> Builder() {
		return new Builder<>(EvlRunConfiguration.class);
	}
	
	public EvlRunConfiguration(ErlRunConfiguration.Builder<? extends EvlRunConfiguration, ?> builder) {
		super(builder);
	}
	
	public EvlRunConfiguration(Builder<? extends EvlRunConfiguration, ?> builder) {
		super(builder);
		IEvlModule module = getModule();
		if (module instanceof EvlModule) {
			((EvlModule) module).setOptimizeConstraints(builder.optimizeConstraints);
		}
		module.getContext().setShortCircuit(builder.shortCircuit);
		module.getContext().setOptimizeConstraintTrace(builder.optimizeTrace);
	}
	
	public EvlRunConfiguration(EvlRunConfiguration other) {
		super(other);
		IEvlModule module = getModule(), otherModule = other.getModule();
		if (module instanceof EvlModule && otherModule instanceof EvlModule) {
			((EvlModule) module).setOptimizeConstraints(((EvlModule) otherModule).isOptimizeConstraints());
		}
		IEvlContext context = module.getContext(), otherContext = otherModule.getContext();
		context.setShortCircuit(otherContext.isShortCircuiting());
		context.setOptimizeConstraintTrace(otherContext.isOptimizeConstraintTrace());
	}
	
	@Override
	public IEvlModule getModule() {
		return (IEvlModule) super.getModule();
	}
	
	// METHOD VISIBILITY
	
	@Override
	public void preExecute() throws Exception {
		super.preExecute();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<UnsatisfiedConstraint> getResult() {
		return (Collection<UnsatisfiedConstraint>) super.getResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<UnsatisfiedConstraint> execute() throws EolRuntimeException {
		return (Collection<UnsatisfiedConstraint>) super.execute();
	}
	
	@Override
	public void postExecute() throws Exception {
		super.postExecute();
		
		if (showResults || profileExecution) {
			IEvlContext context = getModule().getContext();
			int numUnsatisfied = context.getUnsatisfiedConstraints().size();
			if (numUnsatisfied > 0) {
				writeOut(numUnsatisfied+" constraint"+(numUnsatisfied > 1 ? "s have" : " has")+" not been satisfied"+(showResults ? ':' : '.'));
				if (showResults) {
					writeOut(context.getUnsatisfiedConstraintsBySize().entrySet());
				}
			}
			else {
				writeOut("All constraints have been satisfied.");
			}
			
			writeOut(printMarker);
		}
	}
}