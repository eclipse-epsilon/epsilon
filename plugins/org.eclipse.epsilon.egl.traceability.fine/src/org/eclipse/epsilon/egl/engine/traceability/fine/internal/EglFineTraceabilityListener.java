package org.eclipse.epsilon.egl.engine.traceability.fine.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.WeakHashMap;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.egl.engine.traceability.fine.trace.Region;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.output.OutputBuffer;
import org.eclipse.epsilon.eol.dom.OperationCallExpression;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.recording.IPropertyAccess;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccess;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccessExecutionListener;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccessRecorder;

public class EglFineTraceabilityListener extends PropertyAccessExecutionListener {

	protected TracedPropertyAccessLedger ledger;
	protected Stack<List<PropertyAccess>> propertyAccessesStack = new Stack<>();
	protected final WeakHashMap<ModuleElement, Stack<TraceData>> cache = new WeakHashMap<>();
	protected List<String> printMethods = Arrays.asList("printdyn", "println", "print", "prinx");
	
	public EglFineTraceabilityListener(TracedPropertyAccessLedger ledger) {
		this.ledger = ledger;
		this.recorders.add(recorder);
	}
	
	protected PropertyAccessRecorder recorder = new PropertyAccessRecorder() {
		public void record(Object modelElement, String propertyName) {
			if (!propertyAccessesStack.isEmpty()) {
				propertyAccessesStack.peek().add(new PropertyAccess(modelElement, propertyName));
			}
		};
	};
	
	@Override
	public void finishedExecuting(ModuleElement ast, Object result, IEolContext context) {
		
		if (result instanceof OutputBuffer && isCallToPrintMethod(ast.getParent())) {
			callToPrintMethodStarted(ast, (OutputBuffer)result, context);
		}
		
		if (cache.containsKey(ast)) {
			callToPrintMethodFinished(ast, (IEglContext) context);
		}
		
		super.finishedExecuting(ast, result, context);
		
	}
	
	protected void callToPrintMethodStarted(ModuleElement ast, OutputBuffer buffer, IEolContext context) {
		propertyAccessesStack.push(new ArrayList<PropertyAccess>());
		Stack<TraceData> traceDataStack = cache.get(ast.getParent());
		if (traceDataStack == null) {
			traceDataStack = new Stack<>();
			cache.put(ast.getParent(), traceDataStack);
		}
		traceDataStack.push(new TraceData(buffer, buffer.getOffset()));
	}
	
	protected void callToPrintMethodFinished(ModuleElement ast, IEglContext context) {
		List<PropertyAccess> propertyAccesses = propertyAccessesStack.pop();
		associatePropertyAccessesWithRegionInGeneratedText(ast, propertyAccesses, context);		
	}
	
	protected boolean isCallToPrintMethod(ModuleElement p) {
		return p instanceof OperationCallExpression && printMethods.contains(((OperationCallExpression) p).getName());
	}
	
	protected void associatePropertyAccessesWithRegionInGeneratedText(ModuleElement ast, List<PropertyAccess> propertyAccesses, IEglContext context) {
		final Region region = regionFor(ast);
		for (IPropertyAccess access : propertyAccesses) {
			ledger.associate(access, region, context.getCurrentTemplate());
		}
	}

	protected Region regionFor(ModuleElement ast) {
		TraceData traceData = cache.get(ast).pop();
		final int startOffset = traceData.globalOffset;
		final int length = traceData.buffer.getOffset() - startOffset;

		// The extracted text ignores matched indentation
		final String fullText = traceData.buffer.toString();
		final String text = fullText.substring(traceData.localOffset);

		Region region = new Region(startOffset, length, text);
		return region;
	}
	
	protected static class TraceData {
		public final OutputBuffer buffer;

		/** File-level offset (includes added indentation). */
		public final int globalOffset;

		/** Buffer-level offset (ignores added indentation). */
		public final int localOffset;

		public TraceData(OutputBuffer buffer, int offset) {
			this.buffer = buffer;
			this.globalOffset = offset;
			this.localOffset = buffer.getLength();
		}
	}
	
}
