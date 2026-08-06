/**
 * Copyright (c) 2011 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *      Louis Rose - initial API and implementation
 */
package org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink;

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
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TextlinkFactory
 * @model kind="package"
 * @generated
 */
public interface TextlinkPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "textlink";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/gmt/epsilon/egl/textlink";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "textlink";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TextlinkPackage eINSTANCE = org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextlinkPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TraceImpl <em>Trace</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TraceImpl
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextlinkPackageImpl#getTrace()
	 * @generated
	 */
	int TRACE = 0;

	/**
	 * The feature id for the '<em><b>Trace Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE__TRACE_LINKS = 0;

	/**
	 * The number of structural features of the '<em>Trace</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TraceLinkImpl <em>Trace Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TraceLinkImpl
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextlinkPackageImpl#getTraceLink()
	 * @generated
	 */
	int TRACE_LINK = 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_LINK__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Destination</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_LINK__DESTINATION = 1;

	/**
	 * The number of structural features of the '<em>Trace Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_LINK_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TraceLinkEndImpl <em>Trace Link End</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TraceLinkEndImpl
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextlinkPackageImpl#getTraceLinkEnd()
	 * @generated
	 */
	int TRACE_LINK_END = 2;

	/**
	 * The number of structural features of the '<em>Trace Link End</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRACE_LINK_END_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.ModelLocationImpl <em>Model Location</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.ModelLocationImpl
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextlinkPackageImpl#getModelLocation()
	 * @generated
	 */
	int MODEL_LOCATION = 3;

	/**
	 * The feature id for the '<em><b>Property Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_LOCATION__PROPERTY_NAME = TRACE_LINK_END_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Model Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MODEL_LOCATION_FEATURE_COUNT = TRACE_LINK_END_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.EmfModelLocationImpl <em>Emf Model Location</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.EmfModelLocationImpl
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextlinkPackageImpl#getEmfModelLocation()
	 * @generated
	 */
	int EMF_MODEL_LOCATION = 4;

	/**
	 * The feature id for the '<em><b>Property Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMF_MODEL_LOCATION__PROPERTY_NAME = MODEL_LOCATION__PROPERTY_NAME;

	/**
	 * The feature id for the '<em><b>Model Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMF_MODEL_LOCATION__MODEL_ELEMENT = MODEL_LOCATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Emf Model Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EMF_MODEL_LOCATION_FEATURE_COUNT = MODEL_LOCATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextLocationImpl <em>Text Location</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextLocationImpl
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextlinkPackageImpl#getTextLocation()
	 * @generated
	 */
	int TEXT_LOCATION = 5;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_LOCATION__RESOURCE = TRACE_LINK_END_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Region</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_LOCATION__REGION = TRACE_LINK_END_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Text Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEXT_LOCATION_FEATURE_COUNT = TRACE_LINK_END_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.RegionImpl <em>Region</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.RegionImpl
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextlinkPackageImpl#getRegion()
	 * @generated
	 */
	int REGION = 6;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__OFFSET = 0;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__LENGTH = 1;

	/**
	 * The feature id for the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION__TEXT = 2;

	/**
	 * The number of structural features of the '<em>Region</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REGION_FEATURE_COUNT = 3;

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Trace <em>Trace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Trace</em>'.
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Trace
	 * @generated
	 */
	EClass getTrace();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Trace#getTraceLinks <em>Trace Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Trace Links</em>'.
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Trace#getTraceLinks()
	 * @see #getTrace()
	 * @generated
	 */
	EReference getTrace_TraceLinks();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TraceLink <em>Trace Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Trace Link</em>'.
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TraceLink
	 * @generated
	 */
	EClass getTraceLink();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TraceLink#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Source</em>'.
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TraceLink#getSource()
	 * @see #getTraceLink()
	 * @generated
	 */
	EReference getTraceLink_Source();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TraceLink#getDestination <em>Destination</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Destination</em>'.
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TraceLink#getDestination()
	 * @see #getTraceLink()
	 * @generated
	 */
	EReference getTraceLink_Destination();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TraceLinkEnd <em>Trace Link End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Trace Link End</em>'.
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TraceLinkEnd
	 * @generated
	 */
	EClass getTraceLinkEnd();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.ModelLocation <em>Model Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model Location</em>'.
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.ModelLocation
	 * @generated
	 */
	EClass getModelLocation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.ModelLocation#getPropertyName <em>Property Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property Name</em>'.
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.ModelLocation#getPropertyName()
	 * @see #getModelLocation()
	 * @generated
	 */
	EAttribute getModelLocation_PropertyName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.EmfModelLocation <em>Emf Model Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Emf Model Location</em>'.
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.EmfModelLocation
	 * @generated
	 */
	EClass getEmfModelLocation();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.EmfModelLocation#getModelElement <em>Model Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Model Element</em>'.
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.EmfModelLocation#getModelElement()
	 * @see #getEmfModelLocation()
	 * @generated
	 */
	EReference getEmfModelLocation_ModelElement();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TextLocation <em>Text Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Text Location</em>'.
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TextLocation
	 * @generated
	 */
	EClass getTextLocation();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TextLocation#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource</em>'.
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TextLocation#getResource()
	 * @see #getTextLocation()
	 * @generated
	 */
	EAttribute getTextLocation_Resource();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TextLocation#getRegion <em>Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Region</em>'.
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.TextLocation#getRegion()
	 * @see #getTextLocation()
	 * @generated
	 */
	EReference getTextLocation_Region();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Region <em>Region</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Region</em>'.
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Region
	 * @generated
	 */
	EClass getRegion();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Region#getOffset <em>Offset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Offset</em>'.
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Region#getOffset()
	 * @see #getRegion()
	 * @generated
	 */
	EAttribute getRegion_Offset();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Region#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Region#getLength()
	 * @see #getRegion()
	 * @generated
	 */
	EAttribute getRegion_Length();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Region#getText <em>Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Text</em>'.
	 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.Region#getText()
	 * @see #getRegion()
	 * @generated
	 */
	EAttribute getRegion_Text();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TextlinkFactory getTextlinkFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TraceImpl <em>Trace</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TraceImpl
		 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextlinkPackageImpl#getTrace()
		 * @generated
		 */
		EClass TRACE = eINSTANCE.getTrace();

		/**
		 * The meta object literal for the '<em><b>Trace Links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE__TRACE_LINKS = eINSTANCE.getTrace_TraceLinks();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TraceLinkImpl <em>Trace Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TraceLinkImpl
		 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextlinkPackageImpl#getTraceLink()
		 * @generated
		 */
		EClass TRACE_LINK = eINSTANCE.getTraceLink();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE_LINK__SOURCE = eINSTANCE.getTraceLink_Source();

		/**
		 * The meta object literal for the '<em><b>Destination</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRACE_LINK__DESTINATION = eINSTANCE.getTraceLink_Destination();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TraceLinkEndImpl <em>Trace Link End</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TraceLinkEndImpl
		 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextlinkPackageImpl#getTraceLinkEnd()
		 * @generated
		 */
		EClass TRACE_LINK_END = eINSTANCE.getTraceLinkEnd();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.ModelLocationImpl <em>Model Location</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.ModelLocationImpl
		 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextlinkPackageImpl#getModelLocation()
		 * @generated
		 */
		EClass MODEL_LOCATION = eINSTANCE.getModelLocation();

		/**
		 * The meta object literal for the '<em><b>Property Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MODEL_LOCATION__PROPERTY_NAME = eINSTANCE.getModelLocation_PropertyName();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.EmfModelLocationImpl <em>Emf Model Location</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.EmfModelLocationImpl
		 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextlinkPackageImpl#getEmfModelLocation()
		 * @generated
		 */
		EClass EMF_MODEL_LOCATION = eINSTANCE.getEmfModelLocation();

		/**
		 * The meta object literal for the '<em><b>Model Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EMF_MODEL_LOCATION__MODEL_ELEMENT = eINSTANCE.getEmfModelLocation_ModelElement();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextLocationImpl <em>Text Location</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextLocationImpl
		 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextlinkPackageImpl#getTextLocation()
		 * @generated
		 */
		EClass TEXT_LOCATION = eINSTANCE.getTextLocation();

		/**
		 * The meta object literal for the '<em><b>Resource</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEXT_LOCATION__RESOURCE = eINSTANCE.getTextLocation_Resource();

		/**
		 * The meta object literal for the '<em><b>Region</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEXT_LOCATION__REGION = eINSTANCE.getTextLocation_Region();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.RegionImpl <em>Region</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.RegionImpl
		 * @see org.eclipse.epsilon.egl.dt.traceability.fine.emf.textlink.impl.TextlinkPackageImpl#getRegion()
		 * @generated
		 */
		EClass REGION = eINSTANCE.getRegion();

		/**
		 * The meta object literal for the '<em><b>Offset</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REGION__OFFSET = eINSTANCE.getRegion_Offset();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REGION__LENGTH = eINSTANCE.getRegion_Length();

		/**
		 * The meta object literal for the '<em><b>Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REGION__TEXT = eINSTANCE.getRegion_Text();

	}

} //TextlinkPackage
