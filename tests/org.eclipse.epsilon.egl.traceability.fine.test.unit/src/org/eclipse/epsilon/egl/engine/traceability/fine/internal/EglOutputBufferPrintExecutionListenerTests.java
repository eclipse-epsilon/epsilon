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

import static org.mockito.Mockito.*;
import java.util.Arrays;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Region;
import org.eclipse.epsilon.egl.execute.context.EglContext;
import org.eclipse.epsilon.egl.output.OutputBuffer;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.recording.IPropertyAccessRecorder;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccess;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccesses;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({EglOutputBufferPrintExecutionListenerTests.StartsRecordingWhenOutputBufferIsAboutToBePrintedTo.class,
               EglOutputBufferPrintExecutionListenerTests.StopsRecordingWhenPrintCallHasBeenExecuted.class})
public class EglOutputBufferPrintExecutionListenerTests {

	public static class StartsRecordingWhenOutputBufferIsAboutToBePrintedTo {
		
		@Test
		public void startsToRecordWhenFinishedExecutingLeftHandSideOfCallToPrintMethodOnOutputBuffer() throws Exception {
			for (String printMethodName : Arrays.asList("print", "prinx", "printdyn", "println")) {
				final IPropertyAccessRecorder recorder = afterExecutingLeftHandSideTest("out." + printMethodName + "();", new OutputBuffer());
				verify(recorder).startRecording();
			}
		}
		
		@Test
		public void doesNotStartToRecordWhenMethodIsNotPrint() throws Exception {
			final IPropertyAccessRecorder recorder = afterExecutingLeftHandSideTest("out.chop();", new OutputBuffer());
			
			verify(recorder, never()).startRecording();
		}
		
		@Test
		public void doesNotStartToRecordWhenLeftHandSideIsNotAnOutputBuffer() throws Exception {
			final IPropertyAccessRecorder recorder = afterExecutingLeftHandSideTest("out.print();", "DummyBuffer");
			
			verify(recorder, never()).startRecording();
		}
		
		private IPropertyAccessRecorder afterExecutingLeftHandSideTest(String code, Object buffer) throws Exception {
			final IPropertyAccessRecorder recorder = mock(IPropertyAccessRecorder.class);
			final EglOutputBufferPrintExecutionListener listener = new EglOutputBufferPrintExecutionListener(recorder, null);
			final ModuleElement ast = parse(code);
			listener.finishedExecuting(ast.getChildren().get(0), buffer, null);
			return recorder;
		}
	}
	
	public static class StopsRecordingWhenPrintCallHasBeenExecuted {
				
		@Test
		public void associatesPropertyAccessesWithPrintedText() throws Exception {
			// Expected result: the property access contributed to the text at region 4-10 (i.e., 4+6)
			final PropertyAccess access = new PropertyAccess("DummyElement", "name");
			final int preprintOffset = 4; final int postprintOffset = 10;
			
			// Prepare to check that the property access is associated with the region
			final TracedPropertyAccessLedger ledger = mock(TracedPropertyAccessLedger.class);
			
			// Have the output buffer return the correct offsets when queried
			final OutputBuffer buffer = mock(OutputBuffer.class);
			when(buffer.getOffset()).thenReturn(preprintOffset).thenReturn(postprintOffset);
			
			// Have the context return the correct template
			final EglTemplate template = mock(EglTemplate.class);
			final EglContext context = new EglContext() {
				@Override
				public EglTemplate getCurrentTemplate() {
					return template;
				}
			};
			
			// Execute the test
			afterExecutingMethodCallTest(createPropertyAccessRecorder(new PropertyAccesses(access)), ledger, buffer, context);
			
			// Check that the property access is associated with a region with appropriate offset and length
			verify(ledger).associate(access, new Region(preprintOffset, postprintOffset - preprintOffset, buffer.toString()), template);
		}
		
		private void afterExecutingMethodCallTest(IPropertyAccessRecorder recorder, TracedPropertyAccessLedger ledger, OutputBuffer buffer, IEolContext context) throws Exception {
			final EglOutputBufferPrintExecutionListener listener = new EglOutputBufferPrintExecutionListener(recorder, ledger);
			final ModuleElement ast = parse("out.print();");
			
			// First prime the cache
			listener.finishedExecuting(ast.getChildren().get(0), buffer, context);
			
			// Now simulate finished execution of point operator
			listener.finishedExecuting(ast, null, context);
		}

		private IPropertyAccessRecorder createPropertyAccessRecorder(PropertyAccesses accesses) {
			final IPropertyAccessRecorder recorder = mock(IPropertyAccessRecorder.class);
			when(recorder.getPropertyAccesses()).thenReturn(accesses);
			return recorder;
		}
	}

	private static ModuleElement parse(String code) throws Exception {
		final EolModule module = new EolModule();
		module.parse(code);
		return module.getChildren().get(0).getChildren().get(0).getChildren().get(0);
	}

}
