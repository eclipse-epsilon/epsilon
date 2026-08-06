/**
 */
package org.eclipse.epsilon.eunit.examples.emfvalidator;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.eunit.examples.emfvalidator.Model#getPositiveValue <em>Positive Value</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epsilon.eunit.examples.emfvalidator.EmfvalidatorPackage#getModel()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='PositiveValue'"
 * @generated
 */
public interface Model extends EObject {
	/**
	 * Returns the value of the '<em><b>Positive Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Positive Value</em>' attribute.
	 * @see #setPositiveValue(int)
	 * @see org.eclipse.epsilon.eunit.examples.emfvalidator.EmfvalidatorPackage#getModel_PositiveValue()
	 * @model
	 * @generated
	 */
	int getPositiveValue();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.eunit.examples.emfvalidator.Model#getPositiveValue <em>Positive Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Positive Value</em>' attribute.
	 * @see #getPositiveValue()
	 * @generated
	 */
	void setPositiveValue(int value);

} // Model
