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

public class ExampleProjectWizard11 extends WebGitProjectNewWizard {
	
	public ExampleProjectWizard11() {
		super("Transform an RSS feed to an Atom feed using ETL", "In this example, we use ETL and the plain XML driver to transform an RSS feed to an Atom feed.", "org.eclipse.epsilon.examples.rss2atom",
				"https://git.eclipse.org", "/c/epsilon/org.eclipse.epsilon.git/plain/examples/org.eclipse.epsilon.examples.rss2atom/");
		
	}

}