/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.execute.context;

import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.output.IOutputBuffer;
import org.eclipse.epsilon.eol.execute.context.FrameStack;

class ExecutableTemplateSpecification {
	
	public final EglTemplate template;
	public final IOutputBuffer outputBuffer;
	
	public ExecutableTemplateSpecification(EglTemplate template, IOutputBuffer outputBuffer) {
		this.template = template;
		this.outputBuffer = outputBuffer;
	}

	/**
	 * Copy constructor.
	 * @param other
	 * @since 1.6
	 */
	public ExecutableTemplateSpecification(ExecutableTemplateSpecification other) {
		this.template = other.template;
		this.outputBuffer = other.outputBuffer;
	}
	
	public void addAsChild(ExecutableTemplateSpecification child) {
		template.getTemplate().add(child.template.getTemplate());
	}
	
	public void addVariablesTo(FrameStack frameStack) {
		frameStack.putGlobal(org.eclipse.epsilon.eol.execute.context.Variable
			.createReadOnlyVariable("out", outputBuffer)
		);
		
		for (org.eclipse.epsilon.egl.traceability.Variable variable : template.getTemplate().getVariables()) {
			frameStack.put(variable.getName(), variable.getValue());
		}
	}
	
	@Override
	public String toString() {
		return template.getTemplate().toString();
	}
}
