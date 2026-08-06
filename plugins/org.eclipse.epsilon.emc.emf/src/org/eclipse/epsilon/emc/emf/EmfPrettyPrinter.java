/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf;

import java.util.Iterator;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.eol.execute.prettyprinting.PrettyPrinter;

public class EmfPrettyPrinter implements PrettyPrinter{

	@Override
	public boolean appliesTo(Object o) {
		return o instanceof EObject;
	}

	@Override
	public String print(Object o) {
		
		if (o == null) return "";
		
		EObject eObject = (EObject) o;
		
		String str = "";
		
		str = eObject.eClass().getName();
		
		str += " [";
		
		Iterator<EStructuralFeature> it = eObject.eClass().getEAllStructuralFeatures().iterator();
		
		while (it.hasNext()){
			EStructuralFeature sf = it.next();
			
			if (sf.getEType() instanceof EDataType){
				str += sf.getName() + "=" + eObject.eGet(sf);
				if (it.hasNext()){
					str += ", ";
				}
			}
			else {
				//str += sf.getName() + "=<object>";
				//if (it.hasNext()){
				//	str += ", ";
				//}			
			}
		}
		
		str += "]";
		
		return str;
	}

}
