/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.dt.editor;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;

final class HutnKeywordManager {
	
	private final HutnScanner scanner;
	
	private List<String> cachedKeywords = new LinkedList<>();
	
	public HutnKeywordManager(HutnScanner scanner) {
		this.scanner = scanner;
	}
	
	
	public boolean updateKeywordsFrom(List<String> nsUris) {
		if (nsUris == null)
			throw new IllegalArgumentException("nsUris cannot be null");
		
		if (!keywordsHaveChanged(nsUris))
			return false;
		
		
		final List<String> keywords = getKeywordsFor(nsUris);
		scanner.setKeywords(keywords);
		cachedKeywords = keywords;
		
		return true;
	}
	
	public boolean keywordsHaveChanged(List<String> nsUris) {
		return !cachedKeywords.equals(getKeywordsFor(nsUris));
	}
	
	private List<String> getKeywordsFor(List<String> nsUris) {
		final List<String> keywords = new LinkedList<>();

		for (String nsUri : nsUris) {
			try {
				final EmfMetamodelDirectory metamodel = new EmfMetamodelDirectory(nsUri);
				
				keywords.addAll(metamodel.classifierNames());
				keywords.addAll(metamodel.packageNames());
				
			} catch (EolModelLoadingException e) {
				// Ignore, as this is reported as a parse problem
			}
		}
		
		return keywords;
	}
}
