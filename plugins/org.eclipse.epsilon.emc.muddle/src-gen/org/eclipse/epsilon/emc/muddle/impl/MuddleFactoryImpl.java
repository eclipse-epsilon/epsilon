/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitris Kolovos - initial API and implementation
 ******************************************************************************/
/**
 */
package org.eclipse.epsilon.emc.muddle.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.epsilon.emc.muddle.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MuddleFactoryImpl extends EFactoryImpl implements MuddleFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static MuddleFactory init() {
		try {
			MuddleFactory theMuddleFactory = (MuddleFactory)EPackage.Registry.INSTANCE.getEFactory(MuddlePackage.eNS_URI);
			if (theMuddleFactory != null) {
				return theMuddleFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MuddleFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MuddleFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case MuddlePackage.MUDDLE: return createMuddle();
			case MuddlePackage.MUDDLE_ELEMENT: return createMuddleElement();
			case MuddlePackage.MUDDLE_ELEMENT_STYLE: return createMuddleElementStyle();
			case MuddlePackage.SLOT: return createSlot();
			case MuddlePackage.FEATURE: return createFeature();
			case MuddlePackage.MUDDLE_ELEMENT_TYPE: return createMuddleElementType();
			case MuddlePackage.LINK_ELEMENT_TYPE: return createLinkElementType();
			case MuddlePackage.INTEGER_TYPE: return createIntegerType();
			case MuddlePackage.STRING_TYPE: return createStringType();
			case MuddlePackage.BOOLEAN_TYPE: return createBooleanType();
			case MuddlePackage.REAL_TYPE: return createRealType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Muddle createMuddle() {
		MuddleImpl muddle = new MuddleImpl();
		return muddle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MuddleElement createMuddleElement() {
		MuddleElementImpl muddleElement = new MuddleElementImpl();
		return muddleElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MuddleElementStyle createMuddleElementStyle() {
		MuddleElementStyleImpl muddleElementStyle = new MuddleElementStyleImpl();
		return muddleElementStyle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Slot createSlot() {
		SlotImpl slot = new SlotImpl();
		return slot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Feature createFeature() {
		FeatureImpl feature = new FeatureImpl();
		return feature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MuddleElementType createMuddleElementType() {
		MuddleElementTypeImpl muddleElementType = new MuddleElementTypeImpl();
		return muddleElementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LinkElementType createLinkElementType() {
		LinkElementTypeImpl linkElementType = new LinkElementTypeImpl();
		return linkElementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerType createIntegerType() {
		IntegerTypeImpl integerType = new IntegerTypeImpl();
		return integerType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringType createStringType() {
		StringTypeImpl stringType = new StringTypeImpl();
		return stringType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanType createBooleanType() {
		BooleanTypeImpl booleanType = new BooleanTypeImpl();
		return booleanType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RealType createRealType() {
		RealTypeImpl realType = new RealTypeImpl();
		return realType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MuddlePackage getMuddlePackage() {
		return (MuddlePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static MuddlePackage getPackage() {
		return MuddlePackage.eINSTANCE;
	}

} //MuddleFactoryImpl
