/*******************************************************************************
 * Copyright (c) 2008-2018 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Sina Madani - refactoring
 ******************************************************************************/
package org.eclipse.epsilon.evl.dom;

import java.util.*;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElementParameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.erl.dom.NamedRule;
import org.eclipse.epsilon.evl.execute.FixInstance;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.evl.execute.context.IEvlContext;
import org.eclipse.epsilon.evl.execute.operations.SatisfiesOperation;
import org.eclipse.epsilon.evl.parse.EvlParser;

public class Constraint extends NamedRule implements IExecutableModuleElementParameter {
	
	protected boolean isCritique = false;
	protected List<Fix> fixes = new ArrayList<Fix>();
	protected ConstraintContext constraintContext;
	protected ExecutableBlock<Boolean> guardBlock;
	protected ExecutableBlock<Boolean> checkBlock;
	protected ExecutableBlock<String> messageBlock;
	
	/**
	 * This will be set to <code>true</code> by {@link SatisfiesOperation}.
	 * @since 1.6
	 */
	protected boolean isDependedOn = false;
	
	@Override
	@SuppressWarnings("unchecked")
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		if (cst.getType() == EvlParser.CRITIQUE) {
			isCritique = true;
		}
		this.guardBlock = (ExecutableBlock<Boolean>) module.createAst(AstUtil.getChild(cst, EvlParser.GUARD), this);
		this.checkBlock = (ExecutableBlock<Boolean>) module.createAst(AstUtil.getChild(cst,EvlParser.CHECK), this);
		this.messageBlock = (ExecutableBlock<String>) module.createAst(AstUtil.getChild(cst, EvlParser.MESSAGE), this);
		
		List<AST> fixASTs = AstUtil.getChildren(cst, EvlParser.FIX);
		fixes = new ArrayList<>(fixASTs.size());
		
