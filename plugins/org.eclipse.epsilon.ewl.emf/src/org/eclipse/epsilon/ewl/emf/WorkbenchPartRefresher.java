/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
******************************************************************************/

package org.eclipse.epsilon.ewl.emf;

import org.eclipse.ui.IWorkbenchPart;

public abstract class WorkbenchPartRefresher {
	
	
	protected IWorkbenchPart part = null;
	
	public WorkbenchPartRefresher() {
		
	}

	public IWorkbenchPart getPart() {
		return part;
	}



	public void setPart(IWorkbenchPart part) {
		this.part = part;
	}



	public abstract void refresh();
	
}
 
