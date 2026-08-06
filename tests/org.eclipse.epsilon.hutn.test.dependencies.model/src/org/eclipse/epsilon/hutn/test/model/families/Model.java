/**
 * *******************************************************************************
 *  Copyright (c) 2008 The University of York.
 *  This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which is available at https://www.eclipse.org/legal/epl-2.0/
 *  Contributors:
 *    Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.test.model.families;

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
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Model#getContents <em>Contents</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.test.model.families.Model#getContents2 <em>Contents2</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getModel()
 * @model
 * @generated
 */
public interface Model extends EObject {
	/**
	 * Returns the value of the '<em><b>Contents</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.hutn.test.model.families.NamedElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contents</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contents</em>' containment reference list.
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getModel_Contents()
	 * @model containment="true"
	 * @generated
	 */
	EList<NamedElement> getContents();

	/**
	 * Returns the value of the '<em><b>Contents2</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.hutn.test.model.families.NamedElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contents2</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contents2</em>' containment reference list.
	 * @see org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage#getModel_Contents2()
	 * @model containment="true"
	 * @generated
	 */
	EList<NamedElement> getContents2();

} // Model
