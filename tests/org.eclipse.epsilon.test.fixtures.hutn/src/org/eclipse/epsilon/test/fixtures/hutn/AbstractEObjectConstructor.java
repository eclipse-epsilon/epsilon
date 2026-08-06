/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id:$
 */
package org.eclipse.epsilon.test.fixtures.hutn;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.epsilon.emc.emf.AbstractEmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;

public abstract class AbstractEObjectConstructor<T extends EObject> extends AbstractEmfModelConstructor {
	
	public T construct(String hutn) {
		final AbstractEmfModel model = constructModel(hutn);
		
		try {
			for (Object o : model.getAllOfKind(getRootElementType().getSimpleName())) {
				if (getRootElementType().isInstance(o)) {
					return getRootElementType().cast(o);
				}
			}
		} catch (EolModelElementTypeNotFoundException e) {
			e.printStackTrace();
		}

		return null;	
	}
	
	protected abstract Class<T> getRootElementType();
}
