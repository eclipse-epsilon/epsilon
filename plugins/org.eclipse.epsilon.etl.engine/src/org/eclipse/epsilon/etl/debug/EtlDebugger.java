/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.etl.debug;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.etl.dom.TransformationRule;

public class EtlDebugger extends EolDebugger {
	
	@Override
	protected boolean isExpressionOrStatementBlockContainer(ModuleElement ast) {
		return super.isExpressionOrStatementBlockContainer(ast) || ast instanceof TransformationRule;
	}
	
	@Override
	protected boolean isStructuralBlock(ModuleElement ast) {
		return super.isStructuralBlock(ast) || ast instanceof TransformationRule;
	}
	
}
