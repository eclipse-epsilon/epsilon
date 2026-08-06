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
 * $Id: ClassObject.java,v 1.3 2008/08/15 10:05:57 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutn;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Class Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.hutn.model.hutn.ClassObject#getSlots <em>Slots</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epsilon.hutn.model.hutn.HutnPackage#getClassObject()
 * @model
 * @generated
 */
public interface ClassObject extends org.eclipse.epsilon.hutn.model.hutn.Object {
	
	/**
	 * Returns the value of the '<em><b>Slots</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.hutn.model.hutn.Slot}&lt;?>.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.hutn.model.hutn.Slot#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Slots</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Slots</em>' containment reference list.
	 * @see org.eclipse.epsilon.hutn.model.hutn.HutnPackage#getClassObject_Slots()
	 * @see org.eclipse.epsilon.hutn.model.hutn.Slot#getOwner
	 * @model opposite="owner" containment="true"
	 * @generated
	 */
	EList<Slot<?>> getSlots();


	/**
	 * <!-- begin-user-doc -->
	 * Finds a Slot contained in this ClassObject with the 
	 * specified feature. If this ClassObject contains no 
	 * such Slot, this method returns null. If this ClassObject
	 * contains more than one matching Slot, this method returns 
	 * any one of the matching Slots.
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Slot<?> findSlot(String feature);
	
	/**
	 * <!-- begin-user-doc -->
	 * Finds an AttributeSlot contained in this ClassObject with
	 * the specified feature. If this ClassObject contains no such
	 * AttributeSlot, this method returns a new AttributeSlot
	 * with the feature specified. If this ClassObject contains
	 * more than one matching AttributeSlot, this method returns 
	 * any one of the matching AttributeSlots.
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	AttributeSlot findOrCreateAttributeSlot(String feature);


	/**
	 * <!-- begin-user-doc -->
	 * Finds a ReferenceSlot contained in this ClassObject with
	 * the specified feature. If this ClassObject contains no such
	 * ReferenceSlot, this method returns a new ReferenceSlot
	 * with the feature specified. If this ClassObject contains
	 * more than one matching ReferenceSlot, this method returns 
	 * any one of the matching ReferenceSlots.
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	ReferenceSlot findOrCreateReferenceSlot(String feature);


	/**
	 * <!-- begin-user-doc -->
	 * Finds a ContainmentSlot contained in this ClassObject with
	 * the specified feature. If this ClassObject contains no such
	 * ContainmentSlot, this method returns a new ContainmentSlot
	 * with the feature specified. If this ClassObject contains
	 * more than one matching ContainmentSlot, this method returns 
	 * any one of the matching ContainmentSlots.
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	ContainmentSlot findOrCreateContainmentSlot(String feature);


	/**
	 * <!-- begin-user-doc -->
	 * Returns the PackageObject that contains this ClassObject.
	 * <!-- end-user-doc -->
	 * @model kind="operation" required="true"
	 * @generated
	 */
	PackageObject getPackageObject();


	/**
	 * <!-- begin-user-doc -->
	 * Returns the corresponding EClass in the containing 
	 * PackageObject's metamodel.
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EClass getEClass();
	
	
	/**
	 * <!-- begin-user-doc -->
	 * Returns true only if this Slot has a corresponding EClass
	 * in the containing PackageObject's metamodel.
	 * <!-- end-user-doc -->
	 * @model required="true"
	 * @generated
	 */
	boolean hasEClass();
	
	
	/**
	 * <!-- begin-user-doc -->
	 * Indicates whether every Slot contained in this ClassObject is
	 * type compatible with some EStructuralFeature contained in eClass
	 * <!-- end-user-doc -->
	 * @model required="true" eClassRequired="true"
	 * @generated
	 */
	boolean typeCompatibleWith(EClass eClass);
	
} // ClassObject
