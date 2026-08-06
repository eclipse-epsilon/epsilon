/*******************************************************************************
 * Copyright (c) 2022 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Ionut Predoaia - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.yaml;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;

public class YamlModelOperationContributor extends OperationContributor {

	protected YamlModel yamlModel;
	
	public YamlModelOperationContributor(YamlModel yamlModel) {
		this.yamlModel = yamlModel;
	}
	
	@Override
	public boolean contributesTo(Object target) {
		return (target instanceof Entry) || (target instanceof Map) || (target instanceof List);
	}

	public void appendNode(Object item) throws EolRuntimeException {
		Object target = getTarget();
		if(target instanceof Map) {
			Entry entry = (Entry) item;
			((Map) target).put(entry.getKey(), entry.getValue());
		}
		else {
			throw new EolRuntimeException("The object to which you append a node must be a MappingNode. A MappingNode is represented as a map.");
		}		
	}
	
	public void addRow() throws EolRuntimeException {
		Object target = getTarget();
		if((target instanceof List) && (YamlNodeUtility.isListNode((List)target))) {	
			((List) target).add(new LinkedHashMap());
		}
		else {
			throw new EolRuntimeException("The object to which you add a row must be a ListNode. A ListNode is represented as a list of maps.");
		}
	}
	
	public void addRows(int size) throws EolRuntimeException {
		Object target = getTarget();
		if((target instanceof List) && (YamlNodeUtility.isListNode((List)target))) {
			for (int i=0; i < size; i++) {
				((List) target).add(new LinkedHashMap());
			}
		}
		else {
			throw new EolRuntimeException("The object to which you add a row must be a ListNode. A ListNode is represented as a list of maps.");
		}
	}
	
	public void set(int index, Object object) throws EolRuntimeException {
		Object target = getTarget();
		if(target instanceof List) {
			((List) target).set(index, object);
		}
		else {
			throw new EolRuntimeException("The object to which you set a value must be a List.");
		}		
	}
}