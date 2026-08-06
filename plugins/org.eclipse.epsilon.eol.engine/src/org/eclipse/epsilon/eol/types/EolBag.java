/*******************************************************************************
 * Copyright (c) 2012-2020 The University of York, Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio García-Domínguez - add type parameter
 *     Sina Madani - extends {@link EolCollection}
 ******************************************************************************/
package org.eclipse.epsilon.eol.types;

public class EolBag<T> extends EolCollection<T> {

	public EolBag() {
		super(new java.util.ArrayList<>());
	}
}
