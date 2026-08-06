/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.dt.editor.contentAssist;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.hutn.dt.editor.EmfMetamodelDirectory;
import org.eclipse.epsilon.hutn.parse.spec.HutnPreamble;

public class ContentAssistHelper {

	private final String content;
	private final String context;
	private final EmfMetamodelDirectory directory;
	
	private final LastWordLocator locator = new LastWordLocator();
	
	public ContentAssistHelper(String content) throws EolModelLoadingException {
		this.content   = content;
		this.context   = new Contextualiser().contextualise(content);
		this.directory = new EmfMetamodelDirectory(new HutnPreamble().process(content).getNsUris());
	}

	public String lastWord() {
		return locator.lastWord(content);
	}
	
	public Collection<String> computeCompletionProposals() {
		if (contextIsClass()) {
			return slotSuggestionsFor(context);
			
		} else {
			return classObjectSuggestions();
		}
	}
	
	private boolean contextIsClass() {
		return classObjectSuggestions().contains(context);
	}
	
	private Collection<String> classObjectSuggestions() {
		return directory.concreteClassNames();
	}
	
	private Collection<String> slotSuggestionsFor(String type) {
		final Collection<String> slotSuggestions = new LinkedList<>();
		
		for (String feature : directory.featureNamesFor(type)) {
			slotSuggestions.add(feature + ": ");
		}
		
		return slotSuggestions;
	}
}
