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
package org.eclipse.epsilon.epl.dom;

import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.eol.execute.introspection.IUndefined;

public final class NoMatch implements IUndefined {
	private NoMatch() {}
	
	public static final NoMatch INSTANCE = new NoMatch();
	private static List<Object> list; 
	
	public static List<Object> asList() {
		if (list == null) {
			list = Collections.singletonList(INSTANCE);
		}
		return list;
	}
}
