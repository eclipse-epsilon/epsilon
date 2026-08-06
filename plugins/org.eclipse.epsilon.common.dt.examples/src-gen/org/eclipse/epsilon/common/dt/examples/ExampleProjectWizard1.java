/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.dt.examples;

public class ExampleProjectWizard1 extends WebGitProjectNewWizard {
	
	public ExampleProjectWizard1() {
		super("Create an OO model with EOL", "In this example we use EOL to programmatically construct a model that conforms to an object-oriented metamodel.", "org.eclipse.epsilon.examples.buildooinstance",
				"https://git.eclipse.org", "/c/epsilon/org.eclipse.epsilon.git/plain/examples/org.eclipse.epsilon.examples.buildooinstance/");
		
	}

}