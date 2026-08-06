/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.dt.nature;

import org.eclipse.epsilon.common.dt.nature.BuilderConfiguringNature;

public class HutnNature extends BuilderConfiguringNature {

	static final String ID = "org.eclipse.epsilon.hutn.dt.nature.HutnNature";
	
	@Override
	protected String getBuilderID() {
		return HutnBuilder.ID;
	}

}
