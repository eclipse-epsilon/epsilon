/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.jdt;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.epsilon.eol.dom.EqualsOperatorExpression;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.dom.PropertyCallExpression;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.execute.operations.AbstractOperation;
import org.eclipse.epsilon.eol.execute.operations.declarative.IAbstractOperationContributor;
import org.eclipse.epsilon.eol.execute.operations.declarative.SelectOperation;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.core.search.SearchMatch;
import org.eclipse.jdt.core.search.SearchParticipant;
import org.eclipse.jdt.core.search.SearchPattern;
import org.eclipse.jdt.core.search.SearchRequestor;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.core.SourceType;
import org.eclipse.jdt.internal.corext.refactoring.structure.ASTNodeSearchUtil;

/**
 * Variant of <code>TypeDeclaration.all</code> that integrates case sensitive
 * pattern matching into its <code>.select(td|pattern='...')</code> method.
 */
@SuppressWarnings("restriction")
public class SearchableTypeCollection extends AbstractCollection<Object> implements IAbstractOperationContributor {

	protected boolean isSearchByName(String iteratorName, Expression condition) {
		if (condition instanceof EqualsOperatorExpression) {
			EqualsOperatorExpression equalsOperatorExpression = (EqualsOperatorExpression) condition;
			if (equalsOperatorExpression.getFirstOperand() instanceof PropertyCallExpression) {
				PropertyCallExpression propertyCallExpression = (PropertyCallExpression) equalsOperatorExpression
						.getFirstOperand();
				if (propertyCallExpression.getTargetExpression() instanceof NameExpression) {
					NameExpression nameExpression = (NameExpression) propertyCallExpression.getTargetExpression();
					if (nameExpression.getName().equals(iteratorName)
							&& propertyCallExpression.getNameExpression().getName().equals("name")) {
						return true;
					}
				}
			}
		}
		return false;
	}

	protected Object performSearch(IEolContext context, final Expression nameExpression, final List<Object> results,
			final SearchRequestor requestor) throws EolRuntimeException {
		SearchPattern pattern = SearchPattern.createPattern(
				context.getExecutorFactory().execute(nameExpression, context) + "", IJavaSearchConstants.TYPE,
				IJavaSearchConstants.DECLARATIONS, SearchPattern.R_PATTERN_MATCH | SearchPattern.R_CASE_SENSITIVE);

		IJavaSearchScope scope = SearchEngine.createJavaSearchScope(javaProjects, IJavaSearchScope.SOURCES);

		SearchEngine engine = new SearchEngine();
		try {
			engine.search(pattern, new SearchParticipant[] { SearchEngine.getDefaultSearchParticipant() }, scope,
					requestor, null);
			return results;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private final class SelectSearchParticipant extends SelectOperation {

		@Override
		public Object execute(Object target, Variable iterator, Expression expression,
				IEolContext context, boolean returnOnFirstMatch) throws EolRuntimeException {

			if (!isSearchByName(iterator.getName(), expression)) {
				return new SelectOperation().execute(target, iterator, expression, context, returnOnFirstMatch);
			}
			final EqualsOperatorExpression equalsOperatorExpression = (EqualsOperatorExpression) expression;
			final Expression nameExpression = equalsOperatorExpression.getSecondOperand();

			final List<Object> results = new ArrayList<Object>();
			final SearchRequestor requestor = new SearchRequestor() {

				@Override
				public void acceptSearchMatch(SearchMatch match) throws CoreException {
					/*
					 * Note: DOM nodes and internal nodes seem to be created
					 * through different processes, but we have the
					 * ICompilationUnit in common.
					 */
					if (match.getElement() instanceof SourceType) {
						final SourceType srcType = (SourceType) match.getElement();

						/*
						 * TODO: so far, it seems we need to parse the file.
						 *
						 * TODO: take into account nested types / package names
						 * (how to get fully qualified name from DOM node?)
						 */

						ASTParser parser = ASTParser.newParser(AST.JLS8);
						parser.setKind(ASTParser.K_COMPILATION_UNIT);
						parser.setResolveBindings(true);
						parser.setSource(srcType.getCompilationUnit());

						/*
						 * Suggestion from Eclipse forum -
						 * https://www.eclipse.org/forums/index.php?t=rview&goto
						 * =1738534#msg_1738534.
						 *
						 * TODO: discuss jdt.ui dependency with Dimitris.
						 */
						CompilationUnit domAST = (CompilationUnit) parser.createAST(null);
						ASTNode astNode = ASTNodeSearchUtil.getAstNode(match, domAST);

						org.eclipse.jdt.core.dom.TypeDeclaration td = (org.eclipse.jdt.core.dom.TypeDeclaration) astNode.getParent();
						results.add(td);
					} else {
						results.add(match.getElement());
					}
				}
			};

			return performSearch(context, nameExpression, results, requestor);
		}
	}

	/**
	 * More performant but less convenient version of the .select, which does
	 * not reparse and therefore returns raw search results.
	 */
	private final class SearchOperation extends AbstractOperation {
		@Override
		public Object execute(Object target, NameExpression operationNameExpression, List<Parameter> iterators,
				List<Expression> expressions, IEolContext context) throws EolRuntimeException {
			if (!isSearchByName(iterators.get(0).getName(), expressions.get(0))) {
				return new SelectOperation().execute(target, operationNameExpression, iterators, expressions, context);
			}
			final EqualsOperatorExpression equalsOperatorExpression = (EqualsOperatorExpression) expressions.get(0);
			final Expression nameExpression = equalsOperatorExpression.getSecondOperand();

			final List<Object> results = new ArrayList<Object>();
			final SearchRequestor requestor = new SearchRequestor() {
				@Override
				public void acceptSearchMatch(SearchMatch match) throws CoreException {
					results.add(match.getElement());
				}
			};

			return performSearch(context, nameExpression, results, requestor);
		}
	}

	protected IJavaProject[] javaProjects = null;

	/**
	 * The visitor is only used if the script tries to iterate through the
	 * entire TypeDeclaration.all list.
	 */
	protected ReflectiveASTVisitor visitor;

	public SearchableTypeCollection(IJavaProject[] javaProjects, ReflectiveASTVisitor visitor) {
		this.javaProjects = javaProjects;
		this.visitor = visitor;
	}

	@Override
	public AbstractOperation getAbstractOperation(String name) {
		if ("select".equals(name)) {
			return new SelectSearchParticipant();
		} else if ("search".equals(name)) {
			return new SearchOperation();
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<Object> iterator() {
		try {
			return (Iterator<Object>) getAllTypes().iterator();
		} catch (JavaModelException e) {
			throw new RuntimeException(e);
		}
	}

	private Collection<?> getAllTypes() throws JavaModelException {
		return visitor.getAllOfType(TypeDeclaration.class.getSimpleName());
	}

	@Override
	public int size() {
		try {
			return getAllTypes().size();
		} catch (JavaModelException e) {
			throw new RuntimeException(e);
		}
	}

}
