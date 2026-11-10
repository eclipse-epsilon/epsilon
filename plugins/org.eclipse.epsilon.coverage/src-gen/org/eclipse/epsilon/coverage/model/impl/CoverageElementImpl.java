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

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.epsilon.coverage.model.CoverageElement;
import org.eclipse.epsilon.coverage.model.CoverageElementType;
import org.eclipse.epsilon.coverage.model.CoveragePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.CoverageElementImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.CoverageElementImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.CoverageElementImpl#getStartLine <em>Start Line</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.CoverageElementImpl#getStartColumn <em>Start Column</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.CoverageElementImpl#getEndLine <em>End Line</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.CoverageElementImpl#getEndColumn <em>End Column</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.CoverageElementImpl#isProbe <em>Probe</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.CoverageElementImpl#getHits <em>Hits</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.CoverageElementImpl#getChildren <em>Children</em>}</li>
 *   <li>{@link org.eclipse.epsilon.coverage.model.impl.CoverageElementImpl#getParent <em>Parent</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CoverageElementImpl extends MinimalEObjectImpl.Container implements CoverageElement {
	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected CoverageElementType type;

	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getStartLine() <em>Start Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartLine()
	 * @generated
	 * @ordered
	 */
	protected static final int START_LINE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStartLine() <em>Start Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartLine()
	 * @generated
	 * @ordered
	 */
	protected int startLine = START_LINE_EDEFAULT;

	/**
	 * The default value of the '{@link #getStartColumn() <em>Start Column</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartColumn()
	 * @generated
	 * @ordered
	 */
	protected static final int START_COLUMN_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStartColumn() <em>Start Column</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartColumn()
	 * @generated
	 * @ordered
	 */
	protected int startColumn = START_COLUMN_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndLine() <em>End Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndLine()
	 * @generated
	 * @ordered
	 */
	protected static final int END_LINE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getEndLine() <em>End Line</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndLine()
	 * @generated
	 * @ordered
	 */
	protected int endLine = END_LINE_EDEFAULT;

	/**
	 * The default value of the '{@link #getEndColumn() <em>End Column</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndColumn()
	 * @generated
	 * @ordered
	 */
	protected static final int END_COLUMN_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getEndColumn() <em>End Column</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndColumn()
	 * @generated
	 * @ordered
	 */
	protected int endColumn = END_COLUMN_EDEFAULT;

	/**
	 * The default value of the '{@link #isProbe() <em>Probe</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isProbe()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PROBE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isProbe() <em>Probe</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isProbe()
	 * @generated
	 * @ordered
	 */
	protected boolean probe = PROBE_EDEFAULT;

	/**
	 * The default value of the '{@link #getHits() <em>Hits</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHits()
	 * @generated
	 * @ordered
	 */
	protected static final long HITS_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getHits() <em>Hits</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHits()
	 * @generated
	 * @ordered
	 */
	protected long hits = HITS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<CoverageElement> children;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CoverageElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CoveragePackage.Literals.COVERAGE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CoverageElementType getType() {
		if (type != null && type.eIsProxy()) {
			InternalEObject oldType = (InternalEObject)type;
			type = (CoverageElementType)eResolveProxy(oldType);
			if (type != oldType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CoveragePackage.COVERAGE_ELEMENT__TYPE, oldType, type));
			}
		}
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CoverageElementType basicGetType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(CoverageElementType newType) {
		CoverageElementType oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.COVERAGE_ELEMENT__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.COVERAGE_ELEMENT__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getStartLine() {
		return startLine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStartLine(int newStartLine) {
		int oldStartLine = startLine;
		startLine = newStartLine;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.COVERAGE_ELEMENT__START_LINE, oldStartLine, startLine));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getStartColumn() {
		return startColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStartColumn(int newStartColumn) {
		int oldStartColumn = startColumn;
		startColumn = newStartColumn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.COVERAGE_ELEMENT__START_COLUMN, oldStartColumn, startColumn));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getEndLine() {
		return endLine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEndLine(int newEndLine) {
		int oldEndLine = endLine;
		endLine = newEndLine;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.COVERAGE_ELEMENT__END_LINE, oldEndLine, endLine));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getEndColumn() {
		return endColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEndColumn(int newEndColumn) {
		int oldEndColumn = endColumn;
		endColumn = newEndColumn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.COVERAGE_ELEMENT__END_COLUMN, oldEndColumn, endColumn));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isProbe() {
		return probe;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setProbe(boolean newProbe) {
		boolean oldProbe = probe;
		probe = newProbe;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.COVERAGE_ELEMENT__PROBE, oldProbe, probe));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public long getHits() {
		return hits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHits(long newHits) {
		long oldHits = hits;
		hits = newHits;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.COVERAGE_ELEMENT__HITS, oldHits, hits));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<CoverageElement> getChildren() {
		if (children == null) {
			children = new EObjectContainmentWithInverseEList<CoverageElement>(CoverageElement.class, this, CoveragePackage.COVERAGE_ELEMENT__CHILDREN, CoveragePackage.COVERAGE_ELEMENT__PARENT);
		}
		return children;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public CoverageElement getParent() {
		if (eContainerFeatureID() != CoveragePackage.COVERAGE_ELEMENT__PARENT) return null;
		return (CoverageElement)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParent(CoverageElement newParent, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newParent, CoveragePackage.COVERAGE_ELEMENT__PARENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setParent(CoverageElement newParent) {
		if (newParent != eInternalContainer() || (eContainerFeatureID() != CoveragePackage.COVERAGE_ELEMENT__PARENT && newParent != null)) {
			if (EcoreUtil.isAncestor(this, newParent))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParent != null)
				msgs = ((InternalEObject)newParent).eInverseAdd(this, CoveragePackage.COVERAGE_ELEMENT__CHILDREN, CoverageElement.class, msgs);
			msgs = basicSetParent(newParent, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CoveragePackage.COVERAGE_ELEMENT__PARENT, newParent, newParent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CoveragePackage.COVERAGE_ELEMENT__CHILDREN:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getChildren()).basicAdd(otherEnd, msgs);
			case CoveragePackage.COVERAGE_ELEMENT__PARENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetParent((CoverageElement)otherEnd, msgs);
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
			case CoveragePackage.COVERAGE_ELEMENT__CHILDREN:
				return ((InternalEList<?>)getChildren()).basicRemove(otherEnd, msgs);
			case CoveragePackage.COVERAGE_ELEMENT__PARENT:
				return basicSetParent(null, msgs);
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
			case CoveragePackage.COVERAGE_ELEMENT__PARENT:
				return eInternalContainer().eInverseRemove(this, CoveragePackage.COVERAGE_ELEMENT__CHILDREN, CoverageElement.class, msgs);
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
			case CoveragePackage.COVERAGE_ELEMENT__TYPE:
				if (resolve) return getType();
				return basicGetType();
			case CoveragePackage.COVERAGE_ELEMENT__LABEL:
				return getLabel();
			case CoveragePackage.COVERAGE_ELEMENT__START_LINE:
				return getStartLine();
			case CoveragePackage.COVERAGE_ELEMENT__START_COLUMN:
				return getStartColumn();
			case CoveragePackage.COVERAGE_ELEMENT__END_LINE:
				return getEndLine();
			case CoveragePackage.COVERAGE_ELEMENT__END_COLUMN:
				return getEndColumn();
			case CoveragePackage.COVERAGE_ELEMENT__PROBE:
				return isProbe();
			case CoveragePackage.COVERAGE_ELEMENT__HITS:
				return getHits();
			case CoveragePackage.COVERAGE_ELEMENT__CHILDREN:
				return getChildren();
			case CoveragePackage.COVERAGE_ELEMENT__PARENT:
				return getParent();
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
			case CoveragePackage.COVERAGE_ELEMENT__TYPE:
				setType((CoverageElementType)newValue);
				return;
			case CoveragePackage.COVERAGE_ELEMENT__LABEL:
				setLabel((String)newValue);
				return;
			case CoveragePackage.COVERAGE_ELEMENT__START_LINE:
				setStartLine((Integer)newValue);
				return;
			case CoveragePackage.COVERAGE_ELEMENT__START_COLUMN:
				setStartColumn((Integer)newValue);
				return;
			case CoveragePackage.COVERAGE_ELEMENT__END_LINE:
				setEndLine((Integer)newValue);
				return;
			case CoveragePackage.COVERAGE_ELEMENT__END_COLUMN:
				setEndColumn((Integer)newValue);
				return;
			case CoveragePackage.COVERAGE_ELEMENT__PROBE:
				setProbe((Boolean)newValue);
				return;
			case CoveragePackage.COVERAGE_ELEMENT__HITS:
				setHits((Long)newValue);
				return;
			case CoveragePackage.COVERAGE_ELEMENT__CHILDREN:
				getChildren().clear();
				getChildren().addAll((Collection<? extends CoverageElement>)newValue);
				return;
			case CoveragePackage.COVERAGE_ELEMENT__PARENT:
				setParent((CoverageElement)newValue);
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
			case CoveragePackage.COVERAGE_ELEMENT__TYPE:
				setType((CoverageElementType)null);
				return;
			case CoveragePackage.COVERAGE_ELEMENT__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case CoveragePackage.COVERAGE_ELEMENT__START_LINE:
				setStartLine(START_LINE_EDEFAULT);
				return;
			case CoveragePackage.COVERAGE_ELEMENT__START_COLUMN:
				setStartColumn(START_COLUMN_EDEFAULT);
				return;
			case CoveragePackage.COVERAGE_ELEMENT__END_LINE:
				setEndLine(END_LINE_EDEFAULT);
				return;
			case CoveragePackage.COVERAGE_ELEMENT__END_COLUMN:
				setEndColumn(END_COLUMN_EDEFAULT);
				return;
			case CoveragePackage.COVERAGE_ELEMENT__PROBE:
				setProbe(PROBE_EDEFAULT);
				return;
			case CoveragePackage.COVERAGE_ELEMENT__HITS:
				setHits(HITS_EDEFAULT);
				return;
			case CoveragePackage.COVERAGE_ELEMENT__CHILDREN:
				getChildren().clear();
				return;
			case CoveragePackage.COVERAGE_ELEMENT__PARENT:
				setParent((CoverageElement)null);
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
			case CoveragePackage.COVERAGE_ELEMENT__TYPE:
				return type != null;
			case CoveragePackage.COVERAGE_ELEMENT__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case CoveragePackage.COVERAGE_ELEMENT__START_LINE:
				return startLine != START_LINE_EDEFAULT;
			case CoveragePackage.COVERAGE_ELEMENT__START_COLUMN:
				return startColumn != START_COLUMN_EDEFAULT;
			case CoveragePackage.COVERAGE_ELEMENT__END_LINE:
				return endLine != END_LINE_EDEFAULT;
			case CoveragePackage.COVERAGE_ELEMENT__END_COLUMN:
				return endColumn != END_COLUMN_EDEFAULT;
			case CoveragePackage.COVERAGE_ELEMENT__PROBE:
				return probe != PROBE_EDEFAULT;
			case CoveragePackage.COVERAGE_ELEMENT__HITS:
				return hits != HITS_EDEFAULT;
			case CoveragePackage.COVERAGE_ELEMENT__CHILDREN:
				return children != null && !children.isEmpty();
			case CoveragePackage.COVERAGE_ELEMENT__PARENT:
				return getParent() != null;
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
		result.append(" (label: ");
		result.append(label);
		result.append(", startLine: ");
		result.append(startLine);
		result.append(", startColumn: ");
		result.append(startColumn);
		result.append(", endLine: ");
		result.append(endLine);
		result.append(", endColumn: ");
		result.append(endColumn);
		result.append(", probe: ");
		result.append(probe);
		result.append(", hits: ");
		result.append(hits);
		result.append(')');
		return result.toString();
	}

} //CoverageElementImpl
