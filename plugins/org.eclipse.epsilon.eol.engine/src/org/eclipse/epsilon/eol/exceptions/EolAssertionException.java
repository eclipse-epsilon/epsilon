/*******************************************************************************
 * Copyright (c) 2008-2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - actual/expected/delta fields
 ******************************************************************************/
package org.eclipse.epsilon.eol.exceptions;

import org.eclipse.epsilon.common.module.ModuleElement;

/**
 * Exception which is thrown when the expectations in a test case are not met.
 * These exceptions include information about the expected value and the value
 * which was obtained, if available. Optionally, a custom object representing
 * the difference between the expected and actual value can be stored.
 */
public class EolAssertionException extends EolRuntimeException {

	private static final long serialVersionUID = 1L;
	private final Object expected, actual, delta;

	/**
	 * <p>
	 * Creates a new instance. There are three acceptable combinations for the
	 * values of <code>expected</code>, <code>actual</code> and
	 * <code>delta</code>:
	 * </p>
	 *
	 * <ol>
	 * <li>None are <code>null</code>: expected and actual values are available,
	 * and their difference has been precomputed.</li>
	 * <li><code>delta</code> is <code>null</code>: expected and actual values
	 * are available, but the difference has not been precomputed. A regular
	 * text-based line-by-line comparison will be used.</li>
	 * <li>All three are <code>null</code>: no information is available. This
	 * may happen when calling the <code>fail(msg)</code> built-in operation,
	 * for instance, or when using assertNotEquals (it may not make sense to
	 * present differences when they are really the same).</li>
	 * </ol>
	 *
	 * @param reason
	 *            String explaining the expectation which was not met.
	 * @param ast
	 *            AST node of the call to the assertion operation whose test
	 *            failed.
	 * @param expected
	 *            Expected value, if applicable. Otherwise, <code>null</code>.
	 * @param actual
	 *            Actual value, if applicable. Otherwise, <code>null</code>.
	 * @param delta
	 *            Precomputed delta between the values, if available. Otherwise,
	 *            <code>null</code>.
	 */
	public EolAssertionException(String reason, ModuleElement ast, Object expected, Object actual, Object delta) {
		super(reason, ast);
		this.expected = expected;
		this.actual = actual;
		this.delta = delta;
	}

	/**
	 * Returns the expected value, or <code>null</code> if not available.
	 */
	public Object getExpected() {
		return expected;
	}

	/**
	 * Returns the actual value, or <code>null</code> if not available.
	 */
	public Object getActual() {
		return actual;
	}

	/**
	 * Returns the precomputed difference between the expected and the actual
	 * value, or <code>null</code> if not available.
	 */
	public Object getDelta() {
		return delta;
	}
}
