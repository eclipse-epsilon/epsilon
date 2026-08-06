/*******************************************************************************
 * Copyright (c) 2008-2018 The University of York, Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/

 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio García-Domínguez - allow for multiple global stack frames
 *     Sina Madani - Concurrency support
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.epsilon.common.concurrent.ConcurrentBaseDelegate;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;

/**
 * <p>A FrameStack is a stack of frames that stores the variables created during the
 * execution of an EOL program.</p>
 * 
 * <p>A FrameStack is divided into two distinct regions, one for global variables and
 * one for local variables. The global region always contains at least one frame, while 
 * the local region can be empty.</p> 
 * 
 *
 * @author Dimitrios Kolovos, Antonio García-Domínguez, Sina Madani
 * @version 1.4
 * @see org.eclipse.epsilon.eol.execute.context.Frame
 */
public class FrameStack implements Cloneable, ConcurrentBaseDelegate<FrameStack> {
	
	protected FrameStackRegion globals;
	protected FrameStackRegion locals;
	protected Map<String, Variable> builtInVariables;
	protected FrameStack base;
	protected boolean isConcurrent;
	
	public FrameStack() {
		this(null);
	}
	
	/**
	 * 
	 * @param parent
	 * @since 1.6
	 */
	public FrameStack(FrameStack parent) {
		this(parent, false);
	}
	
	/**
	 * 
	 * @param parent
	 * @param concurrent
	 * @since 1.6
	 */
	public FrameStack(FrameStack parent, boolean concurrent) {
		this.base = parent;
		this.isConcurrent = concurrent;
		globals = new FrameStackRegion(concurrent);
		locals = new FrameStackRegion(concurrent);
		initializeState();
	}
	
	/**
	 * Called from constructor or clone() method.
	 */
	protected void initializeState() {
		enterGlobal(FrameType.UNPROTECTED, null);
		builtInVariables = new HashMap<>(2);
		builtInVariables.put("null", Variable.createReadOnlyVariable("null", null));
	}
	
	public void dispose() {
		globals.dispose();
		locals.dispose();
		initializeState();
	}

	/**
	 * Enters a new global frame.
	 * 
	 * @param type
	 *            The type of the frame: variables in lower stack frames are
	 *            visible from an {@link FrameType#UNPROTECTED} frame, and
	 *            invisible from a {@link FrameType#PROTECTED} frame.
	 * @param entryPoint
	 *            The AST from which the entry is performed
	 * @param variables
	 *            Zero or more variables that will be added to the new frame
	 * @return
	 * 			  The new Frame
	 */
	public Frame enterGlobal(FrameType type, ModuleElement entryPoint, Variable... variables) {
		return globals.enter(type, entryPoint, variables);
	}
	
	/**
	 * Enters a new local frame.
	 *
	 * @param type
	 *            The type of the frame: variables in lower stack frames are
	 *            visible from an {@link FrameType#UNPROTECTED} frame, and
	 *            invisible from a {@link FrameType#PROTECTED} frame.
	 * @param entryPoint
	 *            The AST from which the entry is performed
	 * @param variables
	 *            Zero or more variables that will be added to the new frame
	 * @return The new Frame
	 */
	public Frame enterLocal(FrameType type, ModuleElement entryPoint, Variable... variables) {
		return locals.enter(type, entryPoint, variables);
	}
	
	/**
	 * Same as {@link #enterGlobal(FrameType, ModuleElement, Variable...)}
	 * @param type
	 * @param entryPoint
	 * @param variables
	 * @return
	 * @see #enterGlobal(FrameType, ModuleElement, Variable...)
	 * @since 1.6
	 */
	public Frame enterGlobal(FrameType type, ModuleElement entryPoint, Map<String, ?> variables) {
		return globals.enter(type, entryPoint, variables);
	}
	
	/**
	 * Same as {@link #enterLocal(FrameType, ModuleElement, Variable...)}
	 * @param type
	 * @param entryPoint
	 * @param variables
	 * @return
	 * @see #enterLocal(FrameType, ModuleElement, Variable...)
	 * @since 1.6
	 */
	public Frame enterLocal(FrameType type, ModuleElement entryPoint, Map<String, ?> variables) {
		return locals.enter(type, entryPoint, variables);
	}
	
	/**
	 * Leaves the current local frame and returns to the previous frame in the
	 * stack. This method cannot leave a global stack frame: use
	 * {@link #leaveGlobal(AST, boolean)} for that.
	 */
	public void leaveLocal(ModuleElement entryPoint, boolean dispose) {
		locals.leave(entryPoint, dispose);
	}
	
	/**
	 * Convenience method for {@link #leaveLocal(AST, boolean))} which disposes of the stack
	 * frame that was left.
	 */
	public void leaveLocal(ModuleElement entryPoint) {
		leaveLocal(entryPoint, true);
	}
	
	/**
	 * Leaves the current global stack frame and returns to the previous frame
	 * in the stack. This method cannot leave a local stack frame: use
	 * {@link #leaveLocal(AST, boolean)} for that. This method will not leave the
	 * last remaining global stack frame.
	 */
	public void leaveGlobal(ModuleElement entryPoint, boolean dispose) {
		if (countGlobalFrames() > 1)
			globals.leave(entryPoint, dispose);
	}
	
