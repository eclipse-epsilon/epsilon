/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.types.concurrent;

import org.eclipse.epsilon.common.concurrent.ConcurrencyUtils;
import org.eclipse.epsilon.eol.types.EolCollection;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 * @param <T>
 */
public class EolConcurrentBag<T> extends EolCollection<T> {

	public EolConcurrentBag() {
		super(ConcurrencyUtils.concurrentOrderedCollection());
	}
}
