/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * Copyright (c) 2014 University of Twente.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Maarten Bezemer - toBinary, toHex
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.contributors;

import org.eclipse.epsilon.eol.types.EolSequence;

public class IntegerOperationContributor extends OperationContributor {
	
	@Override
	public boolean contributesTo(Object target) {
		return target instanceof Integer;
	}

	@Override
	protected Integer getTarget() {
		return (Integer) super.getTarget();
	}
	
	public EolSequence<Integer> to(Integer end) {
		int start = getTarget();
		EolSequence<Integer> result = new EolSequence<>();
		if (start < end) {
			int cap = end-start;
			if (cap > 0) result.ensureCapacity(cap);
			for (int i = start; i <= end; i++) {
				result.add(i);
			}
		}
		else {
			int cap = start-end;
			if (cap > 0) result.ensureCapacity(cap);
			for (int i = start; i >= end; i--) {
				result.add(i);
			}
		}
		return result;
	}

	public EolSequence<Integer> iota(int i, int step) throws Exception {
		EolSequence<Integer> result = new EolSequence<>();
		int x = getTarget();
		if ((step == 0) || (x < i && step < 0) || (x > i && step > 0)) {
			throw new Exception("Invalid argument(s) to iota(i, step)");
		}
		if (step > 0 && i > step) {
			int div = i / step;
			result.ensureCapacity(div+1);
		}
		while (x <= i) {
			result.add(x);
			x = x + step;
		}
		return result;
	}

	public String toBinary() {
		return Integer.toBinaryString(getTarget());
	}

	public String toHex() {
		return Integer.toHexString(getTarget());
	}
	
	public Integer mod(Integer other) {
		return getTarget() % other;
	}
	
}
