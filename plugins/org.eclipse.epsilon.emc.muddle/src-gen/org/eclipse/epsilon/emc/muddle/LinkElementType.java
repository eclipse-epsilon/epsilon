/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitris Kolovos - initial API and implementation
 ******************************************************************************/
/**
 */
package org.eclipse.epsilon.emc.muddle;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Link Element Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.LinkElementType#getSourceFeature <em>Source Feature</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.LinkElementType#getTargetFeature <em>Target Feature</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.LinkElementType#getRoleInSourceFeature <em>Role In Source Feature</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.LinkElementType#getRoleInTargetFeature <em>Role In Target Feature</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getLinkElementType()
 * @model
 * @generated
 */
public interface LinkElementType extends MuddleElementType {
	/**
	 * Returns the value of the '<em><b>Source Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Source Feature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Feature</em>' reference.
	 * @see #setSourceFeature(Feature)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getLinkElementType_SourceFeature()
	 * @model
	 * @generated
	 */
	Feature getSourceFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.LinkElementType#getSourceFeature <em>Source Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Feature</em>' reference.
	 * @see #getSourceFeature()
	 * @generated
	 */
	void setSourceFeature(Feature value);

	/**
	 * Returns the value of the '<em><b>Target Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Feature</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Feature</em>' reference.
	 * @see #setTargetFeature(Feature)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getLinkElementType_TargetFeature()
	 * @model
	 * @generated
	 */
	Feature getTargetFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.LinkElementType#getTargetFeature <em>Target Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Feature</em>' reference.
	 * @see #getTargetFeature()
	 * @generated
	 */
	void setTargetFeature(Feature value);

	/**
	 * Returns the value of the '<em><b>Role In Source Feature</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Role In Source Feature</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Role In Source Feature</em>' containment reference.
	 * @see #setRoleInSourceFeature(Feature)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getLinkElementType_RoleInSourceFeature()
	 * @model containment="true"
	 * @generated
	 */
	Feature getRoleInSourceFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.LinkElementType#getRoleInSourceFeature <em>Role In Source Feature</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Role In Source Feature</em>' containment reference.
	 * @see #getRoleInSourceFeature()
	 * @generated
	 */
	void setRoleInSourceFeature(Feature value);

	/**
	 * Returns the value of the '<em><b>Role In Target Feature</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Role In Target Feature</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Role In Target Feature</em>' containment reference.
	 * @see #setRoleInTargetFeature(Feature)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getLinkElementType_RoleInTargetFeature()
	 * @model containment="true"
	 * @generated
	 */
	Feature getRoleInTargetFeature();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.LinkElementType#getRoleInTargetFeature <em>Role In Target Feature</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Role In Target Feature</em>' containment reference.
	 * @see #getRoleInTargetFeature()
	 * @generated
	 */
	void setRoleInTargetFeature(Feature value);

} // LinkElementType
