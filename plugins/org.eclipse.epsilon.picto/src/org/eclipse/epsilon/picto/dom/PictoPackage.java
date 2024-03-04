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
package org.eclipse.epsilon.picto.dom;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.epsilon.picto.dom.PictoFactory
 * @model kind="package"
 * @generated
 */
public interface PictoPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "dom";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "picto";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	PictoPackage eINSTANCE = org.eclipse.epsilon.picto.dom.impl.PictoPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.picto.dom.impl.PictoImpl <em>Picto</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.picto.dom.impl.PictoImpl
	 * @see org.eclipse.epsilon.picto.dom.impl.PictoPackageImpl#getPicto()
	 * @generated
	 */
	int PICTO = 0;

	/**
	 * The feature id for the '<em><b>Transformation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PICTO__TRANSFORMATION = 0;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PICTO__FORMAT = 1;

	/**
	 * The feature id for the '<em><b>Standalone</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PICTO__STANDALONE = 2;

	/**
	 * The feature id for the '<em><b>Models</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PICTO__MODELS = 3;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PICTO__PARAMETERS = 4;

	/**
	 * The feature id for the '<em><b>Custom Views</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PICTO__CUSTOM_VIEWS = 5;

	/**
	 * The number of structural features of the '<em>Picto</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PICTO_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Picto</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PICTO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.picto.dom.impl.ModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.picto.dom.impl.ModelImpl
	 * @see org.eclipse.epsilon.picto.dom.impl.PictoPackageImpl#getModel()
	 * @generated
	 */
	int MODEL = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL__PARAMETERS = 2;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.picto.dom.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.picto.dom.impl.ParameterImpl
	 * @see org.eclipse.epsilon.picto.dom.impl.PictoPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__VALUE = 1;

	/**
	 * The feature id for the '<em><b>Values</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__VALUES = 2;

	/**
	 * The feature id for the '<em><b>File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__FILE = 3;

	/**
	 * The feature id for the '<em><b>Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__ITEMS = 4;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_OPERATION_COUNT = 0;


	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.picto.dom.impl.CustomViewImpl <em>Custom View</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.picto.dom.impl.CustomViewImpl
	 * @see org.eclipse.epsilon.picto.dom.impl.PictoPackageImpl#getCustomView()
	 * @generated
	 */
	int CUSTOM_VIEW = 3;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_VIEW__PATH = 0;

	/**
	 * The feature id for the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_VIEW__ICON = 1;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_VIEW__FORMAT = 2;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_VIEW__TYPE = 3;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_VIEW__SOURCE = 4;

	/**
	 * The feature id for the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_VIEW__POSITION = 5;

	/**
	 * The feature id for the '<em><b>Layers</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_VIEW__LAYERS = 6;

	/**
	 * The feature id for the '<em><b>Patches</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_VIEW__PATCHES = 7;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_VIEW__PARAMETERS = 8;

	/**
	 * The number of structural features of the '<em>Custom View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_VIEW_FEATURE_COUNT = 9;

	/**
	 * The number of operations of the '<em>Custom View</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_VIEW_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.picto.dom.impl.PatchImpl <em>Patch</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.picto.dom.impl.PatchImpl
	 * @see org.eclipse.epsilon.picto.dom.impl.PictoPackageImpl#getPatch()
	 * @generated
	 */
	int PATCH = 4;

	/**
	 * The feature id for the '<em><b>Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATCH__FORMAT = 0;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATCH__CONTENT = 1;

	/**
	 * The feature id for the '<em><b>Applies</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATCH__APPLIES = 2;

	/**
	 * The number of structural features of the '<em>Patch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATCH_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Patch</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PATCH_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.picto.dom.Picto <em>Picto</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Picto</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Picto
	 * @generated
	 */
	EClass getPicto();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.picto.dom.Picto#getTransformation <em>Transformation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transformation</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Picto#getTransformation()
	 * @see #getPicto()
	 * @generated
	 */
	EAttribute getPicto_Transformation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.picto.dom.Picto#getFormat <em>Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Picto#getFormat()
	 * @see #getPicto()
	 * @generated
	 */
	EAttribute getPicto_Format();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.picto.dom.Picto#isStandalone <em>Standalone</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Standalone</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Picto#isStandalone()
	 * @see #getPicto()
	 * @generated
	 */
	EAttribute getPicto_Standalone();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.picto.dom.Picto#getModels <em>Models</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Models</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Picto#getModels()
	 * @see #getPicto()
	 * @generated
	 */
	EReference getPicto_Models();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.picto.dom.Picto#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Picto#getParameters()
	 * @see #getPicto()
	 * @generated
	 */
	EReference getPicto_Parameters();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.picto.dom.Picto#getCustomViews <em>Custom Views</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Custom Views</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Picto#getCustomViews()
	 * @see #getPicto()
	 * @generated
	 */
	EReference getPicto_CustomViews();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.picto.dom.Model <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Model
	 * @generated
	 */
	EClass getModel();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.picto.dom.Model#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Model#getName()
	 * @see #getModel()
	 * @generated
	 */
	EAttribute getModel_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.picto.dom.Model#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Model#getType()
	 * @see #getModel()
	 * @generated
	 */
	EAttribute getModel_Type();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.picto.dom.Model#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Model#getParameters()
	 * @see #getModel()
	 * @generated
	 */
	EReference getModel_Parameters();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.picto.dom.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.picto.dom.Parameter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Parameter#getName()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.picto.dom.Parameter#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Parameter#getValue()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Value();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.epsilon.picto.dom.Parameter#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Values</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Parameter#getValues()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Values();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.picto.dom.Parameter#getFile <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Parameter#getFile()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_File();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.picto.dom.Parameter#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Items</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Parameter#getItems()
	 * @see #getParameter()
	 * @generated
	 */
	EReference getParameter_Items();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.picto.dom.CustomView <em>Custom View</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Custom View</em>'.
	 * @see org.eclipse.epsilon.picto.dom.CustomView
	 * @generated
	 */
	EClass getCustomView();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.epsilon.picto.dom.CustomView#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Path</em>'.
	 * @see org.eclipse.epsilon.picto.dom.CustomView#getPath()
	 * @see #getCustomView()
	 * @generated
	 */
	EAttribute getCustomView_Path();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.picto.dom.CustomView#getIcon <em>Icon</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Icon</em>'.
	 * @see org.eclipse.epsilon.picto.dom.CustomView#getIcon()
	 * @see #getCustomView()
	 * @generated
	 */
	EAttribute getCustomView_Icon();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.picto.dom.CustomView#getFormat <em>Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format</em>'.
	 * @see org.eclipse.epsilon.picto.dom.CustomView#getFormat()
	 * @see #getCustomView()
	 * @generated
	 */
	EAttribute getCustomView_Format();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.picto.dom.CustomView#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.epsilon.picto.dom.CustomView#getType()
	 * @see #getCustomView()
	 * @generated
	 */
	EAttribute getCustomView_Type();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.picto.dom.CustomView#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see org.eclipse.epsilon.picto.dom.CustomView#getSource()
	 * @see #getCustomView()
	 * @generated
	 */
	EAttribute getCustomView_Source();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.picto.dom.CustomView#getPosition <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Position</em>'.
	 * @see org.eclipse.epsilon.picto.dom.CustomView#getPosition()
	 * @see #getCustomView()
	 * @generated
	 */
	EAttribute getCustomView_Position();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.epsilon.picto.dom.CustomView#getLayers <em>Layers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Layers</em>'.
	 * @see org.eclipse.epsilon.picto.dom.CustomView#getLayers()
	 * @see #getCustomView()
	 * @generated
	 */
	EAttribute getCustomView_Layers();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.picto.dom.CustomView#getPatches <em>Patches</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Patches</em>'.
	 * @see org.eclipse.epsilon.picto.dom.CustomView#getPatches()
	 * @see #getCustomView()
	 * @generated
	 */
	EReference getCustomView_Patches();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.picto.dom.CustomView#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.eclipse.epsilon.picto.dom.CustomView#getParameters()
	 * @see #getCustomView()
	 * @generated
	 */
	EReference getCustomView_Parameters();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.picto.dom.Patch <em>Patch</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Patch</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Patch
	 * @generated
	 */
	EClass getPatch();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.picto.dom.Patch#getFormat <em>Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Format</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Patch#getFormat()
	 * @see #getPatch()
	 * @generated
	 */
	EAttribute getPatch_Format();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.picto.dom.Patch#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Content</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Patch#getContent()
	 * @see #getPatch()
	 * @generated
	 */
	EAttribute getPatch_Content();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.picto.dom.Patch#getApplies <em>Applies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Applies</em>'.
	 * @see org.eclipse.epsilon.picto.dom.Patch#getApplies()
	 * @see #getPatch()
	 * @generated
	 */
	EAttribute getPatch_Applies();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	PictoFactory getPictoFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.picto.dom.impl.PictoImpl <em>Picto</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.picto.dom.impl.PictoImpl
		 * @see org.eclipse.epsilon.picto.dom.impl.PictoPackageImpl#getPicto()
		 * @generated
		 */
		EClass PICTO = eINSTANCE.getPicto();

		/**
		 * The meta object literal for the '<em><b>Transformation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PICTO__TRANSFORMATION = eINSTANCE.getPicto_Transformation();

		/**
		 * The meta object literal for the '<em><b>Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PICTO__FORMAT = eINSTANCE.getPicto_Format();

		/**
		 * The meta object literal for the '<em><b>Standalone</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PICTO__STANDALONE = eINSTANCE.getPicto_Standalone();

		/**
		 * The meta object literal for the '<em><b>Models</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PICTO__MODELS = eINSTANCE.getPicto_Models();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PICTO__PARAMETERS = eINSTANCE.getPicto_Parameters();

		/**
		 * The meta object literal for the '<em><b>Custom Views</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PICTO__CUSTOM_VIEWS = eINSTANCE.getPicto_CustomViews();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.picto.dom.impl.ModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.picto.dom.impl.ModelImpl
		 * @see org.eclipse.epsilon.picto.dom.impl.PictoPackageImpl#getModel()
		 * @generated
		 */
		EClass MODEL = eINSTANCE.getModel();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL__NAME = eINSTANCE.getModel_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL__TYPE = eINSTANCE.getModel_Type();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MODEL__PARAMETERS = eINSTANCE.getModel_Parameters();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.picto.dom.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.picto.dom.impl.ParameterImpl
		 * @see org.eclipse.epsilon.picto.dom.impl.PictoPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__NAME = eINSTANCE.getParameter_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__VALUE = eINSTANCE.getParameter_Value();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__VALUES = eINSTANCE.getParameter_Values();

		/**
		 * The meta object literal for the '<em><b>File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__FILE = eINSTANCE.getParameter_File();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER__ITEMS = eINSTANCE.getParameter_Items();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.picto.dom.impl.CustomViewImpl <em>Custom View</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.picto.dom.impl.CustomViewImpl
		 * @see org.eclipse.epsilon.picto.dom.impl.PictoPackageImpl#getCustomView()
		 * @generated
		 */
		EClass CUSTOM_VIEW = eINSTANCE.getCustomView();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CUSTOM_VIEW__PATH = eINSTANCE.getCustomView_Path();

		/**
		 * The meta object literal for the '<em><b>Icon</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CUSTOM_VIEW__ICON = eINSTANCE.getCustomView_Icon();

		/**
		 * The meta object literal for the '<em><b>Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CUSTOM_VIEW__FORMAT = eINSTANCE.getCustomView_Format();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CUSTOM_VIEW__TYPE = eINSTANCE.getCustomView_Type();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CUSTOM_VIEW__SOURCE = eINSTANCE.getCustomView_Source();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CUSTOM_VIEW__POSITION = eINSTANCE.getCustomView_Position();

		/**
		 * The meta object literal for the '<em><b>Layers</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CUSTOM_VIEW__LAYERS = eINSTANCE.getCustomView_Layers();

		/**
		 * The meta object literal for the '<em><b>Patches</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CUSTOM_VIEW__PATCHES = eINSTANCE.getCustomView_Patches();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CUSTOM_VIEW__PARAMETERS = eINSTANCE.getCustomView_Parameters();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.picto.dom.impl.PatchImpl <em>Patch</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.picto.dom.impl.PatchImpl
		 * @see org.eclipse.epsilon.picto.dom.impl.PictoPackageImpl#getPatch()
		 * @generated
		 */
		EClass PATCH = eINSTANCE.getPatch();

		/**
		 * The meta object literal for the '<em><b>Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PATCH__FORMAT = eINSTANCE.getPatch_Format();

		/**
		 * The meta object literal for the '<em><b>Content</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PATCH__CONTENT = eINSTANCE.getPatch_Content();

		/**
		 * The meta object literal for the '<em><b>Applies</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PATCH__APPLIES = eINSTANCE.getPatch_Applies();

	}

} //PictoPackage
