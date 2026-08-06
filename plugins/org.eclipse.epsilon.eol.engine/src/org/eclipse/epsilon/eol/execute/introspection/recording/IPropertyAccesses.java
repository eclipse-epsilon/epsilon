/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.introspection.recording;

import java.util.Collection;
import java.util.Set;

public interface IPropertyAccesses {

	public Collection<? extends IPropertyAccess> all();

	public Set<? extends IPropertyAccess> unique();

	public boolean isEmpty();

}
