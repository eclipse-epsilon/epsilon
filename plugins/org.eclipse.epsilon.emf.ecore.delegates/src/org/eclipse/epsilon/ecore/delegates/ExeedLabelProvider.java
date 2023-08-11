/*******************************************************************************
 * Copyright (c) 2023 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Horacio Hoyos Rodriguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.ecore.delegates;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator.SubstitutionLabelProvider;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.Variable;

/**
 * Use Exeed annotations, if present, to provide labels
 * 
 * @since 2.5
 */
public class ExeedLabelProvider implements DelegateLabelProvider {

	public ExeedLabelProvider() {
		this(new EolModule(), new SimpleProvider());
	}
	
	public ExeedLabelProvider(EolModule module, SubstitutionLabelProvider delegate) {
		super();
		this.module = module;
		this.delegate = delegate;
	}

	@Override
	public String getObjectLabel(EObject eObject) {
		var labelCode = getEClassAnnotationDetail(eObject, "exeed", "label");
		try {
			if (labelCode != null && parse(labelCode)) {
				FrameStack frameStack = module.getContext().getFrameStack();
				frameStack.enterLocal(FrameType.UNPROTECTED, null,
					Variable.createReadOnlyVariable("self", eObject),
					Variable.createReadOnlyVariable("thisModule", module)
				);
				String label = StringUtil.toString(module.execute());
				frameStack.leaveLocal(null);
				return label;
			}
		} catch (Exception e) {
			// TODO Do we report?
		}
		return this.delegate.getObjectLabel(eObject);
	}

	@Override
	public String getFeatureLabel(EStructuralFeature eStructuralFeature) {
		EAnnotation eAnnotation = eStructuralFeature.getEAnnotation("exeed");
		if (eAnnotation != null) {
			Object detailValue = eAnnotation.getDetails().get("featureLabel");
			if (detailValue != null) {
				return detailValue.toString();
			}
		}
		return this.delegate.getFeatureLabel(eStructuralFeature);
	}

	@Override
	public String getValueLabel(EDataType eDataType, Object value) {
		return this.delegate.getValueLabel(eDataType, value);
	}
	
	@Override
	public DelegateLabelProvider delegate(SubstitutionLabelProvider delegate) {
		return new ExeedLabelProvider(this.module, delegate);
	}
	
	private static class SimpleProvider implements SubstitutionLabelProvider {

		@Override
		public String getObjectLabel(EObject eObject) {
			 return EcoreUtil.getIdentification(eObject);
		}

		@Override
		public String getFeatureLabel(EStructuralFeature eStructuralFeature) {
			return eStructuralFeature.getName();
		}

		@Override
		public String getValueLabel(EDataType eDataType, Object value) {
			 return EcoreUtil.convertToString(eDataType, value);
		}
		
	}
	
	private final EolModule module;
	private final SubstitutionLabelProvider delegate;
	
	private boolean parse(String code) throws Exception {
		return this.module.parse(code);
	}
	
	private String getEClassAnnotationDetail(EObject eObject, String annotation, String detail) {
		return getEClassAnnotationDetail(eObject.eClass(), annotation, detail);
	}

	private String getEClassAnnotationDetail(EClass eClass, String annotation, String detail) {
		EAnnotation eAnnotation = eClass.getEAnnotation(annotation);
		if (eAnnotation != null) {
			Object detailValue = eAnnotation.getDetails().get(detail);
			if (detailValue != null) {
				return detailValue.toString();
			}
		}
		
		if (eClass.getESuperTypes().size() > 0) {
			for (EClass superType : eClass.getESuperTypes()) {
				String detailValue = getEClassAnnotationDetail(superType, annotation, detail);
				if (detailValue != null) {
					return detailValue;
				}
			}
		}
		
		return null;
		
	}

}
