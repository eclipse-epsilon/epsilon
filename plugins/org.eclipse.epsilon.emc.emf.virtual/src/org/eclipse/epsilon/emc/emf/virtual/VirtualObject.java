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
 * $Id: VirtualObject.java,v 1.2 2008/07/30 11:13:02 dkolovos Exp $
 */
package org.eclipse.epsilon.emc.emf.virtual;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.emc.emf.virtual.VirtualObject#getModel <em>Model</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.emf.virtual.VirtualObject#getSlots <em>Slots</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epsilon.emc.emf.virtual.VirtualPackage#getVirtualObject()
 * @model
 * @generated
 */
public interface VirtualObject extends TypedElement {
	/**
	 * Returns the value of the '<em><b>Model</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.emc.emf.virtual.VirtualModel#getObjects <em>Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model</em>' container reference.
	 * @see #setModel(VirtualModel)
	 * @see org.eclipse.epsilon.emc.emf.virtual.VirtualPackage#getVirtualObject_Model()
	 * @see org.eclipse.epsilon.emc.emf.virtual.VirtualModel#getObjects
	 * @model opposite="objects" required="true" transient="false"
	 * @generated
	 */
	VirtualModel getModel();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.emf.virtual.VirtualObject#getModel <em>Model</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model</em>' container reference.
	 * @see #getModel()
	 * @generated
	 */
	void setModel(VirtualModel value);

	/**
	 * Returns the value of the '<em><b>Slots</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.emc.emf.virtual.Slot}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.emc.emf.virtual.Slot#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Slots</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Slots</em>' containment reference list.
	 * @see org.eclipse.epsilon.emc.emf.virtual.VirtualPackage#getVirtualObject_Slots()
	 * @see org.eclipse.epsilon.emc.emf.virtual.Slot#getOwner
	 * @model opposite="owner" containment="true"
	 * @generated
	 */
	EList<Slot> getSlots();

} // VirtualObject
