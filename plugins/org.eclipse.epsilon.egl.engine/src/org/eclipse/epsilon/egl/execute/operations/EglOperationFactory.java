/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.egl.execute.operations;

import org.eclipse.epsilon.eol.execute.operations.EolOperationFactory;

public class EglOperationFactory extends EolOperationFactory {
	
	public EglOperationFactory() {
		operationCache.put("include", new IncludeOperation());
		operationCache.put("_outdent", new OutdentOperation());
	}
	
}
