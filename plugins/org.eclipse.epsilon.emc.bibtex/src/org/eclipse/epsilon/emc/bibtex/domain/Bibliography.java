/*******************************************************************************
 * Copyright (c) 2010 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.bibtex.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Bibliography {
	public Collection<Publication> publications = new LinkedList<>();
	public Set<String> knownTypes = new HashSet<>();
	
	public void add(Publication publication) {
		publications.add(publication);
		knownTypes.add(publication.type.toLowerCase());
	}

	public boolean hasType(String type) {
		return knownTypes.contains(type.toLowerCase());
	}

	public Collection<Publication> allOfType(String type) {
		final LinkedList<Publication> allOfType = new LinkedList<>();
		
		for (Publication publication : publications) {
			if (publication.type.equalsIgnoreCase(type)) {
				allOfType.add(publication);
			}
		}
		
		return allOfType;
	}
}
