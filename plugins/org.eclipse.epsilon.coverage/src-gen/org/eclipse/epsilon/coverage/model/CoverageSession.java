/**
 */
package org.eclipse.epsilon.coverage.model;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Session</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * The coverage information recorded during the execution of Epsilon programs.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.coverage.model.CoverageSession#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.CoverageSession#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.CoverageSession#getLaunchConfiguration <em>Launch Configuration</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.CoverageSession#getFiles <em>Files</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.CoverageSession#getTypes <em>Types</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageSession()
 * @model
 * @generated
 */
public interface CoverageSession extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageSession_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.coverage.model.CoverageSession#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timestamp</em>' attribute.
	 * @see #setTimestamp(Date)
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageSession_Timestamp()
	 * @model
	 * @generated
	 */
	Date getTimestamp();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.coverage.model.CoverageSession#getTimestamp <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timestamp</em>' attribute.
	 * @see #getTimestamp()
	 * @generated
	 */
	void setTimestamp(Date value);

	/**
	 * Returns the value of the '<em><b>Launch Configuration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Launch Configuration</em>' attribute.
	 * @see #setLaunchConfiguration(String)
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageSession_LaunchConfiguration()
	 * @model
	 * @generated
	 */
	String getLaunchConfiguration();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.coverage.model.CoverageSession#getLaunchConfiguration <em>Launch Configuration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Launch Configuration</em>' attribute.
	 * @see #getLaunchConfiguration()
	 * @generated
	 */
	void setLaunchConfiguration(String value);

	/**
	 * Returns the value of the '<em><b>Files</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.coverage.model.SourceFile}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.coverage.model.SourceFile#getSession <em>Session</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Files</em>' containment reference list.
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageSession_Files()
	 * @see org.eclipse.epsilon.coverage.model.SourceFile#getSession
	 * @model opposite="session" containment="true"
	 * @generated
	 */
	EList<SourceFile> getFiles();

	/**
	 * Returns the value of the '<em><b>Types</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.coverage.model.CoverageElementType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The types of the elements of the session (shared between elements of the same type).
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Types</em>' containment reference list.
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageSession_Types()
	 * @model containment="true"
	 * @generated
	 */
	EList<CoverageElementType> getTypes();

} // CoverageSession
