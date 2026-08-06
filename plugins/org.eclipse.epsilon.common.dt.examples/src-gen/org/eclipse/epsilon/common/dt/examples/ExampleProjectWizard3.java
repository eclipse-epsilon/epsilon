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

public class ExampleProjectWizard3 extends WebGitProjectNewWizard {
	
	public ExampleProjectWizard3() {
		super("Call Java code from Epsilon", "In this example, we create a JFrame from EOL. The aim of this example is to show how to call Java code from within Epsilon languages.", "org.eclipse.epsilon.examples.calljava",
				"https://git.eclipse.org", "/c/epsilon/org.eclipse.epsilon.git/plain/examples/org.eclipse.epsilon.examples.calljava/");
		
	}

}