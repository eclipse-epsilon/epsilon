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
import org.eclipse.epsilon.ecore.delegates.EvlDelegateContext;
import org.eclipse.epsilon.ecore.delegates.EvlDelegateContextFactory;
import org.eclipse.epsilon.ecore.delegates.ExeedLabelProvider;
import org.eclipse.epsilon.ecore.delegates.notify.Adapters;
import org.eclipse.epsilon.ecore.delegates.notify.EpsilonDelegatesAdapter;
import org.eclipse.epsilon.ecore.delegates.notify.EvlAdapters;
import org.eclipse.epsilon.ecore.delegates.validation.EpsilonValidationDelegate.Factory;

/**
 * Delegates validation to the {@link EpsilonValidationDelegate} associated with the respective
 * EClassifier
 * 
 * @since 2.5
 */
public class DelegateFactory
		implements EpsilonValidationDelegate.Factory, EValidator.ValidationDelegate {

	public DelegateFactory() {
		this(new ValidationUri(),
			new ContextFactory.Registry.Fast(),
			new Factory.Registry.Smart());
	}

	public DelegateFactory(
		ValidationUri delegateUri,
		ContextFactory.Registry domainRegistry,
		Factory.Registry delegateRegistry) {
		this.delegateUri = delegateUri;
		this.delegateRegistry = delegateRegistry;
		this.adapters = new EvlAdapters(
				this.delegateUri,
				new EvlDelegateContextFactory(),
				domainRegistry,
				delegateRegistry,
				this);
		this.labelProvider = new ExeedLabelProvider();
		this.delegates = new EvlDelegates(this.delegateUri, this.delegateRegistry, this.adapters);
	}
	
	@Override
	public boolean validate(EClass eClass, EObject eObject, Map<Object, Object> context, EOperation invariant,
			String expression) {
		if (this.delegateUri.isUsedBy(eClass)) {
			EpsilonValidationDelegate validationDelegate = validationDelegate(eClass);
			return validationDelegate.validate(eClass, eObject, context, invariant, expression);
		}
		return true;
	}

	@Override
	public boolean validate(EClass eClass, EObject eObject, Map<Object, Object> context, String constraint,
			String expression) {
		if (this.delegateUri.isUsedBy(eClass)) {
			EpsilonValidationDelegate validationDelegate = validationDelegate(eClass);
			return validationDelegate.validate(eClass, eObject, context, constraint, expression);
		}
		return true;
	}

	@Override
	public boolean validate(EDataType eDataType, Object value, Map<Object, Object> context, String constraint,
			String expression) {
		if (this.delegateUri.isUsedBy(eDataType)) {
			EpsilonValidationDelegate validationDelegate = validationDelegate(eDataType);
			return validationDelegate.validate(eDataType, value, context, constraint, expression);
		}
		return true;
	}

	@Override
	public EpsilonValidationDelegate createValidationDelegate(EClassifier classifier) {
		return new EvlValidationDelegate(delegateContext(classifier.getEPackage()), this.labelProvider);
	}
	
	private final ValidationUri delegateUri;
	private final Adapters adapters;
	private final DelegateLabelProvider labelProvider;
	private final Factory.Registry delegateRegistry;
	private final EvlDelegates delegates;
	
	private EvlDelegateContext delegateContext(EPackage ePackage) {
		return (EvlDelegateContext) this.delegateUri.context(this.adapters.getAdapter(ePackage));
	}
	
	private EpsilonValidationDelegate validationDelegate(EClassifier eClassifier) {
		return this.findAdapter(eClassifier).validationDelegate();
	}
	
	private EpsilonDelegatesAdapter findAdapter(EClassifier eClassifier) {
		EpsilonDelegatesAdapter adapter = (EpsilonDelegatesAdapter) EcoreUtil
			.getAdapter(eClassifier.eAdapters(), EpsilonDelegatesAdapter.class);
		if (adapter == null) {
			adapter = new EpsilonDelegatesAdapter();
			eClassifier.eAdapters().add(adapter);
		}
		if (!adapter.hasValidationDelegate()) {
			adapter.useValidationDelegate(this.delegates.create(eClassifier));
		}
		return adapter;
	}
	
	
}
