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
package org.eclipse.epsilon.flock.engine.test.acceptance.util;

import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage;
import org.eclipse.epsilon.test.fixtures.hutn.AbstractEmfModelConstructor;

public class FamiliesModelConstructor extends AbstractEmfModelConstructor {

	@Override
	protected List<String> getNsUris() {
		return Collections.singletonList(FamiliesPackage.eNS_URI);
	}
		
	@Override
	protected List<String> getConfigFiles() {
		return Collections.singletonList(null);
	}
}
