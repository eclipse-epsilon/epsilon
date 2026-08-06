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

public class ExampleProjectWizard14 extends WebGitProjectNewWizard {
	
	public ExampleProjectWizard14() {
		super("Experiment with the different features of EGL using a Flowchart-to-HTML transformation.", "In this example, we explore the main features of EGL by generating HTML text from an EMF model of a flowchart. We demonstrate the EGX coordination language, code formatters, preserving hand-written text with protected regions and generating a fine-grained trace model.", "org.eclipse.epsilon.examples.egl.flowchart",
				"https://git.eclipse.org", "/c/epsilon/org.eclipse.epsilon.git/plain/examples/org.eclipse.epsilon.examples.egl.flowchart/");
		
	}

}