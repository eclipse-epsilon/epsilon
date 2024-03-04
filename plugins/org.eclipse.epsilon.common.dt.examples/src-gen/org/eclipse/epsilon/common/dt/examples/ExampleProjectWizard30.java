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

public class ExampleProjectWizard30 extends WebGitProjectNewWizard {
	
	public ExampleProjectWizard30() {
		super("Test a model validation script with EUnit", "In this example we show how a model validation script written in EVL can be tested with EUnit, using the exportAsModel attribute of the EVL workflow task.", "org.eclipse.epsilon.eunit.examples.evl",
				"https://git.eclipse.org", "/c/epsilon/org.eclipse.epsilon.git/plain/examples/org.eclipse.epsilon.eunit.examples.evl/");
		
	}

}