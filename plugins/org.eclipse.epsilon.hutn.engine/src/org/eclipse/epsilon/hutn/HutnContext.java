/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn;

import org.eclipse.epsilon.eol.execute.context.EolContext;

public class HutnContext extends EolContext implements IHutnContext {

	HutnContext() {
		super();
	}
	
	public HutnContext(IHutnModule module) {
		setModule(module);
	}
	
	@Override
	public IHutnModule getModule() {
		return (IHutnModule) super.getModule();
	}
}
