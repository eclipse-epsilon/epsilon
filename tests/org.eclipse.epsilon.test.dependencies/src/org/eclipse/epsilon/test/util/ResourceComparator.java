/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.test.util;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.emc.emf.EmfUtil;

public class ResourceComparator {
	
	protected HashMap<EObject, EObject> matches = new HashMap<>();
	protected HashMap<EObject, EObject> tempMatches = new HashMap<>();
	
	protected Resource createResource() {
		ResourceSet rs = new ResourceSetImpl();
		rs.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		Resource r = rs.createResource(URI.createFileURI("dummy"));
		EObject copy = EcoreUtil.copy(EcorePackage.eINSTANCE);
		r.getContents().add(copy);
		return r;
	}
	
	public static void compare(Resource r1, Resource r2) {		
		List<EObject> all1 = EmfUtil.getAllContents(r1);
		List<EObject> all2 = EmfUtil.getAllContents(r2);
		
		assertEquals(all1.size(), all2.size());
		
		int i = 0;
		for (EObject o1 : all1) {
			EObject o2 = all2.get(i++);
			
			assertEquals(o1.eClass(), o2.eClass());
			
			for (EAttribute attr : o1.eClass().getEAllAttributes()) {
				
				if (attr.isDerived())
					continue;
				
				Object v1 = o1.eGet(attr);
				Object v2 = o2.eGet(attr);
				
				if (v1 == null && v2 == null)
					continue;
				
				assertNotNull(v1);
				assertNotNull(v2);
				assertEquals(v1, v2);
			}
			for (EReference ref : o1.eClass().getEAllReferences()) {	
				if (ref.isDerived())
					continue;
				
				if (ref.isMany()) {
					EList<?> cv1 = (EList<?>) o1.eGet(ref);
					EList<?> cv2 = (EList<?>) o2.eGet(ref);

					assertEquals(
						String.format("The number of elements for many-valued reference %s of objects %s and %s should match",
								ref.getName(), EcoreUtil.getURI(o1), EcoreUtil.getURI(o2)),
						cv1.size(), cv2.size());
					
					int j = 0;
					for (Object v1 : cv1) {
						assertEquals(all1.indexOf(v1), all2.indexOf(cv2.get(j++)));
					}
				}
				else {
					Object v1 = o1.eGet(ref);
					Object v2 = o2.eGet(ref);
					
					if (v1 == null && v2 == null)
						continue;
					
					assertNotNull(v1);
					assertNotNull(v2);
					
					assertEquals(all1.indexOf(v1), all2.indexOf(v2));
				}
			}
		}
	}
	
}
