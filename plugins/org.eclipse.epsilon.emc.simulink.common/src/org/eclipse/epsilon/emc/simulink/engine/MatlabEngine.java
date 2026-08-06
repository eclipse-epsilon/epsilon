/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.engine;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.epsilon.emc.simulink.exception.EpsilonSimulinkInternalException;
import org.eclipse.epsilon.emc.simulink.exception.MatlabException;
import org.eclipse.epsilon.emc.simulink.exception.MatlabRuntimeException;
import org.eclipse.epsilon.emc.simulink.model.IGenericSimulinkModel;
import org.eclipse.epsilon.emc.simulink.util.MatlabEngineUtil;
import org.eclipse.epsilon.profiling.Stopwatch;

@SuppressWarnings("unchecked")
public class MatlabEngine {

	/**
	 * 
	 */
	private static final String NL = System.lineSeparator();

	/* MATHWOWRKS CLASS */
	private static final String MATLAB_ENGINE_CLASS = "com.mathworks.engine.MatlabEngine";
	
	/* ENGINE STATIC METHODS */
	private static final String CONNECT_MATLAB_METHOD = "connectMatlab";
	private static final String START_MATLAB_METHOD = "startMatlab";
	private static final String FIND_MATLAB_METHOD = "findMatlab";

	/* ASYNC 
	private static final String CONNECT_MATLAB_ASYNC_METHOD = "connectMatlabAsync";
	private static final String START_MATLAB_ASYNC_METHOD = "connectMatlabAsync";
	private static final String FIND_MATLAB_ASYNC_METHOD = "connectMatlabAsync";
	 */

	/* ENGINE METHODS */
	private static final String GET_VARIABLE_METHOD = "getVariable";
	private static final String PUT_VARIABLE_METHOD = "putVariable";
	private static final String EVAL_METHOD = "eval";
	private static final String EVAL_ASYNC_METHOD = "evalAsync";
	private static final String FEVAL_METHOD = "feval";
	private static final String DISCONNECT_METHOD = "disconnect";
	private static final String QUIT_METHOD = "quit";
	private static final String CLOSE_METHOD = "close";

	private static final String RESULT = "result";
	private static final String ASIGN = " = ";
	private static final String PARAM_REGEX = "[?]";

	/* ASYNC
	
	private static final String GET_VARIABLE_ASYNC_METHOD = "getVariableAsync";
	private static final String PUT_VARIABLE_ASYNC_METHOD = "putVariableAsync";
	private static final String DISCONNECT_ASYNC_METHOD = "disconnectAsync";
	private static final String QUIT_ASYNC_METHOD = "quitAsync";
	 */
	private static final String FEVAL_ASYNC_METHOD = "fevalAsync";
	
	/* ERRORS AND LOGS */
	private static final String ERROR_INVALID_PARAMETER_NUMBER = "%d parameters were expected but %d were provided";

	
	/* VARIABLES */
	protected Object engine;
	private static Class<?> engine_class;

	/* METHODS */
	protected Method getVariableMethod;
	protected Method getVariableAsyncMethod;
	protected Method putVariableMethod;
	protected Method putVariableAsyncMethod;
	protected Method evalMethod;
	protected Method evalAsyncMethod;
	protected Method fevalMethod;
	protected Method fevalWithVariableOutputsMethod;
	protected Method fevalAsyncMethod;
	protected Method closeMethod;
	protected Method quitMethod;
	protected Method disconnectMethod;
	
	protected String project;
	protected Set<IGenericSimulinkModel> models = new HashSet<>();
	protected StringBuilder evalCommandQueue = new StringBuilder();
	protected Boolean tryCatchEnabled = true;
	protected StringBuilder sb;
	protected Stopwatch stopWatch;

	private boolean reduceExchanges;

	public boolean isDisconnected() throws Exception{
		Field declaredField = engine_class.getDeclaredField("fDisconnected");
		declaredField.setAccessible(true);
		AtomicBoolean disconnected = (AtomicBoolean) declaredField.get(engine);
		return disconnected.get();
	}
	
	/**
	 * @return the stopWatch
	 */
	public Stopwatch getStopWatch() {
		return stopWatch;
	}
	
	public static void setEngineClass(Class<?> matlabEngineClass) {
		engine_class = matlabEngineClass;
	}
	
	public void trackApi(boolean track){
		sb = (track) ? new StringBuilder() : null;
	}
	
