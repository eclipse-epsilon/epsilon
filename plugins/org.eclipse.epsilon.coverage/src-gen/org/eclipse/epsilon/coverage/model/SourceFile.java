/**
 */
package org.eclipse.epsilon.coverage.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Source File</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * A source file (module) that was involved in the execution.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.coverage.model.SourceFile#getUri <em>Uri</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.SourceFile#getLanguage <em>Language</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.SourceFile#getContentHash <em>Content Hash</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.SourceFile#getSession <em>Session</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.SourceFile#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getSourceFile()
 * @model
 * @generated
 */
public interface SourceFile extends EObject {
	/**
	 * Returns the value of the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uri</em>' attribute.
	 * @see #setUri(String)
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getSourceFile_Uri()
	 * @model
	 * @generated
	 */
	String getUri();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.coverage.model.SourceFile#getUri <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uri</em>' attribute.
	 * @see #getUri()
	 * @generated
	 */
	void setUri(String value);

	/**
	 * Returns the value of the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Language</em>' attribute.
	 * @see #setLanguage(String)
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getSourceFile_Language()
	 * @model
	 * @generated
	 */
	String getLanguage();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.coverage.model.SourceFile#getLanguage <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Language</em>' attribute.
	 * @see #getLanguage()
	 * @generated
	 */
	void setLanguage(String value);

	/**
	 * Returns the value of the '<em><b>Content Hash</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Hash of the contents of the file at the time of recording; used to detect stale coverage information.
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Content Hash</em>' attribute.
	 * @see #setContentHash(String)
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getSourceFile_ContentHash()
	 * @model
	 * @generated
	 */
	String getContentHash();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.coverage.model.SourceFile#getContentHash <em>Content Hash</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Content Hash</em>' attribute.
	 * @see #getContentHash()
	 * @generated
	 */
	void setContentHash(String value);

	/**
	 * Returns the value of the '<em><b>Session</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.coverage.model.CoverageSession#getFiles <em>Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Session</em>' container reference.
	 * @see #setSession(CoverageSession)
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getSourceFile_Session()
	 * @see org.eclipse.epsilon.coverage.model.CoverageSession#getFiles
	 * @model opposite="files" transient="false"
	 * @generated
	 */
	CoverageSession getSession();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.coverage.model.SourceFile#getSession <em>Session</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Session</em>' container reference.
	 * @see #getSession()
	 * @generated
	 */
	void setSession(CoverageSession value);

	/**
	 * Returns the value of the '<em><b>Elements</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.coverage.model.CoverageElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Elements</em>' containment reference list.
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getSourceFile_Elements()
	 * @model containment="true"
	 * @generated
	 */
	EList<CoverageElement> getElements();

} // SourceFile
