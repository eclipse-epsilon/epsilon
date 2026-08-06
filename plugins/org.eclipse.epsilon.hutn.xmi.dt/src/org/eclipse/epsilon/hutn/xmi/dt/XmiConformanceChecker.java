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
package org.eclipse.epsilon.hutn.xmi.dt;

import java.io.PrintStream;
import java.util.Collection;

import org.eclipse.core.resources.IResource;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.hutn.dt.markers.MarkerManager;
import org.eclipse.epsilon.hutn.xmi.Xmi2Hutn;

public class XmiConformanceChecker {

	private final ConformanceReporter reporter;
	
	public XmiConformanceChecker() {
		this(new PrintStreamConformanceInformer(System.out, System.err));
	}
	
	public XmiConformanceChecker(ConformanceReporter reporter) {
		this.reporter = reporter;
	}
	
	public void reportConformanceOf(IResource resource) {		
		try {
			final Collection<ParseProblem> conformanceProblems = new Xmi2Hutn(resource.getRawLocationURI()).checkConformanceWithRegisteredMetamodel();
			
			new MarkerManager(resource).replaceErrorMarkers(conformanceProblems);
			
			if (conformanceProblems.isEmpty()) {
				reporter.reportConformant(resource.getName());
			} else {
				reporter.reportNonConformant(resource.getName(), conformanceProblems);
			}
			
		} catch (Exception e) {
			LogUtil.log("Error encountered while checking the conformance of: " + resource.getName(), e);
		}
	}
	
	
	private static class PrintStreamConformanceInformer implements ConformanceReporter {

		private final PrintStream infoStream, errorStream;
		
		public PrintStreamConformanceInformer(PrintStream infoStream, PrintStream errorStream) {
			this.infoStream  = infoStream;
			this.errorStream = errorStream;
		}

		public void reportConformant(String name) {
			infoStream.println(name + " conforms to its registered metamodel.");			
		}
		
		public void reportNonConformant(String name, Collection<ParseProblem> conformanceProblems) {
			errorStream.println(name + " does not conform to its registered metamodel.");
			
			for (ParseProblem conformanceProblem : conformanceProblems) {
				errorStream.println("\t" + conformanceProblem.toString());
			}
		}
	}
}
