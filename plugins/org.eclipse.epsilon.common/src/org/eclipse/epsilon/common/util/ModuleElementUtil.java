/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.util;

import java.util.*;
import org.eclipse.epsilon.common.module.*;

/**
 * 
 * @since 1.6
 */
public class ModuleElementUtil {

	/**
	 * Recursively retrieves all of the ModuleElement's children, returning a
	 * unique flattened collection in encounter order.
	 * 
	 * @param ast The ModuleElement to flatten.
	 * 
	 * @return All children (including subchildren) of the ModuleElement. The collection is
	 * guaranteed to contain no duplicates.
	 * 
	 * @author Sina Madani
	 * @since 1.6
	 */
	public static List<ModuleElement> flattenAST(ModuleElement ast) {
		return new ArrayList<>(flattenAST(ast, new LinkedHashSet<>()));
	}
	
	protected static <C extends Collection<ModuleElement>> C flattenAST(ModuleElement ast, C collection) {
		if (ast != null) {
			collection.add(ast);
			Iterable<? extends ModuleElement> children = ast.getChildren();
			if (children != null) for (ModuleElement child : children) {
				collection.addAll(flattenAST(child, collection));
			}
		}
		return collection;
	}
	
}
