/**
 * *******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.hutn.model.hutn.impl;

import java.lang.Object;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.hutn.model.hutn.AttributeSlot;
import org.eclipse.epsilon.hutn.model.hutn.HutnPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Attribute Slot</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class AttributeSlotImpl extends SlotImpl<Object> implements AttributeSlot {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AttributeSlotImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HutnPackage.Literals.ATTRIBUTE_SLOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean typeCompatibleWith(EStructuralFeature structuralFeature) {
		for (Object value : getValues()) {
			if (!typeCompatible(structuralFeature.getEType(), value)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	private static boolean typeCompatible(EClassifier type, Object value) {
		if (type instanceof EEnum) {
			return (((EEnum)type).getEEnumLiteralByLiteral(value.toString()) != null);
			
		} else {
			return type.isInstance(value);
		}
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void coerceValues() {
		if (hasEStructuralFeature()) {
			final EClassifier type = getEStructuralFeature().getEType();
			
			if (type != null) {
				for (int index = 0; index < getValues().size(); index++) {
					coerceValue(index, type);
				}
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	private void coerceValue(int index, final EClassifier type) {
		final Object value = getValues().get(index);
		final Object coerced = coerce(value, (EDataType)type);
		
		getValues().set(index, coerced);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	private static Object coerce(final Object value, final EDataType type) {
		return type.getEPackage().getEFactoryInstance().createFromString(type, value.toString());
	}

} //AttributeSlotImpl
