/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eml.debug;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eml.dom.MergeRule;
import org.eclipse.epsilon.etl.debug.EtlDebugger;

public class EmlDebugger extends EtlDebugger {
	
	@Override
	protected boolean isExpressionOrStatementBlockContainer(ModuleElement ast) {
		return super.isExpressionOrStatementBlockContainer(ast) || ast instanceof MergeRule;
	}
	
	@Override
	protected boolean isStructuralBlock(ModuleElement ast) {
		return super.isStructuralBlock(ast) || ast instanceof MergeRule;
	}
	
}
