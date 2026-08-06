/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.json;

import java.util.Set;

/**
 * Interface for something that has a container.
 */
public interface Contained {

	Set<Object> getContainers();

	default boolean addContainer(Object container) {
		return getContainers().add(container);
	}

	default Object removeContainer(Object container) {
		return getContainers().remove(container);
	}

	default boolean isContainedBy(Object element) {
		for (Object container : getContainers()) {
			if (container == element) {
				// May not be a Contained (e.g. a JsonModel)
				return true;
			}
			if (container instanceof Contained && ((Contained) container).isContainedBy(element)) {
				return true;
			}
		}

		return false;
	}

}
