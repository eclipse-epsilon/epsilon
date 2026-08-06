/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.servlet.caching;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PageCache {
	
	
	private final Map<String, String> cache = new HashMap<String, String>();
	private final Set<String> identifierPatternsThatShouldBeCached = new HashSet<String>();
	
	public void allowCachingOf(String pattern) {
		identifierPatternsThatShouldBeCached.add(pattern);
	}
	
	public void preventAndExpireCachingOf(String pattern) {
		identifierPatternsThatShouldBeCached.remove(pattern);
		expireAll(pattern);
	}
	
	public void cache(String identifier, String page) {
		for (String pattern : identifierPatternsThatShouldBeCached) {
			if (identifier.matches(pattern)) {
				cache.put(identifier, page);
			}
		}
	}
	
	public boolean isCached(String identifier) {
		return cache.containsKey(identifier);
	}

	public String retrieve(String identifier) {
		return cache.get(identifier);
	}
	
	private void expireAll(String pattern) {
		// Use an iterator so that removal does not 
		// affect the collection that we're iterating over
		for (Iterator<String> identifierIterator = cache.keySet().iterator(); identifierIterator.hasNext();) {
			final String identifier = identifierIterator.next();
	
			if (identifier.matches(pattern)) {
				identifierIterator.remove();
			}
		}	
	}
}
