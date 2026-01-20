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

/**
 * Interface for something that knows the {@link JsonModel} that created it.
 */
public interface HasCreatorModel {

	JsonModel getCreatorModel();

}
