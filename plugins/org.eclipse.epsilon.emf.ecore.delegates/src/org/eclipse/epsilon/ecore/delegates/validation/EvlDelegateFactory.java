/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Horacio Hoyos Rodriguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecore.delegates.validation;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.ecore.delegates.DelegateContext.ContextFactory;
import org.eclipse.epsilon.ecore.delegates.DelegateLabelProvider;
import org.eclipse.epsilon.ecore.delegates.ExeedLabelProvider;
import org.eclipse.epsilon.ecore.delegates.invocation.InvocationBehavior;
import org.eclipse.epsilon.ecore.delegates.notify.Adapters;
import org.eclipse.epsilon.ecore.delegates.notify.DelegateEClassifierAdapter;
import org.eclipse.epsilon.ecore.delegates.validation.EvlDelegate.Factory;

/**
 * Delegates validation to the {@link EvlDelegate} associated with the respective EClassifier
 * 
 * @since 2.5
 */
public class EvlDelegateFactory implements EvlDelegate.Factory, EValidator.ValidationDelegate {

	public EvlDelegateFactory() {
		this("http://eclipse.dev/epsilon/ecore/EVL",
			new ContextFactory.Registry.Fast(),
			new Factory.Registry.Delegate());
	}

	public EvlDelegateFactory(
		final String delegateURI,
		final ContextFactory.Registry domainRegistry,
		final Factory.Registry delegateRegistry) {
		this.delegateURI = delegateURI;
		this.adapters = new Adapters(delegateURI, new EvlDelegateContextFactory(), domainRegistry, delegateRegistry);
		this.valBehavior = new EvlDelegates(delegateRegistry, this.adapters);
		this.invBehavior = new InvocationBehavior();
		this.labelProvider = new ExeedLabelProvider();
	}
	
	@Override
	public boolean validate(EClass eClass, EObject eObject, Map<Object, Object> context, EOperation invariant,
			String expression) {
		if (eClass.getEAnnotation(this.delegateURI) != null) {
			EvlDelegate validationDelegate = getValidationDelegate(eClass);
			return validationDelegate.validate(eClass, eObject, context, invariant, expression);
		}
		return true;
	}

	@Override
	public boolean validate(EClass eClass, EObject eObject, Map<Object, Object> context, String constraint,
			String expression) {
		if (eClass.getEAnnotation(this.delegateURI) != null) {
			EvlDelegate validationDelegate = getValidationDelegate(eClass);
			return validationDelegate.validate(eClass, eObject, context, constraint, expression);
		}
		return true;
	}

	@Override
	public boolean validate(EDataType eDataType, Object value, Map<Object, Object> context, String constraint,
			String expression) {
		if (eDataType.getEAnnotation(this.delegateURI) != null) {
			EvlDelegate validationDelegate = getValidationDelegate(eDataType);
			return validationDelegate.validate(eDataType, value, context, constraint, expression);
		}
		return true;
	}

	@Override
	public EvlDelegate createValidationDelegate(EClassifier classifier) {
		EPackage ePackage = classifier.getEPackage();
		return new BasicEvlDelegate(getDelegateDomain(ePackage), this.labelProvider);
	}
	
	@Override
	public String getURI() {
		return this.delegateURI;
	}

	private final EvlDelegates valBehavior;
	private final InvocationBehavior invBehavior;
	private final String delegateURI;
	private final Adapters adapters;
	private final DelegateLabelProvider labelProvider;
	
	private EvlDelegateContext getDelegateDomain(EPackage ePackage) {
		return (EvlDelegateContext) this.adapters.getAdapter(ePackage).getDelegateDomain();
	}
	
	private EvlDelegate getValidationDelegate(EClassifier eClassifier) {
		return this.findAdapter(eClassifier).getValidationDelegate();
	}
	
	private DelegateEClassifierAdapter findAdapter(EClassifier eClassifier) {
		DelegateEClassifierAdapter adapter = (DelegateEClassifierAdapter) EcoreUtil
			.getAdapter(eClassifier.eAdapters(), DelegateEClassifierAdapter.class);
		if (adapter == null) {
			adapter = new DelegateEClassifierAdapter(this.valBehavior, this.invBehavior);
			eClassifier.eAdapters().add(adapter);
		}
		return adapter;
	}
	
	
}
