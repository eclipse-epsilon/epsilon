/*******************************************************************************
 * Copyright (c) 2011-2017 The University of York, Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose, Antonio García-Domínguez - initial API and implementation
 *     Sina Madani - concurrency support
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.context;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.util.CollectionUtil;
import org.eclipse.epsilon.eol.dom.ForStatement;
import org.eclipse.epsilon.eol.dom.WhileStatement;

/**
 * A region of the {@link FrameStack}, which is itself a stack of frames.
 * FrameStackRegions are used to distinguish between different parts
 * of the overall FrameStack.
 * For best performance, concurrency should be handled using different FrameStack for each thread.
 * Nevertheless, since FrameStacks are inherently not immutable,
 * delegation of lookups may lead to {@link java.util.ConcurrentModificationException}.
 * We therefore support concurrent access to the frames in this class through the
 * {@linkplain FrameStackRegion#setThreadSafe} method. Please note that the default
 * behaviour of this class is non-concurrent.
 * 
 * @author Louis Rose, Antonio García-Domínguez, Sina Madani
 * @see org.eclipse.epsilon.eol.execute.context.FrameStack
 */
class FrameStackRegion implements Cloneable {

	/**
	 * We use the newer Deque class rather than a java.util.Stack,
	 * as the latter uses the legacy Vector class and is slower due to synchronization.
	 */
	private Deque<SingleFrame> frames;
	
	public FrameStackRegion() {
		this(false);
	}
	
	/**
	 * 
	 * @param concurrent
	 * @since 1.6
	 */
	public FrameStackRegion(boolean concurrent) {
		frames = concurrent ? new ConcurrentLinkedDeque<>() : new ArrayDeque<>();
	}
	
	/**
	 * Clears every frame.
	 */
	public void clear() {
		for (Frame frame : frames) {
			frame.clear();
		}
	}
	
	/**
	 * Disposes every frame.
	 */
	public void dispose() {
		while (!isEmpty()) {
			disposeTopFrame();
		}
	}
	
	/**
	 * Enters a new frame.
	 * 
	 * @param type
	 *            The type of the frame: variables in lower stack frames are
	 *            visible from an {@link FrameType#UNPROTECTED} frame, and
	 *            invisible from a {@link FrameType#PROTECTED} frame.
	 * @param entryPoint
	 *            The AST from which the entry is performed
	 * @param variables
	 * 			  Zero or more variables that will be added to the new frame
	 * @return
	 * 			  The new Frame
	 */
	public Frame enter(FrameType type, ModuleElement entryPoint, Variable... variables) {
		frames.push(new SingleFrame(type, entryPoint));
		put(variables);
		return top();
	}
	
	/**
	 * Same as {@link #enter(FrameType, ModuleElement, Variable...)}
	 * @param type
	 * @param entryPoint
	 * @param variables The Variables in Map format.
	 * @return
	 * @see #enter(FrameType, ModuleElement, Variable...)
	 * @since 1.6
	 */
	public Frame enter(FrameType type, ModuleElement entryPoint, Map<String, ?> variables) {
		return enter(type, entryPoint, variableMapToArray(variables));
	}
	
	/**
	 * Converts the map into an array of Variables.
	 * @param variablesMap
	 * @return
	 * @since 1.6
	 */
	protected static Variable[] variableMapToArray(Map<String, ?> variablesMap) {
		return variablesMap == null ? new Variable[0] :
			variablesMap.entrySet().stream()
			.map(Variable::createReadOnlyVariable)
			.toArray(Variable[]::new);
	}
	
	/**
	 * WARNING: Do not call if concurrent == true!
	 * 
	 * @return The number of frames in this region.
	 */
	public int frameCount() {
		return frames.size();
	}
	
	/**
	 * Searches for the specified variable in every frame from 
	 * top to bottom until a protected frame is reached (inclusive).
	 * Returns the first matching variable.
	 */
	public Variable get(String name) {
		for (Frame frame : frames) {
			Variable v = frame.get(name);
			if (v != null)
				return v;
			if (frame.isProtected())
				break;
		}
		return null;
	}

	/**
	 * Retrieves all of the variables in this region by searching from 
	 * top to bottom until a protected frame is reached (inclusive).
	 * A variable in a higher frame shadows a variable with the same 
	 * name in a lower frame. 
	 */
	public Map<String, Variable> getAll() {
		final Map<String, Variable> all = new HashMap<>();
		for (Frame frame : frames) {
			for (Map.Entry<String, Variable> entry : frame.getAll().entrySet()) {
				String key = entry.getKey();
				if (!all.containsKey(key)) {
					all.put(key, entry.getValue());
				}
			}
			if (frame.isProtected()) {
				break;
			}
		}
		return all;
	}

	/**
	 * Returns true if and only if there are no frames in this region.
	 */
	public boolean isEmpty() {
		return frames.isEmpty();
	}

