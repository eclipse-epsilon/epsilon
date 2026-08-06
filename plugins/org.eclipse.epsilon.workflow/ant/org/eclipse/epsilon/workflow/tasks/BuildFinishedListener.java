/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;

public abstract class BuildFinishedListener implements BuildListener {
	
	@Override
	public abstract void buildFinished(BuildEvent event);

	@Override
	public void buildStarted(BuildEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void messageLogged(BuildEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void targetFinished(BuildEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void targetStarted(BuildEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void taskFinished(BuildEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void taskStarted(BuildEvent event) {
		// TODO Auto-generated method stub
		
	}

}
