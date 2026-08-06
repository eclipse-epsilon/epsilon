/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.emf;

import java.util.Collection;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.types.EolCollectionType;
import org.eclipse.epsilon.eol.types.IEolCollectionTypeResolver;

public class EmfCollectionTypeResolver implements IEolCollectionTypeResolver {
	
	public static void main(String[] args) throws Exception {
		
		EolModule m = new EolModule();
		m.parse("EClass.all.first().eAttributes.isKindOf(Set).println();");
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		Resource r = rs.createResource(URI.createFileURI(""));
		EObject root = EcorePackage.eINSTANCE;
		r.getContents().add(root);
		
		InMemoryEmfModel model = new InMemoryEmfModel("M", r, EcorePackage.eINSTANCE);
		m.getContext().getModelRepository().addModel(model);
		m.execute();
		
	}
	
	@Override
	public boolean canResolveType(Collection<?> c) {
		return c instanceof Setting;
	}

	@Override
	public EolCollectionType resolveType(Collection<?> c) {
		EStructuralFeature sf = ((Setting) c).getEStructuralFeature();
		if (sf.isOrdered()) {
			if (sf.isUnique()) return EolCollectionType.OrderedSet;
			else return EolCollectionType.Sequence;
		}
		else {
			if (sf.isUnique()) return EolCollectionType.Set;
			else return EolCollectionType.Bag;
		}
 	}
	
}
