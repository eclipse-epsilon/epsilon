/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.exception;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.emc.simulink.engine.MatlabClassLoader;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;

public class MatlabException extends Exception {

	private static final long serialVersionUID = -2998670342449368598L;

	private static final String MATLAB_ENGINE_EXCEPTION_CLASS = "com.mathworks.engine.EngineException";
	private static final String MATLAB_EXECUTION_EXCEPTION_CLASS = "com.mathworks.engine.MatlabExecutionException";
	private static final String MATLAB_SYNTAX_EXCEPTION_CLASS = "com.mathworks.engine.MatlabSyntaxException";
	private static final String MATLAB_UNSUPORTED_TYPE_EXCEPTION_CLASS = "com.mathworks.engine.UnsupportedTypeException";
	private static final String MATLAB_RUNTIME_EXCEPTION_CLASS = "com.mathworks.mvm.exec.MvmRuntimeException";
		
	protected static Class<?> engineClass, executionClass, syntaxClass, typeClass, runtimeClass;
	
	protected Boolean isEngine, isExecution, isSyntax, isType, isRuntime;

	private static final String TOO_MANY_OUTPUT = "Too many output arguments";
		
	public MatlabException(InvocationTargetException e) {
		super(e.getTargetException());
		isEngine = isEngineException(e.getTargetException());
		isExecution = isExecutionException(e.getTargetException());
		isSyntax = isSyntaxException(e.getTargetException());
		isType = isUnsupportedTypeException(e.getTargetException());
		isRuntime = isRuntimeException(e.getTargetException());
	}

	public boolean isTooManyOutput() {
		return getMessage().contains(TOO_MANY_OUTPUT) || (getCause()!=null) ? getCause().getMessage().contains(TOO_MANY_OUTPUT) : false;
	}
	
	@SuppressWarnings("unused")
	private static Class<?> load(String className) throws ClassNotFoundException {
		return MatlabClassLoader.getInstance().loadMatlabClass(className);
	}
	
	public static Boolean isEngineException(Throwable e) {
		return MATLAB_ENGINE_EXCEPTION_CLASS.equals(e.getClass().getName());
	}
	
	public static Boolean isExecutionException(Throwable e) {
		return MATLAB_EXECUTION_EXCEPTION_CLASS.equals(e.getClass().getName());
	}
	
	public static Boolean isSyntaxException(Throwable e) {
		return MATLAB_SYNTAX_EXCEPTION_CLASS.equals(e.getClass().getName());
	}
	
	public static Boolean isUnsupportedTypeException(Throwable e) {
		return MATLAB_UNSUPORTED_TYPE_EXCEPTION_CLASS.equals(e.getClass().getName());
	}
	
	public static Boolean isRuntimeException(Throwable e) {
		return MATLAB_RUNTIME_EXCEPTION_CLASS.equals(e.getClass().getName());
	}
	
	public Boolean isEngineException() {
		return isEngine;
	}
	
	public Boolean isExecutionException() {
		return isExecution;
	}
	
	public Boolean isSyntaxException() {
		return isSyntax;
	}
	
	public Boolean isUnsupportedTypeException() {
		return isType;
	}
	
	public Boolean isRuntimeException(){
		return isRuntime;
	}
	
	public EolRuntimeException toEolRuntimeException(){
		EolRuntimeException eol = new EolRuntimeException(this);
		eol.setReason(getMessage());
		return eol;
	}
	
	public EolRuntimeException toEolRuntimeException(ModuleElement ast){
		EolRuntimeException eol = toEolRuntimeException(); 
		eol.setAst(ast);
		return eol;
	}
	
	@Override
	public String getMessage() {
		return (getCause()!=null)? ((getCause().getCause()!=null) ? getCause().getCause().getMessage() : getCause().getMessage()): super.getMessage();
	}
	
}