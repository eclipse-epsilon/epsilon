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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.uris.FileUpURIResolver;
import org.eclipse.epsilon.eol.execute.context.uris.JarUpURIResolver;
import org.eclipse.epsilon.eol.execute.context.uris.SegmentUpURIResolver;
import org.eclipse.epsilon.eol.execute.introspection.IntrospectionManager;
import org.eclipse.epsilon.eol.execute.operations.EolOperationFactory;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributorRegistry;
import org.eclipse.epsilon.eol.execute.prettyprinting.PrettyPrinterManager;
import org.eclipse.epsilon.eol.models.ModelRepository;
import org.eclipse.epsilon.eol.types.EolClasspathNativeTypeDelegate;
import org.eclipse.epsilon.eol.types.IToolNativeTypeDelegate;
import org.eclipse.epsilon.eol.userinput.IUserInput;
import org.eclipse.epsilon.eol.userinput.JavaConsoleUserInput;

public class EolContext implements IEolContext {
	
	protected IUserInput userInput;
	protected FrameStack frameStack;
	protected ModelRepository modelRepository;
	protected IntrospectionManager introspectionManager;
	protected ExecutorFactory executorFactory;
	protected EolOperationFactory operationFactory;
	protected PrettyPrinterManager prettyPrinterManager;
	protected PrintStream outputStream, errorStream, warningStream;
	protected IModule module;
	protected boolean profilingEnabled = false;
	protected boolean assertionsEnabled = true;
	protected ExtendedProperties extendedProperties;
	protected Queue<AsyncStatementInstance> asyncStatementsQueue;
	protected OperationContributorRegistry methodContributorRegistry;
	protected EolClasspathNativeTypeDelegate classpathNativeTypeDelegate;
	protected List<IToolNativeTypeDelegate> nativeTypeDelegates;
	protected List<IModuleURIResolver> uriResolvers;
	
	/**
	 * The type of {@link #module} when using {@link #getModule()} and {@link #setModule(IModule)}.
	 * @since 1.6
	 */
	Class<? extends IModule> expectedModuleType = IModule.class;
	
	@SuppressWarnings("unchecked")
	public EolContext() {
		userInput = new JavaConsoleUserInput();
		frameStack = new FrameStack();
		modelRepository = new ModelRepository();
		introspectionManager = new IntrospectionManager();
		executorFactory = new ExecutorFactory();
		operationFactory = new EolOperationFactory();
		prettyPrinterManager = new PrettyPrinterManager();
		outputStream = System.out;
		errorStream = System.err;
		warningStream = System.out;
		extendedProperties = new ExtendedProperties();
		asyncStatementsQueue = new LinkedList<>();
		methodContributorRegistry = new OperationContributorRegistry();
		classpathNativeTypeDelegate = new EolClasspathNativeTypeDelegate();
		nativeTypeDelegates = CollectionUtil.asList(classpathNativeTypeDelegate);

		uriResolvers = new ArrayList<>();
		uriResolvers.add(new FileUpURIResolver());
		uriResolvers.add(new JarUpURIResolver());
		uriResolvers.add(new SegmentUpURIResolver());

		// Ensure that setModule is consistent with getModule
		try {
			expectedModuleType = (Class<? extends IModule>) getClass().getMethod("getModule").getReturnType();
		}
		catch (NoSuchMethodException | SecurityException | ClassCastException ex) {
			// Use the default - no need to do anything here.
		}
	}

	/**
	 * Copy constructor, intended for internal use only.
	 * 
	 * @param other The parent context.
	 * @since 1.6
	 * @author Sina Madani
	 */
	public EolContext(IEolContext other) {
		userInput = other.getUserInput();
		modelRepository = other.getModelRepository();
		introspectionManager = other.getIntrospectionManager();
		operationFactory = other.getOperationFactory();
		prettyPrinterManager = other.getPrettyPrinterManager();
		outputStream = other.getOutputStream();
		errorStream = other.getErrorStream();
		warningStream = other.getWarningStream();
		profilingEnabled = other.isProfilingEnabled();
		assertionsEnabled = other.isAssertionsEnabled();
		extendedProperties = other.getExtendedProperties();
		asyncStatementsQueue = other.getAsyncStatementsQueue();
		nativeTypeDelegates = other.getNativeTypeDelegates();
		// Note that if other instanceof EolContextParallel && other.isParallel(), then
		// the following three will be ThreadLocal references as desired.
		methodContributorRegistry = other.getOperationContributorRegistry();
		frameStack = other.getFrameStack();
		executorFactory = other.getExecutorFactory();
		uriResolvers = other.getURIResolvers();
		
		IModule otherModule = other.getModule();
		if (expectedModuleType.isInstance(otherModule)) {
			this.module = otherModule;
		}
		
		if (other instanceof EolContext) {
			classpathNativeTypeDelegate = ((EolContext) other).classpathNativeTypeDelegate;
		}
		else {
			classpathNativeTypeDelegate = new EolClasspathNativeTypeDelegate(other.getClass().getClassLoader());
		}
	}

