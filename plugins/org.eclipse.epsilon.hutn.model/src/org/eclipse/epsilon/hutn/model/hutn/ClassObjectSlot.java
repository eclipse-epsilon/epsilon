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
package org.eclipse.epsilon.hutn.model.hutn;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Class Object Slot</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.epsilon.hutn.model.hutn.HutnPackage#getClassObjectSlot()
 * @model abstract="true"
 * @generated
 */
public interface ClassObjectSlot<T> extends Slot<T> {

	/**
	 * <!-- begin-user-doc -->
	 * Returns a list of the class objects in this slot.
	 * The list might not be modifiable; use {@link #setClassObjects}
	 * to change the contents of the slot.
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<ClassObject> getClassObjects();

	/**
	 * <!-- begin-user-doc -->
	 * Replaces the class objects in this slot.
	 * <!-- end-user-doc -->
	 * @model classObjectsMany="true"
	 * @generated
	 */
	void setClassObjects(EList<ClassObject> classObjects);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void addClassObject(ClassObject classObject);

} // ClassObjectSlot