	/**
	 * Convenience method for {@link #leaveGlobal(AST, boolean)} which disposes of the
	 * global stack frame that was left.
	 */
	public void leaveGlobal(ModuleElement entryPoint) {
		leaveGlobal(entryPoint, true);
	}

	/**
	 * Converts the map into Variables and puts them in the
	 * topmost frame of the scope.
	 * @param variables The effective collection of variables.
	 * @param readOnly Whether the Variables should be immutable.
	 * @since 1.6
	 */
	public void put(final Map<String, ?> variables, final boolean readOnly) {
		FrameStackRegion activeRegion = activeGroup();
		variables.entrySet()
			.stream()
			.map(entry -> readOnly ? Variable.createReadOnlyVariable(entry) : new Variable(entry))
			.forEach(activeRegion::put);
	}
	
	/**
	 * Puts the Entry as read-only variable into the topmost frame of the scope.
	 * 
	 * @param variables
	 * @since 1.6
	 */
	public void put(Map.Entry<String, ?> variable) {
		if (variable != null) {
			put(Variable.createReadOnlyVariable(variable));
		}
	}
	
	public void put(Collection<Variable> variables) {
		activeGroup().put(variables.toArray(new Variable[variables.size()]));
	}
	
	/**
	 * Puts one or more new variables in the topmost frame of the scope.
	 * Note that the topmost frame can be either a local or a global frame,
	 * depending on the current state of the FrameStack.
	 */
	public void put(Variable... variables) {
		activeGroup().put(variables);
	}

	/**
	 * Puts a read-only variable into the topmost frame of the scope
	 * @param name The variable name.
	 * @param value The variable value.
	 * @since 1.6
	 */
	public void put(String name, Object value) {
		activeGroup().put(name, value);
	}
	
	/**
	 * Puts a new variable in the topmost frame of the scope.
	 * Note that the topmost frame can be either a local or a global frame,
	 * depending on the current state of the FrameStack.
	 */
	public void put(Variable variable) {
		activeGroup().put(variable);
	}
	
	/**
	 * Puts one or more new variables in the topmost global stack frame.
	 */
	public void putGlobal(Variable... variables) {
		globals.put(variables);
	}
	
	/**
	 * Puts a new variable in the topmost global stack frame.
	 */
	public void putGlobal(Variable variable) {
		globals.put(variable);
	}
	
	/**
	 * Removes a variable by name from the topmost frame of the scope.
	 * Note that the topmost frame can be either a local or a global frame,
	 * depending on the current state of the FrameStack.
	 */
	public void remove(String variable) {
		activeGroup().top().remove(variable);
	}
	
	/**
	 * Removes the variables from the topmost frame of the scope.
	 * @param variablesThe variables to remove.
	 * @since 1.6
	 */
	public void remove(Collection<String> variables) {
		for (String variable : variables) {
			remove(variable);
		}
	}
	
	/**
	 * Returns the variable with the specified
	 * name and if it does not exist returns <code>null</code>. Note
	 * that variables in a higher frame shadow variables with
	 * the same name in lower frames. Similarly, local variables
	 * shadow global variables with the same name.
	 *
	 * @param name The name of the variable
	 * @return The variable with the specified name or <code>null</code>
	 */
	public Variable get(String name) {
		Variable variable = builtInVariables.get(name);
		
		if (variable == null)
			variable = getLocal(name);
		
		if (variable == null)
			variable = getGlobal(name);
		
		return variable;
	}
	
	/**
	 * <p>Returns the local variable with the specified name
	 * If the local variable does not exist, this method 
	 * returns <code>null</code>.</p>
	 * 
	 * <p><strong>Note:</strong> this method does not respect the 
	 * usual shadowing semantics of the FrameStack, and consequently
	 * most clients should call {@link #get(String)} (i.e., only call 
	 * this method if you really know what you are doing!)</p> 
	 *
	 * @param name The name of the local variable
	 * @return The local variable with the specified name or <code>null</code>
	 */
	public Variable getLocal(String name) {
		Variable variable = locals.get(name);
		if (variable == null && base != null) {
			variable = base.getLocal(name);
		}
		return variable;
	}
	
	/**
	 * <p>Returns the global variable with the specified name
	 * If the global variable does not exist, this method 
	 * returns <code>null</code>.</p>
	 * 
	 * <p><strong>Note:</strong> this method does not respect the 
	 * usual shadowing semantics of the FrameStack, and consequently
	 * most clients should call {@link #get(String)} (i.e., only call 
	 * this method if you really know what you are doing!)</p> 
	 *
	 * @param name The name of the global variable
	 * @return The global variable with the specified name or <code>null</code>
	 */
	public Variable getGlobal(String name) {
		Variable variable = globals.get(name);
		if (variable == null && base != null) {
			variable = base.getGlobal(name);
		}
		return variable;
	}
	
	public boolean isInLoop() {
		return locals.isInLoop() || globals.isInLoop();
	}
	
