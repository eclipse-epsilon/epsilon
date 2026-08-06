/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
******************************************************************************/

package org.eclipse.epsilon.workflow.extension.example.tasks;

import org.eclipse.epsilon.workflow.tasks.EolTask; 
 
public class EolExtTask extends EolTask {
	
	@Override
	protected void initialize() {
		System.err.println("Extending initialize()");
		super.initialize();
	}
	
	@Override
	protected void examine() throws Exception {
		super.examine();
		System.err.println("Extending examine()");
	}
} 
 