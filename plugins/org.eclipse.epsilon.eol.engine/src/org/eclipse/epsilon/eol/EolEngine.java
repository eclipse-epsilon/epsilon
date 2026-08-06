/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol;

import org.eclipse.epsilon.eol.execute.context.EolContext;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class EolEngine {
	
	protected IEolContext context;
	protected IEolModule module;
	
	public EolEngine() {
		reset();
	}
	
	public void reset() {
		module = new EolModule();
		context = new EolContext();
		context.setModule(module);
	}
	
	public Object execute(String code) throws Exception {
		module.parse(code);
		return module.execute();
	}
	
	public void shutdown() {
		context.getModelRepository().dispose();
		context.dispose();
	}
	
	public static void main(String[] args) throws Exception {
		EolEngine engine = new EolEngine();
		
		engine.execute("var i : Integer;");
		engine.execute("i := 5;");
		engine.execute("i.println();");
		engine.shutdown();
	}
	
	public IEolContext getContext() {
		return context;
	}
	
}
