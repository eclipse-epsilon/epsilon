/**
 */
package org.eclipse.epsilon.coverage.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * An element (AST node) of a source file. Probes are elements that are expected to be executed directly by the engine and contribute to coverage statistics. Non-probe elements (e.g. operations, rules) group other elements.
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.coverage.model.CoverageElement#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.CoverageElement#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.CoverageElement#getStartLine <em>Start Line</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.CoverageElement#getStartColumn <em>Start Column</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.CoverageElement#getEndLine <em>End Line</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.CoverageElement#getEndColumn <em>End Column</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.CoverageElement#isProbe <em>Probe</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.CoverageElement#getHits <em>Hits</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.CoverageElement#getChildren <em>Children</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.CoverageElement#getParent <em>Parent</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageElement()
 * @model
 * @generated
 */
public interface CoverageElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see #setKind(String)
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageElement_Kind()
	 * @model
	 * @generated
	 */
	String getKind();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see #getKind()
	 * @generated
	 */
	void setKind(String value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageElement_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Start Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Line</em>' attribute.
	 * @see #setStartLine(int)
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageElement_StartLine()
	 * @model
	 * @generated
	 */
	int getStartLine();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getStartLine <em>Start Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Line</em>' attribute.
	 * @see #getStartLine()
	 * @generated
	 */
	void setStartLine(int value);

	/**
	 * Returns the value of the '<em><b>Start Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Column</em>' attribute.
	 * @see #setStartColumn(int)
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageElement_StartColumn()
	 * @model
	 * @generated
	 */
	int getStartColumn();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getStartColumn <em>Start Column</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Column</em>' attribute.
	 * @see #getStartColumn()
	 * @generated
	 */
	void setStartColumn(int value);

	/**
	 * Returns the value of the '<em><b>End Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Line</em>' attribute.
	 * @see #setEndLine(int)
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageElement_EndLine()
	 * @model
	 * @generated
	 */
	int getEndLine();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getEndLine <em>End Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Line</em>' attribute.
	 * @see #getEndLine()
	 * @generated
	 */
	void setEndLine(int value);

	/**
	 * Returns the value of the '<em><b>End Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Column</em>' attribute.
	 * @see #setEndColumn(int)
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageElement_EndColumn()
	 * @model
	 * @generated
	 */
	int getEndColumn();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getEndColumn <em>End Column</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Column</em>' attribute.
	 * @see #getEndColumn()
	 * @generated
	 */
	void setEndColumn(int value);

	/**
	 * Returns the value of the '<em><b>Probe</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Probe</em>' attribute.
	 * @see #setProbe(boolean)
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageElement_Probe()
	 * @model
	 * @generated
	 */
	boolean isProbe();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.coverage.model.CoverageElement#isProbe <em>Probe</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Probe</em>' attribute.
	 * @see #isProbe()
	 * @generated
	 */
	void setProbe(boolean value);

	/**
	 * Returns the value of the '<em><b>Hits</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Hits</em>' attribute.
	 * @see #setHits(long)
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageElement_Hits()
	 * @model
	 * @generated
	 */
	long getHits();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getHits <em>Hits</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Hits</em>' attribute.
	 * @see #getHits()
	 * @generated
	 */
	void setHits(long value);

	/**
	 * Returns the value of the '<em><b>Children</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.epsilon.coverage.model.CoverageElement}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Children</em>' containment reference list.
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageElement_Children()
	 * @see org.eclipse.epsilon.coverage.model.CoverageElement#getParent
	 * @model opposite="parent" containment="true"
	 * @generated
	 */
	EList<CoverageElement> getChildren();

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' container reference.
	 * @see #setParent(CoverageElement)
	 * @see org.eclipse.epsilon.coverage.model.CoveragePackage#getCoverageElement_Parent()
	 * @see org.eclipse.epsilon.coverage.model.CoverageElement#getChildren
	 * @model opposite="children" transient="false"
	 * @generated
	 */
	CoverageElement getParent();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getParent <em>Parent</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' container reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(CoverageElement value);

} // CoverageElement
