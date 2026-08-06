/*********************************************************************
 * Copyright (c) 2019 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eml;

import java.util.List;

import org.eclipse.epsilon.eml.dom.MergeRule;
import org.eclipse.epsilon.eml.execute.context.IEmlContext;
import org.eclipse.epsilon.etl.IEtlModule;

/**
 * 
 * 
 * @author Betty Sanchez
 * @since 1.6
 */
public interface IEmlModule extends IEtlModule {

	List<MergeRule> getMergeRules();

	List<MergeRule> getDeclaredMergeRules();

	@Override
	default IEmlContext getContext() {
		return (IEmlContext) ((IEtlModule)this).getContext();
	}
	
}
