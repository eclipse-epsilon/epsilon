/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.contributors;

import java.util.stream.LongStream;
import org.eclipse.epsilon.eol.types.NumberUtil;

public class NumberOperationContributor extends OperationContributor {

	@Override
	public boolean contributesTo(Object target) {
		return target instanceof Number;
	}
	
	@Override
	protected Number getTarget() {
		return (Number) super.getTarget();
	}
	
	/**
	 * n!. Note that only values less than or equal to 20 are supported.
	 * @return Factorial of self.
	 * @since 1.6
	 */
	public long factorial() {
		long n = getTarget().longValue();
        if (n > 20 || n < 0) throw new IllegalArgumentException(n + " is out of range");
        return LongStream.rangeClosed(2, n).reduce(1, (a, b) -> a * b);
	}
	
	public Number min(Number other) {
		Number self = getTarget();
		return NumberUtil.lessThan(self, other) ? self : other;
	}
	
	public Number max(Number other) {
		Number self = getTarget();
		return NumberUtil.greaterThan(self, other) ? self : other;
	}
	
	public int floor() {
		return (int) Math.floor(asDouble());
	}
	
	public Number pow(Number n) {
		Double pow = Math.pow(asDouble(), n.doubleValue());
		if (getTarget() instanceof Integer && n instanceof Integer && NumberUtil.greaterThan(n, 0)) {
			return pow.intValue();
		}
		else {
			return pow;
		}
	}
	
	public int ceiling() {
		return (int) Math.ceil(asDouble());
	}
	
	public int round() {
		return Math.round(asFloat());
	}
	
	public double ln() {
		return log();
	}
	
	public double log() {
		return Math.log(asDouble());
	}
	
	public double log10() {
		return Math.log10(asDouble());
	}
	
	public Number abs() {
		Object target = getTarget();
		if (target instanceof Integer) return Math.abs((Integer) target);
		if (target instanceof Float) return Math.abs((Float) target);
		if (target instanceof Double) return Math.abs((Double) target);
		if (target instanceof Long) return Math.abs((Long) target);
		return null;
	}
	
	public double asReal() {
		return asFloat();
	}
	
	public double asDouble() {
		return getTarget().doubleValue();
	}
	
	public float asFloat() {
		return getTarget().floatValue();
	}
	
	public int asInteger() {
		return getTarget().intValue();
	}
	
	public long asLong() {
		return getTarget().longValue();
	}
	
}
