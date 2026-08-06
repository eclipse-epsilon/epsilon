/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.types;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.models.ModelRepository;

public class ObjectUtil {
	
	public static Collection<?> asCollection(Object o) {
		if (o instanceof Collection) {
			return (Collection<?>) o;
		}
		else {
			ArrayList<Object> collection = new ArrayList<>(1);
			collection.add(o);
			return collection;
		}
	}
	
	public static boolean modelElementsEqual(ModelRepository repo, Object e1, Object e2) {
		IModel
			e1Model = repo.getOwningModel(e1),
			e2Model = repo.getOwningModel(e2);
	
		if (!(e1Model == null && e2Model == null)) {
			if (e1Model == null || e2Model == null)
				return false;
			
			return
					Objects.equals(e1Model.getElementId(e1), e2Model.getElementId(e2)) &&
					Objects.equals(e1Model.getElementId(e2), e2Model.getElementId(e1));
		}
		
		return false;
	}
	
}
