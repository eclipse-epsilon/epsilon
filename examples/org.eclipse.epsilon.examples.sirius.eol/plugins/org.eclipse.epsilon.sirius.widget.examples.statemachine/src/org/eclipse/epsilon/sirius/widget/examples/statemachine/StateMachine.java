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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State Machine</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.sirius.widget.examples.statemachine.StateMachine#getInitialState <em>Initial State</em>}</li>
 *   <li>{@link org.eclipse.epsilon.sirius.widget.examples.statemachine.StateMachine#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link org.eclipse.epsilon.sirius.widget.examples.statemachine.StateMachine#getStates <em>States</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epsilon.sirius.widget.examples.statemachine.StatemachinePackage#getStateMachine()
 * @model
 * @generated
 */
public interface StateMachine extends EObject {
	/**
	 * Returns the value of the '<em><b>Initial State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Initial State</em>' containment reference.
	 * @see #setInitialState(InitialState)
	 * @see org.eclipse.epsilon.sirius.widget.examples.statemachine.StatemachinePackage#getStateMachine_InitialState()
	 * @model containment="true"
	 * @generated
	 */
	InitialState getInitialState();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.sirius.widget.examples.statemachine.StateMachine#getInitialState <em>Initial State</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Initial State</em>' containment reference.
	 * @see #getInitialState()
	 * @generated
	 */
	void setInitialState(InitialState value);

	/**
	 * Returns the value of the '<em><b>Transitions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.sirius.widget.examples.statemachine.Transition}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transitions</em>' containment reference list.
	 * @see org.eclipse.epsilon.sirius.widget.examples.statemachine.StatemachinePackage#getStateMachine_Transitions()
	 * @model containment="true"
	 * @generated
	 */
	EList<Transition> getTransitions();

	/**
	 * Returns the value of the '<em><b>States</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.sirius.widget.examples.statemachine.State}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>States</em>' containment reference list.
	 * @see org.eclipse.epsilon.sirius.widget.examples.statemachine.StatemachinePackage#getStateMachine_States()
	 * @model containment="true"
	 * @generated
	 */
	EList<State> getStates();

} // StateMachine
