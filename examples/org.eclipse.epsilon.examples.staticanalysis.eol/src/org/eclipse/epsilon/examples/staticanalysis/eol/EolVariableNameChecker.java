package org.eclipse.epsilon.examples.staticanalysis.eol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.epsilon.common.dt.editor.AbstractModuleEditor;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.IModuleValidator;
import org.eclipse.epsilon.common.module.ModuleMarker;
import org.eclipse.epsilon.common.module.ModuleMarker.Severity;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dom.AbortStatement;
import org.eclipse.epsilon.eol.dom.AndOperatorExpression;
import org.eclipse.epsilon.eol.dom.AnnotationBlock;
import org.eclipse.epsilon.eol.dom.AssignmentStatement;
import org.eclipse.epsilon.eol.dom.BooleanLiteral;
import org.eclipse.epsilon.eol.dom.BreakStatement;
import org.eclipse.epsilon.eol.dom.Case;
import org.eclipse.epsilon.eol.dom.CollectionLiteralExpression;
import org.eclipse.epsilon.eol.dom.ComplexOperationCallExpression;
import org.eclipse.epsilon.eol.dom.ContinueStatement;
import org.eclipse.epsilon.eol.dom.DeleteStatement;
import org.eclipse.epsilon.eol.dom.DivOperatorExpression;
import org.eclipse.epsilon.eol.dom.DoubleEqualsOperatorExpression;
import org.eclipse.epsilon.eol.dom.ElvisOperatorExpression;
import org.eclipse.epsilon.eol.dom.EnumerationLiteralExpression;
import org.eclipse.epsilon.eol.dom.EqualsOperatorExpression;
import org.eclipse.epsilon.eol.dom.ExecutableAnnotation;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.ExpressionInBrackets;
import org.eclipse.epsilon.eol.dom.ExpressionStatement;
import org.eclipse.epsilon.eol.dom.FirstOrderOperationCallExpression;
import org.eclipse.epsilon.eol.dom.ForStatement;
import org.eclipse.epsilon.eol.dom.GreaterEqualOperatorExpression;
import org.eclipse.epsilon.eol.dom.GreaterThanOperatorExpression;
import org.eclipse.epsilon.eol.dom.IEolVisitor;
import org.eclipse.epsilon.eol.dom.IfStatement;
import org.eclipse.epsilon.eol.dom.ImpliesOperatorExpression;
import org.eclipse.epsilon.eol.dom.Import;
import org.eclipse.epsilon.eol.dom.IntegerLiteral;
import org.eclipse.epsilon.eol.dom.ItemSelectorExpression;
import org.eclipse.epsilon.eol.dom.LessEqualOperatorExpression;
import org.eclipse.epsilon.eol.dom.LessThanOperatorExpression;
import org.eclipse.epsilon.eol.dom.MapLiteralExpression;
import org.eclipse.epsilon.eol.dom.MinusOperatorExpression;
import org.eclipse.epsilon.eol.dom.ModelDeclaration;
import org.eclipse.epsilon.eol.dom.ModelDeclarationParameter;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.NegativeOperatorExpression;
import org.eclipse.epsilon.eol.dom.NewInstanceExpression;
import org.eclipse.epsilon.eol.dom.NotEqualsOperatorExpression;
import org.eclipse.epsilon.eol.dom.NotOperatorExpression;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.dom.OperationCallExpression;
import org.eclipse.epsilon.eol.dom.OperatorExpression;
import org.eclipse.epsilon.eol.dom.OrOperatorExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.dom.PlusOperatorExpression;
import org.eclipse.epsilon.eol.dom.PostfixOperatorExpression;
import org.eclipse.epsilon.eol.dom.PropertyCallExpression;
import org.eclipse.epsilon.eol.dom.RealLiteral;
import org.eclipse.epsilon.eol.dom.ReturnStatement;
import org.eclipse.epsilon.eol.dom.SimpleAnnotation;
import org.eclipse.epsilon.eol.dom.StatementBlock;
import org.eclipse.epsilon.eol.dom.StringLiteral;
import org.eclipse.epsilon.eol.dom.SwitchStatement;
import org.eclipse.epsilon.eol.dom.TernaryExpression;
import org.eclipse.epsilon.eol.dom.ThrowStatement;
import org.eclipse.epsilon.eol.dom.TimesOperatorExpression;
import org.eclipse.epsilon.eol.dom.TransactionStatement;
import org.eclipse.epsilon.eol.dom.TypeExpression;
import org.eclipse.epsilon.eol.dom.VariableDeclaration;
import org.eclipse.epsilon.eol.dom.WhileStatement;
import org.eclipse.epsilon.eol.dom.XorOperatorExpression;

public class EolVariableNameChecker implements IModuleValidator, IEolVisitor {
	
	protected List<ModuleMarker> warnings;
	
	public EolVariableNameChecker() {}