	/**
	 * Returns true if and only if any of the frames in this region
	 * have an entryPoint that signify loop constructs.
	 */
	public boolean isInLoop() {
		for (SingleFrame frame : frames) {
			if (isLoopAst(frame.getEntryPoint())) {
				return true;
			}
			else if (frame.isProtected()) {
				return false;
			}
		}
		return false;
	}

	/**
	 * Leaves all frames in the region until the specified entryPoint is reached.
	 * Note that this method also leaves the frame whose entryPoint is the specified
	 * value. 
	 */
	public void leave(ModuleElement entryPoint) {
		leave(entryPoint, true);
	}
	
	/**
	 * Leaves all frames in the region until the specified entryPoint is reached.
	 * 
	 * @param entryPoint
	 * 			the entryPoint of the frame above which all frames will be left
	 * @param disposeFrameWithSpecifiedEntryPoint
	 *          if true, then the frame with the specified entryPoint will be left
	 *          otherwise, the frame with the specified entryPoint will be the topmost frame
	 */
	public void leave(ModuleElement entryPoint, boolean disposeFrameWithSpecifiedEntryPoint) {
		if (!isEmpty()) {
			disposeFramesUntil(entryPoint);
			if (disposeFrameWithSpecifiedEntryPoint)
				disposeTopFrame();
		}
	}
	
	/**
	 * Adds the specified variable to the topmost frame.
	 */
	public void put(String name, Object value) {
		Frame top = top();
		if (top != null)
			top.put(name, value);
	}
	
	/**
	 * Adds the specified variable to the topmost frame.
	 */
	public void put(Variable variable) {
		Frame top = top();
		if (top != null)
			top.put(variable);
	}
	
	/**
	 * Adds the specified variables to the topmost frame.
	 */
	public void put(Variable... variables) {
		Frame top = top();
		if (top != null) {
			for (Variable variable : variables) {
				top.put(variable);
			}
		}
	}
	
	/**
	 * Adds the specified variables to the topmost frame.
	 */
	public void putAll(Map<String, Variable> variables) {
		Frame top = top();
		if (top != null)
			top.putAll(variables);
	}
	
	/**
	 * Removes the specified variable from every frame from top
	 * to bottom until a protected frame is reached (inclusive).
	 */
	public void remove(String name) {
		for (Frame frame : frames) {
			frame.remove(name);			
			if (frame.isProtected()) break;
		}
	}
	
	/**
	 * Returns the topmost frame in this region, or null if this
	 * region is empty.	
	 */
	public Frame top() {
		return frames.peek();
	}
	
	@Override
	public String toString() {
		final StringBuilder result = new StringBuilder();
		
		for (SingleFrame frame : frames) {
			result.append(frame.toString());
		}
		
		return result.toString();
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @since 1.6
	 */
	static void mergeFrames(FrameStackRegion from, FrameStackRegion to) {
		if (from != null && to != null && from.frames != null) {
			Deque<SingleFrame> framesToAdd = to.isThreadSafe() ? new ConcurrentLinkedDeque<>() : new ArrayDeque<>();
			
			for (SingleFrame frame : from.frames) {
				if (!frame.getAll().isEmpty()) {
					framesToAdd.add(frame);
				}
			}
			
			if (to.frames == null) {
				to.frames = framesToAdd;
			}
			else {
				to.frames = CollectionUtil.mergeCollectionsUnique(
					to.frames,
					framesToAdd,
					to.isThreadSafe() ? ConcurrentLinkedDeque::new : ArrayDeque::new
				);
			}
		}
	}

	@Override
	protected FrameStackRegion clone() {
		try {
			final FrameStackRegion clone = (FrameStackRegion) super.clone();
			clone.frames = this.isThreadSafe() ?
				new ConcurrentLinkedDeque<>() : new ArrayDeque<>(this.frames.size());
			
			for (SingleFrame frame : this.frames) {
				clone.frames.add(frame.clone());
			}
			
			return clone;
		}
		catch (CloneNotSupportedException cnsx) {
			throw new RuntimeException(cnsx);
		}
	}
	
	/**
	 * 
	 * @return
	 * @since 1.6
	 */
	boolean isThreadSafe() {
		return frames instanceof ConcurrentLinkedDeque;
	}
	
	/**
	 * 
	 * @param concurrent
	 * @since 1.6
	 */
	void setThreadSafe(boolean concurrent) {
		if (isThreadSafe() != concurrent) {
			frames = concurrent ? new ConcurrentLinkedDeque<>(frames) : new ArrayDeque<>(frames);
		}
	}
	
	protected Collection<SingleFrame> getFrames() {
		return frames;
	}
	
	protected static boolean isLoopAst(ModuleElement ast) {
		return ast instanceof ForStatement || ast instanceof WhileStatement;
	}
	
	private void disposeFramesUntil(ModuleElement entryPoint) {
		while (top().getEntryPoint() != entryPoint)
			disposeTopFrame();
	}

	private void disposeTopFrame() {
		if (!frames.isEmpty())
			frames.pop().dispose();
	}
}
