/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.execute.context;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.IEgxModule;
import org.eclipse.epsilon.egl.traceability.Template;
import org.eclipse.epsilon.erl.execute.context.IErlContext;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public interface IEgxContext extends IErlContext {
	
	/**
	 * Casts the IModule to IEgxModule
	 * @see org.eclipse.epsilon.eol.execute.context.IEolContext#getModule()
	 */
	@Override
	public default IEgxModule getModule() {
		return (IEgxModule) ((IErlContext)this).getModule();
	}

	public void setTemplateFactory(EglTemplateFactory templateFactory);
	
	public EglTemplateFactory getTemplateFactory();

	public Map<URI, EglTemplate> getTemplateCache();
	
	public Collection<Template> getInvokedTemplates();
	
}