	@Override
	public void visit(VariableDeclaration variableDeclaration) {
		if (startsWithUpperCase(variableDeclaration.getName())) {
			createUppercaseWarning(variableDeclaration.getNameExpression());
		}
	}
	
	@Override
	public void visit(Parameter parameter) {
		if (startsWithUpperCase(parameter.getName())) {
			createUppercaseWarning(parameter.getNameExpression());
		}
	}
	
	public void createUppercaseWarning(NameExpression expression) {
		warnings.add(new ModuleMarker(expression, "Variable names in EOL should start with a lower-case letter", 
				Severity.Warning));
	}
	
	protected boolean startsWithUpperCase(String name) {
		return Character.isUpperCase(name.charAt(0));
	}
	
	@Override
	public List<ModuleMarker> validate(IModule module) {
		
		if (!(module instanceof EolModule)) return Collections.emptyList();
		
		warnings = new ArrayList<>();
		
		EolModule eolModule = (EolModule) module;
		if (eolModule.getMain() != null) eolModule.getMain().accept(this);
		eolModule.getDeclaredOperations().forEach(o -> o.accept(this));
		
		return warnings;
	}

	@Override
	public String getMarkerType() {
		return AbstractModuleEditor.PROBLEM_MARKER;
	}
	
	@Override
	public void visit(AbortStatement abortStatement) {
		
	}

	@Override
	public void visit(AndOperatorExpression andOperatorExpression) {
		andOperatorExpression.getFirstOperand().accept(this);
	}

	@Override
	public void visit(DeleteStatement deleteStatement) {
		deleteStatement.getExpression().accept(this);
	}

	@Override
	public void visit(AnnotationBlock annotationBlock) {
		
	}

	@Override
	public void visit(AssignmentStatement assignmentStatement) {
		assignmentStatement.getTargetExpression().accept(this);
		assignmentStatement.getValueExpression().accept(this);
	}

	@Override
	public void visit(BooleanLiteral booleanLiteral) {
		
	}

	@Override
	public void visit(BreakStatement breakStatement) {
		
	}

	@Override
	public void visit(Case case_) {
		case_.getBody().accept(this);
	}

	@Override
	public void visit(CollectionLiteralExpression<?> collectionLiteralExpression) {
		
	}

	@Override
	public void visit(ComplexOperationCallExpression complexOperationCallExpression) {
		
	}

	@Override
	public void visit(ContinueStatement continueStatement) {
		
	}

	@Override
	public void visit(DivOperatorExpression divOperatorExpression) {
		visitOperatorExpression(divOperatorExpression);
	}

	public void visitOperatorExpression(OperatorExpression exp) {
		exp.getFirstOperand().accept(this);
		exp.getSecondOperand().accept(this);
	}
	
	@Override
	public void visit(DoubleEqualsOperatorExpression doubleEqualsOperatorExpression) {
		visitOperatorExpression(doubleEqualsOperatorExpression);
	}

	@Override
	public void visit(ElvisOperatorExpression elvisOperatorExpression) {
		visitOperatorExpression(elvisOperatorExpression);
	}

	@Override
	public void visit(EnumerationLiteralExpression enumerationLiteralExpression) {
		
	}

	@Override
	public void visit(EqualsOperatorExpression equalsOperatorExpression) {
		visitOperatorExpression(equalsOperatorExpression);
	}

	@Override
	public void visit(ExecutableAnnotation executableAnnotation) {
		
	}

	@Override
	public void visit(ExecutableBlock<?> executableBlock) {
		// No need to implement for EOL itself
	}

	@Override
	public void visit(ExpressionInBrackets expressionInBrackets) {
		expressionInBrackets.getExpression().accept(this);
	}

	@Override
	public void visit(ExpressionStatement expressionStatement) {
		expressionStatement.getExpression().accept(this);
	}

	@Override
	public void visit(FirstOrderOperationCallExpression firstOrderOperationCallExpression) {
		firstOrderOperationCallExpression.getTargetExpression().accept(this);
		firstOrderOperationCallExpression.getParameters().forEach(p -> p.accept(this));
	}

	@Override
	public void visit(ForStatement forStatement) {
		forStatement.getIteratorParameter().accept(this);
		forStatement.getIteratedExpression().accept(this);
		forStatement.getBodyStatementBlock().accept(this);
	}

	@Override
	public void visit(GreaterEqualOperatorExpression greaterEqualOperatorExpression) {
		visitOperatorExpression(greaterEqualOperatorExpression);
	}

	@Override
	public void visit(GreaterThanOperatorExpression greaterThanOperatorExpression) {
		visitOperatorExpression(greaterThanOperatorExpression);
	}

	@Override
	public void visit(IfStatement ifStatement) {
		ifStatement.getConditionExpression().accept(this);
		ifStatement.getThenStatementBlock().accept(this);
		ifStatement.getElseStatementBlock().accept(this);
	}

