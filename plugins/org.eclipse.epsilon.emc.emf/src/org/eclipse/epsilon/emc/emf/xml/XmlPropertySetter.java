/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf.xml;

import javax.xml.namespace.QName;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.emc.emf.EmfPropertySetter;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class XmlPropertySetter extends EmfPropertySetter {

	@Override
	public void invoke(Object object, String property, Object value, IEolContext context) throws EolRuntimeException {
		EObject eObject = (EObject) object;
		EStructuralFeature sf = EmfUtil.getEStructuralFeature(eObject.eClass(), property);
		if ("QName".equals(sf.getEType().getName())) {
			QName qName = QName.valueOf((String)(value));
			eObject.eSet(sf, qName);
		}
		else {
			super.invoke(object, property, value, context);
		}
	}
}
