/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.dom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.module.AbstractModuleElement;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.exceptions.EolIllegalReturnException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;

public abstract class AnnotatableModuleElement extends AbstractModuleElement {
	
	protected AnnotationBlock annotationBlock;
	
	public AnnotationBlock getAnnotationBlock() {
		return annotationBlock;
	}
	
	public void setAnnotationBlock(AnnotationBlock annotationBlock) {
		this.annotationBlock = annotationBlock;
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		annotationBlock = (AnnotationBlock) module.createAst(cst.getAnnotationsAst(), this);
	}
	
	public boolean hasAnnotation(String name) {
		return getAnnotation(name) != null;
	}
	
	protected List<Annotation> getAnnotations(String name) {
		if (annotationBlock == null)
			return Collections.emptyList();
		
		return annotationBlock.getAnnotations()
				.stream()
				.filter(annotation -> annotation.getName().equals(name))
				.collect(Collectors.toList());
	}
	
	public Annotation getAnnotation(String name) {
		if (annotationBlock == null)
			return null;
		
		for (Annotation annotation : annotationBlock.getAnnotations()) {
			if (annotation.getName().equals(name))
				return annotation;
		}
		return null;
	}
	
	public boolean getBooleanAnnotationValue(String name, IEolContext context) throws EolRuntimeException {
		return getBooleanAnnotationValue(name, context, false, true);
	}
	
	public boolean getBooleanAnnotationValue(String name, IEolContext context, boolean ifNotExists, boolean ifNoValue) throws EolRuntimeException {
		Annotation annotation = getAnnotation(name);
		if (annotation == null) return ifNotExists;
		if (!annotation.hasValue()) return ifNoValue;
		
		Object result = null;
		if (annotation instanceof SimpleAnnotation)
			result = ((SimpleAnnotation) annotation).getValue();
		else if (annotation instanceof ExecutableAnnotation)
			result = ((ExecutableAnnotation) annotation).getValue(context);
		
		try {
			return Boolean.parseBoolean(result.toString());
		}
		catch (Exception ex) {
			throw new EolIllegalReturnException("Boolean", result, annotation, context);
		}
	}
	
	/**
	 * 
	 * @param name
	 * @param context
	 * @param variables
	 * @return
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	public boolean getBooleanAnnotationValue(String name, IEolContext context, Variable... variables) throws EolRuntimeException {
		return getBooleanAnnotationValue(name, context, () -> variables);
	}
	
	/**
	 * 
	 * @param name
	 * @param context
	 * @param variables
	 * @return
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	public boolean getBooleanAnnotationValue(String name, IEolContext context, Supplier<? extends Variable[]> variables) throws EolRuntimeException {	
		Annotation annotation = getAnnotation(name);
		if (annotation != null) {
			if (annotation instanceof ExecutableAnnotation && annotation.hasValue()) {
				FrameStack frameStack = context.getFrameStack();
				frameStack.enterLocal(FrameType.PROTECTED, annotation, variables != null ? variables.get() : new Variable[0]);
				Object result = annotation.getValue(context);
				frameStack.leaveLocal(annotation);
				return result instanceof Boolean && (boolean) result;
			}
			else return true;
		}
		else return false;
	}
	
	public List<Object> getAnnotationsValues(String name, IEolContext context) throws EolRuntimeException {
		List<Annotation> annotations = getAnnotations(name);
		List<Object> values = new ArrayList<>(annotations.size());
		
		for (Annotation annotation : annotations) {
			values.add(annotation.getValue(context));
		}
		
		return values;
	}
	
	public void accept(IEolVisitor visitor) {
		//TODO: Make this abstract
	};
	
}
