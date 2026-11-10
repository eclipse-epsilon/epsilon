/**
 */
package org.eclipse.epsilon.coverage.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The type of a coverage element, i.e. the class of the AST element it was recorded from.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.coverage.model.CoverageElementType#getImpl <em>Impl</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageElementType()
 * @model
 * @generated
 */
public interface CoverageElementType extends EObject {
	/**
	 * Returns the value of the '<em><b>Impl</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Fully qualified name of the Java class of the AST element (e.g. org.eclipse.epsilon.eol.dom.StatementBlock).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Impl</em>' attribute.
	 * @see #setImpl(String)
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageElementType_Impl()
	 * @model
	 * @generated
	 */
	String getImpl();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.coverage.model.CoverageElementType#getImpl <em>Impl</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Impl</em>' attribute.
	 * @see #getImpl()
	 * @generated
	 */
	void setImpl(String value);

} // CoverageElementType
