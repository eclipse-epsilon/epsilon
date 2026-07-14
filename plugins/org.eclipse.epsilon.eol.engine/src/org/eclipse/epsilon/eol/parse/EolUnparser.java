/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.parse;

import java.util.Iterator;
import java.util.Map.Entry;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dom.*;

public class EolUnparser implements IEolVisitor {
	
	protected StringBuffer buffer = new StringBuffer();
	protected EolModule module = null;
	protected int indentation = 0;
	
	public String unparse(EolModule module) {
		
		this.module = module;
		buffer = new StringBuffer();
		
		module.getImports().stream().forEach(i -> {i.accept(this); newline();});
		module.getDeclaredModelDeclarations().stream().forEach(md -> {md.accept(this); newline();});
		unparseMain();
		module.getDeclaredOperations().stream().forEach(o -> {newline(); o.accept(this);});
		
		return buffer.toString();
	}
	
	public String getCode() {
		return buffer.toString();
	}
	
	protected void unparseMain() {
		module.getMain().accept(this);
	}
	
	protected void newline() {
		buffer.append(System.lineSeparator());
	}
	
	protected void comma() {
		buffer.append(", ");
	}
	
	protected void space() {
		buffer.append(' ');
	}
	
	protected void startCurlybrace() {
		buffer.append('{');
	}
	
	protected void endCurlybrace() {
		buffer.append('}');
	}
	
	protected void plusIndentationAndAppend() {
		++indentation;
		indent();
	}
	
	protected void minusIndentationAndAppend() {
		--indentation;
		indent();
	}
	
	protected void spaceCurlybraceNewlineIndent() {
		space();
		startCurlybrace();
		newline();
		plusIndentationAndAppend();
	}
	
	protected void newlineUnindentCurlybrace() {
		newline();
		minusIndentationAndAppend();
		endCurlybrace();
	}
	
	
	@Override
	public void visit(AbortStatement abortStatement) {
		buffer.append("abort;");
	}

	@Override
	public void visit(AndOperatorExpression andOperatorExpression) {
		unparseBinaryOperatorExpression(andOperatorExpression, "and");
	}

	@Override
	public void visit(DeleteStatement deleteStatement) {
		buffer.append("delete ");
		if (deleteStatement.getExpression() != null) {
			deleteStatement.getExpression().accept(this);
		}
		semicolon();
	}
	
	protected void semicolon() {
		buffer.append(";");
	}



	@Override
	public void visit(AssignmentStatement assignmentStatement) {
		assignmentStatement.getTargetExpression().accept(this);
		buffer.append(" = ");
		assignmentStatement.getValueExpression().accept(this);
		buffer.append(";");
	}

	@Override
	public void visit(BooleanLiteral booleanLiteral) {
		buffer.append(booleanLiteral.getValue());
	}

	@Override
	public void visit(BreakStatement breakStatement) {
		buffer.append(breakStatement.isAll() ? "breakAll" : "break");
		semicolon();
	}

	@Override
	public void visit(Case case_) {
		if (case_.getCondition() != null) {
			buffer.append("case ");
			case_.getCondition().accept(this);
		}
		else buffer.append("default");
		buffer.append(": ");
		case_.getBody().accept(this);
	}

	@Override
	public void visit(CollectionLiteralExpression<?> collectionLiteralExpression) {
		buffer.append(collectionLiteralExpression.getCollectionType() + "{");
		Iterator<Expression> li = collectionLiteralExpression.getParameterExpressions().iterator();
		while (li.hasNext()) {
			li.next().accept(this);
			if (li.hasNext()) {
				if (collectionLiteralExpression.isRange()) {
					buffer.append("..");
				}
				else {
					comma();
				}
			}
		}
		buffer.append("}");
	}

	@Override
	public void visit(ComplexOperationCallExpression complexOperationCallExpression) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(ContinueStatement continueStatement) {
		buffer.append("continue;");
	}

