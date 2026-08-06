/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eml.execute.operations;

import org.eclipse.epsilon.ecl.execute.operations.MatchesOperation;
import org.eclipse.epsilon.eol.execute.operations.EolOperationFactory;

public class EmlOperationFactory extends EolOperationFactory {
	
	public EmlOperationFactory () {
		super();
		operationCache.put("matches", new MatchesOperation());
		operationCache.put("equivalent", new EquivalentOperation());
		operationCache.put("equivalents", new EquivalentsOperation());
	}
	
}
