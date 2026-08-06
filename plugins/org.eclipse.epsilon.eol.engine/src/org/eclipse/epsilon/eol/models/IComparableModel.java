/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.models;

import java.util.Collection;

public interface IComparableModel extends IModel {
	
	public Object getElementSignature(Object instance);
	
	public boolean isIdenticalTo(IComparableModel model);
	
	public Collection<Difference> getDifferences(IComparableModel model);
	
	public class Difference {
		
	}
	
	public enum DifferenceType {
		Added,
		Deleted,
		Modified
	}
	
}
