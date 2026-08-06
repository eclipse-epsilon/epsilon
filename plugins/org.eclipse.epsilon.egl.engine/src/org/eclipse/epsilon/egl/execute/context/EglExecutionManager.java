/*******************************************************************************
 * Copyright (c) 2011-2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 *     Sina Madani - refactoring
 ******************************************************************************/
package org.eclipse.epsilon.egl.execute.context;

import java.net.URI;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.output.IOutputBuffer;
import org.eclipse.epsilon.eol.dom.StatementBlock;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;

/**
 * Manipulates a {@link FrameStack} to provide a slightly more 
 * sophisticated semantics for EGL. Specifically, the 
 * {@link FrameStack} is manipulated as follows when a template
 * is to be executed:
 * <ol>
 * 	<li> The client calls
 *       {@link #prepareFor(ExecutableTemplateSpecification, FrameStack)}
 *       which ensures that the global variables of the parent template
 *       (if any) are accessible to the child, unless they are overwritten.
 *       Note that {@link ExecutableTemplateSpecification#addVariablesTo(FrameStack)}
 *       is called at this point to initialise any template-specific
 *       variables, such as <code>out</code> (the template-specific
 *       global output buffer variable).</li>
 *  <li> The {@link FrameStack} is now setup for the specified
 *       template, and the client executes the template.</li>
 *  <li> The client calls {@link #restore()}
 *       to restore the previous set of global variables to the
 *       {@link FrameStack}. Any global variables created for the
 *       this template are cleared. The {@link FrameStack} is now
 *       setup for executing the parent template, which continues
 *       to execute.</li>
 *  </ol>
 */
class EglExecutionManager {

	public EglExecutionManager(IEglContext context) {
		this.context = context;
	}
	
	final IEglContext context;
	FrameStack frameStack;
	final Deque<ModuleElement> localMarkers = new ArrayDeque<>();
	final Deque<ModuleElement> globalMarkers = new ArrayDeque<>();
	final Deque<ExecutableTemplateSpecification> specs = new LinkedList<>();
	private ExecutableTemplateSpecification firstSpec;
	
	public void prepareFor(EglTemplate template) {
		prepareFor(template, context.newOutputBuffer());
	}
	
	public void prepareFor(EglTemplate template, IOutputBuffer outputBuffer) {
		ExecutableTemplateSpecification spec = new ExecutableTemplateSpecification(template, outputBuffer);
		if (firstSpec == null) firstSpec = spec;
		if (!specs.isEmpty()) getCurrent().addAsChild(spec);
		specs.push(spec);
		setModule();
		prepareFrameStack();
		spec.addVariablesTo(frameStack);
	}
	
	private void prepareFrameStack() {
		frameStack = context.getFrameStack();
		final StatementBlock frameGlobalStackMarker = new StatementBlock();
		frameGlobalStackMarker.setUri(URI.create("template-specific-globals"));
		globalMarkers.push(frameGlobalStackMarker);
		frameStack.enterGlobal(FrameType.UNPROTECTED, frameGlobalStackMarker);
		final StatementBlock frameLocalStackMarker = new StatementBlock();
		frameLocalStackMarker.setUri(URI.create("locals"));
		localMarkers.push(frameLocalStackMarker);
		frameStack.enterGlobal(FrameType.UNPROTECTED, frameLocalStackMarker);
	}
	
	private void restoreFrameStack() {
		frameStack.leaveGlobal(localMarkers.pop());
		frameStack.leaveGlobal(globalMarkers.pop());
	}
	
	void setModule() {
		context.setModule(getCurrent().template.getModule());
	}
	
	public void restore() {
		setModule();
		restoreFrameStack();
		specs.pop();
	}

	public ExecutableTemplateSpecification getCurrent() {
		return specs.peek();
	}

	public ExecutableTemplateSpecification getBase() {
		return firstSpec;
	}
}
