/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage;

import org.eclipse.epsilon.coverage.model.CoverageElement;
import org.eclipse.epsilon.coverage.model.CoverageElementType;

/**
 * Null-safe utilities for working with the types of coverage elements.
 * 
 * @since 2.10
 */
public class CoverageElementTypes {
	
	private CoverageElementTypes() {}
	
	/**
	 * @return The fully qualified name of the Java class of the AST node the
	 * element was recorded from (e.g. org.eclipse.epsilon.eol.dom.StatementBlock),
	 * or null if the element has no type
	 */
	public static String getImpl(CoverageElement element) {
		CoverageElementType type = element.getType();
		return type == null ? null : type.getImpl();
	}
	
	/**
	 * @return The simple name of the Java class of the AST node the element
	 * was recorded from (e.g. StatementBlock), or null if the element has no type
	 */
	public static String getSimpleName(CoverageElement element) {
		return getSimpleName(element.getType());
	}
	
	/**
	 * @return The simple name of the Java class of the type (e.g. StatementBlock),
	 * or null if the type is null or has no impl
	 */
	public static String getSimpleName(CoverageElementType type) {
		String impl = type == null ? null : type.getImpl();
		if (impl == null) return null;
		return impl.substring(Math.max(impl.lastIndexOf('.'), impl.lastIndexOf('$')) + 1);
	}
	
	/**
	 * @return Whether the element was recorded from an AST node of the given class
	 */
	public static boolean isOfType(CoverageElement element, Class<?> clazz) {
		return clazz.getName().equals(getImpl(element));
	}
}
