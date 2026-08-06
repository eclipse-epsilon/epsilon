/*******************************************************************************
 * Copyright (c) 2008-2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - fix for nested template calls
******************************************************************************/
package org.eclipse.epsilon.egl.dom;

import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.output.IOutputBuffer;
import org.eclipse.epsilon.egl.output.TemplateOperationOutputBuffer;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;

public class TemplateOperation extends Operation {

	@Override
	protected Return executeBody(IEolContext context) throws EolRuntimeException {
		final IEglContext eglContext = (IEglContext) context;
		final IOutputBuffer out = new TemplateOperationOutputBuffer(eglContext);
		
		out.setIndenters(eglContext.getOutputBuffer().getIndenters());
		final String outName = "out";
		final FrameStack frameStack = context.getFrameStack();
		
		// If there is already an out variable in the frame stack and it is 
		// an output buffer, set it as the parent of the new output buffer
		Variable outVariable = frameStack.get(outName);
		if (outVariable != null && outVariable.getValue() instanceof IOutputBuffer) {
			out.setParent((IOutputBuffer) outVariable.getValue());
		}

		frameStack.enterGlobal(FrameType.UNPROTECTED, this, Variable.createReadOnlyVariable(outName, out));
		try {
			super.executeBody(context);
		} finally {
			frameStack.leaveGlobal(this);
		}
		return new Return(out.getOutdentationFormatter().format(out.toString()));
	}
	
}
 
