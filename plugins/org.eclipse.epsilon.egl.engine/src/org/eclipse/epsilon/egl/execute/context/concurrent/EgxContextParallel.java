/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl.execute.context.concurrent;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import org.eclipse.epsilon.common.concurrent.ConcurrencyUtils;
import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.IEgxModule;
import org.eclipse.epsilon.egl.execute.context.EgxContext;
import org.eclipse.epsilon.egl.execute.context.IEgxContext;
import org.eclipse.epsilon.egl.traceability.Template;
import org.eclipse.epsilon.erl.execute.context.concurrent.ErlContextParallel;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EgxContextParallel extends ErlContextParallel implements IEgxContextParallel {

	protected EglTemplateFactory templateFactory;
	protected Map<URI, EglTemplate> templateCache;
	protected Collection<Template> invokedTemplates;
	ThreadLocal<Map<URI, EglTemplate>> concurrentTemplateCaches;
	
	public EgxContextParallel() {
		this(null);
	}
	
	public EgxContextParallel(EglTemplateFactory templateFactory) {
		this(templateFactory, 0);
	}
	
	public EgxContextParallel(int parallelism) {
		this(null, parallelism);
	}
	
	public EgxContextParallel(EglTemplateFactory templateFactory, int parallelism) {
		super(parallelism);
		setTemplateFactory(templateFactory != null ? templateFactory : new EglTemplateFactory());
		invokedTemplates = ConcurrencyUtils.concurrentOrderedCollection();
	}

	@Override
	protected IEgxContext createShadowThreadLocalContext() {
		return new EgxContext(this);
	}
	
	@Override
	public IEgxContext getShadow() {
		return (IEgxContext) super.getShadow();
	}
	
	@Override
	protected void initThreadLocals() {
		super.initThreadLocals();
		concurrentTemplateCaches = ThreadLocal.withInitial(java.util.HashMap::new);
	}
	
	@Override
	protected synchronized void clearThreadLocals() {
		super.clearThreadLocals();
		removeAll(concurrentTemplateCaches);
	}
	
	@Override
	protected void nullifyThreadLocals() {
		super.nullifyThreadLocals();
		concurrentTemplateCaches = null;
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
	
	@Override
	public Map<URI, EglTemplate> getTemplateCache() {
		return parallelGet(concurrentTemplateCaches, templateCache);
	}
	
	@Override
	public Collection<Template> getInvokedTemplates() {
		return invokedTemplates;
	}
	
	@Override
	public IEgxModule getModule() {
		return (IEgxModule) super.getModule();
	}
	
	@Override
	public synchronized void dispose() {
		super.dispose();
		if (templateCache != null) templateCache.clear();
		if (invokedTemplates != null) invokedTemplates.clear();
	}
}
