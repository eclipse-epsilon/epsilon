/*******************************************************************************
 * Copyright (c) 2025 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap;

import java.io.PrintStream;
import java.util.List;
import java.util.Queue;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.AsyncStatementInstance;
import org.eclipse.epsilon.eol.execute.context.ExtendedProperties;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.IntrospectionManager;
import org.eclipse.epsilon.eol.execute.operations.EolOperationFactory;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributorRegistry;
import org.eclipse.epsilon.eol.execute.prettyprinting.PrettyPrinterManager;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.eol.types.IToolNativeTypeDelegate;
import org.eclipse.epsilon.eol.userinput.IUserInput;

/**
 * Wrapper over an existing context for evaluating expressions within a debugged
 * program. It reuses most of the existing context, but it works over a copy of
 * the original frame stack, and it uses the default (non-debugging) execution
 * controller over the original ExecutorFactory.
 */
public class EvaluatorContext implements IEolContext {

	private final IEolContext delegate;
	private ExecutorFactory executorFactory;
	private FrameStack frameStack;

	public EvaluatorContext(IEolContext context) {
		this.delegate = context;

		frameStack = context.getFrameStack().clone();
		executorFactory = new ExecutorFactory(context.getExecutorFactory());
	}

	@Override
	public void setUserInput(IUserInput userInput) {
		delegate.setUserInput(userInput);
	}

	@Override
	public IUserInput getUserInput() {
		return delegate.getUserInput();
	}

	@Override
	public PrettyPrinterManager getPrettyPrinterManager() {
		return delegate.getPrettyPrinterManager();
	}

	@Override
	public void setPrettyPrinterManager(PrettyPrinterManager prettyPrinterManager) {
		delegate.setPrettyPrinterManager(prettyPrinterManager);
	}

	@Override
	public PrintStream getOutputStream() {
		return delegate.getOutputStream();
	}

	@Override
	public void setOutputStream(PrintStream outputStream) {
		delegate.getOutputStream();
	}

	@Override
	public PrintStream getWarningStream() {
		return delegate.getWarningStream();
	}

	@Override
	public void setWarningStream(PrintStream warningStream) {
		delegate.setWarningStream(warningStream);
	}

	@Override
	public EolOperationFactory getOperationFactory() {
		return delegate.getOperationFactory();
	}

	@Override
	public void setOperationFactory(EolOperationFactory operationFactory) {
		delegate.setOperationFactory(operationFactory);
	}

	@Override
	public ExecutorFactory getExecutorFactory() {
		return executorFactory;
	}

	@Override
	public void setExecutorFactory(ExecutorFactory executorFactory) {
		this.executorFactory = executorFactory;
	}

	@Override
	public ModelRepository getModelRepository() {
		return delegate.getModelRepository();
	}

	@Override
	public void setModelRepository(ModelRepository modelRepository) {
		delegate.setModelRepository(modelRepository);
	}

	@Override
	public FrameStack getFrameStack() {
		return frameStack;
	}

	@Override
	public void setFrameStack(FrameStack scope) {
		this.frameStack = scope;
	}

	@Override
	public IntrospectionManager getIntrospectionManager() {
		return delegate.getIntrospectionManager();
	}

	@Override
	public void setIntrospectionManager(IntrospectionManager introspectionManager) {
		delegate.setIntrospectionManager(introspectionManager);
	}

	@Override
	public PrintStream getErrorStream() {
		return delegate.getErrorStream();
	}

	@Override
	public void setErrorStream(PrintStream defaultErrorStream) {
		delegate.setErrorStream(defaultErrorStream);
	}

	@Override
	public void setModule(IModule module) {
		delegate.setModule(module);
	}

	@Override
	public IModule getModule() {
		return delegate.getModule();
	}

	@Override
	public void setNativeTypeDelegates(List<IToolNativeTypeDelegate> nativeTypeDelegates) {
		delegate.setNativeTypeDelegates(nativeTypeDelegates);
	}

	@Override
	public List<IToolNativeTypeDelegate> getNativeTypeDelegates() {
		return delegate.getNativeTypeDelegates();
	}

	@Override
	public boolean isProfilingEnabled() {
		return delegate.isProfilingEnabled();
	}

	@Override
	public void setProfilingEnabled(boolean profilingEnabled) {
		delegate.setProfilingEnabled(profilingEnabled);
	}

	@Override
	public boolean isAssertionsEnabled() {
		return delegate.isAssertionsEnabled();
	}

	@Override
	public void setAssertionsEnabled(boolean assertionsEnabled) {
		delegate.setAssertionsEnabled(assertionsEnabled);
	}

	@Override
	public ExtendedProperties getExtendedProperties() {
		return delegate.getExtendedProperties();
	}

	@Override
	public void setExtendedProperties(ExtendedProperties properties) {
		delegate.setExtendedProperties(properties);
	}

	@Override
	public void dispose() {
		// do nothing - we don't want to dispose the base context after evaluating an expression
	}

	@Override
	public Queue<AsyncStatementInstance> getAsyncStatementsQueue() {
		return delegate.getAsyncStatementsQueue();
	}

	@Override
	public OperationContributorRegistry getOperationContributorRegistry() {
		return delegate.getOperationContributorRegistry();
	}

}
