/*******************************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.epsilon.eol.dap;

public class CommonSuffix {

	private CommonSuffix() {}

	/**
	 * Counts the number of matching components at the end between two strings,
	 * as split according to a separator. Whitespace is ignored.
	 *
	 * @param sep Separator to split both strings.
	 * @param a First string to match.
	 * @param b Second string to match.
	 */
	public static int countMatchingTrailingComponents(String sep, String a, String b) {
		if (sep == null) {
			throw new IllegalArgumentException("sep should not be null");
		}
		if (a == null) {
			throw new IllegalArgumentException("a should not be null");
		}
		if (b == null) {
			throw new IllegalArgumentException("b should not be null");
		}

		String[] aSegments = a.split(sep);
		String[] bSegments = b.split(sep);

		int matching = 0;
		for (int iA = aSegments.length - 1, iB = bSegments.length - 1; iA >= 0 && iB >= 0; iA--, iB--) {
			if (!aSegments[iA].equals(bSegments[iB])) {
				break;
			}
			++matching;
		}

		return matching;
	}
}
