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

public class ExampleProjectWizard5 extends WebGitProjectNewWizard {
	
	public ExampleProjectWizard5() {
		super("Building and querying plain XML documents with EOL", "In this example, we use the plain XML driver of Epsilon to build and query an XML document that is not backed by a XSD/DTD.", "org.eclipse.epsilon.examples.plainxml",
				"https://git.eclipse.org", "/c/epsilon/org.eclipse.epsilon.git/plain/examples/org.eclipse.epsilon.examples.plainxml/");
		
	}

}