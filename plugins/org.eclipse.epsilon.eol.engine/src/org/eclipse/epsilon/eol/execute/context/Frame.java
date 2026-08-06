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

import java.util.Map;
import org.eclipse.epsilon.common.module.ModuleElement;

public interface Frame {

	void dispose();
	
	void clear();
	
	String getLabel();

	void setLabel(String label);
	
	void put(String name, Object value);
	
	void remove(String name);
	
	void put(Variable variable);
	
	/**
	 * 
	 * @param variables
	 * @since 1.6
	 */
	default void put(Iterable<Variable> variables) {
		for (Variable variable : variables)
			put(variable);
	}
	
	/**
	 * 
	 * @param variables
	 * @since 1.6
	 */
	default void put(Variable... variables) {
		for (Variable variable : variables)
			put(variable);
	}

	void putAll(Map<String, Variable> variables);
	
	/**
	 * 
	 * @param key
	 * @return The Variable, or <code>null</code> if absent.
	 */
	Variable get(String key);
	
	Map<String, Variable> getAll();
	
	boolean contains(String key);

	/**
	 * 
	 * @return <code>true</code> if this frame is protected visibility.
	 * @since 1.6
	 */
	default boolean isProtected() {
		return getType() == FrameType.PROTECTED;
	}
	
	FrameType getType();
	
	void setType(FrameType type);
	
	ModuleElement getEntryPoint();
	
	void setEntryPoint(ModuleElement entryPoint);

	void setCurrentStatement(ModuleElement ast);

	ModuleElement getCurrentStatement();
}
