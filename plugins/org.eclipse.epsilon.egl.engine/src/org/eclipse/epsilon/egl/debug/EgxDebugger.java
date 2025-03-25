/*******************************************************************************
 * Copyright (c) 2012-2024 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.debug;

import java.util.Collection;
import java.util.function.Supplier;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.IEgxModule;
import org.eclipse.epsilon.egl.dom.GenerationRule;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.execute.control.DefaultTemplateExecutionListener;
import org.eclipse.epsilon.egl.execute.control.ITemplateExecutionListener;
import org.eclipse.epsilon.eol.debug.BreakpointRequest;
import org.eclipse.epsilon.eol.debug.BreakpointResult;
import org.eclipse.epsilon.eol.debug.BreakpointState;
import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.control.IExecutionListener;

public class EgxDebugger extends EolDebugger {

	protected class DebugTemplateListener extends DefaultTemplateExecutionListener {
		private final Supplier<Collection<IExecutionListener>> listenerSupplier;

		public DebugTemplateListener(Supplier<Collection<IExecutionListener>> listenerSupplier) {
			this.listenerSupplier = listenerSupplier;
		}
	
		@Override
		public void aboutToProcess(EglTemplate template) {
			final IEglContext context = template.getModule().getContext();
			final ExecutorFactory executorFactory = context.getExecutorFactory();
			final Collection<IExecutionListener> existingListeners = executorFactory.getExecutionListeners();
			for (IExecutionListener l : listenerSupplier.get()) {
				if (!existingListeners.contains(l)) {
					executorFactory.addExecutionListener(l);
				}
			}
		}
	}

	@Override
	protected boolean isStructuralBlock(ModuleElement ast) {
		return super.isStructuralBlock(ast) || ast instanceof GenerationRule;
	}

	@Override
	public void control(ModuleElement ast, IEolContext context) {
		if (ast instanceof IEgxModule) {
			/*
			 * We are about to run an EGX script: ensure we copy the execution listeners of
			 * the EGX script into the EGL module (for debugging purposes).
			 */
			IEgxModule egxModule = (IEgxModule) ast;

			final Collection<ITemplateExecutionListener> templateListeners = egxModule
				.getTemplateFactory().getTemplateExecutionListeners();
			boolean hasListener = templateListeners.stream()
				.anyMatch(l -> l instanceof DebugTemplateListener);

			if (!hasListener) {
				templateListeners.add(new DebugTemplateListener(
					() -> egxModule.getContext().getExecutorFactory().getExecutionListeners()));
			}
		}
		
		super.control(ast, context);
	}

	@Override
	public BreakpointResult verifyBreakpoint(BreakpointRequest request) {
		BreakpointResult result = super.verifyBreakpoint(request);
		if (result.getState() == BreakpointState.VERIFIED) {
			return result;
		}

		/*
		 * We really do not know if the given breakpoint will be triggered or not: it
		 * will depend on whether it points to an EGL script executed by the EGX rules.
		 */
		return BreakpointResult.pending(request);
	}
	
}
