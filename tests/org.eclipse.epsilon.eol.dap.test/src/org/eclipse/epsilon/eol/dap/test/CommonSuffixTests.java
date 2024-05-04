/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.eclipse.epsilon.eol.dap.CommonSuffix;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class CommonSuffixTests {

	@Parameters
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{0, "a", "b"},
			{1, "a", "a"},
			{1, "a/b", "b"},
			{1, "b", "a/b"},
			{1, "a/b", "b/b"},
			{2, "a/a", "a/a"},
			{2, "c/a/a", "b/a/a"},
		});
	}

	private int expected;
	private String inputA;
	private String inputB;

	public CommonSuffixTests(int expected, String inputA, String inputB) {
		this.expected = expected;
		this.inputA = inputA;
		this.inputB = inputB;
	}

	@Test
	public void test() {
		final String message = String.format(
			"There should be %d matching trailing components between '%s' and '%s'",
			expected, inputA, inputB);

		final int actual = CommonSuffix.countMatchingTrailingComponents("/", inputA, inputB);
		assertEquals(message, expected, actual);
	}
	
}
