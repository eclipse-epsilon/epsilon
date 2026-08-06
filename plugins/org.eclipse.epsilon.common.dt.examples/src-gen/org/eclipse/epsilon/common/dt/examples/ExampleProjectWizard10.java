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

public class ExampleProjectWizard10 extends WebGitProjectNewWizard {
	
	public ExampleProjectWizard10() {
		super("Transform a Tree model to a Graph model with ETL", "In this example, we use ETL to transform a model that conforms to a Tree metamodel to a model that conforms to a Graph metamodel.", "org.eclipse.epsilon.examples.tree2graph",
				"https://git.eclipse.org", "/c/epsilon/org.eclipse.epsilon.git/plain/examples/org.eclipse.epsilon.examples.tree2graph/");
		
	}

}