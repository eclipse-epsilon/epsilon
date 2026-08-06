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
package org.eclipse.epsilon.emc.muddle.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.epsilon.emc.muddle.MuddleElementStyle;
import org.eclipse.epsilon.emc.muddle.MuddlePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element Style</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementStyleImpl#getColor <em>Color</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementStyleImpl#getShape <em>Shape</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementStyleImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementStyleImpl#getHeight <em>Height</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementStyleImpl#getBorderWidth <em>Border Width</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementStyleImpl#getLabelFontSize <em>Label Font Size</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementStyleImpl#getX <em>X</em>}</li>
 *   <li>{@link org.eclipse.epsilon.emc.muddle.impl.MuddleElementStyleImpl#getY <em>Y</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MuddleElementStyleImpl extends MinimalEObjectImpl.Container implements MuddleElementStyle {
	/**
	 * The default value of the '{@link #getColor() <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected static final String COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getColor() <em>Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected String color = COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getShape() <em>Shape</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShape()
	 * @generated
	 * @ordered
	 */
	protected static final String SHAPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getShape() <em>Shape</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShape()
	 * @generated
	 * @ordered
	 */
	protected String shape = SHAPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected static final double WIDTH_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected double width = WIDTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected static final double HEIGHT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected double height = HEIGHT_EDEFAULT;

	/**
	 * The default value of the '{@link #getBorderWidth() <em>Border Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBorderWidth()
	 * @generated
	 * @ordered
	 */
	protected static final double BORDER_WIDTH_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getBorderWidth() <em>Border Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBorderWidth()
	 * @generated
	 * @ordered
	 */
	protected double borderWidth = BORDER_WIDTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getLabelFontSize() <em>Label Font Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabelFontSize()
	 * @generated
	 * @ordered
	 */
	protected static final int LABEL_FONT_SIZE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLabelFontSize() <em>Label Font Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabelFontSize()
	 * @generated
	 * @ordered
	 */
	protected int labelFontSize = LABEL_FONT_SIZE_EDEFAULT;

	/**
	 * The default value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected static final double X_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected double x = X_EDEFAULT;

	/**
	 * The default value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected static final double Y_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected double y = Y_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MuddleElementStyleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MuddlePackage.Literals.MUDDLE_ELEMENT_STYLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getColor() {
		return color;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setColor(String newColor) {
		String oldColor = color;
		color = newColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MuddlePackage.MUDDLE_ELEMENT_STYLE__COLOR, oldColor, color));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getShape() {
		return shape;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShape(String newShape) {
		String oldShape = shape;
		shape = newShape;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MuddlePackage.MUDDLE_ELEMENT_STYLE__SHAPE, oldShape, shape));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidth(double newWidth) {
		double oldWidth = width;
		width = newWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MuddlePackage.MUDDLE_ELEMENT_STYLE__WIDTH, oldWidth, width));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeight(double newHeight) {
		double oldHeight = height;
		height = newHeight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MuddlePackage.MUDDLE_ELEMENT_STYLE__HEIGHT, oldHeight, height));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getBorderWidth() {
		return borderWidth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBorderWidth(double newBorderWidth) {
		double oldBorderWidth = borderWidth;
		borderWidth = newBorderWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MuddlePackage.MUDDLE_ELEMENT_STYLE__BORDER_WIDTH, oldBorderWidth, borderWidth));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLabelFontSize() {
		return labelFontSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabelFontSize(int newLabelFontSize) {
		int oldLabelFontSize = labelFontSize;
		labelFontSize = newLabelFontSize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MuddlePackage.MUDDLE_ELEMENT_STYLE__LABEL_FONT_SIZE, oldLabelFontSize, labelFontSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getX() {
		return x;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setX(double newX) {
		double oldX = x;
		x = newX;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MuddlePackage.MUDDLE_ELEMENT_STYLE__X, oldX, x));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getY() {
		return y;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setY(double newY) {
		double oldY = y;
		y = newY;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MuddlePackage.MUDDLE_ELEMENT_STYLE__Y, oldY, y));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__COLOR:
				return getColor();
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__SHAPE:
				return getShape();
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__WIDTH:
				return getWidth();
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__HEIGHT:
				return getHeight();
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__BORDER_WIDTH:
				return getBorderWidth();
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__LABEL_FONT_SIZE:
				return getLabelFontSize();
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__X:
				return getX();
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__Y:
				return getY();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__COLOR:
				setColor((String)newValue);
				return;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__SHAPE:
				setShape((String)newValue);
				return;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__WIDTH:
				setWidth((Double)newValue);
				return;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__HEIGHT:
				setHeight((Double)newValue);
				return;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__BORDER_WIDTH:
				setBorderWidth((Double)newValue);
				return;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__LABEL_FONT_SIZE:
				setLabelFontSize((Integer)newValue);
				return;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__X:
				setX((Double)newValue);
				return;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__Y:
				setY((Double)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__COLOR:
				setColor(COLOR_EDEFAULT);
				return;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__SHAPE:
				setShape(SHAPE_EDEFAULT);
				return;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__WIDTH:
				setWidth(WIDTH_EDEFAULT);
				return;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__HEIGHT:
				setHeight(HEIGHT_EDEFAULT);
				return;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__BORDER_WIDTH:
				setBorderWidth(BORDER_WIDTH_EDEFAULT);
				return;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__LABEL_FONT_SIZE:
				setLabelFontSize(LABEL_FONT_SIZE_EDEFAULT);
				return;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__X:
				setX(X_EDEFAULT);
				return;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__Y:
				setY(Y_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__COLOR:
				return COLOR_EDEFAULT == null ? color != null : !COLOR_EDEFAULT.equals(color);
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__SHAPE:
				return SHAPE_EDEFAULT == null ? shape != null : !SHAPE_EDEFAULT.equals(shape);
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__WIDTH:
				return width != WIDTH_EDEFAULT;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__HEIGHT:
				return height != HEIGHT_EDEFAULT;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__BORDER_WIDTH:
				return borderWidth != BORDER_WIDTH_EDEFAULT;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__LABEL_FONT_SIZE:
				return labelFontSize != LABEL_FONT_SIZE_EDEFAULT;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__X:
				return x != X_EDEFAULT;
			case MuddlePackage.MUDDLE_ELEMENT_STYLE__Y:
				return y != Y_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (color: ");
		result.append(color);
		result.append(", shape: ");
		result.append(shape);
		result.append(", width: ");
		result.append(width);
		result.append(", height: ");
		result.append(height);
		result.append(", borderWidth: ");
		result.append(borderWidth);
		result.append(", labelFontSize: ");
		result.append(labelFontSize);
		result.append(", x: ");
		result.append(x);
		result.append(", y: ");
		result.append(y);
		result.append(')');
		return result.toString();
	}

} //MuddleElementStyleImpl
