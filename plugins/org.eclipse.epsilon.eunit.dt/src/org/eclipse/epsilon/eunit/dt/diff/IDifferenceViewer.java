/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eunit.dt.diff;

/**
 * Interface for showing the differences between the expected value and the
 * actual value.
 * 
 * @author Antonio Garcia-Dominguez
 */
public interface IDifferenceViewer {

	String EXTENSION_POINT_ID = "org.eclipse.epsilon.eunit.dt.diffviewer";

	/**
	 * Returns <code>true</code> if this viewer can show the differences between
	 * the expected and the actual results. This method will be called from the
	 * SWT thread.
	 * 
	 * @param expected
	 *            Expected value.
	 * @param actual
	 *            Actual value.
	 * @param delta
	 *            Precomputed differences, if available. Otherwise,
	 *            <code>null</code>.
	 * @return <p>
	 *         <code>true</code> if this instance can either:
	 *         </p>
	 *         <ul>
	 *         <li>compute and show the differences between
	 *         <code>expected</code> and <code>actual</code></li>
	 *         <li>, or b) show the precomputed differences in
	 *         <code>delta</code>.</li>
	 *         </ul>
	 *         <p>
	 *         Otherwise, returns <code>false</code>.
	 *         </p>
	 */
	boolean canCompare(Object expected, Object actual, Object delta);

	/**
	 * Shows the differences between the expected and actual values, which might
	 * have been precomputed or not. This method will be called from the SWT
	 * thread. This method should only be called if {@link #canCompare} returned
	 * true with the same parameters.
	 * 
	 * @param expected
	 *            Expected value.
	 * @param actual
	 *            Actual value.
	 * @param delta
	 *            Precomputed difference, if available. Otherwise,
	 *            <code>null</code>.
	 */
	void compare(Object expected, Object actual, Object delta);
}
