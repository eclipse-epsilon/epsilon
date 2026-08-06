/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ewl.execute.context;

import org.eclipse.epsilon.eol.execute.context.EolContext;
import org.eclipse.epsilon.ewl.IEwlModule;

public class EwlContext extends EolContext implements IEwlContext {
	
	@Override
	public IEwlModule getModule() {
		return (IEwlModule) super.getModule();
	}
	
}
