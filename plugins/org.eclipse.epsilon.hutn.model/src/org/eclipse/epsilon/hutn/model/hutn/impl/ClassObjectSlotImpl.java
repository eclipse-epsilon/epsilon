/**
 * *******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.model.hutn.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.hutn.model.hutn.ClassObject;
import org.eclipse.epsilon.hutn.model.hutn.ClassObjectSlot;
import org.eclipse.epsilon.hutn.model.hutn.HutnPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Class Object Slot</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public abstract class ClassObjectSlotImpl<T> extends SlotImpl<T> implements ClassObjectSlot<T> {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClassObjectSlotImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HutnPackage.Literals.CLASS_OBJECT_SLOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public abstract EList<ClassObject> getClassObjects();


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public abstract void setClassObjects(EList<ClassObject> classObjects);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public abstract void addClassObject(ClassObject classObject);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public boolean typeCompatibleWith(EStructuralFeature feature) {	
		if (feature.getEType() instanceof EClass) {
			final EClass typeOfFeature = (EClass)feature.getEType();
			
			for (ClassObject classObject : getClassObjects()) {
				if (classObject != null) {
				
					final EClass typeOfValue = classObject.getEClass();
					
					if (typeOfValue == null || !isSuperType(typeOfFeature, typeOfValue)) {
						return false;
					}
				}
			}
			
			return true;	
		}
		
		return false;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	private static boolean isSuperType(EClass type, EClass candidate) {
		/* type.isSuperTypeOf() doesn't seem to work - it relies
		 * on == rather than .equals
		 */
		
		if (type == null || candidate == null) return false;
		
		if (equals(type, candidate)) return true;
		
		for (EClass supertypeOfCandidate : candidate.getEAllSuperTypes()) {
			if (equals(type, supertypeOfCandidate)) return true;
		}
		
		return false;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	private static boolean equals(EClass c, EClass other) {		
		final boolean namesEqual = (c.getName() == null     ?
		                            other.getName() == null :
		                            c.getName().equals(other.getName())
		                           );
		
		if (c.getEPackage() == null || other.getEPackage() == null) {
			return namesEqual;
		
		} else {
			final boolean nsUriEqual = (c.getEPackage().getNsURI() == null     ?
			                            other.getEPackage().getNsURI() == null :
	                                    c.getEPackage().getNsURI().equals(other.getEPackage().getNsURI())
	                                   );
			
			return namesEqual && nsUriEqual;
		}
	}

} //ClassObjectSlotImpl
