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
 * $Id: ReferenceSlot.java,v 1.2 2008/07/30 11:13:02 dkolovos Exp $
 */
package org.eclipse.epsilon.emc.emf.virtual;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Reference Slot</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.emc.emf.virtual.ReferenceSlot#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epsilon.emc.emf.virtual.VirtualPackage#getReferenceSlot()
 * @model
 * @generated
 */
public interface ReferenceSlot extends Slot {
	/**
	 * Returns the value of the '<em><b>Values</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.emc.emf.virtual.VirtualObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' reference list.
	 * @see org.eclipse.epsilon.emc.emf.virtual.VirtualPackage#getReferenceSlot_Values()
	 * @model
	 * @generated
	 */
	EList<VirtualObject> getValues();

} // ReferenceSlot
