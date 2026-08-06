/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.test.util;

import java.util.Collection;
import java.util.Objects;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public class EpsilonTestUtil {
	protected EpsilonTestUtil() {}
	
	/**
	 * Convenience hack for handling exceptions when resolving this class's package source directory.
	 */
	public static String getTestBaseDir(Class<?> clazz) {
		try {
			// Using temp dirs, so no need to do anything here
			return "";//Paths.get(clazz.getResource("").toURI()).toString().replace("bin", "src")+'/';
		}
		catch (Exception urx) {
			System.err.println(urx.getMessage());
			return null;
		}
	}
	
	/**
	 * 
	 * @param condition
	 * @param expected
	 * @param actual
	 * @param dataLabel
	 * @return The message that was printed, or <code>null</code> if the condition was false.
	 */
	public static <T> String printIfDifferent(boolean condition, T expected, T actual, String dataLabel) {
		if (condition) {
			String intermediary, message = "";
			System.err.println(intermediary = dataLabel+" differ! ");
			message += intermediary;
			System.err.println(intermediary = "Expected "+expected.getClass().getSimpleName()+": ");
			message += intermediary;
			System.err.println(intermediary = Objects.toString(expected));
			message += intermediary+", ";
			System.err.println(intermediary = "Actual "+actual.getClass().getSimpleName()+": ");
			message += intermediary;
			System.err.println(intermediary = Objects.toString(actual));
			message += intermediary;
			System.err.println();
			return message;
		}
		return null;
	}
	
	/**
	 * Tests the two collections for equality, irrespective of ordering.
	 * @param expected
	 * @param actual
	 * @param collectionName
	 * @return The failure message, or <code>null</code> if the collections have the same elements.
	 */
	public static String testCollectionsHaveSameElements(Collection<?> expected, Collection<?> actual, String collectionName) {
		int actualSize = actual.size(), expectedSize = expected.size();
		
		String sameSize = printIfDifferent(actualSize != expectedSize, expectedSize, actualSize, collectionName+" sizes");
		if (sameSize != null) {
			System.err.println("Expected: \n\t"+expected);
			System.err.println("Actual: \n\t"+actual);
			return sameSize;
		}

		return printIfDifferent(!actual.containsAll(expected), expected, actual, collectionName);
	}
}
