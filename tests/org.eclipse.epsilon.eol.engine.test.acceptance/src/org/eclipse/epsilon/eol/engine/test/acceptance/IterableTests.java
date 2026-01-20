/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.eclipse.epsilon.eol.engine.test.acceptance.eunit.EUnitRunnerParallel;
import org.eclipse.epsilon.eol.engine.test.acceptance.eunit.ExtraModel;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.java.JavaModel;
import org.junit.runner.RunWith;

/**
 * Tests for Iterable objects that are not Collections.
 */
@RunWith(EUnitRunnerParallel.class)
public class IterableTests {
	/**
	 * Implements the Iterable interface without being a Collection, using
	 * composition. Also provides a method that populates the list with a
	 * particular number of elements, and another one that creates new
	 * instances. The create method is required in order to test that Iterable
	 * values are not wrapped by the PointExecutor.
	 */
	public static class FakeIterable implements Iterable<Integer> {
		private final ArrayList<Integer> list = new ArrayList<>();

		public FakeIterable create(int nElements) {
			final FakeIterable iterable = new FakeIterable();
			iterable.populate(nElements);
			return iterable;
		}

		public void populate(int nElements) {
			list.clear();
			list.ensureCapacity(nElements);
			for (int i = 0; i < nElements; ++i) {
				list.add(i);
			}
		}

		@Override
		public Iterator<Integer> iterator() {
			return list.iterator();
		}
	}

	@ExtraModel
	public IModel getJavaModel() {
		final JavaModel jModel = new JavaModel(Arrays.asList(), Arrays.asList(FakeIterable.class));
		jModel.setName("J");
		jModel.setReadOnLoad(true);
		jModel.setStoredOnDisposal(false);
		return jModel;
	}
}
