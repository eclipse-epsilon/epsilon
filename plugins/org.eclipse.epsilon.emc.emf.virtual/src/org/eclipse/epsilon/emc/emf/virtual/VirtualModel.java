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
 * $Id: VirtualModel.java,v 1.2 2008/07/30 11:13:02 dkolovos Exp $
 */
package org.eclipse.epsilon.emc.emf.virtual;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.emc.emf.virtual.VirtualModel#getObjects <em>Objects</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epsilon.emc.emf.virtual.VirtualPackage#getVirtualModel()
 * @model
 * @generated
 */
public interface VirtualModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Objects</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.emc.emf.virtual.VirtualObject}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.emc.emf.virtual.VirtualObject#getModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Objects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Objects</em>' containment reference list.
	 * @see org.eclipse.epsilon.emc.emf.virtual.VirtualPackage#getVirtualModel_Objects()
	 * @see org.eclipse.epsilon.emc.emf.virtual.VirtualObject#getModel
	 * @model opposite="model" containment="true"
	 * @generated
	 */
	EList<VirtualObject> getObjects();

} // VirtualModel