	@Override
	public void visit(ImpliesOperatorExpression impliesOperatorExpression) {
		visitOperatorExpression(impliesOperatorExpression);
	}

	@Override
	public void visit(Import import_) {
		
	}

	@Override
	public void visit(IntegerLiteral integerLiteral) {
		
	}

	@Override
	public void visit(ItemSelectorExpression itemSelectorExpression) {
		itemSelectorExpression.getTargetExpression().accept(this);
	}

	@Override
	public void visit(LessEqualOperatorExpression lessEqualOperatorExpression) {
		visitOperatorExpression(lessEqualOperatorExpression);
	}

	@Override
	public void visit(LessThanOperatorExpression lessThanOperatorExpression) {
		visitOperatorExpression(lessThanOperatorExpression);
	}

	@Override
	public void visit(MapLiteralExpression<?, ?> mapLiteralExpression) {
		mapLiteralExpression.getKeyValueExpressionPairs().forEach(kv -> {kv.getKey().accept(this); kv.getValue().accept(this);});
	}

	@Override
	public void visit(MinusOperatorExpression minusOperatorExpression) {
		visitOperatorExpression(minusOperatorExpression);
	}

	@Override
	public void visit(ModelDeclaration modelDeclaration) {
		
	}

	@Override
	public void visit(ModelDeclarationParameter modelDeclarationParameter) {
		
	}

	@Override
	public void visit(NameExpression nameExpression) {
		
	}

	@Override
	public void visit(NegativeOperatorExpression negativeOperatorExpression) {
		visitOperatorExpression(negativeOperatorExpression);
	}

	@Override
	public void visit(NewInstanceExpression newInstanceExpression) {
		newInstanceExpression.getParameterExpressions().forEach(pe -> pe.accept(this));
	}

	@Override
	public void visit(NotEqualsOperatorExpression notEqualsOperatorExpression) {
		visitOperatorExpression(notEqualsOperatorExpression);
	}

	@Override
	public void visit(NotOperatorExpression notOperatorExpression) {
		visitOperatorExpression(notOperatorExpression);
	}

	@Override
	public void visit(Operation operation) {
		operation.getFormalParameters().forEach(p -> p.accept(this));
		operation.getBody().accept(this);
	}

	@Override
	public void visit(OperationCallExpression operationCallExpression) {
		operationCallExpression.getTargetExpression().accept(this);
		operationCallExpression.getParameterExpressions().forEach(p -> p.accept(this));
	}

	@Override
	public void visit(OrOperatorExpression orOperatorExpression) {
		visitOperatorExpression(orOperatorExpression);
	}

	@Override
	public void visit(PlusOperatorExpression plusOperatorExpression) {
		visitOperatorExpression(plusOperatorExpression);
	}

	@Override
	public void visit(PostfixOperatorExpression postfixOperatorExpression) {
		visitOperatorExpression(postfixOperatorExpression);
	}

	@Override
	public void visit(PropertyCallExpression propertyCallExpression) {
		propertyCallExpression.getTargetExpression().accept(this);
	}

	@Override
	public void visit(RealLiteral realLiteral) {
		
	}

	@Override
	public void visit(ReturnStatement returnStatement) {
		returnStatement.getReturnedExpression().accept(this);
	}

	@Override
	public void visit(SimpleAnnotation simpleAnnotation) {
		
	}

	@Override
	public void visit(StatementBlock statementBlock) {
		statementBlock.getStatements().forEach(s -> s.accept(this));
	}

	@Override
	public void visit(StringLiteral stringLiteral) {
		
	}

	@Override
	public void visit(SwitchStatement switchStatement) {
		switchStatement.getConditionExpression().accept(this);
		switchStatement.getCases().forEach(c -> c.accept(this));
		if (switchStatement.getDefault() != null) {
			switchStatement.getDefault().accept(this);
		}
	}

	@Override
	public void visit(TernaryExpression ternaryExpression) {
		visitOperatorExpression(ternaryExpression);
		ternaryExpression.getThirdOperand().accept(this);
	}

	@Override
	public void visit(ThrowStatement throwStatement) {
		throwStatement.getThrown().accept(this);
	}

	@Override
	public void visit(TimesOperatorExpression timesOperatorExpression) {
		visitOperatorExpression(timesOperatorExpression);
	}

	@Override
	public void visit(TransactionStatement transactionStatement) {
		transactionStatement.getBody().accept(this);
	}

	@Override
	public void visit(TypeExpression typeExpression) {
		
	}
	
	@Override
	public void visit(WhileStatement whileStatement) {
		whileStatement.getConditionExpression().accept(this);
		whileStatement.getBodyStatementBlock().accept(this);
	}

	@Override
	public void visit(XorOperatorExpression xorOperatorExpression) {
		visitOperatorExpression(xorOperatorExpression);
	}

	

}
