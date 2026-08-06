/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.console;

import org.eclipse.epsilon.common.dt.util.EclipseUtil;
import org.eclipse.epsilon.common.parse.Region;
import org.eclipse.ui.console.IHyperlink;

public class ConsoleHyperlink implements IHyperlink {
	
	protected String file;
	protected Region region;
	
	public ConsoleHyperlink(String file, Region region){
		this.file = file;
		this.region = region;
	}
	
	@Override
	public void linkEntered() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void linkExited() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void linkActivated() {
		
		EclipseUtil.openEditorAt(file, region);
		
		/*
		try {
			linkActivatedImpl();
		}
		catch (Exception ex){
			
		}*/
	}

}
