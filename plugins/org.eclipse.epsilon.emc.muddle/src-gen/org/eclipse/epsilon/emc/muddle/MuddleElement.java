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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.MuddleElement#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.MuddleElement#getSlots <em>Slots</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.MuddleElement#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.MuddleElement#getMuddle <em>Muddle</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.MuddleElement#getStyle <em>Style</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getMuddleElement()
 * @model
 * @generated
 */
public interface MuddleElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getMuddleElement_Id()
	 * @model
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.MuddleElement#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Slots</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.emc.muddle.Slot}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.emc.muddle.Slot#getOwningElement <em>Owning Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Slots</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Slots</em>' containment reference list.
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getMuddleElement_Slots()
	 * @see org.eclipse.epsilon.emc.muddle.Slot#getOwningElement
	 * @model opposite="owningElement" containment="true"
	 * @generated
	 */
	EList<Slot> getSlots();

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.emc.muddle.MuddleElementType#getInstances <em>Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(MuddleElementType)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getMuddleElement_Type()
	 * @see org.eclipse.epsilon.emc.muddle.MuddleElementType#getInstances
	 * @model opposite="instances"
	 * @generated
	 */
	MuddleElementType getType();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.MuddleElement#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(MuddleElementType value);

	/**
	 * Returns the value of the '<em><b>Muddle</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.emc.muddle.Muddle#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Muddle</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Muddle</em>' container reference.
	 * @see #setMuddle(Muddle)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getMuddleElement_Muddle()
	 * @see org.eclipse.epsilon.emc.muddle.Muddle#getElements
	 * @model opposite="elements" transient="false"
	 * @generated
	 */
	Muddle getMuddle();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.MuddleElement#getMuddle <em>Muddle</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Muddle</em>' container reference.
	 * @see #getMuddle()
	 * @generated
	 */
	void setMuddle(Muddle value);

	/**
	 * Returns the value of the '<em><b>Style</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Style</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Style</em>' containment reference.
	 * @see #setStyle(MuddleElementStyle)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getMuddleElement_Style()
	 * @model containment="true"
	 * @generated
	 */
	MuddleElementStyle getStyle();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.MuddleElement#getStyle <em>Style</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Style</em>' containment reference.
	 * @see #getStyle()
	 * @generated
	 */
	void setStyle(MuddleElementStyle value);

} // MuddleElement