	/**
	 * Returns true if a variable with the
	 * specified name exists in the scope
	 * @param name
	 * @return
	 */
	public boolean contains(String name) {
		return get(name) != null;
	}
	
	/**
	 * Returns true if a local variable with the 
	 * specified name exists.
	 * 
	 * <p><strong>Note:</strong> this method does not respect the 
	 * usual shadowing semantics of the FrameStack, and consequently
	 * most clients should call {@link #contains(String)} (i.e., only call 
	 * this method if you really know what you are doing!)</p> 
	 * 
	 * @param name
	 * @return 
	 */
	public boolean containsLocal(String name) {
		return getLocal(name) != null;
	}
	
	/**
	 * Returns true if a global variable with the 
	 * specified name exists.
	 * 
	 * <p><strong>Note:</strong> this method does not respect the 
	 * usual shadowing semantics of the FrameStack, and consequently
	 * most clients should call {@link #contains(String)} (i.e., only call 
	 * this method if you really know what you are doing!)</p> 
	 * 
	 * @param name
	 * @return 
	 */
	public boolean containsGlobal(String name) {
		return getGlobal(name) != null;
	}
	
	/**
	 * Returns a list with all local (from top to bottom) and global (from top
	 * to bottom) stack frames, in that order.
	 */
	public List<SingleFrame> getFrames() {
		return getFrames(false);
	}
	
	/**
	 * 
	 * @param includeBase
	 * @return
	 * @since 1.6
	 */
	public List<SingleFrame> getFrames(boolean includeBase) {
		final List<SingleFrame> frames = new ArrayList<>();
		frames.addAll(locals.getFrames());
		frames.addAll(globals.getFrames());
		if (includeBase && base != null) {
			frames.addAll(base.getFrames(true));
		}
		return frames;
	}
	
	/**
	 * WARNING: Do not call if {@link #isThreadSafe()} is <code>true</code>
	 * whilst in use. Failure to comply may result in infinite waiting!
	 * 
	 * @return
	 */
	public int getDepth() {
		return locals.frameCount() + globals.frameCount();
	}
	
	@Override
	public FrameStack clone() {
		FrameStack clone = new FrameStack();
		clone.base = this.base;
		clone.isConcurrent = this.isConcurrent;
		clone.builtInVariables = new HashMap<>(this.builtInVariables);
		clone.locals = locals.clone();
		clone.globals = globals.clone();
		return clone;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
			.append("-----------SCOPE------------\r\n")
			.append(locals.toString())
			.append("----------GLOBALS-----------\r\n")
			.append(globals.toString())
			.toString();
	}

	public Frame getTopFrame() {
		return activeGroup().top();
	}
	
	public ModuleElement getCurrentStatement() {
		return activeGroup().top().getCurrentStatement();
	}

	public void setCurrentStatement(ModuleElement ast) {
		activeGroup().top().setCurrentStatement(ast);
	}

	protected int countGlobalFrames() {
		return globals.frameCount();
	}
	
	private FrameStackRegion activeGroup() {
		return locals.isEmpty() ? globals : locals;
	}
	
	/**
	 * Copies the references of all variables in the given FrameStack into this FrameStack.
	 * 
	 * @param other The FrameStack to copy from.
	 * @since 1.6
	 */
	public void putAll(FrameStack other) {
		this.globals.putAll(other.globals.getAll());
		this.locals.putAll(other.locals.getAll());
		this.builtInVariables.putAll(other.builtInVariables);
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public FrameStack getBase() {
		return base;
	}
	
	/**
	 * 
	 * @param parent
	 * @since 1.6
	 */
	public void setBase(FrameStack parent) {
		this.base = parent;
	}
	
	/**
	 * 
	 * @param includeBase
	 * @return
	 * @since 1.6
	 */
	public int size(boolean includeBase) {
		return getFrames(includeBase).size();
	}
	
	public int size() {
		return getFrames().size();
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public boolean isThreadSafe() {
		return isConcurrent;
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public void setThreadSafe(boolean concurrent) {
		if (concurrent != this.isConcurrent) {
			this.isConcurrent = concurrent;
			locals.setThreadSafe(concurrent);
			globals.setThreadSafe(concurrent);
		}
	}
	
	/**
	 * Adds all of this FrameStack's frames into its base FrameStack, or vice-versa.
	 * @param mode Whether to merge from base to this, or from this to base.
	 * @since 1.6
	 */
	@Override
	public void merge(MergeMode mode) {
		if (base != null) {
			mergeFrameStacks(getFrom(mode), getTo(mode));
		}
	}
	
	/**
	 * Adds all the frames and variables from the first argument to the second one.
	 * @since 1.6
	 */
	protected static void mergeFrameStacks(FrameStack from, FrameStack to) {
		if (from != null && to != null) synchronized (to) {
			FrameStackRegion.mergeFrames(from.locals, to.locals);
			FrameStackRegion.mergeFrames(from.globals, to.globals);
			
			// Just in case a subclass adds to built-in variables
			if (!from.builtInVariables.isEmpty()) {
				from.builtInVariables.forEach(to.builtInVariables::putIfAbsent);
			}
		}
	}
}
