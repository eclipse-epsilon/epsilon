/*******************************************************************************
 * Copyright (c) 2016 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.evl.dom;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.eol.dom.AndOperatorExpression;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.FirstOrderOperationCallExpression;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.NotOperatorExpression;
import org.eclipse.epsilon.eol.dom.OperationCallExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.dom.PropertyCallExpression;
import org.eclipse.epsilon.eol.dom.ReturnStatement;
import org.eclipse.epsilon.eol.dom.StatementBlock;
import org.eclipse.epsilon.eol.dom.TypeExpression;
import org.eclipse.epsilon.eol.models.IModel;

/**
 * Decides if a constraint with context T, guard G and check C can be rephrased
 * into a single T.all.select(x|G and not C) query, which can be efficiently
 * executed by certain {@link IModel} implementations (e.g. Hawk or SQL).
 */
public class ConstraintSelectTransfomer {

	public boolean canBeTransformed(Constraint constraint) {
		ConstraintContext constraintContext = constraint.getConstraintContext();
		return constraintContext != null
			&& constraintContext.getTypeName() != null
			&& isOptimisableExpression(constraintContext.guardBlock)
			&& isOptimisableExpression(constraint.guardBlock)
			&& isOptimisableExpression(constraint.checkBlock);
	}

	private static boolean isOptimisableExpression(ExecutableBlock<Boolean> block) {
		if (block == null) {
			// non-existing blocks are trivially optimisable
			return true;
		}

		// AST must not contain any invocations of satisfies*
		if (isDependentOnOtherRules(block)) {
			return false;
		}

		// Either this is not a block (just an expression), or it's
		// a "simple" block with a single "return EXPRESSION;" statement.
		//AST expressionOrBlock = block.getFirstChild();
		IExecutableModuleElement expressionOrBlock = block.getBody();
		if (expressionOrBlock instanceof StatementBlock) {
			return isSimpleBlock((StatementBlock) expressionOrBlock);
		}
		else {
			return true;
		}
	}

	private static boolean isSimpleBlock(StatementBlock block) {
		if (block.getStatements().size() != 1) {
			return false;
		}
		return block.getStatements().get(0) instanceof ReturnStatement;
	}

	private static boolean isDependentOnOtherRules(ModuleElement node) {
		if (node instanceof OperationCallExpression) {
			OperationCallExpression opCall = (OperationCallExpression)node;
			if (opCall.getParameterExpressions().size() > 0) {
				final String functionName = opCall.getName();
				if ("satisfies".equals(functionName)
						|| "satisfiesAll".equals(functionName)
						|| "satisfiesOne".equals(functionName)) {

						// It's one of the functions that declares dependencies between rules and prevents this optimisation from happening
						return true;
					}
			}
		}

		for (ModuleElement child : node.getChildren()) {
			if (isDependentOnOtherRules(child)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Produces a new ExecutableBlock that will find all invalid objects at once.
	 * This method assumes that {@link #canBeTransformed(Constraint)} was checked
	 * first, and that it returned <code>true</code>.
	 */
	public ExecutableBlock<?> transformIntoSelect(Constraint constraint) {
		assert canBeTransformed(constraint) : "The constraint is optimisable";

		final Expression contextGuardExpression = getExpressionFromBlock(constraint.getConstraintContext().guardBlock);
		final Expression constraintGuardExpression = getExpressionFromBlock(constraint.guardBlock);
		final Expression checkExpression = getExpressionFromBlock(constraint.checkBlock);

		// Join the guard G and check C expressions into 'G and not C'
		Expression selectExpression = new NotOperatorExpression(checkExpression);
		if (constraintGuardExpression != null) {
			selectExpression = new AndOperatorExpression(constraintGuardExpression, selectExpression);
		}
		if (contextGuardExpression != null) {
			selectExpression = new AndOperatorExpression(contextGuardExpression, selectExpression);
		}

		// Create the T.all.select(self | ...) expression - the internal 'self' overrides the outer one
		final String typeName = constraint.getConstraintContext().getTypeName();
		final Expression optimisedExpression = new FirstOrderOperationCallExpression(
			new PropertyCallExpression(new TypeExpression(typeName), new NameExpression("all")),
			new NameExpression("select"),
			new Parameter(new NameExpression("self"), new TypeExpression(typeName)),
			selectExpression);

		// Need to do this in order to make it go through addChild (otherwise ANTLR silently drops it)
		// optimisedExpression.setToken(new CommonToken(Evl_EolParserRules.RETURN));

		// Create the executable block
		ExecutableBlock<?> newBlock = new ExecutableBlock<>(java.util.Collection.class);
		newBlock.setBody(optimisedExpression);
		//newBlock.addChild(optimisedExpression);
		//newBlock.build();
		return newBlock;
	}

	private static Expression getExpressionFromBlock(ExecutableBlock<Boolean> guardBlock) {
		if (guardBlock != null) {
			if (guardBlock.getBody() instanceof Expression) {
				return (Expression) guardBlock.getBody();
			}
			else if (guardBlock.getBody() instanceof StatementBlock) {
				StatementBlock statementBlock = (StatementBlock) guardBlock.getBody();

				// This only works because of the check in isSimpleBlock(...)
				ReturnStatement returnStatement = (ReturnStatement) statementBlock.getStatements().get(0);
				return returnStatement.getReturnedExpression();
			}
		}

		return null;
	}

}
