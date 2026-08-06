/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi;

import java.util.HashMap;

public class CachedStringSimilarityProvider implements StringSimilarityProvider {
	
	protected HashMap<String, Integer> cache = new HashMap<>();
	protected StringSimilarityProvider delegate;
	
	public CachedStringSimilarityProvider(StringSimilarityProvider delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public int getSimilarity(String one, String other) {
		
		Integer similarity = cache.get(one + "-" + other);
		if (similarity == null) {
			similarity = delegate.getSimilarity(one, other);
			cache.put(one + "-" + other, similarity);
		}
		return similarity;
	}

}
