/*******************************************************************************
 * Copyright (c) 2008-2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - Refactoring + concurrency + parameterised execution
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import org.eclipse.epsilon.common.function.BaseDelegate;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElementParameter;
import org.eclipse.epsilon.eol.exceptions.EolInternalException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.flowcontrol.EolTerminationException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.control.*;

/**
 * This class is not thread-safe.
 * Use serial thread confinement (ThreadLocal) if using from multiple threads.
 * 
 */
public class ExecutorFactory implements BaseDelegate<ExecutorFactory> {
	
	private ExecutionController executionController;
	private ModuleElement activeModuleElement;
	private Collection<IExecutionListener> executionListeners;
	private ExecutorFactory base;
	private StackTraceManager stackTraceManager;
	
	public ExecutorFactory() {
		this(null);
	}
	
	/**
	 * 
	 * @param parent
	 * @since 1.6
	 */
	public ExecutorFactory(ExecutorFactory parent) {
		this.base = parent;
		executionController = new DefaultExecutionController();
		enableStackTraceManager();
	}
	
	/**
	 * @since 1.6
	 */
	public final void enableStackTraceManager() {
		stackTraceManager = new StackTraceManager();
	}
	
	/**
	 * @since 1.6
	 */
	public final void disableStackTraceManager() {
		stackTraceManager = null;
	}
	
	public final StackTraceManager getStackTraceManager() {
		return stackTraceManager;
	}
	
	public void addExecutionListener(IExecutionListener listener) {
		if (executionListeners == null) {
			executionListeners = new ArrayList<>(1);
		}
		executionListeners.add(listener);
	}
	
	public Collection<IExecutionListener> getExecutionListeners() {
		return executionListeners == null ? Collections.emptyList() :
			Collections.unmodifiableCollection(executionListeners);
	}
	
	public boolean removeExecutionListener(IExecutionListener listener) {
		return executionListeners != null && executionListeners.remove(listener);
	}
	
	public ExecutionController getExecutionController() {
		return executionController;
	}

	public void setExecutionController(ExecutionController executionController) {
		this.executionController = executionController;
	}
	
	/**
	 * 
	 * @return
	 * @since 1.6
	 */
	public boolean isProfilingEnabled() {
		return executionController instanceof ExecutionProfiler;
	}
	
	/**
	 * 
	 * @param moduleElement
	 * @param context
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	protected void preExecute(ModuleElement moduleElement, IEolContext context) throws EolRuntimeException {
		activeModuleElement = moduleElement;
		
		if (executionController != null) {
			if (executionController.isTerminated()) throw new EolTerminationException(moduleElement);
			try {
				executionController.control(moduleElement, context);
			}
			catch (Exception ex) {
				throw new EolInternalException(ex);
			} 
		}
		
		if (stackTraceManager != null) {
			stackTraceManager.stackTrace.push(moduleElement);
		}
		
		if (executionListeners != null) for (IExecutionListener listener : executionListeners) {
			listener.aboutToExecute(moduleElement, context);
		}
	}
	
	/**
	 * Overriding classes must call this super method.
	 * 
	 * @param moduleElement
	 * @param result
	 * @param context
	 * @since 1.6
	 */
	protected void postExecuteSuccess(ModuleElement moduleElement, Object result, IEolContext context) {
		if (executionListeners != null) for (IExecutionListener listener : executionListeners) {
			listener.finishedExecuting(moduleElement, result, context);
		}
		if (stackTraceManager != null) {
			stackTraceManager.stackTrace.pop();
		}
	}
	
	/**
	 * Overriding classes must call this super method.
	 * 
	 * @param moduleElement
	 * @param ex
	 * @param context
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	protected void postExecuteFailure(ModuleElement moduleElement, Exception ex, IEolContext context) throws EolRuntimeException {
		EolRuntimeException exception = null;
		if (ex instanceof EolRuntimeException) {
			EolRuntimeException eolEx = (EolRuntimeException) ex;
			if (eolEx.getAst() == null) {
				eolEx.setAst(moduleElement);
			}
			exception = eolEx;
		}
		else {
			exception = new EolInternalException(ex, moduleElement);
		}
		if (executionListeners != null) for (IExecutionListener listener : executionListeners) {
			listener.finishedExecutingWithException(moduleElement, exception, context);
		}

		throw exception;
	}
	
	/**
	 * Overriding classes must call this super method.
	 * 
	 * @param moduleElement
	 * @param context
	 * @since 1.6
	 */
	protected void postExecuteFinally(ModuleElement moduleElement, IEolContext context) {
		if (executionController != null) {
			executionController.done(moduleElement, context);
		}
	}
	
	/**
	 * Overriding classes should call this super method.
	 * 
	 * @param moduleElement
	 * @param context
	 * @return
	 * @throws Exception
	 * @since 1.6
	 */
	protected Object executeImpl(ModuleElement moduleElement, IEolContext context) throws Exception {
		if (moduleElement instanceof IExecutableModuleElement) {
			return ((IExecutableModuleElement) moduleElement).execute(context);
		}
		else if (moduleElement instanceof EolModule) {
			return ((EolModule) moduleElement).executeImpl();
		}
		else return null;
	}
	
	/**
	 * 
	 * @param moduleElement
	 * @param context
	 * @param self
	 * @return
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	protected Object executeImpl(IExecutableModuleElementParameter moduleElement, IEolContext context, Object parameter) throws EolRuntimeException {
		return moduleElement.execute(context, parameter);
	}
	
	public final Object execute(ModuleElement moduleElement, IEolContext context) throws EolRuntimeException {	
		if (moduleElement == null) return null;
		
		preExecute(moduleElement, context);
		
		Object result = null;
		
		try {
			result = executeImpl(moduleElement, context);
			postExecuteSuccess(moduleElement, result, context);
		}
		catch (Exception ex) {
			postExecuteFailure(moduleElement, ex, context);
		}
		finally {
			postExecuteFinally(moduleElement, context);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @param moduleElement
	 * @param context
	 * @param self
	 * @return
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	public final Object execute(IExecutableModuleElementParameter moduleElement, IEolContext context, Object parameter) throws EolRuntimeException {
		if (moduleElement == null) return null;
		
		preExecute(moduleElement, context);
		
		Object result = null;
		
		try {
			result = executeImpl(moduleElement, context, parameter);
			postExecuteSuccess(moduleElement, result, context);
		}
		catch (Exception ex) {
			postExecuteFailure(moduleElement, ex, context);
		}
		finally {
			postExecuteFinally(moduleElement, context);
		}
		
		return result;
	}
	
	public ModuleElement getActiveModuleElement() {
		return activeModuleElement;
	}
	
	/**
	 * 
	 * @param parent
	 * @since 1.6
	 */
	public void setBase(ExecutorFactory parent) {
		this.base = parent;
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public ExecutorFactory getBase() {
		return base;
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public void merge(MergeMode mode) {
		mergeCollectionsUnique(
			ef -> ef.executionListeners,
			ArrayList::new,
			mode
		);
	}

	/**
	 * @since 1.6
	 */
	public void dispose() {
		activeModuleElement = null;
		if (stackTraceManager != null) stackTraceManager.dispose();
		if (executionController != null) executionController.dispose();
		if (executionListeners != null) executionListeners.clear();
	}
}
