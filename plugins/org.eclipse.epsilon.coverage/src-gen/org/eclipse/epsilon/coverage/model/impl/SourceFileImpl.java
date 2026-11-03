/**
 */
package org.eclipse.epsilon.coverage.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.epsilon.coverage.model.CoverageElement;
import org.eclipse.epsilon.coverage.model.CoveragePackage;
import org.eclipse.epsilon.coverage.model.CoverageSession;
import org.eclipse.epsilon.coverage.model.SourceFile;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Source File</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.SourceFileImpl#getUri <em>Uri</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.SourceFileImpl#getLanguage <em>Language</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.SourceFileImpl#getContentHash <em>Content Hash</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.SourceFileImpl#getSession <em>Session</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.SourceFileImpl#getElements <em>Elements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SourceFileImpl extends MinimalEObjectImpl.Container implements SourceFile {
	/**
	 * The default value of the '{@link #getUri() <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected static final String URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUri() <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected String uri = URI_EDEFAULT;

	/**
	 * The default value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected static final String LANGUAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected String language = LANGUAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getContentHash() <em>Content Hash</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContentHash()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTENT_HASH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContentHash() <em>Content Hash</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContentHash()
	 * @generated
	 * @ordered
	 */
	protected String contentHash = CONTENT_HASH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getElements() <em>Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElements()
	 * @generated
	 * @ordered
	 */
	protected EList<CoverageElement> elements;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SourceFileImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CoveragePackage.Literals.SOURCE_FILE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getUri() {
		return uri;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUri(String newUri) {
		String oldUri = uri;
		uri = newUri;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.SOURCE_FILE__URI, oldUri, uri));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLanguage() {
		return language;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLanguage(String newLanguage) {
		String oldLanguage = language;
		language = newLanguage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.SOURCE_FILE__LANGUAGE, oldLanguage, language));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getContentHash() {
		return contentHash;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContentHash(String newContentHash) {
		String oldContentHash = contentHash;
		contentHash = newContentHash;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.SOURCE_FILE__CONTENT_HASH, oldContentHash, contentHash));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CoverageSession getSession() {
		if (eContainerFeatureID() != CoveragePackage.SOURCE_FILE__SESSION) return null;
		return (CoverageSession)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSession(CoverageSession newSession, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newSession, CoveragePackage.SOURCE_FILE__SESSION, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSession(CoverageSession newSession) {
		if (newSession != eInternalContainer() || (eContainerFeatureID() != CoveragePackage.SOURCE_FILE__SESSION && newSession != null)) {
			if (EcoreUtil.isAncestor(this, newSession))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newSession != null)
				msgs = ((InternalEObject)newSession).eInverseAdd(this, CoveragePackage.COVERAGE_SESSION__FILES, CoverageSession.class, msgs);
			msgs = basicSetSession(newSession, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.SOURCE_FILE__SESSION, newSession, newSession));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CoverageElement> getElements() {
		if (elements == null) {
			elements = new EObjectContainmentEList<CoverageElement>(CoverageElement.class, this, CoveragePackage.SOURCE_FILE__ELEMENTS);
		}
		return elements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CoveragePackage.SOURCE_FILE__SESSION:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetSession((CoverageSession)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CoveragePackage.SOURCE_FILE__SESSION:
				return basicSetSession(null, msgs);
			case CoveragePackage.SOURCE_FILE__ELEMENTS:
				return ((InternalEList<?>)getElements()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case CoveragePackage.SOURCE_FILE__SESSION:
				return eInternalContainer().eInverseRemove(this, CoveragePackage.COVERAGE_SESSION__FILES, CoverageSession.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case CoveragePackage.SOURCE_FILE__URI:
				return getUri();
			case CoveragePackage.SOURCE_FILE__LANGUAGE:
				return getLanguage();
			case CoveragePackage.SOURCE_FILE__CONTENT_HASH:
				return getContentHash();
			case CoveragePackage.SOURCE_FILE__SESSION:
				return getSession();
			case CoveragePackage.SOURCE_FILE__ELEMENTS:
				return getElements();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case CoveragePackage.SOURCE_FILE__URI:
				setUri((String)newValue);
				return;
			case CoveragePackage.SOURCE_FILE__LANGUAGE:
				setLanguage((String)newValue);
				return;
			case CoveragePackage.SOURCE_FILE__CONTENT_HASH:
				setContentHash((String)newValue);
				return;
			case CoveragePackage.SOURCE_FILE__SESSION:
				setSession((CoverageSession)newValue);
				return;
			case CoveragePackage.SOURCE_FILE__ELEMENTS:
				getElements().clear();
				getElements().addAll((Collection<? extends CoverageElement>)newValue);
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
			case CoveragePackage.SOURCE_FILE__URI:
				setUri(URI_EDEFAULT);
				return;
			case CoveragePackage.SOURCE_FILE__LANGUAGE:
				setLanguage(LANGUAGE_EDEFAULT);
				return;
			case CoveragePackage.SOURCE_FILE__CONTENT_HASH:
				setContentHash(CONTENT_HASH_EDEFAULT);
				return;
			case CoveragePackage.SOURCE_FILE__SESSION:
				setSession((CoverageSession)null);
				return;
			case CoveragePackage.SOURCE_FILE__ELEMENTS:
				getElements().clear();
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
			case CoveragePackage.SOURCE_FILE__URI:
				return URI_EDEFAULT == null ? uri != null : !URI_EDEFAULT.equals(uri);
			case CoveragePackage.SOURCE_FILE__LANGUAGE:
				return LANGUAGE_EDEFAULT == null ? language != null : !LANGUAGE_EDEFAULT.equals(language);
			case CoveragePackage.SOURCE_FILE__CONTENT_HASH:
				return CONTENT_HASH_EDEFAULT == null ? contentHash != null : !CONTENT_HASH_EDEFAULT.equals(contentHash);
			case CoveragePackage.SOURCE_FILE__SESSION:
				return getSession() != null;
			case CoveragePackage.SOURCE_FILE__ELEMENTS:
				return elements != null && !elements.isEmpty();
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
		result.append(" (uri: ");
		result.append(uri);
		result.append(", language: ");
		result.append(language);
		result.append(", contentHash: ");
		result.append(contentHash);
		result.append(')');
		return result.toString();
	}

} //SourceFileImpl
