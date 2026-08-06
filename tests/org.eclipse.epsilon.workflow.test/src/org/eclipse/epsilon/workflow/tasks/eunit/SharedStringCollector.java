/*******************************************************************************
 * Copyright (c) 2008-2011 Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio García-Domínguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks.eunit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Test utility class with two static methods which add strings to a synchronized
 * shared list. This is useful to emulate behaviour-based (a la Mockito) testing
 * of EUnit test suites.
 *
 * Even though all methods are static, we keep the constructor public so EUnit
 * can create instances, as we cannot call static methods directly from EOL.
 */
public class SharedStringCollector {

	private final static List<String> strings = Collections.synchronizedList(new ArrayList<String>());

	/**
	 * Returns an unmodifiable list of the collected strings.
	 */
	public static List<String> getList() {
		return Collections.unmodifiableList(strings);
	}

	/**
	 * Adds a new element to the end of the list.
	 */
	public static void add(String s) {
		strings.add(s);
	}

}
