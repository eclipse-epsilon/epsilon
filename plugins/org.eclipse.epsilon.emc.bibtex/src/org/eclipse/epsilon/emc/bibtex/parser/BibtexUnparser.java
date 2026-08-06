/*******************************************************************************
 * Copyright (c) 2010 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.bibtex.parser;

import java.util.Map.Entry;

import org.eclipse.epsilon.emc.bibtex.domain.Bibliography;
import org.eclipse.epsilon.emc.bibtex.domain.Publication;

public class BibtexUnparser {

	private final Bibliography bibliography;
	private final StringBuilder result = new StringBuilder();
	
	private final static String NEWLINE  = System.getProperty("line.separator");
	
	public BibtexUnparser(Bibliography bibliography) {
		this.bibliography = bibliography;
	}

	public String unparse() {
		for (Publication publication : bibliography.publications) {
			appendLine("@" + publication.type + "{" + publication.id);
			
			for (Entry<String, String> property : publication.getProperties()) {
				appendLine(property.getKey() + " = " + "{" + property.getValue() + "},");
			}
			
			appendLine("}");
		}
		
		return result.toString();
	}

	private void appendLine(String string) {
		result.append(string);
		result.append(NEWLINE);
	}

}
