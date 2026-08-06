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
 * $Id: PackageObject.java,v 1.3 2008/08/15 10:05:57 dkolovos Exp $
 */
package org.eclipse.epsilon.hutn.model.hutn;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;



/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Package Object</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.epsilon.hutn.model.hutn.PackageObject#getMetamodel <em>Metamodel</em>}</li>
 *   <li>{@link org.eclipse.epsilon.hutn.model.hutn.PackageObject#getClassObjects <em>Class Objects</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.epsilon.hutn.model.hutn.HutnPackage#getPackageObject()
 * @model
 * @generated
 */
public interface PackageObject extends org.eclipse.epsilon.hutn.model.hutn.Object {

	/**
	 * Returns the value of the '<em><b>Metamodel</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EPackage}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metamodel</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metamodel</em>' reference list.
	 * @see org.eclipse.epsilon.hutn.model.hutn.HutnPackage#getPackageObject_Metamodel()
	 * @model
	 * @generated
	 */
	EList<EPackage> getMetamodel();

	/**
	 * Returns the value of the '<em><b>Class Objects</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.hutn.model.hutn.ClassObject}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Objects</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class Objects</em>' containment reference list.
	 * @see org.eclipse.epsilon.hutn.model.hutn.HutnPackage#getPackageObject_ClassObjects()
	 * @model containment="true"
	 * @generated
	 */
	EList<ClassObject> getClassObjects();

	/**
	 * <!-- begin-user-doc -->
	 * Returns all of the EClasses contained in this PackageObject's metamodel.
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<EClass> getAllEClasses();
} // PackageObject
