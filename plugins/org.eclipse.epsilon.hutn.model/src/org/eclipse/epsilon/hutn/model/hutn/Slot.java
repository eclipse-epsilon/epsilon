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
 * $Id: Slot.java,v 1.3 2008/08/15 10:05:57 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutn;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Slot</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.hutn.model.hutn.Slot#getFeature <em>Feature</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.model.hutn.Slot#getOwner <em>Owner</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.model.hutn.Slot#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epsilon.hutn.model.hutn.HutnPackage#getSlot()
 * @model abstract="true"
 * @generated
 */
public interface Slot<T> extends ModelElement {
	/**
	 * Returns the value of the '<em><b>Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature</em>' attribute.
	 * @see #setFeature(String)
	 * @see org.eclipse.epsilon.hutn.model.hutn.HutnPackage#getSlot_Feature()
	 * @model
	 * @generated
	 */
	String getFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.hutn.model.hutn.Slot#getFeature <em>Feature</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Feature</em>' attribute.
	 * @see #getFeature()
	 * @generated
	 */
	void setFeature(String value);

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.hutn.model.hutn.ClassObject#getSlots <em>Slots</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' container reference.
	 * @see #setOwner(ClassObject)
	 * @see org.eclipse.epsilon.hutn.model.hutn.HutnPackage#getSlot_Owner()
	 * @see org.eclipse.epsilon.hutn.model.hutn.ClassObject#getSlots
	 * @model opposite="slots" required="true" transient="false"
	 * @generated
	 */
	ClassObject getOwner();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.hutn.model.hutn.Slot#getOwner <em>Owner</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' container reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(ClassObject value);

	/**
	 * Returns the value of the '<em><b>Values</b></em>' attribute list.
	 * The list contents are of type {@link T}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' attribute list.
	 * @see org.eclipse.epsilon.hutn.model.hutn.HutnPackage#getSlot_Values()
	 * @model unique="false" changeable="false"
	 * @generated
	 */
	EList<T> getValues();

	/**
	 * <!-- begin-user-doc -->
	 * Indicates whether the contents of this Slot has the same
	 * type as the specified EStructuralFeature.
	 * <!-- end-user-doc -->
	 * @model required="true" eStructuralFeatureRequired="true"
	 * @generated
	 */
	boolean typeCompatibleWith(EStructuralFeature eStructuralFeature);
	
	/**
	 * <!-- begin-user-doc -->
	 * Indicates whether the contents of this Slot can fit in the
	 * specified EStructuralFeature.
	 * <!-- end-user-doc -->
	 * @model required="true" eStructuralFeatureRequired="true"
	 * @generated
	 */
	boolean multiplicityCompatibleWith(EStructuralFeature eStructuralFeature);
	
	/**
	 * <!-- begin-user-doc -->
	 * Indicates whether this Slot's name, type of contents, and 
	 * size of contents is compatible with the specified
	 * EStructuralFeature.
	 * <!-- end-user-doc -->
	 * @model required="true" eStructuralFeatureRequired="true"
	 * @generated
	 */
	boolean compatibleWith(EStructuralFeature eStructuralFeature);
	
	/**
	 * <!-- begin-user-doc -->
	 * Returns the corresponding EStructuralFeature in the containing 
	 * PackageObject's metamodel.
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EStructuralFeature getEStructuralFeature();
	
	
	/**
	 * <!-- begin-user-doc -->
	 * Returns true only if this Slot has a corresponding EStructuralFeature
	 * in the containing PackageObject's metamodel.
	 * <!-- end-user-doc -->
	 * @model required="true"
	 * @generated
	 */
	boolean hasEStructuralFeature();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model valuesMany="true"
	 * @generated
	 */
	void setValues(EList<T> values);

} // Slot
