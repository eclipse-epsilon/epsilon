/*********************************************************************
 * Copyright (c) 2008-2025 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/
package org.eclipse.epsilon.examples.standalone.evl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelNotFoundException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.dom.ConstraintContext;

/**
 * Demonstrates subclassing EvlModule to only check
 * constraints against a selection of model elements
 */

public class SelectiveEvlModule extends EvlModule {
	
	protected List<EObject> rootEObjects;
	
	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		ModuleElement moduleElement = super.adapt(cst, parentAst);
		if (moduleElement instanceof ConstraintContext) {
			return new SelectiveConstraintContext();
		}
		return moduleElement;
	}
	
	class SelectiveConstraintContext extends ConstraintContext {
		@Override
		public Collection<?> getAllOfSourceKind(IEolContext context)
				throws EolModelElementTypeNotFoundException, EolModelNotFoundException {
			// Only return a subset that matches the condition in shouldCheck()
			return super.getAllOfSourceKind(context).stream().
					filter(e -> shouldCheck(e)).collect(Collectors.toList());
		}
	}
	
	@Override
	public HashMap<String, Class<? extends IModule>> getImportConfiguration() {
		// Override getImportConfiguration so that the same
		// module implementation is also used for imported EVL files
		HashMap<String, Class<? extends IModule>> importConfiguration = super.getImportConfiguration();
		importConfiguration.put("evl", SelectiveEvlModule.class);
		return importConfiguration;
	}
	
	protected boolean shouldCheck(Object object) {
		// Only check elements which are descendants of one of the
		// root objects
		return EcoreUtil.isAncestor(rootEObjects, (EObject) object);
	}
	
	public void setRootEObjects(List<EObject> rootEObjects) {
		this.rootEObjects = rootEObjects;
	}
	
	public List<EObject> getRootEObjects() {
		return rootEObjects;
	}
	
	public static void main(String[] args) throws Exception {
		
		SelectiveEvlModule module = new SelectiveEvlModule();
		
		ResourceSetImpl resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		Resource resource = resourceSet.createResource(URI.createURI("foo.ecore"));
		resourceSet.getPackageRegistry().put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		
		// Create 2 classes C1 and C2 with one attribute each
		EClass c1 = EcoreFactory.eINSTANCE.createEClass();
		c1.setName("C1");
		EAttribute a1 = EcoreFactory.eINSTANCE.createEAttribute();
		a1.setName("a1");
		c1.getEStructuralFeatures().add(a1);
		
		EClass c2 = EcoreFactory.eINSTANCE.createEClass();
		c2.setName("C2");
		EAttribute a2 = EcoreFactory.eINSTANCE.createEAttribute();
		a2.setName("a2");
		c2.getEStructuralFeatures().add(a2);
		
		resource.getContents().add(c1);
		resource.getContents().add(c2);
		
		InMemoryEmfModel model = new InMemoryEmfModel(resource);
		
		// Constraint for EAttibute
		module.parse("context EAttribute { constraint Test { check {('Checking ' + self.name).println(); return true;} }}");
		module.getContext().getModelRepository().addModel(model);
		
		// Only elements under C1 (i.e. attribute a1) should be checked
		module.setRootEObjects(Arrays.asList(c1));
		
		module.execute();
	}
	
}
