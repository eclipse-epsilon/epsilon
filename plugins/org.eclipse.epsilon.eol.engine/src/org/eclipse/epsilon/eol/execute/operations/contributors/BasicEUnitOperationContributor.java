/*******************************************************************************
 * Copyright (c) 2011 Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio García-Domínguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.contributors;

import org.eclipse.epsilon.eol.exceptions.EolAssertionException;
import org.eclipse.epsilon.eol.types.EolNoType;
import org.eclipse.epsilon.eol.types.EolObjectComparator;

/**
 * Operation contributor for the simplest EUnit assertions. More advanced
 * assertions (or assertions which require additional dependencies) should
 * be placed in the ExtraEUnitOperationContributor in the eunit.engine
 * project.
 */
public class BasicEUnitOperationContributor extends OperationContributor {

	@Override
	public boolean contributesTo(Object target) {
		return EolNoType.NoInstance.equals(target);
	}

	public void assertTrue(boolean condition) throws EolAssertionException {
		assertTrue("Violated assertion", condition);
	}

	public void assertTrue(String message, boolean condition) throws EolAssertionException {
		compareBoolean(message, condition, true);
	}

	public void assertFalse(boolean condition) throws EolAssertionException {
		assertFalse("Violated assertion", condition);
	}

	public void assertFalse(String message, boolean condition) throws EolAssertionException {
		compareBoolean(message, condition, false);
	}

	public void assertEquals(Object expected, Object obtained) throws EolAssertionException {
		final String sExpected = getContext().getPrettyPrinterManager().print(expected);
		final String sObtained = getContext().getPrettyPrinterManager().print(obtained);
		final String message = "Expected " + sExpected + ", but got " + sObtained + " instead";
		assertEquals(message, expected, obtained);
	}

	public void assertEquals(String message, Object expected, Object obtained) throws EolAssertionException {
		compareGeneric(message, expected, obtained, true);
	}

	public void assertEquals(Number expected, Number obtained, Number ulps) throws EolAssertionException {
		assertEquals(null, expected, obtained, ulps);
	}

	public void assertEquals(String message, Number expected, Number obtained, Number ulps) throws EolAssertionException {
		compareUlps(message, expected, obtained, ulps, true);
	}

	public void assertNotEquals(Object expected, Object obtained) throws EolAssertionException {
		assertNotEquals("Expected something different from " + expected + ", but got something equal to it", expected, obtained);
	}

	public void assertNotEquals(String message, Object expected, Object obtained) throws EolAssertionException {
		compareGeneric(message, expected, obtained, false);
	}

	public void assertNotEquals(Number expected, Number obtained, Number ulps) throws EolAssertionException {
		assertNotEquals(null, expected, obtained, ulps);
	}

	public void assertNotEquals(String message, Number expected, Number obtained, Number ulps) throws EolAssertionException {
		compareUlps(message, expected, obtained, ulps, false);
	}

	public void fail(String message) throws EolAssertionException {
		throw new EolAssertionException(message, getContext().getFrameStack().getCurrentStatement(), null, null, null);
	}

	private void compareBoolean(String message, boolean condition, final boolean expected) throws EolAssertionException {
		if (condition != expected) {
			throw new EolAssertionException(
					getContext().getPrettyPrinterManager().toString(message),
					getContext().getFrameStack().getCurrentStatement(),
					expected, condition, null);
		}
	}

	private void compareGeneric(String message, Object expected, Object obtained, final boolean mustBeEqual)
			throws EolAssertionException {
		if (EolObjectComparator.equals(expected, obtained) != mustBeEqual) {
			throw new EolAssertionException(
					message, getContext().getFrameStack().getCurrentStatement(), expected, obtained, null);
		}
	}

	private void compareUlps(String message, Number expected, Number obtained, Number ulps, boolean mustBeEqual) throws EolAssertionException {
		final long intUlps = ulps.longValue();

		boolean bIsEqual = (expected == null) == (obtained == null);
		String upperBound = null, lowerBound = null;
		if (expected != null) {
			if (expected instanceof Float) {
				final float flExpected = expected.floatValue();
				final float flMargin = intUlps * Math.ulp(flExpected);
				bIsEqual = bIsEqual && Math.abs(obtained.floatValue() - flExpected) <= flMargin;
				lowerBound = Float.toString(flExpected - flMargin);
				upperBound = Float.toString(flExpected + flMargin);
			}
			else {
				final double dblExpected = expected.doubleValue();
				final double dblMargin = intUlps * Math.ulp(dblExpected);
				bIsEqual = bIsEqual && Math.abs(obtained.doubleValue() - dblExpected) <= dblMargin;
				lowerBound = Double.toString(dblExpected - dblMargin);
				upperBound = Double.toString(dblExpected + dblMargin);
			}
		}

		if (bIsEqual != mustBeEqual) {
			if (message == null) {
				message = "Expected " + obtained + (mustBeEqual ? "" : " not")
					+ " to be in [" + lowerBound + ", " + upperBound + "]";
			}
			throw new EolAssertionException(message, getContext().getFrameStack().getCurrentStatement(), expected, obtained != null ? obtained : "null", null);
		}
	}
}
