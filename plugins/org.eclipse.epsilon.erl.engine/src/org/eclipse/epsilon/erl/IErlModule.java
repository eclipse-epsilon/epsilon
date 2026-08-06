/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.erl;

import java.util.List;
import org.eclipse.epsilon.eol.IEolModule;
import org.eclipse.epsilon.erl.dom.Post;
import org.eclipse.epsilon.erl.dom.Pre;
import org.eclipse.epsilon.erl.execute.context.IErlContext;

public interface IErlModule extends IEolModule {
	
	List<Pre> getPre();
	
	List<Post> getPost();
	
	List<Pre> getDeclaredPre();
	
	List<Post> getDeclaredPost();
	
	/**
	 * @since 1.6
	 */
	@Override
	default IErlContext getContext() {
		return (IErlContext) ((IEolModule)this).getContext();
	}
}
