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

public class ExampleProjectWizard21 extends WebGitProjectNewWizard {
	
	public ExampleProjectWizard21() {
		super("Migrate Petri net models with Epsilon Flock", "In this example we demonstrate how to migrate a model in response to metamodel changes with Epsilon Flock.", "org.eclipse.epsilon.examples.flock.petrinets",
				"https://git.eclipse.org", "/c/epsilon/org.eclipse.epsilon.git/plain/examples/org.eclipse.epsilon.examples.flock.petrinets/");
		
	}

}