	@Override
	public void visit(DivOperatorExpression divOperatorExpression) {
		unparseBinaryOperatorExpression(divOperatorExpression, "/");
	}

	@Override
	public void visit(DoubleEqualsOperatorExpression doubleEqualsOperatorExpression) {
		unparseBinaryOperatorExpression(doubleEqualsOperatorExpression, "==");
	}

	@Override
	public void visit(ElvisOperatorExpression elvisOperatorExpression) {
		elvisOperatorExpression.getFirstOperand().accept(this);
		buffer.append(" ?: ");
		elvisOperatorExpression.getSecondOperand().accept(this);
	}

	@Override
	public void visit(EnumerationLiteralExpression enumerationLiteralExpression) {
		buffer.append(enumerationLiteralExpression.getEnumerationLiteral());
	}

	@Override
	public void visit(EqualsOperatorExpression equalsOperatorExpression) {
		unparseBinaryOperatorExpression(equalsOperatorExpression, "=");
	}

	@Override
	public void visit(ExecutableBlock<?> executableBlock) {
		// No need to implement for EOL itself
	}

	@Override
	public void visit(ExpressionInBrackets expressionInBrackets) {
		buffer.append("(");
		unparse(expressionInBrackets.getExpression());
		buffer.append(")");
	}

	@Override
	public void visit(ExpressionStatement expressionStatement) {
		unparse(expressionStatement.getExpression());
		semicolon();
	}
	
	protected void unparse(Expression expression) {
		if (expression != null) expression.accept(this);
	}
	
	@Override
	public void visit(FirstOrderOperationCallExpression operationCallExpression) {
		unparse(operationCallExpression.getTargetExpression());
		arrowOrDot(operationCallExpression);
		buffer.append(operationCallExpression.getName() + "(");
		Iterator<Parameter> pi = operationCallExpression.getParameters().iterator();
		while (pi.hasNext()) {
			pi.next().accept(this);
			if (pi.hasNext()) comma();
		}
		if (!operationCallExpression.getExpressions().isEmpty()) {
			buffer.append("|");
			Iterator<Expression> ei = operationCallExpression.getExpressions().iterator();
			while (ei.hasNext()) {
				ei.next().accept(this);
				if (ei.hasNext()) comma();
			}
		}
		buffer.append(")");
	}

	@Override
	public void visit(ForStatement forStatement) {
		buffer.append("for (");
		forStatement.getIteratorParameter().accept(this);
		buffer.append(" in ");
		forStatement.getIteratedExpression().accept(this);
		buffer.append(") ");
		forStatement.getBodyStatementBlock().accept(this);
	}

	@Override
	public void visit(GreaterEqualOperatorExpression greaterEqualOperatorExpression) {
		unparseBinaryOperatorExpression(greaterEqualOperatorExpression, ">=");
	}

	@Override
	public void visit(GreaterThanOperatorExpression greaterThanOperatorExpression) {
		unparseBinaryOperatorExpression(greaterThanOperatorExpression, ">");
	}

	@Override
	public void visit(IfStatement ifStatement) {
		buffer.append("if (");
		unparse(ifStatement.getConditionExpression());
		buffer.append(") ");
		ifStatement.getThenStatementBlock().accept(this);
		if (ifStatement.getElseStatementBlock() != null) {
			newline();
			indent();
			buffer.append("else ");
			StatementBlock elseStatementBlock = ifStatement.getElseStatementBlock();
			if (elseStatementBlock.getStatements().size() == 1 && elseStatementBlock.getStatements().get(0) instanceof IfStatement) {
				elseStatementBlock.getStatements().get(0).accept(this);
			}
			else {
				ifStatement.getElseStatementBlock().accept(this);
			}
		}
	}

	@Override
	public void visit(ImpliesOperatorExpression impliesOperatorExpression) {
		unparseBinaryOperatorExpression(impliesOperatorExpression, "implies");
	}

	@Override
	public void visit(Import import_) {
		buffer.append("import \"" + import_.getPath() + "\";");
	}

