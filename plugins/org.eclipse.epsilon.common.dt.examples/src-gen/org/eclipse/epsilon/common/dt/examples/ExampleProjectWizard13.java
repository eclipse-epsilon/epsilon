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

public class ExampleProjectWizard13 extends WebGitProjectNewWizard {
	
	public ExampleProjectWizard13() {
		super("Transform an OO model to a DB model with ETL", "In this example, we use ETL to transform a model that conforms to an Object-Oriented metamodel to a model that conforms to the Database metamodel.", "org.eclipse.epsilon.examples.oo2db",
				"https://git.eclipse.org", "/c/epsilon/org.eclipse.epsilon.git/plain/examples/org.eclipse.epsilon.examples.oo2db/");
		
	}

}