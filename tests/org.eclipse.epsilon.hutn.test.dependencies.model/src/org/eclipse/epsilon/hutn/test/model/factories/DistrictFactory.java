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
package org.eclipse.epsilon.hutn.test.model.factories;

import org.eclipse.epsilon.hutn.test.model.families.District;
import org.eclipse.epsilon.hutn.test.model.families.FamiliesFactory;

public abstract class DistrictFactory {

	private DistrictFactory() {}
	
	public static District createDistrict() {
		return FamiliesFactory.eINSTANCE.createDistrict();
	}
}
