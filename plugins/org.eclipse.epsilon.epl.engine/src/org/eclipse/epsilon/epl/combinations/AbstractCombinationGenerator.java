/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.epl.combinations;

import java.util.List;

public abstract class AbstractCombinationGenerator<T> implements CombinationGenerator<T> {
	
	protected int index = 0;

	@Override
	public List<T> next() {
		index++;
		return getNextImpl();
	}
	
	protected abstract List<T> getNextImpl();
	
	@Override
	public void reset() {
		resetImpl();
		index = 0;
	}

	protected abstract void resetImpl();
	
}
