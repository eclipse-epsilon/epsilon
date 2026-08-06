/*********************************************************************
 * Copyright (c) 2018 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.common.concurrent;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;
import org.eclipse.epsilon.common.function.BaseDelegate;

/**
 * 
 * @author Sina Madani
 * @since 1.6
 */
public interface ConcurrentBaseDelegate<T extends ConcurrentBaseDelegate<T>> extends BaseDelegate<T> {
	
	boolean isThreadSafe();
	
	void setThreadSafe(boolean concurrent);
	
	default void mergeAndSetThreadSafety(MergeMode mode, boolean threadSafe) {
		merge(mode);
		setThreadSafe(threadSafe);
	}
	
	/**
	 * Calls {@link BaseDelegate#mergeCollectionsUnique(Function, Function, MergeMode)}
	 * with threadSafeTargetCol if the target (i.e. this or the base) should be thread-safe.
	 */
	default <C> void mergeCollectionsUnique(
		Function<T, Collection<C>> colPropertyGetter,
		Supplier<? extends Collection<C>> threadSafeTargetCol,
		Supplier<? extends Collection<C>> targetCol,
		MergeMode mode) {
		
			T to = getTo(mode);
			
			if (to != null) {
				BaseDelegate.super.mergeCollectionsUnique(
					colPropertyGetter,
					to.isThreadSafe() ? threadSafeTargetCol : targetCol,
					mode
				);
			}
	}
}
