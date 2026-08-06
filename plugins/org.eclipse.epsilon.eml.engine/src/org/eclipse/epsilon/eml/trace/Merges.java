/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eml.trace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ListIterator;

import org.eclipse.epsilon.common.util.CollectionUtil;

public class Merges extends ArrayList<Merge> {
	
	public Collection<Object> getTargets() {
		ListIterator<Merge> li = listIterator();
		Collection<Object> targets = CollectionUtil.createDefaultList();
		while (li.hasNext()) {
			Merge merge = li.next();
			targets.addAll(merge.getTargets());
		}
		return targets;
	}

	
}
