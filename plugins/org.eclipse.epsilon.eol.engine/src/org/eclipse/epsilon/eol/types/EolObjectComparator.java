/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.types;

import java.util.*;

public class EolObjectComparator {
	
	public static boolean equals(Object o1, Object o2) {
		if (o1 == null && o2 == null) return true;
		if (o1 == null || o2 == null) return false;

		if (o1 instanceof Number && o2 instanceof Number) {
			return NumberUtil.isEqual((Number)o1, (Number)o2);
		}
		
		if (o1 instanceof Collection && o2 instanceof Collection) {
			Collection<?> c1 = (Collection<?>) o1;
			Collection<?> c2 = (Collection<?>) o2;
		
			if (c1.size() != c2.size()) return false;
			
			if (EolCollectionType.Bag.isType(c1) && EolCollectionType.Bag.isType(c2)) {
				List<Object> l1 = new ArrayList<>(c1);
				List<Object> l2 = new ArrayList<>(c2);
				
				Comparator<Object> comparator = (e1, e2) -> String.valueOf(e1).compareTo(String.valueOf(e2));
				
				Collections.sort(l1, comparator);
				Collections.sort(l2, comparator);
				
				return l1.equals(l2);
			}
		}
		
		return o1.equals(o2);
	}
}