	@Override
	public void visit(IntegerLiteral integerLiteral) {
		buffer.append(integerLiteral.getText());
	}

	@Override
	public void visit(ItemSelectorExpression itemSelectorExpression) {
		itemSelectorExpression.getTargetExpression().accept(this);
		buffer.append("[");
		itemSelectorExpression.getIndexExpression().accept(this);
		buffer.append("]");
	}

	@Override
	public void visit(LessEqualOperatorExpression lessEqualOperatorExpression) {
		unparseBinaryOperatorExpression(lessEqualOperatorExpression, "<=");
	}

	@Override
	public void visit(LessThanOperatorExpression lessThanOperatorExpression) {
		unparseBinaryOperatorExpression(lessThanOperatorExpression, "<");
	}

	@Override
	public void visit(MapLiteralExpression<?, ?> mapLiteralExpression) {
		buffer.append(mapLiteralExpression.getMapName()).append("{");
		Iterator<Entry<Expression, Expression>> li = mapLiteralExpression.getKeyValueExpressionPairs().iterator();
		while (li.hasNext()) {
			Entry<Expression, Expression> next = li.next();
			next.getKey().accept(this);
			buffer.append(" = ");
			next.getValue().accept(this);
			if (li.hasNext()) comma();
		}
		buffer.append("}");
	}

	@Override
	public void visit(MinusOperatorExpression minusOperatorExpression) {
		unparseBinaryOperatorExpression(minusOperatorExpression, "-");
	}

	@Override
	public void visit(ModelDeclaration modelDeclaration) {
		buffer.append("model ");
		modelDeclaration.getNameExpression().accept(this);
		if (!modelDeclaration.getAliasNameExpressions().isEmpty()) {
			buffer.append(" alias ");
			Iterator<NameExpression> li = modelDeclaration.getAliasNameExpressions().iterator();
			while (li.hasNext()) {
				li.next().accept(this);
				if (li.hasNext()) comma();
			}
		}
		buffer.append(" driver ");
		modelDeclaration.getDriverNameExpression().accept(this);
		buffer.append(" {");
		Iterator<ModelDeclarationParameter> li = modelDeclaration.getModelDeclarationParameters().iterator();
		while (li.hasNext()) {
			li.next().accept(this);
			if (li.hasNext()) comma();
		}
		buffer.append("}");
	}

	@Override
	public void visit(ModelDeclarationParameter modelDeclarationParameter) {
		buffer.append(modelDeclarationParameter.getKey());
		buffer.append(" = \"");
		buffer.append(modelDeclarationParameter.getValue());
		buffer.append("\"");
	}

	@Override
	public void visit(NameExpression nameExpression) {
		buffer.append(nameExpression.getName());
	}

	@Override
	public void visit(NegativeOperatorExpression negativeOperatorExpression) {
		buffer.append("-");
		unparse(negativeOperatorExpression.getFirstOperand());
	}

	@Override
	public void visit(NewInstanceExpression newInstanceExpression) {
		buffer.append("new ");
		newInstanceExpression.getTypeExpression().accept(this);
		if (!newInstanceExpression.getParameterExpressions().isEmpty()) {
			buffer.append("(");
			Iterator<Expression> li = newInstanceExpression.getParameterExpressions().iterator();
			while (li.hasNext()) {
				li.next().accept(this);
				if (li.hasNext()) comma();
			}
			buffer.append(")");
		}
	}

	@Override
	public void visit(NotEqualsOperatorExpression notEqualsOperatorExpression) {
		unparseBinaryOperatorExpression(notEqualsOperatorExpression, notEqualsOperatorExpression.getOperator() != null ? notEqualsOperatorExpression.getOperator() : "<>");
	}

	@Override
	public void visit(NotOperatorExpression notOperatorExpression) {
		buffer.append("not ");
		notOperatorExpression.getFirstOperand().accept(this);
	}

