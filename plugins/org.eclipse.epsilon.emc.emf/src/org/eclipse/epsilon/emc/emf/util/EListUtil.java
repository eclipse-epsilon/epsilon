/*******************************************************************************
 * Copyright (c) 2008 The University of York.
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
package org.eclipse.epsilon.emc.emf.util;

import java.util.Arrays;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

public abstract class EListUtil {

	private EListUtil() {}
	
	@SafeVarargs
	public static <T> EList<T> asEList(T... array) {
		final EList<T> list = new BasicEList<>();
		
		list.addAll(Arrays.asList(array));
		
		return list;
	}
	
	public static <T> EList<T> singletonEList(T original) {
		final EList<T> list = new BasicEList<>();
		
		list.add(original);
		
		return list;
	}
	
	public static boolean elementsAreAllOfSameType(EList<?> list) {
		if (!list.isEmpty()) {
			if (list.get(0) != null) {
				return elementsAreAllInstancesOf(list, list.get(0).getClass());
			
			} else {
				return elementsAreAllNull(list);
			}
		}
		
		return true;
	}
	
	private static boolean elementsAreAllNull(EList<?> list) {
		for (Object element : list) {
			if (element != null) {
				return false;
			}
		}
		
		return true;
	}

	public static boolean elementsAreAllInstancesOf(EList<?> list, Class<?> type) {
		for (Object element : list) {
			if (!type.isInstance(element)) {
				return false;
			}
		}
		
		return true;
	}
	
	public static <T> EList<T> castElements(EList<?> list, Class<? extends T> type) {
		final EList<T> results = new BasicEList<>();
		
		for (Object element : list) {
			results.add(type.cast(element));
		}
		
		return results;
	}
}
