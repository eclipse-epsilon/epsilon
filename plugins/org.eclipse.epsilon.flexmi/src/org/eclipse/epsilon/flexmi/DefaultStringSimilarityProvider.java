/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi;

public class DefaultStringSimilarityProvider implements StringSimilarityProvider {
	
	@Override
	public int getSimilarity(String first, String second) {
		if (first == null || second == null || first.isEmpty() || second.isEmpty()) return 0;

		int maxLen = 0;
		int firstLength = first.length();
		int secondLength = second.length();
		int[][] table = new int[firstLength + 1][secondLength + 1];

		for (int f = 0; f <= firstLength; f++) table[f][0] = 0;
		for (int s = 0; s <= secondLength; s++) table[0][s] = 0;
		
		for (int i = 1; i <= firstLength; i++) {
			for (int j = 1; j <= secondLength; j++) {
				if (first.charAt(i - 1) == second.charAt(j - 1)) {
					if (i == 1 || j == 1) {
						table[i][j] = 1;
					} else {
						table[i][j] = table[i - 1][j - 1] + 1;
					}
					if (table[i][j] > maxLen) {
						maxLen = table[i][j];
					}
				}
			}
		}
		
		if (second.startsWith(first)) maxLen = maxLen * 2;
		
		return maxLen;
	}

	
}
