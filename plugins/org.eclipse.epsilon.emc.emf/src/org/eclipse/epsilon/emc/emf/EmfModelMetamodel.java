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

import java.util.HashMap;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.eol.m3.Attribute;
import org.eclipse.epsilon.eol.m3.MetaClass;
import org.eclipse.epsilon.eol.m3.Metamodel;
import org.eclipse.epsilon.eol.m3.Reference;
import org.eclipse.epsilon.eol.models.IRelativePathResolver;
import org.eclipse.epsilon.eol.types.EolAnyType;
import org.eclipse.epsilon.eol.types.EolModelElementType;
import org.eclipse.epsilon.eol.types.EolPrimitiveType;

public class EmfModelMetamodel extends Metamodel {
	
	public EmfModelMetamodel(StringProperties properties, IRelativePathResolver resolver) {
		
		HashMap<EClass, MetaClass> eClassMetaClassMap = new HashMap<>();
		String nsuri = properties.getProperty("nsuri");
		if (nsuri == null) {
			getErrors().add("Required property nsuri not found");
		}
		else {
			EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(nsuri);
			if (ePackage == null) {
				getErrors().add("EPackage with nsURI " + nsuri + " is not available in EPackage.Registry.INSTANCE");
			}
			else {
				for (EClassifier eClassifier : ePackage.getEClassifiers()) {
					if (eClassifier instanceof EClass) {
						MetaClass metaClass = new MetaClass();
						metaClass.setName(eClassifier.getName());
						eClassMetaClassMap.put((EClass) eClassifier, metaClass);
						metaTypes.add(metaClass);
					}
				}
				for (EClass eClass : eClassMetaClassMap.keySet()) {
					MetaClass metaClass = eClassMetaClassMap.get(eClass);
					for (EClass eSuperType : eClass.getESuperTypes()) {
						MetaClass superType = eClassMetaClassMap.get(eSuperType);
						if (superType != null) metaClass.getSuperTypes().add(superType);
					}
					
					for (EAttribute eAttribute : eClass.getEAttributes()) {
						Attribute attribute = new Attribute();
						attribute.setName(eAttribute.getName());
						attribute.setOrdered(eAttribute.isOrdered());
						attribute.setUnique(eAttribute.isUnique());
						attribute.setMany(eAttribute.isMany());
						
						String instanceClassName = eAttribute.getEAttributeType().getInstanceClassName();
						if (StringUtil.isOneOf(instanceClassName, String.class.getCanonicalName(), "String")) {
							attribute.setType(EolPrimitiveType.String);
						}
						else if (StringUtil.isOneOf(instanceClassName, Integer.class.getCanonicalName(), "int")) {
							attribute.setType(EolPrimitiveType.Integer);
						}
						else if (StringUtil.isOneOf(instanceClassName, Boolean.class.getCanonicalName(), "boolean")) {
							attribute.setType(EolPrimitiveType.Boolean);
						}
						else if ((instanceClassName != null) && (instanceClassName.equals(Float.class.getCanonicalName()) || instanceClassName.equals(Double.class.getCanonicalName()))) {
							attribute.setType(EolPrimitiveType.Real);
						}
						else
							attribute.setType(EolAnyType.Instance);
						metaClass.getStructuralFeatures().add(attribute);
					}
					
					for (EReference eReference : eClass.getEReferences()) {
						Reference reference = new Reference();
						reference.setName(eReference.getName());
						reference.setOrdered(eReference.isOrdered());
						reference.setUnique(eReference.isUnique());
						reference.setMany(eReference.isMany());
						reference.setContainment(eReference.isContainment());
						
						EClass referenceType = eReference.getEReferenceType();
						MetaClass referenceMetaClass = eClassMetaClassMap.get(referenceType);
						if (referenceMetaClass != null) {
							reference.setType(new EolModelElementType(referenceMetaClass));
						}
						metaClass.getStructuralFeatures().add(reference);
					}
				}
			}
		}
	}
	
}
