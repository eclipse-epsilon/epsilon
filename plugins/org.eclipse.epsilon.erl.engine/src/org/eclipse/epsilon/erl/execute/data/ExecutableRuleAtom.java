/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.erl.execute.data;

import java.util.concurrent.Callable;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElementParameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.function.CheckedEolRunnable;
import org.eclipse.epsilon.eol.function.CheckedEolSupplier;
import org.eclipse.epsilon.erl.execute.context.IErlContext;

/**
 * Standalone {@link RuleAtom} which can be submitted to an ExecutorService.
 * This has all necessary information for executing the RuleAtom, and avoids
 * the need for creating a separate anonymous inner class or lambda.
 *
 * @author Sina Madani
 * @since 1.6
 * @param <T>
 */
public class ExecutableRuleAtom<T extends IExecutableModuleElementParameter>
		extends RuleAtom<T> implements CheckedEolRunnable, Callable<Object>, CheckedEolSupplier<Object> {

	protected IErlContext context;
	
	public ExecutableRuleAtom(T construct, Object modelElement, IErlContext context) {
		this(construct, modelElement);
		this.context = context;
	}
	
	protected ExecutableRuleAtom(T construct, Object modelElement) {
		super(construct, modelElement);
	}
	
	protected Object execute() throws EolRuntimeException {
		if (context == null)
			throw new IllegalStateException("Context cannot be null!");
		return execute(context);
	}
	
	@Override
	public final Object call() throws Exception {
		return execute();
	}
	
	@Override
	public final void runThrows() throws EolRuntimeException {
		execute();
	}
	
	@Override
	public final Object getThrows() throws EolRuntimeException {
		return execute();
	}
}
