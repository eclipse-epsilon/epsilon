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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.sirius.widget.examples.statemachine.State#getAction <em>Action</em>}</li>
 *   <li>{@link org.eclipse.epsilon.sirius.widget.examples.statemachine.State#getOutgoing <em>Outgoing</em>}</li>
 *   <li>{@link org.eclipse.epsilon.sirius.widget.examples.statemachine.State#getIncoming <em>Incoming</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epsilon.sirius.widget.examples.statemachine.StatemachinePackage#getState()
 * @model
 * @generated
 */
public interface State extends NamedElement {
	/**
	 * Returns the value of the '<em><b>Action</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action</em>' attribute.
	 * @see #setAction(String)
	 * @see org.eclipse.epsilon.sirius.widget.examples.statemachine.StatemachinePackage#getState_Action()
	 * @model
	 * @generated
	 */
	String getAction();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.sirius.widget.examples.statemachine.State#getAction <em>Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action</em>' attribute.
	 * @see #getAction()
	 * @generated
	 */
	void setAction(String value);

	/**
	 * Returns the value of the '<em><b>Outgoing</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.sirius.widget.examples.statemachine.Transition}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.sirius.widget.examples.statemachine.Transition#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outgoing</em>' reference list.
	 * @see org.eclipse.epsilon.sirius.widget.examples.statemachine.StatemachinePackage#getState_Outgoing()
	 * @see org.eclipse.epsilon.sirius.widget.examples.statemachine.Transition#getFrom
	 * @model opposite="from"
	 * @generated
	 */
	EList<Transition> getOutgoing();

	/**
	 * Returns the value of the '<em><b>Incoming</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.sirius.widget.examples.statemachine.Transition}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.sirius.widget.examples.statemachine.Transition#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Incoming</em>' reference list.
	 * @see org.eclipse.epsilon.sirius.widget.examples.statemachine.StatemachinePackage#getState_Incoming()
	 * @see org.eclipse.epsilon.sirius.widget.examples.statemachine.Transition#getTo
	 * @model opposite="to"
	 * @generated
	 */
	EList<Transition> getIncoming();

} // State
