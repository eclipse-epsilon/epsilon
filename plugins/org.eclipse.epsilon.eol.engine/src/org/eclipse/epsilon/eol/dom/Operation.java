/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.dom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.eol.exceptions.EolIllegalReturnException;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.Return;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.execute.operations.contributors.IterableOperationContributor;
import org.eclipse.epsilon.eol.parse.EolParser;
import org.eclipse.epsilon.eol.types.EolAnyType;
import org.eclipse.epsilon.eol.types.EolNoType;
import org.eclipse.epsilon.eol.types.EolType;

public class Operation extends AnnotatableModuleElement {
	
	protected NameExpression nameExpression;
	protected TypeExpression contextTypeExpression;
	protected TypeExpression returnTypeExpression;
	protected EolType contextType;
	protected EolType returnType;
	protected StatementBlock body;
	protected List<Parameter> formalParameters;
	protected boolean isCached;
	// If this operation cannot be cached, it will stay null, to save some memory.
	// Note that this cache needs to be thread-safe!
	protected Map<Object, Object> cache;
	
	//TODO: Add guards to helpers
	public Operation() {
		
	}
	
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		
		AST nameAst = null;
		if (cst.getFirstChild().getType() == EolParser.TYPE) {
			AST contextTypeExpressionAst = cst.getFirstChild();
			contextTypeExpression = (TypeExpression) module.createAst(contextTypeExpressionAst, this);
			nameAst = contextTypeExpressionAst.getNextSibling(); 
		}
		else {
			nameAst = cst.getFirstChild();
		}
		AST paramListAst = null;
		AST returnAst = null;
		AST bodyAst = null;
		
		if (nameAst.getNextSibling().getType() == EolParser.PARAMLIST) {
			paramListAst = nameAst.getNextSibling();
		}
		
		if (paramListAst != null) { // helper with parameters
			if (paramListAst.getNextSibling().getType() == EolParser.TYPE) { // with return type
				returnAst = paramListAst.getNextSibling();
				bodyAst = returnAst.getNextSibling();
			}
			else { // without return type
				bodyAst = paramListAst.getNextSibling();
			}
		}
		else { // helper without parameters
			if (nameAst.getNextSibling().getType() == EolParser.TYPE) { //with return type
				returnAst = nameAst.getNextSibling();
				bodyAst = returnAst.getNextSibling();
			}
			else { // without return type
				bodyAst = nameAst.getNextSibling();
			}
		}
		
		this.nameExpression = (NameExpression) module.createAst(nameAst, this);
		this.returnTypeExpression = (TypeExpression)  module.createAst(returnAst, this);
		this.body = (StatementBlock) module.createAst(bodyAst, this);
		
		if (paramListAst != null) {
			List<AST> paramListAstChildren = paramListAst.getChildren();
			formalParameters = new ArrayList<>(paramListAstChildren.size());
			for (AST formalParameterAst : paramListAst.getChildren()) {
				formalParameters.add((Parameter) module.createAst(formalParameterAst, this));
			}
		}
		else {
			formalParameters = Collections.emptyList();
		}

