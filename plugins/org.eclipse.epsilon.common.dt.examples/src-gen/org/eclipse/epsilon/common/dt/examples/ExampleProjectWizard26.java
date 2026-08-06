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

public class ExampleProjectWizard26 extends WebGitProjectNewWizard {
	
	public ExampleProjectWizard26() {
		super("Provide custom/extended tasks for the workflow", "In this example we demonstrate how you can define your own ANT tasks that extend the Epsilon workflow tasks.", "org.eclipse.epsilon.workflow.extension.example",
				"https://git.eclipse.org", "/c/epsilon/org.eclipse.epsilon.git/plain/examples/org.eclipse.epsilon.workflow.extension.example/");
		
	}

}