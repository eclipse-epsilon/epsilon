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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;

public class ReflectiveASTVisitor extends ASTVisitor {
	protected List<Object> all = null;
	protected String type = null;
	protected boolean ofTypeOnly = false;
	protected Collection<IJavaProject> projects = null;
	protected boolean resolveBindings = false;
	protected ASTParser parser = null;
	
	public ReflectiveASTVisitor(Collection<IJavaProject> projects, boolean ifBindings) {
		this.projects = projects;
		this.resolveBindings = ifBindings;
	}

	protected ASTParser getASTParser() {
		if (parser == null) {
			parser = ASTParser.newParser(AST.JLS8);
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setResolveBindings(resolveBindings);
		}
		return parser;
	}
	
	private Collection<Object> getAll(String type) throws JavaModelException {
		all = new ArrayList<Object>();
		this.type = type;
		
		for (IJavaProject project : projects) {
			for (IPackageFragment packageFragment : project.getPackageFragments()) {
				if (packageFragment.getKind() == IPackageFragmentRoot.K_SOURCE) {
					for (ICompilationUnit compilationUnit : packageFragment
							.getCompilationUnits()) {
						
						ASTParser parser = getASTParser();
						parser.setSource(compilationUnit);
						parser.createAST(null).accept(this);
					}
				}
			}
		}
		return all;
	}

	public Collection<Object> getAllOfKind(String type) throws JavaModelException {
		this.ofTypeOnly = false;
		return getAll(type);
	}

	public Collection<Object> getAllOfType(String type) throws JavaModelException {
		this.ofTypeOnly = true;
		return getAll(type);
	}

	@Override
	public void preVisit(ASTNode node) {
		if (node.getClass().getSimpleName().equals(type)) {
			all.add(node);
		} else if (!ofTypeOnly) {
			Class<?> superClass = node.getClass().getSuperclass();
			while (superClass != Object.class) {
				if (superClass.getSimpleName().equals(type)) {
					all.add(node);
					break;
				}
				superClass = superClass.getSuperclass();
			}
		}
	}

}