		for (AST fixAst : fixASTs) {
			fixes.add((Fix) module.createAst(fixAst, this));
		}
	}
	
	public boolean isInfo() {
		return isCritique() && hasAnnotation("info");
	}
	
	public boolean isLazy(IEvlContext context) throws EolRuntimeException {
		return getBooleanAnnotationValue("lazy", context);
	}
	
	public boolean shouldBeChecked(Object modelElement, IEvlContext context) throws EolRuntimeException {
		return !context.shouldShortCircuit(this) && !isLazy(context) && appliesTo(modelElement, context);
	}
	
	@Override
	public Optional<UnsatisfiedConstraint> execute(IEolContext context_, Object self) throws EolRuntimeException {
		IEvlContext context = (IEvlContext) context_;
		if (shouldBeChecked(self, context)) {
			return check(self, context);
		}
		return Optional.empty();
	}

	// FIXME : Currently examines only the local guard
	public boolean appliesTo(Object object, IEvlContext context) throws EolRuntimeException {
		if (guardBlock != null)
			return guardBlock.execute(context, Variable.createReadOnlyVariable("self", object));
		
		return true;
	}

	/**
	 * 
	 * @param self
	 * @param context
	 * @param result
	 * @return
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	public boolean optimisedCheck(Object self, IEvlContext context, boolean result) throws EolRuntimeException {
		return postprocessCheck(self, context, preprocessCheck(self, context), result);
	}

	/**
	 *
	 * @param self
	 * @param context
	 * @return
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	public Optional<UnsatisfiedConstraint> check(Object self, IEvlContext context) throws EolRuntimeException {
		UnsatisfiedConstraint unsatisfiedConstraint = preprocessCheck(self, context);
		boolean result;
		
		// Look for a result in the trace first if this constraint is a dependency, otherwise run the check block
		if (isDependedOn && context.getConstraintTrace().isChecked(this, self)) {
			result = context.getConstraintTrace().isSatisfied(this, self);
		}
		else {
			result = executeCheckBlock(self, context);
			if (!context.isOptimizeConstraintTrace()) {
				context.getConstraintTrace().addChecked(this, self, result);
			}
		}
		
		result = postprocessCheck(self, context, unsatisfiedConstraint, result);
		return result ? Optional.empty() : Optional.of(unsatisfiedConstraint);
	}

	protected UnsatisfiedConstraint preprocessCheck(Object self, IEvlContext context) {
		UnsatisfiedConstraint unsatisfiedConstraint = new UnsatisfiedConstraint();
		context.getFrameStack()
			.enterLocal(FrameType.UNPROTECTED, checkBlock.getBody())
			.put(
				Variable.createReadOnlyVariable("self", self),
				Variable.createReadOnlyVariable("extras", unsatisfiedConstraint.getExtras())
			);
		return unsatisfiedConstraint;
	}
	
	/**
	 * 
	 * @param self
	 * @param context
	 * @return
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	protected boolean executeCheckBlock(Object self, IEvlContext context) throws EolRuntimeException {
		return checkBlock.execute(context, false);
	}

	protected boolean postprocessCheck(Object self, IEvlContext context, UnsatisfiedConstraint unsatisfiedConstraint, boolean result) throws EolRuntimeException {
		if (!result) {
			unsatisfiedConstraint.setInstance(self);
			unsatisfiedConstraint.setConstraint(this);

			String messageResult = getUnsatisfiedMessage(self, context);

			unsatisfiedConstraint.setMessage(messageResult);
			context.getUnsatisfiedConstraints().add(unsatisfiedConstraint);
			
			Collection<FixInstance> unsatisfiedConstraintFixes = unsatisfiedConstraint.getFixes();
			for (Fix fix : fixes) {
				if (!fix.appliesTo(self, context)) continue;

				FixInstance fixInstance = new FixInstance(context, fix);
				fixInstance.setSelf(self);
				unsatisfiedConstraintFixes.add(fixInstance);
			}
			
			// Update the short-circuiting logic on fail
			context.shouldShortCircuit(this);
		}

		// Don't dispose the frame we leave if unsatisfied because it may be needed for fix parts,
		// otherwise leaveLocal if constraint is satisfied or there are no possible further uses for it.
		context.getFrameStack().leaveLocal(checkBlock.getBody(), result || (fixes.isEmpty() && messageBlock == null));
		return result;
	}

	/**
	 * Returns the message to be used in {@linkplain UnsatisfiedConstraint#getMessage()}.
	 * 
	 * @param self The model element.
	 * @param context The execution context.
	 * @return The message if this Constraint's check block returns false.
	 * @throws EolRuntimeException
	 * @since 1.6
	 */
	public String getUnsatisfiedMessage(Object self, IEvlContext context) throws EolRuntimeException {
		return messageBlock != null ?
			messageBlock.execute(context, false) :
			"Invariant "+this.getName()+" failed for "+context.getPrettyPrinterManager().toString(self);
	}
	
	/**
	 * Checks whether this constraint's guard block has dependencies on other constraint(s).
	 * 
	 * @return <code>true</code> if the guard block exists and has a satisfies call.
	 * @since 1.6
	 */
	public boolean guardBlockUsesSatisfies() {
		return guardBlock != null && guardBlock.getText().contains("satisfies");
	}

	public ConstraintContext getConstraintContext() {
		return constraintContext;
	}

	public void setConstraintContext(ConstraintContext constraintContext) {
		setParent(this.constraintContext = constraintContext);
	}
	
	public boolean isCritique() {
		return isCritique;
	}

	public void setCritique(boolean isCritique) {
		this.isCritique = isCritique;
	}

	/**
	 * @return Whether this constraint is the target of a dependency.
	 * @since 1.6
	 */
	public boolean isDependedOn() {
		return isDependedOn;
	}
	
	/**
	 * Used to flag this constraint as the target of a dependency.
	 * @since 1.6
	 */
	public void setAsDependency() {
		this.isDependedOn = true;
	}
	
	/**
	 * @since 1.6
	 */
	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), constraintContext, isCritique);
	}

	/**
	 * @since 1.6
	 */
	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (!super.equals(other)) return false;
		
		Constraint constraint = (Constraint) other;
		return
			Objects.equals(this.constraintContext, constraint.constraintContext) &&
			Objects.equals(this.isCritique, constraint.isCritique);
	}
	
	public ExecutableBlock<Boolean> getGuardBlock() {
		return guardBlock;
	}
	
	public void setGuardBlock(ExecutableBlock<Boolean> guardBlock) {
		this.guardBlock = guardBlock;
	}
	
	public ExecutableBlock<Boolean> getCheckBlock() {
		return checkBlock;
	}
	
	public void setCheckBlock(ExecutableBlock<Boolean> checkBlock) {
		this.checkBlock = checkBlock;
	}
	
	public ExecutableBlock<String> getMessageBlock() {
		return messageBlock;
	}
	
	public void setMessageBlock(ExecutableBlock<String> messageBlock) {
		this.messageBlock = messageBlock;
	}
	
	public List<Fix> getFixes() {
		return fixes;
	}
	
	public void accept(IEvlVisitor visitor) {
		visitor.visit(this);
	}
}
