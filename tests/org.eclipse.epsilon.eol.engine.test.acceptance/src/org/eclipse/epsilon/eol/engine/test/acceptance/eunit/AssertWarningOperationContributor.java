/*******************************************************************************
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance.eunit;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.eclipse.epsilon.eol.exceptions.EolAssertionException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.operations.contributors.OperationContributor;

public class AssertWarningOperationContributor extends OperationContributor {
	
	private BookmarkedStream warnings = new BookmarkedStream();

	public AssertWarningOperationContributor(IEolContext context) {
		attachObservableWarningStream(context);
	}
	
	private void attachObservableWarningStream(IEolContext context) {
		context.setWarningStream(warnings.toPrintSteam());
	}

	@Override
	public boolean contributesTo(Object target) {
		return true;
	}
	
	public void assertNoWarnings() throws EolAssertionException {
		try {
			final boolean success = warnings.getUnreadContents().isEmpty();
		
			if (!success) {
				fail("Expected no warnings, but got: " + warnings.getUnreadContents(), null, null);
			}
			
		} finally {
			warnings.markCurrentContentsAsRead();
		}
	}
	
	public void assertWarning(String warning) throws EolAssertionException {
		try {
			final boolean success = warnings.getUnreadContents().contains(warning);
			
			if (!success) {
				fail(failureMessage(warning), warning, warnings.getUnreadContents());
			}
		
		} finally {
			warnings.markCurrentContentsAsRead();
		}
	}

	private String failureMessage(String warning) {
		if (warnings.getUnreadContents().isEmpty()) {
			return "No warnings were issued, but was expecting: " + warning;
		} else {
			return "Expected '" + warning + "', but got: " + warnings.getUnreadContents();				
		}
	}
	
	private void fail(String reason, String expected, String actual) throws EolAssertionException {
		throw new EolAssertionException(
				getContext().getPrettyPrinterManager().toString(reason),
				getContext().getFrameStack().getCurrentStatement(),
				expected, actual, null);
	}
	
	
	public static class BookmarkedStream {
		private final ByteArrayOutputStream observableWarningStream = new ByteArrayOutputStream();
		private int indexOfFirstUnreadCharacter = 0;
		
		public PrintStream toPrintSteam() {
			return new PrintStream(observableWarningStream);
		}

		public String getUnreadContents() {
			return observableWarningStream.toString().substring(indexOfFirstUnreadCharacter);
		}
		
		public void markCurrentContentsAsRead() {
			indexOfFirstUnreadCharacter = observableWarningStream.toString().length();
		}
	}
}