	@Override
	public OperationContributorRegistry getOperationContributorRegistry() {
		return methodContributorRegistry;
	}
	
	@Override
	public PrintStream getWarningStream() {
		return warningStream;
	}

	@Override
	public void setWarningStream(PrintStream warningStream) {
		this.warningStream = warningStream;
	}

	@Override
	public boolean isAssertionsEnabled() {
		return assertionsEnabled;
	}

	@Override
	public void setAssertionsEnabled(boolean assertionsEnabled) {
		this.assertionsEnabled = assertionsEnabled;
	}

	@Override
	public PrettyPrinterManager getPrettyPrinterManager() {
		return prettyPrinterManager;
	}
	
	@Override
	public void setPrettyPrinterManager(PrettyPrinterManager prettyPrinterManager) {
		this.prettyPrinterManager = prettyPrinterManager;
	}

	@Override
	public PrintStream getOutputStream() {
		return outputStream;
	}

	@Override
	public void setOutputStream(PrintStream outputStream) {
		this.outputStream = outputStream;
	}

	@Override
	public EolOperationFactory getOperationFactory() {
		return operationFactory;
	}

	@Override
	public void setOperationFactory(EolOperationFactory operationFactory) {
		this.operationFactory = operationFactory;
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
		return modelRepository;
	}
	
	@Override
	public void setModelRepository(ModelRepository modelRepository) {
		this.modelRepository = modelRepository;
	}
	
	@Override
	public FrameStack getFrameStack() {
		return frameStack;
	}
	
	@Override
	public void setFrameStack(FrameStack frameStack) {
		this.frameStack = frameStack;
	}

	@Override
	public IntrospectionManager getIntrospectionManager() {
		return introspectionManager;
	}

	@Override
	public void setIntrospectionManager(IntrospectionManager introspectionManager) {
		this.introspectionManager = introspectionManager;
	}

	@Override
	public PrintStream getErrorStream() {
		return errorStream;
	}

	@Override
	public void setErrorStream(PrintStream errorStream) {
		this.errorStream = errorStream;
	}

	@Override
	public void setModule(IModule module) {
		if (module != null && !expectedModuleType.isInstance(module)) {
			throw new IllegalArgumentException(
				"Invalid module type: expected "+expectedModuleType.getName()
				+ " but got "+module.getClass().getName()
			);
		}
		this.module = module;
	}

	@Override
	public IModule getModule() {
		return module;
	}
	
	//TODO : Do something with the user input
	@Override
	public void setUserInput(IUserInput userInput) {
		String userInputVarName = "UserInput";
		this.userInput = userInput;
		FrameStack fs = getFrameStack();
		Variable variable = fs.get(userInputVarName);
		if (variable == null) {
			variable = Variable.createReadOnlyVariable(userInputVarName, userInput);
			variable.setDeprecationInfo("Variable UserInput is deprecated. Use System.user instead.");
			fs.putGlobal(variable);
		}
		else {
			variable.setValueBruteForce(userInput);
		}
	}

	@Override
	public IUserInput getUserInput() {
		return userInput;
	}

	@Override
	public List<IToolNativeTypeDelegate> getNativeTypeDelegates() {
		return nativeTypeDelegates;
	}

	@Override
	public void setNativeTypeDelegates(List<IToolNativeTypeDelegate> nativeTypeDelegates) {
		this.nativeTypeDelegates = nativeTypeDelegates;
	}

	@Override
	public boolean isProfilingEnabled() {
		return profilingEnabled;
	}

	@Override
	public void setProfilingEnabled(boolean profilingEnabled) {
		this.profilingEnabled = profilingEnabled;
	}

	@Override
	public ExtendedProperties getExtendedProperties() {
		return extendedProperties;
	}

	@Override
	public void setExtendedProperties(ExtendedProperties extendedProperties) {
		this.extendedProperties = extendedProperties;
	}
	
	@Override
	public void dispose() {
		executorFactory.dispose();
		extendedProperties.clear();
	}

	@Override
	public Queue<AsyncStatementInstance> getAsyncStatementsQueue() {
		return asyncStatementsQueue;
	}

	@Override
	public List<IModuleURIResolver> getURIResolvers() {
		return uriResolvers;
	}

}