	@Override
	public void visit(Operation operation) {
		unparseAnnotations(operation);
		buffer.append("operation ");
		if (operation.getContextTypeExpression() != null) {
			operation.getContextTypeExpression().accept(this);
			space();
		}
		buffer.append(operation.getName() + "(");
		Iterator<Parameter> li = operation.getFormalParameters().iterator();
		while (li.hasNext()) {
			li.next().accept(this);
			if (li.hasNext()) comma();
		}
		buffer.append(") ");
		if (operation.getReturnTypeExpression() != null) {
			buffer.append(": ");
			operation.getReturnTypeExpression().accept(this);
			space();
		}
		operation.getBody().accept(this);
	}

	@Override
	public void visit(OperationCallExpression operationCallExpression) {
		if (operationCallExpression.getTargetExpression() != null) {
			unparse(operationCallExpression.getTargetExpression());
			arrowOrDot(operationCallExpression);
		}
		buffer.append(operationCallExpression.getName() + "(");
		Iterator<Expression> li = operationCallExpression.getParameterExpressions().iterator();
		while (li.hasNext()) {
			li.next().accept(this);
			if (li.hasNext()) comma();
		}
		buffer.append(")");
	}

	@Override
	public void visit(OrOperatorExpression orOperatorExpression) {
		unparseBinaryOperatorExpression(orOperatorExpression, "or");
	}

	@Override
	public void visit(Parameter parameter) {
		buffer.append(parameter.getName());
		if (parameter.getTypeExpression() != null) {
			buffer.append(" : ");
			parameter.getTypeExpression().accept(this);
		}
	}

	@Override
	public void visit(PlusOperatorExpression plusOperatorExpression) {
		unparseBinaryOperatorExpression(plusOperatorExpression, "+");
	}

	@Override
	public void visit(PostfixOperatorExpression postfixOperatorExpression) {
		postfixOperatorExpression.getFirstOperand().accept(this);
		buffer.append(postfixOperatorExpression.isIncrease() ? "++" : "--");
	}

	@Override
	public void visit(PropertyCallExpression propertyCallExpression) {
		propertyCallExpression.getTargetExpression().accept(this);
		// TODO: Check null-safe
		arrowOrDot(propertyCallExpression);
		propertyCallExpression.getNameExpression().accept(this);
	}

	@Override
	public void visit(RealLiteral realLiteral) {
		buffer.append(realLiteral.getText());
	}

	@Override
	public void visit(ReturnStatement returnStatement) {
		buffer.append("return");
		if (returnStatement.getReturnedExpression() != null) {
			buffer.append(" ");
			returnStatement.getReturnedExpression().accept(this);
		}
		semicolon();
	}

	@Override
	public void visit(AnnotationBlock annotationBlock) {
		annotationBlock.getAnnotations().forEach(a -> {indent(); a.accept(this); newline();});
	}
	
	@Override
	public void visit(SimpleAnnotation simpleAnnotation) {
		buffer.append("@" + simpleAnnotation.getName());
		if (simpleAnnotation.getValue() != null) {
			buffer.append(" " + simpleAnnotation.getValue());
		}
	}
	
	@Override
	public void visit(ExecutableAnnotation executableAnnotation) {
		buffer.append("$" + executableAnnotation.getName());
		if (executableAnnotation.getExpression() != null) {
			space();
			executableAnnotation.getExpression().accept(this);
		}
	}

	@Override
	public void visit(StatementBlock statementBlock) {
		if (module == null || statementBlock != module.getMain()) {
			buffer.append("{");
			newline();
			indentation++;
		}
		statementBlock.getStatements().forEach(s -> {indent(); s.accept(this); newline();});
		if (module == null || statementBlock != module.getMain()) {
			indentation--;
			indent();
			buffer.append("}");
		}
	}
	
	protected void indent() {
		for (int i=0;i<indentation;i++) buffer.append("\t");
	}
	
	@Override
	public void visit(StringLiteral stringLiteral) {
		String quote = stringLiteral.isDoubleQuoted() ? "\"" : "'";
		buffer.append(quote + escape(stringLiteral.getValue()) + quote);
	}
	
