/**
 */
package org.eclipse.epsilon.coverage.model;

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
 * @see org.eclipse.epsilon.coverage.model.CoverageFactory
 * @model kind="package"
 * @generated
 */
public interface CoveragePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/epsilon/coverage";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "coverage";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CoveragePackage eINSTANCE = org.eclipse.epsilon.coverage.model.impl.CoveragePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.coverage.model.impl.CoverageSessionImpl <em>Session</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.coverage.model.impl.CoverageSessionImpl
	 * @see org.eclipse.epsilon.coverage.model.impl.CoveragePackageImpl#getCoverageSession()
	 * @generated
	 */
	int COVERAGE_SESSION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_SESSION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_SESSION__TIMESTAMP = 1;

	/**
	 * The feature id for the '<em><b>Launch Configuration</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_SESSION__LAUNCH_CONFIGURATION = 2;

	/**
	 * The feature id for the '<em><b>Files</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_SESSION__FILES = 3;

	/**
	 * The number of structural features of the '<em>Session</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_SESSION_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Session</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_SESSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.coverage.model.impl.SourceFileImpl <em>Source File</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.coverage.model.impl.SourceFileImpl
	 * @see org.eclipse.epsilon.coverage.model.impl.CoveragePackageImpl#getSourceFile()
	 * @generated
	 */
	int SOURCE_FILE = 1;

	/**
	 * The feature id for the '<em><b>Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FILE__URI = 0;

	/**
	 * The feature id for the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FILE__LANGUAGE = 1;

	/**
	 * The feature id for the '<em><b>Content Hash</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FILE__CONTENT_HASH = 2;

	/**
	 * The feature id for the '<em><b>Session</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FILE__SESSION = 3;

	/**
	 * The feature id for the '<em><b>Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FILE__ELEMENTS = 4;

	/**
	 * The number of structural features of the '<em>Source File</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FILE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Source File</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOURCE_FILE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.epsilon.coverage.model.impl.CoverageElementImpl <em>Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.epsilon.coverage.model.impl.CoverageElementImpl
	 * @see org.eclipse.epsilon.coverage.model.impl.CoveragePackageImpl#getCoverageElement()
	 * @generated
	 */
	int COVERAGE_ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_ELEMENT__KIND = 0;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_ELEMENT__LABEL = 1;

	/**
	 * The feature id for the '<em><b>Start Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_ELEMENT__START_LINE = 2;

	/**
	 * The feature id for the '<em><b>Start Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_ELEMENT__START_COLUMN = 3;

	/**
	 * The feature id for the '<em><b>End Line</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_ELEMENT__END_LINE = 4;

	/**
	 * The feature id for the '<em><b>End Column</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_ELEMENT__END_COLUMN = 5;

	/**
	 * The feature id for the '<em><b>Probe</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_ELEMENT__PROBE = 6;

	/**
	 * The feature id for the '<em><b>Hits</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_ELEMENT__HITS = 7;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_ELEMENT__CHILDREN = 8;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_ELEMENT__PARENT = 9;

	/**
	 * The number of structural features of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_ELEMENT_FEATURE_COUNT = 10;

	/**
	 * The number of operations of the '<em>Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COVERAGE_ELEMENT_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.coverage.model.CoverageSession <em>Session</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Session</em>'.
	 * @see org.eclipse.epsilon.coverage.model.CoverageSession
	 * @generated
	 */
	EClass getCoverageSession();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.coverage.model.CoverageSession#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.epsilon.coverage.model.CoverageSession#getName()
	 * @see #getCoverageSession()
	 * @generated
	 */
	EAttribute getCoverageSession_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.coverage.model.CoverageSession#getTimestamp <em>Timestamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timestamp</em>'.
	 * @see org.eclipse.epsilon.coverage.model.CoverageSession#getTimestamp()
	 * @see #getCoverageSession()
	 * @generated
	 */
	EAttribute getCoverageSession_Timestamp();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.coverage.model.CoverageSession#getLaunchConfiguration <em>Launch Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Launch Configuration</em>'.
	 * @see org.eclipse.epsilon.coverage.model.CoverageSession#getLaunchConfiguration()
	 * @see #getCoverageSession()
	 * @generated
	 */
	EAttribute getCoverageSession_LaunchConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.coverage.model.CoverageSession#getFiles <em>Files</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Files</em>'.
	 * @see org.eclipse.epsilon.coverage.model.CoverageSession#getFiles()
	 * @see #getCoverageSession()
	 * @generated
	 */
	EReference getCoverageSession_Files();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.coverage.model.SourceFile <em>Source File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source File</em>'.
	 * @see org.eclipse.epsilon.coverage.model.SourceFile
	 * @generated
	 */
	EClass getSourceFile();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.coverage.model.SourceFile#getUri <em>Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uri</em>'.
	 * @see org.eclipse.epsilon.coverage.model.SourceFile#getUri()
	 * @see #getSourceFile()
	 * @generated
	 */
	EAttribute getSourceFile_Uri();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.coverage.model.SourceFile#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Language</em>'.
	 * @see org.eclipse.epsilon.coverage.model.SourceFile#getLanguage()
	 * @see #getSourceFile()
	 * @generated
	 */
	EAttribute getSourceFile_Language();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.coverage.model.SourceFile#getContentHash <em>Content Hash</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Content Hash</em>'.
	 * @see org.eclipse.epsilon.coverage.model.SourceFile#getContentHash()
	 * @see #getSourceFile()
	 * @generated
	 */
	EAttribute getSourceFile_ContentHash();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.epsilon.coverage.model.SourceFile#getSession <em>Session</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Session</em>'.
	 * @see org.eclipse.epsilon.coverage.model.SourceFile#getSession()
	 * @see #getSourceFile()
	 * @generated
	 */
	EReference getSourceFile_Session();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.coverage.model.SourceFile#getElements <em>Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Elements</em>'.
	 * @see org.eclipse.epsilon.coverage.model.SourceFile#getElements()
	 * @see #getSourceFile()
	 * @generated
	 */
	EReference getSourceFile_Elements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.epsilon.coverage.model.CoverageElement <em>Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element</em>'.
	 * @see org.eclipse.epsilon.coverage.model.CoverageElement
	 * @generated
	 */
	EClass getCoverageElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getKind <em>Kind</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kind</em>'.
	 * @see org.eclipse.epsilon.coverage.model.CoverageElement#getKind()
	 * @see #getCoverageElement()
	 * @generated
	 */
	EAttribute getCoverageElement_Kind();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see org.eclipse.epsilon.coverage.model.CoverageElement#getLabel()
	 * @see #getCoverageElement()
	 * @generated
	 */
	EAttribute getCoverageElement_Label();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getStartLine <em>Start Line</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Line</em>'.
	 * @see org.eclipse.epsilon.coverage.model.CoverageElement#getStartLine()
	 * @see #getCoverageElement()
	 * @generated
	 */
	EAttribute getCoverageElement_StartLine();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getStartColumn <em>Start Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Column</em>'.
	 * @see org.eclipse.epsilon.coverage.model.CoverageElement#getStartColumn()
	 * @see #getCoverageElement()
	 * @generated
	 */
	EAttribute getCoverageElement_StartColumn();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getEndLine <em>End Line</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End Line</em>'.
	 * @see org.eclipse.epsilon.coverage.model.CoverageElement#getEndLine()
	 * @see #getCoverageElement()
	 * @generated
	 */
	EAttribute getCoverageElement_EndLine();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getEndColumn <em>End Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End Column</em>'.
	 * @see org.eclipse.epsilon.coverage.model.CoverageElement#getEndColumn()
	 * @see #getCoverageElement()
	 * @generated
	 */
	EAttribute getCoverageElement_EndColumn();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.coverage.model.CoverageElement#isProbe <em>Probe</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Probe</em>'.
	 * @see org.eclipse.epsilon.coverage.model.CoverageElement#isProbe()
	 * @see #getCoverageElement()
	 * @generated
	 */
	EAttribute getCoverageElement_Probe();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getHits <em>Hits</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hits</em>'.
	 * @see org.eclipse.epsilon.coverage.model.CoverageElement#getHits()
	 * @see #getCoverageElement()
	 * @generated
	 */
	EAttribute getCoverageElement_Hits();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see org.eclipse.epsilon.coverage.model.CoverageElement#getChildren()
	 * @see #getCoverageElement()
	 * @generated
	 */
	EReference getCoverageElement_Children();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.epsilon.coverage.model.CoverageElement#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent</em>'.
	 * @see org.eclipse.epsilon.coverage.model.CoverageElement#getParent()
	 * @see #getCoverageElement()
	 * @generated
	 */
	EReference getCoverageElement_Parent();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CoverageFactory getCoverageFactory();

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
		 * The meta object literal for the '{@link org.eclipse.epsilon.coverage.model.impl.CoverageSessionImpl <em>Session</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.coverage.model.impl.CoverageSessionImpl
		 * @see org.eclipse.epsilon.coverage.model.impl.CoveragePackageImpl#getCoverageSession()
		 * @generated
		 */
		EClass COVERAGE_SESSION = eINSTANCE.getCoverageSession();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COVERAGE_SESSION__NAME = eINSTANCE.getCoverageSession_Name();

		/**
		 * The meta object literal for the '<em><b>Timestamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COVERAGE_SESSION__TIMESTAMP = eINSTANCE.getCoverageSession_Timestamp();

		/**
		 * The meta object literal for the '<em><b>Launch Configuration</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COVERAGE_SESSION__LAUNCH_CONFIGURATION = eINSTANCE.getCoverageSession_LaunchConfiguration();

		/**
		 * The meta object literal for the '<em><b>Files</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COVERAGE_SESSION__FILES = eINSTANCE.getCoverageSession_Files();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.coverage.model.impl.SourceFileImpl <em>Source File</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.coverage.model.impl.SourceFileImpl
		 * @see org.eclipse.epsilon.coverage.model.impl.CoveragePackageImpl#getSourceFile()
		 * @generated
		 */
		EClass SOURCE_FILE = eINSTANCE.getSourceFile();

		/**
		 * The meta object literal for the '<em><b>Uri</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE_FILE__URI = eINSTANCE.getSourceFile_Uri();

		/**
		 * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE_FILE__LANGUAGE = eINSTANCE.getSourceFile_Language();

		/**
		 * The meta object literal for the '<em><b>Content Hash</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOURCE_FILE__CONTENT_HASH = eINSTANCE.getSourceFile_ContentHash();

		/**
		 * The meta object literal for the '<em><b>Session</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_FILE__SESSION = eINSTANCE.getSourceFile_Session();

		/**
		 * The meta object literal for the '<em><b>Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOURCE_FILE__ELEMENTS = eINSTANCE.getSourceFile_Elements();

		/**
		 * The meta object literal for the '{@link org.eclipse.epsilon.coverage.model.impl.CoverageElementImpl <em>Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.epsilon.coverage.model.impl.CoverageElementImpl
		 * @see org.eclipse.epsilon.coverage.model.impl.CoveragePackageImpl#getCoverageElement()
		 * @generated
		 */
		EClass COVERAGE_ELEMENT = eINSTANCE.getCoverageElement();

		/**
		 * The meta object literal for the '<em><b>Kind</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COVERAGE_ELEMENT__KIND = eINSTANCE.getCoverageElement_Kind();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COVERAGE_ELEMENT__LABEL = eINSTANCE.getCoverageElement_Label();

		/**
		 * The meta object literal for the '<em><b>Start Line</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COVERAGE_ELEMENT__START_LINE = eINSTANCE.getCoverageElement_StartLine();

		/**
		 * The meta object literal for the '<em><b>Start Column</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COVERAGE_ELEMENT__START_COLUMN = eINSTANCE.getCoverageElement_StartColumn();

		/**
		 * The meta object literal for the '<em><b>End Line</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COVERAGE_ELEMENT__END_LINE = eINSTANCE.getCoverageElement_EndLine();

		/**
		 * The meta object literal for the '<em><b>End Column</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COVERAGE_ELEMENT__END_COLUMN = eINSTANCE.getCoverageElement_EndColumn();

		/**
		 * The meta object literal for the '<em><b>Probe</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COVERAGE_ELEMENT__PROBE = eINSTANCE.getCoverageElement_Probe();

		/**
		 * The meta object literal for the '<em><b>Hits</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COVERAGE_ELEMENT__HITS = eINSTANCE.getCoverageElement_Hits();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COVERAGE_ELEMENT__CHILDREN = eINSTANCE.getCoverageElement_Children();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COVERAGE_ELEMENT__PARENT = eINSTANCE.getCoverageElement_Parent();

	}

} //CoveragePackage
