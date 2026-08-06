/*******************************************************************************
 * Copyright (c) 2008-2012 The University of York, Antonio Garc??a-Dom??nguez
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garc??a-Dom??nguez - clean up and improve error reporting
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed;

import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.StringUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.execute.operations.EolOperationFactory;
import org.eclipse.epsilon.eol.execute.operations.simple.SimpleOperation;

public abstract class EObjectImageTextProvider {

	public boolean showStructuralInfo = false;
	public EolModule module;
	protected PrintStream outputStream;
	protected PrintStream errorStream;
	
	public EObjectImageTextProvider(InMemoryEmfModel model, PrintStream outputStream, PrintStream errorStream, boolean showStructuralInfo) {
		this.showStructuralInfo = showStructuralInfo;
		this.outputStream = outputStream;
		this.errorStream = errorStream;
		
		module = new EolModule();
		module.getContext().getModelRepository().addModel(model);
		module.getContext().setOperationFactory(new EolOperationFactory() {
			{
				operationCache.put("label", new SimpleOperation() {
					@Override
					public Object execute(Object source, List<?> parameters, IEolContext context, ModuleElement ast) throws EolRuntimeException {
						return getEObjectReferenceLabel(source,getBasicEObjectLabel(source));
					}
	
					protected String getBasicEObjectLabel(Object object) {
						if (!(object instanceof EObject)) return "undefined";
						EObject eObject = (EObject) object;
						String label = eObject.eClass().getName();
						if (eObject.eClass().getEAllAttributes().size() > 0) {
							EAttribute attribute = eObject.eClass().getEAllAttributes().get(0);
							label = label + " " + eObject.eGet(attribute);
						}
						return label;
					}
					
				});
			}
		});
		
		//module.getContext().getIntrospectionManager().setDefaultPropertyGetter(new JavaEmfPropertyGetter());
		//module.getContext().getIntrospectionManager().setDefaultPropertySetter(new JavaEmfPropertySetter());
		module.getContext().setOutputStream(outputStream);
		module.getContext().setErrorStream(errorStream);
	}

	public String getEStructuralFeatureLabel(EStructuralFeature feature, String def) {
		EAnnotation eAnnotation = feature.getEAnnotation("exeed");
		if (eAnnotation != null) {
			Object detailValue = eAnnotation.getDetails().get("featureLabel");
			if (detailValue != null) {
				return detailValue.toString();
			}
		}
		return def;
	}

	public String getEObjectLabel(Object object, String def, boolean forReference) {
		if (!(object instanceof EObject)) return def;
		String labelCode = "";

		try {
			EObject eObject = (EObject) object;
			module.getContext().setErrorStream(errorStream);
			module.getContext().setOutputStream(outputStream);
			labelCode = getEClassAnnotationDetail(eObject, "exeed", "label");
			if (labelCode != null) {
				parse(labelCode);
				FrameStack frameStack = module.getContext().getFrameStack();
				frameStack.enterLocal(FrameType.UNPROTECTED, null,
					Variable.createReadOnlyVariable("self", eObject),
					Variable.createReadOnlyVariable("thisModule", module)
				);
				String label = StringUtil.toString(module.execute());
				frameStack.leaveLocal(null);
				return addStructuralInfo(object,label,forReference);
			}
		} catch (Exception e) {
			logException(e);
		}
		return addStructuralInfo(object, def, forReference);
	}
	
	public Collection<?> getChoiceOfValues(Object object, String filter, Collection<?> def) {
		try {
			parse(filter);
			FrameStack frameStack = module.getContext().getFrameStack();
			frameStack.putGlobal(Variable.createReadOnlyVariable("self", object));
			return (Collection<?>) module.execute();
		}
		catch (Exception ex) {
			logException(ex);
		}
		return def;
	}
	
	public String getEObjectReferenceLabel(Object object, String def) {
		if (!(object instanceof EObject)) return def;

		if (object instanceof EEnumLiteral) {
			return getEEnumLiteralLabel((EEnumLiteral)object);
		}

		try {
			EObject eObject = (EObject) object;
			String labelCode = getEClassAnnotationDetail(eObject, "exeed", "referenceLabel");
			if (labelCode != null) {
				parse(labelCode);
				FrameStack frameStack = module.getContext().getFrameStack();
				frameStack.enterLocal(FrameType.UNPROTECTED, null,
					Variable.createReadOnlyVariable("self", eObject)
				);
				String label = StringUtil.toString(module.execute());
				frameStack.leaveLocal(null);
				return label;
			}
		} catch (Exception e) {
			logException(e);
		}

		return getEObjectLabel(object, def, true);
	}	
	
	public String getEObjectImage(Object object, String def) {
		if (!(object instanceof EObject)) return def;

		String icon = "";
		try {
			EObject eObject = (EObject) object;
			FrameStack frameStack = module.getContext().getFrameStack();
			frameStack.put(Variable.createReadOnlyVariable("self", eObject));
			String labelCode = getEClassAnnotationDetail(eObject, "exeed", "icon");
			if (labelCode != null) {
				parse(labelCode);
				icon = StringUtil.toString(module.execute());
			}
			else {
				icon = getEClassImage(eObject.eClass() ,null);
			}

			if (icon != null) {
				return icon;
			}
		} catch (Exception e) {
			logException(e);
		}

		return def;
	}

	public String getEClassImage(EClass eClass, String def) {
		String icon = "";
		try {
			icon = getEClassAnnotationDetail(eClass, "exeed", "classIcon");

			if (icon != null) {
				return icon;
			}
		} catch (Exception e) {
			logException(e);
		}

		return def;
	}

	public boolean isShowStructuralInfo() {
		return showStructuralInfo;
	}

	public void setShowStructuralInfo(boolean showStructuralInfo) {
		this.showStructuralInfo = showStructuralInfo;
	}

	public String addStructuralInfo(Object object, String label, boolean forReference) {
		if (object instanceof EObject && !forReference && showStructuralInfo) {
			EObject eObject = (EObject) object;
			String suffix = " (" + eObject.eClass().getName();
			if (eObject.eContainingFeature() != null) {
				suffix = suffix + "@"
						+ eObject.eContainingFeature().getEContainingClass().getName()
						+ "." + eObject.eContainingFeature().getName();
			}
			label = label + suffix + ")";
		}
		return label;
	}

	public String getEEnumLiteralLabel(EEnumLiteral literal) {
		EAnnotation annotation = literal.getEAnnotation("exeed");
		if (annotation != null) {
			Object detail = annotation.getDetails().get("label");
			if (detail != null) {
				return detail.toString();
			}
		}
		return literal.getLiteral();
	}

	public String getEClassAnnotationDetail(EObject eObject, String annotation, String detail) {
		return getEClassAnnotationDetail(eObject.eClass(), annotation, detail);
	}

	public String getEClassAnnotationDetail(EClass eClass, String annotation, String detail) {
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

	public void parse(String code) throws Exception {
		if (!module.parse(code)) {
			final StringBuilder sb = new StringBuilder("The EOL label generation snippet produced several parsing errors: ");
			for (ParseProblem pp : module.getParseProblems()) {
				sb.append("\n - ");
				sb.append(pp.toString());
			}
			throw new EolRuntimeException(sb.toString());
		}
	}
	
	protected abstract void logException(Exception ex);
}
