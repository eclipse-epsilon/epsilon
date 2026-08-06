/*******************************************************************************
 * Copyright (c) 2008-2013 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed;

import java.util.Collection;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;

public class ExeedItemPropertyDescriptor extends ItemPropertyDescriptor {

	protected ExeedImageTextProvider imageTextProvider;

	public ExeedItemPropertyDescriptor(ComposeableAdapterFactory rootAdapterFactory, String featureText, String string, EStructuralFeature feature, boolean b, Object imageForEType,
			ExeedImageTextProvider imageTextProvider) {
		super(rootAdapterFactory, featureText, string, feature, b, imageForEType);
		this.imageTextProvider = imageTextProvider;
		fixMultiLine(feature);
		this.sortChoices = true;
	}
	
	@Override
	public Collection<?> getChoiceOfValues(Object object) {
		String filter = getEStructuralFeatureAnnotationDetail(feature, "exeed", "filter");
		if (filter != null) {
			return imageTextProvider.getChoiceOfValues(object, filter, super.getChoiceOfValues(object));
		}
		else {
			return super.getChoiceOfValues(object);
		}
	}
	
	@Override
	public IItemLabelProvider getLabelProvider(Object thisObject) {
		return new ExeedItemLabelProvider(super.getLabelProvider(thisObject));
	}

	protected void fixMultiLine(EStructuralFeature feature) {
		String detail = getEStructuralFeatureAnnotationDetail(feature, "exeed", "multiLine");
		if (detail == null)
			return;
		if (detail.toString().equalsIgnoreCase("true")) {
			this.multiLine = true;
		}
	}

	protected String getEStructuralFeatureAnnotationDetail(EStructuralFeature structuralFeature, String annotation, String detail) {
		EAnnotation eAnnotation = feature.getEAnnotation(annotation);
		if (eAnnotation == null)
			return null;
		Object detailValue = eAnnotation.getDetails().get(detail);
		if (detailValue == null) {
			return null;
		} else {
			return detailValue.toString();
		}
	}
	
	class ExeedItemLabelProvider implements IItemLabelProvider {

		IItemLabelProvider fallBack;

		public ExeedItemLabelProvider(IItemLabelProvider fallBack) {
			this.fallBack = fallBack;
		}

		public Object getImage(Object object) {
			Object result = imageTextProvider.getEObjectImageDescriptor(object, null);
			if (result == null) {
				return fallBack.getImage(object);
			} else {
				return result;
			}
		}

		public String getText(Object object) {
			return imageTextProvider.getEObjectReferenceLabel(object, fallBack.getText(object));
		}

	}

}
