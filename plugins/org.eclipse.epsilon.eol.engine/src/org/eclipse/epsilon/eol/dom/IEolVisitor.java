/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.dom;

public interface IEolVisitor {
	
	public void visit(AbortStatement abortStatement);
	
	public void visit(AndOperatorExpression andOperatorExpression);

	public void visit(DeleteStatement deleteStatement);

	public void visit(AnnotationBlock annotationBlock);

	public void visit(AssignmentStatement assignmentStatement);

	public void visit(BooleanLiteral booleanLiteral);

	public void visit(BreakStatement breakStatement);

	public void visit(Case case_);

	public void visit(CollectionLiteralExpression<?> collectionLiteralExpression);

	public void visit(ComplexOperationCallExpression complexOperationCallExpression);

	public void visit(ContinueStatement continueStatement);

	public void visit(DivOperatorExpression divOperatorExpression);

	public void visit(DoubleEqualsOperatorExpression doubleEqualsOperatorExpression);

	public void visit(ElvisOperatorExpression elvisOperatorExpression);

	public void visit(EnumerationLiteralExpression enumerationLiteralExpression);

	public void visit(EqualsOperatorExpression equalsOperatorExpression);

	public void visit(ExecutableAnnotation executableAnnotation);

	public void visit(ExecutableBlock<?> executableBlock);

	public void visit(ExpressionInBrackets expressionInBrackets);

	public void visit(ExpressionStatement expressionStatement);

	public void visit(FirstOrderOperationCallExpression firstOrderOperationCallExpression);

	public void visit(ForStatement forStatement);

	public void visit(GreaterEqualOperatorExpression greaterEqualOperatorExpression);

	public void visit(GreaterThanOperatorExpression greaterThanOperatorExpression);

	public void visit(IfStatement ifStatement);

	public void visit(ImpliesOperatorExpression impliesOperatorExpression);

	public void visit(Import import_);

	public void visit(IntegerLiteral integerLiteral);

	public void visit(ItemSelectorExpression itemSelectorExpression);

	public void visit(LessEqualOperatorExpression lessEqualOperatorExpression);

	public void visit(LessThanOperatorExpression lessThanOperatorExpression);

	public void visit(MapLiteralExpression<?, ?> mapLiteralExpression);

	public void visit(MinusOperatorExpression minusOperatorExpression);

	public void visit(ModelDeclaration modelDeclaration);

	public void visit(ModelDeclarationParameter modelDeclarationParameter);

	public void visit(NameExpression nameExpression);

	public void visit(NegativeOperatorExpression negativeOperatorExpression);

	public void visit(NewInstanceExpression newInstanceExpression);

	public void visit(NotEqualsOperatorExpression notEqualsOperatorExpression);

	public void visit(NotOperatorExpression notOperatorExpression);

	public void visit(Operation operation);

	public void visit(OperationCallExpression operationCallExpression);

	public void visit(OrOperatorExpression orOperatorExpression);

	public void visit(Parameter parameter);

	public void visit(PlusOperatorExpression plusOperatorExpression);

	public void visit(PostfixOperatorExpression postfixOperatorExpression);

	public void visit(PropertyCallExpression propertyCallExpression);

	public void visit(RealLiteral realLiteral);

	public void visit(ReturnStatement returnStatement);

	public void visit(SimpleAnnotation simpleAnnotation);

	public void visit(StatementBlock statementBlock);

	public void visit(StringLiteral stringLiteral);

	public void visit(SwitchStatement switchStatement);

	public void visit(TernaryExpression ternaryExpression);

	public void visit(ThrowStatement throwStatement);

	public void visit(TimesOperatorExpression timesOperatorExpression);

	public void visit(TransactionStatement transactionStatement);

	public void visit(TypeExpression typeExpression);

	public void visit(VariableDeclaration variableDeclaration);

	public void visit(WhileStatement whileStatement);

	public void visit(XorOperatorExpression xorOperatorExpression);
	
}