		if ((isCached = hasAnnotation("cached") && formalParameters.isEmpty()) == true) {
			this.cache = Collections.synchronizedMap(new WeakHashMap<>());
		}
	}
	
	public void clearCache() {
		if (isCached()) {
			cache.clear();
		}

		// This is important for EUnit, as it ensures that the cached
		// type information will be re-evaluated for the reloaded models
		contextType = returnType = null;
		for (Parameter formalParameter : formalParameters) {
			formalParameter.clearCache();
		}
	}
	
	@Override
	public String toString() {
		String contextTypeName = "";
		String returnTypeName = "";

		if (contextTypeExpression != null) {
			contextTypeName = " - " + contextTypeExpression.getName();// + ".";
		}
		if (returnTypeExpression != null) {
			returnTypeName = " : " + returnTypeExpression.getName();
		}
		
		try (IterableOperationContributor ioc = new IterableOperationContributor(formalParameters)) {
			return getName() + "(" + ioc.concat(", ") + ")" + returnTypeName + contextTypeName;
		}
	}

	public boolean isCached() {
		return isCached;
	}

	public Object execute(Object self, List<?> parameterValues, IEolContext context) throws EolRuntimeException {
		return execute(self, parameterValues, context, true);
	}
	
	public Object execute(Object self, List<?> parameterValues, IEolContext context, boolean inNewStackFrame) throws EolRuntimeException {
		final int paramSize = formalParameters.size();
		assert paramSize > 0 ? paramSize == parameterValues.size() : true;
		boolean cacheContainsSelf = isCached && cache.containsKey(self);

		if (cacheContainsSelf) {
			return cache.get(self);
		}
		
		FrameStack scope = context.getFrameStack();
		
		if (inNewStackFrame) {
			scope.enterLocal(FrameType.PROTECTED, this, Variable.createReadOnlyVariable("self", self));
		}
		
		Iterator<?> parameterValuesIter = parameterValues.iterator();
		for (Parameter fp : formalParameters) {
			scope.put(new Variable(fp.getName(), parameterValuesIter.next(), fp.getType(context)));
		}
		
		evaluatePreConditions(context);
		
		Object result = Return.getValue(executeBody(context));

		checkResultType(result, context);
		evaluatePostConditions(context, result);
		
		if (inNewStackFrame) {
			scope.leaveLocal(this);
		}
		
		// cache.containsKey(self) == false;
		if (isCached && !cacheContainsSelf) {
			cache.put(self, result);
		}
		
		return result;
	}

	protected Object executeBody(IEolContext context) throws EolRuntimeException {
		return context.getExecutorFactory().execute(this.getBody(), context);
	}
	
	protected void evaluatePreConditions(IEolContext context) throws EolRuntimeException {
		for (Annotation annotation : getAnnotations("pre")) {
			if (!(annotation instanceof ExecutableAnnotation)) continue;
			
			Object satisfied = ((ExecutableAnnotation)annotation).getValue(context);
			if (satisfied instanceof Boolean) {
				if (!(boolean) satisfied) {
					throw new EolRuntimeException("Pre-condition not satisfied", annotation);
				}
			}
			else {
				throw new EolIllegalReturnException("Boolean", satisfied, annotation, context);
			}
		}
	}
	
	protected void checkResultType(Object result, IEolContext context) throws EolRuntimeException {
		if (returnTypeExpression != null && result != null) {
			if (returnType == null) {
				returnType = (EolType) context.getExecutorFactory().execute(returnTypeExpression, context);
			}
			if (!returnType.isKind(result)) {
				throw new EolRuntimeException(getName() + " is expected to return a " + returnType.getName() + ", but returned a " + result.getClass().getCanonicalName());
			}
		}
	}

	protected void evaluatePostConditions(IEolContext context, Object result) throws EolRuntimeException {
		FrameStack frameStack = context.getFrameStack();
		frameStack.put(Variable.createReadOnlyVariable("_result", result));
		for (Annotation annotation : getAnnotations("post")) {
			if (!(annotation instanceof ExecutableAnnotation)) continue;
			
			Object satisfied = ((ExecutableAnnotation) annotation).getValue(context);
			if (satisfied instanceof Boolean) {
				if (!(boolean) satisfied) {
					// We can simply use the frame stack here, as we created a frame for the post-condition
					// in order to isolate the _result variable.
					throw new EolRuntimeException(
						"Post-condition not satisfied: _result was "
								+ context.getPrettyPrinterManager().print(result));
				}
			}
			else {
				throw new EolIllegalReturnException("Boolean", satisfied, annotation, context);
			}
		}
	}
	
	public EolType getReturnType(IEolContext context) throws EolRuntimeException{
		if (returnType == null) {
			if (returnTypeExpression != null) {
				returnType = (EolType) context.getExecutorFactory().execute(returnTypeExpression, context);
			}
			else {
				returnType = EolAnyType.Instance;
			}
		}
		return returnType;
	}
	
	public EolType getContextType(IEolContext context) throws EolRuntimeException {
		if (contextType == null) {
			if (contextTypeExpression != null) {
				contextType = (EolType) context.getExecutorFactory().execute(contextTypeExpression, context);
			}
			else {
				contextType = EolNoType.Instance;
			}
		}
		return contextType;
	}
	
	public String getName() {
		return nameExpression.getName();
	}
	
	public List<Parameter> getFormalParameters() {
		return formalParameters;
	}
	
	public StatementBlock getBody() {
		return body;
	}
	
	public void setBody(StatementBlock body) {
		this.body = body;
	}
	
	public NameExpression getNameExpression() {
		return nameExpression;
	}
	
	public void setNameExpression(NameExpression nameExpression) {
		this.nameExpression = nameExpression;
	}
	
	public TypeExpression getContextTypeExpression() {
		return contextTypeExpression;
	}
	
	public void setContextTypeExpression(TypeExpression contextTypeExpression) {
		this.contextTypeExpression = contextTypeExpression;
	}
	
	public TypeExpression getReturnTypeExpression() {
		return returnTypeExpression;
	}
	
	public void setReturnTypeExpression(TypeExpression returnTypeExpression) {
		this.returnTypeExpression = returnTypeExpression;
	}
	
	public void accept(IEolVisitor visitor) {
		visitor.visit(this);
	}
}
