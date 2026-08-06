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

public class ExampleProjectWizard27 extends WebGitProjectNewWizard {
	
	public ExampleProjectWizard27() {
		super("Use model transactions in a workflow", "In this example we demonstrate using the ant-contrib try/catch tasks and the Epsilon model transactions tasks to conditionally rollback changes in models modified in a workflow.", "org.eclipse.epsilon.examples.workflow.transactions",
				"https://git.eclipse.org", "/c/epsilon/org.eclipse.epsilon.git/plain/examples/org.eclipse.epsilon.examples.workflow.transactions/");
		
	}

}