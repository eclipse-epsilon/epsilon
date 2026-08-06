/*******************************************************************************
 * Copyright (c) 2008 The University of York.
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
package org.eclipse.epsilon.hutn.xmi.test.integration.consistent.slots.singleValued;


public class Float extends AbstractSingleValuedSlotTest {
	
	@Override
	protected java.lang.String getXmi() {
		return "averageAge=\"26.5\"";
	}

	@Override
	protected java.lang.String getExpectedFeatureName() {
		return "averageAge";
	}
	
	@Override
	protected Object getExpectedContent() {
		return 26.5f;
	}
}
