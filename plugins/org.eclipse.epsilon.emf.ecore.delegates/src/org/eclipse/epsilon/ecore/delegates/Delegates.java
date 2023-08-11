/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * 
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors: 
 *   Horacio Hoyos Rodriguez - Initial API and implementation
 *******************************************************************************/
package org.eclipse.epsilon.ecore.delegates;

import org.eclipse.emf.ecore.EModelElement;

/**
 * Delegates associated to an EModelElement can provide validation, setting and invocation delegates
 * @param <E> the specific type of EModelElement
 * @param <D> the type of delegates
 * 
 * @since 2.5
 */
public interface Delegates<E extends EModelElement, D> {

	D create(E eClassifier);

	D create(String uri, E eClassifier);

}