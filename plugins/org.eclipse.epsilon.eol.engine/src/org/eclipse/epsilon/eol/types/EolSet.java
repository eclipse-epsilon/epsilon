/*******************************************************************************
 * Copyright (c) 2012-2025 The University of York, Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio García-Domínguez - add type parameter, change to LinkedHashSet
 *     Sina Madani - extends {@link EolCollection}
 ******************************************************************************/
package org.eclipse.epsilon.eol.types;

import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public class EolSet<T> extends EolCollection<T> implements Set<T> {

	public EolSet() {
		super(new LinkedHashSet<>());
	}
}
