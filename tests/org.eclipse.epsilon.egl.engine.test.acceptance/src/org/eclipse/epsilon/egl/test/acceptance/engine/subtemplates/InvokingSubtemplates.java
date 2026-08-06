/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.test.acceptance.engine.subtemplates;

import static org.eclipse.epsilon.common.util.FileUtil.getFileStandalone;
import static org.eclipse.epsilon.egl.util.FileUtil.NEWLINE;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.epsilon.egl.EglFileGeneratingTemplateFactory;
import org.eclipse.epsilon.egl.EglPersistentTemplate;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.execute.control.ITemplateExecutionListener;
import org.eclipse.epsilon.egl.test.acceptance.AcceptanceTestUtil;
import org.eclipse.epsilon.egl.util.StringUtil;
import org.eclipse.epsilon.eol.models.IModel;
import org.junit.BeforeClass;
import org.junit.Test;

public class InvokingSubtemplates {
	
	private static String actual = "";
	private static MockListener listener = new MockListener();

	@BeforeClass
	public static void setup() throws Exception {
		final EglTemplateFactory factory = new EglFileGeneratingTemplateFactory();
		factory.getTemplateExecutionListeners().add(listener);
		
		// Load dependencies
		getFileStandalone("first/First.egl", InvokingSubtemplates.class);
		getFileStandalone("second/Second.egl", InvokingSubtemplates.class);
		final File driver = getFileStandalone("Driver.egl", InvokingSubtemplates.class);
		actual = AcceptanceTestUtil.run(factory, driver, new IModel[]{});
	}
	
	@Test
	public void subtemplatesInDifferentDirectories() throws Exception {
		final String expected = "Hello from First" + NEWLINE + "Hello from Second";
		
		assertEquals(StringUtil.normalizeNewlines(expected), StringUtil.normalizeNewlines(actual));
	}
	
	@Test
	public void notificationsAreFiredForEachSubtemplate() {
		final List<ExecutionEvent> expectedEvents = new LinkedList<>();
		
		expectedEvents.add(new ExecutionEvent("Driver.egl", EventType.PREPROCESS));
		expectedEvents.add(new ExecutionEvent("first/First.egl", EventType.PREPROCESS));
		expectedEvents.add(new ExecutionEvent("first/First.egl", EventType.POSTPROCESS));
		expectedEvents.add(new ExecutionEvent("second/Second.egl", EventType.PREPROCESS));
		expectedEvents.add(new ExecutionEvent("second/Second.egl", EventType.POSTPROCESS));
		expectedEvents.add(new ExecutionEvent("Driver.egl", EventType.POSTPROCESS));
	
		assertEquals(expectedEvents, listener.events);
	}
	
	private static class ExecutionEvent {
		private final String templateName;
		private final EventType type;
		
		public ExecutionEvent(String templateName, EventType type) {
			this.templateName = templateName;
			this.type = type;
		}
		
		@Override
		public boolean equals(Object object) {
			if (!(object instanceof ExecutionEvent))
				return false;
			
			final ExecutionEvent other = (ExecutionEvent)object;
			
			return templateName.equals(other.templateName) &&
			       type.equals(other.type);
		}
		
		@Override
		public String toString() {
			return type.toString() + " for " + templateName;
		}
	}
	
	private static enum EventType {
		PREPROCESS, POSTPROCESS, POSTGENERATE;
	}
	
	private static class MockListener implements ITemplateExecutionListener {
		
		public final Collection<ExecutionEvent> events = new LinkedList<>();  
		
		@Override
		public void aboutToProcess(EglTemplate template) {
			events.add(new ExecutionEvent(template.getName(), EventType.PREPROCESS));
		}

		@Override
		public void finishedProcessing(EglTemplate template) {
			events.add(new ExecutionEvent(template.getName(), EventType.POSTPROCESS));
		}

		@Override
		public void finishedGenerating(EglPersistentTemplate template, String path) {
			events.add(new ExecutionEvent(template.getName(), EventType.POSTGENERATE));
		}
	}
}
