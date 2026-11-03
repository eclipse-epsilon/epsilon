/*******************************************************************************
 * Copyright (c) 2026 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 ******************************************************************************/
package org.eclipse.epsilon.coverage;

import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.Region;
import org.eclipse.epsilon.eol.dom.AnnotationBlock;
import org.eclipse.epsilon.eol.dom.Annotation;
import org.eclipse.epsilon.eol.dom.AssignmentStatement;
import org.eclipse.epsilon.eol.dom.Expression;
import org.eclipse.epsilon.eol.dom.FeatureCallExpression;
import org.eclipse.epsilon.eol.dom.Import;
import org.eclipse.epsilon.eol.dom.ItemSelectorExpression;
import org.eclipse.epsilon.eol.dom.ModelDeclaration;
import org.eclipse.epsilon.eol.dom.NameExpression;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.dom.PropertyCallExpression;
import org.eclipse.epsilon.eol.dom.Statement;
import org.eclipse.epsilon.eol.dom.TypeExpression;
import org.eclipse.epsilon.eol.dom.VariableDeclaration;
import org.eclipse.epsilon.eol.dom.StatementBlock;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;

/**
 * Decides which AST nodes are coverage probes, i.e. nodes that are expected to be
 * executed through the {@link org.eclipse.epsilon.eol.execute.ExecutorFactory}
 * (and as such will be seen by the {@link CoverageRecorder}) whenever the code they
 * represent runs. Nodes that are evaluated directly by their parents (e.g. the name
 * of a feature call, type expressions, assignment targets) are not probes, as they
 * would otherwise always appear as not covered.
 * 
 * @since 2.10
 */
public class CoverageProbes {
	
	public static final CoverageProbes INSTANCE = new CoverageProbes();
	
	/**
	 * @return Whether the subtree rooted at this element should be ignored entirely
	 * (e.g. imports, model declarations, annotations and parameter declarations)
	 */
	public boolean isIgnored(ModuleElement element) {
		return element instanceof Import || element instanceof ModelDeclaration 
			|| element instanceof AnnotationBlock || element instanceof Annotation 
			|| element instanceof Parameter || element instanceof TypeExpression;
	}
	
	public boolean isProbe(ModuleElement element) {
		if (!hasRegion(element)) return false;
		if (!(element instanceof Statement || element instanceof Expression)) return false;
		if (element instanceof TypeExpression) return false;
		
		ModuleElement parent = element.getParent();
		
		// Names used as identifiers (e.g. names of variable declarations, 
		// operations, rules) rather than as expressions are not executed
		if (element instanceof NameExpression) {
			if (parent instanceof VariableDeclaration && ((VariableDeclaration) parent).getNameExpression() == element) {
				return false;
			}
			if (!(parent instanceof Expression || parent instanceof Statement || 
				parent instanceof StatementBlock || parent instanceof ExecutableBlock)) {
				return false;
			}
		}
		
		// The name of a property/operation call is never executed on its own
		if (parent instanceof FeatureCallExpression && 
				((FeatureCallExpression) parent).getNameExpression() == element) {
			return false;
		}
		
		// Assignment targets are resolved by the assignment statement itself
		if (parent instanceof AssignmentStatement && 
				((AssignmentStatement) parent).getTargetExpression() == element) {
			return !(element instanceof NameExpression || 
				element instanceof PropertyCallExpression ||
				element instanceof ItemSelectorExpression);
		}
		
		return true;
	}
	
	public static boolean hasRegion(ModuleElement element) {
		Region region = element.getRegion();
		return region != null && region.getStart() != null && region.getEnd() != null 
			&& region.getStart().getLine() > 0;
	}
}
