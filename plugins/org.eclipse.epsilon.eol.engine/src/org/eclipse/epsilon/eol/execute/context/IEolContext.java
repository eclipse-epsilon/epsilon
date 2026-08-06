/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.context;

import java.io.PrintStream;
import java.util.List;
import java.util.Queue;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.introspection.IntrospectionManager;
import org.eclipse.epsilon.eol.execute.operations.EolOperationFactory;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributorRegistry;
import org.eclipse.epsilon.eol.execute.prettyprinting.PrettyPrinterManager;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.eol.types.IToolNativeTypeDelegate;
import org.eclipse.epsilon.eol.userinput.IUserInput;

public interface IEolContext {
	
	void setUserInput(IUserInput userInput);
	
	IUserInput getUserInput();
	
	PrettyPrinterManager getPrettyPrinterManager();
	
	void setPrettyPrinterManager(PrettyPrinterManager prettyPrinterManager);

	PrintStream getOutputStream();

	void setOutputStream(PrintStream outputStream);

	PrintStream getWarningStream();

	void setWarningStream(PrintStream warningStream);

	EolOperationFactory getOperationFactory();

	void setOperationFactory(EolOperationFactory operationFactory);

	ExecutorFactory getExecutorFactory();

	void setExecutorFactory(ExecutorFactory executorFactory);
	
	ModelRepository getModelRepository();
	
	void setModelRepository(ModelRepository modelRepository);
	
	FrameStack getFrameStack();
	
	void setFrameStack(FrameStack scope);

	IntrospectionManager getIntrospectionManager();

	void setIntrospectionManager(IntrospectionManager introspectionManager);

	PrintStream getErrorStream();

	void setErrorStream(PrintStream defaultErrorStream);
	
	void setModule(IModule module);
	
	IModule getModule();

	void setNativeTypeDelegates(List<IToolNativeTypeDelegate> nativeTypeDelegates);
	
	List<IToolNativeTypeDelegate> getNativeTypeDelegates();
	
	boolean isProfilingEnabled();
	
	void setProfilingEnabled(boolean profilingEnabled);
	
	boolean isAssertionsEnabled();
	
	void setAssertionsEnabled(boolean assertionsEnabled);
	
	ExtendedProperties getExtendedProperties();
	
	void setExtendedProperties(ExtendedProperties properties);
	
	void dispose();
	
	/**
	 * 
	 * @return
	 * @since 1.6
	 */
	Queue<AsyncStatementInstance> getAsyncStatementsQueue();
	
	OperationContributorRegistry getOperationContributorRegistry();

	/**
	 * URI resolvers to use prior to the default resolver.
	 * 
	 * @since 2.9
	 */
	List<IModuleURIResolver> getURIResolvers();
}
