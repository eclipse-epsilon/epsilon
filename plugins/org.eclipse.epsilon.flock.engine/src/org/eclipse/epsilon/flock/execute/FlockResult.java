/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.flock.execute;

import java.io.PrintStream;
import java.util.Collection;
import java.util.LinkedList;

public class FlockResult {

	private final Collection<String> warnings = new LinkedList<>();
	
	public void addWarning(String warning) {
		warnings.add(warning);
	}
	
	public Collection<String> getWarnings() {
		return warnings;
	}
	
	public void printWarnings(PrintStream printStream) {
		for (String warning : warnings) {
			printStream.println(warning);
		}
	}
}