	public StringBuilder getStream(){
		return sb;
	}
	
	public void setProject(String project) throws MatlabException {
		this.project = project;
		if (project.equals("current")) {
			eval("currentProject;");
		} else {			
			File proj = new File(project);
			eval("cd '?';", proj.getParent());
			String location = proj.getName().substring(0, proj.getName().indexOf('.'));
			eval("simulinkproject('?');", location);
		}
	}
	
	public void addModel(IGenericSimulinkModel model) {
		this.models.add(model);
	}
	
	
	public String getProject() {
		return project;
	}
	
	public void release(IGenericSimulinkModel model) throws MatlabRuntimeException {
		if (project != null && !models.isEmpty()) {
			models.remove(model);
		}
		if (models.isEmpty()) {
			this.project = null;
			MatlabEnginePool.getInstance().release(this);
		}
	}
	
	public MatlabEngine() throws Exception  {
		if (engine_class == null) throw new IllegalStateException("Engine Class not yet set");
		try {
			engine = engine_class.getMethod(CONNECT_MATLAB_METHOD).invoke(null);
		} catch (InvocationTargetException e) {
			try {
				engine = engine_class.getMethod(START_MATLAB_METHOD).invoke(null);
			} catch (InvocationTargetException ex) {
				throw new MatlabException(e);
			} catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException ex) {
				throw new EpsilonSimulinkInternalException(e);
			}
		} catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException e) {
			throw new EpsilonSimulinkInternalException(e);
		}
	}
	
	public void enableTryCatch(boolean enableTryCatch) {
		this.tryCatchEnabled = enableTryCatch;
	}
	public Boolean isTryCatchEnabled() {
		return tryCatchEnabled;
	}

	public Object evalWithSetupAndResult(String setup, String cmd, Object... parameters) throws MatlabException {
		eval(setup + (setup.endsWith(";") ? "" : ";") + RESULT + ASIGN + cmd, parameters);
		return getVariable(RESULT);
	}

	public Object evalWithResult(String cmd) throws MatlabException {
		eval(RESULT + ASIGN + cmd);
		return getVariable(RESULT);
	}

	public Object evalWithResult(String cmd, Object... parameters) throws MatlabException {
		eval(RESULT + ASIGN + cmd, parameters);
		return getVariable(RESULT);
	}

	/**
	 * This method is now lazily executed. All commands are stored in a queue and are only dispatched when 
	 * the method {@link #flush()} is invoked.
	 * @param cmd
	 * @param parameters
	 * @throws MatlabException
	 */
	public void eval(String cmd, Object... parameters) throws MatlabException {
		cmd = " " + cmd + " ";
		String[] parts = cmd.split(PARAM_REGEX);
		if (parts.length != parameters.length + 1) {
			String error = String.format(ERROR_INVALID_PARAMETER_NUMBER, parts.length - 1, parameters.length);
			throw new RuntimeException(error);
		}
		cmd = parts[0];
		for (int i = 0; i < parameters.length; i++) {
			cmd += String.valueOf(parameters[i]).replace("'", "''").replace("\n", "\\n") + parts[i + 1];
		}
		cmd = cmd.substring(1, cmd.length() - 1);
		eval(cmd);
	}

	// CLASS HELPERS 
	public static boolean is(Object obj) {
		return (getMatlabClass() == null) ? false : getMatlabClass().isInstance(obj);  
	}
	
	protected static Class<?> getMatlabClass() {
		if (engine_class == null) {
			try {
				engine_class = MatlabClassLoader.getInstance().loadMatlabClass(MATLAB_ENGINE_CLASS);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return engine_class;
	}
	
	public MatlabEngine(Object engine) {
		if (is(engine)) {
			this.engine = engine;
		}
	}
	
	protected Object processInputObject(Object o) {
		return MatlabEngineUtil.formatForMatlabEngine(o);
	}
	
	protected Object[] processInputObject(Object[] objects) {
		return Arrays.stream(objects).map(this::processInputObject).toArray();
	}
	
	// CLASS METHODS
	
	public static MatlabEngine startMatlab() throws MatlabException {
		try {
			Method startMatlabMethod = engine_class.getMethod(START_MATLAB_METHOD);
			Object returnedEngine = startMatlabMethod.invoke(null);
			return new MatlabEngine(returnedEngine);
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new EpsilonSimulinkInternalException(e);
		} catch (InvocationTargetException e) {
			throw new MatlabException(e);
		} 	
	}
	
	public static MatlabEngine startMatlab(String[] options) throws MatlabException {
		try {
			Method startMatlabMethod = engine_class.getMethod(START_MATLAB_METHOD, String[].class);
			Object returnedEngine = startMatlabMethod.invoke(null, (Object) options);
			return new MatlabEngine(returnedEngine);
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new EpsilonSimulinkInternalException(e);
		} catch (InvocationTargetException e) {
			throw new MatlabException(e);
		} 
	}
	
	public static String[] findMatlab() throws MatlabException {
		try {
			Method findMatlabMethod = engine_class.getMethod(FIND_MATLAB_METHOD);
			return (String[]) findMatlabMethod.invoke(null);
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new EpsilonSimulinkInternalException(e);
		} catch (InvocationTargetException e) {
			throw new MatlabException(e);
		} 
	}
	
	public static MatlabEngine connectMatlab() throws MatlabException {
		try {
			Method connectMatlabMethod = engine_class.getMethod(CONNECT_MATLAB_METHOD);
			Object returnedEngine = connectMatlabMethod.invoke(null);
			return new MatlabEngine(returnedEngine);		
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new EpsilonSimulinkInternalException(e);
		} catch (InvocationTargetException e) {
			throw new MatlabException(e);
		} 
	}
	
	public static MatlabEngine connectMatlab(String name) throws MatlabException {
		try {
			Method connectMatlabMethod = engine_class.getMethod(CONNECT_MATLAB_METHOD, String.class);
			Object returnedEngine = connectMatlabMethod.invoke(null, name);
			return new MatlabEngine(returnedEngine);		
		} catch (NoSuchMethodException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new EpsilonSimulinkInternalException(e);
		} catch (InvocationTargetException e) {
			throw new MatlabException(e);
		} 
	}
		
	private void start() {
		if (sb !=null) {
			if (stopWatch == null) {
				stopWatch = new Stopwatch();
			} else {
				stopWatch.resume();
			}
		}
	}
	
	private void stop() {
		if (sb !=null) {
			stopWatch.pause();
		}
	}
	
	public void resetTimer() {
		stopWatch = null;
	}
	
	// OBJECT METHODS
	
	public void eval(String cmd) throws MatlabException {
		if (evalMethod == null) {
			try {
				evalMethod = engine.getClass().getMethod(EVAL_METHOD, String.class);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new EpsilonSimulinkInternalException(e);
			}
		}
		if (isTryCatchEnabled()) {				
			cmd = "try\n" + cmd + "\ncatch ME \ne = MException(ME.identifier,ME.message); throw(e); \nend\n";
		}
		if (sb !=null) {
			sb.append("%eval").append(NL).append(cmd).append(NL);
		}
		if (isReduceExchanges()) {
			evalCommandQueue.append(cmd);
		} else {
			try {
				start();
				evalMethod.invoke(engine, cmd);
			} catch (InvocationTargetException e) {
				throw new MatlabException(e);
			} catch (ReflectiveOperationException | IllegalArgumentException e) {
				throw new EpsilonSimulinkInternalException(e);
			} finally {
				stop();
			}
		}
	}
	
	public Future<Void> evalAsync(String cmd) throws MatlabException {
		if (evalAsyncMethod == null) {
			try {
				evalAsyncMethod = engine.getClass().getMethod(EVAL_ASYNC_METHOD, String.class);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new EpsilonSimulinkInternalException(e);
			}
		}
		if (isReduceExchanges()) flush();
		try {
			if (sb !=null) {
				sb.append("%evalAsync").append(NL).append(cmd).append(NL);
			}
			start();
			return (Future<Void>) evalAsyncMethod.invoke(engine, cmd);
		} catch (InvocationTargetException e) {
			throw new MatlabException(e);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			throw new EpsilonSimulinkInternalException(e);
		} finally {
			stop();
		}
	}
	
	public Object getVariable(String variable) throws MatlabException {
		if (getVariableMethod == null) {
			try {
				getVariableMethod = engine.getClass().getMethod(GET_VARIABLE_METHOD, String.class);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new EpsilonSimulinkInternalException(e);
			}
		}
		if (isReduceExchanges()) flush();
		try {
			if (sb !=null) {
				sb.append("%getVariable").append(NL).append(variable).append(NL);
			}
			start();
			Object invoke = getVariableMethod.invoke(engine, variable);
			stop();
			return MatlabEngineUtil.parseMatlabEngineVariable(invoke);
		} catch (InvocationTargetException e) {
			String className= (String)evalWithResult("class(?);", variable);
			if (className.equals("Simulink.SimulationOutput")) {
				HashMap<String, Object> output = new HashMap<>();
				Object evalWithResult = evalWithResult("?.ErrorMessage;",variable);
				output.put("ErrorMessage", evalWithResult);
				evalWithResult = evalWithResult("?.simout;",variable);
				output.put("simout", evalWithResult);
				evalWithResult = evalWithResult("?.tout;",variable);
				output.put("tout", evalWithResult);
				eval("meta = ?.SimulationMetadata;",variable);
				HashMap<String, Object> meta = new HashMap<>();
				eval("meta.ModelInfo;");
				evalWithResult = getVariable("ans");
				meta.put("ModelInfo", evalWithResult);
				eval("meta.TimingInfo;");
				evalWithResult = getVariable("ans");
				meta.put("TimingInfo", evalWithResult);
				eval("meta.ExecutionInfo;");
				evalWithResult = getVariable("ans");
				meta.put("ExecutionInfo", evalWithResult);
				eval("meta.UserString;");
				evalWithResult = getVariable("ans");
				meta.put("UserString", evalWithResult);
				eval("meta.UserData;");
				evalWithResult = getVariable("ans");
				meta.put("UserData", evalWithResult);
				return output;
			}
			throw new MatlabException(e);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			throw new EpsilonSimulinkInternalException(e);
		} finally {
			stop();
		}
	}

	public Object fevalWithResult(int numberOfOutputs, String function, Object...handles) throws MatlabException {
		if (fevalWithVariableOutputsMethod == null) {
			try {
				fevalWithVariableOutputsMethod = engine.getClass().getMethod(FEVAL_METHOD, int.class, String.class, Object[].class);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new EpsilonSimulinkInternalException(e);
			}
		}
		if (isReduceExchanges()) flush();
		try {
			Object[] processInputObject = processInputObject(handles);
			start();
			Object res = fevalWithVariableOutputsMethod.invoke(engine, numberOfOutputs, function, processInputObject);
			stop();
			if (res != null) {
				return MatlabEngineUtil.parseMatlabEngineVariable(res);
			}
			return null;
		} catch (InvocationTargetException e) {
			throw new MatlabException(e);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			throw new EpsilonSimulinkInternalException(e);
		} finally {
			stop();
		}
	}

	public void feval(int numberOfOutputs, String function, Object...handles) throws MatlabException {
		if (fevalWithVariableOutputsMethod == null) {
			try {
				fevalWithVariableOutputsMethod = engine.getClass().getMethod(FEVAL_METHOD, int.class, String.class, Object[].class);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new EpsilonSimulinkInternalException(e);
			}
		}
		if (isReduceExchanges()) flush();
		try {
			Object[] processInputObject = processInputObject(handles);
			start();
			fevalWithVariableOutputsMethod.invoke(engine, numberOfOutputs, function, processInputObject);
		} catch (InvocationTargetException e) {
			throw new MatlabException(e);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			throw new EpsilonSimulinkInternalException(e);
		} finally {
			stop();
		}
	}
	
	public Object fevalWithResult(String function, Object... handles) throws MatlabException {
		if (fevalMethod == null) {
			try {
				fevalMethod = engine.getClass().getMethod(FEVAL_METHOD, String.class, Object[].class);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new EpsilonSimulinkInternalException(e);
			}
		}
		if (isReduceExchanges()) flush();
		try {
			Object[] processInputObject = processInputObject(handles);
			start();
			Object res = fevalMethod.invoke(engine, function, processInputObject);
			stop();
			return MatlabEngineUtil.parseMatlabEngineVariable(res);
		} catch (InvocationTargetException e) {
			throw new MatlabException(e);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			throw new EpsilonSimulinkInternalException(e);
		} finally {
			stop();
		}
	}
	
	public void feval(String function, Object... handles) throws MatlabException {
		if (fevalMethod == null) {
			try {
				fevalMethod = engine.getClass().getMethod(FEVAL_METHOD, String.class, Object[].class);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new EpsilonSimulinkInternalException(e);
			}
		}
		if (isReduceExchanges()) flush();
		try {
			Object[] processInputObject = processInputObject(handles);
			start();
			fevalMethod.invoke(engine, function, processInputObject);
		} catch (InvocationTargetException e) {
			throw new MatlabException(e);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			throw new EpsilonSimulinkInternalException(e);
		} finally {
			stop();
		}
	}
	
	public void putVariable(String variableName, Object value) throws MatlabException {
		if (putVariableMethod == null) {
			try {
				putVariableMethod = engine.getClass().getMethod(PUT_VARIABLE_METHOD, String.class, Object.class);
			}catch (NoSuchMethodException | SecurityException e) {
				throw new EpsilonSimulinkInternalException(e);
			}
		}
		if (isReduceExchanges()) flush();
		try {
			Object processInputObject = processInputObject(value);
			start();
			putVariableMethod.invoke(engine, variableName, processInputObject);
		} catch (InvocationTargetException e) {
			throw new MatlabException(e);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			throw new EpsilonSimulinkInternalException(e);
		} finally {
			stop();
		}
	}
	
	public void fevalAsync(String function, Object... handles) throws MatlabException {
		if (fevalAsyncMethod == null) {
			try {
				fevalAsyncMethod = engine.getClass().getMethod(FEVAL_ASYNC_METHOD, String.class, Object[].class);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new EpsilonSimulinkInternalException(e);
			}
		}
		if (isReduceExchanges()) flush();
		try {
			start();
			fevalAsyncMethod.invoke(engine, function, handles);
		} catch (InvocationTargetException e) {
			throw new MatlabException(e);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			throw new EpsilonSimulinkInternalException(e);
		} finally {
			stop();
		}
	}
	
	public void close() throws MatlabException {
		if (closeMethod == null) {
			try {
				closeMethod = engine.getClass().getMethod(CLOSE_METHOD);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new EpsilonSimulinkInternalException(e);
			}
		}
		if (isReduceExchanges()) flush();
		try {
			start();
			closeMethod.invoke(engine);
		}  catch (InvocationTargetException e) {
			throw new MatlabException(e);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			throw new EpsilonSimulinkInternalException(e);
		} finally {
			stop();
		}
	}
	
	public void quit() throws MatlabException {
		if (quitMethod == null) {
			try {
				quitMethod = engine.getClass().getMethod(QUIT_METHOD);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new EpsilonSimulinkInternalException(e);
			}
		}
		if (isReduceExchanges()) flush();
		try {
			start();
			quitMethod.invoke(engine);
		}  catch (InvocationTargetException e) {
			throw new MatlabException(e);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			throw new EpsilonSimulinkInternalException(e);
		} finally {
			stop();
		}
	}
	
	public void disconnect() throws MatlabException {
		if (disconnectMethod == null) {
			try {
				disconnectMethod = engine.getClass().getMethod(DISCONNECT_METHOD);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new EpsilonSimulinkInternalException(e);
			}
		}
		if (isReduceExchanges()) flush();
		try {
			start();
			disconnectMethod.invoke(engine);
		} catch (InvocationTargetException e) {
			throw new MatlabException(e);
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			throw new EpsilonSimulinkInternalException(e);
		} finally {
			stop();
		}
	}

	/**
	 * Executes all commands to be executed in the eval method that have been stored in a queue.
	 * @throws MatlabException
	 */
	public void flush() throws MatlabException {
		if (isReduceExchanges()) {
			
			String cmd = evalCommandQueue.toString();
			try {
				if (!cmd.isEmpty()) {				
					start();
					evalMethod.invoke(engine, cmd);
				}
			} catch (InvocationTargetException e) {
				throw new MatlabException(e);
			} catch (ReflectiveOperationException | IllegalArgumentException e) {
				throw new EpsilonSimulinkInternalException(e);
			} finally {
				stop();
				evalCommandQueue = new StringBuilder();
			}
		}
	}
	
	public boolean isEvalCommandQueueEmpty(){
		return evalCommandQueue.toString().isEmpty();
	}

	/**
	 * @param reduceExchanges
	 */
	public void setReduceExchanges(boolean reduceExchanges) {
		this.reduceExchanges = reduceExchanges;
	}

	public boolean isReduceExchanges() {
		return reduceExchanges;
	}
	
}