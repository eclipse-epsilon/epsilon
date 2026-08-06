/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.execute.context;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.IEgxModule;
import org.eclipse.epsilon.egl.traceability.Template;
import org.eclipse.epsilon.erl.execute.context.ErlContext;

/**
 * 
 * @since 1.6 extends ERL rather than EGL
 */
public class EgxContext extends ErlContext implements IEgxContext {
	
	private EgxModuleTemplateAdapter baseTemplate;
	private EglTemplateFactory templateFactory;
	protected Map<URI, EglTemplate> templateCache;
	protected Collection<Template> invokedTemplates;
	
	public EgxContext() {
		this((EglTemplateFactory) null);
	}
	
	public EgxContext(EglTemplateFactory templateFactory) {
		setTemplateFactory(templateFactory != null ? templateFactory : new EglTemplateFactory());
		invokedTemplates = new ArrayList<>();
		templateCache = new HashMap<>();
	}
	
	/**
	 * Copy constructor, intended for internal use only.
	 * 
	 * @param other The parent context.
	 * @since 1.6
	 */
	public EgxContext(IEgxContext other) {
		super(other);
		templateFactory = other.getTemplateFactory();
		invokedTemplates = other.getInvokedTemplates();
		templateCache = other.getTemplateCache();
		if (other instanceof EgxContext) {
			baseTemplate = ((EgxContext) other).baseTemplate;
		}
	}
	
	public EgxModuleTemplateAdapter getTrace() {
		if (baseTemplate == null) {
			baseTemplate = new EgxModuleTemplateAdapter(getModule());
		}
		return baseTemplate;
	}
	
	public void setBaseTemplate(EgxModuleTemplateAdapter baseTemplate) {
		this.baseTemplate = baseTemplate;
	}
	
	@Override
	public IEgxModule getModule() {
		return (IEgxModule) super.getModule();
	}

	@Override
	public Collection<Template> getInvokedTemplates() {
		return invokedTemplates;
	}
	
	@Override
	public void setTemplateFactory(EglTemplateFactory templateFactory) {
		if ((this.templateFactory = templateFactory) != null) {
			templateFactory.copyState(this);
		}
	}

	@Override
	public EglTemplateFactory getTemplateFactory() {
		return templateFactory;
	}

	/**
	 * @since 1.6
	 */
	@Override
	public Map<URI, EglTemplate> getTemplateCache() {
		return templateCache;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		if (templateCache != null) templateCache.clear();
		if (invokedTemplates != null) invokedTemplates.clear();
	}
}
