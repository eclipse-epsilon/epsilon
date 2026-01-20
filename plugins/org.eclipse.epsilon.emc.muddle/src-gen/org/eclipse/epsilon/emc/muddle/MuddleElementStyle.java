/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
/**
 */
package org.eclipse.epsilon.emc.muddle;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Element Style</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getColor <em>Color</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getShape <em>Shape</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getWidth <em>Width</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getHeight <em>Height</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getBorderWidth <em>Border Width</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getLabelFontSize <em>Label Font Size</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getX <em>X</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getY <em>Y</em>}</li>
 * </ul>
 *
 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getMuddleElementStyle()
 * @model
 * @generated
 */
public interface MuddleElementStyle extends EObject {
	/**
	 * Returns the value of the '<em><b>Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Color</em>' attribute.
	 * @see #setColor(String)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getMuddleElementStyle_Color()
	 * @model
	 * @generated
	 */
	String getColor();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getColor <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Color</em>' attribute.
	 * @see #getColor()
	 * @generated
	 */
	void setColor(String value);

	/**
	 * Returns the value of the '<em><b>Shape</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Shape</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Shape</em>' attribute.
	 * @see #setShape(String)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getMuddleElementStyle_Shape()
	 * @model
	 * @generated
	 */
	String getShape();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getShape <em>Shape</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shape</em>' attribute.
	 * @see #getShape()
	 * @generated
	 */
	void setShape(String value);

	/**
	 * Returns the value of the '<em><b>Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Width</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Width</em>' attribute.
	 * @see #setWidth(double)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getMuddleElementStyle_Width()
	 * @model
	 * @generated
	 */
	double getWidth();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getWidth <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Width</em>' attribute.
	 * @see #getWidth()
	 * @generated
	 */
	void setWidth(double value);

	/**
	 * Returns the value of the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Height</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Height</em>' attribute.
	 * @see #setHeight(double)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getMuddleElementStyle_Height()
	 * @model
	 * @generated
	 */
	double getHeight();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getHeight <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Height</em>' attribute.
	 * @see #getHeight()
	 * @generated
	 */
	void setHeight(double value);

	/**
	 * Returns the value of the '<em><b>Border Width</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Border Width</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Border Width</em>' attribute.
	 * @see #setBorderWidth(double)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getMuddleElementStyle_BorderWidth()
	 * @model
	 * @generated
	 */
	double getBorderWidth();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getBorderWidth <em>Border Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Border Width</em>' attribute.
	 * @see #getBorderWidth()
	 * @generated
	 */
	void setBorderWidth(double value);

	/**
	 * Returns the value of the '<em><b>Label Font Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label Font Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label Font Size</em>' attribute.
	 * @see #setLabelFontSize(int)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getMuddleElementStyle_LabelFontSize()
	 * @model
	 * @generated
	 */
	int getLabelFontSize();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getLabelFontSize <em>Label Font Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label Font Size</em>' attribute.
	 * @see #getLabelFontSize()
	 * @generated
	 */
	void setLabelFontSize(int value);

	/**
	 * Returns the value of the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>X</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>X</em>' attribute.
	 * @see #setX(double)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getMuddleElementStyle_X()
	 * @model
	 * @generated
	 */
	double getX();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getX <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>X</em>' attribute.
	 * @see #getX()
	 * @generated
	 */
	void setX(double value);

	/**
	 * Returns the value of the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Y</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Y</em>' attribute.
	 * @see #setY(double)
	 * @see org.eclipse.epsilon.emc.muddle.MuddlePackage#getMuddleElementStyle_Y()
	 * @model
	 * @generated
	 */
	double getY();

	/**
	 * Sets the value of the '{@link org.eclipse.epsilon.emc.muddle.MuddleElementStyle#getY <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Y</em>' attribute.
	 * @see #getY()
	 * @generated
	 */
	void setY(double value);

} // MuddleElementStyle
