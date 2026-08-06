/*******************************************************************************
 * Copyright (c) 2008-2019 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - aggregate into select(...)
 *     Sina Madani - Parameterised execution
 ******************************************************************************/
package org.eclipse.epsilon.evl.dom;

import java.util.*;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.dom.AnnotatableModuleElement;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElementParameter;
import org.eclipse.epsilon.eol.dom.TypeExpression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelElementTypeNotFoundException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelNotFoundException;
import org.eclipse.epsilon.eol.execute.ExecutorFactory;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolModelElementType;
import org.eclipse.epsilon.evl.execute.context.IEvlContext;
import org.eclipse.epsilon.evl.parse.EvlParser;

public class ConstraintContext extends AnnotatableModuleElement implements IExecutableModuleElement, IExecutableModuleElementParameter {
	
	protected final ArrayList<Constraint> constraints = new ArrayList<>(0);
	protected TypeExpression typeExpression;
	protected ExecutableBlock<Boolean> guardBlock;
	protected EolModelElementType type;
	/**
	 * @since 1.6
	 */
	Boolean isLazy;
	
	@SuppressWarnings("unchecked")
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		
		typeExpression = (TypeExpression) module.createAst(cst.getFirstChild(), this);
		guardBlock = (ExecutableBlock<Boolean>) module.createAst(AstUtil.getChild(cst, EvlParser.GUARD), this);
		
		List<AST> constraintASTs = AstUtil.getChildren(cst, EvlParser.CONSTRAINT, EvlParser.CRITIQUE);
		constraints.ensureCapacity(constraintASTs.size());
		
