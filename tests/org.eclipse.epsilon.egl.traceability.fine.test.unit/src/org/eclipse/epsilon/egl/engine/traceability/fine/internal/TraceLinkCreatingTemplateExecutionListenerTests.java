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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.eclipse.epsilon.egl.EglPersistentTemplate;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.ModelLocation;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Region;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.TextLocation;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Trace;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.TraceLink;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccess;
import org.junit.Test;

public class TraceLinkCreatingTemplateExecutionListenerTests {

	@Test
	public void createsTraceLinksAfterFinishedGenerating() throws Exception {
		final EglPersistentTemplate dummyTemplate = mock(EglPersistentTemplate.class);

		final TracedPropertyAccessLedger ledger = new TracedPropertyAccessLedger();
		ledger.associate(new PropertyAccess("DummyElement", "name"), new Region(10, 5, null), dummyTemplate);
		
		final Trace trace = new Trace();
		final TraceLinkCreatingTemplateExecutionListener listener = new TraceLinkCreatingTemplateExecutionListener(trace, ledger);
		
		listener.finishedGenerating(dummyTemplate, "dummyPath");
		
		final Trace expectedTrace = new Trace();
		expectedTrace.traceLinks.add(new TraceLink(new ModelLocation("DummyElement", "name"), new TextLocation(new Region(10, 5, null), "dummyPath")));
		
		assertEquals(expectedTrace, trace);
	}
}
