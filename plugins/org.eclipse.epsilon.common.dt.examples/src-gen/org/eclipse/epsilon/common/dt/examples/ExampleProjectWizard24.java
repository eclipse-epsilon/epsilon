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

public class ExampleProjectWizard24 extends WebGitProjectNewWizard {
	
	public ExampleProjectWizard24() {
		super("Compare, validate and merge OO models", "In this example, we use ECL to compare two OO models, then use EVL to check the identified matches for consistency and finally EML to merge them.", "org.eclipse.epsilon.examples.oomerging",
				"https://git.eclipse.org", "/c/epsilon/org.eclipse.epsilon.git/plain/examples/org.eclipse.epsilon.examples.oomerging/");
		
	}

}