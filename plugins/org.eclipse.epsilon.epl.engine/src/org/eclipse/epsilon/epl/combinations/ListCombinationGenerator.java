/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.epl.combinations;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ListCombinationGenerator<T> implements CombinationGenerator<T> {

	private int a[], n, r;
	private BigInteger remaining, total;
	protected List<T> list;
	protected boolean initialised = false;
	
	public void initialise() {
		if (!initialised) {
			//if (r > n || n < 1) throw new IllegalArgumentException();
			n = list.size();
			a = new int[r];
			BigInteger nFact = getFactorial(n);
			BigInteger rFact = getFactorial(r);
			BigInteger nminusrFact = getFactorial(n - r);
			total = nFact.divide(rFact.multiply(nminusrFact));
			
			initialised = true;
			reset();
		}
	}
	
	public ListCombinationGenerator(List<T> list, int r) {
		this.r = r;
		this.list = list;
	}

	@Override
	public void reset() {
		if (initialised) {
			for (int i = 0; i < a.length; i++) {
				a[i] = i;
			}
			remaining = new BigInteger(total.toString());
		}
	}

	public BigInteger getRemaining() {
		initialise();
		return remaining;
	}

	@Override
	public boolean hasNext() {
		initialise();
		if (list.isEmpty() || r > list.size())
			return false;
		
		return remaining.compareTo(BigInteger.ZERO) == 1;
	}

	public BigInteger getTotal() {
		initialise();
		return total;
	}

	private static BigInteger getFactorial(int n) {
		BigInteger fact = BigInteger.ONE;
		for (int i = n; i > 1; i--) {
			fact = fact.multiply(new BigInteger(Integer.toString(i)));
		}
		return fact;
	}

	@Override
	public List<T> next() {
		initialise();
		if (!hasNext()) return null;
		
		if (remaining.equals(total)) {
			remaining = remaining.subtract(BigInteger.ONE);
		}
		else {
			int i = r - 1;
			while (a[i] == n - r + i) {
				i--;
			}
			a[i] = a[i] + 1;
			for (int j = i + 1; j < r; j++) {
				a[j] = a[i] + j - i;
			}
	
			remaining = remaining.subtract(BigInteger.ONE);
		}
		
		List<T> next = new ArrayList<>(a.length);
		for (int j : a) {
			next.add(list.get(j));
		}
		
		return next;
	}

	@Override
	public void producedValidCombination() {
		// TODO Auto-generated method stub
	}
}