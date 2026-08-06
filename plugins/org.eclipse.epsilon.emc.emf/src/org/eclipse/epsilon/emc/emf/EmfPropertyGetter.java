/*******************************************************************************
 * Copyright (c) 2008-2013 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.eol.exceptions.EolIllegalPropertyException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.AbstractPropertyGetter;
import org.eclipse.epsilon.eol.types.EolCollectionType;

public class EmfPropertyGetter extends AbstractPropertyGetter {
	
	static {
		EolCollectionType.getCollectionTypeResolvers().add(new EmfCollectionTypeResolver());
	}
	
	@Override
	public boolean hasProperty(Object object, String property, IEolContext context) {
		if (object instanceof EObject) {
			return EmfUtil.getEStructuralFeature(((EObject) object).eClass(), property) != null;
		}
		else return false;
	}
	
	@Override
	public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
		EObject eObject = (EObject) object;
		EStructuralFeature sf = EmfUtil.getEStructuralFeature(eObject.eClass(), property);
		if (sf != null) {
			return eObject.eGet(sf);
		}
		else {
			throw new EolIllegalPropertyException(object, property, context);
		}
	}
	
}
