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
 * $Id: Slot.java,v 1.2 2008/07/30 11:13:02 dkolovos Exp $
 */
package org.eclipse.epsilon.emc.emf.virtual;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Slot</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.emc.emf.virtual.Slot#getOwner <em>Owner</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epsilon.emc.emf.virtual.VirtualPackage#getSlot()
 * @model abstract="true"
 * @generated
 */
public interface Slot extends TypedElement {
	/**
	 * Returns the value of the '<em><b>Owner</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.emc.emf.virtual.VirtualObject#getSlots <em>Slots</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' container reference.
	 * @see #setOwner(VirtualObject)
	 * @see org.eclipse.epsilon.emc.emf.virtual.VirtualPackage#getSlot_Owner()
	 * @see org.eclipse.epsilon.emc.emf.virtual.VirtualObject#getSlots
	 * @model opposite="slots" required="true" transient="false"
	 * @generated
	 */
	VirtualObject getOwner();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.emf.virtual.Slot#getOwner <em>Owner</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' container reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(VirtualObject value);

} // Slot
