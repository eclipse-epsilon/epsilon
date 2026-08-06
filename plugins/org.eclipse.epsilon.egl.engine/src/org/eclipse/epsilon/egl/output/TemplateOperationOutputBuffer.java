package org.eclipse.epsilon.egl.output;

import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.egl.execute.context.IEglContext;

public class TemplateOperationOutputBuffer extends OutputBuffer {
	
	public TemplateOperationOutputBuffer(IEglContext context) {
		super(context);
	}

	@Override
	public int getOffset() {
		String noWhitespace = "no-whitespace";
		
		if (parent != null && parent instanceof OutputBuffer) {
			OutputBuffer parentBuffer = (OutputBuffer) parent;
			String indentation = parentBuffer.calculateIndentationToMatch(parentBuffer.getLastLineInBuffer());
			String[] lines = StringUtil.toString(toString() + noWhitespace).split(getNewline());
			int offset = super.getOffset() + Math.max(0, lines.length - 1) * indentation.length();
			return offset;
		}
		else {
			return super.getOffset();
		}
	}
}
