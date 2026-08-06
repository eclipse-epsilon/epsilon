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

public class ExampleProjectWizard28 extends WebGitProjectNewWizard {
	
	public ExampleProjectWizard28() {
		super("Test EOL scripts with EUnit", "In this example we show the basic structure of an EUnit test, some useful assertions for the basic types and how to test for errors and define our own assertions.", "org.eclipse.epsilon.eunit.examples.eol",
				"https://git.eclipse.org", "/c/epsilon/org.eclipse.epsilon.git/plain/examples/org.eclipse.epsilon.eunit.examples.eol/");
		
	}

}