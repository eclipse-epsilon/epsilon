/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.emf.decorator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfPropertyGetter;
import org.eclipse.epsilon.emc.emf.EmfPropertySetter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.introspection.IReflectivePropertySetter;

public class DecoratorEmfModel extends EmfModel { 
	
	EObject decorator = null;
	DecoratorDescriptor decoratorDescriptor = null;
	List<DecoratorDescriptor> decoratorDescriptors;
	
	public DecoratorEmfModel() {
		propertyGetter = new DecoratorPropertyGetter();
	}
	
	protected List<EClass> getDecoratorEClasses(Collection<EPackage> ePackages) {
		List<EClass> decoratorEClasses = new ArrayList<>();
		for (EPackage ePackage :ePackages) {
			for (EClassifier eClassifier : ePackage.getEClassifiers()) {
				if (eClassifier instanceof EClass) {
					EClass eClass = (EClass) eClassifier;
					if (isDecorator(eClass)) {
						decoratorEClasses.add(eClass);
					}
				}
			}
			decoratorEClasses.addAll(getDecoratorEClasses(ePackage.getESubpackages()));
		}
		return decoratorEClasses;
	}
	
	protected List<DecoratorDescriptor> getDecoratorDescriptors() {
		if (decoratorDescriptors == null) {
			decoratorDescriptors = new ArrayList<>();
			
			List<EClass> decoratorEClasses = getDecoratorEClasses(packages);
			
			for (EClass decoratorEClass : decoratorEClasses) {
				for (EReference hook : decoratorEClass.getEAllReferences()) {
					if (isHook(hook)) {
						DecoratorDescriptor decoratorDescriptor = new DecoratorDescriptor();
						decoratorDescriptor.setEClass(decoratorEClass);
						decoratorDescriptor.setEReference(hook);
						decoratorDescriptors.add(decoratorDescriptor);
					}
				}
			}

		}
		return decoratorDescriptors;
	}
	
	@Override
	public boolean knowsAboutProperty(Object instance, String property) {
		
		//If it is not an EObject don't look any further
		if (!(instance instanceof EObject)) return false;
		
		//If it is a model element of the decorator model return true
		if (super.owns(instance)) {
			decorator = (EObject) instance; 
			return true;
		}
		
		// Look for a suitable decorator descriptor
		this.decoratorDescriptor = null;
		for (DecoratorDescriptor decoratorDescriptor : getDecoratorDescriptors()) {
			if (decoratorDescriptor.decorates((EObject) instance, property)) {
				this.decoratorDescriptor = decoratorDescriptor;
				break;
			}
		}
		
		if (this.decoratorDescriptor == null) return false;
		
		// If we find the descriptor, 
		// also cache the 
		// actual decorator
		
		Collection<EObject> candidateDecorators = new ArrayList<>();
		try {
			candidateDecorators = getAllOfKind(getFullyQualifiedName(decoratorDescriptor.getEClass()));
		}
		catch (Exception ex) {
			
		}
		
		for (EObject candidateDecorator : candidateDecorators) {
			if (isDecoratorFor(candidateDecorator, instance, decoratorDescriptor.getEReference())) {
				decorator = candidateDecorator;
				return true;
			}
		}
		
		// No decorator found but if we have reached here
		// it means that we have found a decorator descriptor
		// at least
		decorator = null;
		return true;
		
	}
	
	protected boolean isDecorator(EClass e) {
		if (e.getEAnnotation("decorator") != null) {
			return true;
		}
		else {
			for (EClass supertype : e.getESuperTypes()) {
				if (isDecorator(supertype)) {
					return true;
				}
			}
		}
		return false;
	}
	
	protected boolean isHook(EReference e) {
		return e.getEAnnotation("decorator.hook") != null;
	}
	
	@SuppressWarnings("unchecked")
	protected boolean isDecoratorFor(EObject candidateDecorator, Object target, EReference eReference) {
			
		if (eReference.isMany()) {
			if (((Collection<Object>)candidateDecorator.eGet(eReference)).contains(target)) {
				return true;
			}
		}
		else {
			if (candidateDecorator.eGet(eReference) == target) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public IReflectivePropertySetter getPropertySetter() {
		return new DecoratorPropertySetter();
	}
	
	class DecoratorPropertyGetter extends EmfPropertyGetter {
		
		@Override
		public Object invoke(Object object, String property, IEolContext context) throws EolRuntimeException {
			if (decorator == null) {
				if (decoratorDescriptor.getLastValueFeature().isMany()) {
					return new ArrayList<>();
				}
				else {
					return decoratorDescriptor.getLastValueFeature().getDefaultValue();
				}
			}
			else {
				return super.invoke(decorator, property, context);
			}
		}
	}
	
	class DecoratorPropertySetter extends EmfPropertySetter {
		@Override
		public void invoke(Object object, String property, Object value, IEolContext context) throws EolRuntimeException {
			if (decorator != null) {
				super.invoke(decorator, property, value, context);
			}
			else {
				throw new EolRuntimeException("Cannot set the value of feature " + property + " as the decorator object does not exist");
			}
		}
	}
}
 
