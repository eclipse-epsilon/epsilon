package org.eclipse.epsilon.picto.trace;

import java.util.List;

import org.eclipse.epsilon.egl.execute.operations.EglOperationFactory;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.recording.IPropertyAccess;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccessExecutionListener;
import org.eclipse.epsilon.eol.execute.introspection.recording.PropertyAccessRecorder;
import org.eclipse.epsilon.eol.execute.operations.AbstractOperation;
import org.eclipse.epsilon.picto.PictoView;

public class TraceOperationFactory extends EglOperationFactory {
	
	public TraceOperationFactory(PictoView pictoView) {
		super();
		operationCache.put("trace", new AbstractOperation() {
			
			@Override
			public Object execute(Object target, NameExpression operationNameExpression, List<Parameter> iterators,
					List<Expression> expressions, IEolContext context) throws EolRuntimeException {
				
				// TODO: This makes an assumption that the method is called with a single parameter. What if this doesn't hold?
				Expression expression = expressions.get(0);
				
				PropertyAccessRecorder recorder = new PropertyAccessRecorder();
				recorder.startRecording();
				PropertyAccessExecutionListener listener = new PropertyAccessExecutionListener(recorder);
				context.getExecutorFactory().addExecutionListener(listener);
				
				Object result = context.getExecutorFactory().execute(expression, context);
				
				context.getExecutorFactory().removeExecutionListener(listener);
				
				// TODO: This makes an assumption that there is one property access. What if there are zero or many?
				IPropertyAccess propertyAccess = recorder.getPropertyAccesses().unique().iterator().next();
				// TODO: We only append the invisible tag at the end of the text
				// We also need to account for cases where two traceable strings exist
				// in the context of the same HTML node
				return result + pictoView.getTraceMarkerManager().getTag(context, propertyAccess.getModelElement(), propertyAccess.getPropertyName());
			}
		});
	}
	
}
