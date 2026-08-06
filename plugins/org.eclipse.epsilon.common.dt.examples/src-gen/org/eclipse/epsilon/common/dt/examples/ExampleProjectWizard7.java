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

public class ExampleProjectWizard7 extends WebGitProjectNewWizard {
	
	public ExampleProjectWizard7() {
		super("Cloning EMF model elements with EOL", "In this example, we demonstrate how the EmfTool built-in tool can be used to perform deep-copy (cloning) of EMF model elements using EOL.", "org.eclipse.epsilon.examples.clone",
				"https://git.eclipse.org", "/c/epsilon/org.eclipse.epsilon.git/plain/examples/org.eclipse.epsilon.examples.clone/");
		
	}

}