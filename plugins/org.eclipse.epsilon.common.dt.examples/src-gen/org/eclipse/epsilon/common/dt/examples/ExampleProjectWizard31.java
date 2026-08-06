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

public class ExampleProjectWizard31 extends WebGitProjectNewWizard {
	
	public ExampleProjectWizard31() {
		super("Test a model-to-text transformation with EUnit", "In this example we show how a model-to-text transformation written in EGL can be tested with EUnit and HUTN.", "org.eclipse.epsilon.eunit.examples.egl.files",
				"https://git.eclipse.org", "/c/epsilon/org.eclipse.epsilon.git/plain/examples/org.eclipse.epsilon.eunit.examples.egl.files/");
		
	}

}