/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.examples.tools;

import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.tools.AbstractTool;

public class SampleTool extends AbstractTool {
	
	protected String name;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String sayHello() {
		return "Hello " + name;
	}
	
	// Creates a new variable called variableName
	// and adds it to the frame stack
	public void sayHelloAsVariable(String variableName) {
		context.getFrameStack().put(Variable.
			createReadOnlyVariable(variableName, sayHello()));
	}
	
}
