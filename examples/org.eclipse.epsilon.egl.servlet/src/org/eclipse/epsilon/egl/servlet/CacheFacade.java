/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.servlet;

import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.servlet.caching.FragmentCache;
import org.eclipse.epsilon.egl.servlet.caching.PageCache;

/**
 * Facade class used by EGL Templates to access caching operations.
 * 
 * EGL clients should access only the public operations. Java 
 * clients can access package private member variables for 
 * finer-grained control over caching.
 *  
 * @author louis
 */
public class CacheFacade {

	final PageCache     pageCache     = new PageCache();
	final FragmentCache fragmentCache = new FragmentCache();
	
	public void pages(String pattern) {
		pageCache.allowCachingOf(pattern);
	}
	
	public void expirePages(String pattern) {
		pageCache.preventAndExpireCachingOf(pattern);
	}
	
	public String fragment(EglTemplate template) throws EglRuntimeException {
		return fragmentCache.cache(template);
	}
	
	public void expireFragment(EglTemplate template) throws EglRuntimeException {
		fragmentCache.expire(template);
	}
}
