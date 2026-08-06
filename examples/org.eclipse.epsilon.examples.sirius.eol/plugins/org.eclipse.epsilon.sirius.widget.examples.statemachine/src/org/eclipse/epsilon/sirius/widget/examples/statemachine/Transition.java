/**
 * * Copyright (c) 2021 The University of York.
 * *
 * * This program and the accompanying materials are made
 * * available under the terms of the Eclipse Public License 2.0
 * * which is available at https://www.eclipse.org/legal/epl-2.0/
 * *
 * * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.epsilon.sirius.widget.examples.statemachine;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.sirius.widget.examples.statemachine.Transition#getTo <em>To</em>}</li>
 *   <li>{@link org.eclipse.epsilon.sirius.widget.examples.statemachine.Transition#getFrom <em>From</em>}</li>
 *   <li>{@link org.eclipse.epsilon.sirius.widget.examples.statemachine.Transition#getGuard <em>Guard</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epsilon.sirius.widget.examples.statemachine.StatemachinePackage#getTransition()
 * @model
 * @generated
 */
public interface Transition extends NamedElement {
	/**
	 * Returns the value of the '<em><b>To</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.sirius.widget.examples.statemachine.State#getIncoming <em>Incoming</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(State)
	 * @see org.eclipse.epsilon.sirius.widget.examples.statemachine.StatemachinePackage#getTransition_To()
	 * @see org.eclipse.epsilon.sirius.widget.examples.statemachine.State#getIncoming
	 * @model opposite="incoming"
	 * @generated
	 */
	State getTo();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.sirius.widget.examples.statemachine.Transition#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(State value);

	/**
	 * Returns the value of the '<em><b>From</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.sirius.widget.examples.statemachine.State#getOutgoing <em>Outgoing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(State)
	 * @see org.eclipse.epsilon.sirius.widget.examples.statemachine.StatemachinePackage#getTransition_From()
	 * @see org.eclipse.epsilon.sirius.widget.examples.statemachine.State#getOutgoing
	 * @model opposite="outgoing"
	 * @generated
	 */
	State getFrom();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.sirius.widget.examples.statemachine.Transition#getFrom <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(State value);

	/**
	 * Returns the value of the '<em><b>Guard</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Guard</em>' attribute.
	 * @see #setGuard(String)
	 * @see org.eclipse.epsilon.sirius.widget.examples.statemachine.StatemachinePackage#getTransition_Guard()
	 * @model
	 * @generated
	 */
	String getGuard();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.sirius.widget.examples.statemachine.Transition#getGuard <em>Guard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Guard</em>' attribute.
	 * @see #getGuard()
	 * @generated
	 */
	void setGuard(String value);

} // Transition