		for (AST constraintAst : constraintASTs) {
			Constraint constraint = (Constraint) module.createAst(constraintAst, this);
			constraint.setConstraintContext(this);
			constraints.add(constraint);
		}
	}
	
	/**
	 * 
	 * @param modelElement
	 * @param context
	 * @return
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	public boolean shouldBeChecked(Object modelElement, IEvlContext context) throws EolRuntimeException {
		return !isLazy(context) && appliesTo(modelElement, context);
	}
	
	/**
	 * Checks if the given model element is an instance of the type described by this ConstraintContext.
	 * 
	 * @param modelElement The model element.
	 * @param context The execution context containing the model(s).
	 * @return <code>true</code> if the object's type is equal to this ConstraintContext's type.
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	public boolean isOfSourceType(Object modelElement, IEvlContext context) throws EolRuntimeException {
		return context.getModelRepository().getOwningModel(modelElement).isOfType(modelElement, getType(context).getTypeName());
	}
	
	/**
	 * Checks if the given model element is an instance of the kind described by this ConstraintContext.
	 * 
	 * @param modelElement The model element.
	 * @param context The execution context containing the model(s).
	 * @return <code>true</code> if the model element is compatible with this ConstraintContext.
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	public boolean isOfSourceKind(Object modelElement, IEvlContext context) throws EolRuntimeException {
		return context.getModelRepository().getOwningModel(modelElement).isOfKind(modelElement, getType(context).getTypeName());
	}
	
	public final boolean appliesTo(Object object, IEvlContext context) throws EolRuntimeException {
		return appliesTo(object, context, false);
	}

	public boolean appliesTo(Object object, IEvlContext context, boolean checkType) throws EolRuntimeException {
		if (checkType && !isOfSourceType(object, context))
			return false;

		if (guardBlock != null)
			return guardBlock.execute(context, Variable.createReadOnlyVariable("self", object));
		
		return true;
	}
	
	/**
	 * An entire context is lazy if all constraints are lazy, or if it is
	 * itself marked as lazy.
	 * @throws EolRuntimeException
	 */
	public boolean isLazy(IEvlContext context) throws EolRuntimeException {
		if (isLazy == null) synchronized (this) {
			// Could've changed while we were waiting for the lock
			if (isLazy != null) return isLazy;
			
			boolean lazy = getBooleanAnnotationValue("lazy", context);	// Will be proven false in the loop below if this is not the case
			if (!lazy && !constraints.isEmpty()) {
				lazy = true;
				for (
					Iterator<Constraint> iter = constraints.iterator();
					lazy && iter.hasNext();	// Stop evaluating once we've found a Constraint that is not lazy
					lazy = iter.next().isLazy(context)
				);
			}
			isLazy = lazy;
		}
		return isLazy;
	}
	
	public String getTypeName() {
		return typeExpression != null ? typeExpression.getName() : null;
	}
	
	public List<Constraint> getConstraints() {
		return constraints;
	}
	
	public TypeExpression getTypeExpression() {
		return typeExpression;
	}
	
	public Collection<?> getAllOfSourceType(IEolContext context) throws EolModelElementTypeNotFoundException, EolModelNotFoundException {
		return getType(context).getAllOfType();
	}

	public Collection<?> getAllOfSourceKind(IEolContext context) throws EolModelElementTypeNotFoundException, EolModelNotFoundException {
		return getType(context).getAllOfKind();
	}

	public EolModelElementType getType(IEolContext context) throws EolModelNotFoundException, EolModelElementTypeNotFoundException {
		if (type == null) synchronized (this) {
			if (type == null) {
				type = new EolModelElementType(getTypeName(), context);
			}
		}
		return type;
	}

	/**
	 * Executes all constraints for the given model element, provided it is applicable to this
	 * ConstraintContext and that this ConstraintContext is not lazy.
	 * @param constraintsToCheck The constraints, which may be a subset of this ConstraintContext's children.
	 * @param modelElement The model element object.
	 * @param context The execution context.
	 * @throws EolRuntimeException
	 * @return Whether the constraints were checked.
	 * @since 1.6
	 */
	public boolean execute(Collection<Constraint> constraintsToCheck, Object modelElement, IEvlContext context) throws EolRuntimeException {
		if (shouldBeChecked(modelElement, context)) {
			ExecutorFactory executorFactory = context.getExecutorFactory();
			for (Constraint constraint : constraintsToCheck) {
				executorFactory.execute(constraint, context, modelElement);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Executes all of the give constraints for all applicable elements of this type.
	 * @param constraintsToCheck The Constraints, which may be a subset of this ConstraintContext's children.
	 * @param context The execution context.
	 * @throws EolRuntimeException
	 * @see {@link #execute(Collection, Object, IEvlContext)}
	 * @since 1.6
	 */
	public void execute(Collection<Constraint> constraintsToCheck, IEvlContext context) throws EolRuntimeException {
		if (!isLazy(context)) {
			ExecutorFactory executorFactory = context.getExecutorFactory();
			for (Object modelElement : getAllOfSourceKind(context)) {
				if (appliesTo(modelElement, context)) {
					for (Constraint constraint : constraintsToCheck) {
						executorFactory.execute(constraint, context, modelElement);
					}
				}
			}
		}
	}
	
	/**
	 * Executes all of this ConstraintContext's constraints for the given element.
	 * @param modelElement The model element object.
	 * @param context The execution context.
	 * @throws EolRuntimeException
	 * @see {@link #execute(Collection, Object, IEvlContext)}
	 * @since 1.6
	 */
	public boolean execute(Object modelElement, IEvlContext context) throws EolRuntimeException {
		return execute(getConstraints(), modelElement, context);
	}
	
	/**
	 *
	 * @param context_ The EVL execution context.
	 * @throws EolRuntimeException
	 * @return nothing.
	 * @see {@link #execute(Collection, Object, IEvlContext)}
	 * @since 1.6
	 */
	@Override
	public Object execute(IEolContext context_, Object self) throws EolRuntimeException {
		IEvlContext context = (IEvlContext) context_;
		return execute(getConstraints(), self, context);
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public Object execute(IEolContext context) throws EolRuntimeException {
		execute(getConstraints(), (IEvlContext) context);
		return null;
	}
	
	@Override
	public String toString() {
		return getTypeName();
	}

	/**
	 * @since 1.6
	 */
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getTypeName(), constraints.size());
	}

	/**
	 * @since 1.6
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!super.equals(other)) return false;
		
		ConstraintContext cc = (ConstraintContext) other;
		return
			Objects.equals(this.getTypeName(), cc.getTypeName()) &&
			this.constraints.size() == cc.constraints.size();
	}
	
	public void accept(IEvlVisitor visitor) {
		visitor.visit(this);
	}
	
	public ExecutableBlock<Boolean> getGuardBlock() {
		return guardBlock;
	}
	
	public void setGuardBlock(ExecutableBlock<Boolean> guardBlock) {
		this.guardBlock = guardBlock;
	}
	
}
