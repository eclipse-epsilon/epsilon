/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.engine.traceability.fine.internal;

import org.eclipse.epsilon.egl.EglPersistentTemplate;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.ModelLocation;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Region;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.TextLocation;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Trace;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.TraceLink;
import org.eclipse.epsilon.egl.execute.control.DefaultTemplateExecutionListener;
import org.eclipse.epsilon.eol.execute.introspection.recording.IPropertyAccess;

public class TraceLinkCreatingTemplateExecutionListener extends DefaultTemplateExecutionListener {

	protected Trace trace;
	protected TracedPropertyAccessLedger ledger;

	public TraceLinkCreatingTemplateExecutionListener(Trace trace, TracedPropertyAccessLedger ledger) {
		this.trace = trace;
		this.ledger = ledger;
	}

	@Override
	public void finishedGenerating(EglPersistentTemplate template, String path) {
		for (TracedPropertyAccess access : ledger.retrieve(template)) {
			trace.getTraceLinks().add(createTraceLink(path, access));
		}
		ledger.remove(template);
	}

	private TraceLink createTraceLink(String path, TracedPropertyAccess access) {
		return new TraceLink(createSource(access), createDestination(access.getRegion(), path));
	}

	private ModelLocation createSource(IPropertyAccess access) {
		return new ModelLocation(access.getModelElement(), access.getPropertyName());
	}

	private TextLocation createDestination(Region region, String path) {
		return new TextLocation(region, path);
	}

}
