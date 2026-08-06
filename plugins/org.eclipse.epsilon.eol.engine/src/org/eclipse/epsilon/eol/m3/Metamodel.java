/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.m3;

import java.util.ArrayList;
import java.util.List;

public class Metamodel extends Package {

	protected List<String> warnings = new ArrayList<>();
	protected List<String> errors = new ArrayList<>();
	
	public List<String> getWarnings() {
		return warnings;
	}
	
	public List<String> getErrors() {
		return errors;
	}
	
	public MetaClass getMetaClass(String name) {
		for (MetaType type : metaTypes) {
			if (type instanceof MetaClass && type.getName().equals(name)) {
				return (MetaClass) type;
			}
		}
		return null;
	}
	
}
