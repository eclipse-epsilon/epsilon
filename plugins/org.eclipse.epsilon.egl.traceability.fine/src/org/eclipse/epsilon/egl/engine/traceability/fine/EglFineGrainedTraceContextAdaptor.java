/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.engine.traceability.fine;

import org.eclipse.epsilon.egl.engine.traceability.fine.internal.EglOutputBufferPrintExecutionListener;
import org.eclipse.epsilon.egl.engine.traceability.fine.internal.NonRestartablePropertyAccessRecorder;
import org.eclipse.epsilon.egl.engine.traceability.fine.internal.TraceLinkCreatingTemplateExecutionListener;
import org.eclipse.epsilon.egl.engine.traceability.fine.internal.TracedPropertyAccessLedger;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Trace;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.introspection.recording.IPropertyAccessRecorder;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccessExecutionListener;

/**
 * The entry point to the EGL fine-grained traceability functionality. This adaptor can
 * be used to non-destructively enhance an EglModule with execution listeners that build
 * a trace model when the module is executed.
 * 
 * @see Trace
 */
public class EglFineGrainedTraceContextAdaptor {

	/**
	 * Prepares to build a trace model for the specified context, returning an empty
	 * trace. Subsequent execution of the module attached to this context will cause
	 * the trace to be populated. 
	 */
	public Trace adapt(IEglContext context) {
		final Trace trace = new Trace();
		final TracedPropertyAccessLedger ledger = new TracedPropertyAccessLedger();
		
		selectivelyRecordPropertyAccesses(context, ledger);
		listenForTemplateExecution(context, trace, ledger);
		
		return trace;
	}

	protected void listenForTemplateExecution(IEglContext context, Trace trace, TracedPropertyAccessLedger ledger) {
		context.getTemplateFactory().getTemplateExecutionListeners().add(new TraceLinkCreatingTemplateExecutionListener(trace, ledger));
	}

	protected void selectivelyRecordPropertyAccesses(IEglContext context, TracedPropertyAccessLedger ledger) {
		final IPropertyAccessRecorder recorder = new NonRestartablePropertyAccessRecorder();
		ExecutorFactory executorFactory = context.getExecutorFactory();
		executorFactory.addExecutionListener(new PropertyAccessExecutionListener(recorder));
		executorFactory.addExecutionListener(new EglOutputBufferPrintExecutionListener(recorder, ledger));
	}
}