	public static String escape(String s) {
		return s.replace("\\", "\\\\").replace("\t", "\\t").replace("\b", "\\b").replace("\n", "\\n")
				.replace("\r", "\\r").replace("\f", "\\f").replace("\"", "\\\"");
	}
	
	@Override
	public void visit(SwitchStatement switchStatement) {
		buffer.append("switch (");
		switchStatement.getConditionExpression().accept(this);
		buffer.append(") {");
		newline();
		indentation++;
		switchStatement.getCases().forEach(c -> { indent(); c.accept(this); newline();});
		if (switchStatement.getDefault() != null) {
			indent(); switchStatement.getDefault().accept(this); newline();
		}
		indentation--;
		indent();
		buffer.append("}");
		
	}

	@Override
	public void visit(TernaryExpression ternaryExpression) {
		ternaryExpression.getFirstOperand().accept(this);
		buffer.append(" ? ");
		ternaryExpression.getSecondOperand().accept(this);
		buffer.append(" : ");
		ternaryExpression.getThirdOperand().accept(this);
	}

	@Override
	public void visit(ThrowStatement throwStatement) {
		buffer.append("throw");
		if (throwStatement.getThrown() != null) {
			buffer.append(" ");
			throwStatement.getThrown().accept(this);
		}
		semicolon();
	}

	@Override
	public void visit(TimesOperatorExpression timesOperatorExpression) {
		unparseBinaryOperatorExpression(timesOperatorExpression, "*");
	}
	
	protected void unparseBinaryOperatorExpression(OperatorExpression operatorExpression, String operator) {
		operatorExpression.getFirstOperand().accept(this);
		buffer.append(" " + operator + " ");
		operatorExpression.getSecondOperand().accept(this);
	}
	
	protected void arrowOrDot(FeatureCallExpression expression) {
		if (expression.isNullSafe()) buffer.append("?");
		buffer.append(expression.isArrow() ? "->" : ".");
	}
	
	@Override
	public void visit(TransactionStatement transactionStatement) {
		buffer.append("transaction ");
		Iterator<NameExpression> li = transactionStatement.getModelNames().iterator();
		while (li.hasNext()) {
			li.next().accept(this);
			if (li.hasNext()) comma();
			else space();
		}
		transactionStatement.getBody().accept(this);
	}

	@Override
	public void visit(TypeExpression typeExpression) {
		buffer.append(typeExpression.getName());
		if (typeExpression.getNativeType() != null) {
			buffer.append("(");
			typeExpression.getNativeType().accept(this);
			buffer.append(")");
		}
		else if (!typeExpression.getParameterTypeExpressions().isEmpty()) {
			buffer.append("(");
			Iterator<TypeExpression> li = typeExpression.getParameterTypeExpressions().iterator();
			while (li.hasNext()) {
				li.next().accept(this);
				if (li.hasNext()) comma();
			}
			buffer.append(")");
		}
	}

	@Override
	public void visit(VariableDeclaration variableDeclaration) {
		buffer.append("var " + variableDeclaration.getName());
		if (variableDeclaration.getTypeExpression() != null) {
			buffer.append(" : ");
			if (variableDeclaration.isInstantiate()) buffer.append("new ");
			variableDeclaration.getTypeExpression().accept(this);
		}
	}

	@Override
	public void visit(WhileStatement whileStatement) {
		buffer.append("while (");
		unparse(whileStatement.getConditionExpression());
		buffer.append(")");
		whileStatement.getBodyStatementBlock().accept(this);
	}

	@Override
	public void visit(XorOperatorExpression xorOperatorExpression) {
		unparseBinaryOperatorExpression(xorOperatorExpression, "xor");
	}
	
	protected void unparseAnnotations(AnnotatableModuleElement annotatableModuleElement) {
		if (annotatableModuleElement.getAnnotationBlock() != null) {
			annotatableModuleElement.getAnnotationBlock().accept(this);
		}
	}
	
}
