/*******************************************************************************
 * Copyright (c) 2008-2012 The University of York, Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio García-Domínguez - split into interface + implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.context;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import org.eclipse.epsilon.common.module.ModuleElement;

/**
 * An individual stack frame for the EOL frame stack.
 */
public class SingleFrame implements Frame, Cloneable {

	private Map<String, Variable> storage = new LinkedHashMap<>(4);
	private FrameType type;
	private String label;
	private ModuleElement entryPoint, currentStatement;
	
	public SingleFrame(FrameType type, ModuleElement entryPoint) {
		this(type, entryPoint, null);
	}
	
	public SingleFrame(FrameType type, ModuleElement entryPoint, String label) {
		this.type = type;
		this.entryPoint = entryPoint;
		this.label = label;
	}

	@Override
	public void dispose() {
		for (Variable v : storage.values()) {
			v.dispose();
		}
		this.entryPoint = null;
	}
	
	@Override
	public void clear() {
		storage.clear();
		currentStatement = null;
	}
	
	@Override
	public SingleFrame clone() {
		try {
			SingleFrame clone = (SingleFrame) super.clone();
			clone.storage = new LinkedHashMap<>(this.storage.size());
			clone.type = this.type;
			clone.entryPoint = this.entryPoint;
			clone.label = this.label;
			clone.currentStatement = this.currentStatement;
			for (Variable v : this.storage.values()) {
				clone.storage.put(v.name, v.clone());
			}
			return clone;
		}
		catch (CloneNotSupportedException cnxx) {
			throw new RuntimeException(cnxx);
		}
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}
	
	@Override
	public void put(String name, Object value) {
		put(Variable.createReadOnlyVariable(name, value));
	}
	
	@Override
	public void remove(String name) {
		if (storage.remove(name) == null) {
			//throw new IllegalStateException("No variable named '"+name+"'");
		}
	}
	
	@Override
	public void put(Variable variable) {
		storage.put(variable.getName(), variable);
	}
	
	@Override
	public void putAll(Map<String, Variable> variables) {
		storage.putAll(variables);
	}
	
	@Override
	public Variable get(String key) {
		return storage.get(key);
	}

	@Override
	public Map<String, Variable> getAll() {
		return storage;
	}

	@Override
	public boolean contains(String key) {
		return storage.containsKey(key);
	}
	
	@Override
	public ModuleElement getEntryPoint() {
		return entryPoint;
	}

	@Override
	public void setEntryPoint(ModuleElement entryPoint) {
		this.entryPoint = entryPoint;
	}
	
	@Override
	public FrameType getType() {
		return type;
	}
	
	@Override
	public void setType(FrameType type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("------------"+ type +"-------------\r\n");
		
		for (Map.Entry<String, Variable> entry : storage.entrySet()) {
			String key = entry.getKey();
			Variable value = entry.getValue();
			Object variableValue = value.getValue();
			
			// Deal with infinite recursion incase one of the variables is this frame
			if (variableValue instanceof FrameStack) {
				FrameStack nested = (FrameStack) variableValue;
				if (nested.getFrames().contains(this))
					continue;
			}
			else if (variableValue instanceof Frame) {
				Frame nested = (Frame) variableValue;
				Collection<Variable> nestedVariables = nested.getAll().values();
				if (nestedVariables.stream().map(Variable::getValue).anyMatch(this::equals))
					continue;
			}
			
			sb.append(key + "     " + Objects.toString(value) + "\r\n");
		}
		
		return sb.toString();
	}

	@Override
	public void setCurrentStatement(ModuleElement ast) {
		this.currentStatement = ast;
	}

	@Override
	public ModuleElement getCurrentStatement() {
		return currentStatement;
	}
}
