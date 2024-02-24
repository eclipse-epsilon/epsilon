/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************
 *
 * $Id$
 */
package org.eclipse.epsilon.eol.engine.test.acceptance;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.eclipse.epsilon.eol.engine.test.acceptance.circularImports.CircularImportTests;
import org.eclipse.epsilon.eol.engine.test.acceptance.dom.*;
import org.eclipse.epsilon.eol.engine.test.acceptance.equalityStatement.*;
import org.eclipse.epsilon.eol.engine.test.acceptance.exceptions.ExceptionTests;
import org.eclipse.epsilon.eol.engine.test.acceptance.firstOrder.*;
import org.eclipse.epsilon.eol.engine.test.acceptance.firstOrder.lambda.*;
import org.eclipse.epsilon.eol.engine.test.acceptance.firstOrder.nested.*;
import org.eclipse.epsilon.eol.engine.test.acceptance.recording.*;
import org.eclipse.epsilon.eol.engine.test.acceptance.unparser.EolUnparserTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	AssignmentTests.class,
	CollectionPropertyTests.class,
	ComparisonTests.class,
	CreateDeleteTests.class,
	EqualityTests.class,
	CollectionsTests.class,
	MathTests.class,
	StringTests.class,
	AnnotatedOperationTests.class,
	IsDefinedTests.class,
	ExceptionTests.class,
	IterableTests.class,
	EqualityBecomesAssignmentInStatements.class,
	ReturnTypeTests.class,
	BuiltInVariablesTests.class,
	ModelElementTypeResolutionTests.class,
	BooleanTests.class,
	TernaryTests.class,
	PropertyAccessRecording.class,
	ScopeTests.class,
	ContextlessFirstOrderOperationTests.class,
	DomTests.class,
	VariableTests.class,
	OperationTests.class,
	PostfixOperatorTests.class,
	CompositeAssignmentTests.class,
	SwitchTests.class,
	TransactionTests.class,
	ModelElementConstructorTests.class,
	FirstOrderOperationTests.class,
	ParallelFirstOrderOperationTests.class,
	ParallelFirstOrderOperationEquivalenceTests.class,
	FirstOrderOperationAdvancedTests.class,
	FirstOrderOperationExceptionHandlingTests.class,
	TernaryTests.class,
	SafeNavigationTests.class,
	ElvisTests.class,
	EolModuleEquivalenceTests.class,
	NestedParallelOperationTests.class,
	AutoParallelOperationTests.class,
	LambdaExpressionTests.class,
	LambdaExpressionExceptionTest.class,
	ParallelStreamTests.class,
	CommentsTests.class,
	EolUnparserTests.class,
	BreakAndContinueTests.class,
	EnumResolutionTests.class,
	CircularImportTests.class
})
public class EolAcceptanceTestSuite {

	public static Test suite() {
		return new junit.extensions.RepeatedTest(new JUnit4TestAdapter(EolAcceptanceTestSuite.class), 10);
	}
}
