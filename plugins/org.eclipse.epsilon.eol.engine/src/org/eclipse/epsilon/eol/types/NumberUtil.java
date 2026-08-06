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

import java.math.BigDecimal;

/**
 * NOTE: this class reimplements JLS sections 5.6.1 (unary numeric promotion)
 * and 5.6.2 (binary numeric promotion) to reduce casts and instanceof checks.
 */
public /*strictfp*/ class NumberUtil {
	
	public static Number multiply(Number n1, Number n2) {
		if (n1 instanceof BigDecimal || n2 instanceof BigDecimal) {
			return map(n1).multiply(map(n2));
		}
		else if (n1 instanceof Double || n2 instanceof Double) {
			return n1.doubleValue() * n2.doubleValue();
		}
		else if (n1 instanceof Float || n2 instanceof Float) {
			return n1.floatValue() * n2.floatValue();
		}
		else if (n1 instanceof Long || n2 instanceof Long) {
			return n1.longValue() * n2.longValue();
		}
		else {
			return n1.intValue() * n2.intValue();
		}
	}

	private static BigDecimal map(Number n) {
		if (n instanceof BigDecimal) {
			return (BigDecimal) n;
		} else {
			return new BigDecimal(n.toString());
		}
	}

	public static Number add(Number n1, Number n2) {
		if (n1 instanceof BigDecimal || n2 instanceof BigDecimal) {
			return map(n1).add(map(n2));
		}
		else if (n1 instanceof Double || n2 instanceof Double) {
			return n1.doubleValue() + n2.doubleValue();
		}
		else if (n1 instanceof Float || n2 instanceof Float) {
			return n1.floatValue() + n2.floatValue();
		}
		else if (n1 instanceof Long || n2 instanceof Long) {
			return n1.longValue() + n2.longValue();
		}
		else {
			return n1.intValue() + n2.intValue();
		}
	}
	
	public static boolean greaterThan(Number n1, Number n2) {
		if (n1 instanceof BigDecimal || n2 instanceof BigDecimal) {
			return map(n1).compareTo(map(n2)) > 0;
		}
		else if (n1 instanceof Double || n2 instanceof Double) {
			return n1.doubleValue() > n2.doubleValue();
		}
		else if (n1 instanceof Float || n2 instanceof Float) {
			return n1.floatValue() > n2.floatValue();
		}
		else if (n1 instanceof Long || n2 instanceof Long) {
			return n1.longValue() > n2.longValue();
		}
		else {
			return n1.intValue() > n2.intValue();
		}
	}

	public static Number divide(Number n1, Number n2) {
		if (n1 instanceof BigDecimal || n2 instanceof BigDecimal) {
			return map(n1).divide(map(n2));
		}
		else if (n1 instanceof Double || n2 instanceof Double) {
			return n1.doubleValue() / n2.doubleValue();
		}
		else if (n1 instanceof Float || n2 instanceof Float) {
			return n1.floatValue() / n2.floatValue();
		}
		else if (n1 instanceof Long || n2 instanceof Long) {
			return n1.longValue() / n2.longValue();
		}
		else {
			return n1.intValue() / n2.intValue();
		}
	}

	public static boolean lessThan(Number n1, Number n2) {
		if (n1 instanceof BigDecimal || n2 instanceof BigDecimal) {
			return map(n1).compareTo(map(n2)) < 0;
		}
		else if (n1 instanceof Double || n2 instanceof Double) {
			return n1.doubleValue() < n2.doubleValue();
		}
		else if (n1 instanceof Float || n2 instanceof Float) {
			return n1.floatValue() < n2.floatValue();
		}
		else if (n1 instanceof Long || n2 instanceof Long) {
			return n1.longValue() < n2.longValue();
		}
		else {
			return n1.intValue() < n2.intValue();
		}
	}

	public static Number subtract(Number n1, Number n2) {
		if (n1 instanceof BigDecimal || n2 instanceof BigDecimal) {
			return map(n1).subtract(map(n2));
		}
		else if (n1 instanceof Double || n2 instanceof Double) {
			return n1.doubleValue() - n2.doubleValue();
		}
		else if (n1 instanceof Float || n2 instanceof Float) {
			return n1.floatValue() - n2.floatValue();
		}
		else if (n1 instanceof Long || n2 instanceof Long) {
			return n1.longValue() - n2.longValue();
		}
		else {
			return n1.intValue() - n2.intValue();
		}
	}

	public static Number negative(Number n) {
		if (n instanceof BigDecimal) {
			return ((BigDecimal)n).negate();
		}
		else if (n instanceof Double) {
			return -n.doubleValue();
		}
		else if (n instanceof Float) {
			return -n.floatValue();
		}
		else if (n instanceof Long) {
			return -n.longValue();
		}
		else {
			return -n.intValue();
		}
	}

	public static boolean isEqual(Number n1, Number n2) {
		if (n1 instanceof BigDecimal || n2 instanceof BigDecimal) {
			return map(n1).equals(map(n2));
		}
		else if (n1 instanceof Double || n2 instanceof Double) {
			return n1.doubleValue() == n2.doubleValue();
		}
		else if (n1 instanceof Float || n2 instanceof Float) {
			return n1.floatValue() == n2.floatValue();
		}
		else if (n1 instanceof Long || n2 instanceof Long) {
			return n1.longValue() == n2.longValue();
		}
		else {
			return n1.intValue() == n2.intValue();
		}
	}
	
}
